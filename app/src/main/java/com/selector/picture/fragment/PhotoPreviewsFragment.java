package com.selector.picture.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.selector.picture.R;
import com.selector.picture.activity.PhotoPreviewsActivity;
import com.selector.picture.activity.PhotoSelectActivity;
import com.selector.picture.adapter.PhotoPreviewAdapter;
import com.selector.picture.adapter.PhotoPreviewFragmentAdapter;
import com.selector.picture.base.BaseFragment;
import com.selector.picture.constant.Constant;
import com.selector.picture.model.LocalMedia;
import com.selector.picture.model.MimeType;
import com.selector.picture.model.PicConfig;
import com.selector.picture.model.PicList;
import com.selector.picture.utils.OnItemClickListener;
import com.selector.picture.utils.StringUtils;
import com.selector.picture.utils.UIUtils;
import com.selector.picture.view.MyViewPager;
import com.selector.picture.view.StatusBarUtil;

import java.util.ArrayList;
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
    private MyViewPager vp;
    private TextView tvBottomLeftTextPreviews;
    private RecyclerView ryPreviews;
    private RelativeLayout rlTopRoot;
    private LinearLayout llBottomRootPreviews;
    private PhotoPreviewFragmentAdapter adapter;
    private PhotoPreviewAdapter adapterPreview;
    private ArrayList<LocalMedia> list;//viewpager数据集合
    private ArrayList<LocalMedia> listPreview;//底部recyclerview数据集合
    private List<LocalMedia> sendMedia;//发送和预览的集合
    private int currentPosition = 0;
    private int type;//1 加载的全部数据 2 预览数据
    private boolean isHide = false;//显示隐藏top 和 bottom view
    private int barHeight;//状态栏高度
    private float heightTop;//顶部布局高度
    private float heightBottom;//底部布局高度
    private float heightBottomRy;//底部布局ry高度
    private float heightBottomLine;//底部布局line高度

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (PhotoPreviewsActivity) context;
    }

    @Override
    protected int initView() {
        return R.layout.fragment_photo_previews;
    }

    @Override
    protected void initData() {
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
        tvBottomLeftTextPreviews.setVisibility(PicConfig.getInstances().isEditable() ? View.VISIBLE : View.GONE);
        tvBottomCenterTextPreviews.setVisibility(PicConfig.getInstances().isOptionOriginalImage() ? View.VISIBLE : View.GONE);
        barHeight = StatusBarUtil.getStatusBarHeight(activity);
        heightTop = activity.getResources().getDimension(R.dimen.picture_selector_top_height);
        heightBottom = activity.getResources().getDimension(R.dimen.picture_selector_bottom_height);
        heightBottomRy = activity.getResources().getDimension(R.dimen.picture_selector_previews_ry_height);
        heightBottomLine = activity.getResources().getDimension(R.dimen.picture_selector_previews_ry_line_height);
        ViewGroup.LayoutParams params = rlTopRoot.getLayoutParams();
        params.height = barHeight + (int) heightTop;
        rlTopRoot.requestLayout();
        tvTopSendText.setOnClickListener(this);
        ivTopLeftBack.setOnClickListener(this);
        tvBottomLeftTextPreviews.setOnClickListener(this);
        tvBottomCenterTextPreviews.setOnClickListener(this);
        tvBottomSelectTextPreviews.setOnClickListener(this);
        llBottomRootPreviews.setOnClickListener(this);
        rlTopRoot.setOnClickListener(this);

        adapter = new PhotoPreviewFragmentAdapter(getChildFragmentManager(), list);
        vp.setAdapter(adapter);
        vp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition = position;
                setVPCurrent();
            }
        });
        Bundle bundle = getArguments();
        if (bundle != null) {
            LocalMedia loaclMedia = bundle.getParcelable(Constant.ACTION_TYPE1);
            if (loaclMedia != null) {
                setData(loaclMedia, Constant.TYPE1);
            }
        } else {
            setData(null, Constant.TYPE2);
        }
        ryPreviews.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        adapterPreview = new PhotoPreviewAdapter(activity, this, listPreview, type);
        ryPreviews.setAdapter(adapterPreview);

        sendMedia = PicList.getInstances().getSendList();
        if (sendMedia == null) {
            sendMedia = new ArrayList<>();
            PicList.getInstances().setSendList(sendMedia);
        }
        setText();
        initBottomCenterText();
        listPreview.clear();
        if (sendMedia != null) {
            listPreview.addAll(sendMedia);
        }
        if (currentPosition == 0) {
            setVPCurrent();
        } else {
            vp.setCurrentItem(currentPosition);
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
                    if (PicConfig.getInstances().isEditable()) {
                        //编辑
                    }
                    break;
                case R.id.tv_bottom_center_text_previews:
                    if (PicConfig.getInstances().isOptionOriginalImage()) {
                        //是否选择原图
                        PicConfig.getInstances().setLoadOriginalImage(!PicConfig.getInstances().isLoadOriginalImage());
                        initBottomCenterText();
                    }
                    break;
                case R.id.tv_bottom_select_text_previews:
                    //底部右侧选择
                    setBottomAndTopText();
                    break;

            }
        }
    }

    /**
     * 设置底部和顶部数据
     */
    private void setBottomAndTopText() {
        LocalMedia media = list.get(currentPosition);
        if (media != null) {
            boolean checked = media.isChecked();
            if (!checked) {
                if (UIUtils.selectNumNotice(sendMedia, activity)) return;
            }
            media.setChecked(!checked);
            selectModel(media, sendMedia, Constant.TYPE1);
            selectModel(media, listPreview, Constant.TYPE2);
        }
        setVPCurrent();
        setText();
    }

    /**
     * 选择model 添加和删除状态
     *
     * @param media    LocalMedia
     * @param list     List<LocalMedia>
     * @param listType int   1发送集合 2底部预览集合
     */
    private void selectModel(LocalMedia media, List<LocalMedia> list, int listType) {
        boolean presence = false;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                LocalMedia localMedia = list.get(i);
                if (localMedia != null) {
                    if (TextUtils.equals(media.getId(), localMedia.getId())) {
                        presence = true;
                        break;
                    }
                }
            }
        }
        if (listType == Constant.TYPE1) {
            setSelectStatus(media, list, presence);
        } else if (listType == Constant.TYPE2) {
            if (type == Constant.TYPE1) {
                setSelectStatus(media, list, presence);
            } else if (type == Constant.TYPE2) {
                if (presence) {
                    if (list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            LocalMedia localMedia = list.get(i);
                            if (localMedia != null) {
                                if (TextUtils.equals(media.getId(), localMedia.getId())) {
                                    localMedia.setDelete(!media.isChecked());
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    /**
     * 选择model 添加和删除状态
     *
     * @param media    LocalMedia
     * @param list     List<LocalMedia>
     * @param presence true 存在该model  false 不存在
     */
    private void setSelectStatus(LocalMedia media, List<LocalMedia> list, boolean presence) {
        if (presence) {
            if (!media.isChecked()) {
                list.remove(media);
            }
        } else {
            if (media.isChecked()) {
                if (list != null) {
                    list.add(media);
                }
            }
        }
    }

    /**
     * 设置预览的图片
     *
     * @param currentMedia LocalMedia 选择预览跳转 为null 选择图片跳转不为null
     * @param type         1 Constant.TYPE1(全部数据)  2 Constant.TYPE2(预览数据)
     */
    public void setData(LocalMedia currentMedia, int type) {
        if (activity != null) {
            this.type = type;
            List<LocalMedia> listLocalMedia;
            if (currentMedia != null) {
                listLocalMedia = PicList.getInstances().getCurrentList();
            } else {
                listLocalMedia = PicList.getInstances().getSendList();
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
                                currentPosition = i;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 设置当前的vp 信息   0时没有onPageSelected 回调
     */
    private void setVPCurrent() {
        LocalMedia media = list.get(currentPosition);
        if (media != null) {
            String mimeType = StringUtils.nullToString(media.getPictureType());
            int pictureType = MimeType.isPictureType(mimeType);
            if (pictureType == MimeType.TYPE_IMAGE) {
                if (MimeType.isGif(mimeType)) {
                    tvBottomLeftTextPreviews.setVisibility(View.GONE);
                    tvBottomCenterTextPreviews.setVisibility(View.GONE);
                } else {
                    tvBottomLeftTextPreviews.setVisibility(PicConfig.getInstances().isEditable() ? View.VISIBLE : View.GONE);
                    tvBottomCenterTextPreviews.setVisibility(PicConfig.getInstances().isOptionOriginalImage() ? View.VISIBLE : View.GONE);
                }
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
                if (!isHide) {
                    ViewGroup.LayoutParams params = llBottomRootPreviews.getLayoutParams();
                    params.height = (int) heightBottom + (int) heightBottomRy + (int) heightBottomLine;
                    llBottomRootPreviews.requestLayout();
                    ryPreviews.setVisibility(View.VISIBLE);
                }
                adapterPreview.notifyDataSetChanged();
            } else {
                if (!isHide) {
                    ViewGroup.LayoutParams params = llBottomRootPreviews.getLayoutParams();
                    params.height = (int) heightBottom;
                    llBottomRootPreviews.requestLayout();
                    ryPreviews.setVisibility(View.GONE);
                }
            }
        } else {
            tvBottomSelectTextPreviews.setSelected(false);
        }
        tvTopLeftText.setText(getString(R.string.picture_previews_top_left_text, String.valueOf(currentPosition + 1), String.valueOf(list.size())));
    }

    /**
     * 设置发送和预览按钮状态
     */
    private void setText() {
        if (sendMedia != null && sendMedia.size() > 0) {
            setSelected(true);
            tvTopSendText.setText(getString(R.string.picture_selector_top_send_text_select, String.valueOf(sendMedia.size()), String.valueOf(PicConfig.getInstances().getMaxSelectNum())));
        } else {
            setSelected(false);
            tvTopSendText.setText(R.string.picture_selector_top_send_text_default);
        }
    }

    /**
     * 设置view 选中
     *
     * @param isSelected true选中  false未选中
     */
    public void setSelected(boolean isSelected) {
        tvTopSendText.setSelected(isSelected);
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
            if (activity != null) {
                activity.immersiveHide();
            }
            UIUtils.startAnimation(rlTopRoot, barHeight + heightTop, 0);
            if (listPreview != null && listPreview.size() > 0) {
                UIUtils.startAnimation(llBottomRootPreviews, heightBottom + heightBottomRy + heightBottomLine, 0);
            } else {
                UIUtils.startAnimation(llBottomRootPreviews, heightBottom, 0);
            }
        } else {
            if (activity != null) {
                activity.immersiveShow();
            }
            UIUtils.startAnimation(rlTopRoot, 0, barHeight + heightTop);
            if (listPreview != null && listPreview.size() > 0) {
                UIUtils.startAnimation(llBottomRootPreviews, 0, heightBottom + heightBottomRy + heightBottomLine);
            } else {
                UIUtils.startAnimation(llBottomRootPreviews, 0, heightBottom);
            }
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
                            currentPosition = i;
                            break;
                        }
                    }
                }
            }
        }
        vp.setCurrentItem(currentPosition);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (type == Constant.TYPE2) {
            if (listPreview != null && listPreview.size() > 0) {
                for (int i = 0; i < listPreview.size(); i++) {
                    LocalMedia media = listPreview.get(i);
                    if (media != null) {
                        media.setDelete(false);
                    }
                }
            }
        }
        if (adapter != null) {
            Fragment item = adapter.getItem(currentPosition);
            if (item != null) {
                item.onDestroyView();
            }
        }
    }
}
