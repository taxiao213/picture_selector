package com.edit.picture.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.selector.picture.R;

/**
 * 带背景色,可缩放的 TextView
 * Created by yin13 on 2019/8/24
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoEditScaleTextView extends AppCompatTextView {

    private Paint paint;

    public PhotoEditScaleTextView(Context context) {
        this(context, null);
    }

    public PhotoEditScaleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoEditScaleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3F);

        GradientDrawable drawable = (GradientDrawable) getBackground();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable drawable = new ShapeDrawable();
        Drawable drawable1 = getResources().getDrawable(R.drawable.top_send_text_bg_select_shape);

        setBackground(drawable);
    }





}
