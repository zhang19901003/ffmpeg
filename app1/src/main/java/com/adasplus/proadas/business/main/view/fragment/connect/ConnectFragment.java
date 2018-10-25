package com.adasplus.proadas.business.main.view.fragment.connect;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.LocalSocket;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adasplus.easypermissions.AppSettingsDialog;
import com.adasplus.easypermissions.EasyPermissions;
import com.adasplus.proadas.business.R;

import com.adasplus.proadas.business.connect.view.CheckWifiActivity;
import com.adasplus.proadas.business.connect.view.ConnectWifiDialog;
import com.adasplus.proadas.business.device.view.HelpActivity;
import com.adasplus.proadas.business.main.presenter.fragment.connect.ConnectFragmentPresenter;
import com.adasplus.proadas.business.main.view.activity.ICheck;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.view.fragment.BaseFragment;

import java.util.List;

/**
 * Created by zhangyapeng on 18-4-3.
 */

public class ConnectFragment extends BaseFragment<ConnectFragmentPresenter> implements com.adasplus.proadas.business.main.view.fragment.connect.ICheckoutWifi, EasyPermissions.PermissionCallbacks,
        EasyPermissions.RationaleCallbacks {
    private static final int RC_LOCATION_CONTACTS_PERM = 124;
    private static final String[] LOCATION_AND_CONTACTS =
            {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

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
        return R.layout.fragment_connect;
    }

    @Override
    protected void initView(View view) {
        Button bt_connect = (Button) view.findViewById(R.id.bt_connect);
        bt_connect.setOnClickListener(this);
        view.findViewById(R.id.lv_net_help).setOnClickListener(this);
    }

    @Override
    protected ConnectFragmentPresenter createPresenter() {
        return new ConnectFragmentPresenter(this);
    }

    @Override
    protected void initData() {
        mBasePresenter.tryAgain();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bt_connect:
                mBasePresenter.onResume();
                break;
        }
    }

    @Override
    public void isEnable(boolean b) {
        if (b) {
            locationAndContactsTask();
        } else {

            ConnectWifiDialog connectWifiDialog = new ConnectWifiDialog(mActivity, getResources().getString(R.string.open_wifi), getResources().getString(R.string.go), getResources().getString(R.string.cancel), 1);
            connectWifiDialog.setOnDialogClickListener(new ConnectWifiDialog.OnDialogClickListener() {
                @Override
                public void onDialogLeftDialog() {

                }

                @Override
                public void onDialogRightDialog() {
                    Intent intent = new Intent();
                    intent.setAction("android.net.wifi.PICK_WIFI_NETWORK");
                    mActivity.startActivity(intent);

                }
            });
            connectWifiDialog.show();
        }
    }

    @Override
    public void isCommonPage() {

        ICheck mActivity = (ICheck) this.mActivity;
        mActivity.switchFragment(0, 1, null);
    }

    @Override
    public void isFristResult(boolean b) {

    }

    public void locationAndContactsTask() {
        if (hasLocationAndContactsPermissions()) {
            startActivity(new Intent(mActivity, CheckWifiActivity.class));

        } else {
            EasyPermissions.requestPermissions(
                    this,
                    mActivity.getResources().getString(R.string.wifi_permi),
                    RC_LOCATION_CONTACTS_PERM,
                    LOCATION_AND_CONTACTS);
        }
    }

    private boolean hasLocationAndContactsPermissions() {
        return EasyPermissions.hasPermissions(mActivity, LOCATION_AND_CONTACTS);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        startActivity(new Intent(mActivity, CheckWifiActivity.class));

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
