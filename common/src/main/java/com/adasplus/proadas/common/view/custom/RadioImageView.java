package com.adasplus.proadas.common.view.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.adasplus.proadas.common.R;

/**
 * Created by zhangyapeng on 18-5-8.
 */

public class RadioImageView   {





    public static Bitmap roundBitmapByShader(Bitmap bitmap, int outWidth, int outHeight, int radius) {
        if(bitmap == null) {
            throw new NullPointerException("Bitmap can't be null");
        }
        float widthScale = outWidth * 1.0f / bitmap.getWidth();
        float heightScale = outHeight * 1.0f / bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(widthScale, heightScale);
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        bitmapShader.setLocalMatrix(matrix);
        Bitmap targetBitmap = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        Canvas targetCanvas = new Canvas(targetBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);
        targetCanvas.drawRoundRect(new RectF(0, 0, outWidth, outWidth), radius, radius, paint);
        return targetBitmap;
    }


}
