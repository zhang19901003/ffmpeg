package com.adasplus.proadas.business.common.view;

import com.adasplus.proadas.common.data.VoiceSetting;
import com.adasplus.proadas.common.entry.CalibrateParam;
import com.adasplus.proadas.common.entry.VoiceInfo;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public interface IVoiceSettingView extends BaseView {
     void updateCode(String code);
     void success(VoiceSetting calibrateParam, int code);
     void showDialog();
     void hideDialog();
}
