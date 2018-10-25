package com.adasplus.proadas.business.main.view.fragment.about;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adasplus.proadas.business.R;
import com.adasplus.proadas.business.about.view.UseHelpActivity;
import com.adasplus.proadas.business.about.view.WebViewActivity;
import com.adasplus.proadas.business.common.view.ActivationActivity;
import com.adasplus.proadas.business.connect.view.ConnectWifiDialog;
import com.adasplus.proadas.business.main.presenter.fragment.about.AboutPresenter;
import com.adasplus.proadas.common.Constant;
import com.adasplus.proadas.common.Constants;
import com.adasplus.proadas.common.entry.DevicesInfo;
import com.adasplus.proadas.common.presenter.BasePresenter;
import com.adasplus.proadas.common.view.custom.RadioImageView;
import com.adasplus.proadas.common.view.fragment.BaseFragment;

import org.w3c.dom.Text;

/**
 * Created by zhangyapeng on 18-4-4.
 */

public class AboutFragment extends BaseFragment<AboutPresenter> implements IAboutView, SwipeRefreshLayout.OnRefreshListener {

    private TextView mTvVersion;
    private SwipeRefreshLayout mSwipeLayout;
    private ImageView mIvIcon;

    @Override
    public void showProgress(String msg) {

    }

    @Override
    public void toastNetworkError(String msg) {

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about;
    }

    @Override
    protected void initView(View view) {
        getActivity().findViewById(R.id.tv_right_view).setVisibility(View.INVISIBLE);
        mTvVersion = (TextView) view.findViewById(R.id.tv_app_version);
        view.findViewById(R.id.rv_troud).setOnClickListener(this);
        view.findViewById(R.id.rv_help).setOnClickListener(this);
        view.findViewById(R.id.tv_hide).setOnClickListener(this);
        view.findViewById(R.id.tv_service).setOnClickListener(this);
        mIvIcon = (ImageView) view.findViewById(R.id.iv_icom);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), com.adasplus.proadas.common.R.mipmap.ic_launcher);
        mIvIcon.setImageBitmap(RadioImageView.roundBitmapByShader(bitmap, bitmap.getWidth(), bitmap.getHeight(), 20));
    }

    @Override
    protected AboutPresenter createPresenter() {
        return new AboutPresenter(this);
    }

    @Override
    protected void initData() {
        mBasePresenter.onResume();
    }

    @Override
    public void success(final String commonEntry, final int code) {
                switch (code) {
                    case 0:
                        mTvVersion.setText(commonEntry);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
    }

    @Override
    public void failure() {
                ConnectWifiDialog activationDialog = new ConnectWifiDialog(mActivity, mActivity.getResources().getString(R.string.quest_failue), mActivity.getResources().getString(R.string.confirm), mActivity.getResources().getString(R.string.cancel), 0);
                activationDialog.show();

    }

    @Override
    public void onRefresh() {
        mBasePresenter.onResume();
    }


    public SwipeRefreshLayout getLoadiView() {

        return null;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_service:
                Intent intent = new Intent(mActivity, WebViewActivity.class);
                intent.putExtra(Constants.HTML_PATH, "file:///android_asset/service.html");
                startActivity(intent);
                break;
            case R.id.tv_hide:
                Intent intent1 = new Intent(mActivity, WebViewActivity.class);
                intent1.putExtra(Constants.HTML_PATH, "file:///android_asset/private.html");
                startActivity(intent1);
                break;
            case R.id.rv_help:
                Intent intent2 = new Intent(mActivity, UseHelpActivity.class);
                startActivity(intent2);
                break;
        }
    }
}