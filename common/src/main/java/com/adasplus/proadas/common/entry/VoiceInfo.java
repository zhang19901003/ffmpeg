package com.adasplus.proadas.common.entry;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhangyapeng on 18-4-25.
 */

public class VoiceInfo  {
    private String type;
    private String size;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "VoiceInfo{" +
                "type=" + type +
                ", size=" + size +
                '}';
    }

    public static VoiceInfo toObject(String jsonStr){
        VoiceInfo voiceInfo = new VoiceInfo();
        try {
            JSONObject jsonObject =  new JSONObject(jsonStr);
            String  type =jsonObject.getString("type");
            voiceInfo.setType(type);
            String size =jsonObject.getString("size");
            voiceInfo.setSize(size);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return voiceInfo;
    }
    public static String toJsonStr(VoiceInfo voiceInfo){
        String jsonStr = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", voiceInfo.getType());
            jsonObject.put("size", voiceInfo.getSize());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
