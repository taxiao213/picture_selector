package com.selector.picture.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
    private String[] permissionArray = new String[]{android.Manifest.permission_group.STORAGE};
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
                pictureSelectorFragment = new PhotoSelectFragment();
                transaction.add(R.id.fl, pictureSelectorFragment, Constant.FRAGMENT_TAG1).show(pictureSelectorFragment).commit();
            }
        } else {
            if (manager != null) {
                pictureSelectorFragment = (PhotoSelectFragment) manager.findFragmentByTag(Constant.FRAGMENT_TAG1);
            }
        }
    }

    @Override
    protected void initData() {
        initPhotoData();
//        checkPermission();
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

    public void startActivity(){
        Intent intent = new Intent(mActivity, PhotoPreviewsActivity.class);
        intent.putParcelableArrayListExtra(Constant.ACTION_TYPE1, new ArrayList<LocalMedia>());
        mActivity.startActivityForResult(intent, Constant.TYPE1);
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
        }
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
                        //跳转到允许安装未知来源设置页面
//                        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + getPackageName()));
//                        startActivityForResult(intent, requestCode);
                    } else {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent, requestCode);
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
        Log.e("----", "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("----", "onRestoreInstanceState");
    }
}
