package com.adasplus.proadas.business.common.view;

import com.adasplus.proadas.common.data.SdcardInfo;
import com.adasplus.proadas.common.entry.CalibrateParam;
import com.adasplus.proadas.common.entry.DeviceSpace;
import com.adasplus.proadas.common.model.BaseModel;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public interface ISaveSpaceView extends BaseView {

    void updateCode (String code);
    void success(SdcardInfo deviceSpace, int code);

}
