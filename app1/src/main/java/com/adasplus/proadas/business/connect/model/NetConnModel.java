package com.adasplus.proadas.business.connect.model;

import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.adasplus.proadas.App;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.entry.WifiMessage;
import com.adasplus.proadas.common.reciver.WifiBroadcaseReceiver;
import com.adasplus.proadas.common.util.wifi.WifiManage;

import java.util.concurrent.ExecutorService;

/**
 * Created by zhangyapeng on 18-3-26.
 */

public class NetConnModel implements IConnModelImpl {

    private WifiManage mWifiManager;
    private String mSsid;
    private Result mWifiConnectResult;
    private Handler mMainHandler;

    @Override
    public void loadResourse(Result result) {
        registerWifiReceiver();
        mMainHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void connect(final WifiMessage wifiInfo, String passWord, final Result result) throws InterruptedException {
        Constants.sCurrentSSID = wifiInfo.getSsid();
        Constants.sRIGHT_CONNECT = false;
        mMainHandler.removeCallbacksAndMessages(null);
        this.mWifiConnectResult = result;
        mWifiManager = WifiManage.getInstance(App.getContext());
        mWifiManager.disconnectWifi();
        boolean isClear = mWifiManager.clearWifiConfig(wifiInfo.getSsid());
        if(passWord.length() == 4){
            try {
                int i = Integer.parseInt(passWord);

            }catch (Exception e){
                mWifiConnectResult.failure(null);
                return;
            }
            boolean b = mWifiManager.connectWifi(wifiInfo, "keytech"+passWord, isClear);
            if (!b) {
                mWifiConnectResult.failure(null);
                return;
            }
        }else if(passWord.length() == 11){
            boolean b = mWifiManager.connectWifi(wifiInfo, passWord, isClear);
            if (!b) {
                mWifiConnectResult.failure(null);
                return;
            }
        }else {
            mWifiConnectResult.failure(null);
            return;
        }

        mMainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!Constants.sRIGHT_CONNECT) {
                    Constants.sCurrentSSID = null;
                    mWifiManager = WifiManage.getInstance(App.getContext());
                    mWifiManager.disconnectWifi();
                    mWifiManager.clearWifiConfig(wifiInfo.getSsid());
                    mWifiConnectResult.failure(null);

                }
            }
        }, Constants.CONNECT_TIME);
    }

    @Override
    public void disConnect() {
//        mWifiManager = WifiManage.getInstance(App.getContext());
//        mWifiManager.disconnectWifi();
//        mWifiManager.clearWifiConfig();
        if (mWifiBroadcaseReceiver != null) {
            App.getContext().unregisterReceiver(mWifiBroadcaseReceiver);
            mWifiBroadcaseReceiver = null;
        }
        if (mMainHandler != null) {
            mMainHandler.removeCallbacksAndMessages(null);
        }

    }


    private void registerWifiReceiver() {
        IntentFilter wifiFilter = new IntentFilter();
        //  wifiFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        //  wifiFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        wifiFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        wifiFilter.setPriority(10006);
        App.getContext().registerReceiver(mWifiBroadcaseReceiver, wifiFilter);
    }

    private WifiBroadcaseReceiver mWifiBroadcaseReceiver = new WifiBroadcaseReceiver() {

        @Override
        public void onWifiConnected() {

            if (mWifiManager == null) {
                mWifiManager = WifiManage.getInstance(App.getContext());
            }
            if (Constants.sCurrentSSID == null)
                return;
            if (mWifiManager.isCurrentWifiIsAdas(Constants.sCurrentSSID)) {
                if (mWifiConnectResult != null) {
                    if (mMainHandler != null) {
                        mMainHandler.removeCallbacksAndMessages(null);
                    }
                    mWifiConnectResult.success(mWifiManager.getIpAddressFromHotspot());
                    Constants.sRIGHT_CONNECT = true;
                }
            }
        }

        @Override
        public void onWifiDisconnected() {

        }

    };
}
