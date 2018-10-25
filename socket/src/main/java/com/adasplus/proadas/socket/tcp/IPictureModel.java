package com.adasplus.proadas.socket.tcp;

import android.view.SurfaceHolder;

import com.adasplus.proadas.common.model.BaseModel;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public interface IPictureModel extends IPriviewModel {
    void questPoint(BaseModel.Result result);
    void handAcitvation(String camera, String bumper, String left, String right,String calibrationHeight,String x,String y,int flag, Result result);
    interface Result <T>{
        void brfore(T t);
        void update();
        void success();
        void failue(String s);
    }
}
