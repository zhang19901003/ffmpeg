package com.adasplus.proadas.common.entry;

import android.os.Parcel;
import android.os.Parcelable;

public class GpsInfo implements Parcelable {
	private int  flag = 0;
	private long time;
	private float lat;
	private float lon;
	private float speed;
	private float heading;

	public GpsInfo(){

	}

	protected GpsInfo(Parcel in) {
		flag = in.readInt();
		time = in.readLong();
		lat = in.readFloat();
		lon = in.readFloat();
		speed = in.readFloat();
		heading = in.readFloat();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(flag);
		dest.writeLong(time);
		dest.writeFloat(lat);
		dest.writeFloat(lon);
		dest.writeFloat(speed);
		dest.writeFloat(heading);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<GpsInfo> CREATOR = new Creator<GpsInfo>() {
		@Override
		public GpsInfo createFromParcel(Parcel in) {
			return new GpsInfo(in);
		}

		@Override
		public GpsInfo[] newArray(int size) {
			return new GpsInfo[size];
		}
	};

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLon() {
		return lon;
	}

	public void setLon(float lon) {
		this.lon = lon;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getHeading() {
		return heading;
	}

	public void setHeading(float heading) {
		this.heading = heading;
	}

	@Override
	public String toString() {
		return "GpsInfo{" +
				"flag=" + flag +
				", time=" + time +
				", lat=" + lat +
				", lon=" + lon +
				", speed=" + speed +
				", heading=" + heading +
				'}';
	}
}
