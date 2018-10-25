package com.adasplus.proadas.common.data;

/**
 * Created by zhangyapeng on 18-5-3.
 */

public class DeviceInfo {

    /**
     * ret : 1
     * imei : 866275037061190
     * uuid : 1519293,1525340678
     * plateNum : 京A12777
     * phoneNum :
     * activationState : true
     * appVersion : 1.4.2.20180501
     * osVersion : KYKJMP712D002a_userdebug_V1.1.2_180503
     */

    private String ret;
    private String imei;
    private String uuid;
    private String plateNum;
    private String phoneNum;
    private String activationState;
    private String appVersion;
    private String osVersion;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getActivationState() {
        return activationState;
    }

    public void setActivationState(String activationState) {
        this.activationState = activationState;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }
}
