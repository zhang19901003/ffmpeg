//
// Created by zhangyapeng on 18-10-19.
//
#define TAG "ffmpeg"
#define LOGD(...)__android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__)
#define LOGI(...)__android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)
#define LOGW(...)__android_log_print(ANDROID_LOG_WARN,TAG,__VA_ARGS__)
#define LOGE(...)__android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__)
#define LOGF(...)__android_log_print(ANDROID_LOG_FATAL,TAG,__VA_ARGS__)

#include <android/log.h>
#include "TextSig.h"
 TextSig* TextSig::Get(){

//     TextSig text;
//     TextSig text1;
//     LOGE("%p %p", &text ,&text1 );




     return new TextSig();
 }