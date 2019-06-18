package com.selector.picture.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.selector.picture.R;
import com.selector.picture.activity.PhotoEditActivity;
import com.selector.picture.base.BaseFragment;
import com.selector.picture.constant.Constant;
import com.selector.picture.model.LocalMedia;
import com.selector.picture.model.MimeType;
import com.selector.picture.utils.PicUtils;
import com.selector.picture.utils.StringUtils;
import com.selector.picture.utils.UIUtils;
import com.selector.picture.view.photoview.PhotoView;

/**
 * 图片剪切
 * Create by Han on 2019/5/27
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoEditFragment extends BaseFragment implements View.OnClickListener {

    private PhotoEditActivity activity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (PhotoEditActivity) context;
    }

    @Override
    protected int initView() {
        return R.layout.fragment_photo_edit;
    }

    @Override
    protected void initData() {
        View view = getView();
        if (view == null) return;


    }


    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.iv_photo:

                    break;
                case R.id.rl_play:

                    break;
            }
        }
    }
}
