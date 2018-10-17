#include <jni.h>
#include <string>
#include "FFDemux.h"
#include "XLog.h"

using namespace std;



extern "C"
JNIEXPORT jint JNICALL
Java_com_adasplus_update_c_XPlay_Open(JNIEnv *env, jobject instance, jstring url_,
                                      jobject surface) {
    const char* url  =   (char*)env->GetStringUTFChars(url_,0);
    IDemux *id =  new FFDemux();
    id->Open(url);
    for (; ;) {
      XData xData=  id->Read();
        LOGE("XDATA SIZE  %d",xData.size);
    }
    return 0;
}






