package com.adasplus.proadas.common.entry;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class FcwInfo implements Parcelable {
    private int state;

    private int carNum;

    private CarInfo[] car = new CarInfo[5];

    public FcwInfo(){

    }

    protected FcwInfo(Parcel in) {
        state = in.readInt();
        carNum = in.readInt();
        car = in.createTypedArray(CarInfo.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(state);
        dest.writeInt(carNum);
        dest.writeTypedArray(car, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FcwInfo> CREATOR = new Creator<FcwInfo>() {
        @Override
        public FcwInfo createFromParcel(Parcel in) {
            return new FcwInfo(in);
        }

        @Override
        public FcwInfo[] newArray(int size) {
            return new FcwInfo[size];
        }
    };

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCarNum() {
        return carNum;
    }

    public void setCarNum(int carNum) {
        this.carNum = carNum;
    }

    public CarInfo[] getCar() {
        return car;
    }

    public void setCar(CarInfo[] car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "FcwInfo{" +
                "state=" + state +
                ", carNum=" + carNum +
                ", car=" + Arrays.toString(car) +
                '}';
    }
}
