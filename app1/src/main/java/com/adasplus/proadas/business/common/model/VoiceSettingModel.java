package com.adasplus.proadas.business.common.model;


import android.util.Log;

import com.adasplus.proadas.App;
import com.adasplus.proadas.business.R;
import com.adasplus.proadas.common.Constant;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.data.BaseInfo;
import com.adasplus.proadas.common.data.DeviceState;
import com.adasplus.proadas.common.data.VoiceSetting;
import com.adasplus.proadas.common.util.net.AdasNet;
import com.adasplus.proadas.socket.tcp.TcpClient;
import com.adasplus.proadas.common.entry.VoiceInfo;
import com.adasplus.proadas.common.model.BaseModel;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public class VoiceSettingModel implements IVoiceSettingModel {

    @Override
    public void loadResourse(final BaseModel.Result result) {

        AdasNet.getInstance(App.getContext()).requestVoiceSetting(new BaseModel.Result<String>() {
            @Override
            public void success(String resultCallBack) {
                Gson gson = new Gson();
                VoiceSetting voiceSetting = gson.fromJson(resultCallBack, VoiceSetting.class);
                result.success(voiceSetting);
            }

            @Override
            public void failure(String s) {
                result.failure(s);
            }
        });

    }


    @Override
    public void setVoice(final String type, final String size, final Result result) {

        result.update();

        AdasNet.getInstance(App.getContext()).setVoice(type, size, new BaseModel.Result<String>() {
            @Override
            public void success(String resultCallBack) {
                Gson gson = new Gson();
                try {
                    BaseInfo baseInfo = gson.fromJson(resultCallBack, BaseInfo.class);
                    if (baseInfo != null) {
                        switch (baseInfo.getRet()) {
                            case Constants.CODE_SUCCESS:
                                result.success();
                                break;
                            default:
                                result.failue(App.getContext().getResources().getString(R.string.set_failure));
                        }
                    } else {
                        result.failue(App.getContext().getResources().getString(R.string.set_failure));
                    }
                } catch (Exception e) {
                    result.failue(App.getContext().getResources().getString(R.string.set_failure));
                }
            }

            @Override
            public void failure(String s) {
                result.failue(s);
            }
        });
    }

}
