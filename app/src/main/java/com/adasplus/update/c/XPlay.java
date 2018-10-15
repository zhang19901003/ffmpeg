package com.adasplus.update.c;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by zhangyapeng on 18-10-12.
 */

public class XPlay extends GLSurfaceView implements  Runnable,SurfaceHolder.Callback {
    private FileInputStream inputStream;
    public XPlay(Context context) {
        super(context);
        getHolder().addCallback(this);

    }

    public XPlay(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
       new Thread(this).start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void run() {
//
//        byte bytes[] =  new byte[1024];
//        Surface(bytes,getHolder().getSurface());

        Open("/sdcard/test.mp4",getHolder().getSurface());

    }
    public  native  int Open(String path,Object surface);
    public  native  int Surface(byte[] yuv,Object surface);
}
