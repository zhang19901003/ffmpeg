package com.adasplus.proadas.common.data;

/**
 * Created by zhangyapeng on 18-5-3.
 */

public class DeviceState {
    /**
     * ret : 1
     * deviceState : 1
     * gpsState : 0
     * simState : 0
     */

    private String ret;
    private String deviceState;
    private String gpsState;
    private String simState;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(String deviceState) {
        this.deviceState = deviceState;
    }

    public String getGpsState() {
        return gpsState;
    }

    public void setGpsState(String gpsState) {
        this.gpsState = gpsState;
    }

    public String getSimState() {
        return simState;
    }

    public void setSimState(String simState) {
        this.simState = simState;
    }
}
