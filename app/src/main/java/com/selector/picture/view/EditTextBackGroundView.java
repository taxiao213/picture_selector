package com.selector.picture.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import android.util.Log;
import android.widget.EditText;

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
        mPaint.setAlpha(200);

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
                Log.e("padding", " paddingStart== " + paddingStart + " paddingEnd==" + paddingEnd + " paddingTop=="
                        + paddingTop + " paddingBottom==" + paddingBottom + " lineSpacingExtra==" + lineSpacingExtra
                        + " lineSpacingMultiplier==" + lineSpacingMultiplier + " measuredWidth==" + measuredWidth
                        + " measuredHeight==" + measuredHeight + " lineCount==" + lineCount + " lineHeight==" + lineHeight);
                for (int i = 0; i < length; i++) {
                    String substring = textString.substring(i, i + 1);
                    RectF rectF = new RectF(0, paddingTop + measuredHeight+lineHeight*0, width + DEFAULT_CORNER + paddingStart + paddingEnd, paddingTop  + paddingBottom+measuredHeight+lineHeight*0 );
                    mPaint.setColor(getResources().getColor(R.color.picture_edit_round_color3));
                    canvas.drawRoundRect(rectF, 10, 10, mPaint);



                    /*if (i > 50) {
                        RectF rectF = new RectF(0, paddingTop + getTextHeight(substring) * 2, width + DEFAULT_CORNER + paddingStart + paddingEnd, paddingTop + getTextHeight(substring) * 3 + paddingBottom + lineSpacingExtra * lineSpacingMultiplier);
                        mPaint.setColor(getResources().getColor(R.color.picture_edit_round_color3));
                        canvas.drawRoundRect(rectF, 10, 10, mPaint);
//                        canvas.drawText(substring, getTextWidth(substring) + width, 50, mPaintText);
                    } else if (i > 30) {
                        RectF rectF = new RectF(0, paddingTop + getTextHeight(substring), width + DEFAULT_CORNER + paddingStart + paddingEnd, paddingTop + getTextHeight(substring) * 2 + lineSpacingExtra * lineSpacingMultiplier);
                        mPaint.setColor(getResources().getColor(R.color.picture_edit_round_color4));
                        canvas.drawRect(rectF, mPaint);
//                        canvas.drawText(substring, getTextWidth(substring) + width, 50, mPaintText);
                    } else {
                        RectF rectF = new RectF(width, 0, width + DEFAULT_CORNER + paddingStart + paddingEnd, paddingTop + getTextHeight(substring) + lineSpacingExtra * lineSpacingMultiplier);
                        mPaint.setColor(getResources().getColor(R.color.picture_edit_round_color5));
                        canvas.drawRoundRect(rectF, 10, 10, mPaint);
//                        canvas.drawText(substring, getTextWidth(substring) + width, 50, mPaintText);
                    }*/
                    width += getTextWidth(substring);
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
