package com.edit.picture.model;

/**
 * 位置纠正model
 * Create by yin13 smyhvae on 2019/7/9
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoEditCorrection {
    public static int TYPE1 = 1;
    public static int TYPE2 = 2;
    private int type;//1 缩放 2 滑动
    private float tranX;//X
    private float tranY;//Y
    private float angle;//角度
    private float scale;//缩放系数

    public PhotoEditCorrection() {
    }

    public PhotoEditCorrection(int type, float tranX, float tranY, float angle, float scale) {
        this.type = type;
        this.tranX = tranX;
        this.tranY = tranY;
        this.angle = angle;
        this.scale = scale;
    }

    public int getType() {
        return type;
    }

    public float getTranX() {
        return tranX;
    }

    public float getTranY() {
        return tranY;
    }

    public float getAngle() {
        return angle;
    }

    public float getScale() {
        return scale;
    }

    public void setValue(int type, float tranX, float tranY, float angle, float scale) {
        this.type = type;
        this.tranX = tranX;
        this.tranY = tranY;
        this.angle = angle;
        this.scale = scale;
    }

}
