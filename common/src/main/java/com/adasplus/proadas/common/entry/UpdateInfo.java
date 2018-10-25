package com.adasplus.proadas.common.entry;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhangyapeng on 18-4-24.
 */

public class UpdateInfo {
    private String phoneNumber;
    private String carCode;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCarCode() {
        return carCode;
    }

    @Override
    public String toString() {
        return "UpdateInfo{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", carCode='" + carCode + '\'' +
                '}';
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    public static UpdateInfo toObject(String jsonStr){
        UpdateInfo updateInfo = new UpdateInfo();
        try {
            JSONObject jsonObject =  new JSONObject(jsonStr);
            String phoneNumber =jsonObject.getString("phoneNumber");
            updateInfo.setPhoneNumber(phoneNumber);
            String carCode =jsonObject.getString("carCode");
            updateInfo.setCarCode(carCode);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return updateInfo;
    }
    public static String toJsonStr(UpdateInfo updateInfo){
        String jsonStr = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phoneNumber", updateInfo.getPhoneNumber());
            jsonObject.put("carCode", updateInfo.getCarCode());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

}
