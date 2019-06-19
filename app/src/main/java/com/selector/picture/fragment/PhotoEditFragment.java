package com.selector.picture.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.selector.picture.R;
import com.selector.picture.activity.PhotoEditActivity;
import com.selector.picture.base.BaseFragment;

/**
 * 图片剪切
 * Create by Han on 2019/5/27
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoEditFragment extends BaseFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private PhotoEditActivity activity;
    private RecyclerView ryEditPencileBottom;
    private RadioGroup radioGroupEditMosaic;
    //    private LinearLayout llEditBottomTopRoot;
    private LinearLayout llEditBottomRoot;
    private RelativeLayout rlEditTopRoot;


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
        ImageView ivEdit = view.findViewById(R.id.iv_edit);//图片
        rlEditTopRoot = view.findViewById(R.id.rl_edit_top_root); //顶部根View
        TextView rlEditTopCancel = view.findViewById(R.id.tv_edit_top_cancel);//顶部取消按钮
        TextView rlEditTopComplete = view.findViewById(R.id.tv_edit_top_complete);//顶部完成按钮
        llEditBottomRoot = view.findViewById(R.id.ll_edit_bottom_root); //底部根View
//        llEditBottomTopRoot = view.findViewById(R.id.ll_edit_bottom_top_root);//底部根View
        ryEditPencileBottom = view.findViewById(R.id.ry_edit_pencile_bottom);//底部RecyclerView 展示颜色
        radioGroupEditMosaic = view.findViewById(R.id.radio_group_edit_mosaic); //底部Mosaic
        RadioGroup radioEditGroup = view.findViewById(R.id.radio_group_edit_bottom);//底部RadioGroup
        TextView tvEditBottomText = view.findViewById(R.id.tv_edit_bottom_text);//底部文本按钮
        TextView tvEditBottomCut = view.findViewById(R.id.tv_edit_bottom_cut);//底部剪切按钮
        TextView tvEditBottomWithdraw = view.findViewById(R.id.tv_edit_bottom_withdraw);//底部撤回按钮
        rlEditTopCancel.setOnClickListener(this);
        rlEditTopComplete.setOnClickListener(this);
        tvEditBottomText.setOnClickListener(this);
        tvEditBottomCut.setOnClickListener(this);
        tvEditBottomWithdraw.setOnClickListener(this);
        radioGroupEditMosaic.setOnCheckedChangeListener(this);
        radioEditGroup.setOnCheckedChangeListener(this);

        radioGroupEditMosaic.check(R.id.radio_edit_mosaic_grid);
        radioEditGroup.check(R.id.radio_edit_bottom_pencile);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_edit_bottom_pencile:
                switchPencileAndMosaic(true);
                break;
            case R.id.radio_edit_bottom_mosaic:
                switchPencileAndMosaic(false);
                break;
            case R.id.radio_edit_mosaic_grid:
                //底部MosaicGrid网格

                break;
            case R.id.radio_edit_mosaic_sand:
                //底部MosaicSand网格

                break;
        }

    }


    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.tv_edit_top_cancel:
                    //取消
                    break;
                case R.id.tv_edit_top_complete:
                    //完成
                    break;
                case R.id.tv_edit_bottom_withdraw:
                    //撤回
                    break;
                case R.id.tv_edit_bottom_text:
                    //文本
//                    topAndBottomVisible(false);
                    //弹框显示
                    break;
                case R.id.tv_edit_bottom_cut:
                    //剪切
                    break;
            }
        }
    }

    /**
     * pencile和mosaic切换
     *
     * @param isVisible true pencile可见 false mosaic不可见
     */
    private void switchPencileAndMosaic(boolean isVisible) {
        llEditBottomRoot.setVisibility(View.VISIBLE);
        ryEditPencileBottom.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        radioGroupEditMosaic.setVisibility(isVisible ? View.GONE : View.VISIBLE);
    }

    /**
     * 顶部和底部View展示
     *
     * @param isVisible true 可见 false不可见
     */
    private void topAndBottomVisible(boolean isVisible) {
        llEditBottomRoot.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        rlEditTopRoot.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
}