package com.edit.picture.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.FrameLayout;

import com.edit.picture.model.PhotoEditImage;
import com.edit.picture.util.PhotoEditAnimator;
import com.selector.picture.utils.UIUtils;

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
public class PhotoEditImageView extends FrameLayout implements ScaleGestureDetector.OnScaleGestureListener, Runnable, Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener {
    private PhotoEditImage photoEditImage;
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;
    private PhotoEditAnimator mPhotoEditAnimator;
    private int mPointerCount;//手指接触的个数
    private final int DELAY_TIME = 100;//延迟时间

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
        photoEditImage = new PhotoEditImage();
        mPhotoEditAnimator = new PhotoEditAnimator(this, this);
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        mGestureDetector = new GestureDetector(context, new GestureListener());
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
        canvas.save();
        photoEditImage.drawImage(getContext(), canvas);
        canvas.restore();
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
        /*if (photoEditImage != null) {
            photoEditImage.onSizeChange(w, h);
        }*/
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
                    postDelayed(this, DELAY_TIME);
                    break;
            }
        }
        return onTouch(event);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        if (mPointerCount > 1) {
            if (photoEditImage != null) {
//                photoEditImage.setGestureScale(detector.getScaleFactor(), getScaleX() + detector.getFocusX(), getScaleY() + detector.getFocusY());
                photoEditImage.setGestureScale(detector.getScaleFactor(), UIUtils.getScreenWidth(getContext()) / 2.0F, UIUtils.getScreenHeight(getContext()) / 2.0F);
                invalidate();
            }
            return true;
        }
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
        if (photoEditImage != null) {
            //动画效果
            final float scaleX = photoEditImage.getMatrixScaleX();
            if (scaleX < 1) {
                if (mPhotoEditAnimator != null) {
                    cancelAnimator();
                    mPhotoEditAnimator.setFloat(scaleX);
                    mPhotoEditAnimator.start();
                }
            }
        }
    }


    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        cancelAnimator();
    }


    @Override
    public void onAnimationCancel(Animator animation) {
        cancelAnimator();
    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        if (photoEditImage != null) {
            Float animatedValue = (Float) animation.getAnimatedValue();
            Log.e("animated ", " value == " + animatedValue);
            photoEditImage.setMatrixScaleX(animatedValue);
//            photoEditImage.setPositionCorrection(getContext());
            invalidate();
        }
    }

    /**
     * 取消动画
     */
    private void cancelAnimator() {
        if (mPhotoEditAnimator != null) {
            mPhotoEditAnimator.cancel();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelAnimator();
        Log.e("onDetachedFromWindow ", "取消动画");
    }
}
