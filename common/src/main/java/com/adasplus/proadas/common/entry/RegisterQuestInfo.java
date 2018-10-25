package com.adasplus.proadas.common.entry;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by zhangyapeng on 17-12-27.
 */

public class RegisterQuestInfo implements Serializable {
    private String merchantId;
    private String carNumber;
    private String phoneNumber;
    private String isActivite;
    private String imei;
    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }


    public String getIsActivite() {
        return isActivite;
    }

    public void setIsActivite(String isActivite) {
        this.isActivite = isActivite;
    }


    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
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

    @Override
    public String toString() {
        return "RegisterQuestInfo{" +
                "merchantId='" + merchantId + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", isActivite='" + isActivite + '\'' +
                ", imei='" + imei + '\'' +
                '}';
    }

    public static RegisterQuestInfo toObject(String jsonStr){
        RegisterQuestInfo registerInfo = new RegisterQuestInfo();
        try {
            JSONObject jsonObject =  new JSONObject(jsonStr);
            String uuid =jsonObject.getString("carNumber");
            String merchantId =jsonObject.getString("merchantId");
            String secretKey =jsonObject.getString("phoneNumber");
            String isActivite=jsonObject.getString("isActivite");
            String imei=jsonObject.getString("imei");
            registerInfo.setCarNumber(uuid);
            registerInfo.setMerchantId(merchantId);
            registerInfo.setPhoneNumber(secretKey);
            registerInfo.setIsActivite(isActivite);
            registerInfo.setImei(imei);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return registerInfo;
    }
    public static String toJsonStr(RegisterQuestInfo registerInfo){
        String jsonStr = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("carNumber", registerInfo.getCarNumber());
            jsonObject.put("phoneNumber", registerInfo.getPhoneNumber());
            jsonObject.put("merchantId", registerInfo.getMerchantId());
            jsonObject.put("isActivite",registerInfo.getIsActivite());
            jsonObject.put("imei",registerInfo.getImei());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
