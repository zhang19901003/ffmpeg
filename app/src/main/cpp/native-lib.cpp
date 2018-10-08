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
//编码
#include "libavcodec/avcodec.h"
//封装格式处理
#include "libavformat/avformat.h"
#include "libswresample/swresample.h"
//像素处理
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
    return env->NewStringUTF(info);
}



