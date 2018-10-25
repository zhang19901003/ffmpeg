package com.adasplus.proadas.business.common.view;

import com.adasplus.proadas.common.data.Activation;
import com.adasplus.proadas.common.data.DeviceInfo;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-11.
 */

public interface IAlreadVIew extends BaseView{
    void success(Activation devicesInfo);
    void questFailure();
}
