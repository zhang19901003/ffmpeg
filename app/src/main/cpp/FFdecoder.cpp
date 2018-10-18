//
// Created by zhangyapeng on 18-10-18.
//
extern "C"{
#include <libavcodec/avcodec.h>
}
#include "FFdecoder.h"
#include "XLog.h"

bool FFdecoder:: Open(XParameter para){
    if(!para.para)
        return false;
    AVCodecParameters *p =para.para;
    AVCodec *cd = avcodec_find_decoder(para.para->codec_id);
    if(!cd){
        LOGE("avcodec_find_decoder failed");
        return false;
    }
    LOGE("avcodec_find_decoder success");
    //创建解码器上下文 并复制参数
    codec = avcodec_alloc_context3(cd);
    avcodec_parameters_to_context(codec,p);
    //打开解码器
    int re =avcodec_open2(codec,0,0);
    if(re!=0){
        char buf[1024] = {0};
        LOGE("avcodec_find_decoder failed");
        av_strerror(re,buf, sizeof(buf)-1);
        return false;
    }
    LOGE("avcodec_open2 success");
    return true;
}