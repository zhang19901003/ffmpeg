package com.adasplus.proadas.business.main.view.fragment.common;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adasplus.proadas.App;
import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.common.view.ActivationActivity;
import com.adasplus.proadas.business.common.view.AlreadlyAcitvationActivity;
import com.adasplus.proadas.business.common.view.PreviewVideoActivity;
import com.adasplus.proadas.business.common.view.SaveSpaceActivity;
import com.adasplus.proadas.business.common.view.VoiceSettingActivity;
import com.adasplus.proadas.business.connect.view.CheckWifiDialog;
import com.adasplus.proadas.business.connect.view.ConnectWifiDialog;
import com.adasplus.proadas.business.device.view.HelpActivity;
import com.adasplus.proadas.business.main.presenter.fragment.common.CommonPresenter;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.data.DeviceState;
import com.adasplus.proadas.common.view.custom.LoadingDialog;
import com.adasplus.proadas.common.view.fragment.BaseFragment;

/**
 * Created by zhangyapeng on 18-4-4.
 */

public class CommonFragment extends BaseFragment<CommonPresenter> implements ICommonView, SwipeRefreshLayout.OnRefreshListener, CompoundButton.OnCheckedChangeListener {


    private TextView mTvDevicesStatus;
    private CheckBox mCbGps;
    private TextView mTvNetStatus;
    private SwipeRefreshLayout mSwipeLayout;
    private TextView mTvCurrentLocation;
    private LoadingDialog mLoading;
    private boolean isShowDialog = true;
    private CheckBox mCbModel;
    private RelativeLayout mRvVocieSwitch;
    private int currentModel;
    private String mCurrentModel;

    @Override
    public void toastNetworkError(String msg) {

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutId() {
        isShowDialog = true;
        return R.layout.fragment_commom;
    }

    @Override
    protected void initView(View view) {
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.rv_activation);
        relativeLayout.setOnClickListener(this);
        RelativeLayout relativeLayout1 = (RelativeLayout) view.findViewById(R.id.rv_save_space);
        relativeLayout1.setOnClickListener(this);
        RelativeLayout relativeLayout2 = (RelativeLayout) view.findViewById(R.id.rv_preview_video);
        relativeLayout2.setOnClickListener(this);
        RelativeLayout relativeLayout3 = (RelativeLayout) view.findViewById(R.id.rv_voice);
        relativeLayout3.setOnClickListener(this);
        TextView tvRight = (TextView) getActivity().findViewById(R.id.tv_right_view);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getResources().getString(R.string.use_help));
        tvRight.setOnClickListener(this);
        mTvCurrentLocation = (TextView) view.findViewById(R.id.tv_current);
        mTvDevicesStatus = (TextView) view.findViewById(R.id.tv_devices_status);
        mCbGps = (CheckBox) view.findViewById(R.id.cb_gps);
        mCbGps.setOnClickListener(this);
        RelativeLayout relativeLayout4 = view.findViewById(R.id.rv_resume);
        relativeLayout4.setOnClickListener(this);
        mRvVocieSwitch = (RelativeLayout) view.findViewById(R.id.rv_voice_switch);
        mRvVocieSwitch.setEnabled(false);
        mRvVocieSwitch.setOnClickListener(this);
        mCbModel = (CheckBox) view.findViewById(R.id.cb_model);
        mCbModel.setOnClickListener(this);
        mCbModel.setOnCheckedChangeListener(this);

