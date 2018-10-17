//
// Created by zhangyapeng on 18-10-16.
//

#include "FFDemux.h"
#include "XLog.h"

extern "C" {
#include <libavformat/avformat.h>
}


bool FFDemux::Open(const char *url) {

    LOGW("open url %s", url);
    int re = avformat_open_input(&ic,url,0,0);
    if(re!=0){
        char buf[1000];
        av_strerror(re,buf,sizeof(buf));
        LOGE("open failed %s",buf);
        return false;
    }

    LOGE("open file success");

    return true;
}


XData FFDemux::Read() {
    XData d;
    return d;
}

FFDemux::FFDemux() {
    static bool isFrist = true;
    if (isFrist) {
        isFrist = false;
        av_register_all();
        avcodec_register_all();
        avformat_network_init();
    }
}