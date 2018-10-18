//
// Created by zhangyapeng on 18-10-18.
//

#ifndef C_PROJECT_IDECODE_H
#define C_PROJECT_IDECODE_H

#include "XParameter.h"
#include "IObserver.h"
//解码井口 支持硬解码  后期加上
class IDecode :public IObserver{
public:
    virtual bool Open(XParameter para) = 0;
};


#endif //C_PROJECT_IDECODE_H
