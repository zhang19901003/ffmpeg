package com.adasplus.proadas.business.device.model;


import android.text.TextUtils;

import com.adasplus.proadas.App;
import com.adasplus.proadas.business.R;
import com.adasplus.proadas.common.Constant;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.data.BaseInfo;
import com.adasplus.proadas.common.data.CalibrateInfo;
import com.adasplus.proadas.common.data.DeviceInfo;
import com.adasplus.proadas.common.util.Utils;
import com.adasplus.proadas.common.util.net.AdasNet;
import com.adasplus.proadas.socket.tcp.TcpClient;
import com.adasplus.proadas.common.entry.CalibrateParam;
import com.adasplus.proadas.common.model.BaseModel;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public class CalibrationModel implements ICalibrationModel {

    @Override
    public void loadResourse(final BaseModel.Result result) {

        AdasNet.getInstance(App.getContext()).requestCalibratiion(new BaseModel.Result<String>() {
            @Override
            public void success(String resultCallBack) {
                Gson gson = new Gson();
                try {
                    CalibrateInfo calibrateInfo = gson.fromJson(resultCallBack, CalibrateInfo.class);
                    if (calibrateInfo != null && calibrateInfo.getRet().equals(Constants.CODE_SUCCESS)) {
                        result.success(calibrateInfo);
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
    public void update(final String camera, final String bumper,final String left,final String right,final String calibrationHeight, final Result result) {
        if (TextUtils.isEmpty(camera) || TextUtils.isEmpty(bumper) || TextUtils.isEmpty(left) || TextUtils.isEmpty(right) || TextUtils.isEmpty(calibrationHeight)) {
            result.brfore(App.getContext().getResources().getString(R.string.message_error));
            return;
        }
        if (!Utils.isCalibrate(camera) || !Utils.isCalibrate(bumper) || !Utils.isCalibrate(left) || !Utils.isCalibrate(right) || !Utils.isCalibrate(calibrationHeight)) {
            result.brfore(App.getContext().getResources().getString(R.string.message_error));
            return;
        }

        result.update();

         AdasNet.getInstance(App.getContext()).setCalibrateParams(camera, bumper, left, right, calibrationHeight, new BaseModel.Result<String>() {
            @Override
            public void success(String resultCallBack) {
                try {
                    Gson gson = new Gson();
                    BaseInfo baseInfo = gson.fromJson(resultCallBack, BaseInfo.class);
                    if(baseInfo!=null){
                        switch (baseInfo.getRet()){
                            case Constants.CODE_SUCCESS:
                                result.success();
                                break;
                            default:
                                result.failue();
                                break;
                        }
                    }else {
                        result.failue();
                    }
                }catch (Exception e){
                    result.failue();
                }
            }

            @Override
            public void failure(String s) {
                result.failue();
            }
        });


    }

    @Override
    public void reset(final Result result) {
      //  result.brfore("");
//       result.update();
//        try {
//            TcpClient.getInstance(new BaseModel.Result<byte[]>() {
//                @Override
//                public void success(byte[] resultCallBack) {
//                   result.success();
//                }
//
//                @Override
//                public void failure() {
//                    result.failue();
//                }
//            }).sendSocketMsg(Constant.MSG_REQUEST_RESET_CALIBRATE.getBytes("UTF-8"));
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            result.failue();
//        }
    }
}
