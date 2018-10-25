package com.adasplus.proadas.business.device.model;

import com.adasplus.proadas.common.model.BaseModel;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public interface ICalibrationModel extends BaseModel {

    void update(String camera, String bumper, String left, String right, String calibrationHeight,  Result result);
    interface Result <T>{
        void brfore(T t);
        void update();
        void success();
        void failue();
    }
    void reset(Result result);

}
