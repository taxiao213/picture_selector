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
    private int textColor;//字体颜色
    private float scaleCoefficient;//缩放系数 默认1
    private float radius;//最小半径
    private float spacing;//间距
    private float COEFFICIENT_NORMAL = 1.0F;//正常系数
    private float COEFFICIENT_SCALE = 1.2F;//缩放系数
    private boolean isSelected = false;//是否选择状态
    private String text;//文本

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

    public ColorModel(int frontColor, boolean isSelected, String text) {
        this.frontColor = frontColor;
        this.isSelected = isSelected;
        this.text = text;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getCOEFFICIENT_NORMAL() {
        return COEFFICIENT_NORMAL;
    }

    public void setCOEFFICIENT_NORMAL(float COEFFICIENT_NORMAL) {
        this.COEFFICIENT_NORMAL = COEFFICIENT_NORMAL;
    }

    public float getCOEFFICIENT_SCALE() {
        return COEFFICIENT_SCALE;
    }

    public void setCOEFFICIENT_SCALE(float COEFFICIENT_SCALE) {
        this.COEFFICIENT_SCALE = COEFFICIENT_SCALE;
    }
}
