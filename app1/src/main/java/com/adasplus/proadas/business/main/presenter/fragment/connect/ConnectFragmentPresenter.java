package com.adasplus.proadas.business.main.presenter.fragment.connect;

import com.adasplus.proadas.App;
import com.adasplus.proadas.business.main.model.activity.MainModel;
import com.adasplus.proadas.business.main.view.activity.ICheck;
import com.adasplus.proadas.business.main.view.fragment.connect.ICheckoutWifi;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.presenter.BasePresenterImpl;
import com.adasplus.proadas.common.util.preference.Prefs;
import com.adasplus.proadas.common.util.wifi.WifiManage;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-3.
 */

public class ConnectFragmentPresenter extends BasePresenterImpl implements IConnectFragmentPresenter{

    private final ICheckoutWifi mView;
    private MainModel mModel;
    public ConnectFragmentPresenter(BaseView view) {
        super(view);
        mView = (ICheckoutWifi) getView();
        mModel = new MainModel();
    }

    @Override
    public void onResume() {
        mView.isEnable(WifiManage.getInstance(App.getContext()).isWifiEnabled());
    }


    @Override
    public void tryAgain() {
        boolean wifiEnabled = WifiManage.getInstance(App.getContext()).isWifiEnabled();
        if(wifiEnabled){
            if(WifiManage.getInstance(App.getContext()).isCurrentWifiIsAdas(Constants.ADASPLUS)){
                mView.isCommonPage();
            }
        }
    }


    @Override
    public void isFrist() {
        mView.isFristResult(mModel.isFrist());
    }

    @Override
    public void setInit() {
        mModel.setInit();
    }
}
