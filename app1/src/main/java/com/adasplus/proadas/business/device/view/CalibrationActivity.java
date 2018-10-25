package com.adasplus.proadas.business.device.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adasplus.proadas.App;
import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.common.view.UpdateInfoActivIty;
import com.adasplus.proadas.business.connect.view.CheckWifiDialog;
import com.adasplus.proadas.business.connect.view.ConnectWifiDialog;
import com.adasplus.proadas.business.device.presenter.CalibrationPresenter;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.data.CalibrateInfo;
import com.adasplus.proadas.common.entry.CalibrateParam;
import com.adasplus.proadas.common.util.Utils;
import com.adasplus.proadas.common.view.activity.BaseActivity;
import com.adasplus.proadas.common.view.custom.LoadingDialog;

/**
 * Created by zhangyapeng on 18-4-6.
 */

public class CalibrationActivity extends BaseActivity<CalibrationPresenter> implements ICalibrationView, SwipeRefreshLayout.OnRefreshListener {

    private EditText mEtCamera;
    private EditText mEtBumper;
    private EditText mEtLeftWheel;
    private EditText mEtRight;
    private EditText mEtCalibration;
    private LoadingDialog mLoading;
    private SwipeRefreshLayout mSwipeLayout;
    private Handler mMainHandler;
    @Override
    protected CalibrationPresenter createPresenter() {
        return new CalibrationPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_calibration;
    }
    @Override
    protected void initView() {
        mMainHandler = new Handler(Looper.getMainLooper());
        TextView tvRight = (TextView) findViewById(R.id.tv_right_view);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getResources().getString(R.string.save));
        tvRight.setOnClickListener(this);
        LinearLayout lvLeftView = findViewById(R.id.ll_left_layout);
        lvLeftView.setVisibility(View.VISIBLE);
        lvLeftView.setOnClickListener(this);
        TextView tvTitle = (TextView) findViewById(R.id.tv_main_title_textview);
        tvTitle.setText(getResources().getString(R.string.calibra_setting));
        RelativeLayout rvHelp = (RelativeLayout) findViewById(R.id.rv_use_help);
        rvHelp.setOnClickListener(this);
        mEtCamera = (EditText) findViewById(R.id.et_camera_height);
        mEtBumper = (EditText) findViewById(R.id.et_bumper_distance);
        mEtLeftWheel = (EditText) findViewById(R.id.et_left_distance);
        mEtRight = (EditText) findViewById(R.id.et_right_distance);
        mEtCalibration = (EditText) findViewById(R.id.et_calibration_height);
        findViewById(R.id.rv_calibration).setOnClickListener(this);
        findViewById(R.id.rv_hand_calibration).setOnClickListener(this);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeLayout.setProgressBackgroundColorSchemeResource(R.color.btn_disenable);
    }

    @Override
    protected void initData() {

        mBasePresenter.onResume();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.ll_left_layout){
            finish();
            return;
        }
        if(v.getId() == R.id.rv_use_help){
            Intent intent = new Intent(this, HelpActivity.class);
            intent.putExtra(Constants.HELP, R.layout.activity_calibration_help);
            startActivity(intent);
            return;
        }
        final  String mCameraHeight = mEtCamera.getText().toString().trim();
        final  String mBumperDis = mEtBumper.getText().toString().trim();
        final  String mLeftDis = mEtLeftWheel.getText().toString().trim();
        final  String mRightDis = mEtRight.getText().toString().trim();
        final  String mCalibraHeight = mEtCalibration.getText().toString().trim();
        if (TextUtils.isEmpty(mCameraHeight) || TextUtils.isEmpty(mBumperDis) || TextUtils.isEmpty(mLeftDis) || TextUtils.isEmpty(mRightDis) || TextUtils.isEmpty(mCalibraHeight)) {
            updateCode(App.getContext().getString(R.string.parn_null));
           return;
        }
        if (!Utils.isCalibrate(mCameraHeight) || !Utils.isCalibrate(mBumperDis) || !Utils.isCalibrate(mLeftDis) || !Utils.isCalibrate(mRightDis) || !Utils.isCalibrate(mCalibraHeight)) {
            updateCode(App.getContext().getResources().getString(R.string.message_error));
           return;
        }

        switch (v.getId()) {
            case R.id.tv_right_view:
                ConnectWifiDialog saveDialog = new ConnectWifiDialog(CalibrationActivity.this, getResources().getString(R.string.yes_no), getResources().getString(R.string.yes_), getResources().getString(R.string.no_), 1);
                saveDialog.setOnDialogClickListener(new ConnectWifiDialog.OnDialogClickListener() {
                    @Override
                    public void onDialogLeftDialog() {

                    }

                    @Override
                    public void onDialogRightDialog() {
                        mBasePresenter.activationCallBack(mEtCamera.getText().toString(), mEtBumper.getText().toString(), mEtLeftWheel.getText().toString(), mEtRight.getText().toString(), mEtCalibration.getText().toString());
                    }
                });
                saveDialog.show();
                break;

            case R.id.rv_calibration:
                showDialog(getResources().getString(R.string.loading));
                mMainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideDialog();
                        Intent intent1 = new Intent(CalibrationActivity.this, PictureActivity.class);
                        intent1.putExtra(Constants.CALIBRATION, mCameraHeight + "_" + mBumperDis + "_" + mLeftDis + "_" + mRightDis + "_" + mCalibraHeight);
                        startActivity(intent1);
                    }
                },1000);

                break;
            case R.id.rv_hand_calibration:

                showDialog(getResources().getString(R.string.loading));
                mMainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideDialog();
                        Intent intent2 = new Intent(CalibrationActivity.this, HandlerCalibrationActivity.class);
                        intent2.putExtra(Constants.CALIBRATION, mCameraHeight + "_" + mBumperDis + "_" + mLeftDis + "_" + mRightDis + "_" + mCalibraHeight);
                        startActivity(intent2);
                    }
                },1000);

                break;

        }
    }

    @Override
    public SwipeRefreshLayout getLoadiView() {
        return mSwipeLayout;
    }

    @Override
    public void updateCode(final String code) {
        if (!isShowDialog) {
            return;
        }
        ConnectWifiDialog activationDialog = new ConnectWifiDialog(CalibrationActivity.this, code, getResources().getString(R.string.confirm), getResources().getString(R.string.cancel), 0);
        activationDialog.show();

    }

    @Override
    public void success(final CalibrateInfo calibrateParam, final int code) {
        if (!isShowDialog) {
            return;
        }

        switch (code) {
            case 0:
                mEtCamera.setText(calibrateParam.getCameraHeight());
                mEtCalibration.setText(calibrateParam.getCalibrateHeight());
                mEtBumper.setText(calibrateParam.getBumperDis());
                mEtLeftWheel.setText(calibrateParam.getLeftWheel());
                mEtRight.setText(calibrateParam.getRightWheel());
                break;
            case 1:
                CheckWifiDialog calibrationDialog = new CheckWifiDialog(CalibrationActivity.this, getResources().getString(R.string.saved));
                calibrationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                    }
                });
                calibrationDialog.show();

                break;
            case 2:
                CheckWifiDialog resetDialog = new CheckWifiDialog(CalibrationActivity.this, getResources().getString(R.string.reset_calibration));
                resetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                    }
                });
                resetDialog.show();
                break;
        }

    }

    @Override
    public void showDialog(String des) {
        mLoading = new LoadingDialog(CalibrationActivity.this);
        mLoading.setShowAnimation(true);
        mLoading.setString(des);
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
    public void onRefresh() {
        mBasePresenter.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideDialog();
        if(mMainHandler != null){
            mMainHandler.removeCallbacksAndMessages(null);
        }
    }
}
