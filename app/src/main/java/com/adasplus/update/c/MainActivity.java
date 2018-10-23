package com.adasplus.update.c;


import android.app.Activity;

import android.os.Bundle;


public class MainActivity extends Activity {

    static {
        System.loadLibrary("text");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


       // setContentView(R.layout.activity_main);

        text1();


    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    public native int text();
    public native int text1();
}
