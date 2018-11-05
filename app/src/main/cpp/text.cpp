#include <jni.h>
#include <string>
#include <android/log.h>
#include <stdio.h>
#include <iostream>
#include <fstream>
#include <exception>

#define TAG "ffmpeg"
#define LOGD(...)__android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__)
#define LOGI(...)__android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)
#define LOGW(...)__android_log_print(ANDROID_LOG_WARN,TAG,__VA_ARGS__)
#define LOGE(...)__android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__)
#define LOGF(...)__android_log_print(ANDROID_LOG_FATAL,TAG,__VA_ARGS__)
#define SRCFILE "foreman_cif.yuv"
#define DSTFILE "out.rgb"

#include <thread>
#include <iostream>
#include <android/native_window.h>
#include <android/native_window_jni.h>
#include <media/NdkMediaCodec.h>
#include <GLES2/gl2.h>
#include <EGL/egl.h>
#include <iostream>
#include <vector>
#include <cstdlib>
#include <string>
#include <stdexcept>
#include <pthread.h>
#include "TextSig.h"
#include "ConcreteSubject.h"
#include "ConcreteObserver.h"

extern "C" {
#include <libavcodec/avcodec.h>
#include "libavformat/avformat.h"
#include <libavcodec/jni.h>
#include "libswresample/swresample.h"
#include "libswscale/swscale.h"
#include <unistd.h>

#include <unistd.h>
#include "stdio.h"
#include "errno.h"
}
#define NUM_THREADS 5
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


const double *f1(const double ar[], int n);

const double *f2(const double [], int);

const double *f3(const double *, int);

const double *f1(const double *ar, int n) {
    return ar;
}

const double *f2(const double ar[], int n) {

    return ar + 1;
}

const double *f3(const double ar[], int n) {

    return ar + 2;
}

int add(int a, int b) {
    return a + b;
}

int sub(int a, int b) {
    return a - b;
}

int mul(int a, int b) {
    return a * b;
}

int div1(int a, int b) {

    return a / b;
}


class Person {

public:
    virtual void Display() = 0;

    virtual void Display1() = 0;


};

class Student : public Person {

public:
    void Display() {

        LOGE("asasa %s", "aaaaaaaaaaaaaaaaaaaaaaaaa");
    }

    void Display1() {

        LOGE("asasa %s", "bbbbbbbbbbbbbbbbbbbbbbbbb");
    }
};


template<class T>

class Stack {
private:
    vector<T> elems;     // 元素

public:
    void push(T t);  // 入栈
    void pop();               // 出栈
    T top() const;            // 返回栈顶元素
    bool empty() const {
        // 如果为空则返回真。

        return elems.empty();
    }
};

template<class T>
void Stack<T>::push(T elem) {

    elems.push_back(elem);
}

template<class T>
void Stack<T>::pop() {
    if (elems.empty()) {
        throw out_of_range("Stack<>::pop(): empty stack");
    }
    // 删除最后一个元素
    elems.pop_back();
}

template<class T>
T Stack<T>::top() const {
    if (elems.empty()) {
        throw out_of_range("Stack<>::top(): empty stack");
    }
    // 返回最后一个元素的副本

    return elems.back();
}

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


void *say_hello(void *args) {

    //  LOGE("Hello Runoob！");
    return 0;
}

