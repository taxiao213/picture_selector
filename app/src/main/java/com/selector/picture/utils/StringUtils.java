package com.selector.picture.utils;

import android.text.TextUtils;

/**
 * Create by Han on 2019/5/22
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class StringUtils {

    public static String nullToString(String string) {
        if (TextUtils.isEmpty(string)) {
            string = "";
        }
        return string;
    }
}
