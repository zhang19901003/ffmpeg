package com.adasplus.proadas.business.main.view.activity;

import android.os.Bundle;

import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-3.
 */

public interface ICheck extends BaseView {
    void switchFragment(int from, int to, Bundle argument);
}
