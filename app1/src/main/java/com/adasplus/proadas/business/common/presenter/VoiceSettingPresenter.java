package com.adasplus.proadas.business.common.presenter;

import android.util.Log;

import com.adasplus.proadas.App;
import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.common.model.IVoiceSettingModel;
import com.adasplus.proadas.business.common.model.VoiceSettingModel;
import com.adasplus.proadas.business.common.view.IVoiceSettingView;
import com.adasplus.proadas.business.device.model.CalibrationModel;
import com.adasplus.proadas.business.device.model.ICalibrationModel;
import com.adasplus.proadas.business.device.presenter.ICalibrationPresenter;
import com.adasplus.proadas.business.device.view.ICalibrationView;
import com.adasplus.proadas.common.data.VoiceSetting;
import com.adasplus.proadas.common.entry.CalibrateParam;
import com.adasplus.proadas.common.entry.VoiceInfo;
import com.adasplus.proadas.common.model.BaseModel;
import com.adasplus.proadas.common.presenter.BasePresenterImpl;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public class VoiceSettingPresenter extends BasePresenterImpl implements IVoicePresenter {

    private final IVoiceSettingView mView;
    private final IVoiceSettingModel mModel;

    public VoiceSettingPresenter(BaseView view) {
        super(view);
        mView = (IVoiceSettingView) getView();
        mModel = new VoiceSettingModel();
    }

    @Override
    public void onResume() {
        mView.showProgress();
        mModel.loadResourse(new BaseModel.Result<VoiceSetting>() {
            @Override
            public void success(VoiceSetting resultCallBack) {
                mView.hideProgress();
                mView.success(resultCallBack, 0);
            }

            @Override
            public void failure(String s) {
                mView.hideProgress();
                mView.updateCode(s);
            }
        });
    }


    @Override
    public void setVoice(String type, String size) {
        mModel.setVoice(type, size, new IVoiceSettingModel.Result<String>() {

            @Override
            public void update() {
                mView.showDialog();
            }

            @Override
            public void success() {
                mView.hideDialog();
                mView.success(null, 1);
            }

            @Override
            public void failue(String s) {
                mView.hideDialog();
                mView.updateCode(s);
            }
        });
    }

    @Override
    public void setSeekBarVoice(String type, String size) {
        mModel.setVoice(type, size, new IVoiceSettingModel.Result<String>() {

            @Override
            public void update() {
            }

            @Override
            public void success() {
            }

            @Override
            public void failue(String s) {
                mView.updateCode(s);
            }
        });
    }
}
