package com.edit.picture.model;

import android.animation.TypeEvaluator;

/**
 * 位置纠正model
 * Create by yin13 smyhvae on 2019/7/9
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoEditEvaluator implements TypeEvaluator<PhotoEditCorrection> {
    private float tranX;//X
    private float tranY;//Y
    private float angle;//角度
    private float scale;//缩放系数
    private PhotoEditCorrection correction;

    public PhotoEditEvaluator(float tranX, float tranY, float angle) {
        this.tranX = tranX;
        this.tranY = tranY;
        this.angle = angle;
    }

    public PhotoEditEvaluator() {
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

    @Override
    public PhotoEditCorrection evaluate(float fraction, PhotoEditCorrection startValue, PhotoEditCorrection endValue) {
        float endTranX = endValue.getTranX();
        float endTranY = endValue.getTranY();
        float endAngle = endValue.getAngle();

        float startTranX = startValue.getTranX();
        float startTranY = startValue.getTranY();
        float startAngle = startValue.getAngle();

        float tranX = startTranX + fraction * (endTranX - startTranX);
        float tranY = startTranY + fraction * (endTranY - startTranY);
        float angle = startAngle + fraction * (endAngle - startAngle);
        if (correction == null) {
            correction = new PhotoEditCorrection(tranX, tranY, angle);
        } else {
            correction.setValue(tranX, tranY, angle);
        }
        return correction;
    }
}
