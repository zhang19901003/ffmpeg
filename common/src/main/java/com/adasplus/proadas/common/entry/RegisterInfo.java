package com.adasplus.proadas.common.entry;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by zhangyapeng on 17-12-27.
 */

public class RegisterInfo implements Serializable {
    private String merchantId;
    private String uuid;
    private String secretKey;
    private String phoneNumber;

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

    private String carNumber;
    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String toString() {
        return "RegisterInfo{" +
                "merchantId='" + merchantId + '\'' +
                ", uuid='" + uuid + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", carNumber='" + carNumber + '\'' +
                '}';
    }

    public static RegisterInfo toObject(String jsonStr){
        RegisterInfo registerInfo = new RegisterInfo();
        try {
            JSONObject jsonObject =  new JSONObject(jsonStr);
            String uuid =jsonObject.getString("uuid");
            String merchantId =jsonObject.getString("merchantId");
            String secretKey =jsonObject.getString("secretKey");
            String carNumber=jsonObject.getString("carNumber");
            String phoneNumber=jsonObject.getString("phoneNumber");
            registerInfo.setUuid(uuid);
            registerInfo.setMerchantId(merchantId);
            registerInfo.setSecretKey(secretKey);
            registerInfo.setCarNumber(carNumber);
            registerInfo.setPhoneNumber(phoneNumber);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return registerInfo;
    }
    public static String toJsonStr(RegisterInfo registerInfo){
        String jsonStr = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uuid", registerInfo.getUuid());
            jsonObject.put("secretKey", registerInfo.getSecretKey());
            jsonObject.put("merchantId", registerInfo.getMerchantId());
            jsonObject.put("carNumber",registerInfo.getCarNumber());
            jsonObject.put("phoneNumber",registerInfo.getPhoneNumber());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
