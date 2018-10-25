package com.adasplus.proadas.business.connect.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adasplus.proadas.business.R;

/**
 * Created by zhangyapeng on 18-4-3.
 */

public class CheckWifiDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private String descrbe;
    public CheckWifiDialog(Context context,String descrbe) {
        super(context, R.style.dialog_style);
        mContext = context;
        this.descrbe=descrbe;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        RelativeLayout rv = (RelativeLayout) findViewById(R.id.rv_dialog);
        TextView tvDesc = (TextView) findViewById(R.id.tv_dialog);
        tvDesc.setText(!TextUtils.isEmpty(descrbe)? descrbe: "");
        rv.setOnClickListener(this);
        setCanceledOnTouchOutside(true);
    }

    @Override
    public void onClick(View v) {
        cancel();
    }
}


