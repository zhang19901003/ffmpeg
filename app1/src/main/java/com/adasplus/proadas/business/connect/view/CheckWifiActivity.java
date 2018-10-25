package com.adasplus.proadas.business.connect.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adasplus.easypermissions.AppSettingsDialog;
import com.adasplus.easypermissions.EasyPermissions;
import com.adasplus.proadas.App;
import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.connect.adapter.WifiListAdaspter;
import com.adasplus.proadas.business.connect.presenter.CheckPresenter;
import com.adasplus.proadas.business.device.view.HelpActivity;
import com.adasplus.proadas.business.main.view.activity.MainActivity;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.util.gps.AdasGps;
import com.adasplus.proadas.common.util.wifi.WifiManage;
import com.adasplus.proadas.common.view.activity.BaseActivity;

import java.util.List;

/**
 * Created by zhangyapeng on 18-4-3.
 */

public class CheckWifiActivity extends BaseActivity<CheckPresenter> implements AdapterView.OnItemClickListener, ICheckResultCallback ,EasyPermissions.PermissionCallbacks,
        EasyPermissions.RationaleCallbacks{
    private ListView mLvWifi;
    private WifiListAdaspter mWifiAdapter;
    private List<ScanResult> mList;
    private LinearLayout mLvConnect;
    private RelativeLayout mRvConnectList;
    private TextView mTvConnectName;
    private static final int RC_LOCATION_CONTACTS_PERM = 124;
    private static final String[] LOCATION_AND_CONTACTS =
            {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ScanResult scanResult = mList.get(position);
        if (scanResult == null) {
            return;
        }
        Log.e("Adas",scanResult.BSSID+"   "+scanResult.SSID+"    "+scanResult.capabilities+"    "+scanResult.describeContents());
        Intent intent = new Intent(this, ConnectWifiActivity.class);
        intent.putExtra(Constants.BSSID, scanResult.BSSID);
        intent.putExtra(Constants.SSID, scanResult.SSID);
        intent.putExtra(Constants.CAPABILITIES, scanResult.capabilities);
        intent.putExtra(Constants.TYPE, scanResult.describeContents());
        startActivity(intent);
    }

    @Override
    public void imageCallBack(List<ScanResult> list) {
        this.mList = list;
        mWifiAdapter.addList(list);
    }


    @Override
    public void currentConnectWifi(WifiInfo wifiInfo) {
        if (wifiInfo == null) {
            mLvConnect.setVisibility(View.GONE);
            mRvConnectList.setVisibility(View.GONE);
        } else {
            mLvConnect.setVisibility(View.VISIBLE);
            mRvConnectList.setVisibility(View.VISIBLE);
            mTvConnectName.setText(wifiInfo.getSSID());
        }
    }

    @Override
    public void showDialog() {
        if(!isShowDialog){
            return;
        }
        CheckWifiDialog connectWifiDialog = new CheckWifiDialog(this,getResources().getString(R.string.connect_success));
        connectWifiDialog.setCanceledOnTouchOutside(false);
        connectWifiDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                App.remove(Constants.MAINACTIVITY);
                Intent intent = new Intent(CheckWifiActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        connectWifiDialog.show();
        Constants.sCurrentSSID = null;
    }

    @Override
    public void showGpsInfo() {
        if(!isShowDialog){
            return;
        }
        ConnectWifiDialog connectWifiDialog = new ConnectWifiDialog(CheckWifiActivity.this,getResources().getString(R.string.opne_gps),getResources().getString(R.string.confirm),getResources().getString(R.string.cancel),1);
        connectWifiDialog.setOnDialogClickListener(new ConnectWifiDialog.OnDialogClickListener(){

            @Override
            public void onDialogLeftDialog() {

            }

            @Override
            public void onDialogRightDialog() {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 10);
            }
        });
        connectWifiDialog.show();
    }

    @Override
    protected CheckPresenter createPresenter() {
        return new CheckPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_wifi;
    }

    @Override
    protected void initView() {
        mLvWifi = (ListView) findViewById(R.id.lv_wifi);
        mLvWifi.setOnItemClickListener(this);
        TextView tvTitle = (TextView) findViewById(R.id.tv_main_title_textview);
        tvTitle.setText(getResources().getString(R.string.net_setting));
        mLvConnect = (LinearLayout) findViewById(R.id.lv_connect);
        mRvConnectList = (RelativeLayout) findViewById(R.id.rv_connect_item);
        mTvConnectName = (TextView) findViewById(R.id.tv_wifi_connect);
        LinearLayout lvLeftView = findViewById(R.id.ll_left_layout);
        lvLeftView.setVisibility(View.VISIBLE);
        lvLeftView.setOnClickListener(this);
        TextView tvRight = (TextView) findViewById(R.id.tv_right_view);
        tvRight.setText(getResources().getString(R.string.use_help));
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setOnClickListener(this);
        findViewById(R.id.rv_connect_item).setOnClickListener(this);
    }

    @Override
    protected void initData() {

        locationAndContactsTask();
    }



    @Override
    protected void onPause() {
        super.onPause();
        mCreate = true;
        mBasePresenter.onPause();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_left_layout:
                finish();
                break;
            case R.id.tv_right_view:
                Intent intent = new Intent(this, HelpActivity.class);
                intent.putExtra(Constants.HELP,R.layout.activity_net_help);
                startActivity(intent);
                break;
            case R.id.rv_connect_item:

                ConnectWifiDialog connectWifiDialog = new ConnectWifiDialog(CheckWifiActivity.this,getResources().getString(R.string.disconnect_connect),getResources().getString(R.string.confirm),getResources().getString(R.string.cancel),1);
                connectWifiDialog.setOnDialogClickListener(new ConnectWifiDialog.OnDialogClickListener(){

                    @Override
                    public void onDialogLeftDialog() {

                    }

                    @Override
                    public void onDialogRightDialog() {
                        WifiManage  mWifiManager = WifiManage.getInstance(App.getContext());
                        mWifiManager.clearWifiConfig(mWifiManager.getCurrentWifiInfo().getSSID());
                        mWifiManager.disconnectWifi();
                    }
                });
                connectWifiDialog.show();
                break;
        }
    }

    @Override
    public SwipeRefreshLayout getLoadiView() {
        return null;
    }

    public void locationAndContactsTask() {
        if (hasLocationAndContactsPermissions()) {
            mBasePresenter.onResume();

            mWifiAdapter = new WifiListAdaspter<>(this, R.layout.lv_wifi_item);
            mLvWifi.setAdapter(mWifiAdapter);
        } else {
            EasyPermissions.requestPermissions(
                    this,
                     getResources().getString(R.string.wifi_permi),
                    RC_LOCATION_CONTACTS_PERM,
                    LOCATION_AND_CONTACTS);
        }
    }


    private boolean hasLocationAndContactsPermissions() {
        return EasyPermissions.hasPermissions(this, LOCATION_AND_CONTACTS);
    }
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        mBasePresenter.onResume();
        mWifiAdapter = new WifiListAdaspter<>(this, R.layout.lv_wifi_item);
        mLvWifi.setAdapter(mWifiAdapter);
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onRationaleAccepted(int requestCode) {

    }

    @Override
    public void onRationaleDenied(int requestCode) {

    }


}
