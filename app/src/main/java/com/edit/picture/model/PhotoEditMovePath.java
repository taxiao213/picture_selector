package com.edit.picture.model;

import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * 绘制路径
 * Create by yin13 smyhvae on 2019/7/12
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoEditMovePath {
    private Path path;//画笔路径
    private int paintColor;//画笔颜色
    private float paintWidth;//画笔宽度
    private Paint.Style paintStyle;//画笔风格
    private float scale;//放大系数
    private Matrix matrix;//矩阵
    private float paintMosaicWidth;//马赛克宽度
    public PhotoEditMovePath(Path path, int paintColor, float scale) {
        this.path = path;
        this.paintColor = paintColor;
        this.scale = scale;
    }

    public PhotoEditMovePath(Path path, int paintColor, float paintWidth, Paint.Style paintStyle) {
        this.path = path;
        this.paintColor = paintColor;
        this.paintWidth = paintWidth;
        this.paintStyle = paintStyle;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public int getPaintColor() {
        return paintColor;
    }

    public void setPaintColor(int paintColor) {
        this.paintColor = paintColor;
    }

    public float getPaintWidth() {
        return paintWidth;
    }

    public void setPaintWidth(float paintWidth) {
        this.paintWidth = paintWidth;
    }

    public Paint.Style getPaintStyle() {
        return paintStyle;
    }

    public void setPaintStyle(Paint.Style paintStyle) {
        this.paintStyle = paintStyle;
    }

    public float getScale() {
        return scale;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public float getPaintMosaicWidth() {
        return paintMosaicWidth;
    }

    public void setPaintMosaicWidth(float paintMosaicWidth) {
        this.paintMosaicWidth = paintMosaicWidth;
    }
}
