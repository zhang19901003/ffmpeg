package com.adasplus.proadas.business.main.view.fragment.sim;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.connect.view.ConnectWifiDialog;
import com.adasplus.proadas.business.device.view.DevicesInfoActivity;
import com.adasplus.proadas.business.main.presenter.fragment.sim.SimPresenter;
import com.adasplus.proadas.business.sim.view.TrafficQueryActivity;
import com.adasplus.proadas.common.entry.SimInfo;
import com.adasplus.proadas.common.view.fragment.BaseFragment;

/**
 * Created by zhangyapeng on 18-4-4.
 */

public class SimFragment extends BaseFragment<SimPresenter> implements ISimView ,SwipeRefreshLayout.OnRefreshListener {

    private TextView mTvSim;
    private SwipeRefreshLayout mSwipeLayout;
    @Override
    public void showProgress(String msg) {

    }

    @Override
    public SwipeRefreshLayout getLoadiView() {
        return mSwipeLayout;
    }

    @Override
    public void toastNetworkError(String msg) {

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sim_manager;
    }

    @Override
    protected void initView(View view) {
        RelativeLayout relativeLayou = (RelativeLayout) view.findViewById(R.id.rv_traffic_query);
        relativeLayou.setOnClickListener(this);
        getActivity().findViewById(R.id.tv_right_view).setVisibility(View.INVISIBLE);
        mTvSim = (TextView) view.findViewById(R.id.tv_sim);
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeLayout.setProgressBackgroundColorSchemeResource(R.color.btn_disenable);
    }

    @Override
    protected SimPresenter createPresenter() {
        return new SimPresenter(this);
    }

    @Override
    protected void initData() {
            mBasePresenter.onResume();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rv_traffic_query:
                getActivity().startActivity(new Intent(mActivity, TrafficQueryActivity.class));
                break;
        }
    }

    @Override
    public void success(final SimInfo simInfo) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(simInfo.getMessage())) {
                    mTvSim.setText(simInfo.getMessage());
                }
            }
        });
    }

    @Override
    public void failure() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ConnectWifiDialog activationDialog = new ConnectWifiDialog(mActivity,mActivity.getResources().getString(R.string.quest_failue),mActivity.getResources().getString(R.string.confirm),mActivity.getResources().getString(R.string.cancel),0);
                activationDialog.show();

            }
        });
    }

    @Override
    public void onRefresh() {
        mBasePresenter.onResume();
    }


}