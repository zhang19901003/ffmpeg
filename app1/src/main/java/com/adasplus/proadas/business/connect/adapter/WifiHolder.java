package com.adasplus.proadas.business.connect.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by zhangyapeng on 18-3-21.
 */

public class WifiHolder<V> {
    private TextView mTvWifiName;
    private ImageView mIvWifiIcon;
    private View mView;

    public View getmView() {
        return mView;
    }

    public void setmView(View mView) {
        this.mView = mView;
    }

    public TextView getmTvWifiName() {
        return mTvWifiName;
    }

    public void setmTvWifiName(TextView mTvWifiName) {
        this.mTvWifiName = mTvWifiName;
    }

    public ImageView getmIvWifiIcon() {
        return mIvWifiIcon;
    }

    public void setmIvWifiIcon(ImageView mIvWifiIcon) {
        this.mIvWifiIcon = mIvWifiIcon;
    }
}
