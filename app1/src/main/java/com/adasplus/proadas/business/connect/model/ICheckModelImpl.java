package com.adasplus.proadas.business.connect.model;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;

import com.adasplus.proadas.common.model.BaseModel;

/**
 * Created by zhangyapeng on 18-3-26.
 */

public interface ICheckModelImpl extends BaseModel {
    void loadResourse(Result resultCallBack);

    void cancel();

    void loadScanResult(int i, Result result);

    public interface Result <T>{
        void success(T resultCallBack);
        void failure();
        void connectList(WifiInfo scanResult);
    }

    void showSuccessDialog(BaseModel.Result result);

    void showGpsInfo(BaseModel.Result result);
}