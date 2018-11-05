//
// Created by zhangyapeng on 18-11-2.
//

#ifndef C_PROJECT_IOBSERVER_H
#define C_PROJECT_IOBSERVER_H


#include "XLog.h"

class IObserver {
public:
    virtual void Update(float price) = 0;
    ~ Update(){
        LOGE("IObserver delete");
    };
};


#endif //C_PROJECT_IOBSERVER_H
