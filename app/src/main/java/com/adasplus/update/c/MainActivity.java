package com.adasplus.update.c;


import android.app.Activity;

import android.os.Bundle;
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
        text1();


    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    public native int text();
    public native int text1();
}
