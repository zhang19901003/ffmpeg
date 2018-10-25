package com.adasplus.proadas.common.entry;
/**
 * Created by fengyin on 16/8/25.
 */

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author fengyin(email:594601408@qq.com)
 * @date 2016-08-25 09:29
 * @package com.example.cameratest
 * @description DrawInfo contains LdwInfo, FcwInfo and AdasConfig object and uses these attributes
 *              to draw in surfaceview.
 * @params
 */
public class DrawInfo implements Parcelable {
    private LdwInfo ldwResults;
    private FcwInfo fcwResults;
    private AdasConfig config;
    private float speed;

    public DrawInfo(){

    }


    protected DrawInfo(Parcel in) {
        ldwResults = in.readParcelable(LdwInfo.class.getClassLoader());
        fcwResults = in.readParcelable(FcwInfo.class.getClassLoader());
        config = in.readParcelable(AdasConfig.class.getClassLoader());
        speed = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(ldwResults, flags);
        dest.writeParcelable(fcwResults, flags);
        dest.writeParcelable(config, flags);
        dest.writeFloat(speed);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DrawInfo> CREATOR = new Creator<DrawInfo>() {
        @Override
        public DrawInfo createFromParcel(Parcel in) {
            return new DrawInfo(in);
        }

        @Override
        public DrawInfo[] newArray(int size) {
            return new DrawInfo[size];
        }
    };

    public LdwInfo getLdwResults() {
        return ldwResults;
    }

    public void setLdwResults(LdwInfo ldwResults) {
        this.ldwResults = ldwResults;
    }

    public FcwInfo getFcwResults() {
        return fcwResults;
    }

    public void setFcwResults(FcwInfo fcwResults) {
        this.fcwResults = fcwResults;
    }

    public AdasConfig getConfig() {
        return config;
    }

    public void setConfig(AdasConfig config) {
        this.config = config;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "DrawInfo{" +
                "ldwResults=" + ldwResults +
                ", fcwResults=" + fcwResults +
                ", config=" + config +
                ", speed=" + speed +
                '}';
    }
}
