package com.adasplus.proadas.business.device.presenter;

import com.adasplus.proadas.common.data.WarnParams;
import com.adasplus.proadas.common.entry.EarlyWarning;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public interface IEarlyWarningPresenter {
    //  void activationCallBack(String camera, String bumper, String left, String right, String calibrationHeight);
    void updateCallBack(WarnParams adasConfig2);

    void resetWarnParms();

}
