package com.adasplus.proadas.business.main.presenter.activity;

import com.adasplus.proadas.business.main.model.activity.MainModel;
import com.adasplus.proadas.business.main.view.activity.ICheck;
import com.adasplus.proadas.common.model.BaseModel;
import com.adasplus.proadas.common.presenter.BasePresenterImpl;
import com.adasplus.proadas.common.view.BaseView;
import com.adasplus.proadas.mainlogic.welcome.presenter.IGuidePresenter;

/**
 * Created by zhangyapeng on 18-4-3.
 */

public class MainPresenter extends BasePresenterImpl{
    private MainModel mModel;
    private ICheck mView;

    public MainPresenter(BaseView view) {
        super(view);
        mModel = new MainModel();
        mView = (ICheck) getView();
    }

    @Override
    public void onResume() {
        mModel.loadResourse(new BaseModel.Result() {
            @Override
            public void success(Object resultCallBack) {
               mView.switchFragment(0,1,null);
            }

            @Override
            public void failure(String s) {
                mView.switchFragment(0,0,null);
            }
        });
    }

//    @Override
//    public void isFrist() {
//         mView.isFristResult(mModel.isFrist());
//    }
//
//    @Override
//    public void setInit() {
//        mModel.setInit();
//    }
}
