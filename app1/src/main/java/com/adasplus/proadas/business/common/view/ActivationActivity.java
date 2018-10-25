package com.adasplus.proadas.business.common.view;

import android.content.DialogInterface;
import android.graphics.Rect;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.common.presenter.ActivationPresenter;
import com.adasplus.proadas.business.connect.view.CheckWifiDialog;
import com.adasplus.proadas.business.connect.view.ConnectWifiDialog;
import com.adasplus.proadas.business.device.view.CalibrationActivity;
import com.adasplus.proadas.common.data.Activation;
import com.adasplus.proadas.common.entry.DevicesInfo;
import com.adasplus.proadas.common.view.activity.BaseActivity;
import com.adasplus.proadas.common.view.custom.LoadingDialog;
import com.adasplus.proadas.common.view.custom.ScrollLinearLayout;

/**
 * Created by zhangyapeng on 18-4-6.
 */

public class ActivationActivity extends BaseActivity<ActivationPresenter> implements IActivation {

    private EditText mEtBussiness;
    private EditText mEtCar;
    private EditText mEtPhone;
    private LoadingDialog mLoading;
    private ScrollLinearLayout mLl;

    @Override
    protected ActivationPresenter createPresenter() {
        return new ActivationPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_activation;
    }

    @Override
    protected void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tv_main_title_textview);
        tvTitle.setText(getResources().getString(R.string.activation));
        LinearLayout lvLeftView = findViewById(R.id.ll_left_layout);
        lvLeftView.setVisibility(View.VISIBLE);
        lvLeftView.setOnClickListener(this);
        TextView tvRightActivation = (TextView) findViewById(R.id.tv_right_view);
        tvRightActivation.setText(getResources().getString(R.string.finish));
        tvRightActivation.setOnClickListener(this);
        Button btnActivation = (Button) findViewById(R.id.btn_activation);
        btnActivation.setOnClickListener(this);
        mEtBussiness = (EditText) findViewById(R.id.et_business_number);
        mEtCar = (EditText) findViewById(R.id.et_car_number);
        mEtPhone = (EditText) findViewById(R.id.et_phone_number);
        mLl = (ScrollLinearLayout) findViewById(R.id.lv_activation);
       final  LinearLayout mLvPhone = (LinearLayout) findViewById(R.id.lv_phone);
//        TextView textView = findViewById(R.id.tv_null);
//        mEtPhone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                autoScrollView(mLl, mLvPhone);
//            }
//        });
        autoScrollView(mLl, mLvPhone);
//        mEtPhone.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch ()
//                autoScrollView(mLl, mLvPhone);
//                return false;
//            }
//        });

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_left_layout:
                finish();
                break;
            case R.id.tv_right_view:
            case R.id.btn_activation:
                String bussiness = mEtBussiness.getText().toString();
                String carNumber = mEtCar.getText().toString();
                String phoneNumber = mEtPhone.getText().toString();
                mBasePresenter.activationCallBack(bussiness, carNumber, phoneNumber);
                break;
        }
    }

    @Override
    public SwipeRefreshLayout getLoadiView() {
        return null;
    }

    @Override
    public void registerCode(final String code) {
        if (!isShowDialog) {
            return;
        }
        ConnectWifiDialog activationDialog = new ConnectWifiDialog(ActivationActivity.this, code, getResources().getString(R.string.confirm), getResources().getString(R.string.cancel), 0);
        activationDialog.show();
    }

    @Override
    public void show(final String car, final String phone) {
        if (!isShowDialog) {
            return;
        }
        if (!TextUtils.isEmpty(car))
            mEtPhone.setText(phone);
        if (!TextUtils.isEmpty(phone))
            mEtCar.setText(car);
    }

    @Override
    public void success(final Activation activation) {
        if (!isShowDialog) {
            return;
        }
        mEtCar.setText(activation.getPlateNum());
        mEtPhone.setText(activation.getPhoneNum());
        CheckWifiDialog calibrationDialog = new CheckWifiDialog(ActivationActivity.this, getResources().getString(R.string.activation_success));
        calibrationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });
        calibrationDialog.show();
    }

    @Override
    public void showDialog() {
        mLoading = new LoadingDialog(ActivationActivity.this);
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

    private int scrollToPosition = 0;

    private void autoScrollView(final ScrollLinearLayout root, final LinearLayout scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect rect = new Rect();
                        root.getWindowVisibleDisplayFrame(rect);
                        root.getHeight();
                        int rootInvisibleHeight = (int) (root.getHeight() + root.getY() - rect.bottom);
                        Log.e("Adas", "   " + rect.bottom + "  " + root.getHeight() + "   " + root.getY() + "    " + rootInvisibleHeight);
                        if (rootInvisibleHeight > 10) {
                            int[] location = new int[2];
                            scrollToView.getLocationInWindow(location);
                            int scrollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;
                            scrollToPosition += scrollHeight;
                        } else {
                            scrollToPosition = 0;
                        }

                     //    root.startMove(scrollToPosition);
                         root.scrollTo(0, scrollToPosition);

                    }
                });
    }


}
