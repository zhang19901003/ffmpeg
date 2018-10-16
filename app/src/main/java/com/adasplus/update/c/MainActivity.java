package com.adasplus.update.c;


import android.app.Activity;

import android.os.Bundle;


public class MainActivity extends Activity {

    static {
        System.loadLibrary("native-lib");
    }

    private XPlay glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        glSurfaceView = (XPlay) findViewById(R.id.gl);
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(new GlRender());

    }


    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }
}
