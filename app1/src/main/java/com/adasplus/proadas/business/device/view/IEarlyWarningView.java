package com.adasplus.proadas.business.device.view;

import com.adasplus.proadas.common.data.WarnParams;
import com.adasplus.proadas.common.entry.EarlyWarning;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-23.
 */

public interface IEarlyWarningView extends BaseView{
    void success(WarnParams adasConfig2, int code);
    void updateCode(String code);
    void showDialog(String s);
    void hideDialog();
}
