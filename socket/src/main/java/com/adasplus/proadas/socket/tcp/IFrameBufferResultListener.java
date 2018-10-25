package com.adasplus.proadas.socket.tcp;


public interface IFrameBufferResultListener {
    void onFrame(int frameSize, int packetNum, int index, byte[] data, int size);
}