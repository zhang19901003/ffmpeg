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
        setContentView(R.layout.activity_main);

//        TextView tv = (TextView) findViewById(R.id.sample_text);
//         tv.setText(String.valueOf(ffOpen("/sdcard/2018-10-11-18-09-43after.mp4")));
        // test();

    }

    //  public native int ffOpen(String path);

    // public native void test();
}
