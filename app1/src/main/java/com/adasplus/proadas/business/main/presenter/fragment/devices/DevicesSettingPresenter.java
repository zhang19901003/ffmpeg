package com.adasplus.proadas.business.main.presenter.fragment.devices;

import com.adasplus.proadas.App;
import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.device.model.CalibrationModel;
import com.adasplus.proadas.business.device.model.ICalibrationModel;
import com.adasplus.proadas.business.device.presenter.ICalibrationPresenter;
import com.adasplus.proadas.business.device.view.ICalibrationView;
import com.adasplus.proadas.business.main.model.fragment.devices.DevicesSettingModel;
import com.adasplus.proadas.business.main.model.fragment.devices.IDevicesSettingModel;
import com.adasplus.proadas.business.main.view.fragment.devices.IDevicesSettingView;
import com.adasplus.proadas.common.entry.CalibrateParam;
import com.adasplus.proadas.common.model.BaseModel;
import com.adasplus.proadas.common.presenter.BasePresenterImpl;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public class DevicesSettingPresenter extends BasePresenterImpl implements IDevicesSettingPresenter {

    private final IDevicesSettingView mView;
    private final IDevicesSettingModel mModel;

    public DevicesSettingPresenter(BaseView view) {
        super(view);
        mView = (IDevicesSettingView) getView();
        mModel = new DevicesSettingModel();
    }

    @Override
    public void onResume() {

    }


    @Override
    public void requestStatus() {
        mModel.request(new IDevicesSettingModel.Result() {
            @Override
            public void update() {
                mView.showDialog();
            }

            @Override
            public void success() {
                mView.hideDialog();
                mView.success();
            }

            @Override
            public void failue(String s) {
                mView.hideDialog();
                mView.failure(s);

            }
        });
    }
}
