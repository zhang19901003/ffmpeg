package com.adasplus.proadas.business.common.presenter;

import com.adasplus.proadas.App;
import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.common.model.ActivationModel;
import com.adasplus.proadas.business.common.model.IActivationModel;
import com.adasplus.proadas.business.common.view.IActivation;

import com.adasplus.proadas.common.data.Activation;
import com.adasplus.proadas.common.entry.DevicesInfo;
import com.adasplus.proadas.common.model.BaseModel;
import com.adasplus.proadas.common.presenter.BasePresenterImpl;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public class ActivationPresenter extends BasePresenterImpl implements IActivationPresenter{

    private final IActivation mView;
    private final IActivationModel mModel;
    public ActivationPresenter(BaseView view) {
        super(view);
        mView = (IActivation)getView();
        mModel = new ActivationModel();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void activationCallBack(String bussion, String car, String phone) {
        mModel.register(bussion, car, phone, new  IActivationModel.Result<Activation>() {
            @Override
            public void brfore(String s) {
                mView.registerCode(s);
            }

            @Override
            public void register() {
                mView.showDialog();
            }

            @Override
            public void success(Activation activation) {
                mView.hideDialog();
                mView.success(activation);
            }

            @Override
            public void failue(String s) {
                mView.hideDialog();
                mView.registerCode(s);
            }
        });
    }
}
