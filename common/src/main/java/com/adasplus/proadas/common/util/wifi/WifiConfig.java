package com.adasplus.proadas.common.util.wifi;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiConfiguration.AuthAlgorithm;
import android.net.wifi.WifiConfiguration.KeyMgmt;

import com.adasplus.proadas.common.entry.WifiMessage;

public class WifiConfig {

    static final int SECURITY_NONE = 0;
    static final int SECURITY_WEP = 1;
    static final int SECURITY_PSK = 2;
    static final int SECURITY_EAP = 3;

    private static int getSecurity(WifiConfiguration config) {
        if (config.allowedKeyManagement.get(KeyMgmt.WPA_PSK)) {
            return SECURITY_PSK;
        }
        if (config.allowedKeyManagement.get(KeyMgmt.WPA_EAP) ||
                config.allowedKeyManagement.get(KeyMgmt.IEEE8021X)) {
            return SECURITY_EAP;
        }
        return (config.wepKeys[0] != null) ? SECURITY_WEP : SECURITY_NONE;
    }

    private static int getSecurity(WifiMessage result) {
        if (result.getCapabilities().contains("WEP")) {
            return SECURITY_WEP;
        } else if (result.getCapabilities().contains("PSK")) {
            return SECURITY_PSK;
        } else if (result.getCapabilities().contains("EAP")) {
            return SECURITY_EAP;
        }
        return SECURITY_NONE;
    }


    public String getWifiConfigurationSecurity(WifiConfiguration wifiConfig) {
        return String.valueOf(getSecurity(wifiConfig));
    }


    public String getScanResultSecurity(WifiMessage scanResult) {
        return String.valueOf(getSecurity(scanResult));
    }


    public void setupSecurity(WifiConfiguration config, String security, String password) {
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();

        final int sec = security == null ? SECURITY_NONE : Integer.valueOf(security);
        final int passwordLen = password == null ? 0 : password.length();
        switch (sec) {
            case SECURITY_NONE:
                config.allowedKeyManagement.set(KeyMgmt.NONE);
                break;

            case SECURITY_WEP:
                config.allowedKeyManagement.set(KeyMgmt.NONE);
                config.allowedAuthAlgorithms.set(AuthAlgorithm.OPEN);
                config.allowedAuthAlgorithms.set(AuthAlgorithm.SHARED);
                if (passwordLen != 0) {

                    if ((passwordLen == 10 || passwordLen == 26 || passwordLen == 58) &&
                            password.matches("[0-9A-Fa-f]*")) {
                        config.wepKeys[0] = password;
                    } else {
                        config.wepKeys[0] = '"' + password + '"';
                    }
                }
                break;

            case SECURITY_PSK:
                config.allowedKeyManagement.set(KeyMgmt.WPA_PSK);
                if (passwordLen != 0) {
                    if (password.matches("[0-9A-Fa-f]{64}")) {
                        config.preSharedKey = password;
                    } else {
                        config.preSharedKey = '"' + password + '"';
                    }
                }
                break;

            case SECURITY_EAP:
                config.allowedKeyManagement.set(KeyMgmt.WPA_EAP);
                config.allowedKeyManagement.set(KeyMgmt.IEEE8021X);
                break;
        }
    }

    public static WifiConfig newInstance() {
        return new WifiConfig();
    }


}
