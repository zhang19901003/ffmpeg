package com.adasplus.proadas.business.common.model;

import com.adasplus.proadas.common.model.BaseModel;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public interface ISaveSpaceModel extends BaseModel {

    void format(Result result);

    interface Result<T> {
        void update();

        void success();

        void failue(String s);
    }
}
