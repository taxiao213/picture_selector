package com.selector.picture.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Create by Han on 2019/5/10
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public abstract class BaseFragment extends Fragment {

    private BaseActivity mActivity;
    private View view;

    protected abstract int initView();//初始化view

    protected abstract void initData();//初始化数据

    protected ProgressDialog dialog;//加载框

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(initView(), container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    public View getView() {
        return view;
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
