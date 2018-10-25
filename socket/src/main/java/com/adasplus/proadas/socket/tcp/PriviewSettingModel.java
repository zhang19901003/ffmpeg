package com.adasplus.proadas.socket.tcp;


import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

import com.adasplus.proadas.App;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.model.BaseModel;
import com.adasplus.proadas.common.util.net.AdasNet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangyapeng on 18-4-10.
 */
//TODO  mCode sysn
public class PriviewSettingModel implements IPriviewModel {

    private MediaCodec mCodec;
    private CircleBuffer mCircleBuffer;
    private ParseMachine parseMachine;
    private DecodeThread mDecodeThread;
    private MediaCodec.BufferInfo mBufferInfo;
    private BaseModel.Result mResult;
    private Handler mSocketHandler;
    private HandlerThread mSocketHandlerThread;
    private StartRunable mStartRunable;
    private StartRunable mStartCameraFrontRunable;
    private StartRunable mStartCameraBackRunable;

    @Override
    public void loadResourse(final BaseModel.Result result) {

        mSocketHandlerThread = new HandlerThread("socket");
        mSocketHandlerThread.start();
        mSocketHandler = new Handler(mSocketHandlerThread.getLooper());
        mBufferInfo = new MediaCodec.BufferInfo();
        TcpClient.getInstance(new Result<byte[]>() {

            @Override
            public void success(byte[] resultCallBack, int ret) {
                if (resultCallBack == null && ret == 0)
                    result.success(null);
            }

            @Override
            public void failure(String s) {
                result.failure(s);
            }
        });
    }

    @Override
    public void priview(SurfaceHolder mHolder, final BaseModel.Result result, final String carmeId) {

        mSocketHandler.post(new Runnable() {
            @Override
            public void run() {
                AdasNet.getInstance(App.getContext()).stopPriview("0");
                mStartRunable = new StartRunable(carmeId);
                if(mSocketHandler != null)
                mSocketHandler.postDelayed(mStartRunable, 0);
            }
        });

        mCircleBuffer = new CircleBuffer(4096 * 1000);
        parseMachine = new ParseMachine(mCircleBuffer);
        parseMachine.setFrameBufferListener(new IFrameBufferResultListener() {
            @Override
            public void onFrame(int frameSize, int packetNum, int index, byte[] data, int size) {

                if (mCodec != null) {
                    try {
                        ByteBuffer[] inputBuffers = mCodec.getInputBuffers();
                        int inputBufferIndex = mCodec.dequeueInputBuffer(0);
                        if (inputBufferIndex >= 0) {
                            ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];
                            inputBuffer.clear();
                            inputBuffer.put(data, 0, size);
                            mCodec.queueInputBuffer(inputBufferIndex, 0, size, TimeUnit.MILLISECONDS.toMicros(SystemClock.elapsedRealtime()), 0);
                        }
                    } catch (Exception e) {

                    }
                }
            }
        });
        parseMachine.startParse();
        try {
            mCodec = MediaCodec.createDecoderByType("video/avc");
        } catch (IOException e) {
            e.printStackTrace();
        }
        final MediaFormat mediaformat = MediaFormat.createVideoFormat("video/avc", Constants.BACK_PREVIEW_WIDTH, Constants.BACK_PREVIEW_HEIGHT);
        mediaformat.setInteger(MediaFormat.KEY_FRAME_RATE, 15);
        mCodec.configure(mediaformat, mHolder.getSurface(), null, 0);
        mCodec.start();
        mDecodeThread = new DecodeThread(mCodec);
        mDecodeThread.start();
        TcpClient.getInstance(new Result<byte[]>() {
            @Override
            public void success(byte[] resultCallBack, int ret) {
                mCircleBuffer.writeBuffer(resultCallBack, ret);
            }

            @Override
            public void failure(String s) {
                result.failure(s);
                relese();
            }
        });
    }

    @Override
    public void relese() {

        if (mSocketHandler != null) {
            mSocketHandler.removeCallbacks(mStartRunable);
            mSocketHandler.removeCallbacks(mStartCameraFrontRunable);
            mSocketHandler.removeCallbacks(mStartCameraBackRunable);
            mSocketHandler.removeCallbacksAndMessages(null);
            mSocketHandler = null;
        }

        if (mSocketHandlerThread != null) {
            mSocketHandlerThread.quit();
            mSocketHandlerThread.quitSafely();
            mSocketHandlerThread = null;
        }

        if (mDecodeThread != null) {
            mDecodeThread.setDoneCoding(true);
        }

        if (parseMachine != null) {
            parseMachine.setFlag();
            parseMachine = null;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                AdasNet.getInstance(App.getContext()).stopPriview("1");
            }
        }).start();
        if (mCodec != null) {
            mCodec.stop();
            mCodec.release();
            mCodec = null;
        }
        mCircleBuffer = null;
    }

    @Override
    public void disconnectTcp() {
        TcpClient.getInstance(null).relese();
    }

    @Override
    public void selectCameraId(final int cameraId) {

        if(mSocketHandler == null)
            return;

        mSocketHandler.post(new Runnable() {
            @Override
            public void run() {
                switch (cameraId) {
                    case 0:

                        if(mSocketHandler != null){
                            AdasNet.getInstance(App.getContext()).stopPriview("1");
                            mStartCameraFrontRunable = new StartRunable("0");
                            if(mSocketHandler !=null)
                            mSocketHandler.postDelayed(mStartCameraFrontRunable, 0);
                        }
                        break;
                    case 1:
                        if(mSocketHandler != null){
                            AdasNet.getInstance(App.getContext()).stopPriview("0");
                            mStartCameraBackRunable = new StartRunable("1");
                            if(mSocketHandler !=null)
                            mSocketHandler.postDelayed(mStartCameraBackRunable, 0);
                        }
                        break;

                }
            }
        });

    }

    @Override
    public void initSuccess(BaseModel.Result result) {
        this.mResult = result;
    }

    private class DecodeThread extends Thread {
        private MediaCodec mCodec;
        private boolean doneCoding;

        public DecodeThread(MediaCodec codec) {
            mCodec = codec;
            doneCoding = false;
        }

        public void setDoneCoding(boolean doneCoding) {
            this.doneCoding = doneCoding;
        }

        @Override
        public void run() {
            super.run();
            while (!doneCoding) {
                try {
                    int outputBufferIndex = mCodec.dequeueOutputBuffer(mBufferInfo, 0);
                    if (outputBufferIndex >= 0) {
                        mCodec.releaseOutputBuffer(outputBufferIndex, true);
                    } else if (outputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                        if (mResult != null) {
                            mResult.success(null);
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    private class StartRunable implements Runnable {

        private String cameraId;

        public StartRunable(String cameraId) {
            this.cameraId = cameraId;
        }

        @Override
        public void run() {
            AdasNet.getInstance(App.getContext()).startPriview(cameraId);
        }
    };

}
