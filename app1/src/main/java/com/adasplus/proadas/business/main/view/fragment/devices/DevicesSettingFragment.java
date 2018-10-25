package com.adasplus.proadas.business.main.view.fragment.devices;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.common.view.ActivationActivity;
import com.adasplus.proadas.business.connect.view.CheckWifiActivity;
import com.adasplus.proadas.business.connect.view.ConnectWifiDialog;
import com.adasplus.proadas.business.device.presenter.DevicesInfoPresenter;
import com.adasplus.proadas.business.device.view.CalibrationActivity;
import com.adasplus.proadas.business.device.view.DevicesInfoActivity;
import com.adasplus.proadas.business.device.view.EarlyWarningActivity;
import com.adasplus.proadas.business.device.view.HelpActivity;
import com.adasplus.proadas.business.main.presenter.fragment.devices.DevicesSettingPresenter;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.presenter.BasePresenter;
import com.adasplus.proadas.common.view.custom.LoadingDialog;
import com.adasplus.proadas.common.view.fragment.BaseFragment;

/**
 * Created by zhangyapeng on 18-4-4.
 */

public class DevicesSettingFragment extends BaseFragment<DevicesSettingPresenter> implements IDevicesSettingView {

    private LoadingDialog mLoading;
    private Class mCurrentClass;
    private boolean isShowDialog = true;
    @Override
    public void showProgress(String msg) {

    }

    @Override
    public SwipeRefreshLayout getLoadiView() {
        return null;
    }

    @Override
    public void toastNetworkError(String msg) {

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_devices_setting;
    }

    @Override
    protected void initView(View view) {
        isShowDialog =true;
        RelativeLayout rv1 =(RelativeLayout) view.findViewById(R.id.rv_early_setting);
        rv1.setOnClickListener(this);
        RelativeLayout rv2 =(RelativeLayout) view.findViewById(R.id.rv_calibration_setting);
        rv2.setOnClickListener(this);
        RelativeLayout rv3 =(RelativeLayout) view.findViewById(R.id.rv_net_setting);
        rv3.setOnClickListener(this);
        RelativeLayout rv4 =(RelativeLayout) view.findViewById(R.id.rv_devices_info);
        rv4.setOnClickListener(this);
        TextView tvRight = (TextView)getActivity().findViewById(R.id.tv_right_view);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getResources().getString(R.string.use_help));
        tvRight.setOnClickListener(this);
    }

    @Override
    protected DevicesSettingPresenter createPresenter() {
        return new DevicesSettingPresenter(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.rv_early_setting:
                mBasePresenter.requestStatus();
                mCurrentClass=EarlyWarningActivity.class;
//                getActivity().startActivity(new Intent(getActivity(), EarlyWarningActivity.class));
                break;
            case R.id.rv_calibration_setting:
                mCurrentClass=CalibrationActivity.class;
                mBasePresenter.requestStatus();

                break;
            case R.id.rv_net_setting:
                getActivity().startActivity(new Intent(mActivity, CheckWifiActivity.class));
                break;
            case R.id.rv_devices_info:
                getActivity().startActivity(new Intent(mActivity, DevicesInfoActivity.class));
                break;
            case R.id.tv_right_view:
                Intent intent = new Intent(mActivity, HelpActivity.class);
                intent.putExtra(Constants.HELP,R.layout.activity_devices_help);
                getActivity().startActivity(intent);
                break;
        }
    }

    @Override
    public void success() {
        if(isShowDialog){
                    mActivity.startActivity(new Intent(mActivity, mCurrentClass));

        }
    }

    @Override
    public void failure(final String code) {
        if(isShowDialog){
                    ConnectWifiDialog activationDialog = new ConnectWifiDialog(mActivity,code, getResources().getString(R.string.confirm),mActivity.getResources().getString(R.string.cancel),0);
                    activationDialog.show();
        }

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
                if(mLoading !=null){
                    mLoading.stopAnimal();
                    mLoading.cancel();
                    mLoading=null;
                }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isShowDialog = false;
    }
}