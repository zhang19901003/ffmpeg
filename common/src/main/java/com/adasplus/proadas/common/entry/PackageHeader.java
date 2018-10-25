package com.adasplus.proadas.common.entry;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by zhangyapeng on 17-12-20.
 */

public class PackageHeader implements Serializable {

    private int uTransPackageHdrSize;
    //当前包的大小(sizeof(PackageHeader)+数据长度)
    private int uTransPackageSize;


    //数据的总大小
    private int uDataSize;
    //数据被分成包的个数
    private int uDataPackageNum;
    //数据包当前的帧号
    private int uDataPackageCurrIndex;
    //数据包在整个数据中的偏移


    public int getuTransPackageHdrSize() {
        return uTransPackageHdrSize;
    }

    public void setuTransPackageHdrSize(int uTransPackageHdrSize) {
        this.uTransPackageHdrSize = uTransPackageHdrSize;
    }

    public int getuTransPackageSize() {
        return uTransPackageSize;
    }

    public void setuTransPackageSize(int uTransPackageSize) {
        this.uTransPackageSize = uTransPackageSize;
    }

    public int getuDataSize() {
        return uDataSize;
    }

    public void setuDataSize(int uDataSize) {
        this.uDataSize = uDataSize;
    }

    public int getuDataPackageNum() {
        return uDataPackageNum;
    }

    public void setuDataPackageNum(int uDataPackageNum) {
        this.uDataPackageNum = uDataPackageNum;
    }

    public int getuDataPackageCurrIndex() {
        return uDataPackageCurrIndex;
    }

    public void setuDataPackageCurrIndex(int uDataPackageCurrIndex) {
        this.uDataPackageCurrIndex = uDataPackageCurrIndex;
    }

    public int getuDataPackageOffset() {
        return uDataPackageOffset;
    }

    public void setuDataPackageOffset(int uDataPackageOffset) {
        this.uDataPackageOffset = uDataPackageOffset;
    }

    private int uDataPackageOffset;

    public static String toJsonStr(PackageHeader packageHeader) {
        String jsonStr = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uTransPackageHdrSize", packageHeader.getuTransPackageHdrSize());
            jsonObject.put("uTransPackageSize", packageHeader.getuTransPackageSize());
            jsonObject.put("uDataSize", packageHeader.getuDataSize());
            jsonObject.put("uDataPackageNum", packageHeader.getuDataPackageNum());
            jsonObject.put("uDataPackageCurrIndex", packageHeader.getuDataPackageCurrIndex());
            jsonObject.put("uDataPackageOffset", packageHeader.getuDataPackageOffset());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


    @Override
    public String toString() {
        return "PackageHeader{" +
                "uTransPackageHdrSize=" + uTransPackageHdrSize +
                ", uTransPackageSize=" + uTransPackageSize +
                ", uDataSize=" + uDataSize +
                ", uDataPackageNum=" + uDataPackageNum +
                ", uDataPackageCurrIndex=" + uDataPackageCurrIndex +
                ", uDataPackageOffset=" + uDataPackageOffset +
                '}';
    }

    public static PackageHeader toObject(String jsonStr) {
        PackageHeader packageHeader = new PackageHeader();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            int uTransPackageHdrSize = (jsonObject.getInt("uTransPackageHdrSize"));
            int uTransPackageSize = (jsonObject.getInt("uTransPackageSize"));
            int uDataSize = (jsonObject.getInt("uDataSize"));
            int uDataPackageNum = (jsonObject.getInt("uDataPackageNum"));
            int uDataPackageCurrIndex = (jsonObject.getInt("uDataPackageCurrIndex"));
            int uDataPackageOffset = (jsonObject.getInt("uDataPackageOffset"));

            packageHeader.setuTransPackageHdrSize(uTransPackageHdrSize);
            packageHeader.setuTransPackageSize(uTransPackageSize);
            packageHeader.setuDataSize(uDataSize);
            packageHeader.setuDataPackageNum(uDataPackageNum);
            packageHeader.setuDataPackageCurrIndex(uDataPackageCurrIndex);
            packageHeader.setuDataPackageOffset(uDataPackageOffset);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return packageHeader;
    }

}