        mTvNetStatus = (TextView) view.findViewById(R.id.tv_sim_status);
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeLayout.setProgressBackgroundColorSchemeResource(R.color.btn_disenable);

    }

    @Override
    protected CommonPresenter createPresenter() {
        return new CommonPresenter(this);
    }

    @Override
    protected void initData() {
        mBasePresenter.onResume();
        mBasePresenter.getModel();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        String devicesStates = mTvDevicesStatus.getText().toString().toString();
        switch (v.getId()) {

            case R.id.rv_activation:


                if (!TextUtils.isEmpty(devicesStates) && devicesStates.equals(getResources().getString(R.string.activation))) {
                    getActivity().startActivity(new Intent(getActivity(), AlreadlyAcitvationActivity.class));
                } else {
                    getActivity().startActivity(new Intent(getActivity(), ActivationActivity.class));
                }
                break;
            case R.id.rv_save_space:
                if (!TextUtils.isEmpty(devicesStates) && devicesStates.equals(getResources().getString(R.string.activation))) {
                    getActivity().startActivity(new Intent(getActivity(), SaveSpaceActivity.class));
                } else {
                    failure(getResources().getString(R.string.activation_devices));
                }

                break;
            case R.id.rv_preview_video:
                getActivity().startActivity(new Intent(getActivity(), PreviewVideoActivity.class));
                break;
            case R.id.rv_voice:
                if (!TextUtils.isEmpty(devicesStates) && devicesStates.equals(getResources().getString(R.string.activation))) {
                    mBasePresenter.adasRunning();
                } else {
                    failure(getResources().getString(R.string.activation_devices));
                }

                break;
            case R.id.rv_resume:
                if (!TextUtils.isEmpty(devicesStates) && devicesStates.equals(getResources().getString(R.string.activation))) {
                    ResetFactoryDialog activationDialog = new ResetFactoryDialog(mActivity, getResources().getString(R.string.reset_factoy_warning), getResources().getString(R.string.go_on), getResources().getString(R.string.cancel), 1);
                    activationDialog.setOnDialogClickListener(new ResetFactoryDialog.OnDialogClickListener() {
                        @Override
                        public void onDialogLeftDialog() {
                        }

                        @Override
                        public void onDialogRightDialog() {
                            ConnectWifiDialog activationDialog = new ConnectWifiDialog(mActivity, getResources().getString(R.string.is_reset_factoy), getResources().getString(R.string.confirm), getResources().getString(R.string.cancel), 1);
                            activationDialog.setOnDialogClickListener(new ConnectWifiDialog.OnDialogClickListener() {
                                @Override
                                public void onDialogLeftDialog() {
                                }

                                @Override
                                public void onDialogRightDialog() {
                                    mBasePresenter.resumeFactory();
                                }
                            });
                            activationDialog.show();
                        }
                    });
                    activationDialog.show();
                } else {
                    failure(getResources().getString(R.string.activation_devices));
                }
                break;
            case R.id.tv_right_view:
                Intent intent = new Intent(mActivity, HelpActivity.class);
                intent.putExtra(Constants.HELP, R.layout.activity_common_help);
                getActivity().startActivity(intent);
                break;
            case R.id.cb_gps:
                if (!TextUtils.isEmpty(devicesStates) && devicesStates.equals(getResources().getString(R.string.activation))) {
                    mBasePresenter.enableGps(mCbGps.isChecked() ? "1" : "-1");
                } else {
                    failure(getResources().getString(R.string.activation_devices));
                }
                break;
            case R.id.cb_model:
                mCurrentModel = mCbModel.isChecked() ? "1" : "0";
                mBasePresenter.enableModel(mCbModel.isChecked() ? "1" : "0");
                break;
            case R.id.rv_voice_switch:
                mBasePresenter.playSound();
                break;
        }
    }

    @Override
    public SwipeRefreshLayout getLoadiView() {
        return mSwipeLayout;
    }

    @Override
    public void questSuccess(final DeviceState commonEntry) {

        if (!TextUtils.isEmpty(commonEntry.getDeviceState())) {
            mTvDevicesStatus.setText(commonEntry.getDeviceState().equals("true") ? mActivity.getResources().getString(R.string.activation) : mActivity.getResources().getString(R.string.not_active));
        }
        if (!TextUtils.isEmpty(commonEntry.getSimState())) {
            mTvNetStatus.setText(commonEntry.getSimState().equals("0") ? mActivity.getResources().getString(R.string.not_net) : commonEntry.getSimState());
        }
        if (!TextUtils.isEmpty(commonEntry.getGpsState())) {

            switch (commonEntry.getGpsState()) {
                case "-1":
                    if (mCbGps.isChecked())
                        mCbGps.setChecked(false);
                    mTvCurrentLocation.setText(App.getContext().getResources().getString(R.string.location_failure));
                    break;
                case "0":
                    mTvCurrentLocation.setText(App.getContext().getResources().getString(R.string.locating));
                    if (!mCbGps.isChecked())
                        mCbGps.setChecked(true);
                    break;
                case "1":
                    mTvCurrentLocation.setText(App.getContext().getResources().getString(R.string.location_success));
                    if (!mCbGps.isChecked())
                        mCbGps.setChecked(true);
                    break;
            }
        }
    }

    @Override
    public void failure(final String des) {
        if (isShowDialog) {
            mSwipeLayout.setRefreshing(false);
            ConnectWifiDialog activationDialog = new ConnectWifiDialog(mActivity, des, mActivity.getResources().getString(R.string.confirm), mActivity.getResources().getString(R.string.cancel), 0);
            activationDialog.show();
        }
    }

    @Override
    public void onRefresh() {
        mBasePresenter.onResume();
    }


    @Override
    public void showDialog() {
        mLoading = new LoadingDialog(mActivity);
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

    @Override
    public void adasRunning() {
        if (isShowDialog) {
            getActivity().startActivity(new Intent(getActivity(), VoiceSettingActivity.class));
        }
    }

    @Override
    public void setSuccess(final String s) {

        CheckWifiDialog checkWifiDialog = new CheckWifiDialog(mActivity, s);
        checkWifiDialog.show();
//                checkWifiDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
//                        App.startMainActivity();
//                    }
//                });
    }

    @Override
    public void adasModel(String s) {
        if (!TextUtils.isEmpty(s)) {

            switch (s) {
                case "0":
                    mRvVocieSwitch.setEnabled(false);
                    if (mCbModel.isChecked())
                        mCbModel.setChecked(false);
                    break;
                case "1":
                    mRvVocieSwitch.setEnabled(true);
                    if (!mCbModel.isChecked())
                        mCbModel.setChecked(true);
                    break;
            }
        }
    }

    @Override
    public void adasModelFailure() {
        if (!TextUtils.isEmpty(mCurrentModel)) {

            switch (mCurrentModel) {
                case "1":
                    mRvVocieSwitch.setEnabled(false);
                    if (mCbModel.isChecked())
                        mCbModel.setChecked(false);
                    break;
                case "0":
                    mRvVocieSwitch.setEnabled(true);
                    if (!mCbModel.isChecked())
                        mCbModel.setChecked(true);
                    break;
            }
        }
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isShowDialog = false;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            mRvVocieSwitch.setEnabled(true);
        } else {
            mRvVocieSwitch.setEnabled(false);
        }
    }
}
