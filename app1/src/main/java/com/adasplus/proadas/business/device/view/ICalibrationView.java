package com.adasplus.proadas.business.device.view;

import com.adasplus.proadas.common.data.CalibrateInfo;
import com.adasplus.proadas.common.entry.CalibrateParam;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public interface ICalibrationView extends BaseView {
     void updateCode (String code);
     void success(CalibrateInfo calibrateParam, int code);
     void showDialog(String s);
     void hideDialog();
}
