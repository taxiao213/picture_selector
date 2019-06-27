package com.edit.picture.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.FrameLayout;

import com.edit.picture.util.PhotoEditImage;
import com.selector.picture.utils.UIUtils;

/**
 * 编辑界面加载ImageView 需要实现 手势放大缩小，截切，旋转，马赛克，画笔功能
 * Create by yin13 smyhvae on 2019/6/27
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoEditImageView extends FrameLayout implements ScaleGestureDetector.OnScaleGestureListener {
    private PhotoEditImage photoEditImage = new PhotoEditImage();
    private ScaleGestureDetector mScaleGestureDetector;
    private Matrix matrix;
    private float SCALE_MAX = 4.0F;
    private float SCALE_MIN = 1.0F;

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
        matrix = new Matrix();
    }

    /**
     * 设置图片
     *
     * @param bitmap Bitmap
     */
    public void setPhotoEditImage(Bitmap bitmap) {
        if (photoEditImage != null) {
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
     * @param scaleFactor float
     */
    private void onScaleImage(float scaleFactor) {
        if (scaleFactor <= SCALE_MIN) {
            scaleFactor = SCALE_MIN;
        } else {
            scaleFactor = SCALE_MAX;
        }
        matrix.postScale(scaleFactor, scaleFactor, UIUtils.getScreenWidth(getContext()) / 2, UIUtils.getScreenHeight(getContext()) / 2);
        photoEditImage.setMatrix(matrix);
        invalidate();
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

        return mScaleGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float currentSpanX = detector.getCurrentSpanX();
        float currentSpanY = detector.getCurrentSpanY();
        float scaleFactor = detector.getScaleFactor();
        float focusX = detector.getFocusX();
        float focusY = detector.getFocusY();
        onScaleImage(scaleFactor);
        Log.e("onScale", " currentSpanX== " + currentSpanX + " currentSpanY== " + currentSpanY
                + " scaleFactor== " + scaleFactor + " focusX== " + focusX + " focusY== " + focusY);
        return false;
    }


    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        float currentSpanX = detector.getCurrentSpanX();
        float currentSpanY = detector.getCurrentSpanY();
        float scaleFactor = detector.getScaleFactor();
        Log.e("onScaleBegin", " currentSpanX== " + currentSpanX + " currentSpanY== " + currentSpanY + " scaleFactor== " + scaleFactor);
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }
}
