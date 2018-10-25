package com.adasplus.proadas.business.main.view.fragment.about;

import com.adasplus.proadas.common.entry.CommonEntry;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-23.
 */

public interface IAboutView  extends BaseView{
    /**
     *
     * @param commonEntry
     * @param code
     * 1.version
     * 2.checkout version from service
     * 3 troudle
     *
     * */
    void success(String commonEntry,int code);
    void failure();

}
