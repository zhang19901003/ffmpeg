package com.adasplus.proadas.common.entry;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhangyapeng on 18-4-23.
 */

public class CommonEntry {
    private String deviceStatus;
    private String currentLocation;
    private String gpsInfo;
    private String netStatus;

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getGpsInfo() {
        return gpsInfo;
    }

    public void setGpsInfo(String gpsInfo) {
        this.gpsInfo = gpsInfo;
    }

    public String getNetStatus() {
        return netStatus;
    }

    public void setNetStatus(String netStatus) {
        this.netStatus = netStatus;
    }



    public static CommonEntry toObject(String jsonStr) {
        CommonEntry commonEntry = new CommonEntry();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            commonEntry.setDeviceStatus(jsonObject.getString("deviceStatus"));
            commonEntry.setCurrentLocation(jsonObject.getString("currentLocation"));
            commonEntry.setGpsInfo(jsonObject.getString("gpsInfo"));
            commonEntry.setNetStatus(jsonObject.getString("netStatus"));

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return commonEntry;
    }

    @Override
    public String toString() {
        return "CommonEntry{" +
                "deviceStatus='" + deviceStatus + '\'' +
                ", currentLocation='" + currentLocation + '\'' +
                ", gpsInfo='" + gpsInfo + '\'' +
                ", netStatus='" + netStatus + '\'' +
                '}';
    }

    public static String toJsonStr(CommonEntry commonEntry) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("deviceStatus", commonEntry.getDeviceStatus());
            jsonObject.put("currentLocation", commonEntry.getCurrentLocation());
            jsonObject.put("gpsInfo", commonEntry.getGpsInfo());
            jsonObject.put("netStatus", commonEntry.getNetStatus());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
