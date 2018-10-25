package com.adasplus.proadas.business.main.model.fragment.about;

import com.adasplus.proadas.common.model.BaseModel;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public interface IAboutModel extends BaseModel {

    void checkout(Result result);
    void introduction(Result result);
    interface Result <T>{
        void brfore(T t);
        void request();
        void success();
        void failue();
    }
}
