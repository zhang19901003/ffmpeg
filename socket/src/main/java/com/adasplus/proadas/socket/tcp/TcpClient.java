package com.adasplus.proadas.socket.tcp;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.adasplus.proadas.App;
import com.adasplus.proadas.common.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


/**
 * Created by zhangyapeng on 17-12-26.
 */

public class TcpClient {
    private static final TcpClient ourInstance = new TcpClient();
    private static IPriviewModel.Result sHandler;
    private static byte[] buffer = new byte[10240];
    private static boolean flag = true;
    private static InputStream mReciver;
    private static Socket mSocket;
    private static OutputStream mSend;
    private static final byte msendByte[] = {0x4B, 0x59, 0x4B, 0x4A, 0x00, 0x00, 0x57};
    private static HandlerThread mSendThread;
    private static Handler mSendHandler;
    private static int DURTION = 5000;

    public static TcpClient
    getInstance(final IPriviewModel.Result result) {
        sHandler = result;
        if (sHandler == null) {
            return ourInstance;
        }
        if (mSocket == null) {
            mSendThread = new HandlerThread("send");
            mSendThread.start();
            mSendHandler = new Handler(mSendThread.getLooper());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mSocket = new Socket(Constants.IP, Constants.DEFAULT_SERVER_PORT);
                        mSocket.setReceiveBufferSize(100 * 4096);
                        mSocket.sendUrgentData(0xFF);
                        mReciver = mSocket.getInputStream();
                        mSend = mSocket.getOutputStream();
                        mSendHandler.post(mSendRunable);
                        result.success(null, 0);
                        Log.e("TAG", "connect success");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                receiveMsg();
                            }
                        }).start();

                    } catch (Exception e) {
                        relese();
                        if (result != null) {
                            result.failure("请求失败");
                        }
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        return ourInstance;
    }


    private static Runnable mSendRunable = new Runnable() {
        @Override
        public void run() {
            try {
                mSend.write(msendByte);
                mSend.flush();
                mSendHandler.postDelayed(this, DURTION);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private TcpClient() {
    }

    public static void receiveMsg() {

        while (flag) {
            int ret = 0;
            try {
                ret = mReciver.read(buffer);
                if (ret == -1) {
                    relese();
                    if (sHandler != null)
                        sHandler.failure("连接断开");
                    break;
                } else {

                    if (sHandler != null) {
                        Log.e("TAG", "read  " + ret);
                        sHandler.success(buffer, ret);
                    }
                }
            } catch (Exception e) {
                break;
            }
        }
    }

    public static void relese() {
        if (mSocket != null) {
            try {
                mReciver.close();
                mSend.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!mSocket.isClosed()) {
                try {
                    mSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mSocket = null;
            mReciver = null;
            mSend = null;
            if (mSendHandler != null) {
                mSendThread.quit();
                mSendThread.quitSafely();
                mSendThread = null;
            }

            if (mSendHandler != null) {
                mSendHandler.removeCallbacks(mSendRunable);
                mSendHandler = null;
            }
        }
    }
}
