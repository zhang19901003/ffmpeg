package com.adasplus.proadas.business.common.presenter;

import android.view.SurfaceHolder;

import com.adasplus.proadas.common.model.BaseModel;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public interface IPriviewPresenter {

    void priview(SurfaceHolder surfaceHolder,String id);
    void initHolder(final BaseModel.Result result);
    void disconnectTcp();
    void selectCameraId(int cameraId);
    void initSuccess();
    void relese();
}
