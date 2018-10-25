package com.adasplus.proadas.business.device.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.connect.view.ConnectWifiDialog;
import com.adasplus.proadas.business.device.presenter.DevicesInfoPresenter;
import com.adasplus.proadas.common.data.DeviceInfo;
import com.adasplus.proadas.common.entry.DevicesInfo;
import com.adasplus.proadas.common.view.activity.BaseActivity;

/**
 * Created by zhangyapeng on 18-4-6.
 */

public class DevicesInfoActivity extends BaseActivity<DevicesInfoPresenter> implements IDevicesInfoView, SwipeRefreshLayout.OnRefreshListener {

    private TextView mTvImei;
    private TextView mTvUuid;
    private TextView mTvCar;
    private TextView mTvPhone;
    private TextView mTvActiva;
    private TextView mTvAppVersion;
    private TextView mTvSystemVerion;
    private SwipeRefreshLayout mSwipeLayout;

    @Override
    protected DevicesInfoPresenter createPresenter() {
        return new DevicesInfoPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_devices_info;
    }

    @Override
    protected void initView() {

        TextView tvTitle = (TextView) findViewById(R.id.tv_main_title_textview);
        tvTitle.setText(getResources().getString(R.string.devices_info));
        LinearLayout lvLeftView = findViewById(R.id.ll_left_layout);
        lvLeftView.setVisibility(View.VISIBLE);
        lvLeftView.setOnClickListener(this);
        mTvImei = (TextView) findViewById(R.id.tv_imei);
        mTvUuid = (TextView) findViewById(R.id.tv_uuid);
        mTvCar = (TextView) findViewById(R.id.tv_car);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mTvActiva = (TextView) findViewById(R.id.tv_activa);
        mTvAppVersion = (TextView) findViewById(R.id.tv_app);
        mTvSystemVerion = (TextView) findViewById(R.id.tv_system_version);
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
        }
    }

    @Override
    public SwipeRefreshLayout getLoadiView() {
        return mSwipeLayout;
    }

    @Override
    public void success(final DeviceInfo devicesInfo) {
        if (!isShowDialog) {
            return;
        }
        mTvImei.setText(TextUtils.isEmpty(devicesInfo.getImei()) ? "" : devicesInfo.getImei());
        mTvUuid.setText(TextUtils.isEmpty(devicesInfo.getUuid()) ? "" : devicesInfo.getUuid().split(",")[0]);
        mTvCar.setText(TextUtils.isEmpty(devicesInfo.getPlateNum()) ? "" : devicesInfo.getPlateNum());
        mTvPhone.setText(TextUtils.isEmpty(devicesInfo.getPhoneNum()) ? "" : devicesInfo.getPhoneNum());
        mTvActiva.setText(TextUtils.isEmpty(devicesInfo.getActivationState()) ? "" : devicesInfo.getActivationState().equals("true") ? getResources().getString(R.string.activation) : getResources().getString(R.string.not_active));
        mTvAppVersion.setText(TextUtils.isEmpty(devicesInfo.getAppVersion()) ? "" : devicesInfo.getAppVersion());
        mTvSystemVerion.setText(TextUtils.isEmpty(devicesInfo.getOsVersion()) ? "" : devicesInfo.getOsVersion());

    }

    @Override
    public void questFailure() {
        if (!isShowDialog) {
            return;
        }
        ConnectWifiDialog activationDialog = new ConnectWifiDialog(DevicesInfoActivity.this, getResources().getString(R.string.quest_failue), getResources().getString(R.string.confirm), getResources().getString(R.string.cancel), 0);
        activationDialog.show();

    }


    @Override
    public void onRefresh() {
        mBasePresenter.onResume();
    }
}
