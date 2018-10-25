package com.adasplus.proadas.business.connect.presenter;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.Build;

import com.adasplus.proadas.common.model.BaseModel;
import com.adasplus.proadas.common.presenter.BasePresenterImpl;
import com.adasplus.proadas.common.view.BaseView;
import com.adasplus.proadas.business.connect.model.ICheckModelImpl;
import com.adasplus.proadas.business.connect.model.NetCheckModel;
import com.adasplus.proadas.business.connect.view.ICheckResultCallback;

import java.util.List;

/**
 * Created by zhangyapeng on 18-3-21.
 */

public class CheckPresenter extends BasePresenterImpl implements ICheckPresenter {

    private final ICheckModelImpl mModel;
    private final ICheckResultCallback mView;

    public CheckPresenter(BaseView view) {
        super(view);
        mModel = new NetCheckModel();
        mView = (ICheckResultCallback) getView();
    }

    @Override
    public void onResume() {

        mModel.loadResourse(new ICheckModelImpl.Result<List<ScanResult>>() {
            @Override
            public void success(List<ScanResult> resultCallBack) {
                mView.imageCallBack(resultCallBack);
            }

            @Override
            public void failure() {

            }

            @Override
            public void connectList(WifiInfo scanResult) {
                mView.currentConnectWifi(scanResult);
            }
        });

        mModel.showSuccessDialog(new BaseModel.Result<Object>() {
            @Override
            public void success(Object resultCallBack) {
                mView.showDialog();
            }

            @Override
            public void failure(String s) {

            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mModel.showGpsInfo(new BaseModel.Result<Object>() {
                @Override
                public void success(Object resultCallBack) {

                }

                @Override
                public void failure(String s) {
                    mView.showGpsInfo();
                }
            });
        }

    }

    @Override
    public void onPause() {
        mModel.cancel();
    }


//    @Override
//    public void chooseWifi(int position) {
//
//         mModel.loadScanResult(position, new BaseModel.Result<Bundle>() {
//
//             @Override
//             public void updateSuccess(Bundle resultCallBack) {
//                  mView.switchFragment(0,1, resultCallBack);
//             }
//
//             @Override
//             public void failure() {
//
//             }
//         });
//    }
}
