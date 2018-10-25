package com.adasplus.proadas.business.device.presenter;

import com.adasplus.proadas.App;
import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.device.model.CalibrationModel;
import com.adasplus.proadas.business.device.model.DevicesInfoModel;
import com.adasplus.proadas.business.device.model.ICalibrationModel;
import com.adasplus.proadas.business.device.view.DevicesInfoActivity;
import com.adasplus.proadas.business.device.view.ICalibrationView;
import com.adasplus.proadas.business.device.view.IDevicesInfoView;
import com.adasplus.proadas.common.data.DeviceInfo;
import com.adasplus.proadas.common.entry.CalibrateParam;
import com.adasplus.proadas.common.entry.DevicesInfo;
import com.adasplus.proadas.common.model.BaseModel;
import com.adasplus.proadas.common.presenter.BasePresenterImpl;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public class DevicesInfoPresenter extends BasePresenterImpl  {

    private final IDevicesInfoView mView;
    private final BaseModel mModel;
    public DevicesInfoPresenter(BaseView view) {
        super(view);
        mView = (IDevicesInfoView)getView();
        mModel = new DevicesInfoModel();
    }

    @Override
    public void onResume() {
        mView.showProgress();
        mModel.loadResourse(new BaseModel.Result<DeviceInfo>() {


            @Override
            public void success(DeviceInfo resultCallBack) {
                mView.hideProgress();
                if(resultCallBack==null){
                    mView.questFailure();
                }else {
                    mView.success(resultCallBack);
                }

            }

            @Override
            public void failure(String s) {
                mView.hideProgress();
                mView.questFailure();
            }
        });
    }



}
