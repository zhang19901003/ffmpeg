package com.adasplus.proadas.common.entry;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by fengyin on 16-12-29.
 */

public class PedInfo implements Parcelable {
    private int state;

    private int perNum;

    private Person[] persons = new Person[5];

    public PedInfo(){

    }

    protected PedInfo(Parcel in) {
        state = in.readInt();
        perNum = in.readInt();
        persons = in.createTypedArray(Person.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(state);
        dest.writeInt(perNum);
        dest.writeTypedArray(persons, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PedInfo> CREATOR = new Creator<PedInfo>() {
        @Override
        public PedInfo createFromParcel(Parcel in) {
            return new PedInfo(in);
        }

        @Override
        public PedInfo[] newArray(int size) {
            return new PedInfo[size];
        }
    };

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPerNum() {
        return perNum;
    }

    public void setPerNum(int perNum) {
        this.perNum = perNum;
    }

    public Person[] getCar() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "PedInfo{" +
                "state=" + state +
                ", perNum=" + perNum +
                ", persons=" + Arrays.toString(persons) +
                '}';
    }
}
