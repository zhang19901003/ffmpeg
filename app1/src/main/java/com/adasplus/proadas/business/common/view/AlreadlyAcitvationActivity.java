package com.adasplus.proadas.business.common.view;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.common.presenter.AlreadlyActivationPresenter;
import com.adasplus.proadas.business.common.presenter.UpdatePresenter;
import com.adasplus.proadas.business.connect.view.ConnectWifiDialog;
import com.adasplus.proadas.business.device.presenter.DevicesInfoPresenter;
import com.adasplus.proadas.business.device.view.IDevicesInfoView;
import com.adasplus.proadas.common.data.Activation;
import com.adasplus.proadas.common.data.DeviceInfo;
import com.adasplus.proadas.common.entry.DevicesInfo;
import com.adasplus.proadas.common.view.activity.BaseActivity;

/**
 * Created by zhangyapeng on 18-4-11.
 */

public class AlreadlyAcitvationActivity extends BaseActivity<AlreadlyActivationPresenter> implements IAlreadVIew, SwipeRefreshLayout.OnRefreshListener {

    private TextView mTvMerchantId;
    private TextView mTvPhone;
    private TextView mTvCar;
    private SwipeRefreshLayout mSwipeLayout;

    @Override
    protected AlreadlyActivationPresenter createPresenter() {
        return new AlreadlyActivationPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_alread_activated;
    }

    @Override
    protected void initView() {
        TextView tvTitle = findViewById(R.id.tv_main_title_textview);
        tvTitle.setText(getResources().getString(R.string.alreadly_activation));
        LinearLayout lvLeftView = findViewById(R.id.ll_left_layout);
        lvLeftView.setVisibility(View.VISIBLE);
        lvLeftView.setOnClickListener(this);
        TextView tvRight = (TextView) findViewById(R.id.tv_right_view);
        tvRight.setText(getResources().getString(R.string.update_info));
        tvRight.setOnClickListener(this);
        mTvMerchantId = (TextView) findViewById(R.id.tv_marchant);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mTvCar = (TextView) findViewById(R.id.tv_car);
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
                Intent intent = new Intent(this, UpdateInfoActivIty.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public SwipeRefreshLayout getLoadiView() {
        return mSwipeLayout;
    }

    @Override
    public void success(final Activation devicesInfo) {
        if(!isShowDialog){
            return;
        }
                if(!TextUtils.isEmpty(devicesInfo.getMerchantId())){
                    mTvMerchantId.setText(devicesInfo.getMerchantId());
                }
                if(!TextUtils.isEmpty(devicesInfo.getPlateNum())){
                    mTvCar.setText(devicesInfo.getPlateNum());
                }else {
                    mTvCar.setText("");
                }
                if(!TextUtils.isEmpty(devicesInfo.getPhoneNum())){
                    mTvPhone.setText(devicesInfo.getPhoneNum());
                }else {
                    mTvPhone.setText("");
                }
    }

    @Override
    public void questFailure() {
        if(!isShowDialog){
            return;
        }
                ConnectWifiDialog activationDialog = new ConnectWifiDialog(AlreadlyAcitvationActivity.this,getResources().getString(R.string.quest_failue),getResources().getString(R.string.confirm),getResources().getString(R.string.cancel),0);
                activationDialog.show();
    }

    @Override
    public void onRefresh() {
        mBasePresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCreate=true;
    }
}


