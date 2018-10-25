package com.adasplus.proadas.business.main.presenter.fragment.about;

import android.text.TextUtils;

import com.adasplus.proadas.business.common.presenter.IActivationPresenter;
import com.adasplus.proadas.business.main.model.fragment.about.AboutModel;
import com.adasplus.proadas.business.main.model.fragment.about.IAboutModel;
import com.adasplus.proadas.business.main.model.fragment.common.CommonModel;
import com.adasplus.proadas.business.main.view.fragment.about.IAboutView;
import com.adasplus.proadas.business.main.view.fragment.common.ICommonView;
import com.adasplus.proadas.common.entry.CommonEntry;
import com.adasplus.proadas.common.entry.DevicesInfo;
import com.adasplus.proadas.common.model.BaseModel;
import com.adasplus.proadas.common.presenter.BasePresenterImpl;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public class AboutPresenter extends BasePresenterImpl implements  IAboutPresenter{

    private final IAboutView mView;
    private final IAboutModel mModel;
    public AboutPresenter(BaseView view) {
        super(view);
        mView = (IAboutView)getView();
        mModel = new AboutModel();
    }

    @Override
    public void onResume() {
      //  mView.showProgress();
        mModel.loadResourse(new BaseModel.Result<String>() {

            @Override
            public void success(String resultCallBack) {
             //   mView.hideProgress();
                if(resultCallBack!=null){
                    mView.success(resultCallBack,0);
                }else {
                    mView.failure();
                }
            }

            @Override
            public void failure(String s) {
              //  mView.hideProgress();
                mView.failure();
            }
        });
    }


    @Override
    public void checkoutVersion() {
        mModel.checkout(new IAboutModel.Result<String>() {
            @Override
            public void brfore(String s) {

            }

            @Override
            public void request() {

            }

            @Override
            public void success() {

            }

            @Override
            public void failue() {

            }
        });
    }

    @Override
    public void introduction() {
        mModel.introduction(new IAboutModel.Result<String>() {
            @Override
            public void brfore(String s) {

            }

            @Override
            public void request() {

            }

            @Override
            public void success() {

            }

            @Override
            public void failue() {

            }
        });
    }
}
