//
// Created by zhangyapeng on 18-10-16.
//

#include "IDemux.h"
#include "XLog.h"

void IDemux ::Main() {
    for (; ;) {
        XData xData= Read();
        LOGE("XDATA SIZE  %d",xData.size);
        if(xData.size == 0){
            break;
        }
    }
}