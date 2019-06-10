package com.selector.picture.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.selector.picture.R;
import com.selector.picture.activity.PhotoPreviewsActivity;
import com.selector.picture.adapter.PhotoPreviewAdapter;
import com.selector.picture.adapter.PhotoPreviewFragmentAdapter;
import com.selector.picture.base.BaseFragment;
import com.selector.picture.constant.Constant;
import com.selector.picture.model.LocalMedia;
import com.selector.picture.model.LocalMediaFolder;
import com.selector.picture.model.MimeType;
import com.selector.picture.model.PicConfig;
import com.selector.picture.model.PicSelector;
import com.selector.picture.utils.OnItemClickListener;
import com.selector.picture.utils.StringUtils;
import com.selector.picture.utils.UIUtils;
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
public class PhotoPreviewsFragment extends BaseFragment implements View.OnClickListener, OnItemClickListener<LocalMedia> {

    private PhotoPreviewsActivity activity;
    private TextView tvTopLeftText;
    private TextView tvTopSendText;
    private TextView tvBottomCenterTextPreviews;
    private TextView tvBottomSelectTextPreviews;
    private ViewPager vp;
    private PhotoPreviewFragmentAdapter adapter;
    private PhotoPreviewAdapter adapterPreview;
    private ArrayList<LocalMedia> list;//viewpager数据集合
    private ArrayList<LocalMedia> listPreview;//底部recyclerview数据集合
    private List<LocalMedia> sendMedia;//发送和预览的集合
    private int position = 0;
    private TextView tvBottomLeftTextPreviews;
    private RecyclerView ryPreviews;
    private boolean isHide = false;//显示隐藏top 和 bottom view
    private RelativeLayout rlTopRoot;
    private LinearLayout llBottomRootPreviews;


    @Override
    protected int initView() {
        return R.layout.fragment_photo_previews;
    }

    @Override
    protected void initData() {
        activity = (PhotoPreviewsActivity) getActivity();
        list = new ArrayList<>();
        listPreview = new ArrayList<>();
        View view = getView();
        rlTopRoot = view.findViewById(R.id.rl_top_root);//顶部根布局
        ImageView ivTopLeftBack = view.findViewById(R.id.iv_top_left_back);//顶部左侧后退按钮
        tvTopLeftText = view.findViewById(R.id.tv_top_lef_text); //顶部左侧标题
        tvTopSendText = view.findViewById(R.id.tv_top_send_text); //顶部右侧发送按钮
        llBottomRootPreviews = view.findViewById(R.id.ll_bottom_root_previews); //底部根布局
        ryPreviews = view.findViewById(R.id.ry_previews);//recyclerview
        tvBottomLeftTextPreviews = view.findViewById(R.id.tv_bottom_lef_text_previews); //底部左侧编辑
        tvBottomCenterTextPreviews = view.findViewById(R.id.tv_bottom_center_text_previews);//底部中间原图标题
        tvBottomSelectTextPreviews = view.findViewById(R.id.tv_bottom_select_text_previews);//底部右侧选择按钮
        vp = view.findViewById(R.id.vp);  //viewpager
        tvTopSendText.setOnClickListener(this);
        ivTopLeftBack.setOnClickListener(this);
        tvBottomCenterTextPreviews.setOnClickListener(this);
        tvBottomSelectTextPreviews.setOnClickListener(this);

        adapter = new PhotoPreviewFragmentAdapter(getChildFragmentManager(), list);
        vp.setAdapter(adapter);
        vp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setVPCurrent(position);
            }
        });
        ryPreviews.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        adapterPreview = new PhotoPreviewAdapter(activity, this, this, listPreview);
        ryPreviews.setAdapter(adapterPreview);

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
        listPreview.clear();
        if (sendMedia != null) {
            listPreview.addAll(sendMedia);
        }
        if (listPreview != null && listPreview.size() > 0) {
            ryPreviews.setVisibility(View.VISIBLE);
            adapterPreview.notifyDataSetChanged();
        } else {
            ryPreviews.setVisibility(View.GONE);
        }

        if (position == 0) {
            setVPCurrent(position);
        } else {
            vp.setCurrentItem(position);
        }
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
                            activity.setResult();
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
        }
    }

    /**
     * 设置当前的vp 信息
     *
     * @param position 0时没有onPageSelected 回调
     */
    private void setVPCurrent(int position) {
        LocalMedia media = list.get(position);
        if (media != null) {
            String mimeType = StringUtils.nullToString(media.getPictureType());
            int pictureType = MimeType.isPictureType(mimeType);
            if (pictureType == MimeType.TYPE_IMAGE) {
                tvBottomLeftTextPreviews.setVisibility(View.VISIBLE);
                tvBottomCenterTextPreviews.setVisibility(View.VISIBLE);
            } else {
                tvBottomLeftTextPreviews.setVisibility(View.GONE);
                tvBottomCenterTextPreviews.setVisibility(View.GONE);
            }
            tvBottomSelectTextPreviews.setSelected(media.isChecked());
            if (listPreview != null && listPreview.size() > 0) {
                String id = media.getId();
                for (int i = 0; i < listPreview.size(); i++) {
                    LocalMedia localMedia = listPreview.get(i);
                    if (localMedia != null) {
                        if (TextUtils.equals(id, localMedia.getId())) {
                            localMedia.setSelect(true);
                            ryPreviews.scrollToPosition(i);
                        } else {
                            localMedia.setSelect(false);
                        }
                    }
                }
                adapterPreview.notifyDataSetChanged();
            }
        } else {
            tvBottomSelectTextPreviews.setSelected(false);
        }
        tvTopLeftText.setText(getString(R.string.picture_previews_top_left_text, String.valueOf(position + 1), String.valueOf(list.size())));
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

    /**
     * 显示隐藏top 和 bottom view    true 隐藏  false 显示
     */
    public void hideView() {
        this.isHide = !isHide;
        if (isHide) {
            UIUtils.setSystemUIVisible(activity, false);
            rlTopRoot.setVisibility(View.GONE);
            llBottomRootPreviews.setVisibility(View.GONE);
//            adapter.notifyDataSetChanged();
        } else {
            UIUtils.setSystemUIVisible(activity, true);
            rlTopRoot.setVisibility(View.VISIBLE);
            llBottomRootPreviews.setVisibility(View.VISIBLE);
//            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(LocalMedia currentMedia) {
        if (currentMedia != null) {
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    LocalMedia media = list.get(i);
                    if (media != null) {
                        if (TextUtils.equals(media.getId(), currentMedia.getId())) {
                            position = i;
                            break;
                        }
                    }
                }
            }
        }
        vp.setCurrentItem(position);
    }
}
