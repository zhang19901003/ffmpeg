package com.adasplus.proadas.common.entry;

import org.json.JSONException;
import org.json.JSONObject;

public class EarlyWarning {

    // 3---->4 //2018/4/20
    private int ldwSensitivity;

    private int fcwSensitivity;

    private int pedSensitivity;

    public int getDfwSensitivity() {
        return dfwSensitivity;
    }

    public void setDfwSensitivity(int dfwSensitivity) {
        this.dfwSensitivity = dfwSensitivity;
    }

    private int dfwSensitivity;

    //4
    private int ldwMinVelocity;

    private int fcwMinVelocity;

    private int pedMinVelocity;

    private int dfwMinVelocity;
    //  8
    private int isLdwEnable;

    private int isFcwEnable;

    private int isPedEnable;

    private int isDfwEnable;

    private int isYawnEnable;

    private int isLampEnable;

    private int isSmokeEnable;

    private int isCallPhoneEnable;

    public int getLdwSensitivity() {
        return ldwSensitivity;
    }

    public void setLdwSensitivity(int ldwSensitivity) {
        this.ldwSensitivity = ldwSensitivity;
    }

    public int getFcwSensitivity() {
        return fcwSensitivity;
    }

    public void setFcwSensitivity(int fcwSensitivity) {
        this.fcwSensitivity = fcwSensitivity;
    }

    public int getPedSensitivity() {
        return pedSensitivity;
    }

    public void setPedSensitivity(int pedSensitivity) {
        this.pedSensitivity = pedSensitivity;
    }

    public int getLdwMinVelocity() {
        return ldwMinVelocity;
    }

    public void setLdwMinVelocity(int ldwMinVelocity) {
        this.ldwMinVelocity = ldwMinVelocity;
    }

    public int getFcwMinVelocity() {
        return fcwMinVelocity;
    }

    public void setFcwMinVelocity(int fcwMinVelocity) {
        this.fcwMinVelocity = fcwMinVelocity;
    }

    public int getPedMinVelocity() {
        return pedMinVelocity;
    }

    public void setPedMinVelocity(int pedMinVelocity) {
        this.pedMinVelocity = pedMinVelocity;
    }

    public int getDfwMinVelocity() {
        return dfwMinVelocity;
    }

    public void setDfwMinVelocity(int dfwMinVelocity) {
        this.dfwMinVelocity = dfwMinVelocity;
    }

    public int getIsLdwEnable() {
        return isLdwEnable;
    }

    public void setIsLdwEnable(int isLdwEnable) {
        this.isLdwEnable = isLdwEnable;
    }

    public int getIsFcwEnable() {
        return isFcwEnable;
    }

    public void setIsFcwEnable(int isFcwEnable) {
        this.isFcwEnable = isFcwEnable;
    }

    public int getIsPedEnable() {
        return isPedEnable;
    }

    public void setIsPedEnable(int isPedEnable) {
        this.isPedEnable = isPedEnable;
    }

    public int getIsDfwEnable() {
        return isDfwEnable;
    }

    public void setIsDfwEnable(int isDfwEnable) {
        this.isDfwEnable = isDfwEnable;
    }

    public int getIsYawnEnable() {
        return isYawnEnable;
    }

    public void setIsYawnEnable(int isYawnEnable) {
        this.isYawnEnable = isYawnEnable;
    }

    public int getIsLampEnable() {
        return isLampEnable;
    }

    public void setIsLampEnable(int isLampEnable) {
        this.isLampEnable = isLampEnable;
    }

    public int getIsSmokeEnable() {
        return isSmokeEnable;
    }

    public void setIsSmokeEnable(int isSmokeEnable) {
        this.isSmokeEnable = isSmokeEnable;
    }

    public int getIsCallPhoneEnable() {
        return isCallPhoneEnable;
    }

    public void setIsCallPhoneEnable(int isCallPhoneEnable) {
        this.isCallPhoneEnable = isCallPhoneEnable;
    }



