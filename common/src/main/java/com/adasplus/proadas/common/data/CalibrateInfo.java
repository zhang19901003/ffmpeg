package com.adasplus.proadas.common.data;


/**
 * Created by fengyin on 18-5-8.
 */

public class CalibrateInfo extends BaseInfo{
    private String cameraHeight;
    private String calibrateHeight;
    private String bumperDis;
    private String leftWheel;
    private String rightWheel;

    public String getCameraHeight() {
        return cameraHeight;
    }

    public void setCameraHeight(String cameraHeight) {
        this.cameraHeight = cameraHeight;
    }

    public String getCalibrateHeight() {
        return calibrateHeight;
    }

    public void setCalibrateHeight(String calibrateHeight) {
        this.calibrateHeight = calibrateHeight;
    }

    public String getBumperDis() {
        return bumperDis;
    }

    public void setBumperDis(String bumperDis) {
        this.bumperDis = bumperDis;
    }

    public String getLeftWheel() {
        return leftWheel;
    }

    public void setLeftWheel(String leftWheel) {
        this.leftWheel = leftWheel;
    }

    public String getRightWheel() {
        return rightWheel;
    }

    public void setRightWheel(String rightWheel) {
        this.rightWheel = rightWheel;
    }
}
