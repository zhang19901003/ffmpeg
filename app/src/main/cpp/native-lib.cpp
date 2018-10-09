#include <jni.h>
#include <string>
#include <android/log.h>
#include <stdio.h>
#include <iostream>
#define LOG_TAG "native-dev"#define LOGI(...)
#define TAG "Adas"
#define LOGD(...)__android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__)
#define LOGI(...)__android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)
#define LOGW(...)__android_log_print(ANDROID_LOG_WARN,TAG,__VA_ARGS__)
#define LOGE(...)__android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__)
#define LOGF(...)__android_log_print(ANDROID_LOG_FATAL,TAG,__VA_ARGS__)



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

using namespace std;
extern "C"
JNIEXPORT jint JNICALL
Java_com_adasplus_update_c_MainActivity_ffOpen(JNIEnv *env, jobject instance, jstring path_) {
    const char *path = env->GetStringUTFChars(path_, 0);
    const std::string message = avcodec_configuration();
    LOGE("%s", message.c_str());
    av_register_all();
    avformat_network_init();
    AVFormatContext *ic = NULL;
    int videoStream = 0;
    int re = avformat_open_input(&ic, path, 0, 0);
    if (re != 0) {
        char errorbuf[1024] = {0};
        av_strerror(re, errorbuf, sizeof(errorbuf));
        LOGE("%s", "open failure");
        return -1;
    }

    for (int i = 0; i < ic->nb_streams; i++) {
        AVCodecContext *enc = ic->streams[i]->codec;
        if (enc->codec_type == AVMEDIA_TYPE_VIDEO) {
            videoStream = i;
            LOGE("%d", videoStream);
            LOGE("%d", ic->nb_streams);
            AVCodec *codec = avcodec_find_decoder(enc->codec_id);
            if (!codec) {
                return -2;
            }
            int err = avcodec_open2(enc, codec, NULL);
            if (err != 0) {
                char buf[1024] = {0};
                av_strerror(err, buf, sizeof(buf));
                return -3;
            }
        }
    }

    int i = 0;
    AVPacket pkt;
    memset(&pkt, 0, sizeof(AVPacket));
    for (;;) {
        i++;
        int err = av_read_frame(ic, &pkt);
        if (err != 0) {
            char buf[1024] = {0};

            av_strerror(err, buf, sizeof(buf));
            LOGE("%s", err);
            break;
        }
        
        if (pkt.stream_index == videoStream) {

        }
        av_packet_unref(&pkt);

    }
    return 0;
}



