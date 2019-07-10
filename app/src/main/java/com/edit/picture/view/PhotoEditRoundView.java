package com.edit.picture.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.selector.picture.R;

/**
 * 圆形图片
 * Create by Han on 2019/6/20
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoEditRoundView extends View {

    private Paint mPaint;
    private float mRadius;//最小半径
    private float mSpacing;//间距
    private float mScaleCoefficient;//缩放系数
    private int mBackColor;//后景色
    private int mFrontColor;//前景色
    private int mRadiusX;//圆形X
    private int mRadiusY;//圆形Y

    public PhotoEditRoundView(Context context) {
        this(context, null);
    }

    public PhotoEditRoundView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoEditRoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PhotoEditRoundView);
        mRadius = typedArray.getFloat(R.styleable.PhotoEditRoundView_round_radius, context.getResources().getDimension(R.dimen.picture_edit_circle_radius));
        mSpacing = typedArray.getFloat(R.styleable.PhotoEditRoundView_round_spacing, context.getResources().getDimension(R.dimen.picture_edit_circle_spacing));
        float SCALE_COEFFICIENT = 1.0f;
        mScaleCoefficient = typedArray.getFloat(R.styleable.PhotoEditRoundView_round_scale_coefficient, SCALE_COEFFICIENT);
        mBackColor = typedArray.getColor(R.styleable.PhotoEditRoundView_round_back_color, context.getResources().getColor(R.color.white_trans));
        mFrontColor = typedArray.getColor(R.styleable.PhotoEditRoundView_round_front_color, context.getResources().getColor(R.color.colorPrimary));
        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mRadiusX = width / 2;
        mRadiusY = height / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackCircle(canvas);
        drawFrontCircle(canvas);
    }

    /**
     * 绘制正常圆形
     *
     * @param canvas Canvas
     */
    private void drawBackCircle(Canvas canvas) {
        mPaint.setColor(mBackColor);
        canvas.drawCircle(mRadiusX, mRadiusY, (mRadius + mSpacing) * mScaleCoefficient, mPaint);
    }

    /**
     * 绘制彩色圆形
     *
     * @param canvas Canvas
     */
    private void drawFrontCircle(Canvas canvas) {
        mPaint.setColor(mFrontColor);
        canvas.drawCircle(mRadiusX, mRadiusY, mRadius * mScaleCoefficient, mPaint);
    }

    /**
     * 设置前景色
     *
     * @param color int
     */
    public void setFrontColor(int color) {
        this.mFrontColor = color;
    }

    /**
     * 设置后景色
     *
     * @param color int
     */
    public void setBackColor(int color) {
        this.mBackColor = color;
    }


    /**
     * 设置缩放系数 默认1.1f
     *
     * @param coefficient float
     */
    public void setScaleCoefficient(float coefficient) {
        this.mScaleCoefficient = coefficient;
    }

}
