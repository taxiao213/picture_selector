package com.selector.picture.constant;

import com.selector.picture.R;
import com.selector.picture.model.MimeType;

/**
 * 常量类
 * Create by Han on 2019/5/18
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class Constant {
    public static String FRAGMENT_TAG = "fragment_tag";//Fragment tag

    public static int PIC_DEFAULT_THEME = R.style.pictrue_white_Theme;//相册选择主题
    public static int PIC_MIN_SELECT_NUM = 1;//设置图片可选择最小数量 默认最小1个
    public static int PIC_MAX_SELECT_NUM = 9;//设置图片可选择最大数量 默认最大9个
    public static int PIC_GRID_SIZE_NUM = 3;//设置图片网格数量 默认3个
    public static int PIC_CHOOSE_MIMETYPE = MimeType.TYPE_ALL;//设置图片选择格式 默认全部
    public static float PIC_CHOOSE_MULTIPLIER = 0.8F;//设置Glide加载资源压缩系数(0.0F,1.0F)，默认0.8F
    public static float PIC_GRID_SPACE = 2.5F;//设置网格的间距
    public static boolean PIC_CHOOSE_IS_GIF = true;//设置是否选择动图 默认true
    public static boolean PIC_LOAD_ANIMATION = false;//设置是否加载动画，默认false
    public static boolean PIC_LOAD_ORIGINAL_IMAGE = false;//设置是否加载原图，默认false

    public static String PIC_INTENT_BUNDLE_KEY = "pic_intent_bundle_key";//bundle 意图
    public static String PIC_INTENT_ACTIVITY_KEY = "pic_intent_activity_key";//intent 意图
}