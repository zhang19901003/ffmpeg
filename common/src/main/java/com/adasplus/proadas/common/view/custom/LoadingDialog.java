
package com.adasplus.proadas.common.view.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.proadas.common.R;


public class LoadingDialog extends Dialog {

    private static final int DURATION = 1500;
    private ImageView mIv;
    private Animation mAnimation;
    private boolean isAnimation;
    private Context mContext;
    private TextView mTv;
    private String s;
    private Bitmap mBitmap;
    @Override
    public void show() {
        super.show();
        if (isAnimation) {
            mIv.setAnimation(mAnimation);
            mAnimation.start();
        }

    }

    public void setShowAnimation(boolean isAnimation){
        this.isAnimation = isAnimation;
    }

    public LoadingDialog(Context context) {
        super(context, R.style.dialog_style);
        this.mContext = context;
        setCancelable(false);
    }

    public void setString(String s) {
       this.s=s;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        mTv = (TextView) findViewById(R.id.tv_dialog);
        mIv = (ImageView) findViewById(R.id.iv_loading);
        if (isAnimation) {
            mAnimation = new RotateAnimation(0f, 360f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                    0.5f);

            mAnimation.setDuration(DURATION);
            mAnimation.setFillAfter(false);
            mAnimation.setRepeatCount(1000);
        }

        mTv.setText(s);
        mIv.setImageBitmap(mBitmap);
    }

    @Override
    protected void onStop() {
        super.onStop();
        dismiss();
    }

    public void stopAnimal() {
        if (mAnimation != null) {
            mAnimation.cancel();
        }
    }

    public void setImage(int id) {
         mBitmap = BitmapFactory.decodeResource(mContext.getResources(), id);
    }
}