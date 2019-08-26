package com.edit.picture.util;

import android.animation.ValueAnimator;

import com.edit.picture.model.PhotoEditCorrection;
import com.edit.picture.model.PhotoEditEvaluator;

/**
 * 动画缩放
 * Create by yin13 smyhvae on 2019/7/8
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoEditAnimator extends ValueAnimator {

    private final int DURATION_TIME = 220;//默认加载时长

    public PhotoEditAnimator(AnimatorListener animatorListener, AnimatorUpdateListener animatorUpdateListener) {
        setDuration(DURATION_TIME);
        addListener(animatorListener);
        addUpdateListener(animatorUpdateListener);
    }

    @Override
    public void setObjectValues(Object... values) {
        super.setObjectValues(values);
        PhotoEditEvaluator evaluator = new PhotoEditEvaluator();
        setEvaluator(evaluator);
    }

    public void setFloatValues(PhotoEditCorrection start, float endScale) {
        PhotoEditCorrection end = new PhotoEditCorrection(PhotoEditCorrection.TYPE1, 0F, 0F, 0F, endScale);
        setObjectValues(start, end);
    }
}
