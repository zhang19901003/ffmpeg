//
// Created by zhangyapeng on 18-10-17.
//

#include "XThread.h"
#include <thread>
using namespace std;

void XThread::Start() {
    thread th(&XThread::ThreadMain,this);
    th.detach();
}
void XThread::Stop() {

}

void XThread::ThreadMain() {
    // thread start
    Main();
    // thread exit
}

