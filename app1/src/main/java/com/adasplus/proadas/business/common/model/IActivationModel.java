package com.adasplus.proadas.business.common.model;

import android.net.wifi.WifiInfo;

import com.adasplus.proadas.common.data.Activation;
import com.adasplus.proadas.common.model.BaseModel;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public interface IActivationModel extends BaseModel {

    void register (String bussion,String carNumber,String phoneNumber,Result result);
    interface Result <T> {
        void brfore(String t);
        void register();
        void success(T activation);
        void failue(String s);
    }
}
