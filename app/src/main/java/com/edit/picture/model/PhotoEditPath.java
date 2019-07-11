package com.edit.picture.model;

import android.graphics.Path;

/**
 * 绘制路径
 * Create by yin13 smyhvae on 2019/7/11
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoEditPath extends Path {
    private int mPointerId;//对于每个触控的点的细节，我们可以通过一个循环执行getPointerId方法获取索引

    public boolean isPointerId(int pointerId) {
        return mPointerId == pointerId;
    }

    public void setPointerId(int pointerId) {
        this.mPointerId = pointerId;
    }

    public void setRest(float x, float y) {
        reset();
        moveTo(x, y);
    }

}
