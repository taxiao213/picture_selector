package com.selector.picture.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.selector.picture.R;
import com.selector.picture.activity.PhotoPreviewsActivity;
import com.selector.picture.activity.PhotoSelectActivity;
import com.selector.picture.base.BaseActivity;
import com.selector.picture.constant.Constant;
import com.selector.picture.model.LocalMedia;
import com.selector.picture.model.MimeType;
import com.selector.picture.model.PicConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
     * 开启键盘
     *
     * @param view
     */
    public static void openBroad(Context context, View view) {
        if (context == null) return;
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInput(view, 0);
        }
    }

    /**
     * 关闭键盘
     *
     * @param view
     */
    public static void closeBroad(Context context, View view) {
        if (context == null) return;
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
     * 是否是长图 高大于宽度的三倍时显示长图 宽大于高度的三倍时显示长图
     *
     * @param media LocalMedia
     * @return true 是   false 不是
     */
    public static boolean isLongImg(LocalMedia media) {
        if (null != media) {
            int width = media.getWidth();
            int height = media.getHeight();
            return height > width * 3 || width > height * 3;
        }
        return false;
    }

    /**
     * 设置选中文件的状态 选中有阴影
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
     * @param type      Constant.TYPE2 点击音效效果 {@link Constant#TYPE1,Constant#TYPE2}
     */
    public static void setSelectStatus(ImageView imageView, TextView tv_check, boolean selected, int type) {
        tv_check.setSelected(selected);
        if (selected) {
            if (type == Constant.TYPE2) {
                if (PicConfig.getInstances().isloadVoice()) {
                    VoiceUtils.playVoice(imageView.getContext(), selected);
                }
            }
            imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), R.color.image_overlay_true), PorterDuff.Mode.SRC_ATOP);
        } else {
            imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), R.color.image_overlay_false), PorterDuff.Mode.SRC_ATOP);
        }
    }

    /**
     * 设置选中文件的状态 选中有阴影
     *
     * @param imageView 图片
     * @param selected  true 选中 false 不选择
     * @param type      1 加载的全部数据(删除增加) 2 预览数据(不选择 显示遮罩)
     */
    public static void setPreviewSelectStatus(ImageView imageView, boolean selected, int type) {
        if (type == Constant.TYPE2) {
            if (selected) {
                imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), R.color.image_preview_overlay_true), PorterDuff.Mode.SRC_ATOP);
            } else {
                imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), R.color.image_preview_overlay_false), PorterDuff.Mode.SRC_ATOP);
            }
        }
    }

    /**
     * 设置选中执行的动画
     *
     * @param imageView 执行动画的view
     * @param selected  true 选中 false 不选择
     */
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
    private static void zoom(ImageView imageView) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 1.12f),
                ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 1.12f));
        set.setDuration(Constant.PIC_ANIMATION_DURATION);
        set.start();
    }

    /**
     * 缩小动画
     */
    private static void disZoom(ImageView imageView) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(imageView, "scaleX", 1.12f, 1f),
                ObjectAnimator.ofFloat(imageView, "scaleY", 1.12f, 1f));
        set.setDuration(Constant.PIC_ANIMATION_DURATION);
        set.start();
    }

    public static void setAnimation(ImageView imageView, boolean selected) {
        if (selected) {
            ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 1.2f, 1f, 1.2f, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
            scaleAnimation.setDuration(Constant.PIC_ANIMATION_DURATION);
            scaleAnimation.start();
            scaleAnimation.setFillAfter(true);
            imageView.startAnimation(scaleAnimation);
        } else {
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.2f, 1f, 1.2f, 1f, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
            scaleAnimation.setDuration(Constant.PIC_ANIMATION_DURATION);
            scaleAnimation.start();
            scaleAnimation.setFillAfter(true);
            imageView.startAnimation(scaleAnimation);
        }
    }

    /**
     * 预览界面view展开收缩动画
     *
     * @param view ViewGroup
     * @param from float
     * @param to   float
     */
    public static void startAnimation(final ViewGroup view, float from, float to) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
        valueAnimator.setDuration(Constant.PIC_ANIMATION_DURATION);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = (int) value;
                view.requestLayout();
            }
        });
        valueAnimator.start();
    }

    /**
     * 提示语
     *
     * @param context context
     */
    public static void toastShow(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 设置选择数量上限的提示
     *
     * @param sendMedia List<LocalMedia>
     * @param context   Context
     * @return boolean
     */
    public static boolean selectNumNotice(List<LocalMedia> sendMedia, Context context) {
        if (sendMedia != null && (sendMedia.size() >= PicConfig.getInstances().getMaxSelectNum())) {
            if (PicConfig.getInstances().getImageType() == MimeType.TYPE_ALL) {
                UIUtils.toastShow(context, context.getString(R.string.picture_selector_notice_all_count, String.valueOf(PicConfig.getInstances().getMaxSelectNum())));
            } else if (PicConfig.getInstances().getImageType() == MimeType.TYPE_IMAGE) {
                UIUtils.toastShow(context, context.getString(R.string.picture_selector_notice_pic_count, String.valueOf(PicConfig.getInstances().getMaxSelectNum())));
            } else if (PicConfig.getInstances().getImageType() == MimeType.TYPE_VIDEO) {
                UIUtils.toastShow(context, context.getString(R.string.picture_selector_notice_video_count, String.valueOf(PicConfig.getInstances().getMaxSelectNum())));
            } else if (PicConfig.getInstances().getImageType() == MimeType.TYPE_AUDIO) {
                UIUtils.toastShow(context, context.getString(R.string.picture_selector_notice_audio_count, String.valueOf(PicConfig.getInstances().getMaxSelectNum())));
            } else {
                UIUtils.toastShow(context, context.getString(R.string.picture_selector_notice_all_count, String.valueOf(PicConfig.getInstances().getMaxSelectNum())));
            }
            return true;
        }
        return false;
    }


    /**
     * 跳转预览界面
     */
    public static void startActivityForResult(PhotoSelectActivity context, LocalMedia model) {
        if (context == null) return;
        Intent intent = new Intent(context, PhotoPreviewsActivity.class);
        if (model != null) {
            intent.putExtra(Constant.ACTION_TYPE1, model);
        }
        context.startActivityForResult(intent, Constant.TYPE1);
    }

    /**
     * 打开文件调用系统工具
     * 7.0以上需要FileProvider
     *
     * @param mActivity 上下文
     * @param filePath  文件的绝对路径
     */
    public static void openFile(Context mActivity, final String filePath) {
        if (!TextUtils.isEmpty(filePath) && filePath.contains(".")) {
            String ext = filePath.substring(filePath.lastIndexOf('.') + 1).toLowerCase(Locale.US);
            try {
                MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
                String mime = mimeTypeMap.getMimeTypeFromExtension(ext);
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                File file = new File(filePath);
                Uri uri = getUri(mActivity, file);
                intent.setDataAndType(uri, mime);
                mActivity.startActivity(intent);
            } catch (Exception e) {
                toastShow(mActivity, mActivity.getString(R.string.picture_previews_not_open_file));
            }
        } else {
            toastShow(mActivity, mActivity.getString(R.string.picture_previews_not_open_file));
        }
    }

    /**
     * 返回Uri格式
     *
     * @param mActivity Context
     * @param file      File 文件路径
     * @return Uri
     */
    public static Uri getUri(Context mActivity, File file) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(mActivity, "com.selector.picture.provider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    /**
     * 设置字体颜色
     *
     * @param context Context
     * @param color   字体颜色
     * @return Uri
     */
    public static int setTextColor(Context context, int color) {
        int textColor;
        double calculate = ColorUtils.calculateLuminance(color);
        if (calculate >= 0.5) {
            //设置黑色字体
            textColor = context.getResources().getColor(android.R.color.black);
        } else {
            //设置白色字体
            textColor = context.getResources().getColor(android.R.color.white);
        }
        return textColor;
    }

}
