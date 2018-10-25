package com.adasplus.proadas.socket.tcp;

import android.util.Log;

import com.adasplus.proadas.App;
import com.adasplus.proadas.common.Constant;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.entry.AdasConfig;
import com.adasplus.proadas.common.model.BaseModel;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * Created by zhangyapeng on 18-5-2.
 */

public class UdpClient {

    private static final UdpClient ourInstance = new UdpClient();
    private static BaseModel.Result sHandler;
    private static DatagramSocket mDataGramSocket;
    public static UdpClient getInstance(final BaseModel.Result result) {
        sHandler = result;
        if (mDataGramSocket == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mDataGramSocket = new DatagramSocket(null);
                        mDataGramSocket.setReuseAddress(true);
                        mDataGramSocket.bind(new InetSocketAddress(Constants.DEFAULT_UDP_PORT));
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                receiveMsg();
                            }
                        }).start();
                        result.success(null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }

        return ourInstance;
    }

    private UdpClient() {}

    public static void receiveMsg() {
        byte[] receiveData = new byte[1030];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        while (true) {
            try {
                mDataGramSocket.receive(receivePacket);
                if (sHandler != null) {
                    sHandler.success(receiveData);
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void sendMsg(final byte[] bytes) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getByName("192.168.43.1");
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    sHandler.failure("a");
                    closeUdp();
                    return;
                }
                DatagramPacket sendPacket =
                        new DatagramPacket(bytes, bytes.length, inetAddress, Constants.DEFAULT_UDP_PORT);
                try {
                    mDataGramSocket.send(sendPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                    sHandler.failure("a");
                    closeUdp();
                }
            }
        }).start();
    }

    public void closeUdp() {
        try {
            InetAddress inetAddress = null;
            try {
                inetAddress = InetAddress.getByName("192.168.43.1");
            } catch (UnknownHostException e) {
                e.printStackTrace();
                return;
            }
            byte[] bytes = Constant.CLOSE.getBytes("UTF-8");
            DatagramPacket sendPacket =
                    new DatagramPacket(bytes, bytes.length, inetAddress, Constants.DEFAULT_UDP_PORT);
            try {
                mDataGramSocket.send(sendPacket);
            } catch (IOException e) {
                 e.printStackTrace();
            }
            if (mDataGramSocket != null) {
                mDataGramSocket.close();
                mDataGramSocket = null;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
