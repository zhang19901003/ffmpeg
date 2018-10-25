package com.adasplus.proadas.business.device.view;

import android.view.SurfaceHolder;

import com.adasplus.proadas.common.data.PointInfo;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public interface IPictureView extends BaseView {

    void connectSuccess();
    void failure(String s);
    void calibrationSuccess();
    void questSuccess(PointInfo info);
    void showDialog();
    void hideDialog();
    void hideTextView();

}
