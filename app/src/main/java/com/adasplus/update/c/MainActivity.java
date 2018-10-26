package com.adasplus.update.c;


import android.app.Activity;

import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        byte a[] = new byte[640 * 360 * 15 / 10];
        setContentView(R.layout.activity_main);
        glSurfaceView = (XPlay) findViewById(R.id.gl);
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(new GlRender());

//        File file = new File("/sdcard/dms");
//        File[] files = file.listFiles();
//        for (int i = 0; i < files.length; i++) {
//            File f = files[i];
//            try {
//                FileInputStream inputStream = new FileInputStream(f);
//                inputStream.read(a);
//                byte[] jpg = PicUtil.savejpeg(a, 640, 360);
//                String name = f.getName();
//                String s = name + "." + "jpg";
//                File file1 = new File("/sdcard/dms/" + s);
//                boolean newFile = file1.createNewFile();
//                FileOutputStream fileOutputStream = new FileOutputStream(file1);
//                fileOutputStream.write(jpg);
//                fileOutputStream.flush();
//                fileOutputStream.close();
//                inputStream.close();
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }
}
