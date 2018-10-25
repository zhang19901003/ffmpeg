package com.adasplus.proadas.common.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by zhangyapeng on 18-5-2.
 */

public class SurfaceViewRadio extends SurfaceView {
    public SurfaceViewRadio(Context context) {
        super(context);
    }

    public SurfaceViewRadio(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SurfaceViewRadio(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void draw(Canvas canvas) {

        Path path = new Path();
        RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        path.addRoundRect(rect, 15.0f, 15.0f, Path.Direction.CCW);
        canvas.clipPath(path, Region.Op.REPLACE);
        super.draw(canvas);
    }


}
