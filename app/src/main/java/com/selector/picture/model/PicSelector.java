package com.selector.picture.model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.selector.picture.activity.PhotoSelectActivity;
import com.selector.picture.constant.Constant;

import java.io.Serializable;
import java.lang.ref.WeakReference;

/**
 * 相册选择实体
 * Create by Han on 2019/5/17
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PicSelector implements Parcelable {

    private WeakReference<Activity> mActivity;
    private WeakReference<Fragment> mFragment;
    private int mTheme;//相册选择主题
    private int mMinSelectNum;//设置图片可选择最小数量 默认最小1个
    private int mMaxSelectNum;//设置图片可选择最大数量 默认最大9个
    private int mGridSize;//设置图片网格数量 默认3列
    private int mRequestCode;//设置返回的请求code

    private PicSelector(Activity activity, Fragment fragment) {
        this.mTheme = Constant.PIC_DEFAULT_THEME;
        this.mMinSelectNum = Constant.PIC_MIN_SELECT_NUM;
        this.mMaxSelectNum = Constant.PIC_MAX_SELECT_NUM;
        this.mGridSize = Constant.PIC_GRID_SIZE_NUM;
        mActivity = new WeakReference(activity);
        mFragment = new WeakReference(fragment);
    }

    private PicSelector(Fragment fragment) {
        this(fragment.getActivity(), fragment);
    }

    private PicSelector(Activity activity) {
        this(activity, null);
    }

    private PicSelector(Parcel in) {
        mTheme = in.readInt();
        mMinSelectNum = in.readInt();
        mMaxSelectNum = in.readInt();
        mGridSize = in.readInt();
        mRequestCode = in.readInt();
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
        this.mTheme = theme;
        return this;
    }

    /**
     * 获取主题
     */
    public int getTheme() {
        return mTheme;
    }

    /**
     * 设置图片可选择最小数量，默认最小1个 最大9个
     *
     * @param num int
     * @return PicSelector
     */
    public PicSelector minSelectNum(int num) {
        this.mMinSelectNum = num;
        return this;
    }

    /**
     * 获取图片可选择最小数量，默认最小1个 最大9个
     */
    public int getMinSelectNum() {
        return mMinSelectNum;
    }

    /**
     * 设置图片可选择最大数量，默认最小1个 最大9个
     *
     * @param num int
     * @return PicSelector
     */
    public PicSelector maxSelectNum(int num) {
        this.mMaxSelectNum = num;
        return this;
    }

    /**
     * 获取图片可选择最大数量，默认最小1个 最大9个
     */
    public int getMaxSelectNum() {
        return mMaxSelectNum;
    }

    /**
     * 设置图片网格数量 默认3列
     *
     * @param num int
     * @return PicSelector
     */
    public PicSelector gridSize(int num) {
        this.mGridSize = num;
        return this;
    }

    /**
     * 获取图片网格数量 默认3列
     */
    public int getGridSize() {
        return mGridSize;
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
     * 跳转的界面
     *
     * @param activity Activity
     * @return Intent
     */
    @NonNull
    private Intent getIntent(Activity activity) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.PIC_INTENT_BUNDLE_KEY, PicSelector.this);
        Intent intent = new Intent(activity, PhotoSelectActivity.class);
        intent.putExtra(Constant.PIC_INTENT_ACTIVITY_KEY, bundle);
        return intent;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mTheme);
        dest.writeInt(mMinSelectNum);
        dest.writeInt(mMaxSelectNum);
        dest.writeInt(mGridSize);
        dest.writeInt(mRequestCode);
    }

    public static final Creator<PicSelector> CREATOR = new Creator<PicSelector>() {
        @Override
        public PicSelector createFromParcel(Parcel in) {
            return new PicSelector(in);
        }

        @Override
        public PicSelector[] newArray(int size) {
            return new PicSelector[size];
        }
    };
}

