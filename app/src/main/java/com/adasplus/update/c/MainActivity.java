package com.adasplus.update.c;


import android.app.Activity;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;


public class MainActivity extends Activity {

    static {
        System.loadLibrary("text");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String imei = Utils.getIMEI(this);
        // setContentView(R.layout.activity_main);
        Log.e("ffmpeg",imei+"***");
        new Thread(new Runnable() {
            @Override
            public void run() {
                text2();
            }
        }).start();

        //A*256^3 + B*256^2 + C*256 + D
        int l = 22*256^3 + 100*256^2 + 99*256 + 85;
        Log.e("ffmpeg",l+"  ------" );
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    public native int text();
    public native int text1();
    public native int text2();
}
