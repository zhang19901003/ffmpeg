//
// Created by zhangyapeng on 18-10-16.
//

#ifndef C_PROJECT_FFDEMUX_H
#define C_PROJECT_FFDEMUX_H

#include "IDemux.h"
struct AVFormatContext;
class FFDemux : public IDemux {

public:
    virtual bool Open(const char *url);
    virtual XData Read();
    FFDemux();

private:
    AVFormatContext *ic = 0;
};
#endif //C_PROJECT_FFDEMUX_H
