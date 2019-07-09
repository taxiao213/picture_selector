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
    private PhotoEditCorrection correction;

    public PhotoEditEvaluator() {
    }

    @Override
    public PhotoEditCorrection evaluate(float fraction, PhotoEditCorrection startValue, PhotoEditCorrection endValue) {
        float endTranX = endValue.getTranX();
        float endTranY = endValue.getTranY();
        float endAngle = endValue.getAngle();
        float endScale = endValue.getScale();

        float startTranX = startValue.getTranX();
        float startTranY = startValue.getTranY();
        float startAngle = startValue.getAngle();
        float startScale = startValue.getScale();

        float tranX = startTranX + fraction * (endTranX - startTranX);
        float tranY = startTranY + fraction * (endTranY - startTranY);
        float angle = startAngle + fraction * (endAngle - startAngle);
        float scale = startScale + fraction * (endScale - startScale);
        int type = startValue.getType();
        if (correction == null) {
            correction = new PhotoEditCorrection(type, tranX, tranY, angle, scale);
        } else {
            correction.setValue(type, tranX, tranY, angle, scale);
        }
        return correction;
    }
}
