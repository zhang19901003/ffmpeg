package com.adasplus.proadas.common;

import android.util.Log;
import java.util.concurrent.ArrayBlockingQueue;

public class Constant {


    public static final String MSG_REQUEST_CALIBRATE = "MSG_REQUEST_CALIBRATE";
    public static final String MSG_DEVICES_REQUEST ="MSG_DEVICES_REQUEST";
    public static final String MSG_REQUEST_WARNING="MSG_REQUEST_WARNING";
    public static final String MSG_REQUEST_COMMON_INFO = "MSG_REQUEST_COMMON_INFO";
    public static final String MSG_REQUEST_DEVICES_SPACE = "MSG_REQUEST_DEVICES_SPACE";
    public static final String MSG_REQUEST_SIM_INFO = "MSG_REQUEST_SIM_INFO";
    public static final String MSG_UPDATE_SUCCESS = "MSG_UPDATE_SUCCESS";
    public static final String MSG_UPDATE_FAILURE = "MSG_UPDATE_FAILURE";
    public static final String MSG_REQUEST_RESET_CALIBRATE = "MSG_REQUEST_RESET_CALIBRATE";
    public static final String MSG_REQUEST_VOICE_INFO = "MSG_REQUEST_VOICE_INFO";
    public static final String MSG_REQUEST_FAILURE_DISCONNECT = "MSG_REQUEST_FAILURE_DISCONNECT";
    public static final String MSG_REQUEST_DEVICES_RUNING = "MSG_REQUEST_DEVICES_RUNING";
    public static final String MSG_REQUEST_DEVICES_RUNING_FAILURE = "MSG_REQUEST_DEVICES_RUNING_FAILURE";
    public static final String ONLY_ONE = "only_one";
    public static final String OK = "ok";
    public static final String OPEN = "open";
    public static final String CLOSE = "close";

    public static void putBackYUVData(byte[] buffer) {
        Log.e("Adas","add"+  YUVQueue.size()+"   ....");
        if (YUVQueue.size() >= 10) {
            YUVQueue.poll();
        }
        YUVQueue.add(buffer);
    }
    private static final int YUVQUEUESIZE = 10;
    public static ArrayBlockingQueue<byte[]> YUVQueue = new ArrayBlockingQueue<byte[]>(YUVQUEUESIZE);
}

