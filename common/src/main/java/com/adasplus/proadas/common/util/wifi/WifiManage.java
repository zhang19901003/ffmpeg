package com.adasplus.proadas.common.util.wifi;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.entry.WifiMessage;

import java.util.ArrayList;
import java.util.List;

public class WifiManage {

    private Context mContext;
    private static WifiManage mWifiManage;

    private WifiManager mWifiManager;

    //    public boolean isWifi(final Context context) {
//        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
//        return networkInfo != null && networkInfo.getType() == 1;
//    }
//
    public boolean connectWifi(WifiMessage scanResults, final String pwd, boolean isClear) throws InterruptedException {

        if (scanResults == null) {
            return false;
        }

        if (isAdHoc(scanResults)) {
            return false;
        }
        boolean connResult;
        connResult = Wifi.connectToNewNetwork(mContext, mWifiManager, scanResults, pwd, isClear);
        return connResult;
    }

    public void disconnectWifi() {
        String SSID = mWifiManager.getConnectionInfo().getSSID().replace("\"", "");
        if (!TextUtils.isEmpty(SSID)) {
            mWifiManager.disableNetwork(getNetworkIdBySSID(SSID));
            mWifiManager.disconnect();
        }

    }


    public boolean clearWifiConfig(String ssid) {

        List<WifiConfiguration> wifiConfigurations = mWifiManager.getConfiguredNetworks();
        if (wifiConfigurations != null && wifiConfigurations.size() > 0) {
            for (WifiConfiguration wifiConfiguration : wifiConfigurations) {
                if (wifiConfiguration.SSID.replace("\"", "").contains(ssid)) {
                    if (mWifiManager.removeNetwork(wifiConfiguration.networkId) && mWifiManager.saveConfiguration()) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean isAdHoc(final WifiMessage scanResule) {
        return scanResule.getCapabilities().indexOf("IBSS") != -1;
    }

    public boolean isCurrentWifiIsAdas(String ssid) {

        if (mWifiManager != null) {
            Log.e("TAG","current wifiInfo :::"+intIP2StringIP(mWifiManager.getConnectionInfo().getIpAddress())+"   "+mWifiManager.getConnectionInfo().getSSID());
            return mWifiManager.getConnectionInfo().getSSID().contains(ssid) &&intIP2StringIP(mWifiManager.getConnectionInfo().getIpAddress()).contains("192.168");
        } else {
            return false;
        }
    }


    public WifiInfo getCurrentWifiInfo(){
        if (mWifiManager != null) {
            return mWifiManager.getConnectionInfo();
        } else {
            return null;
        }
    }

    public int getNetworkIdBySSID(String SSID) {
        if (TextUtils.isEmpty(SSID)) {
            return 0;
        }
        WifiConfiguration config = isExsits(SSID);
        if (config != null) {
            return config.networkId;
        }
        return 0;
    }

    private WifiConfiguration isExsits(String SSID) {
        List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
        if (existingConfigs != null && existingConfigs.size() > 0) {
            for (WifiConfiguration existingConfig : existingConfigs) {
                if (existingConfig.SSID.equals(SSID) || existingConfig.SSID.equals("\"" + SSID + "\"")) {
                    return existingConfig;
                }
            }
        }
        return null;
    }

    private WifiManage(Context context) {
        this.mContext = context;
        this.mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    public static WifiManage getInstance(Context context) {
        if (mWifiManage == null) {
            synchronized (WifiManage.class) {
                if (mWifiManage == null) {
                    mWifiManage = new WifiManage(context);
                }
            }
        }
        return mWifiManage;
    }

    public boolean startScan() {
        if (isWifiEnabled()) {
            return mWifiManager.startScan();
        }
        return false;
    }


    public List<ScanResult> getScanResults() {
        List<ScanResult> scanResults = mWifiManager.getScanResults();
        if (scanResults != null && scanResults.size() > 0) {
            return scanResults;
        } else {
            return new ArrayList<>();
        }
    }

    public boolean isWifiEnabled() {

        return mWifiManager.isWifiEnabled();
    }

    public int getWifiState(){
        return mWifiManager.getWifiState();
    }

    public String getIpAddressFromHotspot() {
        DhcpInfo dhcpInfo = mWifiManager.getDhcpInfo();
        if(dhcpInfo != null) {
            int address = dhcpInfo.gateway;
            return ((address & 0xFF)
                    + "." + ((address >> 8) & 0xFF)
                    + "." + ((address >> 16) & 0xFF)
                    + "." + ((address >> 24) & 0xFF));
        }
        return null;
    }


    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }
}
