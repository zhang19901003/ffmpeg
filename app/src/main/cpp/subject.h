//
// Created by zhangyapeng on 18-11-2.
//

#ifndef C_PROJECT_SUBJECT_H
#define C_PROJECT_SUBJECT_H

#include "XLog.h"

class IObserver;

class subject {
public:
    virtual void Attach(IObserver *) = 0;  // 注册观察者
    virtual void Detach(IObserver *) = 0;  // 注销观察者
    virtual void Notify() = 0;  // 通知观察者
    virtual ~subject(){LOGE("subject delete");};
};

#endif //C_PROJECT_SUBJECT_H
