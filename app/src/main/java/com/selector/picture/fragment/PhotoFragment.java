package com.selector.picture.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.selector.picture.R;
import com.selector.picture.base.BaseFragment;
import com.selector.picture.constant.Constant;
import com.selector.picture.model.LocalMedia;
import com.selector.picture.model.MimeType;
import com.selector.picture.model.PicConfig;
import com.selector.picture.utils.OnItemClickListener;
import com.selector.picture.utils.PicUtils;
import com.selector.picture.utils.StringUtils;
import com.selector.picture.utils.UIUtils;
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
    private PhotoView photoView;
    private RelativeLayout rlPlay;
    private ImageView ivPlay;
    private PhotoPreviewsFragment fragment;
    private LocalMedia media;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (FragmentActivity) context;
    }

    @Override
    protected int initView() {
        return R.layout.fragment_photo;
    }

    @Override
    protected void initData() {
        View view = getView();
        if (view == null) return;
        photoView = view.findViewById(R.id.iv_photo);
        rlPlay = view.findViewById(R.id.rl_play);
        ivPlay = view.findViewById(R.id.iv_play);
        Bundle bundle = getArguments();
        if (bundle != null) {
            media = bundle.getParcelable(Constant.ACTION_TYPE1);
            if (media != null) {
                String mimeType = StringUtils.nullToString(media.getPictureType());
                int pictureType = MimeType.isPictureType(mimeType);
                String path = media.getPath();
                if (pictureType == MimeType.TYPE_IMAGE) {
                    rlPlay.setVisibility(View.GONE);
                    photoView.setVisibility(View.VISIBLE);
                    PicUtils.getInstances().loadPreviewPhoto(activity, photoView, path);
                } else {
                    rlPlay.setVisibility(View.VISIBLE);
                    photoView.setVisibility(View.GONE);
                    PicUtils.getInstances().loadPreviewImage(activity, ivPlay, path);
                }
            }
        }
        photoView.setOnClickListener(this);
        rlPlay.setOnClickListener(this);
        fragment = (PhotoPreviewsFragment) getParentFragment();
    }

    public static PhotoFragment newInstances(LocalMedia media) {
        PhotoFragment photoFragment = new PhotoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.ACTION_TYPE1, media);
        photoFragment.setArguments(bundle);
        return photoFragment;
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.iv_photo:
                    if (fragment != null) {
                        fragment.hideView();
                    }
                    break;
                case R.id.rl_play:
                    if (media != null) {
                        UIUtils.openFile(activity, StringUtils.nullToString(media.getPath()));
                    }
                    break;
            }
        }
    }
}
