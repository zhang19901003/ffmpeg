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
    int re = avformat_open_input(&(this->ic),url,0,0);
    if(re!=0){
        char buf[1000];
        av_strerror(re,buf,sizeof(buf));
        LOGE("open failed %s",buf);
        return false;
    }
    LOGE("open file success");
    //flv h264 格式获取不到时长 nb_stream（没有头部信息） 用这个方法去读取一段数据 做探测
    // 但是此方法不能保证所有格式都能获取时长，只能通过遍历帧数获取时长
    re = avformat_find_stream_info(ic, 0);
    if (re != 0) {
        LOGE("%s", "avformat_find_stream_info failure");
    }

    this->totalMs = ic->duration/(AV_TIME_BASE/1000);
    LOGE("ffmpeg total = %d",totalMs);

    return true;
}


XData FFDemux::Read() {
    XData d;
    if(!ic) return XData();
    AVPacket *pkt =av_packet_alloc();
    int re =av_read_frame(ic,pkt);
    if(re!=0){
        av_packet_free(&pkt);
        return XData();
    }
    LOGE("pack size is %d , pts = %lld",pkt->size,pkt->pts);
    d.data= (unsigned char *) pkt;
    d.size = pkt->size;
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
XParameter FFDemux::GetPara() {
    if (!ic) {
        LOGE("GetPara failed ");
        return XParameter();
    }
    //获取视频流的索引
    int re = av_find_best_stream(ic, AVMEDIA_TYPE_VIDEO, -1, -1, 0, 0);
    if(re<0){
        LOGE("av_find_best_stream failed ");
        return XParameter();
    }
    XParameter xParameter;
    xParameter.para=ic->streams[re]->codecpar;
    return xParameter;

};



