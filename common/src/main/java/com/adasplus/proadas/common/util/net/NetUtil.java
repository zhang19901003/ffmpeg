package com.adasplus.proadas.common.util.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by zhang on 2016/4/20.
 */
public class NetUtil {

    public enum NetType {

        NONE, WIFI, CMWAP, CMNET

    }

    /**
     * 检测网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    /**
     * 获取网络连接类型
     *
     * @param context
     * @return
     */
    public static NetType getNetworkType(Context context) {
        NetType netType = NetType.NONE;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (extraInfo != null && extraInfo.length() > 0) {
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = NetType.CMNET;
                } else {
                    netType = NetType.CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NetType.WIFI;
        }
        return netType;
    }
}
