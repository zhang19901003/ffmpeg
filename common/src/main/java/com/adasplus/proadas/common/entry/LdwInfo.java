package com.adasplus.proadas.common.entry;

import android.os.Parcel;
import android.os.Parcelable;

public class LdwInfo implements Parcelable {

	private int state;

	private LaneInfo left;

	private LaneInfo right;

	public LdwInfo(){

	}

	protected LdwInfo(Parcel in) {
		state = in.readInt();
		left = in.readParcelable(LaneInfo.class.getClassLoader());
		right = in.readParcelable(LaneInfo.class.getClassLoader());
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(state);
		dest.writeParcelable(left, flags);
		dest.writeParcelable(right, flags);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<LdwInfo> CREATOR = new Creator<LdwInfo>() {
		@Override
		public LdwInfo createFromParcel(Parcel in) {
			return new LdwInfo(in);
		}

		@Override
		public LdwInfo[] newArray(int size) {
			return new LdwInfo[size];
		}
	};

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public LaneInfo getLeft() {
		return left;
	}

	public void setLeft(LaneInfo left) {
		this.left = left;
	}

	public LaneInfo getRight() {
		return right;
	}

	@Override
	public String toString() {
		return "LdwInfo{" +
				"state=" + state +
				", left=" + left +
				", right=" + right +
				'}';
	}

	public void setRight(LaneInfo right) {
		this.right = right;
	}
}
