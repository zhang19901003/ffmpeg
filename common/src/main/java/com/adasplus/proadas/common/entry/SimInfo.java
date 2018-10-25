package com.adasplus.proadas.common.entry;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhangyapeng on 18-4-24.
 */

public class SimInfo {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SimInfo{" +
                "message='" + message + '\'' +
                '}';
    }

    private String message;

    public static SimInfo toObject(String jsonStr){
        SimInfo simInfo = new SimInfo();
        try {
            JSONObject jsonObject =  new JSONObject(jsonStr);
            String message =jsonObject.getString("message");
            simInfo.setMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return simInfo;
    }
    public static String toJsonStr(SimInfo simInfo){
        String jsonStr = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("message", simInfo.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

}
