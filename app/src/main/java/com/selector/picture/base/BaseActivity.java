package com.selector.picture.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.selector.picture.R;

/**
 * Create by Han on 2019/5/10
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public abstract class BaseActivity extends AppCompatActivity {


    protected abstract void initView();//初始化view

    protected abstract void initData();//初始化数据

    protected BaseActivity mActivity;//初始化数据

    protected ProgressDialog dialog;//加载框

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.pictrue_white_Theme);
        super.onCreate(savedInstanceState);
        mActivity = BaseActivity.this;
        setContentView(R.layout.activity_base);
        initView();
        initData();
        dialog = new ProgressDialog(mActivity);
    }

    /**
     * 打开加载框
     */
    protected void loadingProgressDialog() {
        if (dialog != null && mActivity != null && !mActivity.isDestroyed()) {
            if (!dialog.isShowing()) {
                dialog.show();
            }
        }
    }

    /**
     * 关闭加载框
     */
    protected void cancelProgressDialog() {
        if (dialog != null && mActivity != null && !mActivity.isDestroyed()) {
            if (dialog.isShowing()) {
                dialog.cancel();
            }
        }
    }

}
