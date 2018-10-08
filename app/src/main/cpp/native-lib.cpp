#include <jni.h>
#include <string>
#include <android/log.h>
#define LOG_TAG "native-dev"#define LOGI(...)
#define TAG"Adas"
#define LOGD(...)__android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__)//定义LOGD类型
#define LOGI(...)__android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)//定义LOGI类型
#define LOGW(...)__android_log_print(ANDROID_LOG_WARN,TAG,__VA_ARGS__)//定义LOGW类型
#define LOGE(...)__android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__)//定义LOGE类型
#define LOGF(...)__android_log_print(ANDROID_LOG_FATAL,TAG,__VA_ARGS__)//定义LOGF类型

extern "C" {
#include "libavcodec/avcodec.h"
#include "libavformat/avformat.h"
#include "libswresample/swresample.h"
#include "libswscale/swscale.h"
#include <android/native_window_jni.h>
#include <unistd.h>
#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_Android.h>
}



extern "C"
JNIEXPORT jstring JNICALL
Java_com_adasplus_update_c_MainActivity_stringFromJNI(JNIEnv *env, jobject  ) {
    char info[10000] = { 0 };
    sprintf(info, "%s\n", avcodec_configuration());
    LOGE("%s",avcodec_configuration());

    av_register_all();
    char path[1024] = "/sdcard/aa.mp4";
    AVFormatContext *ic = NULL;
    int videoStream = 0;
    int re = avformat_open_input(&ic, path, 0, 0);
    if (re != 0)
    {
        char errorbuf[1024] = { 0 };
        av_strerror(re, errorbuf, sizeof(errorbuf));
        LOGE("%s","open failure");
        return env->NewStringUTF(info);
    }

    for (int i = 0; i < ic->nb_streams; i++)
    {
        AVCodecContext *enc = ic->streams[i]->codec;
        if (enc->codec_type == AVMEDIA_TYPE_VIDEO)
        {
            videoStream = i;
            LOGE("%d",videoStream);
            LOGE("%d",ic->nb_streams);
            AVCodec *codec = avcodec_find_decoder(enc->codec_id);
            if (!codec)
            {
                printf("video code not find!\n");
                return env->NewStringUTF(info);
            }
            int err = avcodec_open2(enc, codec, NULL);
            if (err != 0)

            {
                char buf[1024] = { 0 };
                av_strerror(err, buf, sizeof(buf));
                return env->NewStringUTF(info);
            }
        }
    }





    AVPacket pkt;
    memset(&pkt, 0, sizeof(AVPacket));
    for (;;) {

        int err = av_read_frame(ic, &pkt);
        if (err != 0) {
            char buf[1024] = {0};
            av_strerror(err, buf, sizeof(buf));
            break;
        }
        if (pkt.stream_index == videoStream) {
            LOGE("%d", pkt.size);
        }
        av_packet_unref(&pkt);
    }
}



