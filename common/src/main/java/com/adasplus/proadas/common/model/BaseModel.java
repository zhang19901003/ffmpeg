package com.adasplus.proadas.common.model;

/**
 * Created by zhangyapeng on 18-3-20.
 */

public interface BaseModel {

    void loadResourse (Result result);

    interface Result<T> {
        void success(T resultCallBack);
        void failure(String s);
    }

}
