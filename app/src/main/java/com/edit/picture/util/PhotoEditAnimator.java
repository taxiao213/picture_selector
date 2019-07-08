package com.edit.picture.util;

import android.animation.ValueAnimator;

/**
 * 动画缩放
 * Create by yin13 smyhvae on 2019/7/8
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoEditAnimator extends ValueAnimator {

    private final int DURATION_TIME = 300;//默认加载时长

    public PhotoEditAnimator(AnimatorListener animatorListener, AnimatorUpdateListener animatorUpdateListener) {
        setDuration(DURATION_TIME);
        addListener(animatorListener);
        addUpdateListener(animatorUpdateListener);
    }

    public void setFloat(float values) {
        setFloatValues(values, 1.0F);
    }

}
