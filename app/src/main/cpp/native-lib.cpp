#include <jni.h>
#include <string>
#include "iostream"
#include <stdio.h>
#include <iostream>
#include <strstream>
#include <string.h>
#include <stdlib.h>
#include "android/log.h"
using namespace std;

#define LOG_TAG "native-dev"#define LOGI(...)
#define TAG"Adas"
#define LOGD(...)__android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__)//定义LOGD类型
#define LOGI(...)__android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)//定义LOGI类型
#define LOGW(...)__android_log_print(ANDROID_LOG_WARN,TAG,__VA_ARGS__)//定义LOGW类型
#define LOGE(...)__android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__)//定义LOGE类型
#define LOGF(...)__android_log_print(ANDROID_LOG_FATAL,TAG,__VA_ARGS__)//定义LOGF类型


extern "C"
JNIEXPORT jstring JNICALL
Java_com_adasplus_update_c_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    string hello = "Hello from C++aa";
    typedef struct Student {
        int age;
        string name;

        int age1;

    } Student;
//    char *name = "helloworld";
//  //  LOGE("%d",strlen(hello.c_str()));
//    char *otherName = "aaaa";
//
//    LOGE("%d",sizeof(name));
//    LOGE("%d",sizeof(otherName));
//    LOGE("%d", sizeof(Student));
//
//    Student *s = (Student *) malloc(sizeof(Student));
//    LOGE("%d", sizeof(s));
//    LOGE("%d", sizeof(int)*10);
//
//    int *p1 = (int*)malloc(sizeof(int)*2);
//    int *p2 = (int*)malloc(sizeof(int)*2);
//    LOGE("p1%p ",  p1);
//    LOGE("p2%p ",  p2);
//
//    char *p3 = (char *)malloc(4);
//    char *p4 = (char *)malloc(sizeof(char)*4);
//    LOGE("p3%p ",  p3);
//    LOGE("p4%p ",  p4);

    int a11;

    int c11[] = {1,2,3,4,5};
    LOGE("a11 point%p ", &a11);

    LOGE("b11 point %p ",c11);


//    char d[] = {'x', 'y', 'z', 'q', 'g', 'f', 'x', 'y', 'z', 'q', 'g', 'f', 'x', 'y', 'z', 'q', 'g',
//                'f'};
//    char c[] = {'a'};
//    LOGE("d point%p ", d);
//    LOGE("c point %p ", c);











    //   strcpy(name,otherName);
//    Student *s = new Student;
//    s->age = 10;
//    s->name = "asd";
//    strstream ss;
//    string s1;
//    ss << 10;
//    ss >> s1;
//    cout << s << endl;
//    cout << s->name << s->name << endl;
    //   hello += name;


    return env->NewStringUTF(hello.c_str());
}
