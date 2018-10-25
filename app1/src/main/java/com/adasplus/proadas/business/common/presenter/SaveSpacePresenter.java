package com.adasplus.proadas.business.common.presenter;

import com.adasplus.proadas.App;
import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.common.model.ISaveSpaceModel;
import com.adasplus.proadas.business.common.model.SaveSpaceModel;
import com.adasplus.proadas.business.common.view.ISaveSpaceView;
import com.adasplus.proadas.business.device.model.CalibrationModel;
import com.adasplus.proadas.business.device.model.ICalibrationModel;
import com.adasplus.proadas.business.device.presenter.ICalibrationPresenter;
import com.adasplus.proadas.business.device.view.ICalibrationView;
import com.adasplus.proadas.common.data.SdcardInfo;
import com.adasplus.proadas.common.entry.CalibrateParam;
import com.adasplus.proadas.common.entry.DeviceSpace;
import com.adasplus.proadas.common.model.BaseModel;
import com.adasplus.proadas.common.presenter.BasePresenterImpl;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public class SaveSpacePresenter extends BasePresenterImpl implements ISaveSpacePresenter {

    private final ISaveSpaceView mView;
    private final ISaveSpaceModel mModel;
    public SaveSpacePresenter(BaseView view) {
        super(view);
        mView = (ISaveSpaceView)getView();
        mModel = new SaveSpaceModel();
    }

    @Override
    public void onResume() {
        mView.showProgress();
        mModel.loadResourse(new BaseModel.Result<SdcardInfo>() {
            @Override
            public void success(SdcardInfo resultCallBack) {
                mView.hideProgress();
                if(null != resultCallBack){
                    mView.success(resultCallBack,0);
                }else {
                    mView.success(null ,2);
                }

            }
            @Override
            public void failure(String s) {
                mView.hideProgress();
                mView.updateCode(s);
            }
        });
    }


    @Override
    public void format( ) {

        mModel.format(new ISaveSpaceModel.Result<String>() {

            @Override
            public void update() {
                mView.showProgress();
            }

            @Override
            public void success() {
                mModel.loadResourse(new BaseModel.Result<SdcardInfo>() {
                    @Override
                    public void success(SdcardInfo resultCallBack) {
                        mView.hideProgress();
                        mView.success(null,1);
//                        try {
//                            Thread.sleep(5000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        mView.hideProgress();
//                        if(null != resultCallBack){
//                            mView.success(resultCallBack,0);
//
//                        }else {
//                            mView.success(null ,2);
//                        }
                    }
                    @Override
                    public void failure(String s) {
                        mView.hideProgress();
                        mView.updateCode(s);
                    }
                });
            }

            @Override
            public void failue(String s) {
                mView.hideProgress();
                mView.updateCode(s);
            }
        });
    }
}
