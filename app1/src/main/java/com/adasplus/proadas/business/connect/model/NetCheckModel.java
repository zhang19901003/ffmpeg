package com.adasplus.proadas.business.connect.model;

import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.adasplus.proadas.App;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.model.BaseModel;
import com.adasplus.proadas.common.util.gps.AdasGps;
import com.adasplus.proadas.common.util.wifi.WifiManage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyapeng on 18-3-26.
 */

public class NetCheckModel implements ICheckModelImpl {

    private ArrayList<ScanResult> mList;
    private Handler mMainHandler;
    private WifiManage mWifiManager;
    private NetCheckModel.mCheckRunable mCheckRunable;
    private AdasGps mAdasGps;

    @Override
    public void loadResourse(final Result resultCallBack) {
        mWifiManager = WifiManage.getInstance(App.getContext());
        // mWifiManager.disconnectWifi();
        mList = new ArrayList<>();
        mCheckRunable = new mCheckRunable(resultCallBack);
        mMainHandler = new Handler(Looper.getMainLooper());
        mMainHandler.postDelayed(mCheckRunable, 500);
    }

    @Override
    public void cancel() {
        if (mMainHandler != null) {
            mMainHandler.removeCallbacks(mCheckRunable);
            mMainHandler.removeCallbacksAndMessages(null);
        }
        if(mAdasGps != null){
            mAdasGps.release();
        }
    }

    @Override
    public void loadScanResult(int position, Result result) {
        Bundle bundle = new Bundle();
        ScanResult scanResult = (ScanResult) this.mList.get(position);
        bundle.putString("ssid", scanResult.SSID);
        bundle.putInt("type", scanResult.describeContents());
        bundle.putString("capabilities", scanResult.capabilities);
        bundle.putString("bssid", scanResult.BSSID);
        result.success(bundle);
    }

    @Override
    public void showSuccessDialog(BaseModel.Result result) {
        if (TextUtils.isEmpty(Constants.sCurrentSSID))
            return;
        WifiInfo currentWifiInfo = WifiManage.getInstance(App.getContext()).getCurrentWifiInfo();
        if (currentWifiInfo.getSSID().contains(Constants.sCurrentSSID)) {
            result.success(null);
        }

    }

    @Override
    public void showGpsInfo(final BaseModel.Result result) {
        mAdasGps = new AdasGps(App.getContext());
        mAdasGps.setGpsListener(new AdasGps.GpsListener() {
            @Override
            public void gpsCallBack(int flag) {
                if(flag==1){
                    result.failure("");
                }
            }
        });
    }


    @Override
    public void loadResourse(BaseModel.Result result) {

    }

    private class mCheckRunable implements Runnable  {

        public mCheckRunable(Result resultCallBack) {
            this.resultCallBack = resultCallBack;
        }

        private Result resultCallBack;

        @Override
        public void run() {

            mList.clear();
            WifiManage.getInstance(App.getContext()).startScan();
            List<ScanResult> scanResultList = WifiManage.getInstance(App.getContext()).getScanResults();

            if (scanResultList != null && resultCallBack != null) {

                for (ScanResult scanResult : scanResultList) {

                    if (scanResult.SSID.length() == 12) {
                        String substring = scanResult.SSID.substring(0, 8);
                        if (substring.equals(Constants.ADASPLUS)) {
                            Log.e("Adas",scanResult.SSID);
                            mList.add(scanResult);
                        }
                    }
                }
                if (mList.size() > 0) {
                    resultCallBack.success(mList);
                } else {
                    resultCallBack.success(null);
                }
            } else {
                Log.e("TAG","find null");
                resultCallBack.success(null);
            }
            if (resultCallBack != null) {
                WifiInfo currentWifiInfo = WifiManage.getInstance(App.getContext()).getCurrentWifiInfo();
                if (currentWifiInfo.getSSID().contains(Constants.ADASPLUS) && currentWifiInfo.getSupplicantState() != SupplicantState.DISCONNECTED) {
                    resultCallBack.connectList(currentWifiInfo);
                } else {
                    resultCallBack.connectList(null);
                }
            }
            mMainHandler.postDelayed(this, 3000);
        }
    };
}
