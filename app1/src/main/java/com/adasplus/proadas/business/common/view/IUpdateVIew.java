package com.adasplus.proadas.business.common.view;

import com.adasplus.proadas.common.data.Activation;
import com.adasplus.proadas.common.entry.DevicesInfo;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-11.
 */

public interface IUpdateVIew extends BaseView{
    void updateSuccess(Activation devicesInfo);
    void updateCode(String code);
    void updateSuccess();
    void showDialog();
    void hideDialog();
}
