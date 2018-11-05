//
// Created by zhangyapeng on 18-11-5.
//

#ifndef C_PROJECT_XLOG_H
#define C_PROJECT_XLOG_H
#define TAG "ffmpeg"

#define LOGD(...)__android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__)
#define LOGI(...)__android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)
#define LOGW(...)__android_log_print(ANDROID_LOG_WARN,TAG,__VA_ARGS__)
#define LOGE(...)__android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__)
#define LOGF(...)__android_log_print(ANDROID_LOG_FATAL,TAG,__VA_ARGS__)
#include <android/log.h>

class XLog {

};


#endif //C_PROJECT_XLOG_H
