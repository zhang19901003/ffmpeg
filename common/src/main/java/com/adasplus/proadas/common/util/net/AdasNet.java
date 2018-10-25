package com.adasplus.proadas.common.util.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.adasplus.proadas.common.Constant;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.data.WarnParams;
import com.adasplus.proadas.common.entry.EarlyWarning;
import com.adasplus.proadas.common.model.BaseModel;

import java.util.HashMap;
import java.util.Map;

public class AdasNet {

    private Context mContext;
    private static AdasNet mInstance = null;

    private AdasNet(Context context) {
        mContext = context;
    }

    public static AdasNet getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AdasNet(context.getApplicationContext());
        }
        return mInstance;
    }

    public void requestDevicesStates(final BaseModel.Result<String> result) {

        RequestManager.getInstance(mContext).getResponseByGetMethod(Constants.ADAS_SERVICE + Constants.ServiceConstant.DEVICE_STATE, new IReponseListener<String>() {
            @Override
            public void onSuccess(String t) {
                if (result == null) {
                    return;
                }
                result.success(t);
            }

            @Override
            public void onFail(String errMsg) {
                if (result == null) {
                    return;
                }
                result.failure(errMsg);
            }
        }, null);
    }

    public void requestActivation(final BaseModel.Result<String> result) {
        RequestManager.getInstance(mContext).getResponseByGetMethod(Constants.ADAS_SERVICE + Constants.ServiceConstant.ACTICATION, new IReponseListener<String>() {
            @Override
            public void onSuccess(String t) {
                if (result == null) {
                    return;
                }
                result.success(t);
            }

            @Override
            public void onFail(String errMsg) {
                if (result == null) {
                    return;
                }
                result.failure(errMsg);
            }

        }, null);
    }

    public void requestDeviceInfo(final BaseModel.Result<String> result) {
        RequestManager.getInstance(mContext).getResponseByGetMethod(Constants.ADAS_SERVICE + Constants.ServiceConstant.DEVICE_INFO, new IReponseListener<String>() {
            @Override
            public void onSuccess(String t) {
                if (result == null) {
                    return;
                }
                result.success(t);
            }

            @Override
            public void onFail(String errMsg) {
                if (result == null) {
                    return;
                }
                result.failure(errMsg);
            }

        }, null);
    }

    public void requestWarnParams(final BaseModel.Result<String> result) {
        RequestManager.getInstance(mContext).getResponseByGetMethod(Constants.ADAS_SERVICE + Constants.ServiceConstant.WARNPARAMS, new IReponseListener<String>() {


            @Override
            public void onSuccess(String t) {
                if (result == null) {
                    return;
                }
                result.success(t);
            }

            @Override
            public void onFail(String errMsg) {
                if (result == null) {
                    return;
                }
                result.failure(errMsg);
            }


        }, null);
    }

    public void requestVoiceSetting(final BaseModel.Result<String> result) {
        RequestManager.getInstance(mContext).getResponseByGetMethod(Constants.ADAS_SERVICE + Constants.ServiceConstant.VOICE_SETTING, new IReponseListener<String>() {
            @Override
            public void onSuccess(String t) {
                if (result != null) {
                    result.success(t);
                }
            }

            @Override
            public void onFail(String errMsg) {
                if (result != null) {
                    result.failure(errMsg);
                }
            }


        }, null);
    }

    public void requestCalibratiion(final BaseModel.Result<String> result) {
        RequestManager.getInstance(mContext).getResponseByGetMethod(Constants.ADAS_SERVICE + Constants.ServiceConstant.CALIBRATE_PARAMS, new IReponseListener<String>() {
            @Override
            public void onSuccess(String t) {
                if (result == null) {
                    return;
                }
                result.success(t);
            }

            @Override
            public void onFail(String errMsg) {
                if (result == null) {
                    return;
                }
                result.failure(errMsg);
            }


        }, null);
    }

    public void requestSdcardInfo(final BaseModel.Result<String> result) {
        RequestManager.getInstance(mContext).getResponseByGetMethod(Constants.ADAS_SERVICE + Constants.ServiceConstant.SDCARD_INFO, new IReponseListener<String>() {
            @Override
            public void onSuccess(String t) {
                if (result == null) {
                    return;
                }
                result.success(t);
            }

            @Override
            public void onFail(String errMsg) {
                if (result == null) {
                    return;
                }
                result.failure(errMsg);
            }


        }, null);
    }

    public void requestAdasRunning(final BaseModel.Result<String> result) {
        RequestManager.getInstance(mContext).getResponseByGetMethod(Constants.ADAS_SERVICE + Constants.ServiceConstant.ADAS_RUNNING_INFO, new IReponseListener<String>() {
            @Override
            public void onSuccess(String t) {
                if (result == null) {
                    return;
                }
                result.success(t);
            }

            @Override
            public void onFail(String errMsg) {
                if (result == null) {
                    return;
                }
                result.failure(errMsg);
            }

        }, null);
    }


    public void setPlaySound(final BaseModel.Result<String> result) {
        Map<String, String> params = new HashMap<String, String>();
        RequestManager.getInstance(mContext).
                getReponseByPostMethodDe(Constants.ADAS_SERVICE + Constants.ServiceConstant.PLAY_SOUND, new IReponseListener<String>() {
                    @Override
                    public void onSuccess(String t) {
                        if (result == null) {
                            return;
                        }
                        result.success(t);
                    }

                    @Override
                    public void onFail(String errMsg) {
                        if (result == null) {
                            return;
                        }
                        result.failure(errMsg);
                    }
                }, params);
    }

    public void getTestState (final BaseModel.Result<String> result) {
        Map<String, String> params = new HashMap<String, String>();
        RequestManager.getInstance(mContext).
                getReponseByPostMethod(Constants.ADAS_SERVICE + Constants.ServiceConstant.GET_TEST_STATE, new IReponseListener<String>() {
                    @Override
                    public void onSuccess(String t) {
                        if (result == null) {
                            return;
                        }
                        result.success(t);
                    }

                    @Override
                    public void onFail(String errMsg) {
                        if (result == null) {
                            return;
                        }
                        result.failure(errMsg);
                    }
                }, params);
    }


    public void setModelEnable  (String s,final BaseModel.Result<String> result) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("state",s);
        RequestManager.getInstance(mContext).
                getReponseByPostMethod(Constants.ADAS_SERVICE + Constants.ServiceConstant.SET_TEST_STATE, new IReponseListener<String>() {
                    @Override
                    public void onSuccess(String t) {
                        if (result == null) {
                            return;
                        }
                        result.success(t);
                    }

                    @Override
                    public void onFail(String errMsg) {
                        if (result == null) {
                            return;
                        }
                        result.failure(errMsg);
                    }
                }, params);
    }


    public void requestAdasPoint(final BaseModel.Result<String> result) {
        RequestManager.getInstance(mContext).getResponseByGetMethod(Constants.ADAS_SERVICE + Constants.ServiceConstant.GET_POINT, new IReponseListener<String>() {
            @Override
            public void onSuccess(String t) {
                if (result == null) {
                    return;
                }
                result.success(t);
            }

            @Override
            public void onFail(String errMsg) {
                if (result == null) {
                    return;
                }
                result.failure(errMsg);
            }

        }, null);
    }

    public void setActivation(String merchantId, String plateNum, String phoneNum, final BaseModel.Result<String> result) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.CONFIG_MERCHANTID, merchantId);
        params.put(Constants.CONFIG_PLATENUM, plateNum);
        params.put(Constants.STR_PHONENUMBER, phoneNum);
        RequestManager.getInstance(mContext).getReponseByPostMethod(Constants.ADAS_SERVICE + Constants.ServiceConstant.ACTIVATEDEVICE, new IReponseListener<String>() {
            @Override
            public void onSuccess(String t) {
                if (result == null) {
                    return;
                }
                result.success(t);
            }

            @Override
            public void onFail(String errMsg) {
                if (result == null) {
                    return;
                }
                result.failure(errMsg);
            }
        }, params);
