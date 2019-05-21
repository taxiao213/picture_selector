package com.selector.picture.model;

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
    private boolean mIsGif;//是否选择动图，默认没有动图

    private PicConfig() {
        this.mTheme = Constant.PIC_DEFAULT_THEME;
        this.mMinSelectNum = Constant.PIC_MIN_SELECT_NUM;
        this.mMaxSelectNum = Constant.PIC_MAX_SELECT_NUM;
        this.mGridSize = Constant.PIC_GRID_SIZE_NUM;
        this.mimeType = Constant.PIC_CHOOSE_MIMETYPE;
        this.mIsGif = Constant.PIC_CHOOSE_IS_GIF;
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

}
