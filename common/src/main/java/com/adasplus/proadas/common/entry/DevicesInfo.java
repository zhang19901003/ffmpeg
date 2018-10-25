package com.adasplus.proadas.common.entry;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by zhangyapeng on 17-12-26.
 */

public class DevicesInfo implements Serializable {
    private String uuid;
    private String imei;
    private String carNumber;
    private String phoneNumber;
    private String activeStatus;
    private String devicesStatus;
    private String appVersion;
    private String systemVersion;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    private String merchantId;
    public String getUuid() {
        return uuid;
    }

    public DevicesInfo() {
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    @Override
    public String toString() {
        return "DevicesInfo{" +
                "uuid='" + uuid + '\'' +
                ", imei='" + imei + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", activeStatus='" + activeStatus + '\'' +
                ", devicesStatus='" + devicesStatus + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", systemVersion='" + systemVersion + '\'' +
                ", merchantId='" + merchantId + '\'' +
                '}';
    }

    public static DevicesInfo toObject(String jsonStr){
        DevicesInfo devicesInfo = new DevicesInfo();
        try {
            JSONObject jsonObject =  new JSONObject(jsonStr);
            String uuid =jsonObject.getString("uuid");
            String imei =jsonObject.getString("imei");
            String carNumber =jsonObject.getString("carNumber");
            String phoneNumber =jsonObject.getString("phoneNumber");
            String activeStatus =jsonObject.getString("activeStatus");
            String devicesStatus =jsonObject.getString("devicesStatus");
            String appVersion =jsonObject.getString("appVersion");
            String systemVersion =jsonObject.getString("systemVersion");
            String merchantId = jsonObject.getString("merchantId");
            devicesInfo.setUuid(uuid);
            devicesInfo.setImei(imei);
            devicesInfo.setCarNumber(carNumber);
            devicesInfo.setPhoneNumber(phoneNumber);
            devicesInfo.setActiveStatus(activeStatus);
            devicesInfo.setDevicesStatus(devicesStatus);
            devicesInfo.setAppVersion(appVersion);
            devicesInfo.setSystemVersion(systemVersion);
            devicesInfo.setMerchantId(merchantId);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return devicesInfo;
    }
    public static String toJsonStr(DevicesInfo devicesInfo){
        String jsonStr = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uuid", devicesInfo.getUuid());
            jsonObject.put("imei", devicesInfo.getImei());
            jsonObject.put("carNumber", devicesInfo.getCarNumber());
            jsonObject.put("phoneNumber", devicesInfo.getPhoneNumber());
            jsonObject.put("activeStatus", devicesInfo.getActiveStatus());
            jsonObject.put("devicesStatus", devicesInfo.getDevicesStatus());
            jsonObject.put("appVersion", devicesInfo.getAppVersion());
            jsonObject.put("systemVersion", devicesInfo.getSystemVersion());
            jsonObject.put("merchantId",devicesInfo.getMerchantId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getDevicesStatus() {
        return devicesStatus;
    }

    public void setDevicesStatus(String devicesStatus) {
        this.devicesStatus = devicesStatus;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }
}