//        return RequestManager.getInstance(mContext).
//                getReponseByPostMethod(Constants.ADAS_SERVICE + Constants.ServiceConstant.ACTIVATEDEVICE, params);
    }


    public void setFormat(final BaseModel.Result<String> result) {
        Map<String, String> params = new HashMap<String, String>();
        RequestManager.getInstance(mContext).
                getReponseByPostMethod(Constants.ADAS_SERVICE + Constants.ServiceConstant.FORMAT_SDCARD, new IReponseListener<String>() {
                    @Override
                    public void onSuccess(String t) {
                        if (result == null) {
                            return;
                        }
                        result.success(t);
                    }

                    @Override
                    public void onFail(String errMsg) {
                        if (result == null) {
                            return;
                        }
                        result.failure(errMsg);
                    }
                }, params);
    }

    public void updateInfo(String plateNum, String phoneNum, final BaseModel.Result<String> result) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.CONFIG_PLATENUM, plateNum);
        params.put(Constants.STR_PHONENUMBER, phoneNum);
        RequestManager.getInstance(mContext).
                getReponseByPostMethod(Constants.ADAS_SERVICE + Constants.ServiceConstant.UPDATE_DEVICEINFO, new IReponseListener<String>() {
                    @Override
                    public void onSuccess(String t) {
                        if (result == null) {
                            return;
                        }
                        result.success(t);
                    }

                    @Override
                    public void onFail(String errMsg) {
                        if (result == null) {
                            return;
                        }
                        result.failure(errMsg);
                    }
                }, params);
    }

    public void setVoice(String type, String size, final BaseModel.Result<String> result) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.CONFIG_VOICETYPE, type);
        params.put(Constants.CONFIG_VOICESIZE, size);
        RequestManager.getInstance(mContext).
                getReponseByPostMethod(Constants.ADAS_SERVICE + Constants.ServiceConstant.SET_VOICE, new IReponseListener<String>() {
                    @Override
                    public void onSuccess(String t) {
                        if (result == null) {
                            return;
                        }
                        result.success(t);
                    }

                    @Override
                    public void onFail(String errMsg) {
                        if (result == null) {
                            return;
                        }
                        result.failure(errMsg);
                    }
                }, params);
    }

    public void setWarnParams(WarnParams earlyWarning, final BaseModel.Result<String> result) {

        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.CONFIG_DFWSPEED, earlyWarning.getDfwSpeed());
        params.put(Constants.CONFIG_DFWSENSITIVITY, earlyWarning.getDfwSensitivity());
        params.put(Constants.CONFIG_DFWENABLE, earlyWarning.getDfwEnable());

        params.put(Constants.CONFIG_FCWSPEED, earlyWarning.getFcwSpeed());
        params.put(Constants.CONFIG_FCWSENSITIVITY, earlyWarning.getFcwSensitivity());
        params.put(Constants.CONFIG_FCWENABLE, earlyWarning.getFcwEnable());

        params.put(Constants.CONFIG_PCWSPEED, earlyWarning.getPcwSpeed());
        params.put(Constants.CONFIG_PCWSENSITIVITY, earlyWarning.getPcwSensitivity());
        params.put(Constants.CONFIG_PCWENABLE, earlyWarning.getPcwEnable());

        params.put(Constants.CONFIG_LDWSPEED, earlyWarning.getLdwSpeed());
        params.put(Constants.CONFIG_LDWSENSITIVITY, earlyWarning.getLdwSensitivity());
        params.put(Constants.CONFIG_LDWENABLE, earlyWarning.getLdwEnable());

        params.put(Constants.CONFIG_CALLPHONEENABLE, earlyWarning.getCallphoneEnable());
        params.put(Constants.CONFIG_SMOKEENABLE, earlyWarning.getSmokingEnable());
        params.put(Constants.CONFIG_YAWNENABLE, earlyWarning.getYawnEnable());

        RequestManager.getInstance(mContext).
                getReponseByPostMethod(Constants.ADAS_SERVICE + Constants.ServiceConstant.SET_WARNPARAMS, new IReponseListener<String>() {
                    @Override
                    public void onSuccess(String t) {
                        if (result == null) {
                            return;
                        }
                        result.success(t);
                    }

                    @Override
                    public void onFail(String errMsg) {
                        if (result == null) {
                            return;
                        }
                        result.failure(errMsg);
                    }
                }, params);
    }

    public void resumeFactory(final BaseModel.Result<String> result) {
        Map<String, String> params = new HashMap<String, String>();
        RequestManager.getInstance(mContext).
                getReponseByPostMethod(Constants.ADAS_SERVICE + Constants.ServiceConstant.SET_RESUME_FACTORY, new IReponseListener<String>() {
                    @Override
                    public void onSuccess(String t) {
                        if (result == null) {
                            return;
                        }
                        result.success(t);
                    }

                    @Override
                    public void onFail(String errMsg) {
                        if (result == null) {
                            return;
                        }
                        result.failure(errMsg);
                    }
                }, params);
    }

    public void setGpsEnable(final String enable, final BaseModel.Result<String> result) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.GPS_ENABLE, enable);
        RequestManager.getInstance(mContext).
                getReponseByPostMethod(Constants.ADAS_SERVICE + Constants.ServiceConstant.SET_GPS_ENABLE, new IReponseListener<String>() {
                    @Override
                    public void onSuccess(String t) {
                        if (result == null) {
                            return;
                        }
                        result.success(t);
                    }

                    @Override
                    public void onFail(String errMsg) {
                        if (result == null) {
                            return;
                        }
                        result.failure(errMsg);
                    }
                }, params);
    }

    public void setResetWarnParms(final BaseModel.Result<String> result) {
        Map<String, String> params = new HashMap<String, String>();
        RequestManager.getInstance(mContext).
                getReponseByPostMethod(Constants.ADAS_SERVICE + Constants.ServiceConstant.RESUME_WARNPARMS, new IReponseListener<String>() {
                    @Override
                    public void onSuccess(String t) {
                        if (result == null) {
                            return;
                        }
                        result.success(t);
                    }

                    @Override
                    public void onFail(String errMsg) {
                        if (result == null) {
                            return;
                        }
                        result.failure(errMsg);
                    }
                }, params);
    }

    public String startPriview(String cameraId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.CAMERA_ID, cameraId);
        return RequestManager.getInstance(mContext).
                getReponseByPostMethod(Constants.ADAS_SERVICE + Constants.ServiceConstant.START_PRIVIEW, params);
    }

    public String stopPriview(String cameraId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.CAMERA_ID, cameraId);
        return RequestManager.getInstance(mContext).
                getReponseByPostMethod(Constants.ADAS_SERVICE + Constants.ServiceConstant.STOPT_PRIVIEW, params);
    }

    public void setCalibrateParams(String camera, String bumper, String left, String right, String calibrationHeight, final BaseModel.Result<String> result) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.CAMERAHEIGHT, String.valueOf((int) Float.parseFloat(camera)));
        params.put(Constants.BUMPERDIS, String.valueOf((int) Float.parseFloat(bumper)));
        params.put(Constants.LEFTWHEEL, String.valueOf((int) Float.parseFloat(left)));
        params.put(Constants.RIGHTWHEEL, String.valueOf((int) Float.parseFloat(right)));
        params.put(Constants.CALIBRATIONDIS, String.valueOf((int) Float.parseFloat(calibrationHeight)));
        RequestManager.getInstance(mContext).
                getReponseByPostMethod(Constants.ADAS_SERVICE + Constants.ServiceConstant.SET_CALIBRATEPARAMS, new IReponseListener<String>() {
                    @Override
                    public void onSuccess(String t) {
                        if (result == null) {
                            return;
                        }
                        result.success(t);
                    }

                    @Override
                    public void onFail(String errMsg) {
                        if (result == null) {
                            return;
                        }
                        result.failure(errMsg);
                    }
                }, params);
    }

    public void setHandCalibration(String camera, String bumper, String left, String right, String calibrationHeight, String x, String y, final BaseModel.Result<String> result) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.CAMERAHEIGHT, String.valueOf((int) Float.parseFloat(camera)));
        params.put(Constants.BUMPERDIS, String.valueOf((int) Float.parseFloat(bumper)));
        params.put(Constants.LEFTWHEEL, String.valueOf((int) Float.parseFloat(left)));
        params.put(Constants.RIGHTWHEEL, String.valueOf((int) Float.parseFloat(right)));
        params.put(Constants.CALIBRATIONDIS, String.valueOf((int) Float.parseFloat(calibrationHeight)));
        params.put(Constants.POINT_X, String.valueOf((int) Float.parseFloat(x)));
        params.put(Constants.POINT_Y, String.valueOf((int) Float.parseFloat(y)));
        RequestManager.getInstance(mContext).
                getReponseByPostMethod(Constants.ADAS_SERVICE + Constants.ServiceConstant.SET_HAND_CALIBRATEPARAMS, new IReponseListener<String>() {
                    @Override
                    public void onSuccess(String t) {
                        if (result == null) {
                            return;
                        }
                        result.success(t);
                    }

                    @Override
                    public void onFail(String errMsg) {
                        if (result == null) {
                            return;
                        }
                        result.failure(errMsg);
                    }
                }, params);
    }

    public void setAutoCalibration(String camera, String bumper, String left, String right, String calibrationHeight, final BaseModel.Result<String> result) {
        Map<String, String> params = new HashMap<String, String>();

        params.put(Constants.CAMERAHEIGHT, String.valueOf((int) (Float.parseFloat(camera))));
        params.put(Constants.BUMPERDIS, String.valueOf((int) Float.parseFloat(bumper)));
        params.put(Constants.LEFTWHEEL, String.valueOf((int) Float.parseFloat(left)));
        params.put(Constants.RIGHTWHEEL, String.valueOf((int) Float.parseFloat(right)));
        params.put(Constants.CALIBRATIONDIS, String.valueOf((int) Float.parseFloat(calibrationHeight)));
        RequestManager.getInstance(mContext).
                getReponseByPostMethod(Constants.ADAS_SERVICE + Constants.ServiceConstant.SET_AUTO_CALIBRATEPARAMS, new IReponseListener<String>() {
                    @Override
                    public void onSuccess(String t) {
                        if (result == null) {
                            return;
                        }
                        result.success(t);
                    }

                    @Override
                    public void onFail(String errMsg) {
                        if (result == null) {
                            return;
                        }
                        result.failure(errMsg);
                    }
                }, params);
    }
}
