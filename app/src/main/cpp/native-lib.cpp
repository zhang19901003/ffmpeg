#include <jni.h>
#include <string>
#include <android/log.h>
#include <stdio.h>
#include <iostream>
#include <fstream>

#define TAG "ffmpeg"
#define LOGD(...)__android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__)
#define LOGI(...)__android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)
#define LOGW(...)__android_log_print(ANDROID_LOG_WARN,TAG,__VA_ARGS__)
#define LOGE(...)__android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__)
#define LOGF(...)__android_log_print(ANDROID_LOG_FATAL,TAG,__VA_ARGS__)

#include <android/native_window.h>
#include <android/native_window_jni.h>
#include <media/NdkMediaCodec.h>

extern "C" {
#include <libavcodec/avcodec.h>
#include "libavformat/avformat.h"
#include <libavcodec/jni.h>
#include "libswresample/swresample.h"
#include "libswscale/swscale.h"
#include <unistd.h>
#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_Android.h>
#include <unistd.h>


}

using namespace std;

static double r2b(AVRational rational) {
    return rational.num == 0 || rational.den == 0 ? 0 : (double) rational.num /
                                                        (double) rational.den;
}

long long GetNowMs() {
    struct timeval tv;
    gettimeofday(&tv, NULL);
    int sec = tv.tv_sec % 36000000;
    long long t = sec * 1000 + tv.tv_usec / 1000;
    return t;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_adasplus_update_c_XPlay_Open(JNIEnv *env, jobject instance, jstring path_,
                                      jobject surface) {
    const char *url = env->GetStringUTFChars(path_, 0);
    av_register_all();
    avformat_network_init();
    //注册解码器
    avcodec_register_all();

    AVFormatContext *ic = NULL;

    int videoStream = 0;
    int re = avformat_open_input(&ic, url, 0, 0);
    if (re != 0) {
        char errorbuf[1024] = {0};
        av_strerror(re, errorbuf, sizeof(errorbuf));
        LOGE("%s", "open failure");
        return -1;
    }
    //flv h264 格式获取不到时长 nb_stream（没有头部信息） 用这个方法去读取一段数据 做探测
    // 但是此方法不能保证所有格式都能获取时长，只能通过遍历帧数获取时长
    re = avformat_find_stream_info(ic, 0);
    if (re != 0) {
        LOGE("%s", "avformat_find_stream_info failure");
    }
    LOGE("%lld", ic->duration);
    LOGE("%d", ic->nb_streams);
    int vS = -1;
    int aS = -1;
    int fps = -1;
    for (int i = 0; i < ic->nb_streams; i++) {

        AVStream *as = ic->streams[i];
        if (as->codecpar->codec_type == AVMEDIA_TYPE_VIDEO) {
            LOGE("视频数据");
            vS = i;
            fps = r2b(as->avg_frame_rate);
            // codec_id 28 h264   AV_PIX_FMT_YUVJ420P = codecpar->format = 8
            LOGW("帧率%d,w=%d,h=%d codeid =%d pixformat =%d", fps, as->codecpar->width,
                 as->codecpar->height,
                 as->codecpar->codec_id,
                 as->codecpar->format);
        } else if (as->codecpar->codec_type == AVMEDIA_TYPE_AUDIO) {
            LOGE("音频数据");
            aS = i;
            LOGW("sample_rate = %d,channels = %d ,sample_format = %d",
                 as->codecpar->sample_rate,
                 as->codecpar->channels,
                 as->codecpar->format);
        }
    }
    aS = av_find_best_stream(ic, AVMEDIA_TYPE_AUDIO, -1, -1, NULL, 0);
    LOGW("av_find_best_stream = %d", aS);
    vS = av_find_best_stream(ic, AVMEDIA_TYPE_VIDEO, -1, -1, NULL, 0);
    LOGW("av_find_best_stream = %d", vS);
    //软解码器
    AVCodec *codec = avcodec_find_decoder(ic->streams[vS]->codecpar->codec_id);
    //硬解码
    codec = avcodec_find_decoder_by_name("h264_mediacodec");
    if (!codec) {
        LOGW("avcodec_find_decoder null");
        return -2;
    }
    //解码器初始化
    AVCodecContext *vc = avcodec_alloc_context3(codec);
    avcodec_parameters_to_context(vc, ic->streams[vS]->codecpar);
    vc->thread_count = 8;
    //打开解码器
    re = avcodec_open2(vc, 0, 0);
    if (re != 0) {
        LOGW("视频解码器打开失败");
        return -3;
    }


    if (aS >= 0) {
        //   //软解码器
        AVCodec *acodec = avcodec_find_decoder(ic->streams[aS]->codecpar->codec_id);
        AVCodecContext *ac = avcodec_alloc_context3(acodec);
        avcodec_parameters_to_context(ac, ic->streams[aS]->codecpar);
        ac->thread_count = 8;
        //打开解码器
        re = avcodec_open2(ac, 0, 0);
        if (re != 0) {
            LOGW("解码音频器打开失败");
            return -5;
        }
    }

    AVPacket *pkt = av_packet_alloc();
    AVFrame *frame = av_frame_alloc();
    //初始化像素格式转换上下文
    SwsContext *vctx = NULL;
    int outWidth = 640;
    int outHeight = 480;
    long long start = GetNowMs();
    int frameCount = 0;
    char *rgb = new char[1920 * 1080 * 4];
    //显示窗口初始化
    ANativeWindow *nwin = ANativeWindow_fromSurface(env, surface);
    int aa = ANativeWindow_setBuffersGeometry(nwin, outWidth, outHeight, WINDOW_FORMAT_RGBA_8888);
    ANativeWindow_Buffer wbuf;
    for (;;) {
        if (GetNowMs() - start >= 3000) {
            LOGW("now decode fps is  %d", frameCount / 3);
            start = GetNowMs();
            frameCount = 0;
        }
        int re = av_read_frame(ic, pkt);
        if (re != 0) {
            int pos = 20 * r2b(ic->streams[vS]->time_base);
            LOGW("video end %d,%d,%d", ic->streams[vS]->time_base.den,
                 ic->streams[vS]->time_base.num, pos);
            av_seek_frame(ic, vS, pos, AVSEEK_FLAG_BACKWARD | AVSEEK_FLAG_FRAME);
            continue;
        }

        AVCodecContext *cc = vc;
        if (pkt->stream_index != AVMEDIA_TYPE_VIDEO) {
            continue;
        }
        re = avcodec_send_packet(cc, pkt);
        //发送到线程中解码  pkt 会被复制 所以可以清理
        // LOGW("stream = %d,size = %d,pts = %lld, flag =%d",pkt->stream_index,pkt->size,pkt->pts,pkt->flags);
        av_packet_unref(pkt);
        if (re != 0) {
            //解码失败
            LOGW("avcodec_send_packet failure");
            continue;
        }

        for (;;) {
            re = avcodec_receive_frame(cc, frame);
            if (re != 0) {
                //  LOGW("avcodec_receive_frame failure");
                break;
            }
            //        LOGW("avcodec_receive_frame success %lld",frame->pts);
            if (cc == vc) {

                vctx = sws_getCachedContext(vctx,
                                            frame->width,
                                            frame->height,
                                            (AVPixelFormat) frame->format,
                                            outWidth,
                                            outHeight,
                                            AV_PIX_FMT_RGBA,
                                            SWS_FAST_BILINEAR,
                                            0, 0, 0
                );
                if (vctx == NULL) {
                    LOGW("sws_getCachedContext failed");
                } else {
                    uint8_t *data[AV_NUM_DATA_POINTERS] = {0};
                    data[0] = (uint8_t *) rgb;
                    int lines[AV_NUM_DATA_POINTERS] = {0};
                    // 一行宽度  根据像素格式确定大小
                    lines[0] = outWidth * 4;
                    int h = sws_scale(vctx, (const uint8_t *const *) frame->data, frame->linesize,
                                      0, frame->height,
                                      data, lines
                    );
                    LOGW("sws_scale %d", h);
                    if (h > 0) {
                        ANativeWindow_lock(nwin, &wbuf, 0);
                        uint8_t *dst = (uint8_t *) wbuf.bits;
                        memcpy(dst, rgb, outWidth * outHeight * 4);
                        ANativeWindow_unlockAndPost(nwin);
                    }
                }
                frameCount++;
            }
        }
    }
    delete (rgb);
    avformat_close_input(&ic);
    env->ReleaseStringUTFChars(path_, url);
    return 0;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_adasplus_update_c_MainActivity_test(JNIEnv *env, jobject instance) {


    char date[1024 * 4];

    fstream afile;
    afile.open("/sdcard/gps.txt", ios::out | ios::in | ios::binary);
    fstream afile1;
    afile1.open("/sdcard/123456.txt", ios::out | ios::in | ios::trunc);
    afile.clear();
    for (;;) {
        memset(date, 0, strlen(date));
        if (afile.eof())
            break;
        afile.read(date, 1024 * 4);
        LOGE("%d ,%s ", strlen(date), date);
        afile1.write(date, strlen(date));
        memset(date, 0, strlen(date));
    }


    afile.close();
    afile1.close();





//    char buffer[1000];
//    ifstream in("/sdcard/gps.txt");
//    if (! in.is_open())
//    { cout << "Error opening file"; exit (1); }
//    for (;;)
//    {
//        in.getline (buffer,1000);
//        if(in.eof())
//            break;
//        LOGE("%s , %d", buffer,strlen(buffer));
//
//    }
//    in.close();
}

extern "C"
JNIEXPORT
jint JNI_OnLoad(JavaVM *vm, void *res) {
    av_jni_set_java_vm(vm, 0);
    return JNI_VERSION_1_4;
}




