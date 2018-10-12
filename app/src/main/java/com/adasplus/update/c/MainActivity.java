package com.adasplus.update.c;

import android.Manifest;
import android.app.Activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String cpuInfo = android.os.Build.CPU_ABI;
        Log.e("Adas",cpuInfo);
        setContentView(R.layout.activity_main);
    }
    // public native void test();
}
