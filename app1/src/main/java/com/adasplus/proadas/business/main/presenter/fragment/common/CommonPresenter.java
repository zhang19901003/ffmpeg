package com.adasplus.proadas.business.main.presenter.fragment.common;

import com.adasplus.proadas.App;
import com.adasplus.proadas.business.main.model.activity.MainModel;
import com.adasplus.proadas.business.main.model.fragment.common.CommonModel;
import com.adasplus.proadas.business.main.model.fragment.common.ICommonModel;
import com.adasplus.proadas.business.main.view.fragment.common.ICommonView;
import com.adasplus.proadas.common.data.DeviceState;
import com.adasplus.proadas.common.data.Model;
import com.adasplus.proadas.common.model.BaseModel;
import com.adasplus.proadas.common.presenter.BasePresenterImpl;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public class CommonPresenter extends BasePresenterImpl implements ICommonPresenter {

    private final ICommonView mView;
    private final ICommonModel mModel;

    public CommonPresenter(BaseView view) {
        super(view);
        mView = (ICommonView) getView();
        mModel = new CommonModel();
    }

    @Override
    public void onResume() {
        mView.showProgress();
        mModel.loadResourse(new BaseModel.Result<DeviceState>() {


            @Override
            public void success(DeviceState resultCallBack) {
                mView.hideProgress();
                mView.questSuccess(resultCallBack);
            }

            @Override
            public void failure(String s) {
                mView.hideProgress();
                mView.failure(s);
            }
        });
    }


    @Override
    public void resumeFactory() {
        mView.showDialog();
        mModel.resumeFactory(new BaseModel.Result() {
            @Override
            public void success(Object resultCallBack) {
                mView.hideDialog();
                mView.setSuccess(App.getContext().getResources().getString(com.adasplus.proadas.common.R.string.reset_factory));
            }

            @Override
            public void failure(String s) {
                mView.hideDialog();
                mView.failure(s);
            }
        });
    }

    @Override
    public void enableGps(String s) {
        mView.showDialog();
        mModel.setGpsEnable(s, new BaseModel.Result() {

            @Override
            public void success(Object resultCallBack) {

                mModel.loadResourse(new BaseModel.Result<DeviceState>() {

                    @Override
                    public void success(DeviceState resultCallBack) {
                        mView.hideDialog();
                        mView.questSuccess(resultCallBack);
                    }

                    @Override
                    public void failure(String s) {
                        mView.hideDialog();
                        mView.failure(s);
                    }
                });
            }

            @Override
            public void failure(String s) {
                mView.hideDialog();
                mView.failure(s);
            }
        });
    }

    @Override
    public void adasRunning() {
        mView.showDialog();
        mModel.adasRunnning(new BaseModel.Result() {
            @Override
            public void success(Object resultCallBack) {
                mView.hideDialog();
                mView.adasRunning();
            }

            @Override
            public void failure(String s) {
                mView.hideDialog();
                mView.failure(s);
            }
        });
    }

    @Override
    public void enableModel(String s) {
        mView.showDialog();

        mModel.setModelEnable(s, new BaseModel.Result<String>() {
            @Override
            public void success(String resultCallBack) {
                mView.hideDialog();
                mView.toast(resultCallBack);

            }

            @Override
            public void failure(String s) {
                mView.hideDialog();
                mView.toast(s);
                mView.adasModelFailure();
            }
        });

    }

    @Override
    public void getModel() {
        mView.showDialog();
        mModel.getModelEnable(new BaseModel.Result<Model>() {
            @Override
            public void success(Model resultCallBack) {
                mView.hideDialog();
                mView.adasModel(resultCallBack.getState());
            }

            @Override
            public void failure(String s) {
                mView.hideDialog();

            }
        });
    }

    @Override
    public void playSound() {

        mModel.playSound(new BaseModel.Result<String>() {
            @Override
            public void success(String resultCallBack) {
                mView.toast("播放成功");
            }

            @Override
            public void failure(String s) {
                mView.toast("播放失败");
            }
        });
    }
}
