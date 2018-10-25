package com.adasplus.proadas.business.main.model.fragment.common;


import android.util.Log;

import com.adasplus.proadas.App;
import com.adasplus.proadas.common.Constant;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.data.AdasRunningInfo;
import com.adasplus.proadas.common.data.BaseInfo;
import com.adasplus.proadas.common.data.DeviceState;
import com.adasplus.proadas.common.data.Model;
import com.adasplus.proadas.common.data.Ret;
import com.adasplus.proadas.common.util.net.AdasNet;
import com.adasplus.proadas.common.util.wifi.WifiManage;
import com.adasplus.proadas.socket.tcp.TcpClient;
import com.adasplus.proadas.common.entry.CommonEntry;
import com.adasplus.proadas.common.model.BaseModel;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by zhangyapeng on 18-4-10.
 */

/**
 * 46000 中国移动 （GSM）
 * 46001 中国联通 （GSM）
 * 46002 中国移动 （TD-S）
 * 46003 中国电信（CDMA）
 * 46004 空（似乎是专门用来做测试的）
 * 46005 中国电信 （CDMA）
 * 46006 中国联通 （WCDMA）
 * 46007 中国移动 （TD-S）
 * 46008
 * 46009
 * 46010
 * 46011 中国电信 （FDD-LTE）
 */
public class CommonModel implements ICommonModel {
    int i = 0;

    @Override
    public void loadResourse(final BaseModel.Result result) {

        App.registerWifiReceiver();

        AdasNet.getInstance(App.getContext()).requestDevicesStates(new Result<String>() {
            @Override
            public void success(String resultCallBack) {
                Gson gson = new Gson();
                try {
                    DeviceState deviceState = gson.fromJson(resultCallBack, DeviceState.class);
                    if (deviceState != null && deviceState.getRet().equals(Constants.CODE_SUCCESS)) {
                        String simState = deviceState.getSimState();
                        switch (simState) {
                            case "46000":
                            case "46002":
                            case "46007":
                            case "46004":
                                deviceState.setSimState("中国移动");
                                break;
                            case "46003":
                            case "46005":
                            case "46011":
                                deviceState.setSimState("中国电信");
                                break;
                            case "46001":
                            case "46006":
                                deviceState.setSimState("中国联通");
                                break;
                        }
                        result.success(deviceState);
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
    public void resumeFactory(final Result result) {
        AdasNet.getInstance(App.getContext()).resumeFactory(new Result<String>() {
            @Override
            public void success(String resultCallBack) {
                result.success(null);
            }

            @Override
            public void failure(String s) {

            }
        });
    }


    @Override
    public void setGpsEnable(final String enable, final Result result) {

        AdasNet.getInstance(App.getContext()).setGpsEnable(enable, new Result<String>() {
            @Override
            public void success(String resultCallBack) {
                Gson gson = new Gson();
                try {
                    BaseInfo deviceState = gson.fromJson(resultCallBack, BaseInfo.class);
                    if (deviceState != null) {
                        switch (deviceState.getRet()) {
                            case Constants.CODE_SUCCESS:
                                result.success(null);
                                break;
                            default:
                                result.failure(App.getContext().getResources().getString(com.adasplus.proadas.common.R.string.set_failure));
                                break;

                        }
                    }
                } catch (Exception e) {
                    result.failure(App.getContext().getResources().getString(com.adasplus.proadas.common.R.string.set_failure));
                }
            }

            @Override
            public void failure(String s) {
                result.failure(s);
            }
        });

    }

    @Override
    public void adasRunnning(final Result result) {

        AdasNet.getInstance(App.getContext()).requestAdasRunning(new BaseModel.Result<String>() {
            @Override
            public void success(String resultCallBack) {
                Gson gson = new Gson();
                try {
                    AdasRunningInfo deviceState = gson.fromJson(resultCallBack, AdasRunningInfo.class);
                    if (deviceState == null || deviceState.getRunning().equals("0")) {
                        result.failure(App.getContext().getResources().getString(com.adasplus.proadas.common.R.string.adas_stop));
                    } else {
                        result.success(null);
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
    public void setModelEnable(String s, final Result result) {
        AdasNet.getInstance(App.getContext()).setModelEnable(s, new BaseModel.Result<String>() {

            @Override
            public void success(String resultCallBack) {
                Gson gson = new Gson();
                try {
                    Ret deviceState = gson.fromJson(resultCallBack, Ret.class);
                    if (deviceState == null) {
                        result.failure(App.getContext().getResources().getString(com.adasplus.proadas.common.R.string.set_failue));
                    } else {
                        result.success(App.getContext().getResources().getString(com.adasplus.proadas.common.R.string.set_success));
                    }
                } catch (Exception e) {
                    result.failure(App.getContext().getResources().getString(com.adasplus.proadas.common.R.string.set_failue));
                }
            }

            @Override
            public void failure(String s) {

            }
        });
    }

    @Override
    public void getModelEnable(final Result result) {
        AdasNet.getInstance(App.getContext()).getTestState(new BaseModel.Result<String>() {
            @Override
            public void success(String resultCallBack) {
                Gson gson = new Gson();
                try {
                    Model deviceState = gson.fromJson(resultCallBack, Model.class);
                    if (deviceState == null) {
                        result.failure(App.getContext().getResources().getString(com.adasplus.proadas.common.R.string.quest_failue));
                    } else {
                        result.success(deviceState);
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
    public void playSound(final Result result) {
        AdasNet.getInstance(App.getContext()).setPlaySound(new BaseModel.Result<String>() {
            @Override
            public void success(String resultCallBack) {
                Gson gson = new Gson();
                try {
                    Ret deviceState = gson.fromJson(resultCallBack, Ret.class);
                    if (deviceState != null &&  deviceState.getRet().equals("1000")) {
                        result.success(null);
                    } else {
                        result.failure(App.getContext().getResources().getString(com.adasplus.proadas.common.R.string.adas_stop));
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
