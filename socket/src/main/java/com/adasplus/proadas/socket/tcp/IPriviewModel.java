package com.adasplus.proadas.socket.tcp;

import android.view.SurfaceHolder;

import com.adasplus.proadas.common.model.BaseModel;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public interface IPriviewModel extends BaseModel {
     void priview(SurfaceHolder surfaceHolder,final BaseModel.Result result,String id);
     void relese();
     void disconnectTcp();
     interface Result<T> {
          void success(T resultCallBack,int ret);
          void failure(String s);
     }
     void selectCameraId(int cameraId);
     void initSuccess(final BaseModel.Result result);
    // void initHolder(SurfaceHolder surfaceHolder,final BaseModel.Result result);

}
