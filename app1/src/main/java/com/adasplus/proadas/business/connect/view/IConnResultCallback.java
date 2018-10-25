package com.adasplus.proadas.business.connect.view;

/**
 * Created by zhangyapeng on 18-3-26.
 */

import android.os.Bundle;

import com.adasplus.proadas.common.entry.WifiMessage;
import com.adasplus.proadas.common.view.BaseView;

//https://blog.csdn.net/leixiaohua1020/article/details/50534150

public interface IConnResultCallback extends BaseView{
    void imageCallBack(WifiMessage wifiInfo);
    void failueDialog();
    void finish();

}
