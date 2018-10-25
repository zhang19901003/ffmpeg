package com.adasplus.proadas.common.entry;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class LaneInfo implements Parcelable {

	private int isCredible;

	private Point[] points = new Point[2];

	public LaneInfo(){

	}

	protected LaneInfo(Parcel in) {
		isCredible = in.readInt();
		points = in.createTypedArray(Point.CREATOR);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(isCredible);
		dest.writeTypedArray(points, flags);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<LaneInfo> CREATOR = new Creator<LaneInfo>() {
		@Override
		public LaneInfo createFromParcel(Parcel in) {
			return new LaneInfo(in);
		}

		@Override
		public LaneInfo[] newArray(int size) {
			return new LaneInfo[size];
		}
	};

	public int getIsCredible() {
		return isCredible;
	}

	public void setIsCredible(int isCredible) {
		this.isCredible = isCredible;
	}

	public Point[] getPoints() {
		return points;
	}

	public void setPoints(Point[] points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return "LaneInfo{" +
				"isCredible=" + isCredible +
				", points=" + Arrays.toString(points) +
				'}';
	}
}
