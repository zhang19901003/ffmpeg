package com.adasplus.proadas.business.common.presenter;

import android.os.Handler;
import android.os.HandlerThread;
import android.view.SurfaceHolder;

import com.adasplus.proadas.socket.tcp.IPriviewModel;
import com.adasplus.proadas.socket.tcp.PriviewSettingModel;
import com.adasplus.proadas.business.common.view.IPriviewView;
import com.adasplus.proadas.common.model.BaseModel;
import com.adasplus.proadas.common.presenter.BasePresenterImpl;
import com.adasplus.proadas.common.view.BaseView;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public class PriviewPresenter extends BasePresenterImpl implements IPriviewPresenter {

    private final IPriviewView mView;
    private final IPriviewModel mModel;
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    public PriviewPresenter(BaseView view) {
        super(view);
        mView = (IPriviewView) getView();
        mModel = new PriviewSettingModel();
    }

    @Override
    public void onResume() {

        mHandlerThread = new HandlerThread("inint");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());

        mModel.loadResourse(new BaseModel.Result<byte[]>() {
            @Override
            public void success(byte[] resultCallBack) {
                mView.connectSuccess();
            }

            @Override
            public void failure(String s) {
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
                mView.failure(s);
            }
        }, cameraId);
    }

    @Override
    public void initHolder(final BaseModel.Result result) {

//        if(mView.getHolder() == null){
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if(mView.getHolder() == null){
//                        mHandler.postDelayed(this,40);
//                    }else {
//                        result.success(null);
//                    }
//                }
//            },40);
//        }else {
//            result.success(null);
//        }
//        mModel.initHolder(holder, new BaseModel.Result<Object>() {
//            @Override
//            public void success(Object resultCallBack) {
//                    result.success(null);
//            }
//
//            @Override
//            public void failure(String s) {
//
//            }
//        });
    }

    @Override
    public void disconnectTcp() {
        mModel.disconnectTcp();
    }

    @Override
    public void selectCameraId(int cameraId) {
        mModel.selectCameraId(cameraId);
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
