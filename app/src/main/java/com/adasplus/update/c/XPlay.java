package com.adasplus.update.c;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by zhangyapeng on 18-10-12.
 */

public class XPlay extends  SurfaceView implements  Runnable,SurfaceHolder.Callback {
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
            new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void run() {
         Open("/sdcard/aa.mp4",getHolder().getSurface());
    }
    public  native  int Open(String path,Object surface);
}
