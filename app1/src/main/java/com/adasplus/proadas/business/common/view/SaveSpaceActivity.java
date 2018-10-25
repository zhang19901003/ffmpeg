package com.adasplus.proadas.business.common.view;

import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adasplus.proadas.App;
import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.common.presenter.SaveSpacePresenter;
import com.adasplus.proadas.business.connect.view.CheckWifiDialog;
import com.adasplus.proadas.business.connect.view.ConnectWifiDialog;
import com.adasplus.proadas.business.device.view.EarlyWarningActivity;
import com.adasplus.proadas.common.data.SdcardInfo;
import com.adasplus.proadas.common.entry.CalibrateParam;
import com.adasplus.proadas.common.entry.DeviceSpace;
import com.adasplus.proadas.common.presenter.BasePresenter;
import com.adasplus.proadas.common.view.activity.BaseActivity;

import org.w3c.dom.Text;

/**
 * Created by zhangyapeng on 18-4-6.
 */

public class SaveSpaceActivity extends BaseActivity<SaveSpacePresenter> implements ISaveSpaceView, SwipeRefreshLayout.OnRefreshListener {

    private TextView mTvTotal;
    private TextView mTvUsable;
    private TextView mTvuse;
    private SwipeRefreshLayout mSwipeLayout;
    private LinearLayout mLvExist;
    private RelativeLayout mRvExist;

    @Override
    protected SaveSpacePresenter createPresenter() {
        return new SaveSpacePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_memory_space;
    }

    @Override
    protected void initView() {
        TextView tvTitle = findViewById(R.id.tv_main_title_textview);
        tvTitle.setText(getResources().getString(R.string.storage_space));
        LinearLayout lvLeftView = findViewById(R.id.ll_left_layout);
        lvLeftView.setVisibility(View.VISIBLE);
        lvLeftView.setOnClickListener(this);
        RelativeLayout rvFormat = (RelativeLayout) findViewById(R.id.rv_format);
        rvFormat.setOnClickListener(this);
        mTvTotal = (TextView) findViewById(R.id.tv_total);
        mTvUsable = (TextView) findViewById(R.id.tv_useable);
        mTvuse = (TextView) findViewById(R.id.tv_use);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeLayout.setProgressBackgroundColorSchemeResource(R.color.btn_disenable);
        mLvExist = (LinearLayout) findViewById(R.id.lv_exist);
        mRvExist = (RelativeLayout) findViewById(R.id.rv_exist);
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
            case R.id.rv_format:
                ConnectWifiDialog activationDialog = new ConnectWifiDialog(SaveSpaceActivity.this, getResources().getString(R.string.is_format), getResources().getString(R.string.confirm), getResources().getString(R.string.cancel), 1);
                activationDialog.setOnDialogClickListener(new ConnectWifiDialog.OnDialogClickListener() {
                    @Override
                    public void onDialogLeftDialog() {

                    }

                    @Override
                    public void onDialogRightDialog() {

                        mBasePresenter.format();
                    }
                });
                activationDialog.show();

                break;

        }
    }

    @Override
    public SwipeRefreshLayout getLoadiView() {
        return mSwipeLayout;
    }

    @Override
    public void updateCode(final String code) {
        if (!isShowDialog) {
            return;
        }
        ConnectWifiDialog activationDialog = new ConnectWifiDialog(SaveSpaceActivity.this, code, getResources().getString(R.string.confirm), getResources().getString(R.string.cancel), 0);
        activationDialog.show();

    }

    @Override
    public void success(final SdcardInfo space, int code) {
        if (!isShowDialog) {
            return;
        }
        switch (code) {
            case 0:
                mRvExist.setVisibility(View.GONE);
                mLvExist.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(space.getTotal())) {
                    mTvTotal.setText(space.getTotal());
                }
                if (!TextUtils.isEmpty(space.getAvailable())) {
                    mTvuse.setText(space.getAvailable());
                }
                if (!TextUtils.isEmpty(space.getUsed())) {
                    mTvUsable.setText(space.getUsed());
                }

                break;
            case 1:
                if (!isShowDialog) {
                    return;
                }

                CheckWifiDialog calibrationDialog = new CheckWifiDialog(SaveSpaceActivity.this, getResources().getString(R.string.format_success));
                calibrationDialog.show();
                calibrationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                    }
                });


                break;

            case 2:
                if (!isShowDialog) {
                    return;
                }
                ConnectWifiDialog activationDialog = new ConnectWifiDialog(SaveSpaceActivity.this, App.getContext().getResources().getString(R.string.sdcard_exist), getResources().getString(R.string.confirm), getResources().getString(R.string.cancel), 0);
                activationDialog.show();
                mRvExist.setVisibility(View.VISIBLE);
                mLvExist.setVisibility(View.GONE);

                break;
        }
    }

    @Override
    public void onRefresh() {
        mBasePresenter.onResume();
    }
}
