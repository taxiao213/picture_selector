package com.edit.picture.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.Editable;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;

import com.selector.picture.R;

/**
 * 带背景色的EditText
 * Create by Han on 2019/6/21
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoEditTextView extends android.support.v7.widget.AppCompatEditText {
    private Paint mPaint;
    private int DEFAULT_CORNER = 10;
    private float[] CORNER_TOP = {DEFAULT_CORNER, DEFAULT_CORNER, DEFAULT_CORNER, DEFAULT_CORNER, 0, 0, 0, 0};
    private float[] CORNER_BOTTOM = {0, 0, 0, 0, DEFAULT_CORNER, DEFAULT_CORNER, DEFAULT_CORNER, DEFAULT_CORNER};
    private float[] CORNER_ALL = {DEFAULT_CORNER, DEFAULT_CORNER, DEFAULT_CORNER, DEFAULT_CORNER, DEFAULT_CORNER, DEFAULT_CORNER, DEFAULT_CORNER, DEFAULT_CORNER};
    private float[] CORNER_NONE = {0, 0, 0, 0, 0, 0, 0, 0};

    public PhotoEditTextView(Context context) {
        this(context, null);
    }

    public PhotoEditTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public PhotoEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(getContext().getResources().getColor(R.color.grey_00));//默认透明色
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Editable text = getText();
        //满一行 自动换行，绘制背景色
        if (text != null) {
            int length = text.length();
            if (length > 0) {
                int paddingStart = getPaddingStart();
                int paddingEnd = getPaddingEnd();
                int paddingTop = getPaddingTop();
                int paddingBottom = getPaddingBottom();
                int measuredWidth = getMeasuredWidth();
                int measuredHeight = getMeasuredHeight();
                int lineCount = getLineCount();
                int lineHeight = getLineHeight();

                final int offset = getSelectionStart();
                Layout layout = getLayout();
                final int line = layout.getLineForOffset(offset);
                int lineTop = layout.getLineTop(line);
                int lineBottom = layout.getLineBottom(line);
                final int bottom = layout.getLineTop(line + 1); // 算出第 line+1 行的顶部坐标
                int cursorHeight = lineBottom - lineTop;//光标高度
                Log.e("padding", " paddingStart== " + paddingStart + " paddingEnd==" + paddingEnd + " paddingTop=="
                        + paddingTop + " paddingBottom==" + paddingBottom + " measuredWidth==" + measuredWidth
                        + " measuredHeight==" + measuredHeight + " lineCount==" + lineCount + " lineHeight==" + lineHeight
                        + " offset==" + offset + " lineTop==" + lineTop + " lineBottom==" + lineBottom
                        + " bottom==" + bottom);
                RectF rectF = null;
                for (int i = 0; i < lineCount; i++) {
                    if (i == 0) {
                        Rect rect = new Rect();
                        getLineBounds(i, rect);
                        Path path = new Path();
                        if (i == (lineCount - 1)) {
//                            rectF = new RectF(0, 0, rect.right + paddingEnd, paddingTop + paddingBottom + lineHeight);
                            rectF = new RectF(0, 0, rect.right + paddingEnd, paddingTop + paddingBottom + cursorHeight);
                            path.addRoundRect(rectF, CORNER_ALL, Path.Direction.CW);
                        } else {
//                            rectF = new RectF(0, 0, rect.right + paddingEnd, paddingTop + lineHeight + paddingBottom);
                            rectF = new RectF(0, 0, rect.right + paddingEnd, paddingTop + paddingBottom + cursorHeight);
                            path.addRoundRect(rectF, CORNER_TOP, Path.Direction.CW);
                        }
                        canvas.drawPath(path, mPaint);
                    } else {
                        Rect rect = new Rect();
                        getLineBounds(i, rect);
                        Path path = new Path();
                        if (i == (lineCount - 1)) {
//                            rectF = new RectF(0, paddingTop + lineHeight * i, rect.right + paddingEnd, paddingTop + paddingBottom + lineHeight * (i + 1));
                            rectF = new RectF(0, paddingTop + cursorHeight * i, rect.right + paddingEnd, paddingTop + paddingBottom + cursorHeight * (i + 1));
                            path.addRoundRect(rectF, CORNER_BOTTOM, Path.Direction.CW);
                        } else {
//                            rectF = new RectF(0, paddingTop + lineHeight * i, rect.right + paddingEnd, paddingTop + lineHeight * (i + 1));
                            rectF = new RectF(0, paddingTop + cursorHeight * i, rect.right + paddingEnd, paddingTop + cursorHeight * (i + 1));
                            path.addRoundRect(rectF, CORNER_NONE, Path.Direction.CW);
                        }
                        canvas.drawPath(path, mPaint);
                    }
                }
            }
        }
        super.onDraw(canvas);
    }

    /**
     * 获取文字的高度
     *
     * @param text String
     * @return int
     */
    private int getTextHeight(String text) {
        Rect bounds = new Rect();
        //获取文本框
        mPaint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.height();
    }

    /**
     * 获取文字的宽度
     *
     * @param text String
     * @return int
     */
    private int getTextWidth(String text) {
        Rect bounds = new Rect();
        //获取文本框
        mPaint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.width();
    }

    /**
     * 设置canvas的背景颜色
     *
     * @param resources int 资源文件 eg: R.color.white
     */
    public void setMPaintColor(int resources) {
        if (mPaint != null) {
            mPaint.setColor(resources);
        }
    }

    /**
     * 获取canvas的背景颜色
     *
     * @return int 背景颜色
     */
    public int getMPaintColor() {
        return mPaint.getColor();
    }
}
