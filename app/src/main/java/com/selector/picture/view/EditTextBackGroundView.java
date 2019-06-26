package com.selector.picture.view;

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
public class EditTextBackGroundView extends android.support.v7.widget.AppCompatEditText {
    private Paint mPaint;
    private Paint mPaintText;
    private int DEFAULT_HEIGHT = 10;
    private int DEFAULT_CORNER = 10;
    private float[] CORNER_TOP = {DEFAULT_CORNER, DEFAULT_CORNER, DEFAULT_CORNER, DEFAULT_CORNER, 0, 0, 0, 0};
    private float[] CORNER_BOTTOM = {0, 0, 0, 0, DEFAULT_CORNER, DEFAULT_CORNER, DEFAULT_CORNER, DEFAULT_CORNER};
    private float[] CORNER_ALL = {DEFAULT_CORNER, DEFAULT_CORNER, DEFAULT_CORNER, DEFAULT_CORNER, DEFAULT_CORNER, DEFAULT_CORNER, DEFAULT_CORNER, DEFAULT_CORNER};
    private float[] CORNER_NONE = {0, 0, 0, 0, 0, 0, 0, 0};

    public EditTextBackGroundView(Context context) {
        this(context, null);
    }

    public EditTextBackGroundView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public EditTextBackGroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(context.getResources().getColor(R.color.colorAccent));

        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setAntiAlias(true);
        mPaintText.setTextSize(getTextSize());
        mPaintText.setColor(context.getResources().getColor(R.color.colorPrimary));

    }


    @Override
    protected void onDraw(Canvas canvas) {

       /* if (text != null) {
            int height = 0;
            int width = 0;
            int length = text.length();
            String textString = text.toString();
            Log.e("---长度", length + "---" + textString);
            if (length > 0) {
                for (int i = 0; i < length - 1; i++) {
                    String st = textString.substring(i, i + 1);
                    Log.e("---", st + "---剩余字体--- " + textString);
                    RectF rectF = new RectF(0, 0, width + getPaddingRight() + getPaddingLeft(), getTextHeight(st) + getPaddingBottom() + getPaddingTop());
                    canvas.drawRoundRect(rectF, 10, 10, mPaint);
                    width += getTextWidth(st);
                }
            }
        }*/
//       canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        Editable text = getText();
        //满一行 自动换行，绘制背景色
        if (text != null) {
            int length = text.length();
            float width = 0;
            float height = 0;
            if (length > 0) {
                String textString = text.toString();
                int paddingStart = getPaddingStart();
                int paddingEnd = getPaddingEnd();
                int paddingTop = getPaddingTop();
                int paddingBottom = getPaddingBottom();
                float lineSpacingExtra = getLineSpacingExtra();
                float lineSpacingMultiplier = getLineSpacingMultiplier();
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
                        + paddingTop + " paddingBottom==" + paddingBottom + " lineSpacingExtra==" + lineSpacingExtra
                        + " lineSpacingMultiplier==" + lineSpacingMultiplier + " measuredWidth==" + measuredWidth
                        + " measuredHeight==" + measuredHeight + " lineCount==" + lineCount + " lineHeight==" + lineHeight
                        + " offset==" + offset + " lineTop==" + lineTop + " lineBottom==" + lineBottom
                        + " bottom==" + bottom);
                int lines = (measuredHeight - paddingTop - paddingBottom) / lineHeight;
                Log.e("padding ", " lines== " + lines);
                RectF rectF = null;
                for (int i = 0; i < lineCount; i++) {
                    if (i == 0) {
                        Rect rect = new Rect();
                        getLineBounds(i, rect);
                        Log.e("padding 0 ", " right== " + rect.right + " bottom== " + rect.bottom);
//                        RectF rectF = new RectF(0, 0, 200 + paddingStart + paddingEnd, paddingTop   +paddingBottom + lineHeight);
//                        RectF rectF = new RectF(0, 0, rect.right + paddingEnd, rect.bottom);

//                        mPaint.setColor(getResources().getColor(R.color.picture_edit_round_color5));
//                        canvas.drawRoundRect(rectF, DEFAULT_CORNER, DEFAULT_CORNER, mPaint);
//                        mPaint.setStyle(Paint.Style.FILL);
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
                        Log.e("padding " + i + " ", " right== " + rect.right + " bottom== " + rect.bottom);
//                        RectF rectF = new RectF(0, paddingTop + paddingBottom + lineHeight * i - DEFAULT_CORNER * 2, 270 + paddingStart + paddingEnd, paddingBottom + lineHeight * (i + 1));
//                        RectF rectF = new RectF(0, paddingTop + paddingBottom + lineHeight * i - DEFAULT_CORNER * 2, rect.right + paddingEnd, paddingBottom + lineHeight * (i + 1));
//                        canvas.drawRoundRect(rectF, DEFAULT_CORNER, DEFAULT_CORNER, mPaint);

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


    private int getTextHeight(String text) {
        Rect bounds = new Rect();
        //获取文本框
        mPaintText.getTextBounds(text, 0, text.length(), bounds);
        return bounds.height();
    }

    private int getTextWidth(String text) {
        Rect bounds = new Rect();
        //获取文本框
        mPaintText.getTextBounds(text, 0, text.length(), bounds);
        return bounds.width();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int getMeasuredLength(int length, boolean isWidth) {
        int specMode = MeasureSpec.getMode(length);
        int specSize = MeasureSpec.getSize(length);
        int size;
        int padding = isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom();
        if (specMode == MeasureSpec.EXACTLY) {
            size = specSize;
        } else {
            size = isWidth ? padding : DEFAULT_HEIGHT + padding;
            if (specMode == MeasureSpec.AT_MOST) {
                size = Math.min(size, specSize);
            }
        }
        return size;
    }

}
