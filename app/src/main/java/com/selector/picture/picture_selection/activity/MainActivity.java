package com.selector.picture.picture_selection.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.selector.picture.picture_selection.R;
import com.selector.picture.picture_selection.base.BaseActivity;
import com.selector.picture.picture_selection.fragment.PictureSelectorFragment;

public class MainActivity extends BaseActivity {

    private PictureSelectorFragment pictureSelectorFragment;

    @Override
    protected void initView() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager != null) {
            FragmentTransaction transaction = manager.beginTransaction();
            pictureSelectorFragment = new PictureSelectorFragment();
            transaction.replace(R.id.fl, pictureSelectorFragment);
        }
    }

    @Override
    protected void initData() {

    }


}
