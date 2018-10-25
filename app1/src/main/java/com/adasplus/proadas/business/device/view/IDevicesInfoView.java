package com.adasplus.proadas.business.device.view;

import com.adasplus.proadas.common.data.Activation;
import com.adasplus.proadas.common.data.DeviceInfo;
import com.adasplus.proadas.common.entry.DevicesInfo;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-23.
 */

public interface  IDevicesInfoView  extends BaseView{
     void success(DeviceInfo devicesInfo);
     void questFailure();

}
