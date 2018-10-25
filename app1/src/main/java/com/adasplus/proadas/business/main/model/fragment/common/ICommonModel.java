package com.adasplus.proadas.business.main.model.fragment.common;

import com.adasplus.proadas.common.model.BaseModel;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public interface ICommonModel extends BaseModel {

    void resumeFactory(Result result);
    void setGpsEnable(String s,Result result);
    void adasRunnning(Result result);
    void setModelEnable(String s,Result result);
    void getModelEnable(Result result);
    void playSound(Result result);
}
