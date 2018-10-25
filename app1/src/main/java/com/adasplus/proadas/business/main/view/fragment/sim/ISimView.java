package com.adasplus.proadas.business.main.view.fragment.sim;

import com.adasplus.proadas.common.entry.CommonEntry;
import com.adasplus.proadas.common.entry.SimInfo;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-23.
 */

public interface ISimView extends BaseView{
    void success(SimInfo simInfo);
    void failure();
}
