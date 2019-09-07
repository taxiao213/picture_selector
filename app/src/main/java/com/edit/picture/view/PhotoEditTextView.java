package com.edit.picture.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * 带背景色的EditText
 * Create by Han on 2019/6/21
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoEditTextView extends AppCompatEditText {
    private int mBackgroundColor;
    private GradientDrawable background;
    private boolean isSelected = false;

    public PhotoEditTextView(Context context) {
        this(context, null);
    }

    public PhotoEditTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        background = (GradientDrawable) getBackground();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (background != null) {
            if (isSelected) {
                background.setColor(mBackgroundColor);
            } else {
                background.setColor(android.R.color.transparent);
            }
        }
    }

    /**
     * 设置背景颜色
     *
     * @param resources int 资源文件 eg: R.color.white
     */
    public void setBackgroudColor(int resources) {
        this.mBackgroundColor = resources;
    }

    /**
     * 获取背景颜色
     *
     * @return int 背景颜色
     */
    public int getBackgroudColor() {
        return mBackgroundColor;
    }

    public void setSelect(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
