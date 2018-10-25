package com.adasplus.proadas.socket.tcp;

import android.util.Log;

import com.adasplus.proadas.common.Constants;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by zhangyapeng on 18-5-22.
 */

class NClient {
    private static final NClient ourInstance = new NClient();
    private static IPriviewModel.Result sHandler;
    private static Selector mSelector = null;
    private static SocketChannel mSocketChannel = null;
    static ByteBuffer buff = ByteBuffer.allocate(1024);

    public static NClient getInstance(final IPriviewModel.Result result) {
        sHandler = result;
        if (sHandler == null) {
            return ourInstance;
        }
        if (mSocketChannel == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mSelector = Selector.open();
                        InetSocketAddress isa = new InetSocketAddress(Constants.IP, Constants.DEFAULT_SERVER_PORT);
                        mSocketChannel = SocketChannel.open(isa);
                        mSocketChannel.configureBlocking(false);
                        mSocketChannel.register(mSelector, SelectionKey.OP_READ);
                        new ClientThread().start();
                    } catch (IOException e) {
                        e.printStackTrace();
                        relese();
                        if (result != null) {
                            result.failure("请求失败");
                        }
                    }
                }
            }).start();
        }

        return ourInstance;
    }

    private static class ClientThread extends Thread {
        public void run() {

            while (true) {
                try {
                    if (mSelector.select() > 0) {
                        for (SelectionKey sk : mSelector.selectedKeys()) {
                            Log.e("socket", "ok ....");
                            mSelector.selectedKeys().remove(sk);

                            if (sk.isReadable()) {
                                mSocketChannel = (SocketChannel) sk.channel();
                                int ret = mSocketChannel.read(buff);
                                if (ret > 0) {
                                    buff.flip();
                                    if (sHandler != null) {
                                        byte[] array = buff.array();
                                        sHandler.success(array, ret);
                                    }
                                    sk.interestOps(SelectionKey.OP_READ);
                                } else {
                                    if (mSocketChannel != null) {
                                        mSocketChannel.close();
                                        mSocketChannel = null;
                                    }
                                    sk.cancel();
                                    if (mSelector != null) {
                                        mSelector.close();
                                        mSelector = null;
                                    }

                                    if (sHandler != null)
                                        sHandler.failure("连接断开");

                                    break;
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    private NClient() {
    }

    public static void relese() {
        if (mSocketChannel != null) {
            try {
                mSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mSocketChannel = null;
        }
        if (mSelector != null) {
            try {
                mSelector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mSelector = null;
        }
    }
}
