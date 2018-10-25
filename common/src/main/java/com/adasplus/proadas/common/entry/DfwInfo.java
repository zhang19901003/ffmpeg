package com.adasplus.proadas.common.entry;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by fengyin on 17-2-4.
 */

public class DfwInfo implements Parcelable {
    private int state;
    private int headMoving;
    private int eyeCloseState;//
    private int faceDetected;
    private int sunGlassesLabel;
    private int callPhone;//
    private int smoking;//
    private RectInt faceRect;
    private Point[] keyPoints = new Point[68];
    private float yaw;
    private float pitch;
    private float roll;
    private int bValidPose;

    private RectInt leftRoi;//
    private RectInt rightRoi;//
    private RectInt mouthRoi;//

    public DfwInfo(){

    }


    protected DfwInfo(Parcel in) {
        state = in.readInt();
        headMoving = in.readInt();
        eyeCloseState = in.readInt();
        faceDetected = in.readInt();
        sunGlassesLabel = in.readInt();
        callPhone = in.readInt();
        smoking = in.readInt();
        faceRect = in.readParcelable(RectInt.class.getClassLoader());
        keyPoints = in.createTypedArray(Point.CREATOR);
        yaw = in.readFloat();
        pitch = in.readFloat();
        roll = in.readFloat();
        bValidPose = in.readInt();
        leftRoi = in.readParcelable(RectInt.class.getClassLoader());
        rightRoi = in.readParcelable(RectInt.class.getClassLoader());
        mouthRoi = in.readParcelable(RectInt.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(state);
        dest.writeInt(headMoving);
        dest.writeInt(eyeCloseState);
        dest.writeInt(faceDetected);
        dest.writeInt(sunGlassesLabel);
        dest.writeInt(callPhone);
        dest.writeInt(smoking);
        dest.writeParcelable(faceRect, flags);
        dest.writeTypedArray(keyPoints, flags);
        dest.writeFloat(yaw);
        dest.writeFloat(pitch);
        dest.writeFloat(roll);
        dest.writeInt(bValidPose);
        dest.writeParcelable(leftRoi, flags);
        dest.writeParcelable(rightRoi, flags);
        dest.writeParcelable(mouthRoi, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DfwInfo> CREATOR = new Creator<DfwInfo>() {
        @Override
        public DfwInfo createFromParcel(Parcel in) {
            return new DfwInfo(in);
        }

        @Override
        public DfwInfo[] newArray(int size) {
            return new DfwInfo[size];
        }
    };

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getHeadMoving() {
        return headMoving;
    }

    public void setHeadMoving(int headMoving) {
        this.headMoving = headMoving;
    }

    public int getEyeCloseState() {
        return eyeCloseState;
    }

    public void setEyeCloseState(int eyeCloseState) {
        this.eyeCloseState = eyeCloseState;
    }

    public int getFaceDetected() {
        return faceDetected;
    }

    public void setFaceDetected(int faceDetected) {
        this.faceDetected = faceDetected;
    }

    public int getSunGlassesLabel() {
        return sunGlassesLabel;
    }

    public void setSunGlassesLabel(int sunGlassesLabel) {
        this.sunGlassesLabel = sunGlassesLabel;
    }

    public int getCallPhone() {
        return callPhone;
    }

    public void setCallPhone(int callPhone) {
        this.callPhone = callPhone;
    }

    public int getSmoking() {
        return smoking;
    }

    public void setSmoking(int smoking) {
        this.smoking = smoking;
    }

    public RectInt getFaceRect() {
        return faceRect;
    }

    public void setFaceRect(RectInt faceRect) {
        this.faceRect = faceRect;
    }

    public Point[] getKeyPoints() {
        return keyPoints;
    }

    public void setKeyPoints(Point[] keyPoints) {
        this.keyPoints = keyPoints;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getRoll() {
        return roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }

    public int getbValidPose() {
        return bValidPose;
    }

    public void setbValidPose(int bValidPose) {
        this.bValidPose = bValidPose;
    }

    public RectInt getLeftRoi() {
        return leftRoi;
    }

    public void setLeftRoi(RectInt leftRoi) {
        this.leftRoi = leftRoi;
    }

    public RectInt getRightRoi() {
        return rightRoi;
    }

    public void setRightRoi(RectInt rightRoi) {
        this.rightRoi = rightRoi;
    }

    public RectInt getMouthRoi() {
        return mouthRoi;
    }

    public void setMouthRoi(RectInt mouthRoi) {
        this.mouthRoi = mouthRoi;
    }

    @Override
    public String toString() {
        return "DfwInfo{" +
                "state=" + state +
                ", headMoving=" + headMoving +
                ", eyeCloseState=" + eyeCloseState +
                ", faceDetected=" + faceDetected +
                ", sunGlassesLabel=" + sunGlassesLabel +
                ", callPhone=" + callPhone +
                ", smoking=" + smoking +
                ", faceRect=" + faceRect +
                ", keyPoints=" + Arrays.toString(keyPoints) +
                ", yaw=" + yaw +
                ", pitch=" + pitch +
                ", roll=" + roll +
                ", bValidPose=" + bValidPose +
                ", leftRoi=" + leftRoi +
                ", rightRoi=" + rightRoi +
                ", mouthRoi=" + mouthRoi +
                '}';
    }
}
