package com.adasplus.update.c;


import android.app.Activity;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;


public class MainActivity extends Activity {

    static {
        System.loadLibrary("text");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        String imei = Utils.getIMEI(this);
//        TelephonyManager teleMgr = (TelephonyManager)  getSystemService(Context.TELEPHONY_SERVICE);
//
//
//        Log.d("ffmpeg", teleMgr.getDeviceId()+"   "+ teleMgr.getLine1Number()+"  "+teleMgr.getSimSerialNumber()+"   "+teleMgr.getNetworkOperator());
       //  setContentView(R.layout.activity_main);
//        Log.e("ffmpeg",imei+"***");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                text2();
//            }
//        }).start();
       text4();
//
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    public native int text();
    public native int text1();
    public native int text2();
    public native int text4();
}
