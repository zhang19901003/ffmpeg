package com.adasplus.proadas.common.entry;

/**
 * Created by zhangyapeng on 18-3-26.
 */

public class WifiMessage {

    private String bssid;
    private String capabilities;
    private String ssid;

    @Override
    public String toString() {
        return "WifiMessage{" +
                "bssid='" + bssid + '\'' +
                ", capabilities='" + capabilities + '\'' +
                ", ssid='" + ssid + '\'' +
                ", type=" + type +
                '}';
    }

    public String getBssid() {
        return bssid;
    }

    public WifiMessage(String bssid, String capabilities, String ssid, int type) {
        this.bssid = bssid;
        this.capabilities = capabilities;
        this.ssid = ssid;
        this.type = type;
    }

    public String getCapabilities() {
        return capabilities;
    }

    public String getSsid() {
        return ssid;
    }

    public int getType() {
        return type;
    }

    private int type;

}
