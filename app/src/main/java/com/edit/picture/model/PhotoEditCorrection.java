package com.edit.picture.model;

/**
 * 位置纠正model
 * Create by yin13 smyhvae on 2019/7/9
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoEditCorrection {
    private float tranX;//X
    private float tranY;//Y
    private float angle;//角度

    public PhotoEditCorrection() {
    }

    public PhotoEditCorrection(float tranX, float tranY, float angle) {
        this.tranX = tranX;
        this.tranY = tranY;
        this.angle = angle;
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

    public void setValue(float tranX, float tranY, float angle) {
        this.tranX = tranX;
        this.tranY = tranY;
        this.angle = angle;
    }

}
