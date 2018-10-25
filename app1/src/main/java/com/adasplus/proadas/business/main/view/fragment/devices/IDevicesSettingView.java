package com.adasplus.proadas.business.main.view.fragment.devices;

import com.adasplus.proadas.common.entry.EarlyWarning;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-23.
 */

public interface IDevicesSettingView extends BaseView{
    void success();
    void failure(String code);
    void showDialog();
    void hideDialog();
}
