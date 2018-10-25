package com.adasplus.proadas.business.main.model.fragment.devices;

import com.adasplus.proadas.common.model.BaseModel;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public interface IDevicesSettingModel extends BaseModel {

    void request(Result result);
    interface Result <T>{

        void update();
        void success();
        void failue(String s);
    }
}
