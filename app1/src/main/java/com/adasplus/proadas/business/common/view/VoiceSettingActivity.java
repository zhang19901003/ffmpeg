package com.adasplus.proadas.business.common.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.common.presenter.VoiceSettingPresenter;
import com.adasplus.proadas.business.connect.view.CheckWifiDialog;
import com.adasplus.proadas.business.connect.view.ConnectWifiDialog;
import com.adasplus.proadas.business.device.view.CalibrationActivity;
import com.adasplus.proadas.common.data.VoiceSetting;
import com.adasplus.proadas.common.entry.CalibrateParam;
import com.adasplus.proadas.common.entry.VoiceInfo;
import com.adasplus.proadas.common.presenter.BasePresenter;
import com.adasplus.proadas.common.view.activity.BaseActivity;
import com.adasplus.proadas.common.view.custom.LoadingDialog;

/**
 * Created by zhangyapeng on 18-4-6.
 */

public class VoiceSettingActivity extends BaseActivity<VoiceSettingPresenter> implements IVoiceSettingView, SwipeRefreshLayout.OnRefreshListener {


    private SeekBar mSeekBar;
    private CheckBox mRbRing;
    private CheckBox mRbHuman;
    private SwipeRefreshLayout mSwipeLayout;
    private LoadingDialog mLoading;

    @Override
    protected VoiceSettingPresenter createPresenter() {
        return new VoiceSettingPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_voice;
    }

    @Override
    protected void initView() {

        TextView tvTitle = (TextView) findViewById(R.id.tv_main_title_textview);
        tvTitle.setText(getResources().getString(R.string.voice));
        TextView tvRightActivation = (TextView) findViewById(R.id.tv_right_view);
        tvRightActivation.setText(getResources().getString(R.string.finish));
        tvRightActivation.setVisibility(View.VISIBLE);
        tvRightActivation.setOnClickListener(this);
        LinearLayout lvLeftView = findViewById(R.id.ll_left_layout);
        lvLeftView.setVisibility(View.VISIBLE);
        lvLeftView.setOnClickListener(this);
        mSeekBar = (SeekBar) findViewById(R.id.sb_player_seek_bar);
        mSeekBar.setMax(15);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    if (mRbHuman.isChecked()) {
                        mBasePresenter.setSeekBarVoice("0", progress + "");
                    } else if (mRbRing.isChecked()) {
                        mBasePresenter.setSeekBarVoice("1", progress + "");
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSeekBar.setOnTouchListener(new View.OnTouchListener() {
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
        mRbRing = (CheckBox) findViewById(R.id.cb_ring);
        mRbHuman = (CheckBox) findViewById(R.id.cb_human);

        mRbHuman.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mRbRing.setChecked(false);
                    mBasePresenter.setSeekBarVoice("0", mSeekBar.getProgress() + "");
                }


            }
        });
        mRbRing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mRbHuman.setChecked(false);
                    mBasePresenter.setSeekBarVoice("1", mSeekBar.getProgress() + "");
                }

            }
        });
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
        switch (v.getId()) {
            case R.id.ll_left_layout:
                finish();
                break;
            case R.id.tv_right_view:
                if (mRbHuman.isChecked()) {
                    mBasePresenter.setVoice("0", mSeekBar.getProgress() + "");
                } else if (mRbRing.isChecked()) {
                    mBasePresenter.setVoice("1", mSeekBar.getProgress() + "");
                }
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
        ConnectWifiDialog activationDialog = new ConnectWifiDialog(VoiceSettingActivity.this, code, getResources().getString(R.string.confirm), getResources().getString(R.string.cancel), 0);
        activationDialog.show();

    }

    @Override
    public void success(final VoiceSetting voiceInfo, final int code) {
        if (!isShowDialog) {
            return;
        }

        switch (code) {
            case 0:

                if (!TextUtils.isEmpty(voiceInfo.getVoiceSize())) {
                    int i = Integer.parseInt(voiceInfo.getVoiceSize());
                    mSeekBar.setProgress(i);
                }

                if (!TextUtils.isEmpty(voiceInfo.getVoiceType()) && voiceInfo.getVoiceType().equals("0")) {
                    mRbRing.setChecked(false);
                    mRbHuman.setChecked(true);
                }

                if (!TextUtils.isEmpty(voiceInfo.getVoiceType()) && voiceInfo.getVoiceType().equals("1")) {
                    mRbRing.setChecked(true);
                    mRbHuman.setChecked(false);
                }

                break;
            case 1:
                CheckWifiDialog calibrationDialog = new CheckWifiDialog(VoiceSettingActivity.this, getResources().getString(R.string.saved));
                calibrationDialog.show();
                break;
        }


    }

    @Override
    public void onRefresh() {
        mBasePresenter.onResume();
    }

    @Override
    public void showDialog() {
        mLoading = new LoadingDialog(VoiceSettingActivity.this);
        mLoading.setShowAnimation(true);
        mLoading.setString(getResources().getString(R.string.loading));
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
}
