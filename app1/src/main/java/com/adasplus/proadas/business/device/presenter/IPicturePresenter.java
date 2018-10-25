package com.adasplus.proadas.business.device.presenter;

import android.view.SurfaceHolder;

import com.adasplus.proadas.common.model.BaseModel;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public interface IPicturePresenter {

    void priview(SurfaceHolder surfaceHolder, String id);
    void disconnectTcp();
    void questPoint();
    void acitvation(String camera, String bumper, String left, String right, String calibrationHeight, String x, String y, int flag);
    void initSuccess();
    void relese();
}
