package com.adasplus.proadas.common.reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.adasplus.proadas.common.Constant;
import com.adasplus.proadas.common.Constants;

public abstract class WifiBroadcaseReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            int i = intent.getAction().hashCode();
            String s = intent.toString();

            if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (info != null) {
                    if (info.getState().equals(NetworkInfo.State.CONNECTED)) {
                        long l = System.currentTimeMillis();
                        if(l- Constants.sCurrentConnectTime>1500){
                            Constants.sCurrentConnectTime =l;
                            onWifiConnected();
                        }
                    } else if (info.getState().equals(NetworkInfo.State.DISCONNECTED)) {
                        long l = System.currentTimeMillis();
                        if(l- Constants.sCurrentDisconnectTime >1500){
                            Constants.sCurrentDisconnectTime =l;
                            onWifiDisconnected();
                        }
                    }
                }
            }
        }
    }


    public abstract void onWifiConnected();

    public abstract void onWifiDisconnected();

}
