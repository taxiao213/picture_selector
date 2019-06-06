package com.selector.picture.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.selector.picture.R;
import com.selector.picture.activity.PhotoPreviewsActivity;
import com.selector.picture.adapter.PhotoPreviewFragmentAdapter;
import com.selector.picture.base.BaseFragment;
import com.selector.picture.constant.Constant;
import com.selector.picture.model.LocalMedia;
import com.selector.picture.model.LocalMediaFolder;
import com.selector.picture.model.PicConfig;
import com.selector.picture.model.PicSelector;
import com.selector.picture.utils.OnItemClickListener;
import com.selector.picture.view.DialogUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 图片预览
 * Create by Han on 2019/5/27
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoPreviewsFragment extends BaseFragment implements View.OnClickListener {

    private FragmentActivity activity;
    private TextView tvBottomLeftTextPreviews;
    private TextView tvBottomCenterTextPreviews;
    private TextView tvBottomSelectTextPreviews;

    private List<LocalMedia> sendMedia;//发送和预览的集合
    private TextView tvTopSendText;
    private ViewPager vp;
    private PhotoPreviewFragmentAdapter adapter;
    private ArrayList<LocalMedia> list;


    @Override
    protected int initView() {
        return R.layout.fragment_photo_previews;
    }

    @Override
    protected void initData() {
        activity = getActivity();
        View view = getView();
        RelativeLayout rlTopRoot = view.findViewById(R.id.rl_top_root);//顶部根布局
        ImageView ivTopLeftBack = view.findViewById(R.id.iv_top_left_back);//顶部左侧后退按钮
        TextView tvTopLeftText = view.findViewById(R.id.tv_top_lef_text);//顶部左侧标题
        tvTopSendText = view.findViewById(R.id.tv_top_send_text); //顶部右侧发送按钮

        LinearLayout llBottomRootPreviews = view.findViewById(R.id.ll_bottom_root_previews); //底部根布局
        RecyclerView ryPreviews = view.findViewById(R.id.ry_previews);//recyclerview

        tvBottomLeftTextPreviews = view.findViewById(R.id.tv_bottom_lef_text_previews);//底部左侧编辑
        tvBottomCenterTextPreviews = view.findViewById(R.id.tv_bottom_center_text_previews);//底部中间原图标题
        tvBottomSelectTextPreviews = view.findViewById(R.id.tv_bottom_select_text_previews);//底部右侧选择按钮
        tvTopSendText.setOnClickListener(this);
        ivTopLeftBack.setOnClickListener(this);
        tvBottomCenterTextPreviews.setOnClickListener(this);
        tvBottomSelectTextPreviews.setOnClickListener(this);
        vp = view.findViewById(R.id.vp);  //viewpager
        list = new ArrayList<>();
        adapter = new PhotoPreviewFragmentAdapter(getChildFragmentManager(), list);
        vp.setAdapter(adapter);
        vp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                LocalMedia media = list.get(position);
                if (media != null) {
                    tvBottomSelectTextPreviews.setSelected(media.isChecked());
                } else {
                    tvBottomSelectTextPreviews.setSelected(false);
                }
            }
        });
        Bundle bundle = getArguments();
        if (bundle != null) {
            LocalMedia loaclMedia = bundle.getParcelable(Constant.ACTION_TYPE1);
            if (loaclMedia != null) {
                setData(loaclMedia);
            }
        } else {
            setData(null);
        }
        sendMedia = PicConfig.getInstances().getSendList();
        setText();
        initBottomCenterText();
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.iv_top_left_back:
                    if (activity != null) {
                        activity.finish();
                    }
                    break;
                case R.id.tv_top_send_text:
                    //顶部右侧发送按钮
                    if (activity != null) {
                        if (sendMedia != null && sendMedia.size() > 0) {
                            ((PhotoPreviewsActivity) activity).setResult();
                        }
                    }
                    break;
                case R.id.tv_bottom_lef_text_previews:
                    //编辑

                    break;
                case R.id.tv_bottom_center_text_previews:
                    //是否选择原图
                    PicConfig.getInstances().setLoadOriginalImage(!PicConfig.getInstances().isLoadOriginalImage());
                    initBottomCenterText();
                    break;
                case R.id.tv_bottom_select_text_previews:
                    //底部右侧选择
//                    tvBottomSelectTextPreviews.setSelected(true);
                    break;

            }
        }
    }

    /**
     * 设置预览的图片
     *
     * @param currentMedia LocalMedia 选择预览跳转 为null 选择图片跳转不为null
     */
    public void setData(LocalMedia currentMedia) {
        if (activity != null) {
            List<LocalMedia> listLocalMedia;
            if (currentMedia != null) {
                listLocalMedia = PicConfig.getInstances().getCurrentList();
            } else {
                listLocalMedia = PicConfig.getInstances().getSendList();
            }

            if (list == null) {
                list = new ArrayList<>();
            }
            list.clear();
            if (listLocalMedia != null) {
                list.addAll(listLocalMedia);
            }
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
            int position = 0;
            if (currentMedia != null) {
                if (listLocalMedia != null && listLocalMedia.size() > 0) {
                    for (int i = 0; i < listLocalMedia.size(); i++) {
                        LocalMedia media = listLocalMedia.get(i);
                        if (media != null) {
                            if (TextUtils.equals(media.getId(), currentMedia.getId())) {
                                position = i;
                                break;
                            }
                        }
                    }
                }
            }
            if (vp != null) {
                vp.setCurrentItem(position);
            }
        }
    }

    /**
     * 设置发送和预览按钮状态
     */
    private void setText() {
        if (sendMedia != null && sendMedia.size() > 0) {
            setSelected(true);
            tvTopSendText.setText(getString(R.string.picture_selector_top_send_text_select, String.valueOf(sendMedia.size()), String.valueOf(PicConfig.getInstances().getMaxSelectNum())));
//            tvBottomPreviewText.setText(getString(R.string.picture_selector_bottom_preview_text, String.valueOf(sendMedia.size())));
        } else {
            setSelected(false);
            tvTopSendText.setText(R.string.picture_selector_top_send_text_default);
//            tvBottomPreviewText.setText(R.string.picture_selector_bottom_preview_default);
        }
    }

    /**
     * 设置view 选中
     *
     * @param isSelected true选中  false未选中
     */
    public void setSelected(boolean isSelected) {
        tvTopSendText.setSelected(isSelected);
//        tvBottomPreviewText.setSelected(isSelected);
    }

    /**
     * 设置原图  默认false
     */
    private void initBottomCenterText() {
        tvBottomCenterTextPreviews.setSelected(PicConfig.getInstances().isLoadOriginalImage());
    }
}
