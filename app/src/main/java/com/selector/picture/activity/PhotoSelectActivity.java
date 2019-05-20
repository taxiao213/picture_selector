package com.selector.picture.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.selector.picture.R;
import com.selector.picture.base.BaseActivity;
import com.selector.picture.constant.Constant;
import com.selector.picture.fragment.PictureSelectorFragment;
import com.selector.picture.model.LocalMediaLoader;
import com.selector.picture.model.PicConfig;

/**
 * 相册选择
 */
public class PhotoSelectActivity extends BaseActivity {

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
        checkPermission();
    }

    /**
     * 加载相册数据
     */
    private void initPhotoData() {
        LocalMediaLoader loader = LocalMediaLoader.getInstances();
        loader.loadMedia(PhotoSelectActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == permissionRequestCode) {
            if (grantResults.length > 0) {
                int grantResult = grantResults[0];
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    initPhotoData();
                } else {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {

                    } else {

                    }
                }
            }
        }
    }

    public void checkPermission() {
        if (permissionArray != null && permissionArray.length > 0) {
            for (String per : permissionArray) {
                if (!TextUtils.isEmpty(per)) {
                    int permission = ContextCompat.checkSelfPermission(PhotoSelectActivity.this, per);
                    if (permission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(PhotoSelectActivity.this, permissionArray, permissionRequestCode);
                    }
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
