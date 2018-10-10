#include <jni.h>
#include <string>
#include <android/log.h>
#include <stdio.h>
#include <iostream>

#define TAG "ffmpeg"
#define LOGD(...)__android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__)
#define LOGI(...)__android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)
#define LOGW(...)__android_log_print(ANDROID_LOG_WARN,TAG,__VA_ARGS__)
#define LOGE(...)__android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__)
#define LOGF(...)__android_log_print(ANDROID_LOG_FATAL,TAG,__VA_ARGS__)

#include <media/NdkMediaCodec.h>

extern "C" {
#include <libavcodec/avcodec.h>
#include "libavformat/avformat.h"
#include "libswresample/swresample.h"
#include "libswscale/swscale.h"
#include <android/native_window_jni.h>
#include <unistd.h>
#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_Android.h>
}


static double r2b(AVRational rational) {
    return rational.num == 0 || rational.den == 0 ? 0 : (double) rational.num /
                                                        (double) rational.den;
}


using namespace std;
extern "C"
JNIEXPORT jint JNICALL
Java_com_adasplus_update_c_MainActivity_ffOpen(JNIEnv *env, jobject instance, jstring path_) {
    av_register_all();
    avformat_network_init();
    //注册解码器
    avcodec_register_all();
    const char *path = env->GetStringUTFChars(path_, 0);
    AVFormatContext *ic = NULL;

    int re = avformat_open_input(&ic, path, 0, 0);
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
    //codec = avcodec_find_decoder_by_name("h264_mediacodec");
    if (!codec) {
        LOGW("avcodec_find_decoder null");
        return -2;
    }
    //解码器初始化
    AVCodecContext *cc = avcodec_alloc_context3(codec);
    avcodec_parameters_to_context(cc, ic->streams[vS]->codecpar);
    cc->thread_count = 8;
    //打开解码器
    re = avcodec_open2(cc, 0, 0);
    if (re != 0) {
        LOGW("解码器打开失败");
        return -3;
    }
    AVPacket *pkt = av_packet_alloc();

    for (;;) {

        int re = av_read_frame(ic, pkt);
        if (re != 0) {

            int pos = 20 * r2b(ic->streams[vS]->time_base);
            LOGW("video end %d,%d,%d", ic->streams[vS]->time_base.den,
                 ic->streams[vS]->time_base.num, pos);

            av_seek_frame(ic, vS, pos, AVSEEK_FLAG_BACKWARD | AVSEEK_FLAG_FRAME);
            continue;
        }
        LOGW("stream = %d,size = %d,pts = %lld, flag =%d", pkt->stream_index, pkt->size, pkt->pts,
             pkt->flags);
        av_packet_unref(pkt);
    }
    avformat_close_input(&ic);
    env->ReleaseStringUTFChars(path_, path);
    return 0;
}
