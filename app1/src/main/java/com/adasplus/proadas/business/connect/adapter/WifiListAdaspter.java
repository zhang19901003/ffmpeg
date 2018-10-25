package com.adasplus.proadas.business.connect.adapter;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.proadas.business.R;
import com.adasplus.proadas.common.adapter.ListViewBaseAdapter;

/**
 * Created by zhangyapeng on 18-3-21.
 */

public class WifiListAdaspter <T extends ScanResult,V extends WifiHolder> extends ListViewBaseAdapter {
    public WifiListAdaspter(Context context, int resId) {
        super(context, resId);
    }

    @Override
    protected V initView(View convertView) {
        V v= (V) new WifiHolder();
        v.setmTvWifiName((TextView) convertView.findViewById(R.id.lv_wifi_name));
        v.setmIvWifiIcon((ImageView) convertView.findViewById(R.id.lv_wifi_icon));
        v.setmView((View)convertView.findViewById(R.id.lv_vw));
        return v;
    }

    @Override
    protected void initData(int position, Object mHolder, Object o,int size) {
        V v = (V) mHolder;
        ScanResult s = (ScanResult) o;
//        v.getmIvWifiIcon().setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.ic_launcher_round));
        v.getmTvWifiName().setText(s.SSID);
        if(size-1==position){
            v.getmView().setVisibility(View.INVISIBLE);
        }else {
            v.getmView().setVisibility(View.VISIBLE);
        }

    }



    @Override
    public long getItemId(int position) {
        return position;
    }
}
