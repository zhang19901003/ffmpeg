package com.adasplus.proadas.business.device.presenter;

import android.util.Log;

import com.adasplus.proadas.App;
import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.device.model.EaryWarningModel;
import com.adasplus.proadas.business.device.model.IEarlyWarningModel;
import com.adasplus.proadas.business.device.view.IEarlyWarningView;
import com.adasplus.proadas.common.data.WarnParams;
import com.adasplus.proadas.common.entry.EarlyWarning;
import com.adasplus.proadas.common.model.BaseModel;
import com.adasplus.proadas.common.presenter.BasePresenterImpl;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public class EarlyWarningPresenter extends BasePresenterImpl implements IEarlyWarningPresenter {

    private final IEarlyWarningView mView;
    private final IEarlyWarningModel mModel;
    public EarlyWarningPresenter(BaseView view) {
        super(view);
        mView = (IEarlyWarningView)getView();
        mModel = new EaryWarningModel();
    }

    @Override
    public void onResume() {
        mView.showProgress();
        mModel.loadResourse(new BaseModel.Result<WarnParams>() {
            @Override
            public void success(WarnParams resultCallBack) {
                mView.hideProgress();
                mView.success(resultCallBack,0);
            }
            @Override
            public void failure(String  s) {
                mView.hideProgress();
                mView.updateCode(s);
            }
        });
    }

    @Override
    public void updateCallBack(WarnParams adasConfig2) {
        mModel.update(adasConfig2, new EaryWarningModel.Result<String>() {
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
                mView.success(null,1);
            }

            @Override
            public void failue(String s) {
                mView.hideDialog();
                mView.updateCode(s);
            }
        });
    }

    @Override
    public void resetWarnParms() {
        mModel.resetWarnParms(new EaryWarningModel.Result<String>() {
            @Override
            public void brfore(String s) {

            }

            @Override
            public void update() {
                mView.showDialog(App.getContext().getResources().getString(R.string.reset_warning));
            }

            @Override
            public void success() {
                mModel.loadResourse(new BaseModel.Result<WarnParams>() {
                    @Override
                    public void success(WarnParams resultCallBack) {
                        mView.hideDialog();
                        mView.success(resultCallBack,2);
                    }
                    @Override
                    public void failure(String s) {
                        mView.hideDialog();
                        mView.updateCode(s);
                    }
                });
            }

            @Override
            public void failue(String s) {
                mView.hideDialog();
                mView.updateCode(s);
            }
        });
    }


}
