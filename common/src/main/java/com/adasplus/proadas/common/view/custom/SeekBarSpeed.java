package com.adasplus.proadas.common.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import com.adasplus.proadas.common.R;

/**
 * Created by zhangyapeng on 18-4-9.
 */

public class SeekBarSpeed extends SeekBar implements SeekBar.OnSeekBarChangeListener {

    private String mNumText;
    private int mNumTextSize;
    private Paint mPaint;

    private Rect mRectThumb;
    private int mPadding;
    private String max;
    private String min;
    private float number;
    private int mStartPoint;
    public SeekBarSpeed(Context context) {
        this(context, null);
    }

    public SeekBarSpeed(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SeekBarSpeed(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        initPaint();

    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomSeekBar);

        mNumTextSize = array.getDimensionPixelSize(R.styleable.CustomSeekBar_numTextSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        mPadding = array.getDimensionPixelSize(R.styleable.CustomSeekBar_padding, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_PX, 16, getResources().getDisplayMetrics()));

        min = array.getString(R.styleable.CustomSeekBar_min_);
        max = array.getString(R.styleable.CustomSeekBar_max_);
        setPadding(mPadding, 0, mPadding, 0);
        number=   (((float)(100)/(Integer.parseInt(max)-Integer.parseInt(min))));
        setOnSeekBarChangeListener(this);
        array.recycle();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setTextSize(mNumTextSize);
        mPaint.setColor(Color.WHITE);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint.FontMetrics   fm = mPaint.getFontMetrics();
        mNumText = (((int)(getProgress()/number)+Integer.parseInt(min))+"");
        float numTextWidth = mPaint.measureText(mNumText);
        float ascent = fm.ascent;
        Rect rect_seek = this.getProgressDrawable().getBounds();
        mRectThumb = this.getThumb().getBounds();
        mRectThumb = this.getThumb().getBounds();
        canvas.drawText(mNumText, mRectThumb.centerX()-numTextWidth/2, rect_seek.centerY() -ascent / 2-2, mPaint);
    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
         if(mOnReallySeekBarChangeListener!=null){
             mOnReallySeekBarChangeListener.onProgressChanged(((int)(getProgress()/ number))+Integer.parseInt(min));
         }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    public interface  OnReallySeekBarChangeListener{
       void onProgressChanged(int progress);
    }

    private OnReallySeekBarChangeListener mOnReallySeekBarChangeListener;

    public void setOnReallySeekBarChangeListener(OnReallySeekBarChangeListener OnReallySeekBarChangeListener){
        this.mOnReallySeekBarChangeListener = OnReallySeekBarChangeListener;
    }

    public int getReallyProgress (){
        return ((int)(getProgress()/ number))+Integer.parseInt(min);
    }

    @Override
    public synchronized void setProgress(int progress) {
        if(progress==0){
            super.setProgress(progress);
        }else {
            float i = (float)(progress - Integer.parseInt(min)) / (Integer.parseInt(max) - Integer.parseInt(min));
            super.setProgress((int) (i*100));
        }
    }

    @Override
    public void setThumb(Drawable thumb) {
        super.setThumb(thumb);
        invalidate();
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//                mSwipeLayout.setEnabled(false);
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                mSwipeLayout.setEnabled(true);
//                break;
//        }
//        return false;
//
//
//    }



}
