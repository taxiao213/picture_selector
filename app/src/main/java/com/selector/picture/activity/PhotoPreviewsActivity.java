package com.selector.picture.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.selector.picture.R;
import com.selector.picture.base.BaseActivity;
import com.selector.picture.constant.Constant;
import com.selector.picture.fragment.PhotoPreviewsFragment;
import com.selector.picture.fragment.PhotoSelectFragment;

/**
 * 图片预览
 * Create by Han on 2019/5/27
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoPreviewsActivity extends BaseActivity {

    private PhotoPreviewsFragment previewsFragment;

    @Override
    protected void setThem() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        FragmentManager manager = getSupportFragmentManager();
        if (manager != null) {
            FragmentTransaction transaction = manager.beginTransaction();
            previewsFragment = new PhotoPreviewsFragment();
            transaction.add(R.id.fl, previewsFragment).commit();
        }
    }

    @Override
    protected void initData() {

    }
}