    public static EarlyWarning toObject(String jsonStr) {
        EarlyWarning adasConfig2 = new EarlyWarning();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            adasConfig2.setLdwSensitivity(Integer.parseInt(jsonObject.getString("ldwSensitivity")));
            adasConfig2.setFcwSensitivity(Integer.parseInt(jsonObject.getString("fcwSensitivity")));
            adasConfig2.setPedSensitivity(Integer.parseInt(jsonObject.getString("pedSensitivity")));
            adasConfig2.setDfwSensitivity(Integer.parseInt(jsonObject.getString("dfwSensitivity")));
            adasConfig2.setLdwMinVelocity(Integer.parseInt(jsonObject.getString("ldwMinVelocity")));
            adasConfig2.setFcwMinVelocity(Integer.parseInt(jsonObject.getString("fcwMinVelocity")));
            adasConfig2.setPedMinVelocity(Integer.parseInt(jsonObject.getString("pedMinVelocity")));
            adasConfig2.setDfwMinVelocity(Integer.parseInt(jsonObject.getString("dfwMinVelocity")));
            adasConfig2.setIsLdwEnable(Integer.parseInt(jsonObject.getString("isLdwEnable")));
            adasConfig2.setIsFcwEnable(Integer.parseInt(jsonObject.getString("isFcwEnable")));
            adasConfig2.setIsPedEnable(Integer.parseInt(jsonObject.getString("isPedEnable")));
            adasConfig2.setIsDfwEnable(Integer.parseInt(jsonObject.getString("isDfwEnable")));
            adasConfig2.setIsYawnEnable(Integer.parseInt(jsonObject.getString("isYawnEnable")));
            adasConfig2.setIsLampEnable(Integer.parseInt(jsonObject.getString("isLampEnable")));
            adasConfig2.setIsSmokeEnable(Integer.parseInt(jsonObject.getString("isSmokeEnable")));
            adasConfig2.setIsCallPhoneEnable(Integer.parseInt(jsonObject.getString("isCallPhoneEnable")));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return adasConfig2;
    }

    @Override
    public String toString() {
        return "EarlyWarning{" +
                "ldwSensitivity=" + ldwSensitivity +
                ", fcwSensitivity=" + fcwSensitivity +
                ", pedSensitivity=" + pedSensitivity +
                ", dfwSensitivity=" + dfwSensitivity +
                ", ldwMinVelocity=" + ldwMinVelocity +
                ", fcwMinVelocity=" + fcwMinVelocity +
                ", pedMinVelocity=" + pedMinVelocity +
                ", dfwMinVelocity=" + dfwMinVelocity +
                ", isLdwEnable=" + isLdwEnable +
                ", isFcwEnable=" + isFcwEnable +
                ", isPedEnable=" + isPedEnable +
                ", isDfwEnable=" + isDfwEnable +
                ", isYawnEnable=" + isYawnEnable +
                ", isLampEnable=" + isLampEnable +
                ", isSmokeEnable=" + isSmokeEnable +
                ", isCallPhoneEnable=" + isCallPhoneEnable +
                '}';
    }

    public static String toJsonStr(EarlyWarning adasConfig2) {
        String jsonStr = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ldwSensitivity", adasConfig2.getLdwSensitivity());
            jsonObject.put("fcwSensitivity", adasConfig2.getFcwSensitivity());
            jsonObject.put("pedSensitivity", adasConfig2.getPedSensitivity());
            jsonObject.put("dfwSensitivity",adasConfig2.getDfwSensitivity());
            jsonObject.put("ldwMinVelocity", adasConfig2.getLdwMinVelocity());
            jsonObject.put("fcwMinVelocity", adasConfig2.getFcwMinVelocity());
            jsonObject.put("pedMinVelocity", adasConfig2.getPedMinVelocity());
            jsonObject.put("dfwMinVelocity", adasConfig2.getDfwMinVelocity());
            jsonObject.put("isLdwEnable", adasConfig2.getIsLdwEnable());
            jsonObject.put("isFcwEnable", adasConfig2.getIsFcwEnable());
            jsonObject.put("isPedEnable", adasConfig2.getIsPedEnable());
            jsonObject.put("isDfwEnable", adasConfig2.getIsDfwEnable());
            jsonObject.put("isYawnEnable", adasConfig2.getIsYawnEnable());
            jsonObject.put("isLampEnable", adasConfig2.getIsLampEnable());
            jsonObject.put("isSmokeEnable", adasConfig2.getIsSmokeEnable());
            jsonObject.put("isCallPhoneEnable", adasConfig2.getIsCallPhoneEnable());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
