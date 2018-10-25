package com.adasplus.proadas.business.connect.view;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.Bundle;

import com.adasplus.proadas.common.view.BaseView;

import java.util.List;

/**
 * Created by zhangyapeng on 18-3-26.
 */

public interface ICheckResultCallback extends BaseView {
    void imageCallBack(List<ScanResult> list);
    void currentConnectWifi(WifiInfo wifiInfo);
    void showDialog();
    void showGpsInfo();
}