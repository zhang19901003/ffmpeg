package com.adasplus.proadas.business.main.model.fragment.devices;

import com.adasplus.proadas.App;
import com.adasplus.proadas.common.Constant;
import com.adasplus.proadas.common.data.AdasRunningInfo;
import com.adasplus.proadas.common.data.SdcardInfo;
import com.adasplus.proadas.common.data.WarnParams;
import com.adasplus.proadas.common.util.net.AdasNet;
import com.adasplus.proadas.socket.tcp.TcpClient;
import com.adasplus.proadas.common.model.BaseModel;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by zhangyapeng on 18-4-27.
 */

public class DevicesSettingModel implements IDevicesSettingModel{
    @Override
    public void loadResourse(BaseModel.Result result) {

    }

    @Override
    public void request(final Result result) {
        result.update();
        AdasNet.getInstance(App.getContext()).requestAdasRunning(new BaseModel.Result<String>() {
            @Override
            public void success(String resultCallBack) {
                Gson gson = new Gson();
                try {
                    AdasRunningInfo deviceState = gson.fromJson(resultCallBack, AdasRunningInfo.class);
                    if(deviceState ==null || deviceState.getRunning().equals("0")){
                        result.failue(App.getContext().getResources().getString(com.adasplus.proadas.common.R.string.adas_stop));
                    }else {
                        result.success();
                    }

                }catch (Exception e){
                    result.failue(App.getContext().getResources().getString(com.adasplus.proadas.common.R.string.quest_failue));
                }
            }

            @Override
            public void failure(String s) {
                result.failue(s);
            }
        });
    }
}
