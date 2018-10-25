package com.adasplus.proadas.common.util.net;

/**
 * Created by zhangyapeng on 18-3-20.
 */

public interface IReponseListener <T> {
    void onSuccess(String t);

    void onFail(String errMsg);


}

