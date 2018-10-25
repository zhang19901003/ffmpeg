package com.adasplus.proadas.business.main.view.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.adasplus.proadas.App;
import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.main.presenter.activity.MainPresenter;
import com.adasplus.proadas.business.main.view.fragment.about.AboutFragment;
import com.adasplus.proadas.business.main.view.fragment.common.CommonFragment;
import com.adasplus.proadas.business.main.view.fragment.connect.ConnectFragment;
import com.adasplus.proadas.business.main.view.fragment.devices.DevicesSettingFragment;
import com.adasplus.proadas.business.main.view.fragment.sim.SimFragment;
import com.adasplus.proadas.common.util.Utils;
import com.adasplus.proadas.common.view.activity.BaseActivity;
import com.adasplus.proadas.common.view.fragment.BaseFragment;
import java.util.ArrayList;


public class MainActivity extends BaseActivity<MainPresenter> implements ICheck {

    private TextView mTvTitle;
    private ArrayList<BaseFragment> mBaseFragment;
    private RadioGroup mRadioGroup;

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mTvTitle = (TextView) findViewById(R.id.tv_main_title_textview);
        mTvTitle.setText(getResources().getString(R.string.title));
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_main);
        mRadioGroup.setOnCheckedChangeListener(new CheckChangeListener());
        initFragment();
    }

    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new ConnectFragment());
        mBaseFragment.add(new CommonFragment());
        mBaseFragment.add(new DevicesSettingFragment());
        mBaseFragment.add(new SimFragment());
        mBaseFragment.add(new AboutFragment());
    }

    @Override
    protected void initData() {
        if (mBasePresenter != null) {
            mBasePresenter.onResume();
        }
    }

    @Override
    public SwipeRefreshLayout getLoadiView() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TODO  MVP

    }

    @Override
    public void switchFragment(int from, int to, Bundle argument) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_main_content, mBaseFragment.get(to)).commit();
        switch (to) {
            case 1:
                enableRadioGroup(mRadioGroup);
                mRadioGroup.check(R.id.rb_common_frame);
                mTvTitle.setText(getResources().getString(R.string.common));
                break;
            case 0:
                mRadioGroup.clearCheck();
                disableRadioGroup(mRadioGroup);
                break;
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

    class CheckChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_common_frame:
                    switchFragment(0, 1, null);
                    mTvTitle.setText(getResources().getString(R.string.common));
                    break;
                case R.id.rb_thirdparty:
                    switchFragment(0, 2, null);
                    mTvTitle.setText(getResources().getString(R.string.device_setting));
                    break;
                case R.id.rb_other:
                    switchFragment(0, 4, null);
                    mTvTitle.setText(getResources().getString(R.string.about));
                    break;
                default:
                    break;
            }
        }
    }


}
