package com.adasplus.proadas.business.main.view.fragment.common;

import com.adasplus.proadas.common.data.DeviceState;
import com.adasplus.proadas.common.entry.CommonEntry;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-23.
 */

public interface ICommonView  extends BaseView{
    void questSuccess(DeviceState commonEntry);
    void failure(String s);
    void showDialog();
    void hideDialog();
    void adasRunning();
    void setSuccess(String s);
    void adasModel(String s);
    void adasModelFailure();
}
