package com.selector.picture.model;

import android.app.Activity;

import com.selector.picture.constant.Constant;

import java.util.IllegalFormatCodePointException;

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
    public final static int TYPE_ALL = 0;
    public final static int TYPE_IMAGE = 1;
    public final static int TYPE_VIDEO = 2;
    public final static int TYPE_AUDIO = 3;
    public int MIME_TYPE = TYPE_ALL;

    private PicConfig() {
        this.mTheme = Constant.PIC_DEFAULT_THEME;
        this.mMinSelectNum = Constant.PIC_MIN_SELECT_NUM;
        this.mMaxSelectNum = Constant.PIC_MAX_SELECT_NUM;
        this.mGridSize = Constant.PIC_GRID_SIZE_NUM;
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

    public static PicConfig getmPicConfig() {
        return mPicConfig;
    }

    public static void setmPicConfig(PicConfig mPicConfig) {
        PicConfig.mPicConfig = mPicConfig;
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

    public void imageType(int type) {
        this.MIME_TYPE = type;
    }

    public static int isPictureType(String pictureType) {
        switch (pictureType) {
            case "image/png":
            case "image/PNG":
            case "image/jpeg":
            case "image/JPEG":
            case "image/webp":
            case "image/WEBP":
            case "image/gif":
            case "image/bmp":
            case "image/GIF":
            case "imagex-ms-bmp":
                return PicConfig.TYPE_IMAGE;
            case "video/3gp":
            case "video/3gpp":
            case "video/3gpp2":
            case "video/avi":
            case "video/mp4":
            case "video/quicktime":
            case "video/x-msvideo":
            case "video/x-matroska":
            case "video/mpeg":
            case "video/webm":
            case "video/mp2ts":
                return PicConfig.TYPE_VIDEO;
            case "audio/mpeg":
            case "audio/x-ms-wma":
            case "audio/x-wav":
            case "audio/amr":
            case "audio/wav":
            case "audio/aac":
            case "audio/mp4":
            case "audio/quicktime":
            case "audio/lamr":
            case "audio/3gpp":
                return PicConfig.TYPE_AUDIO;
        }
        return PicConfig.TYPE_IMAGE;
    }
}
