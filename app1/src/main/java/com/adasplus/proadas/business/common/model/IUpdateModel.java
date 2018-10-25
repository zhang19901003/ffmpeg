package com.adasplus.proadas.business.common.model;

import com.adasplus.proadas.common.model.BaseModel;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public interface IUpdateModel extends BaseModel {

    void update(String bussion, String carNumber, String phoneNumber, Result result);
    interface Result <T>{
        void brfore(T t);
        void update();
        void success();
        void failue(String s);
    }
}
