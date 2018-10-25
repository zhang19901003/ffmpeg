package com.adasplus.proadas.business.device.presenter;

import android.util.Log;
import android.view.SurfaceHolder;

import com.adasplus.proadas.business.device.view.IPictureView;
import com.adasplus.proadas.common.data.PointInfo;
import com.adasplus.proadas.common.model.BaseModel;
import com.adasplus.proadas.common.presenter.BasePresenterImpl;
import com.adasplus.proadas.common.view.BaseView;
import com.adasplus.proadas.socket.tcp.IPictureModel;
import com.adasplus.proadas.socket.tcp.PictureModel;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public class PicturePresenter extends BasePresenterImpl implements IPicturePresenter {

    private final IPictureView mView;
    private final IPictureModel mModel;
    public PicturePresenter(BaseView view) {
        super(view);
        mView = (IPictureView) getView();
        mModel = new  PictureModel();
    }

    @Override
    public void onResume() {

        mModel.loadResourse(new BaseModel.Result<byte[]>() {
            @Override
            public void success(byte[] resultCallBack) {
                mView.connectSuccess();
            }

            @Override
            public void failure(String s) {
                Log.e("Adas","csasasasa");
                mView.failure(s);
            }
        });
    }

    @Override
    public void priview(SurfaceHolder surfaceHolder, String cameraId) {
        mModel.priview(surfaceHolder, new BaseModel.Result<String>() {
            @Override
            public void success(String resultCallBack) {

            }

            @Override
            public void failure(String s) {
                Log.e("Adas","csasasasasasas");
                mView.failure(s);
            }
        }, cameraId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }



    @Override
    public void disconnectTcp() {
        mModel.disconnectTcp();
    }

    @Override
    public void questPoint() {
        mView.showProgress();
        mModel.questPoint(new BaseModel.Result<PointInfo>() {
            @Override
            public void success(PointInfo resultCallBack) {
                mView.hideProgress();
                mView.questSuccess(resultCallBack);
            }

            @Override
            public void failure(String s) {
                mView.hideProgress();
                mView.failure(s);
            }
        });
    }


    @Override
    public void acitvation(String camera, String bumper, String left, String right, String calibrationHeight, String x, String y, int flag) {

        mModel.handAcitvation(camera,bumper,left,right,calibrationHeight, x,y,flag,new IPictureModel.Result<String>() {
            @Override
            public void brfore(String s) {
                mView.failure(s);
            }

            @Override
            public void update() {
                mView.showDialog();
            }

            @Override
            public void success() {
                mView.hideDialog();
                mView.calibrationSuccess();
            }

            @Override
            public void failue(String s) {
                mView.hideDialog();
                mView.failure(s);
            }
        });
    }

    @Override
    public void initSuccess() {
        mModel.initSuccess(new BaseModel.Result() {
            @Override
            public void success(Object resultCallBack) {
                mView.hideTextView();
            }

            @Override
            public void failure(String s) {

            }
        });
    }

    @Override
    public void relese() {
        mModel.relese();
    }

}
