package com.adasplus.update.c;

import android.Manifest;
import android.app.Activity;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends Activity {

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//       String cpuInfo = android.os.Build.CPU_ABI;
//        byte a [] = new byte[1280*720*15/10];
//        try {
//            FileInputStream fileOutputStream = new FileInputStream(new File("/sdcard/asa"));
//            fileOutputStream.read(a);
//
//            fileOutputStream.close();
//
//           byte [] b= PicUtil.rgb2YCbCr420(a,1280,720);
//
//
//            byte[] savejpeg = PicUtil.savejpeg(b, 1280, 720);
//
//
//
//            FileOutputStream fileOutputStream1 = new FileOutputStream(new File("/sdcard/asd.jpg"));
//            fileOutputStream1.write(savejpeg);
//            fileOutputStream1.close();
//
//        } catch ( Exception e) {
//            e.printStackTrace();
//            Log.e("ffm", e.getMessage());
//        }
//        Log.e("Adas",cpuInfo);

   setContentView(R.layout.activity_main);
    }
    // public native void test();
}
