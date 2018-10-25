package com.adasplus.proadas.common.entry;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhangyapeng on 18-4-24.
 */

public class DeviceSpace {
    private String total;
    private String useAble;
    private String use;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUseAble() {
        return useAble;
    }

    public void setUseAble(String useAble) {
        this.useAble = useAble;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    @Override
    public String toString() {
        return "DeviceSpace{" +
                "total='" + total + '\'' +
                ", useAble='" + useAble + '\'' +
                ", use='" + use + '\'' +
                '}';
    }

    public static String toJsonStr(DeviceSpace deviceSpace) {
        String jsonStr = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("total", deviceSpace.getTotal());
            jsonObject.put("useAble", deviceSpace.getUseAble());
            jsonObject.put("use", deviceSpace.getUse());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public static DeviceSpace toObject(String jsonStr) {
        DeviceSpace deviceSpace = new DeviceSpace();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            deviceSpace.setTotal((jsonObject.getString("total")));
            deviceSpace.setUseAble((jsonObject.getString("useAble")));
            deviceSpace.setUse((jsonObject.getString("use")));

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return deviceSpace;
    }
}
