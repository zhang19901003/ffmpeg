package com.adasplus.proadas.business.common.model;

import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Log;

import com.adasplus.proadas.App;
import com.adasplus.proadas.business.R;
import com.adasplus.proadas.common.Constant;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.data.Activation;
import com.adasplus.proadas.common.data.BaseInfo;
import com.adasplus.proadas.common.data.DeviceState;
import com.adasplus.proadas.common.util.net.AdasNet;
import com.adasplus.proadas.socket.tcp.TcpClient;
import com.adasplus.proadas.common.entry.DevicesInfo;
import com.adasplus.proadas.common.entry.UpdateInfo;
import com.adasplus.proadas.common.model.BaseModel;
import com.adasplus.proadas.common.util.Utils;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public class UpdateModel implements IUpdateModel {

    public Handler mHandler;
    private HandlerThread mHandlerThread;

    @Override
    public void loadResourse(final BaseModel.Result result) {
        mHandlerThread = new HandlerThread("update");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());

        AdasNet.getInstance(App.getContext()).requestActivation(new BaseModel.Result<String>() {
            @Override
            public void success(String resultCallBack) {
                Gson gson = new Gson();
                Activation deviceState = gson.fromJson(resultCallBack, Activation.class);
                if (deviceState != null) {
                    result.success(deviceState);
                } else {
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
    public void update(String bussion, final String carNumber, final String phoneNumber, final Result result) {
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
        result.update();

        AdasNet.getInstance(App.getContext()).updateInfo(carNumber, phoneNumber, new BaseModel.Result<String>() {
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

                            case Constants.CODE_NO_NETWORK:
                                result.failue(App.getContext().getResources().getString(R.string.net_disconnect));
                                break;
                            default:
                                result.failue(App.getContext().getResources().getString(R.string.update_failure));
                                break;
                        }
                    } else {
                        result.failue(App.getContext().getResources().getString(R.string.update_failure));
                    }
                } catch (Exception e) {
                    result.failue(App.getContext().getResources().getString(R.string.update_failure));
                }
            }

            @Override
            public void failure(String s) {
                result.failue(s);
            }
        });
    }
}
