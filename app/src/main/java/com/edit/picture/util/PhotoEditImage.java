package com.edit.picture.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
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
     *
     * @param canvas Canvas 画布
     */
    public void drawImage(Context context, Canvas canvas) {
        if (mBitmap == null) return;
        canvas.drawBitmap(mBitmap, matrix, null);
    }


    /**
     * 设置手势缩放系数
     *
     * @param scale  float
     * @param focusX
     * @param focusY float
     */
    public void setGestureScale(float scale, float focusX, float focusY) {
        if (scale == 1f) return;
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
     * @param width   宽
     * @param height  高
     * @param context 上下文
     */
    public void onSizeChange(int width, int height, Context context) {
        if (mBitmap == null) return;
        scale = Math.min(width * 1f / mBitmap.getWidth(), height * 1f / mBitmap.getHeight());
        float tranWidth = scale * mBitmap.getWidth();
        float tranHeight = scale * mBitmap.getHeight();
        int screenWidth = UIUtils.getScreenWidth(context);
        int screenHeight = UIUtils.getScreenHeight(context);
        tranWidth = getTranslate(tranWidth, screenWidth);
        tranHeight = getTranslate(tranHeight, screenHeight);
        matrix.postScale(scale, scale);
        matrix.postTranslate(tranWidth, tranHeight);
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
}
