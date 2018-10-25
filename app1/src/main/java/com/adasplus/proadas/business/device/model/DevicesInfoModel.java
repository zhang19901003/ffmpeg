package com.adasplus.proadas.business.device.model;


import com.adasplus.proadas.App;
import com.adasplus.proadas.common.Constant;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.data.DeviceInfo;
import com.adasplus.proadas.common.data.VoiceSetting;
import com.adasplus.proadas.common.util.net.AdasNet;
import com.adasplus.proadas.socket.tcp.TcpClient;
import com.adasplus.proadas.common.entry.DevicesInfo;
import com.adasplus.proadas.common.model.BaseModel;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public class DevicesInfoModel implements BaseModel {

    @Override
    public void loadResourse(final BaseModel.Result result) {

        AdasNet.getInstance(App.getContext()).requestDeviceInfo(new BaseModel.Result<String>() {
            @Override
            public void success(String resultCallBack) {
                Gson gson = new Gson();
                try {
                    DeviceInfo voiceSetting = gson.fromJson(resultCallBack, DeviceInfo.class);
                    if (voiceSetting != null && voiceSetting.getRet().equals(Constants.CODE_SUCCESS)) {
                        result.success(voiceSetting);
                    } else {
                        result.failure(App.getContext().getResources().getString(com.adasplus.proadas.common.R.string.quest_failue));
                    }
                } catch (Exception e) {
                    result.failure(App.getContext().getResources().getString(com.adasplus.proadas.common.R.string.quest_failue));
                }
            }

            @Override
            public void failure(String s) {
                result.failure(s);
            }
        });
    }
}
