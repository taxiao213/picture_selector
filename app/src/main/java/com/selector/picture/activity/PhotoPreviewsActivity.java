package com.selector.picture.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;

import com.selector.picture.R;
import com.selector.picture.base.BaseActivity;
import com.selector.picture.constant.Constant;
import com.selector.picture.fragment.PhotoPreviewsFragment;
import com.selector.picture.model.LocalMedia;
import com.selector.picture.model.PicConfig;
import com.selector.picture.view.StatusBarUtil;

/**
 * 图片预览
 * Create by Han on 2019/5/27
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoPreviewsActivity extends BaseActivity {

    private PhotoPreviewsFragment previewsFragment;
    private LocalMedia currentMedia;

    @Override
    protected void setThem() {
        setTheme(PicConfig.getInstances().getTheme());
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        FragmentManager manager = mActivity.getSupportFragmentManager();
        Intent intent = getIntent();
        if (intent != null) {
            currentMedia = intent.getParcelableExtra(Constant.ACTION_TYPE1);
        }
        if (manager != null) {
            previewsFragment = (PhotoPreviewsFragment) manager.findFragmentByTag(Constant.FRAGMENT_TAG2);
            if (previewsFragment == null) {
                previewsFragment = new PhotoPreviewsFragment();
                if (currentMedia != null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constant.ACTION_TYPE1, currentMedia);
                    previewsFragment.setArguments(bundle);
                }
            } else {
                if (currentMedia != null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constant.ACTION_TYPE1, currentMedia);
                    previewsFragment.setArguments(bundle);
                } else {
                    previewsFragment.setData(null, Constant.TYPE2);
                }
            }
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fl, previewsFragment, Constant.FRAGMENT_TAG2).commit();
        }
        immersiveShow();
    }

    /**
     * 状态栏显示
     */
    public void immersiveShow() {
        StatusBarUtil.setStatusBar(mActivity, true);
        StatusBarUtil.immersive(mActivity, ContextCompat.getColor(mActivity, R.color.white_trans), 1.0f);
    }

    /**
     * 状态栏隐藏
     */
    public void immersiveHide() {
        StatusBarUtil.setStatusBar(mActivity, false);
    }

    @Override
    protected void initData() {

    }

    public void setResult() {
        setResult(RESULT_OK);
        finish();
    }
}
