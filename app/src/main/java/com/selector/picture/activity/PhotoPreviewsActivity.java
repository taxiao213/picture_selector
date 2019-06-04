package com.selector.picture.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.selector.picture.R;
import com.selector.picture.base.BaseActivity;
import com.selector.picture.constant.Constant;
import com.selector.picture.fragment.PhotoPreviewsFragment;
import com.selector.picture.fragment.PhotoSelectFragment;
import com.selector.picture.model.LocalMedia;
import com.selector.picture.model.PicConfig;

import java.util.ArrayList;

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
        setTheme(PicConfig.getInstances().getTheme());
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        FragmentManager manager = getSupportFragmentManager();
        if (manager != null) {
            FragmentTransaction transaction = manager.beginTransaction();
            previewsFragment = new PhotoPreviewsFragment();
            transaction.add(R.id.fl, previewsFragment, Constant.FRAGMENT_TAG2).commit();
        }
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            LocalMedia currentMedia = intent.getParcelableExtra(Constant.ACTION_TYPE1);
            if (previewsFragment != null) {
                previewsFragment.setData(currentMedia);
            }
        }
    }
}
