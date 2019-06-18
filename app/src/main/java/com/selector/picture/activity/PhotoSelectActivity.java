package com.selector.picture.activity;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import com.selector.picture.model.LocalMedia;
import com.selector.picture.utils.ImageLoadListener;
import com.selector.picture.R;
import com.selector.picture.base.BaseActivity;
import com.selector.picture.constant.Constant;
import com.selector.picture.fragment.PhotoSelectFragment;
import com.selector.picture.model.LocalMediaFolder;
import com.selector.picture.model.LocalMediaLoader;
import com.selector.picture.model.PicConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 相册选择
 */
public class PhotoSelectActivity extends BaseActivity implements ImageLoadListener<List<LocalMediaFolder>> {

    private PhotoSelectFragment pictureSelectorFragment;

    @Override
    protected void setThem() {
        setTheme(PicConfig.getInstances().getTheme());
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            PicConfig config = savedInstanceState.getParcelable(Constant.ACTION_TYPE1);
            if (config != null) {
                PicConfig.getInstances().setConfig(config);
            }
        }
        FragmentManager manager = mActivity.getSupportFragmentManager();
        if (manager != null) {
            FragmentTransaction transaction = manager.beginTransaction();
            if (pictureSelectorFragment != null) {
                if (pictureSelectorFragment.isAdded()) {
                    transaction.show(pictureSelectorFragment).commit();
                } else {
                    transaction.remove(pictureSelectorFragment);
                    newFragment(transaction);
                }
            } else {
                newFragment(transaction);
            }
        }
    }

    private void newFragment(FragmentTransaction transaction) {
        pictureSelectorFragment = new PhotoSelectFragment();
        transaction.add(R.id.fl, pictureSelectorFragment).commit();
    }

    @Override
    protected void initData() {
        initPhotoData();
    }

    /**
     * 加载相册数据
     */
    private void initPhotoData() {
        LocalMediaLoader loader = LocalMediaLoader.getInstances();
        loader.loadMedia(PhotoSelectActivity.this, this);
    }

    @Override
    public void loadComplete(List<LocalMediaFolder> localMediaFolders) {
        if (pictureSelectorFragment != null) {
            pictureSelectorFragment.setList(localMediaFolders);
        }
    }

    public void setResult(Intent intent) {
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constant.TYPE1:
                    if (pictureSelectorFragment != null) {
                        pictureSelectorFragment.setResult();
                    }
                    break;
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            if (pictureSelectorFragment != null) {
                pictureSelectorFragment.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PicConfig config = PicConfig.getInstances();
        if (outState != null) {
            outState.putParcelable(Constant.ACTION_TYPE1, config);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PicConfig.getInstances().restoreConfig();
    }
}
