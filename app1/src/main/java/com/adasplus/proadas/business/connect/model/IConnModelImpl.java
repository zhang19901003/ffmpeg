package com.adasplus.proadas.business.connect.model;

import android.os.Bundle;

import com.adasplus.proadas.common.entry.WifiMessage;
import com.adasplus.proadas.common.model.BaseModel;

/**
 * Created by zhangyapeng on 18-3-26.
 */

public interface IConnModelImpl extends BaseModel {
    void connect(WifiMessage wifiInfo, String str,Result result) throws InterruptedException;
    void disConnect();


}