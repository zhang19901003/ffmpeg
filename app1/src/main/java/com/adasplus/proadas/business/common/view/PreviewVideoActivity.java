package com.adasplus.proadas.business.common.view;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.common.presenter.PriviewPresenter;
import com.adasplus.proadas.business.connect.view.ConnectWifiDialog;
import com.adasplus.proadas.business.device.view.HelpActivity;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.view.activity.BaseActivity;
import com.adasplus.proadas.common.view.custom.LoadingDialog;
import com.adasplus.proadas.common.view.custom.SurfaceViewRadio;
import com.adasplus.proadas.common.view.custom.VideoRadioButton;

/**
 * Created by zhangyapeng on 18-4-8.
 */

public class PreviewVideoActivity extends BaseActivity<PriviewPresenter> implements SurfaceHolder.Callback, IPriviewView, CompoundButton.OnCheckedChangeListener {

    private SurfaceViewRadio mSvRadio;
    private SurfaceHolder mHolder;
    private VideoRadioButton mVbAu;
    private VideoRadioButton mVbHand;
    private TextView mTvLoading;
    private Handler mMainHandler;
    private String mCameraId = "0";
    private LoadingDialog mLoading;
    private RelativeLayout mRv;

    @Override
    protected PriviewPresenter createPresenter() {
        return new PriviewPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_previewing_video;
    }

    @Override
    protected void initView() {

        TextView tvTitle = (TextView) findViewById(R.id.tv_main_title_textview);
        tvTitle.setText(getResources().getString(R.string.preview_video));
        LinearLayout lvLeftView = findViewById(R.id.ll_left_layout);
        lvLeftView.setVisibility(View.VISIBLE);
        lvLeftView.setOnClickListener(this);
        mVbAu = (VideoRadioButton) findViewById(R.id.vb_au);
        mVbAu.setChecked(true);
        mVbAu.setOnCheckedChangeListener(this);
        mVbHand = (VideoRadioButton) findViewById(R.id.vb_hand);
        mVbHand.setOnCheckedChangeListener(this);
        mSvRadio = (SurfaceViewRadio) findViewById(R.id.sv_priview);
        mSvRadio.getHolder().addCallback(this);
        findViewById(R.id.rv_use_help).setOnClickListener(this);
        mTvLoading = (TextView) findViewById(R.id.tv_loading);
        mMainHandler = new Handler(Looper.getMainLooper());
        mRv = (RelativeLayout)findViewById(R.id.scan_window);
        mRv.setVisibility(View.INVISIBLE);
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
            case R.id.rv_use_help:
                Intent intent = new Intent(this, HelpActivity.class);
                intent.putExtra(Constants.HELP, R.layout.activity_priview);
                startActivity(intent);
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
        hideDialog();


    }

    @Override
    public void connectSuccess() {
        mBasePresenter.priview(mHolder, mCameraId);
    }

    @Override
    public void failure(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isShowDialog) {
                    ConnectWifiDialog activationDialog = new ConnectWifiDialog(PreviewVideoActivity.this, s, getResources().getString(R.string.confirm), getResources().getString(R.string.cancel), 0);
                    activationDialog.show();

                }
            }
        });

    }

    @Override
    public SurfaceHolder getHolder() {
        return mHolder;
    }

    @Override
    public void hideTextView() {
        if (isShowDialog) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            });
            mMainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mTvLoading.setVisibility(View.INVISIBLE);
                }
            },1000);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.vb_au:
                if (mVbAu.isChecked()) {
                    return;
                }
                showDialog();
             //   mVbAu.setEnabled(false);
                mMainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                      //  mVbAu.setEnabled(true);
                    hideDialog();
                        mRv.setVisibility(View.VISIBLE);
                    }
                }, 2000);
                mBasePresenter.selectCameraId(1);
                mCameraId = "1";
                break;
            case R.id.vb_hand:
                if (mVbHand.isChecked()) {
                    return;
                }
                showDialog();
             //   mVbHand.setEnabled(false);
                mMainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                     //   mVbHand.setEnabled(true);
                        hideDialog();

                    }
                }, 2000);
                mRv.setVisibility(View.INVISIBLE);
                mBasePresenter.selectCameraId(0);
                mCameraId = "0";
                break;
        }
    }


    public void showDialog() {
                if(!isShowDialog){
                    return;
                }
                mLoading = new LoadingDialog(PreviewVideoActivity.this);
                mLoading.setShowAnimation(true);
                mLoading.setString(getResources().getString(R.string.change));
                mLoading.setImage(R.drawable.loading);
                mLoading.show();

    }


    public void hideDialog() {
                if(mLoading !=null){
                    mLoading.stopAnimal();
                    mLoading.cancel();
                    mLoading=null;
                }
    }
}

