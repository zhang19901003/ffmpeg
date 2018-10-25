package com.adasplus.proadas.business.connect.presenter;

import android.util.Log;

import com.adasplus.proadas.business.connect.view.IConnResultCallback;
import com.adasplus.proadas.socket.tcp.TcpClient;
import com.adasplus.proadas.common.entry.WifiMessage;
import com.adasplus.proadas.common.model.BaseModel;
import com.adasplus.proadas.common.presenter.BasePresenterImpl;
import com.adasplus.proadas.common.view.BaseView;
import com.adasplus.proadas.business.connect.model.IConnModelImpl;
import com.adasplus.proadas.business.connect.model.NetConnModel;


/**
 * Created by zhangyapeng on 18-3-26.
 */

public class ConnPresenter extends BasePresenterImpl implements IConnectPresenter {

    private IConnModelImpl mModel;
    private IConnResultCallback mView;


    public ConnPresenter(BaseView view) {
        super(view);
        this.mModel = new NetConnModel();
        this.mView = (IConnResultCallback) getView();

    }

    @Override
    public void connect(WifiMessage mWifiInfo, String passWord) {
        this.mView.showProgress();

        try {
            this.mModel.connect(mWifiInfo, passWord, new IConnModelImpl.Result<String>() {
                @Override
                public void success(String resultCallBack) {
                    mView.hideProgress();
                    mView.finish();
                }
                @Override
                public void failure(String s) {
                    mView.hideProgress();
                    mView.failueDialog();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
            mView.hideProgress();
            mView.failueDialog();
        }
    }

    @Override
    public void disConnect() {
        mView.hideProgress();
        mModel.disConnect();
    }

    @Override
    public void onResume() {
        this.mModel.loadResourse(null);
    }


}
