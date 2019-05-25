package com.selector.picture.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.selector.picture.R;
import com.selector.picture.constant.Constant;
import com.selector.picture.model.LocalMedia;
import com.selector.picture.model.MimeType;
import com.selector.picture.model.PicConfig;
import com.selector.picture.utils.DateUtils;
import com.selector.picture.utils.OnItemClickListener;
import com.selector.picture.utils.PicUtils;
import com.selector.picture.utils.StringUtils;
import com.selector.picture.utils.UIUtils;

import java.util.List;

/**
 * Create by Han on 2019/5/21
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class GridPicViewHolder extends RecyclerView.ViewHolder {

    private ImageView iv_picture;
    private RelativeLayout ll_check;
    private TextView tv_check;
    private TextView tv_gif;
    private TextView tv_long_chart;
    private TextView tv_duration;
    private View currentView;
    private GridPicAdapter adapter;
    private OnItemClickListener<com.selector.picture.model.LocalMedia> mOnItemClickListener;

    public GridPicViewHolder(@NonNull View itemView, GridPicAdapter adapter, OnItemClickListener<LocalMedia> onItemClickListener) {
        super(itemView);
        this.currentView = itemView;
        this.adapter = adapter;
        this.mOnItemClickListener = onItemClickListener;
        iv_picture = itemView.findViewById(R.id.iv_picture);
        ll_check = itemView.findViewById(R.id.ll_check);
        tv_check = itemView.findViewById(R.id.tv_check);
        tv_gif = itemView.findViewById(R.id.tv_gif);
        tv_long_chart = itemView.findViewById(R.id.tv_long_chart);
        tv_duration = itemView.findViewById(R.id.tv_duration);
    }


    public void bindViewHolder(final Context context, final LocalMedia model) {
        if (context == null || model == null) return;
        PicUtils.getInstances().loadImage(context, iv_picture, model.getPath());
        String mimeType = StringUtils.nullToString(model.getPictureType());
        int pictureType = MimeType.isPictureType(mimeType);
        if (pictureType == MimeType.TYPE_IMAGE) {
            tv_duration.setVisibility(View.GONE);
            tv_duration.setText("");
        } else if (pictureType == MimeType.TYPE_VIDEO) {
            tv_duration.setText(DateUtils.timeParse(model.getDuration()));
            UIUtils.setDrawable(tv_duration, R.drawable.bottom_video_icon);
        } else if (pictureType == MimeType.TYPE_AUDIO) {
            tv_duration.setText(DateUtils.timeParse(model.getDuration()));
            UIUtils.setDrawable(tv_duration, R.drawable.bottom_audio_icon);
        } else {
            tv_duration.setVisibility(View.GONE);
            tv_duration.setText("");
        }
        tv_gif.setVisibility(MimeType.isGif(mimeType) ? View.VISIBLE : View.GONE);
        tv_long_chart.setVisibility(UIUtils.isLongImg(model) ? View.VISIBLE : View.GONE);
        boolean checked = model.isChecked();
        UIUtils.setSelectStatus(iv_picture, tv_check, checked, Constant.TYPE1);
        if (checked) {
            UIUtils.setSelectAnimation(iv_picture, checked);
        }
        currentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转预览
            }
        });
        ll_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = !(model.isChecked());//是否选中
                if (selected) {
                    if (adapter != null) {
                        List<LocalMedia> sendMedia = adapter.getSendMedia();
                        if (sendMedia != null && (sendMedia.size() >= PicConfig.getInstances().getMaxSelectNum())) {
                            if (PicConfig.getInstances().getImageType() == MimeType.TYPE_ALL) {
                                UIUtils.toastShow(context, context.getString(R.string.picture_selector_notice_all_count, String.valueOf(PicConfig.getInstances().getMaxSelectNum())));
                            } else if (PicConfig.getInstances().getImageType() == MimeType.TYPE_IMAGE) {
                                UIUtils.toastShow(context, context.getString(R.string.picture_selector_notice_pic_count, String.valueOf(PicConfig.getInstances().getMaxSelectNum())));
                            } else if (PicConfig.getInstances().getImageType() == MimeType.TYPE_VIDEO) {
                                UIUtils.toastShow(context, context.getString(R.string.picture_selector_notice_video_count, String.valueOf(PicConfig.getInstances().getMaxSelectNum())));
                            } else if (PicConfig.getInstances().getImageType() == MimeType.TYPE_AUDIO) {
                                UIUtils.toastShow(context, context.getString(R.string.picture_selector_notice_audio_count, String.valueOf(PicConfig.getInstances().getMaxSelectNum())));
                            } else {
                                UIUtils.toastShow(context, context.getString(R.string.picture_selector_notice_all_count, String.valueOf(PicConfig.getInstances().getMaxSelectNum())));
                            }
                            return;
                        }
                    }
                }
                model.setChecked(selected);
                UIUtils.setSelectStatus(iv_picture, tv_check, selected, Constant.TYPE2);
                UIUtils.setSelectAnimation(iv_picture, selected);
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(model);
                }
            }
        });
    }
}
