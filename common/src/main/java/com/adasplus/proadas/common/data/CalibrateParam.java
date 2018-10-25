package com.adasplus.proadas.common.data;

/**
 * Created by zhangyapeng on 18-5-17.
 */

public class CalibrateParam {
    private String cameraHeight;
    private String bumperDis;
    private String leftWheel;
    private String rightWheel;

    public String getCameraHeight() {
        return cameraHeight;
    }

    public void setCameraHeight(String cameraHeight) {
        this.cameraHeight = cameraHeight;
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

    public String getCalibrationDis() {
        return calibrationDis;
    }

    public void setCalibrationDis(String calibrationDis) {
        this.calibrationDis = calibrationDis;
    }

    public String getPointX() {
        return pointX;
    }

    public void setPointX(String pointX) {
        this.pointX = pointX;
    }

    public String getPointY() {
        return pointY;
    }

    public void setPointY(String pointY) {
        this.pointY = pointY;
    }

    @Override
    public String toString() {
        return "CalibrateParam{" +
                "cameraHeight='" + cameraHeight + '\'' +
                ", bumperDis='" + bumperDis + '\'' +
                ", leftWheel='" + leftWheel + '\'' +
                ", rightWheel='" + rightWheel + '\'' +
                ", calibrationDis='" + calibrationDis + '\'' +
                ", pointX='" + pointX + '\'' +
                ", pointY='" + pointY + '\'' +
                '}';
    }

    private String calibrationDis;
    private String pointX;
    private String pointY;
}
