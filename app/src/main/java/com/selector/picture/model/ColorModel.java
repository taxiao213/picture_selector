package com.selector.picture.model;

/**
 * 图片编辑界面圆形图片model
 * Create by Han on 2019/6/20
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class ColorModel {
    private int frontColor;//前景色
    private int backColor;//后景色
    private float scaleCoefficient;//缩放系数 默认1
    private float radius;//最小半径
    private float spacing;//间距
    private float COEFFICIENT_NORMAL = 1.0F;//正常系数
    private float COEFFICIENT_SCALE = 1.2F;//缩放系数

    /**
     * @param frontColor 前背景色
     * @param isScale    是否缩放 true 缩放  false 不缩放
     */
    public ColorModel(int frontColor, boolean isScale) {
        if (isScale) {
            this.scaleCoefficient = COEFFICIENT_SCALE;
        } else {
            this.scaleCoefficient = COEFFICIENT_NORMAL;
        }
        this.frontColor = frontColor;
    }

    /**
     * 还原缩放状态  默认1
     */
    public void reductionCoefficient() {
        this.scaleCoefficient = COEFFICIENT_NORMAL;
    }

    /**
     * 设置缩放系数
     */
    public void setScaleCoefficient() {
        this.scaleCoefficient = COEFFICIENT_SCALE;
    }

    public int getFrontColor() {
        return frontColor;
    }

    public void setFrontColor(int frontColor) {
        this.frontColor = frontColor;
    }

    public int getBackColor() {
        return backColor;
    }

    public void setBackColor(int backColor) {
        this.backColor = backColor;
    }

    public float getScaleCoefficient() {
        return scaleCoefficient;
    }

    public void setScaleCoefficient(float scaleCoefficient) {
        this.scaleCoefficient = scaleCoefficient;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getSpacing() {
        return spacing;
    }

    public void setSpacing(float spacing) {
        this.spacing = spacing;
    }

}
