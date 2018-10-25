package com.adasplus.proadas.common.entry;

import android.os.Parcel;
import android.os.Parcelable;

public class RectInt implements Parcelable {

    private int x;

    private int y;

    private int w;

    private int h;

    public RectInt(){

    }

    protected RectInt(Parcel in) {
        x = in.readInt();
        y = in.readInt();
        w = in.readInt();
        h = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(x);
        dest.writeInt(y);
        dest.writeInt(w);
        dest.writeInt(h);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RectInt> CREATOR = new Creator<RectInt>() {
        @Override
        public RectInt createFromParcel(Parcel in) {
            return new RectInt(in);
        }

        @Override
        public RectInt[] newArray(int size) {
            return new RectInt[size];
        }
    };

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    @Override
    public String toString() {
        return "RectInt{" +
                "x=" + x +
                ", y=" + y +
                ", w=" + w +
                ", h=" + h +
                '}';
    }
}
