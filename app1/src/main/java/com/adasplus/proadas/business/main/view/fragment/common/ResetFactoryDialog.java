package com.adasplus.proadas.business.main.view.fragment.common;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.adasplus.proadas.business.R;

/**
 * Created by zhangyapeng on 18-4-3.
 */

public class ResetFactoryDialog extends Dialog implements View.OnClickListener {

    private String mOk;
    private String mCancel;
    private Context mContext;
    private String mDesc;
    private int mVisi;
    public ResetFactoryDialog(Context context, String desc, String ok, String cancel, int visi) {
        super(context, R.style.dialog_style);
        this.mDesc = desc;
        this.mOk = ok;
        this.mCancel = cancel;
        mContext = context;
        mVisi=visi;
    }

    public ResetFactoryDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    protected ResetFactoryDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_reset_factory);
        findViewById();
    }

    private void findViewById() {

        TextView tvRight = (TextView) findViewById(R.id.tv_dialog_go);
        tvRight.setOnClickListener(this);
        tvRight.setText(mOk);
        TextView tvLeft = findViewById(R.id.tv_dialog_cancle);
        if(mVisi==0){
            tvLeft.setVisibility(View.INVISIBLE);
        }else {
            tvLeft.setOnClickListener(this);
            tvLeft.setText(mCancel);
        }
        TextView tvDesc = (TextView) findViewById(R.id.tv_disc);
        tvDesc.setText(mDesc);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_dialog_cancle:
                dismiss();
                if (mOnDialogClickListener != null) {
                    mOnDialogClickListener.onDialogLeftDialog();
                }
                break;
            case R.id.tv_dialog_go:
                dismiss();
                if (mOnDialogClickListener != null) {
                    mOnDialogClickListener.onDialogRightDialog();
                }
                break;
            default:
                break;
        }
    }

    public interface OnDialogClickListener {
        void onDialogLeftDialog();

        void onDialogRightDialog();
    }

    private OnDialogClickListener mOnDialogClickListener;

    public void setOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
        mOnDialogClickListener = onDialogClickListener;
    }

    @Override
    public void setOnDismissListener(@Nullable OnDismissListener listener) {
        super.setOnDismissListener(listener);
    }
}


