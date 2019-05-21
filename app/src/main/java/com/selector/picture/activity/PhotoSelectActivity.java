package com.selector.picture.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.selector.picture.utils.ImageLoadListener;
import com.selector.picture.R;
import com.selector.picture.base.BaseActivity;
import com.selector.picture.constant.Constant;
import com.selector.picture.fragment.PictureSelectorFragment;
import com.selector.picture.model.LocalMediaFolder;
import com.selector.picture.model.LocalMediaLoader;
import com.selector.picture.model.PicConfig;

import java.util.List;

/**
 * 相册选择
 */
public class PhotoSelectActivity extends BaseActivity implements ImageLoadListener<List<LocalMediaFolder>> {

    private PictureSelectorFragment pictureSelectorFragment;
    private String[] permissionArray = new String[]{Manifest.permission_group.STORAGE};
    private int permissionRequestCode = 100;

    @Override
    protected void setThem() {
        setTheme(PicConfig.getInstances().getTheme());
    }

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
}
