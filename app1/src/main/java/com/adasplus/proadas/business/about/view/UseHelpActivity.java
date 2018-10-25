package com.adasplus.proadas.business.about.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adasplus.proadas.business.R;
import com.adasplus.proadas.common.presenter.BasePresenter;
import com.adasplus.proadas.common.view.activity.BaseActivity;

/**
 * Created by zhangyapeng on 18-5-10.
 */

public class UseHelpActivity extends BaseActivity {

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_use_help;
    }

    @Override
    protected void initView() {
        LinearLayout lvLeftView = findViewById(R.id.ll_left_layout);
        lvLeftView.setVisibility(View.VISIBLE);
        lvLeftView.setOnClickListener(this);
        TextView tvTitle = (TextView) findViewById(R.id.tv_main_title_textview);
        tvTitle.setText(getResources().getString(R.string.use_help));
    }

    @Override
    protected void initData() {

    }

    @Override
    public SwipeRefreshLayout getLoadiView() {
        return null;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.ll_left_layout:
                finish();
                break;
        }
    }
}
