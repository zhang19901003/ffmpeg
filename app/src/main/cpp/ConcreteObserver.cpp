//
// Created by zhangyapeng on 18-11-2.
//
#define TAG "ffmpeg"
#define LOGD(...)__android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__)
#define LOGI(...)__android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)
#define LOGW(...)__android_log_print(ANDROID_LOG_WARN,TAG,__VA_ARGS__)
#define LOGE(...)__android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__)
#define LOGF(...)__android_log_print(ANDROID_LOG_FATAL,TAG,__VA_ARGS__)

#include <android/log.h>
#include "ConcreteObserver.h"
void ConcreteObserver:: Update(float price) {
     LOGE("%d,%s",(int)price,m_strName.c_str());
}