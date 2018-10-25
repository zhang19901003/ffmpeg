package com.adasplus.proadas.business.device.view;

import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adasplus.proadas.App;
import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.connect.view.CheckWifiDialog;
import com.adasplus.proadas.business.connect.view.ConnectWifiDialog;
import com.adasplus.proadas.business.device.presenter.PicturePresenter;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.data.PointInfo;
import com.adasplus.proadas.common.view.activity.BaseActivity;
import com.adasplus.proadas.common.view.custom.LoadingDialog;
import com.adasplus.proadas.common.view.custom.SurfaceViewRadio;


/**
 * Created by zhangyapeng on 18-4-6.
 */

public class PictureActivity extends BaseActivity<PicturePresenter> implements SurfaceHolder.Callback, IPictureView  {

    private SurfaceViewRadio mSvRadio;
    private SurfaceHolder mHolder;
    private View mVwAuto;
    private LoadingDialog mLoading;

    private String mCalibrateInfo;
    private TextView mTvLoading;

    private Button mBtnSave;
    private Handler mMainHandler;

    @Override
    protected PicturePresenter createPresenter() {
        return new PicturePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_picture_video;
    }

    @Override
    protected void initView() {
        mCalibrateInfo = getIntent().getStringExtra(Constants.CALIBRATION);
        mMainHandler = new Handler(Looper.getMainLooper());
        TextView tvTitle = (TextView) findViewById(R.id.tv_main_title_textview);
        tvTitle.setText(getResources().getString(R.string.auto_setting));
        LinearLayout lvLeftView = findViewById(R.id.ll_left_layout);
        lvLeftView.setVisibility(View.VISIBLE);
        lvLeftView.setOnClickListener(this);
        mSvRadio = (SurfaceViewRadio) findViewById(R.id.sv_priview);
        mSvRadio.getHolder().addCallback(this);
        mBtnSave = (Button) findViewById(R.id.btn_save);
        mBtnSave.setEnabled(false);
        mBtnSave.setOnClickListener(this);
        mVwAuto = findViewById(R.id.vw_v_auto);
        mTvLoading = (TextView) findViewById(R.id.tv_loading);
    }

    @Override
    protected void initData() {
        mBasePresenter.initSuccess();
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_left_layout:
                finish();
                break;

            case R.id.btn_save:
                String[] split = mCalibrateInfo.split("_");
                mBasePresenter.acitvation(split[0], split[1], split[2], split[3], split[4], "0", "0", 0);
                break;
        }
    }

    @Override
    public SwipeRefreshLayout getLoadiView() {
        return null;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.mHolder = holder;
        mBasePresenter.onResume();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mBasePresenter.relese();
        mBasePresenter.disconnectTcp();
        mTvLoading.setVisibility(View.VISIBLE);

    }

    @Override
    public void connectSuccess() {
        mBasePresenter.priview(mHolder, "0");
    }

    @Override
    public void failure(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isShowDialog) {
                    ConnectWifiDialog activationDialog = new ConnectWifiDialog(PictureActivity.this, s, getResources().getString(R.string.confirm), getResources().getString(R.string.cancel), 0);
                    activationDialog.show();
                }
            }
        });

    }


    @Override
    public void calibrationSuccess() {
        if (isShowDialog) {
            CheckWifiDialog calibrationDialog = new CheckWifiDialog(PictureActivity.this, getResources().getString(R.string.saved));
            calibrationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    App.startMainActivity();
                }
            });
            calibrationDialog.show();
        }
    }

    @Override
    public void questSuccess(final PointInfo info) {

    }

    @Override
    public void showDialog() {
        mLoading = new LoadingDialog(PictureActivity.this);
        mLoading.setShowAnimation(true);
        mLoading.setString(getResources().getString(R.string.saving));
        mLoading.setImage(R.drawable.loading);
        mLoading.show();
    }

    @Override
    public void hideDialog() {

        if (mLoading != null) {
            mLoading.stopAnimal();
            mLoading.cancel();
            mLoading = null;
        }
    }

    @Override
    public void hideTextView() {
        if (isShowDialog) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    mTvLoading.setVisibility(View.INVISIBLE);
//                    mVwAuto.setVisibility(View.VISIBLE);
//                    mBtnSave.setEnabled(true);
//
//                }
//            });
            mMainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mTvLoading.setVisibility(View.INVISIBLE);
                    mVwAuto.setVisibility(View.VISIBLE);
                    mBtnSave.setEnabled(true);
                }
            },1000);
        }
    }

}

