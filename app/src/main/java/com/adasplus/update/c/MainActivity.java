package com.adasplus.update.c;


import android.app.Activity;

import android.os.Bundle;


public class MainActivity extends Activity {

    static {
        System.loadLibrary("native-lib");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


       // setContentView(R.layout.activity_main);

        text();


    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    public native int text();
}
