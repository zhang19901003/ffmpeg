package com.adasplus.update.c;

import android.Manifest;
import android.app.Activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends Activity {

    static {
        System.loadLibrary("native-lib");
    }

    private XPlay glSurfaceView;
    private FileInputStream inputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File file = new File("/sdcard/adas.yuv");
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
        glSurfaceView = (XPlay) findViewById(R.id.gl);
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(new FirstOpenGLProjectRenderer(glSurfaceView.getHolder().getSurface()));



    }




    // public native void test();


    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }
}
