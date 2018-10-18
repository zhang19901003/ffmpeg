//
// Created by zhangyapeng on 18-10-18.
//

#ifndef C_PROJECT_FFDECODER_H
#define C_PROJECT_FFDECODER_H



#include "IDecode.h"
struct AVCodecContext;
class FFdecoder : public IDecode{
public:
    virtual bool Open(XParameter para);
protected:
    AVCodecContext *codec = 0;
};


#endif //C_PROJECT_FFDECODER_H
