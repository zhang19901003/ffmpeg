package com.adasplus.proadas.common.entry;

import android.os.Parcel;
import android.os.Parcelable;

public class CarInfo implements Parcelable {

    private float dis;

    private float t;

    private float s;

    private int state;
    private RectInt carRect;

    public CarInfo(){

    }

    protected CarInfo(Parcel in) {
        dis = in.readFloat();
        t = in.readFloat();
        s = in.readFloat();
        state = in.readInt();
        carRect = in.readParcelable(RectInt.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(dis);
        dest.writeFloat(t);
        dest.writeFloat(s);
        dest.writeInt(state);
        dest.writeParcelable(carRect, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CarInfo> CREATOR = new Creator<CarInfo>() {
        @Override
        public CarInfo createFromParcel(Parcel in) {
            return new CarInfo(in);
        }

        @Override
        public CarInfo[] newArray(int size) {
            return new CarInfo[size];
        }
    };

    /**
     * @return the dis
     */
    public float getDis() {
        return dis;
    }

    /**
     * @param dis the dis to set
     */
    public void setDis(float dis) {
        this.dis = dis;
    }

    /**
     * @return the t
     */
    public float getT() {
        return t;
    }

    /**
     * @param t the t to set
     */
    public void setT(float t) {
        this.t = t;
    }

    /**
     * @return the s
     */
    public float getS() {
        return s;
    }

    /**
     * @param s the s to set
     */
    public void setS(float s) {
        this.s = s;
    }

    /**
     * @return the state
     */
    public int getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * @return the carRect
     */
    public RectInt getCarRect() {
        return carRect;
    }

    /**
     * @param carRect the carRect to set
     */
    public void setCarRect(RectInt carRect) {
        this.carRect = carRect;
    }

    @Override
    public String toString() {
        return "CarInfo{" +
                "dis=" + dis +
                ", t=" + t +
                ", s=" + s +
                ", state=" + state +
                ", carRect=" + carRect +
                '}';
    }
}
