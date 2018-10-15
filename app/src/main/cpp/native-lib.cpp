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
#define SRCFILE "foreman_cif.yuv"
#define DSTFILE "out.rgb"

#include <android/native_window.h>
#include <android/native_window_jni.h>
#include <media/NdkMediaCodec.h>
#include <GLES2/gl2.h>
#include <EGL/egl.h>


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
#include "stdio.h"
#include "errno.h"
}

using namespace std;

double division(int a, int b) {
    if (b == 0) {
        throw "Division by zero condition!";
    }
    return (a / b);
}

//class MyException : public exception
//{
//public : const char * what () const throw ()
//    {
//        return "C++ Exception";
//    }
//};


class MyException {
public:
    MyException(const char *message)
            : message_(message) {
        cout << "MyException ..." << endl;
        LOGE("MyException ...");
    }

    MyException(const MyException &other) : message_(other.message_) {
        cout << "Copy MyException ..." << endl;
        LOGE("Copy MyException ...");
    }

    virtual ~MyException() {
        cout << "~MyException ..." << endl;
        LOGE("~MyException ...");
    }

    const char *what() const {
        return message_.c_str();
    }

private:
    string message_;
};

class MyExceptionD : public MyException {
public:
    MyExceptionD(const char *message)
            : MyException(message) {
        cout << "MyExceptionD ..." << endl;
        LOGE("MyExceptionD ...");
    }

    MyExceptionD(const MyExceptionD &other)
            : MyException(other) {
        cout << "Copy MyExceptionD ..." << endl;
        LOGE("Copy MyExceptionD ...");
    }

    ~MyExceptionD() {
        cout << "~MyExceptionD ..." << endl;
        LOGE("~MyExceptionD ...");
    }
};

void fun(int n) throw(int, MyException, MyExceptionD) {
    if (n == 1) {
        throw 1;
    } else if (n == 2) {
        throw MyException("test Exception");
    } else if (n == 3) {
        throw MyExceptionD("test ExceptionD");
    }

}

template<typename T>
  T  &Max(T  &a, T  &b) {
    return a < b ? b : a;
}


extern "C"
JNIEXPORT jint JNICALL
Java_com_adasplus_update_c_MainActivity_text(JNIEnv *env, jobject instance) {

    LOGE("hello world");

    int x = 50;
    int y = 0;
    double z = 0;

    try {
        z = division(x, y);

    } catch (const char *msg) {
        LOGE("%s", msg);
    }

//    try
//    {
//        throw MyException();
//    }
//    catch(MyException& e)
//    {
//        LOGE("MyException caught");
//        LOGE("MyException caught %s",e.what());
//
//    }
//    catch(std::exception& e)
//    {
//        LOGE("exception caught");
//    }

    try {
        fun(1);
    }

    catch (int n) {
        LOGE("catch int ...");
        LOGE("catch int ... %d", n);


    }
    catch (MyExceptionD &e) {
        LOGE("catch MyExceptionD ...");
        LOGE("catch int ... %s", e.what());

    }
    catch (MyException &e) {
        LOGE("catch MyException ...aaaa");
        LOGE("catch int ... %s", e.what());
    }

    int **array = NULL;

    int m = 5;
    int n = 10;
    array = new int *[m];
    for (int i = 0; i < m; i++) {
        array[i] = new int[n];
        for (int j = 0; j < n; j++) {
            array[i][j] = i * j + 5;
            //   LOGE("catch MyException ...%d,%d %d",i,j , array[i][j]);
        }
    }


    for (int i = 0; i < m; i++) {

        for (int j = 0; j < n; j++) {

            LOGE("catch MyException ...%d,%d %d", i, j, array[i][j]);
        }
    }


    for (int i = 0; i < m; i++) {
        delete[] array[i];
    }
    delete[] array;


    int i = 39;
    int j = 20;
    LOGE("%d", Max(i, j));

    double f1 = 13.5;
    double f2 = 20.7;
    LOGE("%f", Max(f1, f2));

    string s1 = "Hello";
    string s2 = "World";
    string s3 = Max(s1, s2);
    LOGE("%s", s3.c_str());


    return 0;
}

