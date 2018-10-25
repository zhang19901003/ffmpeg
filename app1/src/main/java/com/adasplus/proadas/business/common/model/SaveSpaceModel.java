package com.adasplus.proadas.business.common.model;

import android.util.Log;

import com.adasplus.proadas.App;
import com.adasplus.proadas.business.R;
import com.adasplus.proadas.common.Constant;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.data.Activation;
import com.adasplus.proadas.common.data.BaseInfo;
import com.adasplus.proadas.common.data.SdcardInfo;
import com.adasplus.proadas.common.util.net.AdasNet;
import com.adasplus.proadas.socket.tcp.TcpClient;
import com.adasplus.proadas.common.entry.DeviceSpace;
import com.adasplus.proadas.common.model.BaseModel;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public class SaveSpaceModel implements ISaveSpaceModel {

    @Override
    public void loadResourse(final BaseModel.Result result) {
        AdasNet.getInstance(App.getContext()).requestSdcardInfo(new BaseModel.Result<String>() {
            @Override
            public void success(String resultCallBack) {
                Gson gson = new Gson();
                try {
                    SdcardInfo deviceState = gson.fromJson(resultCallBack, SdcardInfo.class);

                    if (deviceState != null) {
                        switch (deviceState.getRet()) {
                            case Constants.CODE_SUCCESS:
                                result.success(deviceState);
                                break;
                            case Constants.CODE_NO_SDCARD:
                                result.success(null);
                                break;
                            default:
                                result.failure(App.getContext().getResources().getString(com.adasplus.proadas.common.R.string.quest_failue));
                                break;
                        }

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

    @Override
    public void format(final Result result) {
        result.update();

        AdasNet.getInstance(App.getContext()).setFormat(new BaseModel.Result<String>() {
            @Override
            public void success(String resultCallBack) {
                Gson gson = new Gson();
                BaseInfo baseInfo = gson.fromJson(resultCallBack, BaseInfo.class);
                if (baseInfo != null) {
                    switch (baseInfo.getRet()) {
                        case Constants.CODE_SUCCESS:
                            result.success();
                            break;
                        default:
                            result.failue(App.getContext().getResources().getString(R.string.format_failure));
                            break;
                    }
                } else {
                    result.failue(App.getContext().getResources().getString(R.string.format_failure));
                }
            }

            @Override
            public void failure(String s) {
                result.failue(s);
            }
        });

    }
}
