//
// Created by zhangyapeng on 18-11-12.
//

#ifndef C_PROJECT_MONKEY_H
#define C_PROJECT_MONKEY_H


#include "XLog.h"

class Monkey {
public:
    Monkey(){}
    virtual ~Monkey(){LOGE("Monkey  ~Monkey");}
    virtual Monkey* Clone() = 0;  // 克隆
    virtual void Play() = 0;  // 玩耍
};


#endif //C_PROJECT_MONKEY_H
