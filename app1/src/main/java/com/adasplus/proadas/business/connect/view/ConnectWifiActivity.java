package com.adasplus.proadas.business.connect.view;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.connect.presenter.ConnPresenter;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.entry.WifiMessage;
import com.adasplus.proadas.common.view.activity.BaseActivity;
import com.adasplus.proadas.common.view.custom.LoadingDialog;


/**
 * Created by zhangyapeng on 18-4-3.
 */

public class ConnectWifiActivity extends BaseActivity<ConnPresenter> implements IConnResultCallback {

    private EditText mEtPassWord;
    private WifiMessage mWifiMessage;
    private TextView mTvWifiName;
    private LoadingDialog mLoading;

    @Override
    public void imageCallBack(WifiMessage wifiInfo) {

    }

    @Override
    public void failueDialog() {
        if(!isShowDialog){
            return;
        }
        final ConnectWifiDialog connectWifiDialog = new ConnectWifiDialog(this,getResources().getString(R.string.password_error),getResources().getString(R.string.restart),getResources().getString(R.string.cancel),1);
        connectWifiDialog.setOnDialogClickListener(new ConnectWifiDialog.OnDialogClickListener() {
            @Override
            public void onDialogLeftDialog() {

                ConnectWifiActivity.this.finish();
            }

            @Override
            public void onDialogRightDialog() {

            }
        });
        connectWifiDialog.show();
    }

    @Override
    protected ConnPresenter createPresenter() {
        return new ConnPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_connect_wifi;
    }

    @Override
    protected void initView() {

        TextView tvTitle = findViewById(R.id.tv_main_title_textview);
        tvTitle.setText(getResources().getString(R.string.connect_setting));
        TextView tvLeftBack = (TextView) findViewById(R.id.tv_left_view);
        tvLeftBack.setOnClickListener(this);
        tvLeftBack.setText(getResources().getString(R.string.cancel));
        TextView tvRightJoin = (TextView) findViewById(R.id.tv_right_view);
        tvRightJoin.setText(getResources().getString(R.string.join));
        tvRightJoin.setOnClickListener(this);
        tvLeftBack.setVisibility(View.VISIBLE);
        mEtPassWord = (EditText) findViewById(R.id.et_password);
        mTvWifiName = (TextView) findViewById(R.id.tv_wifi_name);
        CheckBox mCbPassword = (CheckBox) findViewById(R.id.cb_password);
        mCbPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mEtPassWord.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
                    mEtPassWord.setSelection(mEtPassWord.getText().toString().trim().length());
                }else{
                    mEtPassWord.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mEtPassWord.setSelection(mEtPassWord.getText().toString().trim().length());
                }
            }
        });

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String bSsid = intent.getStringExtra(Constants.BSSID);
        String ssid = intent.getStringExtra(Constants.SSID);
        String capa = intent.getStringExtra(Constants.CAPABILITIES);
        int type = intent.getIntExtra(Constants.TYPE,0);
        mWifiMessage = new WifiMessage(bSsid,capa,ssid,type);
        if(!TextUtils.isEmpty(ssid)){
            mTvWifiName.setText(ssid);
        }
        if(mBasePresenter!=null){
            mBasePresenter.onResume();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_left_view:
                finish();
                break;
            case R.id.tv_right_view:
                if (TextUtils.isEmpty(mEtPassWord.getText().toString())) {
                    showToast(getResources().getString(R.string.password_null));
                } else {
                    Log.e("Adas","wifiMessage    ::  "+mWifiMessage.toString());
                    mBasePresenter.connect(mWifiMessage,mEtPassWord.getText().toString());
                }
                break;
        }
    }

    @Override
    public SwipeRefreshLayout getLoadiView() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBasePresenter.disConnect();
    }

    @Override
    public void showProgress() {
        mLoading = new LoadingDialog(this);
        mLoading.setShowAnimation(true);
        mLoading.setString(getResources().getString(R.string.connect_net_ing));
        mLoading.setImage(R.drawable.loading);
        mLoading.show();
    }

    @Override
    public void hideProgress() {
        if(mLoading !=null){
            mLoading.stopAnimal();
            mLoading.cancel();
            mLoading=null;
        }
    }
}
