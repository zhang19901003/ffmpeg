package com.adasplus.proadas.common.entry;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by zhangyapeng on 17-12-19.
 */

public class CalibrateParam implements Serializable {
    private float cameraHeight;
    private float cameraToBumper;
    private float cameraToLeftWheel;
    private float cameraToRightWhell;
    private float cameraToFrontAxle;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    private float x,y;
    public float getCameraCalitionHeight() {
        return cameraCalitionHeight;
    }

    public void setCameraCalitionHeight(float cameraCalitionHeight) {
        this.cameraCalitionHeight = cameraCalitionHeight;
    }

    private float cameraCalitionHeight;
    public CalibrateParam() {
    }

    public float getCameraHeight() {
        return this.cameraHeight;
    }

    public void setCameraHeight(float cameraHeight) {
        this.cameraHeight = cameraHeight;
    }

    public float getCameraToBumper() {
        return this.cameraToBumper;
    }

    public void setCameraToBumper(float cameraToBumper) {
        this.cameraToBumper = cameraToBumper;
    }

    public float getCameraToLeftWheel() {
        return this.cameraToLeftWheel;
    }

    public void setCameraToLeftWheel(float cameraToLeftWheel) {
        this.cameraToLeftWheel = cameraToLeftWheel;
    }

    public float getCameraToRightWhell() {
        return this.cameraToRightWhell;
    }

    public void setCameraToRightWhell(float cameraToRightWhell) {
        this.cameraToRightWhell = cameraToRightWhell;
    }

    public float getCameraToFrontAxle() {
        return this.cameraToFrontAxle;
    }

    public void setCameraToFrontAxle(float cameraToFrontAxle) {
        this.cameraToFrontAxle = cameraToFrontAxle;
    }

    @Override
    public String toString() {
        return "CalibrateParam{" +
                "cameraHeight=" + cameraHeight +
                ", cameraToBumper=" + cameraToBumper +
                ", cameraToLeftWheel=" + cameraToLeftWheel +
                ", cameraToRightWhell=" + cameraToRightWhell +
                ", cameraToFrontAxle=" + cameraToFrontAxle +
                ", x=" + x +
                ", y=" + y +
                ", cameraCalitionHeight=" + cameraCalitionHeight +
                '}';
    }

    public static String toJsonStr(CalibrateParam calibrateParam){
        String jsonStr = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cameraHeight", calibrateParam.getCameraHeight());
            jsonObject.put("cameraToBumper", calibrateParam.getCameraToBumper());
            jsonObject.put("cameraToLeftWheel", calibrateParam.getCameraToLeftWheel());
            jsonObject.put("cameraToRightWhell", calibrateParam.getCameraToRightWhell());
            jsonObject.put("cameraToFrontAxle", calibrateParam.getCameraToFrontAxle());
            jsonObject.put("cameraCalitionHeight", calibrateParam.getCameraCalitionHeight());
            jsonObject.put("x",calibrateParam.getX());
            jsonObject.put("y",calibrateParam.getY());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


    public static CalibrateParam toObject(String jsonStr){
        CalibrateParam calibrateParam = new CalibrateParam();
        try {
            JSONObject jsonObject =  new JSONObject(jsonStr);
            float cameraHeight = Float.parseFloat(jsonObject.getString("cameraHeight"));
            float cameraToBumper =    Float.parseFloat(jsonObject.getString("cameraToBumper"));
            float cameraToLeftWheel =   Float.parseFloat( jsonObject.getString("cameraToLeftWheel"));
            float cameraToRightWhell =    Float.parseFloat(jsonObject.getString("cameraToRightWhell"));
            float cameraToFrontAxle =    Float.parseFloat(jsonObject.getString("cameraToFrontAxle"));
            float cameraCalitionHeight =   Float.parseFloat( jsonObject.getString("cameraCalitionHeight"));
            float x=Float.parseFloat(jsonObject.getString("x"));
            float y=Float.parseFloat(jsonObject.getString("y"));
            calibrateParam.setCameraHeight(cameraHeight);
            calibrateParam.setCameraToBumper(cameraToBumper);
            calibrateParam.setCameraToLeftWheel(cameraToLeftWheel);
            calibrateParam.setCameraToRightWhell(cameraToRightWhell);
            calibrateParam.setCameraToFrontAxle(cameraToFrontAxle);
            calibrateParam.setCameraCalitionHeight(cameraCalitionHeight);
            calibrateParam.setX(x);
            calibrateParam.setY(y);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return calibrateParam;
    }
}
