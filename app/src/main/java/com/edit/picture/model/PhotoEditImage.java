package com.edit.picture.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

import com.selector.picture.utils.CompressPicUtil;
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
            int screenWidth = UIUtils.getScreenWidth(context);
            int screenHeight = UIUtils.getScreenHeight(context);
            float scale = Math.min(screenWidth * 1f / width, screenHeight * 1f / height);
            float tranWidth = scale * mBitmap.getWidth();
            float tranHeight = scale * mBitmap.getHeight();
            tranWidth = CompressPicUtil.getTranslate(tranWidth, screenWidth);
            tranHeight = CompressPicUtil.getTranslate(tranHeight, screenHeight);
            matrix.postTranslate(tranWidth, tranHeight);
            canvas.drawBitmap(mBitmap, matrix, null);
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


    public Matrix getMatrix() {
        return matrix;
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
    public void setMatrixScaleX(float scale, float focusX, float focusY) {
//        MATRIX_FLOAT[Matrix.MSCALE_X] = scale;
//        MATRIX_FLOAT[Matrix.MSCALE_Y] = scale;
//        Log.e("scale ", " setMatrixScaleX == " + MATRIX_FLOAT[Matrix.MSCALE_X]);
//        matrix.setValues(MATRIX_FLOAT);

        float matrixScaleX = getMatrixScaleX();
        if (scale >= matrixScaleX) {
            setGestureScale(scale / matrixScaleX, focusX, focusY);
        }
    }

}