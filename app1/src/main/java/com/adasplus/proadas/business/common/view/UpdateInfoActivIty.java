package com.adasplus.proadas.business.common.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.common.presenter.UpdatePresenter;
import com.adasplus.proadas.business.connect.view.CheckWifiDialog;
import com.adasplus.proadas.business.connect.view.ConnectWifiDialog;
import com.adasplus.proadas.common.Constant;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.data.Activation;
import com.adasplus.proadas.common.entry.DevicesInfo;
import com.adasplus.proadas.common.entry.UpdateInfo;
import com.adasplus.proadas.common.view.activity.BaseActivity;
import com.adasplus.proadas.common.view.custom.LoadingDialog;

/**
 * Created by zhangyapeng on 18-4-11.
 */

public class UpdateInfoActivIty extends BaseActivity<UpdatePresenter> implements IUpdateVIew, SwipeRefreshLayout.OnRefreshListener {

    private TextView mTvBussiness;
    private EditText mEtCar;
    private EditText mEtPhone;
    private LoadingDialog mLoading;
    private String merchantId;
    private SwipeRefreshLayout mSwipeLayout;

    @Override
    protected UpdatePresenter createPresenter() {
        return new UpdatePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_info;
    }

    @Override
    protected void initView() {


        TextView tvTitle = findViewById(R.id.tv_main_title_textview);
        tvTitle.setText(getResources().getString(R.string.update_info));
        LinearLayout lvLeftView = findViewById(R.id.ll_left_layout);
        lvLeftView.setVisibility(View.VISIBLE);
        lvLeftView.setOnClickListener(this);
        TextView tvRight = (TextView) findViewById(R.id.tv_right_view);
        tvRight.setText(getResources().getString(R.string.save));
        tvRight.setOnClickListener(this);
        mTvBussiness = (TextView) findViewById(R.id.tv_bussiness_number);
        mEtCar = (EditText) findViewById(R.id.et_car_number);
        mEtPhone = (EditText) findViewById(R.id.et_phone_number);
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
                String bussiness = mTvBussiness.getText().toString();
                String carNumber = mEtCar.getText().toString();
                String phoneNumber = mEtPhone.getText().toString();
                mBasePresenter.updateInfoCallBack(bussiness, carNumber, phoneNumber);
                break;
        }
    }

    @Override
    public SwipeRefreshLayout getLoadiView() {
        return mSwipeLayout;
    }

    @Override
    public void updateSuccess(Activation devicesInfo) {
        if (!isShowDialog) {
            return;
        }
        this.merchantId = devicesInfo.getMerchantId();

        if (!TextUtils.isEmpty(merchantId)) {
            mTvBussiness.setText(merchantId);
        }

        if (!TextUtils.isEmpty(devicesInfo.getPlateNum())) {
            mEtCar.setText(devicesInfo.getPlateNum());
        }
        if (!TextUtils.isEmpty(devicesInfo.getPhoneNum())) {
            mEtPhone.setText(devicesInfo.getPhoneNum());
        }

    }

    @Override
    public void updateCode(final String code) {
        if (!isShowDialog) {
            return;
        }
        ConnectWifiDialog activationDialog = new ConnectWifiDialog(UpdateInfoActivIty.this, code, getResources().getString(R.string.confirm), getResources().getString(R.string.cancel), 0);
        activationDialog.show();

    }

    @Override
    public void updateSuccess() {
        if (!isShowDialog) {
            return;
        }
        CheckWifiDialog calibrationDialog = new CheckWifiDialog(UpdateInfoActivIty.this, getResources().getString(R.string.update_success));
        calibrationDialog.show();
        calibrationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });

    }

    @Override
    public void showDialog() {
        mLoading = new LoadingDialog(UpdateInfoActivIty.this);
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
    public void onRefresh() {
        mBasePresenter.onResume();
    }
}

