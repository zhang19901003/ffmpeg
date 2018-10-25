package com.adasplus.proadas.business.common.model;

import android.text.TextUtils;
import android.util.Log;

import com.adasplus.proadas.App;
import com.adasplus.proadas.business.R;
import com.adasplus.proadas.common.Constant;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.data.Activation;
import com.adasplus.proadas.common.data.BaseInfo;
import com.adasplus.proadas.common.data.SdcardInfo;
import com.adasplus.proadas.common.data.VoiceSetting;
import com.adasplus.proadas.common.util.net.AdasNet;
import com.adasplus.proadas.socket.tcp.TcpClient;
import com.adasplus.proadas.common.entry.RegisterInfo1;
import com.adasplus.proadas.common.model.BaseModel;
import com.adasplus.proadas.common.util.Utils;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public class ActivationModel implements IActivationModel {

    @Override
    public void loadResourse(final BaseModel.Result result) {

    }

    @Override
    public void register(final String bussion, final String carNumber, final String phoneNumber, final Result result) {
        if (TextUtils.isEmpty(bussion)) {
            result.brfore(App.getContext().getResources().getString(R.string.merchantid_cannot_be_null));
            return;
        }
        if (!TextUtils.isEmpty(carNumber) && !Utils.isCar(carNumber)) {
            result.brfore(App.getContext().getResources().getString(R.string.plate_num_error));
            return;
        }
        if (!TextUtils.isEmpty(phoneNumber) && !Utils.isMobile(phoneNumber)) {
            result.brfore(App.getContext().getResources().getString(R.string.phone_num_error));
            return;
        }
        result.register();
        new Thread(new Runnable() {
            @Override
            public void run() {
                AdasNet.getInstance(App.getContext()).setActivation(bussion, carNumber, phoneNumber, new BaseModel.Result<String>() {
                    @Override
                    public void success(String resultCallBack) {
                        Gson gson = new Gson();
                        try {
                            Activation baseInfo = gson.fromJson(resultCallBack, Activation.class);
                            if (baseInfo != null) {
                                switch (baseInfo.getRet()) {
                                    case Constants.CODE_SUCCESS:
                                        result.success(baseInfo);
                                    break;
                                    case Constants.CODE_NO_NETWORK:
                                        result.failue(App.getContext().getResources().getString(R.string.sim_error));
                                        break;
                                    default:
                                        result.failue(App.getContext().getResources().getString(R.string.activation_failure));
                                        break;
                                }
                            } else {
                                result.failue(App.getContext().getResources().getString(R.string.register_fail));
                            }
                        } catch (Exception e) {
                            result.failue(App.getContext().getResources().getString(R.string.register_fail));
                        }
                    }

                    @Override
                    public void failure(String s) {
                        result.failue(App.getContext().getResources().getString(R.string.register_fail));
                    }
                });
            }
        }).start();
    }
}
