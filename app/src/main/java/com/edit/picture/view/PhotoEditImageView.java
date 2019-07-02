package com.edit.picture.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.FrameLayout;

import com.edit.picture.util.PhotoEditImage;

/**
 * 编辑界面加载ImageView 需要实现 手势放大缩小，裁剪，旋转，马赛克，画笔功能
 * 裁剪可以旋转，有取消按钮操作
 * 画笔有撤回功能
 * 马赛克有撤回功能
 * Create by yin13 smyhvae on 2019/6/27
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoEditImageView extends FrameLayout implements ScaleGestureDetector.OnScaleGestureListener, Runnable {
    private PhotoEditImage photoEditImage = new PhotoEditImage();
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;
    private Matrix matrix;
    private int mPointerCount;//手指接触的个数

    public PhotoEditImageView(@NonNull Context context) {
        this(context, null);
    }

    public PhotoEditImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoEditImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        mGestureDetector = new GestureDetector(context, new GestureListener());
        matrix = new Matrix();
    }

    /**
     * 设置图片
     *
     * @param bitmap Bitmap
     */
    public void setPhotoEditImage(Bitmap bitmap) {
        if (photoEditImage != null && bitmap != null) {
            photoEditImage.setPhotoEditImage(bitmap);
            invalidate();
        }
    }

    /**
     * 绘制图片
     *
     * @param canvas Canvas 画布
     */
    private void onDrawImage(Canvas canvas) {
        photoEditImage.drawImage(getContext(), canvas);
    }


    /**
     * 缩放照片
     *
     * @param event MotionEvent
     */
    private boolean onTouch(MotionEvent event) {
        mPointerCount = event.getPointerCount();
        Log.e("touch ", " mPointerCount == " + mPointerCount);
        boolean touch = mScaleGestureDetector.onTouchEvent(event);
        mGestureDetector.onTouchEvent(event);
        return touch;
    }

    /**
     * 手势拖动
     *
     * @param distanceX X移动距离
     * @param distanceY Y移动距离
     * @return boolean
     */
    private boolean onScroll(float distanceX, float distanceY) {
        if (mPointerCount > 1) {
            return false;
        }
//        IMGHoming homing = mImage.onScroll(getScrollX(), getScrollY(), -dx, -dy);
//        if (homing != null) {
//            toApplyHoming(homing);
//            return true;
//        }
        return onScrollTo(getScrollX() + Math.round(distanceX), getScrollY() + Math.round(distanceY));
    }

    /**
     * 手势拖动
     *
     * @param distanceX X移动距离
     * @param distanceY Y移动距离
     * @return boolean
     */
    private boolean onScrollTo(int distanceX, int distanceY) {
        if (getScrollX() != distanceX || getScrollY() != distanceY) {
            scrollTo(distanceX, distanceY);
            return true;
        }
        return false;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (photoEditImage != null) {
            photoEditImage.onSizeChange(w, h, getContext());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        onDrawImage(canvas);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event != null) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    removeCallbacks(this);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    postDelayed(this, 1200);
                    break;
            }
        }
        return onTouch(event);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float currentSpanX = detector.getCurrentSpanX();
        float currentSpanY = detector.getCurrentSpanY();
        float currentSpan = detector.getCurrentSpan();
        float previousSpanX = detector.getPreviousSpanX();
        float previousSpanY = detector.getPreviousSpanY();
        float previousSpan = detector.getPreviousSpan();
        float scaleFactor = detector.getScaleFactor();
        float focusX = detector.getFocusX();
        float focusY = detector.getFocusY();
        if (mPointerCount > 1) {
            if (photoEditImage != null) {
                photoEditImage.setGestureScale(detector.getScaleFactor(), getScaleX() + detector.getFocusX(), getScaleY() + detector.getFocusY());
                invalidate();
            }
            return true;
        }
        Log.e("onScale",
                " currentSpanX== " + currentSpanX + "\n"
                        + " currentSpanY== " + currentSpanY + "\n"
                        + " currentSpan== " + currentSpan + "\n"
                        + " previousSpanX== " + previousSpanX + "\n"
                        + " previousSpanY== " + previousSpanY + "\n"
                        + " previousSpan== " + previousSpan + "\n"
                        + " scaleFactor== " + scaleFactor + "\n"
                        + " focusX== " + focusX + "\n"
                        + " focusY== " + focusY);
        return false;
    }


    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        if (mPointerCount > 1) {
            return true;
        }
        return false;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    /**
     * 手势监听
     */
    public class GestureListener extends SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.e("distanceX == " + distanceX, "distanceY == " + distanceY);
            return PhotoEditImageView.this.onScroll(distanceX, distanceY);
        }
    }

    @Override
    public void run() {

    }


}
