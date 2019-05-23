package com.selector.picture.utils;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PorterDuff;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.selector.picture.R;
import com.selector.picture.model.LocalMedia;
import com.selector.picture.model.PicConfig;

/**
 * view工具类
 * Create by Han on 2019/5/21
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class UIUtils {

    /**
     * dp转换px
     *
     * @param context 上下文
     * @param dp      int值
     */
    public static int dp2px(Context context, float dp) {
        if (context == null) return 0;
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    /**
     * px转换dip
     *
     * @param context 上下文
     * @param px      int值
     */
    public static int px2dp(Context context, int px) {
        if (context == null) return 0;
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 获取屏幕宽度
     *
     * @param context 上下文
     */
    public static int getScreenWidth(Context context) {
        if (context == null) return 0;
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context 上下文
     */
    public static int getScreenHeight(Context context) {
        if (context == null) return 0;
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 设置drawable
     *
     * @param view         TextView
     * @param drawableLeft 图标地址
     */
    public static void setDrawable(TextView view, int drawableLeft) {
        if (view != null) {
            view.setVisibility(View.VISIBLE);
            view.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, 0, 0, 0);
        }
    }

    /**
     * 是否是长图 高大于宽度的三倍时显示长图
     *
     * @param media LocalMedia
     * @return true 是   false 不是
     */
    public static boolean isLongImg(LocalMedia media) {
        if (null != media) {
            int width = media.getWidth();
            int height = media.getHeight();
            int h = width * 3;
            return height > h;
        }
        return false;
    }

    /**
     * 设置选中文件的状态
     * <p>
     * LocalMedia
     * 1.PorterDuff.Mode.CLEAR 所绘制不会提交到画布上。
     * 2.PorterDuff.Mode.SRC 显示上层绘制图片
     * 3.PorterDuff.Mode.DST  显示下层绘制图片
     * 4.PorterDuff.Mode.SRC_OVER 正常绘制显示，上下层绘制叠盖。
     * 5.PorterDuff.Mode.DST_OVER 上下层都显示。下层居上显示。
     * 6.PorterDuff.Mode.SRC_IN 取两层绘制交集。显示上层。
     * 7.PorterDuff.Mode.DST_IN 取两层绘制交集。显示下层。
     * 8.PorterDuff.Mode.SRC_OUT 取上层绘制非交集部分。
     * 9.PorterDuff.Mode.DST_OUT 取下层绘制非交集部分。
     * 10.PorterDuff.Mode.SRC_ATOP 取下层非交集部分与上层交集部分
     * 11.PorterDuff.Mode.DST_ATOP 取上层非交集部分与下层交集部分
     * 12.PorterDuff.Mode.XOR 取两层绘制非交集。两层绘制非交集。
     * 13.PorterDuff.Mode.DARKEN 上下层都显示。变暗
     * 14.PorterDuff.Mode.LIGHTEN 上下层都显示。变量
     * 15.PorterDuff.Mode.MULTIPLY 取两层绘制交集
     * 16.PorterDuff.Mode.SCREEN 上下层都显示。
     *
     * @param imageView 图片
     * @param tv_check  复选框view
     * @param selected  true 选中 false 不选择
     */
    public static void setSelectStatus(ImageView imageView, TextView tv_check, boolean selected) {
        tv_check.setSelected(selected);
        if (selected) {
            imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), R.color.image_overlay_true), PorterDuff.Mode.SRC_ATOP);
        } else {
            imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), R.color.image_overlay_false), PorterDuff.Mode.SRC_ATOP);
        }
    }

    public static void setSelectAnimation(ImageView imageView, boolean selected) {
        if (PicConfig.getInstances().isAnimation()) {
            if (selected) {
                zoom(imageView);
            } else {
                disZoom(imageView);
            }
        }
    }

    /**
     * 放大动画
     */
    private static void zoom(ImageView iv_img) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(iv_img, "scaleX", 1f, 1.12f),
                ObjectAnimator.ofFloat(iv_img, "scaleY", 1f, 1.12f)
        );
        set.setDuration(450);
        set.start();
    }

    /**
     * 缩小动画
     */
    private static void disZoom(ImageView iv_img) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(iv_img, "scaleX", 1.12f, 1f),
                ObjectAnimator.ofFloat(iv_img, "scaleY", 1.12f, 1f)
        );
        set.setDuration(450);
        set.start();
    }

}