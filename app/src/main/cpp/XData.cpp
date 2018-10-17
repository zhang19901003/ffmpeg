//
// Created by zhangyapeng on 18-10-16.
//

#include "XData.h"
extern  "C" {
#include <libavformat/avformat.h>
}

void XData::Drop() {

    if(data)return;
    av_packet_free((AVPacket **) data);
    size = 0;
    data = 0;

}