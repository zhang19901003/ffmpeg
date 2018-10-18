#include <jni.h>
#include <string>
#include "FFDemux.h"
#include "XLog.h"

using namespace std;

class TestObs :public IObserver{
public:
    void Update (XData xData){
        LOGE("TestObs Update data size %d",xData.size);
    }
};

extern "C"
JNIEXPORT jint JNICALL
Java_com_adasplus_update_c_XPlay_Open(JNIEnv *env, jobject instance, jstring url_,
                                      jobject surface) {
    const char* url  =   (char*)env->GetStringUTFChars(url_,0);
    IDemux *id =  new FFDemux();
    TestObs*tobs=new TestObs();
    id->AddObs(tobs);
    id->Open(url);
    id->Start();
    XSleep(50);

    id->Stop();
    return 0;
}






