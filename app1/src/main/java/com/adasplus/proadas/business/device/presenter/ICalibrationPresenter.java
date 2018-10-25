package com.adasplus.proadas.business.device.presenter;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public interface ICalibrationPresenter {
    void activationCallBack(String camera, String bumper, String left, String right,String calibrationHeight);
    void resetCalibration();
}
