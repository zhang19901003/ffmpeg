package com.adasplus.proadas.business.device.presenter;

import android.util.Log;

import com.adasplus.proadas.App;
import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.device.model.CalibrationModel;
import com.adasplus.proadas.business.device.model.ICalibrationModel;
import com.adasplus.proadas.business.device.view.*;
import com.adasplus.proadas.common.data.CalibrateInfo;
import com.adasplus.proadas.common.entry.CalibrateParam;
import com.adasplus.proadas.common.model.BaseModel;
import com.adasplus.proadas.common.presenter.BasePresenterImpl;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public class CalibrationPresenter extends BasePresenterImpl implements ICalibrationPresenter {

    private final ICalibrationView mView;
    private final ICalibrationModel mModel;
    public CalibrationPresenter(BaseView view) {
        super(view);
        mView = (ICalibrationView)getView();
        mModel = new CalibrationModel();
    }

    @Override
    public void onResume() {
        mView.showProgress();
        mModel.loadResourse(new BaseModel.Result<CalibrateInfo>() {
            @Override
            public void success(CalibrateInfo resultCallBack) {
                mView.hideProgress();
                mView.success(resultCallBack,0);
            }
            @Override
            public void failure(String s) {
                mView.hideProgress();
                mView.updateCode(s);
            }
        });
    }


    @Override
    public void activationCallBack(String camera, String bumper, String left, String right, String calibrationHeight) {
        mModel.update(camera, bumper, left, right, calibrationHeight, new ICalibrationModel.Result<String>() {
            @Override
            public void brfore(String o) {
                mView.updateCode(o);

            }

            @Override
            public void update() {
                 mView.showDialog(App.getContext().getResources().getString(R.string.saving));
            }

            @Override
            public void success() {
                mView.hideDialog();
                mView.success(null ,1);
            }

            @Override
            public void failue() {
                mView.hideDialog();
                mView.updateCode(App.getContext().getResources().getString(R.string.calibration_fail));
            }
        });
    }

    @Override
    public void resetCalibration() {
        mModel.reset(new ICalibrationModel.Result<String>() {
            @Override
            public void brfore(String s) {

            }

            @Override
            public void update() {
                mView.showDialog(App.getContext().getResources().getString(R.string.saving));
            }

            @Override
            public void success() {
                mView.hideDialog();
                mView.success(null ,2);
            }

            @Override
            public void failue() {
                mView.hideDialog();
                mView.updateCode(App.getContext().getResources().getString(R.string.message_error));
            }
        });
    }

}
