package com.selector.picture.model;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;

/**
 * Create by Han on 2019/5/20
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class LocalMediaLoader {

    private static LocalMediaLoader mLocalMediaLoader;

    private LocalMediaLoader() {

    }

    public static LocalMediaLoader getInstances() {
        if (mLocalMediaLoader == null) {
            synchronized (LocalMediaLoader.class) {
                if (mLocalMediaLoader == null) {
                    mLocalMediaLoader = new LocalMediaLoader();
                }
            }
        }
        return mLocalMediaLoader;
    }

    public void loadMedia(FragmentActivity context) {
        LoaderManager manager = context.getSupportLoaderManager();
        if (manager != null) {
//            manager.initLoader()
        }
    }
}
