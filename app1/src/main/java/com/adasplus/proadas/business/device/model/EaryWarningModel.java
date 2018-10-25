package com.adasplus.proadas.business.device.model;


import android.text.TextUtils;
import android.util.Log;

import com.adasplus.proadas.App;
import com.adasplus.proadas.business.R;
import com.adasplus.proadas.common.Constant;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.data.BaseInfo;
import com.adasplus.proadas.common.data.SdcardInfo;
import com.adasplus.proadas.common.data.WarnParams;
import com.adasplus.proadas.common.util.net.AdasNet;
import com.adasplus.proadas.socket.tcp.TcpClient;
import com.adasplus.proadas.common.entry.EarlyWarning;
import com.adasplus.proadas.common.model.BaseModel;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public class EaryWarningModel implements IEarlyWarningModel {

    @Override
    public void loadResourse(final BaseModel.Result result) {

        AdasNet.getInstance(App.getContext()).requestWarnParams(new BaseModel.Result<String>() {
            @Override
            public void success(String resultCallBack) {
                Gson gson = new Gson();
                try {
                    WarnParams deviceState = gson.fromJson(resultCallBack, WarnParams.class);

                    if (deviceState != null && deviceState.getRet().equals(Constants.CODE_SUCCESS)) {
                        result.success(deviceState);
                    } else {
                        result.failure(App.getContext().getResources().getString(com.adasplus.proadas.common.R.string.quest_failue));
                    }
                } catch (Exception ex) {
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
    public void update(final WarnParams adasConfig2, final Result result) {

        result.update();

        AdasNet.getInstance(App.getContext()).setWarnParams(adasConfig2, new BaseModel.Result<String>() {
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
                            case Constants.CODE_FAIL:
                                result.failue(Constants.CODE_FAIL);
                                break;
                            case Constants.CODE_SERVER_ERROR:
                                result.failue(Constants.CODE_SERVER_ERROR);
                                break;
                            case Constants.CODE_DEVICE_NO_ACTIVATE:
                                result.failue(Constants.CODE_DEVICE_NO_ACTIVATE);
                                break;
                        }
                    } else {
                        result.failue(App.getContext().getResources().getString(R.string.set_failure));
                    }
                } catch (Exception e) {
                    result.failue(App.getContext().getResources().getString(R.string.save_fali));
                }
            }

            @Override
            public void failure(String s) {
                result.failue(s);
            }
        });


    }

    @Override
    public void resetWarnParms(final Result result) {
        result.update();
        AdasNet.getInstance(App.getContext()).setResetWarnParms(new BaseModel.Result<String>() {
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
                            case Constants.CODE_FAIL:
                                result.failue(App.getContext().getResources().getString(R.string.set_failure));
                                break;

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
                result.failue(App.getContext().getResources().getString(R.string.set_failure));
            }
        });


    }
}
