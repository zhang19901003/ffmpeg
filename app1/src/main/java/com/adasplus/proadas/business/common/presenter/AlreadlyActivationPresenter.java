package com.adasplus.proadas.business.common.presenter;

import com.adasplus.proadas.App;
import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.common.model.ActivationModel;
import com.adasplus.proadas.business.common.model.IActivationModel;
import com.adasplus.proadas.business.common.model.IUpdateModel;
import com.adasplus.proadas.business.common.model.UpdateModel;
import com.adasplus.proadas.business.common.view.IActivation;
import com.adasplus.proadas.business.common.view.IAlreadVIew;
import com.adasplus.proadas.common.data.Activation;
import com.adasplus.proadas.common.model.BaseModel;
import com.adasplus.proadas.common.presenter.BasePresenterImpl;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-5-4.
 */

public class AlreadlyActivationPresenter extends BasePresenterImpl {

    private final IAlreadVIew mView;
    private final IUpdateModel mModel;

    public AlreadlyActivationPresenter(BaseView view) {
        super(view);
        mView = (IAlreadVIew) getView();
        mModel = new UpdateModel();
    }

    @Override
    public void onResume() {
        mView.showProgress();
        mModel.loadResourse(new BaseModel.Result<Activation>() {

            @Override
            public void success(Activation resultCallBack) {
                mView.hideProgress();
                mView.success(resultCallBack);
            }

            @Override
            public void failure(String s) {
                mView.hideProgress();
                mView.questFailure();
            }
        });
    }

}
