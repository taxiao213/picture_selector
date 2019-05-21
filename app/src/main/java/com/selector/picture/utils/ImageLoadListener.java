package com.selector.picture.utils;

/**
 * 加载图片回调
 * Create by Han on 2019/5/21
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public interface ImageLoadListener<T> {
    void loadComplete(T t);
}
