//
// Created by zhangyapeng on 18-10-16.
//

#ifndef C_PROJECT_IDEMUX_H
#define C_PROJECT_IDEMUX_H


#include "XData.h"
#include "XThread.h"

class IDemux : public XThread{
public:
    virtual bool Open(const char* url) = 0;
    virtual XData Read() = 0;
    int totalMs = 0;
    virtual void Main();
};


#endif //C_PROJECT_IDEMUX_H
