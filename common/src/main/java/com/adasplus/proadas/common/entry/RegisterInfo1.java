package com.adasplus.proadas.common.entry;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by zhangyapeng on 17-12-27.
 */

public class RegisterInfo1 implements Serializable {
    private String merchantId;
    private String phoneNumber;
    private String carNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @Override
    public String toString() {
        return "RegisterInfo{" +
                "merchantId='" + merchantId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", carNumber='" + carNumber + '\'' +
                '}';
    }

    public static RegisterInfo1 toObject(String jsonStr) {
        RegisterInfo1 registerInfo = new RegisterInfo1();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            String merchantId = jsonObject.getString("merchantId");
            String carNumber = jsonObject.getString("carNumber");
            String phoneNumber = jsonObject.getString("phoneNumber");
            registerInfo.setMerchantId(merchantId);
            registerInfo.setCarNumber(carNumber);
            registerInfo.setPhoneNumber(phoneNumber);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return registerInfo;
    }

    public static String toJsonStr(RegisterInfo1 registerInfo) {
        String jsonStr = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("merchantId", registerInfo.getMerchantId());
            jsonObject.put("carNumber", registerInfo.getCarNumber());
            jsonObject.put("phoneNumber", registerInfo.getPhoneNumber());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
