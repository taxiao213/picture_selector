package com.selector.picture.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * viewpager 解决图片放大缩小时奔溃的问题
 * Create by Han on 2019/5/22
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class MyViewPager extends ViewPager {
    private boolean mIsDisallowIntercept = false;

    public MyViewPager(@NonNull Context context) {
        this(context, null);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            //只能捕捉和这个异常，捕捉Exception 滑动会出现问题
            Log.e("IllegalArgument >>> ", "onTouchEvent");
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            //只能捕捉和这个异常，捕捉Exception 滑动会出现问题
            Log.e("IllegalArgument >>> ", "onInterceptTouchEvent");
            ex.printStackTrace();
        }
        return false;
    }
}
