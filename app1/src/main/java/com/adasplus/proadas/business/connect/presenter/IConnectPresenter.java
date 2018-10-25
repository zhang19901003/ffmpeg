package com.adasplus.proadas.business.connect.presenter;

import android.net.wifi.WifiInfo;

import com.adasplus.proadas.common.entry.WifiMessage;

/**
 * Created by zhangyapeng on 18-3-27.
 */

public interface IConnectPresenter {

    void connect(WifiMessage wifiInfo, String passWord);

    void disConnect();
}
