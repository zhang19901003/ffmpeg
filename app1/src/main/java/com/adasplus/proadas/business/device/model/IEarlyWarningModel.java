package com.adasplus.proadas.business.device.model;

import com.adasplus.proadas.common.data.WarnParams;
import com.adasplus.proadas.common.entry.EarlyWarning;
import com.adasplus.proadas.common.model.BaseModel;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public interface IEarlyWarningModel extends BaseModel {

    void update(WarnParams adasConfig2, Result result);
    interface Result <T>{
        void brfore(T t);
        void update();
        void success();
        void failue(String s);
    }
    void resetWarnParms(Result result);
}
