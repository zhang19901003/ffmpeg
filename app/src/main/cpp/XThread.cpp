//
// Created by zhangyapeng on 18-10-17.
//

#include "XThread.h"
#include "XLog.h"
#include <thread>

using namespace std;

void XThread::Start() {
    thread th(&XThread::ThreadMain, this);
    th.detach();
}

void XSleep(int mis) {
    chrono::milliseconds du(mis);
    this_thread::sleep_for(du);
}

void XThread::Stop() {

    isExit = true;
    for (int i = 0; i < 200; i++) {
        if (!isRuning) {
            LOGE("Stop 线程停止成功 ");
            return;
        }
        XSleep(1);
    }
    LOGE("Stop 线程停止失败 ");

}

void XThread::ThreadMain() {
    // thread start
    LOGE("线程函数进入");
    isRuning = true;
    Main();
    LOGE("线程函数退出");
    isRuning = false;
    // thread exit
}


