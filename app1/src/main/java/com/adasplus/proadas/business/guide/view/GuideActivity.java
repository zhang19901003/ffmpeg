package com.adasplus.proadas.business.guide.view;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.main.presenter.activity.MainPresenter;
import com.adasplus.proadas.business.main.presenter.fragment.connect.ConnectFragmentPresenter;
import com.adasplus.proadas.business.main.view.activity.ICheck;
import com.adasplus.proadas.business.main.view.activity.MainActivity;
import com.adasplus.proadas.business.main.view.fragment.about.AboutFragment;
import com.adasplus.proadas.business.main.view.fragment.common.CommonFragment;
import com.adasplus.proadas.business.main.view.fragment.connect.ConnectFragment;
import com.adasplus.proadas.business.main.view.fragment.connect.ICheckoutWifi;
import com.adasplus.proadas.business.main.view.fragment.devices.DevicesSettingFragment;
import com.adasplus.proadas.business.main.view.fragment.sim.SimFragment;
import com.adasplus.proadas.common.view.activity.BaseActivity;
import com.adasplus.proadas.common.view.fragment.BaseFragment;

import java.util.ArrayList;


public class GuideActivity extends BaseActivity<ConnectFragmentPresenter> implements ICheckoutWifi {
    private int currentDialog = 0;
    private TextView mTvTitle;
    private RadioGroup mRadioGroup;
    private RadioButton mRgDevices;
    private RadioButton mRgCommon;
    private TextView mTvDashed1;
    private RelativeLayout mTvDesc;
    private TextView mTvDashed2;
    private TextView mTvDashed3;
    private Dialog mDialog;
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected ConnectFragmentPresenter createPresenter() {
        return new ConnectFragmentPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {
        mTvTitle = (TextView) findViewById(R.id.tv_main_title_textview);
        mTvTitle.setText(getResources().getString(R.string.title));
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_main);
        disableRadioGroup(mRadioGroup);
    }
    @Override
    protected void initData() {
        if (mBasePresenter != null) {
            mBasePresenter.isFrist();
        }
    }

    @Override
    public SwipeRefreshLayout getLoadiView() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBasePresenter.setInit();
    }



    public void disableRadioGroup(RadioGroup testRadioGroup) {
        for (int i = 0; i < testRadioGroup.getChildCount(); i++) {
            testRadioGroup.getChildAt(i).setEnabled(false);
        }
    }



    @Override
    public void isEnable(boolean b) {

    }

    @Override
    public void isCommonPage() {

    }

    @Override
    public void isFristResult(boolean b) {
        if (b) {

            int statusBarHeight = 50;
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            }
            mDialog = new Dialog(GuideActivity.this, R.style.dialog_popu);
            mDialog.show();
            LayoutInflater inflater = LayoutInflater.from(GuideActivity.this);
            View viewDialog = inflater.inflate(R.layout.dialog3_connect, null);
            Display display = GuideActivity.this.getWindowManager().getDefaultDisplay();
            int width = display.getWidth();
            int height = display.getHeight();
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height - statusBarHeight);
            mDialog.setContentView(viewDialog, layoutParams);
            mTvDesc = mDialog.findViewById(R.id.rv_des);
            mTvDashed1 = mDialog.findViewById(R.id.tv_dashed_1);
            mTvDashed2 = mDialog.findViewById(R.id.tv_dashed_2);
            mTvDashed3 = mDialog.findViewById(R.id.tv_dashed_3);

            mDialog.findViewById(R.id.fl_onclick).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentDialog == 0) {
                        mRgCommon.setVisibility(View.VISIBLE);
                        mRgDevices.setVisibility(View.INVISIBLE);
                        mTvDashed2.setVisibility(View.VISIBLE);
                        mTvDashed3.setVisibility(View.INVISIBLE);
                        mTvDashed1.setVisibility(View.INVISIBLE);
                        mTvDesc.setVisibility(View.INVISIBLE);
                        currentDialog = 1;
                        return;
                    }

                    if (currentDialog == 1) {
                        mRgCommon.setVisibility(View.INVISIBLE);
                        mRgDevices.setVisibility(View.VISIBLE);
                        mTvDashed3.setVisibility(View.VISIBLE);
                        mTvDashed2.setVisibility(View.INVISIBLE);
                        mTvDashed1.setVisibility(View.INVISIBLE);
                        mTvDesc.setVisibility(View.INVISIBLE);
                        currentDialog = 2;
                        return;
                    }

                    if (currentDialog == 2) {
                        mDialog.dismiss();
                        Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    }
                }
            });
            mRgDevices = mDialog.findViewById(R.id.rg_devices);
            mRgDevices.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Adas", "rgbutton onclick");
                }
            });
            mRgCommon = mDialog.findViewById(R.id.rg_common);
            mRgCommon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Adas", "rgbutton onclick");
                }
            });

            mRgCommon.setVisibility(View.INVISIBLE);
            mRgDevices.setVisibility(View.INVISIBLE);
            mTvDashed2.setVisibility(View.INVISIBLE);
            mTvDashed3.setVisibility(View.INVISIBLE);
        }else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mBasePresenter.setInit();
    }


}
