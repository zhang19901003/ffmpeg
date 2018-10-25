package com.adasplus.proadas.business.device.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.connect.view.CheckWifiDialog;
import com.adasplus.proadas.business.connect.view.ConnectWifiDialog;
import com.adasplus.proadas.business.device.presenter.EarlyWarningPresenter;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.data.WarnParams;
import com.adasplus.proadas.common.view.activity.BaseActivity;
import com.adasplus.proadas.common.view.custom.LoadingDialog;
import com.adasplus.proadas.common.view.custom.SeekBarSpeed;

/**
 * Created by zhangyapeng on 18-4-6.
 */

public class EarlyWarningActivity extends BaseActivity<EarlyWarningPresenter> implements IEarlyWarningView, SwipeRefreshLayout.OnRefreshListener, CompoundButton.OnCheckedChangeListener {

    private CheckBox mCbFront;
    private CheckBox mCbLdw;
    private CheckBox mCbPed;
    private CheckBox mCbDfw;
    private CheckBox mCbSmoke;
    private CheckBox mCbCall;
    private CheckBox mCbYawn;
    private RadioGroup mRgFront;
    private SeekBarSpeed mSeekBarFcw;
    private SeekBarSpeed mSeekBaLdw;
    private SeekBarSpeed mSeekBarPed;
    private SeekBarSpeed mSeekBarDfw;
    private RadioGroup mRgLdw;
    private RadioGroup mRgDfw;
    private RadioGroup mRgPcw;
    private WarnParams mSetWarnParams;
    private LoadingDialog mLoading;
    private SwipeRefreshLayout mSwipeLayout;
    private WarnParams mQuestWarnParams;
    private String mFcw = "0";
    private String mPcw = "0";
    private String mDfw = "0";
    private String mLdw = "0";
    private TextView mTvFcwSen;
    private TextView mTvLdwSen;
    private TextView mTvPcwSen;
    private TextView mTvDfwSen;
    private TextView mTvFcwSpeed;
    private TextView mTvLdwSpeed;
    private TextView mTvPcwSpeed;
    private TextView mTvDfwSpeed;
    private TextView mTvChoose;
    private TextView mTvCall;
    private TextView mTvSmoke;
    private TextView mTvYawn;

    @Override
    protected EarlyWarningPresenter createPresenter() {
        return new EarlyWarningPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_early_warning;
    }

