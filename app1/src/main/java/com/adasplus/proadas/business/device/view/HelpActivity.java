package com.adasplus.proadas.business.device.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adasplus.proadas.business.R;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.presenter.BasePresenter;
import com.adasplus.proadas.common.view.activity.BaseActivity;

/**
 * Created by zhangyapeng on 18-4-6.
 */

public class HelpActivity extends BaseActivity {
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        
        return getIntent().getIntExtra(Constants.HELP, R.layout.activity_devices_help);
    }

    @Override
    protected void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tv_main_title_textview);
         tvTitle.setText(getResources().getString(R.string.use_help));
        LinearLayout lvLeftView = findViewById(R.id.ll_left_layout);
        lvLeftView.setVisibility(View.VISIBLE);
        lvLeftView.setOnClickListener(this);
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
        }
    }

    @Override
    public SwipeRefreshLayout getLoadiView() {
        return null;
    }
}
