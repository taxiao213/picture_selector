package com.edit.picture.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.selector.picture.utils.UIUtils;


/**
 * 编辑图片model
 * Create by yin13 smyhvae on 2019/6/27
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoEditImage {
    private Bitmap mBitmap;
    private Matrix matrix = new Matrix();
    /*完整图片边框*/
    private RectF mFrame = new RectF();
    private float scale;
    private float SCALE_MAX = 4.0F;
    private float SCALE_MIN = 1.0F;
    private boolean isInit = true;//初始化
    private float[] MATRIX_FLOAT = new float[9];


    /**
     * 设置图片
     *
     * @param bitmap Bitmap
     */
    public void setPhotoEditImage(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    /**
     * 获取图片
     */
    public Bitmap getPhotoEditImage() {
        return mBitmap;
    }

    /**
     * 绘制图片 小图片放大问题
     * canvas.drawBitmap(mBitmap, rect, rectF1, null); 第一个Rect 代表要绘制的bitmap 区域，第二个 Rect 代表的是要将bitmap 绘制在屏幕的什么地方
     *
     * @param canvas Canvas 画布
     */
    public void drawImage(Context context, Canvas canvas) {
        if (mBitmap == null) return;
        if (isInit) {
            int width = mBitmap.getWidth();
            int height = mBitmap.getHeight();
            Rect rect = new Rect(0, 0, width, height);
            int screenWidth = UIUtils.getScreenWidth(context);
            int screenHeight = UIUtils.getScreenHeight(context);
            RectF rectF1 = new RectF((screenWidth - width) / 2, (screenHeight - height) / 2, (screenWidth - width) / 2 + width, (screenHeight - height) / 2 + height);
            canvas.drawBitmap(mBitmap, rect, rectF1, null);
            isInit = false;
        } else {
            canvas.drawBitmap(mBitmap, matrix, null);
        }
    }


    /**
     * 设置手势缩放系数
     *
     * @param scale  float
     * @param focusX
     * @param focusY float
     */
    public void setGestureScale(float scale, float focusX, float focusY) {
        if (scale >= SCALE_MIN && getMatrixScaleX() >= SCALE_MAX) return;
        matrix.postScale(scale, scale, focusX, focusY);
    }

    /**
     * 获取原始尺寸
     * post是后乘，当前的矩阵乘以参数给出的矩阵
     * pre是前乘，参数给出的矩阵乘以当前的矩阵
     *
     * @return RectF
     */
    public RectF getmFrame() {
        return mFrame;
    }

    /**
     * 设置matrix
     *
     * @param width  宽
     * @param height 高
     */
    public void onSizeChange(int width, int height) {
        if (mBitmap == null) return;
        if (!isInit) {
            scale = Math.min(width * 1f / mBitmap.getWidth(), height * 1f / mBitmap.getHeight());
            matrix.postScale(scale, scale);
        }
    }

    /**
     * 获取需要移动的距离
     *
     * @param trans      bitmap的宽和高
     * @param screenSize 屏幕的宽和高尺寸
     * @return float
     */
    private float getTranslate(float trans, int screenSize) {
        if (trans <= screenSize) {
            trans = (screenSize - trans) / 2;
        } else {
            trans = screenSize;
        }
        return trans;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    /**
     * 缩放位置调整
     *
     * @param context Context
     */
    public void setPositionCorrection(Context context) {
//        float scale = getMatrixScaleX();
//        float tranWidth = scale * mBitmap.getWidth();
//        float tranHeight = scale * mBitmap.getHeight();
//        int screenWidth = UIUtils.getScreenWidth(context);
//        int screenHeight = UIUtils.getScreenHeight(context);
//        tranWidth = getTranslate(tranWidth, screenWidth);
//        tranHeight = getTranslate(tranHeight, screenHeight);
//        matrix.postTranslate(tranWidth, tranHeight);

        MATRIX_FLOAT[Matrix.MTRANS_X] = 0.0F;
        MATRIX_FLOAT[Matrix.MTRANS_Y] = 0.0F;
        matrix.setValues(MATRIX_FLOAT);
    }

    /**
     * 获取矩阵的缩放比
     *
     * @return float
     */
    public float getMatrixScaleX() {
        matrix.getValues(MATRIX_FLOAT);
        return MATRIX_FLOAT[Matrix.MSCALE_X];
    }

    /**
     * 设置矩阵的缩放比
     *
     * @param scale 缩放比
     */
    public void setMatrixScaleX(float scale) {
        Log.e("scale ", " scale == " + scale);
        MATRIX_FLOAT[Matrix.MSCALE_X] = scale;
        MATRIX_FLOAT[Matrix.MSCALE_Y] = scale;
        Log.e("scale ", " setMatrixScaleX == " + MATRIX_FLOAT[Matrix.MSCALE_X]);
        matrix.setValues(MATRIX_FLOAT);
    }
}
