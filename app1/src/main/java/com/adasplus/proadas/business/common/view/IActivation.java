package com.adasplus.proadas.business.common.view;

import com.adasplus.proadas.common.data.Activation;
import com.adasplus.proadas.common.entry.DeviceSpace;
import com.adasplus.proadas.common.entry.DevicesInfo;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public interface IActivation extends BaseView{

    void registerCode(String code);
    void show (String car,String phone);
    void success(Activation activation);
    void showDialog();
    void hideDialog();
}
