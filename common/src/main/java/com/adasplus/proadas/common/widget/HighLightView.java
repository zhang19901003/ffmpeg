package com.adasplus.proadas.common.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.adasplus.proadas.common.R;


public class HighLightView extends View {
    private static final float RADIUS_RATIO = 3f / 5;
    private static final PorterDuffXfermode X_FER_MODE = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);

    private final Paint mPaint;
    private boolean showFlag;
    private int mOverlayColor;//遮罩层颜色
    private int mRadius; // 圆半径
    private int mCenterX; // 圆心横坐标
    private int mCenterY; // 圆心纵坐标

    private String mTip; // 提示文字
    private float mTipX; // 文字横坐标
    private float mTipY; // 文字纵坐标
    private float[] floats = new float[2];


    private int bitmapID;

    private View mHighLightView; // 高亮的View
    private Bitmap mBitmap;

    private OnClickListener mClickListener; // 点击回调
    /**
     * 可点击的区域
     */
    private Rect mClickRect = new Rect();
    /**
     * 是否在点击区域按下
     */
    private boolean mDownInClickRect;
    /**
     * 指示图片出现的位置
     */
    private int mOrientation;
    private boolean setSuccessflag;
    private OutClickListener mOutClickListener;

    public HighLightView(Context context) {

        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
        int textSize = getResources().getDimensionPixelSize(R.dimen.base16dp);
        mPaint.setTextSize(textSize);
        // daoshihou zaihuan
        mOverlayColor = getResources().getColor(R.color.black33);
        setFilterTouchesWhenObscured(false);
        showFlag = true;
    }

    /**
     * @param view        要高亮的view
     * @param bitmapID    提示图片
     * @param listener    点击回调
     * @param orientation 提示图片方向
     *                    0-左
     *                    1-上
     *                    2-右
     *                    3-下
     *                    4-右下
     *                    5-左下
     *                    6-左下
     */
    public void showTipForView(final View view, final int bitmapID, OnClickListener listener, int orientation) {
        if (view == null) return;
        mHighLightView = view;
//        mTip = tip;
        this.bitmapID = bitmapID;
        mClickListener = listener;
        this.mOrientation = orientation;
        view.post(new Runnable() {
            @Override
            public void run() {
                final View anchorView = view.getRootView();
                prepare(anchorView, view);
                if (anchorView instanceof ViewGroup) {
                    ((ViewGroup) anchorView).addView(HighLightView.this, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                }
            }
        });
    }


//    public void showTipForView(final View view, final int bitmapID, OnClickListener listener, final View lightView, int mOrientation) {
//        mHighLightView = view;
////        mTip = tip;
//        this.bitmapID = bitmapID;
//        mClickListener = listener;
//        this.mOrientation = mOrientation;
//        view.post(new Runnable() {
//            @Override
//            public void run() {
//                final Bitmap bm = UIUtils.getBitmapFromView(lightView);
//                if (bm==null)return;
//                final View anchorView = view.getRootView();
//                prepare(anchorView, view, bm);
//                if (anchorView instanceof ViewGroup) {
//                    ((ViewGroup) anchorView).addView(HighLightView.this, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                }
//            }
//        });
//    }

    private void prepare(View anchorView, View view) {
        final int[] baseLocation = new int[2];
        anchorView.getLocationInWindow(baseLocation);
        final int[] viewLocation = new int[2];
        view.getLocationInWindow(viewLocation);//获取当前view在窗口中的位置
        final int viewHeight = view.getHeight();
        final int viewWidth = view.getWidth();
        final int halfHeight = viewHeight / 2;
        mRadius = (int) (viewWidth * RADIUS_RATIO);
        //获取需要高亮的View在屏幕上真正的坐标，避免Activity的进入效果不同导致的坐标异常问题
        final int[] realViewLocation = new int[2];
        realViewLocation[0] = viewLocation[0] - baseLocation[0];
        realViewLocation[1] = viewLocation[1] - baseLocation[1];
        mCenterX = viewLocation[0] - baseLocation[0] + viewWidth / 2;// 获取圆心x坐标
        mCenterY = viewLocation[1] - baseLocation[1] + halfHeight; // 获取圆心y坐标

        // 可点击区域为圆心按钮相交的近似矩形
//        mClickRect.set(mCenterX - mRadius, mCenterY - halfHeight, mCenterX + mRadius, mCenterY + halfHeight);
        //可点击区域为整个高亮圆圈范围
        mClickRect.set(mCenterX - mRadius, mCenterY - mRadius, mCenterX + mRadius, mCenterY + mRadius);
//        mTipX = (anchorView.getWidth() - mPaint.measureText(mTip)) / 2; //提示文字的横坐标，居中即可
//        mTipY = viewLocation[1] + mRadius * 2; // 提示文字的纵坐标，只需要在圆的下方，这里设为view的纵坐标加上直径
        mBitmap = Bitmap.createBitmap(anchorView.getWidth(), anchorView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mBitmap);
        canvas.drawColor(mOverlayColor);
        mPaint.setXfermode(X_FER_MODE);
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
        mPaint.setXfermode(null);
        mPaint.setColor(Color.WHITE);
//        canvas.drawText(mTip, mTipX, mTipY, mPaint);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), bitmapID);

        getDrawBitmapLocation(viewHeight, realViewLocation, viewWidth, bitmap);

        if (showFlag) {
            canvas.drawBitmap(bitmap, floats[0], floats[1], new Paint());
        }
    }

    /**
     * 设置提示图出现的位置
     *
     * @param viewHeight
     * @param viewLocation
     * @param viewWidth
     * @param bitmap
     */
    private void getDrawBitmapLocation(int viewHeight, int[] viewLocation, int viewWidth, Bitmap bitmap) {
        switch (mOrientation) {
            //左
            case 0:
                floats[0] = viewLocation[0] - bitmap.getWidth();//左
                floats[1] = viewLocation[1] + viewHeight - bitmap.getHeight();//上
                break;
            //上
            case 1:
                floats[0] = mCenterX - bitmap.getWidth() / 2;
                floats[1] = mCenterY - bitmap.getHeight() - mRadius;
                break;
            //右
            case 2:
                floats[0] = viewLocation[0] + viewWidth;
                floats[1] = viewLocation[1] + viewHeight - bitmap.getHeight();
                break;
            //下
            case 3:
                floats[0] = mCenterX - bitmap.getWidth() / 2;
                floats[1] = mCenterY + mRadius;
                break;
            //右下
            case 4:
                floats[0] = mCenterX + mRadius;
                floats[1] = viewLocation[1] + viewHeight;
                break;
            //左下
            case 5:
                floats[0] = viewLocation[0] - bitmap.getWidth();//左
                floats[1] = viewLocation[1];//上
                break;
            //左下
            case 6:
                floats[0] = viewLocation[0] + viewWidth / 2 - bitmap.getWidth();
                floats[1] = viewLocation[1] + viewHeight;
                break;
            default:
                break;
        }
        this.setSuccessflag = (floats[0] < 0 || floats[1] < 0);

    }

    private void prepare(View anchorView, View view, Bitmap bm) {
        final int[] baseLocation = new int[2];
        anchorView.getLocationInWindow(baseLocation);
        final int[] viewLocation = new int[2];
        view.getLocationInWindow(viewLocation);//获取当前view在窗口中的位置
        //获取需要高亮的View在屏幕上真正的坐标，避免Activity的进入效果不同导致的坐标异常问题
        final int[] realViewLocation = new int[2];
        realViewLocation[0] = viewLocation[0] - baseLocation[0];
        realViewLocation[1] = viewLocation[1] - baseLocation[1];
        final int viewHeight = view.getHeight();
        final int viewWidth = view.getWidth();
        final int halfHeight = viewHeight / 2;
        mRadius = (int) (viewWidth * RADIUS_RATIO);
        mCenterX = viewLocation[0] - baseLocation[0] + viewWidth / 2;// 获取圆心x坐标
        mCenterY = viewLocation[1] - baseLocation[1] + halfHeight; // 获取圆心y坐标

        // 可点击区域为圆心按钮相交的近似矩形
        mClickRect.set(mCenterX - mRadius, mCenterY - halfHeight, mCenterX + mRadius, mCenterY + halfHeight);
        //可点击区域为整个高亮圆
//        mClickRect.set(mCenterX - mRadius, mCenterY - mRadius, mCenterX + mRadius, mCenterY + mRadius);
//        mTipX = (anchorView.getWidth() - mPaint.measureText(mTip)) / 2; //提示文字的横坐标，居中即可
//        mTipY = viewLocation[1] + mRadius * 2; // 提示文字的纵坐标，只需要在圆的下方，这里设为view的纵坐标加上直径
        mBitmap = Bitmap.createBitmap(anchorView.getWidth(), anchorView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mBitmap);
        canvas.drawColor(mOverlayColor);
        mPaint.setXfermode(X_FER_MODE);
        canvas.drawBitmap(bm, realViewLocation[0], realViewLocation[1], mPaint);
        mPaint.setXfermode(null);
        mPaint.setColor(Color.WHITE);
//        canvas.drawText(mTip, mTipX, mTipY, mPaint);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                bitmapID);
        switch (mOrientation) {
            //左
            case 0:
                floats[0] = realViewLocation[0] - bitmap.getWidth();//左
                floats[1] = realViewLocation[1] + viewHeight - bitmap.getHeight();//上
                break;
            //上
            case 1:
                floats[0] = mCenterX - bitmap.getWidth() / 2;
                floats[1] = mCenterY - bitmap.getHeight() - halfHeight;
                break;
            //右
            case 2:
                floats[0] = realViewLocation[0] + viewWidth;
                floats[1] = realViewLocation[1] + viewHeight - bitmap.getHeight();
                break;
            //下
            case 3:
                floats[0] = mCenterX - bitmap.getWidth() / 2;
                floats[1] = mCenterY + halfHeight;
                break;
            //右下
            case 4:
                floats[0] = mCenterX + halfHeight;
                floats[1] = realViewLocation[1] + viewHeight;//上
                break;
            // 右上
            case 5:
                floats[0] = mCenterX;
                floats[1] = mCenterY - bitmap.getHeight() - halfHeight;
                break;
            //左下
            case 6:
                floats[0] = viewLocation[0] + viewWidth / 2 - bitmap.getWidth();
                floats[1] = realViewLocation[1] + viewHeight;
                break;
            //左上：
            case 7:
                floats[0] = viewLocation[0] + viewWidth / 2 - bitmap.getWidth();
                floats[1] = mCenterY - bitmap.getHeight() - halfHeight;
                break;
            default:
                break;
        }
        if (showFlag) {
            canvas.drawBitmap(bitmap, floats[0], floats[1], new Paint());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                final PointF down = new PointF(event.getX(), event.getY());
                if (isInClickRect(down)) {
                    mDownInClickRect = true;
                }
                break;

            case MotionEvent.ACTION_UP:
                if (mDownInClickRect) {
                    // TODO: 2016/6/22 关闭引导效果
                    ((ViewGroup) getParent()).removeView(HighLightView.this);
//                    ToastUtil.showMyToast("关闭引导");
                    if(mOutClickListener != null){
                        mOutClickListener.click();
                    }
                    break;
                }
                final PointF up = new PointF(event.getX(), event.getY());
                if (isInClickRect(up) && mClickListener != null) {
                    mClickListener.onClick(mHighLightView);
                    ((ViewGroup) getParent()).removeView(HighLightView.this);
                }
                mDownInClickRect = false;
                return true;
        }
        return true;
    }
    public interface OutClickListener{
        void click();
    }

    public void setmOutClickListener(OutClickListener mOutClickListener){
        this.mOutClickListener = mOutClickListener;
    }
    private boolean isInClickRect(PointF point) {
        return point.x > mClickRect.left && point.x < mClickRect.right
                && point.y > mClickRect.top && point.y < mClickRect.bottom;
    }
}