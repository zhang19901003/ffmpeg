
package com.adasplus.proadas.common.util.wifi;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import com.adasplus.proadas.common.entry.WifiMessage;

import java.util.Comparator;
import java.util.List;

public class Wifi {
	
	public static final WifiConfig ConfigSec = WifiConfig.newInstance();

	public static boolean connectToNewNetwork(final Context ctx, final WifiManager wifiMgr, final WifiMessage scanResult, final String password ,boolean isClear) {
		final String security = ConfigSec.getScanResultSecurity(scanResult);
		WifiConfiguration config = new WifiConfiguration();
		config.SSID = convertToQuotedString(scanResult.getSsid());
		config.BSSID = scanResult.getBssid();
		ConfigSec.setupSecurity(config, security, password);
		if(isClear){
			int id = -1;
			try {
				id = wifiMgr.addNetwork(config);
			} catch(NullPointerException e) {

			}
			if(id == -1) {
				Log.e("Adas","return false2");
				return false;
			}
		}

		if(!wifiMgr.saveConfiguration()) {
			Log.e("Adas","return false0");
			return false;
		}
		
		config = getWifiConfiguration(wifiMgr, config, security);
		if(config == null) {
			Log.e("Adas","return false1");
			return false;
		}
		
		return connectToConfiguredNetwork(ctx, wifiMgr, config);
	}

	public static boolean connectToConfiguredNetwork(final Context ctx, final WifiManager wifiMgr, WifiConfiguration config) {

        if(Version.SDK >= 23) {

            return connectToConfiguredNetworkV23(ctx, wifiMgr, config );
        }
		final String security = ConfigSec.getWifiConfigurationSecurity(config);
		
		int oldPri = config.priority;

		int newPri = getMaxPriority(wifiMgr) + 1;
		if(newPri > MAX_PRIORITY) {
			newPri = shiftPriorityAndSave(wifiMgr);
			config = getWifiConfiguration(wifiMgr, config, security);
			if(config == null) {
				Log.e("Adas","return false3");
				return false;
			}
		}

		config.priority = newPri;
		int networkId = wifiMgr.updateNetwork(config);
		if(networkId == -1) {
			Log.e("Adas","return false4");
			return false;
		}
		if(!wifiMgr.enableNetwork(networkId, false)) {
			config.priority = oldPri;
			Log.e("Adas","return false5");
			return false;
		}
		
		if(!wifiMgr.saveConfiguration()) {
			config.priority = oldPri;
			Log.e("Adas","return false6");
			return false;
		}
		config = getWifiConfiguration(wifiMgr, config, security);
		if(config == null) {
			Log.e("Adas","return false7");
			return false;
		}

		if(!wifiMgr.enableNetwork(config.networkId, true)) {
			Log.e("Adas","return false8");
			return false;
		}

		return  wifiMgr.reassociate();
	}

    private static boolean connectToConfiguredNetworkV23(final Context ctx, final WifiManager wifiMgr, WifiConfiguration config ) {
        if(!wifiMgr.enableNetwork(config.networkId, true)) {
            return false;
        }

        return  wifiMgr.reassociate();
    }
	
	private static void sortByPriority(final List<WifiConfiguration> configurations) {
		java.util.Collections.sort(configurations, new Comparator<WifiConfiguration>() {

			@Override
			public int compare(WifiConfiguration object1,
					WifiConfiguration object2) {
				return object1.priority - object2.priority;
			}
		});
	}

	private static final int MAX_PRIORITY = 99999;
	
	private static int shiftPriorityAndSave(final WifiManager wifiMgr) {
		final List<WifiConfiguration> configurations = wifiMgr.getConfiguredNetworks();
		sortByPriority(configurations);
		final int size = configurations.size();
		for(int i = 0; i < size; i++) {
			final WifiConfiguration config = configurations.get(i);
			config.priority = i;
			wifiMgr.updateNetwork(config);
		}
		wifiMgr.saveConfiguration();
		return size;
	}

	private static int getMaxPriority(final WifiManager wifiManager) {
		final List<WifiConfiguration> configurations = wifiManager.getConfiguredNetworks();
		int pri = 0;
		for(final WifiConfiguration config : configurations) {
			if(config.priority > pri) {
				pri = config.priority;
			}
		}
		return pri;
	}
	
	private static final String BSSID_ANY = "any";

	
	public static WifiConfiguration getWifiConfiguration(final WifiManager wifiMgr, final WifiConfiguration configToFind, String security) {
		final String ssid = configToFind.SSID;
		if(ssid.length() == 0) {
			return null;
		}
		
		final String bssid = configToFind.BSSID;

		
		if(security == null) {
			security = ConfigSec.getWifiConfigurationSecurity(configToFind);
		}
		
		final List<WifiConfiguration> configurations = wifiMgr.getConfiguredNetworks();

		for(final WifiConfiguration config : configurations) {
			if(config.SSID == null || !ssid.equals(config.SSID)) {
				continue;
			}
			if(config.BSSID == null || BSSID_ANY.equals(config.BSSID) || bssid == null || bssid.equals(config.BSSID)) {
				final String configSecurity = ConfigSec.getWifiConfigurationSecurity(config);
				if(security.equals(configSecurity)) {
					return config;
				}
			}
		}
		return null;
	}
	
	public static String convertToQuotedString(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        
        final int lastPos = string.length() - 1;
        if(lastPos > 0 && (string.charAt(0) == '"' && string.charAt(lastPos) == '"')) {
            return string;
        }
        return "\"" + string + "\"";
    }


}
