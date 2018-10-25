package com.adasplus.proadas.common.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by zhangyapeng on 18-5-23.
 */

public class ScrollLinearLayout extends LinearLayout {

    Scroller mScroller;

    public ScrollLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(mScroller == null){
            mScroller = new Scroller(context);
        }
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }


    public void startMove(int y){
        if(y == 0)
        {
            mScroller.startScroll(mScroller.getStartX(),mScroller.getCurrY(),0,mScroller.getStartY()-mScroller.getCurrY(),400);
        }else {
            mScroller.startScroll(mScroller.getStartX(),mScroller.getCurrY(),0,y,400);
        }

        invalidate();
    }
}
