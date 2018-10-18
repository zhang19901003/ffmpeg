//
// Created by zhangyapeng on 18-10-16.
//

#include "IDemux.h"
#include "XLog.h"

void IDemux ::Main() {
    while (!isExit) {
        XData xData= Read();
        LOGE("XDATA SIZE  %d",xData.size);
        if(xData.size>0){
            Notify(xData);
        } else{
            break;
        }
    }
}