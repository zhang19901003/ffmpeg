package com.adasplus.proadas;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.reciver.WifiBroadcaseReceiver;
import com.tencent.bugly.crashreport.CrashReport;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhangyapeng on 18-4-2.
 */

public class App extends Application {

    private static Context sContext;

    private static List<Activity> mActivitys = Collections
            .synchronizedList(new LinkedList<Activity>());

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;

        CrashReport.initCrashReport(getApplicationContext(), "959af10d78", false);



        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                pushActivity(activity);
                Log.e("TAG", "onActivityCreat" + "   " + mActivitys.size());
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

                if (null == mActivitys && mActivitys.isEmpty()) {
                    return;
                }
                if (mActivitys.contains(activity)) {
                    popActivity(activity);
                }

                Log.e("TAG", "onActivityDestoryed   " + mActivitys.size());
            }
        });

    }


    public static void popActivity(Activity activity) {
        mActivitys.remove(activity);
    }

    public static void remove(String name) {
        for (Activity activity : mActivitys) {
            if (activity.getClass().getName().equals(name)) {

                activity.finish();
            }
        }
    }

    private static WifiBroadcaseReceiver mWifiBroadcaseReceiver = new WifiBroadcaseReceiver() {

        @Override
        public void onWifiConnected() {
        }

        @Override
        public void onWifiDisconnected() {
            Log.e("TAG", "App      disconnect ");
            if (mActivitys.size() > 0) {
                Activity currentActivity = mActivitys.get(mActivitys.size() - 1);
                for (Activity activity : mActivitys) {
                    if (currentActivity.equals(activity)) {
                        continue;
                    }
                    activity.finish();
                }
                mActivitys.clear();
                mActivitys.add(currentActivity);
                Class<?> className = null;
                try {
                    className = Class.forName(Constants.MAINACTIVITY);
                    currentActivity.startActivity(new Intent(currentActivity, className));
                    currentActivity.finish();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }


    };


    public static void registerWifiReceiver() {
        IntentFilter wifiFilter = new IntentFilter();
        wifiFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        wifiFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        wifiFilter.setPriority(10000);
        App.getContext().registerReceiver(mWifiBroadcaseReceiver, wifiFilter);
    }


    public void pushActivity(Activity activity) {
        mActivitys.add(activity);
    }

    public static void unRegister() {
        App.getContext().unregisterReceiver(mWifiBroadcaseReceiver);
    }

    public static void startMainActivity() {
        if (mActivitys.size() > 0) {

            for (int num = mActivitys.size() - 1; num > 0; num--) {
                try {
                    if (mActivitys.get(num).getClass().getName().equals(Class.forName(Constants.MAINACTIVITY))) {
                        continue;
                    } else {
                        mActivitys.get(num).finish();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
//            Activity currentActivity = mActivitys.get(mActivitys.size() - 1);
//
//            for (Activity activity : mActivitys) {
//                if(currentActivity.equals(activity)){
//                    continue;
//                }
//                activity.finish();
//            }
//            mActivitys.clear();
//            mActivitys.add(currentActivity);
//            Class<?> className = null;
//            try {
//                className = Class.forName(Constants.MAINACTIVITY);
//                currentActivity.startActivity(new Intent(currentActivity,className));
//                currentActivity.finish();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
        }
    }

    /**
     *     /**
     * Save the given network to the list of configured networks for the
     * foreground user. If the network already exists, the configuration
     * is updated. Any new network is enabled by default.
     *
     * For a new network, this function is used instead of a
     * sequence of addNetwork(), enableNetwork() and saveConfiguration().
     *
     * For an existing network, it accomplishes the task of updateNetwork()
     * and saveConfiguration()
     */


    /**
     *   /**
     * Connect to a network with the given configuration. The network also
     * gets added to the list of configured networks for the foreground user.
     *
     * For a new network, this function is used instead of a
     * sequence of addNetwork(), enableNetwork(), saveConfiguration() and
     * reconnect()
     *
     */
}

