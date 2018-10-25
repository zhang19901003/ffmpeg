package com.adasplus.proadas.business.common.view;

import android.view.SurfaceHolder;

import com.adasplus.proadas.common.data.PointInfo;
import com.adasplus.proadas.common.entry.DeviceSpace;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public interface IPriviewView extends BaseView {

    void connectSuccess ();
    void failure (String s);
    SurfaceHolder getHolder();
    void hideTextView();

}
