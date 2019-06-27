package com.edit.picture.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

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
    private boolean init = false;//初始化为false
    /*完整图片边框*/
    private RectF mFrame = new RectF();


    /**
     * 设置图片
     *
     * @param bitmap Bitmap
     */
    public void setPhotoEditImage(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    /**
     * 绘制图片 小图片放大问题
     *
     * @param canvas Canvas 画布
     */
    public void drawImage(Context context, Canvas canvas) {
        if (!init) {
            int screenWidth = UIUtils.getScreenWidth(context);
            int width = mBitmap.getWidth();
            int scale = screenWidth / width;//缩放系数

            mFrame = new RectF(0, 0,  width, scale * mBitmap.getHeight());
            canvas.drawBitmap(mBitmap, null, mFrame, null);
            init = true;
        } else {
            canvas.drawBitmap(mBitmap, matrix, null);
        }
    }

    /**
     * 设置矩阵
     *
     * @param matrix Matrix
     */
    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    /**
     * 获取原始尺寸
     *
     * @return RectF
     */
    public RectF getmFrame() {
        return mFrame;
    }
}
