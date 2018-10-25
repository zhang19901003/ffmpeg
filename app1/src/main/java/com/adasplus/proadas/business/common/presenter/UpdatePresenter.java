package com.adasplus.proadas.business.common.presenter;

import com.adasplus.proadas.App;
import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.common.model.IUpdateModel;
import com.adasplus.proadas.business.common.model.UpdateModel;
import com.adasplus.proadas.business.common.view.IUpdateVIew;
import com.adasplus.proadas.common.data.Activation;
import com.adasplus.proadas.common.entry.DevicesInfo;
import com.adasplus.proadas.common.model.BaseModel;
import com.adasplus.proadas.common.presenter.BasePresenterImpl;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public class UpdatePresenter extends BasePresenterImpl implements IUpdatePresenter {

    private final IUpdateVIew mView;
    private final IUpdateModel mModel;

    public UpdatePresenter(BaseView view) {
        super(view);
        mView = (IUpdateVIew) getView();
        mModel = new UpdateModel();
    }

    @Override
    public void onResume() {
        mView.showProgress();
        mModel.loadResourse(new BaseModel.Result<Activation>() {

            @Override
            public void success(Activation resultCallBack) {
                mView.hideProgress();
                mView.updateSuccess(resultCallBack);
            }

            @Override
            public void failure(String s) {
                mView.hideProgress();
                mView.updateCode(s);
            }
        });
    }

    @Override
    public void updateInfoCallBack(String bussion, String car, String phone) {
        mModel.update(bussion, car, phone, new IUpdateModel.Result<String>() {
            @Override
            public void brfore(String s) {
                mView.updateCode(s);
            }

            @Override
            public void update() {
                mView.showDialog();
            }


            @Override
            public void success() {

                mView.hideDialog();
                mView.updateSuccess();
            }

            @Override
            public void failue(String s) {
                mView.hideDialog();
                mView.updateCode(s);
            }
        });
    }
}
