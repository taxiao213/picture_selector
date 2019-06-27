package com.edit.picture.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.selector.picture.R;
import com.selector.picture.base.BaseActivity;
import com.edit.picture.fragment.PhotoEditFragment;
import com.selector.picture.constant.Constant;
import com.selector.picture.model.LocalMedia;
import com.selector.picture.model.PicConfig;
import com.selector.picture.view.StatusBarUtil;

/**
 * 图片剪切
 * Create by Han on 2019/6/15
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoEditActivity extends BaseActivity {
    private PhotoEditFragment pictureEditFragment;
    private LocalMedia model;

    @Override
    protected void setThem() {
        setTheme(PicConfig.getInstances().getTheme());
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            model = intent.getParcelableExtra(Constant.ACTION_TYPE1);
        }
        FragmentManager manager = mActivity.getSupportFragmentManager();
        if (manager != null) {
            FragmentTransaction transaction = manager.beginTransaction();
            if (pictureEditFragment != null) {
                if (pictureEditFragment.isAdded()) {
                    transaction.show(pictureEditFragment).commit();
                } else {
                    transaction.remove(pictureEditFragment);
                    newFragment(transaction);
                }
            } else {
                newFragment(transaction);
            }
        }
        StatusBarUtil.setStatusBar(mActivity, false);
    }

    private void newFragment(FragmentTransaction transaction) {
        pictureEditFragment = new PhotoEditFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.ACTION_TYPE1, model);
        pictureEditFragment.setArguments(bundle);
        transaction.add(R.id.fl, pictureEditFragment).commit();
    }

    @Override
    protected void initData() {

    }
}
