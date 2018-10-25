package com.adasplus.proadas.business.device.view;

import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
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

public class HandlerCalibrationActivity extends BaseActivity<PicturePresenter> implements SurfaceHolder.Callback, IPictureView, View.OnTouchListener {

    private SurfaceViewRadio mSvRadio;
    private SurfaceHolder mHolder;
    private int mWidth, mHeight;
    private View mVwV;
    private View mVwH;
    private static volatile Rect mLandRect;
    private static volatile Rect mPortRect;
    private boolean isLand;
    private boolean isPort;
    private float mStartX;
    private float mStartY;
    private int mBackX = -1;
    private int mBackY = -1;
    private float mViewHoriPosition, mViewVertiPosition;
    private LoadingDialog mLoading;
    private String mCalibrateInfo;
    private TextView mTvLoading;
    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;
    private Button mBtnSave;
    private Handler mMainHandler;
    @Override
    protected PicturePresenter createPresenter() {
        return new PicturePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hand_video;
    }

    @Override
    protected void initView() {
        mCalibrateInfo = getIntent().getStringExtra(Constants.CALIBRATION);
        mMainHandler = new Handler(Looper.getMainLooper());
        TextView tvTitle = (TextView) findViewById(R.id.tv_main_title_textview);
        tvTitle.setText(getResources().getString(R.string.hand_setting));
        LinearLayout lvLeftView = findViewById(R.id.ll_left_layout);
        lvLeftView.setVisibility(View.VISIBLE);
        lvLeftView.setOnClickListener(this);
        mSvRadio = (SurfaceViewRadio) findViewById(R.id.sv_priview);
        mSvRadio.getHolder().addCallback(this);
        mLandRect = new Rect();
        mPortRect = new Rect();
        ViewTreeObserver viewTreeObserver = mSvRadio.getViewTreeObserver();
        onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                mWidth = mSvRadio.getMeasuredWidth();
                mHeight = mSvRadio.getMeasuredHeight();
                mBasePresenter.questPoint();
                mPortRect.set((int) mBackX - 30, 0, (int) mBackX + 30, mHeight);
                mLandRect.set(0, (int) mBackY - 30, mWidth, (int) mBackY + 30);

            }
        };
        viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener);
        mVwV = findViewById(R.id.vw_v);
        mVwH = findViewById(R.id.vw_h);
        mSvRadio.setOnTouchListener(this);
        mBtnSave = (Button) findViewById(R.id.btn_save);
        mBtnSave.setEnabled(false);
        mBtnSave.setOnClickListener(this);
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
                mBasePresenter.acitvation(split[0], split[1], split[2], split[3], split[4], String.valueOf((mBackX / (mWidth / 640.0f))), String.valueOf((mBackY / (mHeight / 480.0f))), 1);
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
                    ConnectWifiDialog activationDialog = new ConnectWifiDialog(HandlerCalibrationActivity.this, s, getResources().getString(R.string.confirm), getResources().getString(R.string.cancel), 0);
                    activationDialog.show();
                }
            }
        });

    }


    @Override
    public void calibrationSuccess() {
        if (isShowDialog) {
            CheckWifiDialog calibrationDialog = new CheckWifiDialog(HandlerCalibrationActivity.this, getResources().getString(R.string.saved));
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
        mSvRadio.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        mVwV.setY(Float.parseFloat(info.getY()) * (mHeight / 480.0f));
        mBackX = (int) (Float.parseFloat(info.getX()) * (mWidth / 640.0f));
        mVwH.setX(Float.parseFloat(info.getX()) * (mWidth / 640.0f));
        mBackY = (int) (Float.parseFloat(info.getY()) * (mHeight / 480.0f));
    }

    @Override
    public void showDialog() {
        mLoading = new LoadingDialog(HandlerCalibrationActivity.this);
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTvLoading.setVisibility(View.INVISIBLE);
                    mVwH.setVisibility(View.VISIBLE);
                    mVwV.setVisibility(View.VISIBLE);
                    mBtnSave.setEnabled(true);

                }
            });

            mMainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mTvLoading.setVisibility(View.INVISIBLE);
                    mVwH.setVisibility(View.VISIBLE);
                    mVwV.setVisibility(View.VISIBLE);
                    mBtnSave.setEnabled(true);
                }
            },1000);
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mViewHoriPosition = mVwV.getY();
                mViewVertiPosition = mVwH.getX();
                mStartX = event.getX();
                mStartY = event.getY();
                if (mLandRect.contains((int) mStartX, (int) mStartY)) {
                    isLand = true;
                } else if (mPortRect.contains((int) mStartX, (int) mStartY)) {
                    isPort = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:

                if (isLand) {
                    float sConfigY = event.getY();
                    float vwPosition = mViewHoriPosition + sConfigY - mStartY;
                    if (vwPosition > mHeight - 10) {
                        vwPosition = mHeight - 10;
                    }
                    if (vwPosition < 10) {
                        vwPosition = 10;
                    }

                    mVwV.setY(vwPosition);
                    mBackY = (int) (vwPosition);
                } else if (isPort) {
                    float sConfigX = event.getX();
                    float viewPosition = (mViewVertiPosition + sConfigX - mStartX);
                    if (viewPosition > mWidth - 10) {
                        viewPosition = mWidth - 10;
                    }
                    if (viewPosition < 10) {
                        viewPosition = 10;
                    }
                    mVwH.setX(viewPosition);
                    mBackX = (int) (viewPosition);
                }
                break;
            case MotionEvent.ACTION_UP:
                mLandRect.set(0, (int) mBackY - 30, mWidth, (int) mBackY + 30);
                mPortRect.set((int) mBackX - 30, 0, (int) mBackX + 30, mHeight);
                isPort = false;
                isLand = false;
                break;
        }
        return true;
    }
}

