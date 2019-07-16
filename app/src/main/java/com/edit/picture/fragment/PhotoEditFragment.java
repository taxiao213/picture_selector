package com.edit.picture.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edit.picture.activity.PhotoEditActivity;
import com.edit.picture.adapter.PhotoEditAdapter;
import com.edit.picture.model.Mode;
import com.edit.picture.view.PhotoEditDialogTextUtils;
import com.edit.picture.view.PhotoEditImageView;
import com.selector.picture.R;
import com.selector.picture.base.BaseFragment;
import com.selector.picture.constant.Constant;
import com.selector.picture.model.ColorModel;
import com.selector.picture.model.LocalMedia;
import com.selector.picture.model.PicConfig;
import com.selector.picture.utils.CompressPicUtil;
import com.selector.picture.utils.Function;
import com.selector.picture.utils.OnItemClickListener;
import com.selector.picture.utils.StringUtils;

import java.util.ArrayList;

/**
 * 图片剪切
 * Create by Han on 2019/5/27
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoEditFragment extends BaseFragment implements View.OnClickListener, OnItemClickListener<Integer>, Function<ColorModel> {

    private PhotoEditActivity activity;
    private RecyclerView ryEditPencileBottom;
    private LinearLayout llGroupEditMosaic;
    private LinearLayout llEditBottomRoot;
    private ImageView tvEditBottomWithdraw;
    private RelativeLayout rlEditTopRoot;
    private FrameLayout flEdit;
    private TextView tvEditBottomPencile;
    private TextView tvEditBottomMosaic;
    private TextView tvEditMosaicGrid;
    private TextView tvEditMosaicSand;
    private LinearLayout llEditColorBottom;
    private PhotoEditImageView photoEditImage;
    private PhotoEditAdapter adapter;
    private ArrayList<ColorModel> list;//画笔颜色的结合
    private LocalMedia model;
    private int currentPosition = -1;//当前位置

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
        Bundle bundle = getArguments();
        if (bundle != null) {
            model = bundle.getParcelable(Constant.ACTION_TYPE1);
        }
        photoEditImage = view.findViewById(R.id.iv_edit); //图片
        rlEditTopRoot = view.findViewById(R.id.rl_edit_top_root); //顶部根View
        flEdit = view.findViewById(R.id.fl_edit); //可编辑View
        TextView rlEditTopCancel = view.findViewById(R.id.tv_edit_top_cancel);//顶部取消按钮
        TextView rlEditTopComplete = view.findViewById(R.id.tv_edit_top_complete);//顶部完成按钮
        llEditBottomRoot = view.findViewById(R.id.ll_edit_bottom_root); //底部根View
        ryEditPencileBottom = view.findViewById(R.id.ry_edit_pencile_bottom);//底部RecyclerView 展示颜色
        llGroupEditMosaic = view.findViewById(R.id.ll_group_edit_mosaic); //底部Mosaic
        LinearLayout llEditBottom = view.findViewById(R.id.ll_edit_bottom);//底部按钮根View
        llEditColorBottom = view.findViewById(R.id.ll_edit_color_bottom);//底部按钮选择颜色根View
        tvEditBottomPencile = view.findViewById(R.id.tv_edit_bottom_pencile); //底部铅笔按钮
        TextView tvEditBottomText = view.findViewById(R.id.tv_edit_bottom_text);//底部文本按钮
        tvEditBottomMosaic = view.findViewById(R.id.tv_edit_bottom_mosaic); //底部马赛克按钮
        TextView tvEditBottomCut = view.findViewById(R.id.tv_edit_bottom_cut);//底部剪切按钮
        tvEditBottomWithdraw = view.findViewById(R.id.tv_edit_bottom_withdraw); //底部撤回按钮
        tvEditMosaicGrid = view.findViewById(R.id.tv_edit_mosaic_grid); //底部MosaicGrid网格
        tvEditMosaicSand = view.findViewById(R.id.tv_edit_mosaic_sand); //底部MosaicSand网格
        rlEditTopCancel.setOnClickListener(this);
        rlEditTopComplete.setOnClickListener(this);
        tvEditBottomPencile.setOnClickListener(this);
        tvEditBottomText.setOnClickListener(this);
        tvEditBottomMosaic.setOnClickListener(this);
        tvEditBottomCut.setOnClickListener(this);
        tvEditBottomWithdraw.setOnClickListener(this);
        tvEditMosaicGrid.setOnClickListener(this);
        tvEditMosaicSand.setOnClickListener(this);
        llEditColorBottom.setVisibility(View.GONE);
        tvEditBottomPencile.setSelected(false);
        tvEditBottomMosaic.setSelected(false);

        list = new ArrayList<>();
        adapter = new PhotoEditAdapter(activity, this, list);
        LinearLayoutManager manager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        ryEditPencileBottom.setAdapter(adapter);
        ryEditPencileBottom.setLayoutManager(manager);
        setMosaicSelect(Constant.TYPE1);

        initColorList();
        if (model != null) {
            String path = model.getPath();
            if (!TextUtils.isEmpty(path)) {
                Bitmap bitmap = CompressPicUtil.getEditImage(StringUtils.nullToString(model.getPath()), activity);
                if (bitmap != null) {
                    if (photoEditImage != null) {
                        photoEditImage.setPhotoEditImage(bitmap);
                    }
                }
            }
        }
    }

    /**
     * 初始化画笔颜色,默认第一个缩放
     */
    private void initColorList() {
        int[] paintColor = PicConfig.getInstances().getEditPaintColor();
        if (paintColor == null) {
            paintColor = Constant.PIC_EDIT_PAINT_COLOR;
        }
        for (int i = 0; i < paintColor.length; i++) {
            if (i == 0) {
                list.add(new ColorModel(activity.getResources().getColor(paintColor[i]), true));
            } else {
                list.add(new ColorModel(activity.getResources().getColor(paintColor[i]), false));
            }
            this.currentPosition = 0;
        }
        adapter.notifyDataSetChanged();
        setPaintColor();
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.tv_edit_top_cancel:
                    //取消
                    if (activity != null) {
                        activity.finish();
                    }
                    break;
                case R.id.tv_edit_top_complete:
                    //完成
                    break;
                case R.id.tv_edit_bottom_withdraw:
                    //撤回
                    if (photoEditImage != null) {
                        photoEditImage.withdrawPath();
                    }
                    break;
                case R.id.tv_edit_bottom_pencile:
                    //画笔
                    switchPencileAndMosaic(true);
                    break;
                case R.id.tv_edit_bottom_text:
                    //文本
                    topAndBottomVisible(false);
                    new PhotoEditDialogTextUtils(activity, this);
                    break;
                case R.id.tv_edit_bottom_mosaic:
                    //马赛克
                    switchPencileAndMosaic(false);
                    break;
                case R.id.tv_edit_mosaic_grid:
                    //底部MosaicGrid网格
                    setMosaicSelect(Constant.TYPE1);
                    break;
                case R.id.tv_edit_mosaic_sand:
                    //底部MosaicSand网格
                    setMosaicSelect(Constant.TYPE2);
                    break;
                case R.id.tv_edit_bottom_cut:
                    //剪切
                    break;
            }
        }
    }

    @Override
    public void onItemClick(Integer position) {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                ColorModel model = list.get(i);
                if (model != null) {
                    model.reductionCoefficient();
                }
            }
            if (position != null && position != -1) {
                this.currentPosition = position;
                ColorModel colorModel = list.get(position);
                colorModel.setScaleCoefficient();
            }
        }
        adapter.notifyDataSetChanged();
        setPaintColor();
    }


    @Override
    public void action(ColorModel var) {
        topAndBottomVisible(true);
        if (var != null) {
            // TODO: 2019/6/27 addView
        }
    }

    /**
     * pencile和mosaic切换
     *
     * @param isVisible true pencile  false mosaic
     */
    private void switchPencileAndMosaic(boolean isVisible) {
        if (isVisible) {
            boolean selected = tvEditBottomPencile.isSelected();
            tvEditBottomPencile.setSelected(!selected);
            tvEditBottomMosaic.setSelected(false);
            llGroupEditMosaic.setVisibility(View.GONE);
            if (tvEditBottomPencile.isSelected()) {
                llEditColorBottom.setVisibility(View.VISIBLE);
                ryEditPencileBottom.setVisibility(View.VISIBLE);
                setMode(Mode.PENCIL);
            } else {
                llEditColorBottom.setVisibility(View.GONE);
                setMode(Mode.NONE);
            }
        } else {
            boolean selected = tvEditBottomMosaic.isSelected();
            tvEditBottomMosaic.setSelected(!selected);
            tvEditBottomPencile.setSelected(false);
            ryEditPencileBottom.setVisibility(View.GONE);
            if (tvEditBottomMosaic.isSelected()) {
                llEditColorBottom.setVisibility(View.VISIBLE);
                llGroupEditMosaic.setVisibility(View.VISIBLE);
                setMode(Mode.MOSAIC);
            } else {
                llEditColorBottom.setVisibility(View.GONE);
                setMode(Mode.NONE);
            }
        }
//        if (isVisible) {
//            setGravity(Gravity.CENTER);
//        } else {
//            setGravity(Gravity.TOP);
//        }
    }

    /**
     * 设置对齐方式
     *
     * @param gravity int
     */
    private void setGravity(int gravity) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tvEditBottomWithdraw.getLayoutParams();
        params.gravity = gravity;
        tvEditBottomWithdraw.requestLayout();
    }

    /**
     * 设置马赛克选中
     *
     * @param type {@link Constant#ACTION_TYPE1 tvEditMosaicGrid 选中 默认选中}
     *             {@link Constant#ACTION_TYPE2 tvEditMosaicSand 选中}
     */
    private void setMosaicSelect(int type) {
        if (type == Constant.TYPE1) {
            tvEditMosaicGrid.setSelected(true);
            tvEditMosaicSand.setSelected(false);
        } else if (type == Constant.TYPE2) {
            tvEditMosaicGrid.setSelected(false);
            tvEditMosaicSand.setSelected(true);
        }
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

    /**
     * 获取当前model
     *
     * @return ColorModel
     */
    public ColorModel getCurrentModel() {
        ColorModel model = null;
        if (currentPosition != -1) {
            model = list.get(currentPosition);
        }
        return model;
    }

    /**
     * 设置画笔颜色
     */
    public void setPaintColor() {
        ColorModel currentModel = getCurrentModel();
        if (currentModel != null) {
            if (photoEditImage != null) {
                photoEditImage.setPaintColor(currentModel.getFrontColor());
            }
        }
    }

    /**
     * 设置模式
     *
     * @param mode Mode
     */
    private void setMode(Mode mode) {
        if (photoEditImage != null) {
            photoEditImage.setMode(mode);
        }
    }
}