    @Override
    protected void initView() {
        mSetWarnParams = new WarnParams();
        TextView tvRight = (TextView) findViewById(R.id.tv_right_view);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getResources().getString(R.string.save));
        tvRight.setOnClickListener(this);
        LinearLayout lvLeftView = findViewById(R.id.ll_left_layout);
        lvLeftView.setVisibility(View.VISIBLE);
        lvLeftView.setOnClickListener(this);
        TextView tvTitle = (TextView) findViewById(R.id.tv_main_title_textview);
        tvTitle.setText(getResources().getString(R.string.early_setting));
        RelativeLayout rvHelp = (RelativeLayout) findViewById(R.id.rv_use_help);
        rvHelp.setOnClickListener(this);
//        LinearLayout mLvFcw =(LinearLayout) findViewById(R.id.lv_fcw);
//        int mFcwchildCount = mLvFcw.getChildCount();
//        for (int i=0;i<mFcwchildCount;i++){
//            View childAt = mLvFcw.getChildAt(i);
//
//        }
//        LinearLayout mLvLdw =(LinearLayout) findViewById(R.id.lv_ldw);
//        int mLdwChildCount = mLvLdw.getChildCount();
//        LinearLayout mLvPcw =(LinearLayout) findViewById(R.id.lv_pcw);
//        int mPcwChildCount = mLvPcw.getChildCount();
//        LinearLayout mLvDfw =(LinearLayout) findViewById(R.id.lv_dfw);
//        int mDfwChildCount = mLvDfw.getChildCount();
//        Log.e("Adas",mFcwchildCount+"  "+mLdwChildCount+"  "+mPcwChildCount+" "+mDfwChildCount);
        mCbFront = (CheckBox) findViewById(R.id.cb_front);
        mCbLdw = (CheckBox) findViewById(R.id.cb_ldw);
        mCbPed = (CheckBox) findViewById(R.id.cb_ped);
        mCbDfw = (CheckBox) findViewById(R.id.cb_dfw);
        mCbSmoke = (CheckBox) findViewById(R.id.cb_smoke);
        mCbCall = (CheckBox) findViewById(R.id.cb_call);
        mCbYawn = (CheckBox) findViewById(R.id.cb_yawn);
        mRgFront = (RadioGroup) findViewById(R.id.rg_front_sen);
        mRgLdw = (RadioGroup) findViewById(R.id.rg_ldw);
        mRgDfw = (RadioGroup) findViewById(R.id.rg_dfw);
        mRgPcw = (RadioGroup) findViewById(R.id.rg_ped);
        mCbDfw.setOnCheckedChangeListener(this);
        mCbFront.setOnCheckedChangeListener(this);
        mCbLdw.setOnCheckedChangeListener(this);
        mCbPed.setOnCheckedChangeListener(this);
        mRgFront.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_fcw_h:
                        mFcw = "2";
                        break;
                    case R.id.rb_fcw_m:
                        mFcw = "1";
                        break;
                    case R.id.rb_fcw_l:
                        mFcw = "0";
                        break;
                }
            }
        });
        mRgLdw.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_ldw_h:
                        mLdw = "2";
                        break;
                    case R.id.rb_ldw_m:
                        mLdw = "1";
                        break;
                    case R.id.rb_ldw_l:
                        mLdw = "0";
                        break;
                }
            }
        });
        mRgDfw.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_dfw_h:
                        mDfw = "2";
                        break;
                    case R.id.rb_dfw_m:
                        mDfw = "1";
                        break;
                    case R.id.rb_dfw_l:
                        mDfw = "0";
                        break;
                }
            }
        });

        mRgPcw.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_ped_h:
                        mPcw = "2";
                        break;
                    case R.id.rb_ped_m:
                        mPcw = "1";
                        break;
                    case R.id.rb_ped_l:
                        mPcw = "0";
                        break;
                }
            }
        });

        mSeekBarFcw = (SeekBarSpeed) findViewById(R.id.seek_fcw);
        mSeekBaLdw = (SeekBarSpeed) findViewById(R.id.seek_ldw);
        mSeekBarPed = (SeekBarSpeed) findViewById(R.id.seek_ped);
        mSeekBarDfw = (SeekBarSpeed) findViewById(R.id.seek_dfw);
        findViewById(R.id.btn_reset).setOnClickListener(this);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeLayout.setProgressBackgroundColorSchemeResource(R.color.btn_disenable);

        mTvFcwSen = (TextView) findViewById(R.id.tv_fcw_sen);
        mTvFcwSpeed = (TextView) findViewById(R.id.tv_fcw_speed);

        mTvLdwSen = (TextView) findViewById(R.id.tv_ldw_sen);
        mTvLdwSpeed = (TextView) findViewById(R.id.tv_ldw_speed);

        mTvPcwSen = (TextView) findViewById(R.id.tv_pcw_sen);
        mTvPcwSpeed = (TextView) findViewById(R.id.tv_pcw_speed);

        mTvDfwSen = (TextView) findViewById(R.id.tv_dfw_sen);
        mTvDfwSpeed = (TextView) findViewById(R.id.tv_dfw_speed);
        mTvChoose = (TextView) findViewById(R.id.tv_choose);
        mTvCall = (TextView) findViewById(R.id.tv_call);
        mTvSmoke = (TextView) findViewById(R.id.tv_smoke);
        mTvYawn = (TextView) findViewById(R.id.tv_yawn);
        mSeekBarFcw.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        mSwipeLayout.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        mSwipeLayout.setEnabled(true);
                        break;
                }
                return false;
            }
        });
        mSeekBarDfw.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        mSwipeLayout.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        mSwipeLayout.setEnabled(true);
                        break;
                }
                return false;
            }
        });
        mSeekBarPed.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        mSwipeLayout.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        mSwipeLayout.setEnabled(true);
                        break;
                }
                return false;
            }
        });
        mSeekBaLdw.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        mSwipeLayout.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        mSwipeLayout.setEnabled(true);
                        break;
                }
                return false;
            }
        });

    }

    private int getDisableColor() {
        return getResources().getColor(R.color.gray66);
    }

    private int getEnableColor() {
        return getResources().getColor(R.color.black);
    }

    @Override
    protected void initData() {
        mBasePresenter.onResume();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_left_layout:
                if (this.mQuestWarnParams == null) {
                    finish();
                } else {
                    mSetWarnParams.setFcwSpeed(mSeekBarFcw.getReallyProgress() + "");
                    mSetWarnParams.setLdwSpeed(mSeekBaLdw.getReallyProgress() + "");
                    mSetWarnParams.setDfwSpeed(mSeekBarDfw.getReallyProgress() + "");
                    mSetWarnParams.setPcwSpeed(mSeekBarPed.getReallyProgress() + "");
                    mSetWarnParams.setLdwEnable(mCbLdw.isChecked() ? "true" : "false");
                    mSetWarnParams.setFcwEnable(mCbFront.isChecked() ? "true" : "false");
                    mSetWarnParams.setCallphoneEnable(mCbCall.isChecked() ? "true" : "false");
                    mSetWarnParams.setDfwEnable(mCbDfw.isChecked() ? "true" : "false");
                    mSetWarnParams.setPcwEnable(mCbPed.isChecked() ? "true" : "false");
                    mSetWarnParams.setSmokingEnable(mCbSmoke.isChecked() ? "true" : "false");
                    mSetWarnParams.setYawnEnable(mCbYawn.isChecked() ? "true" : "false");
                    mSetWarnParams.setFcwSensitivity(mFcw);
                    mSetWarnParams.setLdwSensitivity(mLdw);
                    mSetWarnParams.setDfwSensitivity(mDfw);
                    mSetWarnParams.setPcwSensitivity(mPcw);
                    if (mQuestWarnParams.equals(mSetWarnParams)) {
                        finish();
                    } else {
                        ConnectWifiDialog saveDialog = new ConnectWifiDialog(EarlyWarningActivity.this, getResources().getString(R.string.yes_no), getResources().getString(R.string.yes_), getResources().getString(R.string.no_), 1);
                        saveDialog.setOnDialogClickListener(new ConnectWifiDialog.OnDialogClickListener() {
                            @Override
                            public void onDialogLeftDialog() {
                                finish();
                            }

                            @Override
                            public void onDialogRightDialog() {
                                mBasePresenter.updateCallBack(mSetWarnParams);
                            }
                        });
                        saveDialog.show();
                    }
                }
                break;
            case R.id.tv_right_view:
                ConnectWifiDialog saveDialog = new ConnectWifiDialog(EarlyWarningActivity.this, getResources().getString(R.string.yes_no), getResources().getString(R.string.yes_), getResources().getString(R.string.no_), 1);
                saveDialog.setOnDialogClickListener(new ConnectWifiDialog.OnDialogClickListener() {
                    @Override
                    public void onDialogLeftDialog() {

                    }

                    @Override
                    public void onDialogRightDialog() {
                        mSetWarnParams.setFcwSpeed(mSeekBarFcw.getReallyProgress() + "");
                        mSetWarnParams.setLdwSpeed(mSeekBaLdw.getReallyProgress() + "");
                        mSetWarnParams.setDfwSpeed(mSeekBarDfw.getReallyProgress() + "");
                        mSetWarnParams.setPcwSpeed(mSeekBarPed.getReallyProgress() + "");
                        mSetWarnParams.setLdwEnable(mCbLdw.isChecked() ? "true" : "false");
                        mSetWarnParams.setFcwEnable(mCbFront.isChecked() ? "true" : "false");
                        mSetWarnParams.setCallphoneEnable(mCbCall.isChecked() ? "true" : "false");
                        mSetWarnParams.setDfwEnable(mCbDfw.isChecked() ? "true" : "false");
                        mSetWarnParams.setPcwEnable(mCbPed.isChecked() ? "true" : "false");
                        mSetWarnParams.setSmokingEnable(mCbSmoke.isChecked() ? "true" : "false");
                        mSetWarnParams.setYawnEnable(mCbYawn.isChecked() ? "true" : "false");
                        mSetWarnParams.setFcwSensitivity(mFcw);
                        mSetWarnParams.setLdwSensitivity(mLdw);
                        mSetWarnParams.setDfwSensitivity(mDfw);
                        mSetWarnParams.setPcwSensitivity(mPcw);
                        mBasePresenter.updateCallBack(mSetWarnParams);
                    }
                });
                saveDialog.show();

                break;
            case R.id.rv_use_help:
                Intent intent = new Intent(this, HelpActivity.class);
                intent.putExtra(Constants.HELP, R.layout.activity_warn);
                startActivity(intent);
                break;
            case R.id.btn_reset:
                ConnectWifiDialog activationDialog = new ConnectWifiDialog(EarlyWarningActivity.this, getResources().getString(R.string.is_reset_calibration), getResources().getString(R.string.confirm), getResources().getString(R.string.cancel), 1);
                activationDialog.setOnDialogClickListener(new ConnectWifiDialog.OnDialogClickListener() {
                    @Override
                    public void onDialogLeftDialog() {

                    }

                    @Override
                    public void onDialogRightDialog() {
                        mBasePresenter.resetWarnParms();
                    }
                });
                activationDialog.show();
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
                ConnectWifiDialog activationDialog = new ConnectWifiDialog(EarlyWarningActivity.this, code, getResources().getString(R.string.confirm), getResources().getString(R.string.cancel), 0);
                activationDialog.show();

    }

    @Override
    public void success(final WarnParams adasConfig2, final int code) {
        if (!isShowDialog) {
            return;
        }
        switch (code) {
            case 0:
                mQuestWarnParams = adasConfig2;
                settParam();
                break;
            case 1:
                CheckWifiDialog connectWifiDialog = new CheckWifiDialog(EarlyWarningActivity.this, getResources().getString(R.string.saved));
                connectWifiDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                    }
                });
                connectWifiDialog.show();
                break;
            case 2:
                mQuestWarnParams = adasConfig2;
                settParam();
                CheckWifiDialog resetDialog = new CheckWifiDialog(EarlyWarningActivity.this, getResources().getString(R.string.reset_warn));
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
    public void showDialog(final String des) {
        mLoading = new LoadingDialog(EarlyWarningActivity.this);
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

    public void settParam() {
        mSeekBarDfw.setProgress(Integer.parseInt(mQuestWarnParams.getDfwSpeed()));
        mSeekBarFcw.setProgress(Integer.parseInt(mQuestWarnParams.getFcwSpeed()));
        mSeekBarPed.setProgress(Integer.parseInt(mQuestWarnParams.getPcwSpeed()));
        mSeekBaLdw.setProgress(Integer.parseInt(mQuestWarnParams.getLdwSpeed()));
        mCbFront.setChecked(mQuestWarnParams.getFcwEnable().equals("true") ? true : false);
        mCbCall.setChecked(mQuestWarnParams.getCallphoneEnable().equals("true") ? true : false);
        mCbDfw.setChecked(mQuestWarnParams.getDfwEnable().equals("true") ? true : false);
        mCbPed.setChecked(mQuestWarnParams.getPcwEnable().equals("true") ? true : false);
        mCbSmoke.setChecked(mQuestWarnParams.getSmokingEnable().equals("true") ? true : false);
        mCbYawn.setChecked(mQuestWarnParams.getYawnEnable().equals("true") ? true : false);
        mCbLdw.setChecked(mQuestWarnParams.getLdwEnable().equals("true") ? true : false);
        String fcwSensitivity = mQuestWarnParams.getFcwSensitivity();
        mFcw = fcwSensitivity;
        if (mCbFront.isChecked()) {
            enableRadioGroup(mRgFront);
            mSeekBarFcw.setEnabled(true);
            mTvFcwSen.setTextColor(getEnableColor());
            mTvFcwSpeed.setTextColor(getEnableColor());
            mSeekBarFcw.setThumb(getDrawable(R.drawable.seekbar_speed_thumd));
            switch (fcwSensitivity) {
                case "0":
                    mRgFront.check(R.id.rb_fcw_l);
                    break;
                case "1":
                    mRgFront.check(R.id.rb_fcw_m);
                    break;
                case "2":
                    mRgFront.check(R.id.rb_fcw_h);
                    break;
            }
        } else {
            mSeekBarFcw.setThumb(getDrawable(R.drawable.seekbar_speed_disenable_thumd));
            mTvFcwSen.setTextColor(getDisableColor());
            mTvFcwSpeed.setTextColor(getDisableColor());
            mSeekBarFcw.setEnabled(false);
            mRgFront.clearCheck();
            disableRadioGroup(mRgFront);
        }
        String ldwSensitivity = mQuestWarnParams.getLdwSensitivity();
        mLdw = ldwSensitivity;
        if (mCbLdw.isChecked()) {
            enableRadioGroup(mRgLdw);
            mSeekBaLdw.setEnabled(true);
            mTvLdwSen.setTextColor(getEnableColor());
            mTvLdwSpeed.setTextColor(getEnableColor());
            mSeekBaLdw.setThumb(getDrawable(R.drawable.seekbar_speed_thumd));
            switch (ldwSensitivity) {
                case "0":
                    mRgLdw.check(R.id.rb_ldw_l);
                    break;
                case "1":
                    mRgLdw.check(R.id.rb_ldw_m);
                    break;
                case "2":
                    mRgLdw.check(R.id.rb_ldw_h);
                    break;
            }
        } else {
            mSeekBaLdw.setThumb(getDrawable(R.drawable.seekbar_speed_disenable_thumd));
            mTvLdwSen.setTextColor(getDisableColor());
            mTvLdwSpeed.setTextColor(getDisableColor());
            mSeekBaLdw.setEnabled(false);
            mRgLdw.clearCheck();
            disableRadioGroup(mRgLdw);
        }
        String pedMinVelocity = mQuestWarnParams.getPcwSensitivity();
        mPcw = pedMinVelocity;
        if (mCbPed.isChecked()) {
            enableRadioGroup(mRgPcw);
            mSeekBarPed.setEnabled(true);
            mTvPcwSen.setTextColor(getEnableColor());
            mTvPcwSpeed.setTextColor(getEnableColor());
            mSeekBarPed.setThumb(getDrawable(R.drawable.seekbar_speed_thumd));
            switch (pedMinVelocity) {
                case "0":
                    mRgPcw.check(R.id.rb_ped_l);
                    break;
                case "1":
                    mRgPcw.check(R.id.rb_ped_m);
                    break;
                case "2":
                    mRgPcw.check(R.id.rb_ped_h);
                    break;
            }
        } else {
            mSeekBarPed.setThumb(getDrawable(R.drawable.seekbar_speed_disenable_thumd));
            mTvPcwSen.setTextColor(getDisableColor());
            mTvPcwSpeed.setTextColor(getDisableColor());
            mSeekBarPed.setEnabled(false);
            mRgPcw.clearCheck();
            disableRadioGroup(mRgPcw);
        }
        String dfwSensitivity = mQuestWarnParams.getDfwSensitivity();
        mDfw = dfwSensitivity;
        if (mCbDfw.isChecked()) {
            enableRadioGroup(mRgDfw);
            mSeekBarDfw.setEnabled(true);
            mTvDfwSen.setTextColor(getEnableColor());
            mTvDfwSpeed.setTextColor(getEnableColor());
            mTvChoose.setTextColor(getEnableColor());
            mTvSmoke.setTextColor(getEnableColor());
            mTvCall.setTextColor(getEnableColor());
            mTvYawn.setTextColor(getEnableColor());
            mCbCall.setClickable(true);
            mCbSmoke.setClickable(true);
            mCbYawn.setClickable(true);
            mSeekBarDfw.setThumb(getDrawable(R.drawable.seekbar_speed_thumd));
            switch (dfwSensitivity) {
                case "0":
                    mRgDfw.check(R.id.rb_dfw_l);
                    break;
                case "1":
                    mRgDfw.check(R.id.rb_dfw_m);
                    break;
                case "2":
                    mRgDfw.check(R.id.rb_dfw_h);
                    break;
            }
        } else {
            mSeekBarDfw.setThumb(getDrawable(R.drawable.seekbar_speed_disenable_thumd));
            mTvDfwSen.setTextColor(getDisableColor());
            mTvDfwSpeed.setTextColor(getDisableColor());
            mTvChoose.setTextColor(getDisableColor());
            mTvSmoke.setTextColor(getDisableColor());
            mTvCall.setTextColor(getDisableColor());
            mTvYawn.setTextColor(getDisableColor());
            mCbCall.setChecked(false);
            mCbSmoke.setChecked(false);
            mCbYawn.setChecked(false);
            mCbCall.setClickable(false);
            mCbSmoke.setClickable(false);
            mCbYawn.setClickable(false);
            mSeekBarDfw.setEnabled(false);
            mRgDfw.clearCheck();
            disableRadioGroup(mRgDfw);
        }
    }


    public void disableRadioGroup(RadioGroup testRadioGroup) {
        for (int i = 0; i < testRadioGroup.getChildCount(); i++) {
            testRadioGroup.getChildAt(i).setEnabled(false);
        }
    }

    public void enableRadioGroup(RadioGroup testRadioGroup) {
        for (int i = 0; i < testRadioGroup.getChildCount(); i++) {
            testRadioGroup.getChildAt(i).setEnabled(true);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {
            case R.id.cb_front:
                if (isChecked) {
                    enableRadioGroup(mRgFront);
                    mSeekBarFcw.setEnabled(true);
                    mTvFcwSen.setTextColor(getEnableColor());
                    mTvFcwSpeed.setTextColor(getEnableColor());
                    mSeekBarFcw.setThumb(getDrawable(R.drawable.seekbar_speed_thumd));
                    switch (mFcw) {
                        case "0":
                            mRgFront.check(R.id.rb_fcw_l);
                            break;
                        case "1":
                            mRgFront.check(R.id.rb_fcw_m);
                            break;
                        case "2":
                            mRgFront.check(R.id.rb_fcw_h);
                            break;
                    }
                } else {
                    mSeekBarFcw.setThumb(getDrawable(R.drawable.seekbar_speed_disenable_thumd));
                    mTvFcwSen.setTextColor(getDisableColor());
                    mTvFcwSpeed.setTextColor(getDisableColor());
                    mSeekBarFcw.setEnabled(false);
                    mRgFront.clearCheck();
                    disableRadioGroup(mRgFront);
                }
                break;
            case R.id.cb_dfw:
                if (isChecked) {
                    mSeekBarDfw.setEnabled(true);
                    mSeekBarDfw.setThumb(getDrawable(R.drawable.seekbar_speed_thumd));
                    mTvDfwSen.setTextColor(getEnableColor());
                    mTvDfwSpeed.setTextColor(getEnableColor());
                    mTvChoose.setTextColor(getEnableColor());
                    mTvSmoke.setTextColor(getEnableColor());
                    mTvCall.setTextColor(getEnableColor());
                    mTvYawn.setTextColor(getEnableColor());
                    mCbCall.setClickable(true);
                    mCbSmoke.setClickable(true);
                    mCbYawn.setClickable(true);
                    enableRadioGroup(mRgDfw);
                    switch (mDfw) {
                        case "0":
                            mRgDfw.check(R.id.rb_dfw_l);
                            break;
                        case "1":
                            mRgDfw.check(R.id.rb_dfw_m);
                            break;
                        case "2":
                            mRgDfw.check(R.id.rb_dfw_h);
                            break;
                    }
                } else {
                    mSeekBarDfw.setThumb(getDrawable(R.drawable.seekbar_speed_disenable_thumd));
                    mTvDfwSen.setTextColor(getDisableColor());
                    mTvDfwSpeed.setTextColor(getDisableColor());
                    mTvChoose.setTextColor(getDisableColor());
                    mTvSmoke.setTextColor(getDisableColor());
                    mTvCall.setTextColor(getDisableColor());
                    mTvYawn.setTextColor(getDisableColor());
                    mCbCall.setChecked(false);
                    mCbSmoke.setChecked(false);
                    mCbYawn.setChecked(false);
                    mCbCall.setClickable(false);
                    mCbSmoke.setClickable(false);
                    mCbYawn.setClickable(false);
                    mSeekBarDfw.setEnabled(false);
                    mRgDfw.clearCheck();
                    disableRadioGroup(mRgDfw);
                }
                break;
            case R.id.cb_ldw:
                if (isChecked) {
                    mSeekBaLdw.setEnabled(true);
                    mSeekBaLdw.setThumb(getDrawable(R.drawable.seekbar_speed_thumd));
                    mTvLdwSen.setTextColor(getEnableColor());
                    mTvLdwSpeed.setTextColor(getEnableColor());
                    enableRadioGroup(mRgLdw);
                    switch (mLdw) {
                        case "0":
                            mRgLdw.check(R.id.rb_ldw_l);
                            break;
                        case "1":
                            mRgLdw.check(R.id.rb_ldw_m);
                            break;
                        case "2":
                            mRgLdw.check(R.id.rb_ldw_h);
                            break;
                    }
                } else {
                    mSeekBaLdw.setThumb(getDrawable(R.drawable.seekbar_speed_disenable_thumd));
                    mTvLdwSen.setTextColor(getDisableColor());
                    mTvLdwSpeed.setTextColor(getDisableColor());
                    mSeekBaLdw.setEnabled(false);
                    mRgLdw.clearCheck();
                    disableRadioGroup(mRgLdw);
                }
                break;
            case R.id.cb_ped:
                if (isChecked) {
                    mSeekBarPed.setEnabled(true);
                    mSeekBarPed.setThumb(getDrawable(R.drawable.seekbar_speed_thumd));
                    mTvPcwSen.setTextColor(getEnableColor());
                    mTvPcwSpeed.setTextColor(getEnableColor());
                    enableRadioGroup(mRgPcw);
                    switch (mPcw) {
                        case "0":
                            mRgPcw.check(R.id.rb_ped_l);
                            break;
                        case "1":
                            mRgPcw.check(R.id.rb_ped_m);
                            break;
                        case "2":
                            mRgPcw.check(R.id.rb_ped_h);
                            break;
                    }
                } else {
                    mSeekBarPed.setThumb(getDrawable(R.drawable.seekbar_speed_disenable_thumd));
                    mTvPcwSen.setTextColor(getDisableColor());
                    mTvPcwSpeed.setTextColor(getDisableColor());
                    mSeekBarPed.setEnabled(false);
                    mRgPcw.clearCheck();
                    disableRadioGroup(mRgPcw);
                }
                break;
        }
    }

}
