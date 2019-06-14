package com.selector.picture.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.selector.picture.R;
import com.selector.picture.activity.PhotoSelectActivity;
import com.selector.picture.constant.Constant;
import com.selector.picture.utils.UIUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 相册选择实体
 * Create by Han on 2019/5/17
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PicSelector {

    private WeakReference<Activity> mActivity;
    private WeakReference<Fragment> mFragment;
    private int mRequestCode;//设置返回的请求code
    private PicConfig mConfig;

    private PicSelector(Activity activity, Fragment fragment) {
        mConfig = PicConfig.getInstances();
        mActivity = new WeakReference(activity);
        mFragment = new WeakReference(fragment);
    }

    private PicSelector(Fragment fragment) {
        this(fragment.getActivity(), fragment);
    }

    private PicSelector(Activity activity) {
        this(activity, null);
    }


    /**
     * 调用上下文
     *
     * @param fragment Fragment
     * @return PicSelector
     */
    public static PicSelector create(Fragment fragment) {
        return new PicSelector(fragment);
    }

    /**
     * 调用上下文
     *
     * @param activity Activity
     * @return PicSelector
     */
    public static PicSelector create(Activity activity) {
        return new PicSelector(activity);
    }

    /**
     * 设置主题
     *
     * @param theme R.style.pictrue_white_Theme 默认主题
     * @return PicSelector
     */
    public PicSelector theme(int theme) {
        mConfig.theme(theme);
        return this;
    }

    /**
     * 获取主题
     */
    public int getTheme() {
        return mConfig.getTheme();
    }

    /**
     * 设置图片可选择最小数量，默认最小1个 最大9个
     *
     * @param num int
     * @return PicSelector
     */
    public PicSelector minSelectNum(@IntRange(from = 1) int num) {
        mConfig.minSelectNum(num);
        return this;
    }

    /**
     * 获取图片可选择最小数量，默认最小1个 最大9个
     */
    public int getMinSelectNum() {
        return mConfig.getMinSelectNum();
    }

    /**
     * 设置图片可选择最大数量，默认最小1个 最大9个
     *
     * @param num int
     * @return PicSelector
     */
    public PicSelector maxSelectNum(@IntRange(from = 1) int num) {
        mConfig.maxSelectNum(num);
        return this;
    }

    /**
     * 获取图片可选择最大数量，默认最小1个 最大9个
     */
    public int getMaxSelectNum() {
        return mConfig.getMaxSelectNum();
    }

    /**
     * 设置图片网格数量 默认3列
     *
     * @param num int
     * @return PicSelector
     */
    public PicSelector gridSize(int num) {
        mConfig.gridSize(num);
        return this;
    }

    /**
     * 获取图片网格数量 默认3列
     *
     * @return int
     */
    public int getGridSize() {
        return mConfig.getGridSize();
    }

    /**
     * 选择的模式
     *
     * @param mimeType int{@link MimeType#TYPE_ALL,MimeType#TYPE_IMAGE,MimeType#TYPE_AUDIO,MimeType#TYPE_VIDEO}
     * @return PicSelector
     */
    public PicSelector choose(int mimeType) {
        mConfig.imageType(mimeType);
        return this;
    }

    /**
     * 设置动图
     *
     * @param isGif true 选择的有Gif动图，false没有，默认是没有动图
     * @return PicSelector
     */
    public PicSelector gif(boolean isGif) {
        mConfig.gif(isGif);
        return this;
    }

    /**
     * 设置加载图片的宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
     *
     * @param width  glide width
     * @param height glide height
     * @return PicSelector
     */
    public PicSelector glideOverride(@IntRange(from = 100) int width, @IntRange(from = 100) int height) {
        mConfig.overrideWidth(width, height);
        return this;
    }

    /**
     * Glide压缩资源
     *
     * @param sizeMultiplier Applies a multiplier to the {@link com.bumptech.glide.request.target.Target}'s size before
     *                       loading the resource. Useful for loading thumbnails or trying to avoid loading huge resources
     * @return PicSelector
     */
    public PicSelector sizeMultiplier(@FloatRange(from = 0, to = 1) float sizeMultiplier) {
        mConfig.sizeMultiplier(sizeMultiplier);
        return this;
    }

    /**
     * 是否加载动画
     *
     * @param loadAnimation true 加载动画 false 不加载动画
     * @return PicSelector
     */
    public PicSelector loadAnimation(boolean loadAnimation) {
        mConfig.loadAnimation(loadAnimation);
        return this;
    }

    /**
     * 是否有点击声音，默认false
     *
     * @param loadVoice true 有声音 false 没有声音
     * @return PicSelector
     */
    public PicSelector loadVoice(boolean loadVoice) {
        mConfig.loadVoice(loadVoice);
        return this;
    }

    /**
     * 是否有原图，默认false
     *
     * @param optionOriginalImage true 有原图 false 没有原图
     */
    public PicSelector optionOriginalImage(boolean optionOriginalImage) {
        mConfig.optionOriginalImage(optionOriginalImage);
        return this;
    }

    /**
     * 是否可编辑，默认false
     *
     * @param editable true 有编辑 false 不可编辑
     */
    public PicSelector editable(boolean editable) {
        mConfig.editable(editable);
        return this;
    }

    /**
     * 设置返回的请求code
     *
     * @param requestCode int
     */
    public void setResult(int requestCode) {
        this.mRequestCode = requestCode;
        Activity activity = getActivity();
        if (activity == null) return;
        Intent intent = getIntent(activity);
        Fragment fragment = getFragment();
        if (fragment != null) {
            fragment.startActivityForResult(intent, mRequestCode);
        } else {
            activity.startActivityForResult(intent, mRequestCode);
        }
        activity.overridePendingTransition(R.anim.anim_activity_enter, 0);
    }

    /**
     * 获取请求code
     *
     * @return int
     */
    public int getRequestCode() {
        return mRequestCode;
    }

    /**
     * 返回选择后的结果 ArrayList<String>
     *
     * @param data Intent
     * @return ArrayList<String>
     */
    public static ArrayList<String> obtainResultFile(Intent data) {
        if (data == null) return null;
        return data.getStringArrayListExtra(Constant.PIC_INTENT_ACTIVITY_KEY);
    }

    /**
     * 返回选择后的结果 List<Uri>
     *
     * @param context Context
     * @param data    Intent
     * @return List<Uri>
     */
    public static ArrayList<Uri> obtainResultUri(Context context, Intent data) {
        if (data == null) return null;
        ArrayList<Uri> uriList = new ArrayList<>();
        ArrayList<String> list = data.getStringArrayListExtra(Constant.PIC_INTENT_ACTIVITY_KEY);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                String path = list.get(i);
                if (!TextUtils.isEmpty(path)) {
                    uriList.add(UIUtils.getUri(context, new File(path)));
                }
            }
        }
        return uriList;
    }


    /**
     * 跳转的界面
     *
     * @param activity Activity
     * @return Intent
     */
    @NonNull
    private Intent getIntent(Activity activity) {
        return new Intent(activity, PhotoSelectActivity.class);
    }

    /**
     * 获取上下文
     *
     * @return Activity
     */
    public Activity getActivity() {
        return mActivity == null ? null : mActivity.get();
    }

    /**
     * 获取上下文
     *
     * @return Fragment
     */
    public Fragment getFragment() {
        return mFragment == null ? null : mFragment.get();
    }

}

