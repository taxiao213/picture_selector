package com.selector.picture.fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.selector.picture.R;
import com.selector.picture.base.BaseFragment;
import com.selector.picture.model.LocalMedia;
import com.selector.picture.model.PicConfig;
import com.selector.picture.utils.PicUtils;
import com.selector.picture.view.photoview.PhotoView;

import java.util.ArrayList;

/**
 * 加载图片
 * Create by Han on 2019/5/27
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoFragment extends BaseFragment implements View.OnClickListener {

    private FragmentActivity activity;
    private ArrayList<LocalMedia> sendMedia;//发送和预览的集合
    private static PhotoFragment photoFragment;
    private PhotoView photoView;

    @Override
    protected int initView() {
        return R.layout.fragment_photo;
    }

    @Override
    protected void initData() {
        View view = getView();
        photoView = view.findViewById(R.id.iv_photo);

    }

    @Override
    public void onClick(View v) {

    }

    public static PhotoFragment newInstances(String path) {
        if (photoFragment == null) {
            photoFragment = new PhotoFragment();
        }
        photoFragment.setData(path);
        return photoFragment;
    }

    private void setData(String path) {
        PicUtils.getInstances().loadPreviewPhoto(getActivity(), photoView, path);
    }
}
