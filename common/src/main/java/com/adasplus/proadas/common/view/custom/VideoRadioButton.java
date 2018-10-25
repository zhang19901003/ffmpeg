package com.adasplus.proadas.common.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;


/**
 * Created by zhangyapeng on 18-4-6.
 */

public class VideoRadioButton extends android.support.v7.widget.AppCompatRadioButton {


    public VideoRadioButton(Context context) {
        super(context);
    }

    public VideoRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable[] drawables = getCompoundDrawables();
        Drawable drawable = drawables[0];
        int gravity = getGravity();
        int left = 0;
        if (gravity == Gravity.CENTER) {
            left = ((int) (getWidth() - drawable.getIntrinsicWidth() - getPaint().measureText(getText().toString()))
                    / 2);
        }
        drawable.setBounds(left-20, 0, (left + drawable.getIntrinsicWidth()-20), drawable.getIntrinsicHeight());
    }
}
