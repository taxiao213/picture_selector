package com.edit.picture.util;

import android.animation.ValueAnimator;

import com.edit.picture.model.PhotoEditCorrection;
import com.edit.picture.model.PhotoEditEvaluator;

/**
 * 位置纠正动画
 * Create by yin13 smyhvae on 2019/7/8
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoEditCorrectionAnimator extends ValueAnimator {

    private final int DURATION_TIME = 300;//默认加载时长

    public PhotoEditCorrectionAnimator(AnimatorUpdateListener animatorUpdateListener) {
        setDuration(DURATION_TIME);
        addUpdateListener(animatorUpdateListener);
    }

    @Override
    public void setObjectValues(Object... values) {
        super.setObjectValues(values);
        PhotoEditEvaluator evaluator = new PhotoEditEvaluator();
        setEvaluator(evaluator);
    }

    public void setCorrectionValues(PhotoEditCorrection start, PhotoEditCorrection end) {
        setObjectValues(start, end);
    }
}
