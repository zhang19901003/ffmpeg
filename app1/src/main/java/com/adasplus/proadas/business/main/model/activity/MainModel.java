package com.adasplus.proadas.business.main.model.activity;

import com.adasplus.proadas.App;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.util.preference.Prefs;
import com.adasplus.proadas.common.util.wifi.WifiManage;

/**
 * Created by zhangyapeng on 18-4-3.
 */

public class MainModel implements IMainModel {
    @Override
    public void loadResourse(Result result) {
        boolean wifiEnabled = WifiManage.getInstance(App.getContext()).isWifiEnabled();
        if(wifiEnabled){
            if(WifiManage.getInstance(App.getContext()).isCurrentWifiIsAdas(Constants.ADASPLUS)){
                result.success(null);
            }else {
                result.failure(null);
            }
        }else {
            result.failure(null);
        }
    }
    public boolean isFrist() {
        return Prefs.with(App.getContext()).readBoolean(Constants.INIT, true);
    }

    public void setInit() {
        Prefs.with(App.getContext()).writeBoolean(Constants.INIT, false);
    }
}
