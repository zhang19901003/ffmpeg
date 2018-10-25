package com.adasplus.proadas.socket.tcp;


import android.util.Log;

public class ParseMachine {
    enum CHECK_STATE {
        CHECK_MAGIC1,
        CHECK_MAGIC2,
        CHECK_MAGIC3,
        CHECK_MAGIC4,
    }

    private byte[] mOneData;
    private CircleBuffer mCircleBuffer;
    private CHECK_STATE mState;
    private byte[] mFrameLen;
    private byte[] mDataLen;
    private int mIndex;
    private byte[] mRealFrame;
    private byte[] mBuffer;
    private byte[] mData;
    private int mFrameSize;
    private int mDataSize;
    private int mPacketNum;
    private boolean isFlag =true;

    public ParseMachine(CircleBuffer circleBuffer) {
        mOneData = new byte[1];
        mRealFrame = new byte[40960];
        mBuffer = new byte[10];
        mData = new byte[1024*10];
        mState = CHECK_STATE.CHECK_MAGIC1;
        mFrameLen = new byte[4];
        mDataLen = new byte[4];
        mCircleBuffer = circleBuffer;
    }

    public void startParse() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                boolean flag;
                Thread.currentThread().setPriority(3);
                while (isFlag) {
                    flag = true;
                    while (flag && isFlag) {
                        mCircleBuffer.readBuffer(mOneData);
                        switch (mState) {
                            case CHECK_MAGIC1:
                                if (mOneData[0] == 0x4B) {
                                    mState = CHECK_STATE.CHECK_MAGIC2;
                                }
                                break;
                            case CHECK_MAGIC2:
                                if (mOneData[0] == 0x59) {
                                    mState = CHECK_STATE.CHECK_MAGIC3;
                                }
                                break;
                            case CHECK_MAGIC3:
                                if (mOneData[0] == 0x4B) {
                                    mState = CHECK_STATE.CHECK_MAGIC4;
                                }
                                break;
                            case CHECK_MAGIC4:
                                if (mOneData[0] == 0x4A) {
                                    flag = false;
                                    mState = CHECK_STATE.CHECK_MAGIC1;
                                }
                                break;
                            default:
                                break;

                        }
                    }

                    if(!isFlag){
                        return;
                    }

                    while(isFlag &&mCircleBuffer.readBuffer2(mBuffer, 10) != 10){
                        try {
                            Thread.sleep( 20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.arraycopy(mBuffer, 0, mFrameLen, 0, 4);
                    mFrameSize = byte2int(mFrameLen);
                    System.arraycopy(mBuffer, 4, mOneData, 0, 1);
                    mPacketNum = mOneData[0];
                    System.arraycopy(mBuffer, 5, mOneData, 0, 1);
                    int index = mOneData[0];
                    if(index == 0){
                        mIndex = 0;
                    }
                    System.arraycopy(mBuffer, 6, mDataLen, 0, 4);
                    mDataSize = byte2int(mDataLen);
                    Log.e("Tcp",mFrameSize + "  "+mPacketNum+"   "+index+"    "+mDataSize);
                    while(isFlag &&mCircleBuffer.readBuffer2(mData, mDataSize) != mDataSize){
                        try {
                            Thread.sleep( 20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.arraycopy(mData, 0, mRealFrame, mIndex, mDataSize);
                    mIndex += mDataSize;
                    if (index + 1 == mPacketNum && mFrameBufferListener != null) {
                        mFrameBufferListener.onFrame(mFrameSize, mPacketNum, index, mRealFrame, mFrameSize);
                    }
                }
            }
        }.start();
    }

    private int byte2int(byte[] buffer) {
        return (buffer[3] & 0xff) << 24 | (buffer[2] & 0xff) << 16 | (buffer[1] & 0xff) << 8 | (buffer[0] & 0xff);
    }

    private IFrameBufferResultListener mFrameBufferListener;

    public void setFrameBufferListener(IFrameBufferResultListener listener) {
        mFrameBufferListener = listener;


    }

    public void setFlag(){
        isFlag =false;
    }
}