class MyExceptionD : public MyException {
public:
    MyExceptionD(const char *message)
            : MyException(message) {
        cout << "MyExceptionD ..." << endl;

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
T &Max(T &a, T &b) {
    return a < b ? b : a;
}

struct Data {
    int a = 0;
    char *x = 0;
};

class A {
    int a;

    virtual void asd() {

    }
};

class B {

public:


    static B *const get() {

        return b1;
    }

    void setValue() {
        number = 15;
    }


    ~B() {
        LOGE("xi gou hanshu");
    }

    B(const B &b) {
        LOGE("xkaobei  hanshu");
    }

    int number = 14;

    B *get1() {
        B b;
        return &b;
    }

    B *get2() {
        B b;
        return &b;
    }

private:
    B() {
        LOGE("gou zao han shu");

    }

    static B *const b1;
};

B *const B::b1 = new B;

int c = 100;

void TestRenfen(int *&a) {
    a = &c;
}


void getA(int a) {
    LOGE("getA***  %d", a);
}

void getB() {
    LOGE("getA***------------  %d");
}


void callback(int a)//回调函数
{
    LOGE("callback result =  %d", a);

}

int add1(int x, int y) {

    return x + y;
}

int dec(int x, int y) {

    return x - y;
}

int max(int x, int y) {

    return x > y ? x : y;
}

void Hello() {
    LOGE("hello world");
}


int Exec(void (*Fun)()) {
    Fun();
    return 0;
}

int Exec(int x, int y, int (*Fun)(int, int)) {
    return Fun(x, y);

}


void printWelcome(int len) {
    LOGE("printWelcome -- %d/n", len);
}


void printGoodbye(int len) {

    LOGE("printGoodbye -- %d/n", len);

}


void callback(int times, void (*print)(int)) {

    int i;

    for (i = 0; i < times; ++i) {

        print(i);

    }


}


class CBasic {
public:
    virtual int test()=0; // 一定要是 virtual
};

class CDerived : public CBasic {
public:
    virtual int test() { return 1; }
};


void showvector(vector<int> v) {
    for (vector<int>::iterator it = v.begin(); it != v.end(); it++) {
        LOGE("%d//////", *it);
    }

}


JNIEXPORT jint JNICALL
Java_com_adasplus_update_c_MainActivity_text1(JNIEnv *env, jobject instance) {
    LOGE("hello world    *******");
    LOGE("%s %d %s %s", __FILE__, __LINE__, __TIME__, __DATE__);

    Person *s = new Student;
    s->Display();
    typedef void (*Fun3)();
    Fun3 fun3 = &getB;
    fun3();


    typedef void (*Fun)();
    (Fun(*(int *) *(int *) s))();
    (Fun(*((int *) *(int *) s + 1)))();

    void (*Fun1[])(int) = {getA, getA};
    Fun1[0](10086);

    void (*(*Fun2)[2])(int);
    Fun2 = &Fun1;
    (*(*Fun2)[0])(1008611);


    int (*Fun4)(int, int);
    Fun4 = add1;
    LOGE("%d fun4", Fun4(3, 4));

    Exec(Hello);

    int x = Exec(3, 10086, add1);
    LOGE("%d", x);

    int (*str[3])(int, int) ={add1, dec, max};
    typedef int (*str1[3])(int, int);

    LOGE("%d", str[0](5, 10));
    LOGE("%d", str[1](5, 10));
    LOGE("%d", str[2](5, 10));

    str1 str11 = {add1, dec, max};

    LOGE("%d", str11[0](5, 10));
    LOGE("%d", str11[1](5, 10));
    LOGE("%d", str11[2](5, 10));
    //  callback(10, printWelcome);

//    callback(10, printGoodbye);
//
//    printWelcome(5);

    string s1 = "asasa ,"; //正确

    string s3 = s1 + "hello " + ",";

    LOGE("%d  ,%d,    %s", s3.size(), s3.length(), s3.c_str());


    for (int i = 0; i < s3.size(); i++) {
        cout << s3[i] << endl;
        s3[i] = 's';
    }

    LOGE("%s", s3.c_str());


    string str111("hi sysu");
    for (string::iterator it = str111.begin(); it != str111.end(); it++) {
        LOGE("%c", *it);

    }


    string sq("heoolo sdaa ss");

    LOGE("index %d", sq.find("aa", 0));
    LOGE("index %d", sq.find("aa1", 0));
    vector<int> v1(5);
    v1.resize(0);
    for (int i = 0; i < 20; i++) {
        v1.push_back(i);
    }

    for (int i = 0; i < v1.size(); i++) {
        LOGE("%d ******  %d", v1[i], i);
    }


    LOGE("index %d %d", sizeof(v1), v1.size());

    showvector(v1);

    char s111 = 's';
    int a11111 = static_cast<int>  (s111);

    LOGE("aaa1111 = %d", a11111);

    s111 = static_cast<char> (119);

    LOGE("aaa1111 = %c", s111);



//    CBasic  *pB1  =   CDerived();
//  //  CBasic * pB2  ;
//
//    //dynamic cast failed, so pD1 is null.
//    CDerived * pD1 = dynamic_cast<CDerived * > (&pB1);

//    //dynamic cast succeeded, so pD2 points to  CDerived object
//    CDerived * pD2 = dynamic_cast<CDerived * > (pB2);
//
//    //dynamci cast failed, so throw an exception.
//    CDerived & rD1 = dynamic_cast<CDerived &> (*pB1);
//
////dynamic cast succeeded, so rD2 references to CDerived object.
//    CDerived & rD2 = dynamic_cast<CDerived &> (*pB2);



    // TODO
    return 0;
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

//    double f1 = 13.5;
//    double f2 = 20.7;
//    LOGE("%f", Max(f1, f2));

    string s1 = "Hello";
    string s2 = "World";
    string s3 = Max(s1, s2);
    LOGE("%s", s3.c_str());


    try {
        Stack<int> intStack;  // int 类型的栈
        Stack<string> stringStack;    // string 类型的栈


        int a;
        intStack.push(a);

        LOGE("%d", intStack.top());
        stringStack.push("hello");
        string temp = stringStack.top();
        LOGE("%s", temp.c_str());
        stringStack.pop();
        stringStack.pop();
    }
    catch (exception const &ex) {


    }

    cout << "Value of __LINE__ : " << __LINE__ << endl;
    cout << "Value of __FILE__ : " << __FILE__ << endl;
    cout << "Value of __DATE__ : " << __DATE__ << endl;
    cout << "Value of __TIME__ : " << __TIME__ << endl;


    pthread_t tids[NUM_THREADS];

    for (int i = 0; i < NUM_THREADS; ++i) {

        int ret = pthread_create(&tids[i], NULL, say_hello, NULL);
        if (ret != 0) {

            LOGE("pthread_create error: error_code= %d", ret);
        } else {
            LOGE("pthread_create  success");
        }
    }

    int a[5][5];
    int(*p)[5];
    p = a;
    LOGW("%d,%p", &p[4][2] - &a[4][2], &p[4][2] - &a[4][2]);

    //把函数的地址存到一个数组中，那这个数组就叫函数指针数组，  parr1 先和 [] 结合，说明parr1是数组，数组的内容是什么呢？ 是 int (*)() 类型的函数指针。
    int (*parr1[10])();

    int (*pp[5])(int x, int y) = {0, add, sub, mul, div1};
    //指向函数指针数组的指针  指向函数指针数组的指针是一个 指针 ，指针指向一个 数组 ，数组的元素都是 函数指针
    void (*(*ppp)[5])(void);


    LOGE("%d", pp[1](10, 20));


    double av[3] = {1234.3, 6952.6, 9185.9};
    const double *(*p1)(const double *, int) = &f1;
    LOGE("%p *********** %.2f ", p1(av, 3), *p1(av, 3));
    LOGE("%p *********** %f ", (*p1)(av, 3), *(*p1)(av, 3));

    const double *(*pa[3])(const double *, int) = {&f1, &f2, &f3};
    for (int i = 0; i < 3; i++) {

        LOGE("%p *********** %f ", (*(pa + i))(av, 3), *(**(pa + i))(av, 3));
    }

    const double *(*(*pd)[3])(const double *, int);
    pd = &pa;
    LOGE("%p *********** %p **************%p ", (*(*pd)[0])(av, 3), (*(*pd)[1])(av, 3),
         (*(*pd)[2])(av, 3));
    LOGE("%f *********** %f **************%f ", *(((*((*pd) + 0))(av, 3))), *((*pd)[1](av, 3)),
         *(*(*pd)[2])(av, 3));

    LOGE("%p *********** %p  ", sub, &sub);

    LOGE("%s", "end of");
    LOGE("size of  %d", sizeof(A));


    Person *person = new Student();


    person->Display();
    TextSig *asdas = TextSig::Get();
    TextSig *asdad = TextSig::Get();
    LOGE("-      -------%p   %p", asdas, asdad);
    //   B::get()->number = 20;
//    LOGE("number  is  %d",  B::get()->number);

    B *const b3 = B::get();
    // b3 = NULL;
    //  delete (b3);

    LOGE("--------%p   %p", asdas, asdad);
    //   B b5 = *b3;
//    B *b4 = B::get();
//    B b6;
//    LOGE("****%p   %p  %p", b3, b4, &b5);
//    b3->number = 100;
//    b4->number = 200;
//    b5.number = 300;
//    b6.number = 400;
//
//    LOGE("hahahahahahah%d   %d  %d", b3->number, b4->number, b5.number);
//    LOGE("****%d   %d  %d ,%d", b3->number, b4->number, b5.number,b6.number);
//    LOGE("****%p   %p  %p", b3, b4, &b5);
//
//    B *basa = new B();
//    delete basa;
//
//    int n111 = 2;
//    int const *const pn = &n111;



//    TestRenfen(pn);
//    LOGE("****%d   ", *pn);
//    B b1(*basa);

    //caller(callback);
    return 0;

}

#include <arpa/inet.h>
#include <arpa/inet.h>
#include <sys/stat.h>

#define SERVER_PORT 8000
#define MAXLINE 4096

typedef char *(*PType)(char *a, const char *b);

#include <mutex>

extern "C"
JNIEXPORT jint JNICALL
Java_com_adasplus_update_c_MainActivity_text2(JNIEnv *env, jobject instance) {

    char a[10];
    PType p = strcpy;

    LOGE("Type   %s", p(a, "sasas"));
    short num = 0x1122;
    char *c;
    c = (char *) &num;
    LOGE("%d", *c);
    if (*c == 0x22)
        LOGE("this is little end");
    else
        LOGE("this is big end");


    LOGE("This is test2");
    struct sockaddr_in serveraddr, clientaddr;
    int sockfd, addrlen, confd, len, i;
    char ipstr[128];
    char buf[MAXLINE];

    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    LOGE("1111111111111   %d", sockfd);

    bzero(&serveraddr, sizeof(serveraddr));
    serveraddr.sin_family = AF_INET;
    serveraddr.sin_addr.s_addr = htonl(INADDR_ANY);
    serveraddr.sin_port = htons(SERVER_PORT);
//13363880500
    int code = bind(sockfd, (struct sockaddr *) &serveraddr, sizeof(serveraddr));
    LOGE("222222222   %d", code);

    int i88 = listen(sockfd, 128);
    LOGE("333333333333   %d", i88);
    LOGE("%s", "aaaaaaaaaaaaaaaaaa");
    while (1) {

        addrlen = sizeof(clientaddr);

        LOGE("%s", "bbbbbbbbbbbbbbb");
        char buf[20037];
        LOGE("size of %d", sizeof(buf) / sizeof(char));
        confd = accept(sockfd, (struct sockaddr *) &clientaddr, &addrlen);
        LOGE("%s   %d", "ccccccccccccccc", confd);
        inet_ntop(AF_INET, &clientaddr.sin_addr.s_addr, ipstr, sizeof(ipstr));

        int count = 100 ^5;

        LOGE("%s  ,%d  %d %d",
             inet_ntop(AF_INET, &clientaddr.sin_addr.s_addr, ipstr, sizeof(ipstr)),
             ntohs(clientaddr.sin_port));

        while (true) {
            len = read(confd, buf, sizeof(buf));
            LOGE("%s", buf);
            i = 0;
            while (i < len) {
                buf[i] = toupper(buf[i]);
                i++;
            }

            write(confd, buf, strlen(buf));
        }


        close(confd);
    }
    close(sockfd);

    return 0;
}

class Singleton {
public:
    static Singleton *GetInstance();

    int a;

    // GC 机制
    class GC {
    public:
        GC() {
            LOGE("Here oncreat the gc...");
        }

        ~GC() {
            // 可以在这里销毁所有的资源，例如：db 连接、文件句柄等
            if (m_pSingleton != NULL) {
                LOGE("Here destroy the m_pSingleton...");
                //  delete m_pSingleton;
                m_pSingleton = NULL;
            }
        }

        static GC gc;  // 用于释放单例
    };

    GC *aaa = NULL;

    ~Singleton() {
        if (aaa == NULL) {
            LOGE("~GC  null");
        }
        delete (aaa);
        LOGE("~Singleton  destory");
    }

private:
    Singleton() {
        aaa = new GC();
        LOGE("Singleton  oncreat");
    }  // 构造函数（被保护）



private:
    static Singleton *m_pSingleton;  // 指向单例对象的指针
    static mutex m_mutex;
    static int asd;

};

Singleton *Singleton::m_pSingleton = NULL;
mutex Singleton::m_mutex;

Singleton *Singleton::GetInstance() {
    if (m_pSingleton == NULL) {

        if (m_pSingleton == NULL) {
            m_pSingleton = new Singleton();
            LOGE("hello world");

        }

    }
    return m_pSingleton;
}


Singleton::GC  Singleton::GC::gc;


void texx() {
    Singleton *pSingleton1 = Singleton::GetInstance();
    delete (pSingleton1);
//    Singleton *pSingleton2 = Singleton::GetInstance();
//
//    LOGE ("%p,,,,,,,,%p",pSingleton1 ,pSingleton2);
    //  delete(pSingleton1);
}
#define SAFE_DELETE(p) { if(p){delete(p); (p)=NULL;} }
extern "C"
JNIEXPORT jint JNICALL
Java_com_adasplus_update_c_MainActivity_text4(JNIEnv *env, jobject instance) {

    LOGE("welecome  Test4");
//    Singleton single = Singleton::GetInstance();
//    LOGE("Singleton  copy  %p",&single);
//    Singleton singleton= single;
//    LOGE("Singleton  copy  %p",&singleton);
    //   texx();

    subject *pSubject = new ConcreteSubject();
    IObserver *pObserver1 = new ConcreteObserver("Jack Ma");
    IObserver *pObserver2 = new ConcreteObserver("Pony");

    pSubject->Attach(pObserver1);
    pSubject->Attach(pObserver2);

    ConcreteSubject * concreteSubject =  dynamic_cast<ConcreteSubject *> (pSubject);
    concreteSubject->SetPrice(12.5);


    pSubject->Notify();

    pSubject->Detach(pObserver2);

    concreteSubject->SetPrice(15);
    pSubject->Notify();
  //  SAFE_DELETE(pObserver1);


    return 10086;

}
