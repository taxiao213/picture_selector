package com.selector.picture.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.selector.picture.R;
import com.selector.picture.base.BaseFragment;
import com.selector.picture.model.PicSelector;

/**
 * 图片选择的fragment
 * Create by Han on 2019/5/10
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PictureSelectorFragment extends BaseFragment {

    @Override
    protected int initView() {
        return R.layout.fragment_picture_selector;
    }

    @Override
    protected void initData() {
        View view = getView();
        RelativeLayout rlTopRoot = view.findViewById(R.id.rl_top_root);//顶部根布局
        ImageView ivTopLeftBack = view.findViewById(R.id.iv_top_left_back);//顶部左侧后退按钮
        TextView tvTopLeftText = view.findViewById(R.id.tv_top_lef_text);//顶部左侧标题
        TextView tvTopSendText = view.findViewById(R.id.tv_top_send_text);//顶部右侧发送按钮

        TextView tvTopSlideLeftText = view.findViewById(R.id.tv_top_slide_lef_text);//recyclerview 滑动时显示的提示框
        RecyclerView ry = view.findViewById(R.id.ry);//recyclerview
        RelativeLayout rlBottomRoot = view.findViewById(R.id.rl_bottom_root);//底部根布局
        TextView tvBottomLeftText = view.findViewById(R.id.tv_bottom_lef_text);//底部左侧标题
        CheckBox tvBottomCenterText = view.findViewById(R.id.ck_bottom_center_text);//底部中间标题
        TextView tvBottomPreviewText = view.findViewById(R.id.tv_bottom_preview_text);//底部右侧预览按钮
    }

    /**
     * 设置配置
     *
     * @param picSelector PicSelector
     */
    public void setConfiguration(PicSelector picSelector) {

    }
}
