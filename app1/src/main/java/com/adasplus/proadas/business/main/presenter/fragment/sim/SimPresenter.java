package com.adasplus.proadas.business.main.presenter.fragment.sim;

import com.adasplus.proadas.business.main.model.fragment.common.CommonModel;
import com.adasplus.proadas.business.main.model.fragment.sim.SimModel;
import com.adasplus.proadas.business.main.view.fragment.common.ICommonView;
import com.adasplus.proadas.business.main.view.fragment.sim.ISimView;
import com.adasplus.proadas.common.entry.CommonEntry;
import com.adasplus.proadas.common.entry.SimInfo;
import com.adasplus.proadas.common.model.BaseModel;
import com.adasplus.proadas.common.presenter.BasePresenterImpl;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public class SimPresenter extends BasePresenterImpl  {

    private final ISimView mView;
    private final BaseModel mModel;
    public SimPresenter(BaseView view) {
        super(view);
        mView = (ISimView)getView();
        mModel = new SimModel();
    }

    @Override
    public void onResume() {
        mView.showProgress();
        mModel.loadResourse(new BaseModel.Result<SimInfo>() {
            @Override
            public void success(SimInfo resultCallBack) {
                mView.hideProgress();
                mView.success(resultCallBack);
            }

            @Override
            public void failure(String s) {
                mView.hideProgress();
                mView.failure();
            }
        });
    }



}
