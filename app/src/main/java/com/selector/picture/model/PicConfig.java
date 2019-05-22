package com.selector.picture.model;

import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;

import com.selector.picture.constant.Constant;

/**
 * 相册选择配置
 * Create by Han on 2019/5/20
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PicConfig {
    private static PicConfig mPicConfig;
    private int mTheme;//相册选择主题
    private int mMinSelectNum;//设置图片可选择最小数量 默认最小1个
    private int mMaxSelectNum;//设置图片可选择最大数量 默认最大9个
    private int mGridSize;//设置图片网格数量 默认3列
    private int mimeType;//图片选择的类型
    private boolean mIsGif;//设置是否选择动图 默认true
    private int overrideWidth;//压缩宽 默认不压缩
    private int overrideHeight;//压缩高 默认不压缩
    private float sizeMultiplier;//设置Glide加载资源压缩系数(0.0F,1.0F)，默认0.8F
    private boolean loadAnimation;//设置是否加载动画，默认false
    private boolean loadOriginalImage;//是否选择原图，默认false

    private PicConfig() {
        this.mTheme = Constant.PIC_DEFAULT_THEME;
        this.mMinSelectNum = Constant.PIC_MIN_SELECT_NUM;
        this.mMaxSelectNum = Constant.PIC_MAX_SELECT_NUM;
        this.mGridSize = Constant.PIC_GRID_SIZE_NUM;
        this.mimeType = Constant.PIC_CHOOSE_MIMETYPE;
        this.mIsGif = Constant.PIC_CHOOSE_IS_GIF;
        this.sizeMultiplier = Constant.PIC_CHOOSE_MULTIPLIER;
        this.overrideWidth = 0;
        this.overrideHeight = 0;
        this.loadAnimation = Constant.PIC_LOAD_ANIMATION;
        this.loadOriginalImage = Constant.PIC_LOAD_ORIGINAL_IMAGE;
    }

    public static PicConfig getInstances() {
        if (mPicConfig == null) {
            synchronized (PicConfig.class) {
                if (mPicConfig == null) {
                    mPicConfig = new PicConfig();
                }
            }
        }
        return mPicConfig;
    }

    /**
     * 设置主题
     *
     * @param theme R.style.pictrue_white_Theme 默认主题
     * @return PicSelector
     */
    public PicConfig theme(int theme) {
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
    public PicConfig minSelectNum(int num) {
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
    public PicConfig maxSelectNum(int num) {
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
    public PicConfig gridSize(int num) {
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
     * 设置选区图片的格式
     *
     * @param type int
     */
    public void imageType(int type) {
        this.mimeType = type;
    }

    /**
     * 获取图片格式
     *
     * @return int
     */
    public int getImageType() {
        return mimeType;
    }

    /**
     * 设置动图
     */
    public void gif(boolean isGif) {
        this.mIsGif = isGif;
    }

    /**
     * 是否选择动图
     *
     * @return int
     */
    public boolean isGif() {
        return mIsGif;
    }

    /**
     * 设置加载图片的宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
     *
     * @param overrideWidth  压缩宽
     * @param overrideHeight 压缩高
     */
    public void overrideWidth(@IntRange(from = 100) int overrideWidth, @IntRange(from = 100) int overrideHeight) {
        this.overrideWidth = overrideWidth;
        this.overrideHeight = overrideHeight;
    }


    /**
     * 获取压缩宽度
     *
     * @return int
     */
    public int getOverrideWidth() {
        return overrideWidth;
    }

    /**
     * 获取压缩高度
     *
     * @return int
     */
    public int getOverrideHeight() {
        return overrideHeight;
    }

    /**
     * Glide压缩资源系数
     *
     * @param sizeMultiplier Applies a multiplier to the {@link com.bumptech.glide.request.target.Target}'s size before
     *                       loading the resource. Useful for loading thumbnails or trying to avoid loading huge resources
     */
    public void sizeMultiplier(@FloatRange(from = 0, to = 1) float sizeMultiplier) {
        this.sizeMultiplier = sizeMultiplier;
    }

    /**
     * Glide压缩资源系数
     *
     * @return float
     */
    public float getMultiplier() {
        return sizeMultiplier;
    }

    /**
     * 是否加载动画
     *
     * @param loadAnimation true 加载动画 false 不加载动画
     */
    public void loadAnimation(boolean loadAnimation) {
        this.loadAnimation = loadAnimation;
    }

    /**
     * 是否加载动画
     *
     * @return true 加载 false不加载
     */
    public boolean isAnimation() {
        return loadAnimation;
    }

    /**
     * 是否加载原图
     *
     * @return true 加载 false不加载
     */
    public boolean isLoadOriginalImage() {
        return loadOriginalImage;
    }

    /**
     * 是否加载原图
     *
     * @param loadOriginalImage true 加载 false不加载
     */
    public void setLoadOriginalImage(boolean loadOriginalImage) {
        this.loadOriginalImage = loadOriginalImage;
    }
}
