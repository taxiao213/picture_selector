package com.selector.picture.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.selector.picture.R;
import com.selector.picture.base.BaseActivity;
import com.selector.picture.constant.Constant;
import com.selector.picture.fragment.PictureSelectorFragment;
import com.selector.picture.model.PicSelector;

/**
 * 相册选择
 */
public class PhotoSelectActivity extends BaseActivity {

    private PictureSelectorFragment pictureSelectorFragment;

    @Override
    protected void initView(Bundle savedInstanceState) {
        FragmentManager manager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            if (manager != null) {
                FragmentTransaction transaction = manager.beginTransaction();
                pictureSelectorFragment = new PictureSelectorFragment();
                transaction.add(R.id.fl, pictureSelectorFragment, Constant.FRAGMENT_TAG).show(pictureSelectorFragment).commit();
            }
        } else {
            if (manager != null) {
                pictureSelectorFragment = (PictureSelectorFragment) manager.findFragmentByTag(Constant.FRAGMENT_TAG);
            }
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }
}
