package com.selector.picture.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.selector.picture.R;
import com.selector.picture.activity.PhotoPreviewsActivity;
import com.selector.picture.activity.PhotoSelectActivity;
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
 * 预览界面底部图片滑动ViewHolder
 * Create by Han on 2019/5/21
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoPreviewViewHolder extends RecyclerView.ViewHolder {

    private RelativeLayout root_rl;
    private ImageView iv_picture;
    private TextView tv_gif;
    private TextView tv_long_chart;
    private TextView tv_duration;
    private View currentView;
    private PhotoPreviewAdapter adapter;
    private OnItemClickListener<LocalMedia> mOnItemClickListener;

    PhotoPreviewViewHolder(@NonNull View itemView, PhotoPreviewAdapter adapter, OnItemClickListener<LocalMedia> onItemClickListener) {
        super(itemView);
        this.currentView = itemView;
        this.adapter = adapter;
        this.mOnItemClickListener = onItemClickListener;
        root_rl = itemView.findViewById(R.id.root_rl);
        iv_picture = itemView.findViewById(R.id.iv_picture);
        tv_gif = itemView.findViewById(R.id.tv_gif);
        tv_long_chart = itemView.findViewById(R.id.tv_long_chart);
        tv_duration = itemView.findViewById(R.id.tv_duration);
    }

    public void bindViewHolder(final PhotoPreviewsActivity context, final LocalMedia model) {
        if (context == null || model == null) return;
        PicUtils.getInstances().loadPreviewImage(context, iv_picture, model.getPath());
        String mimeType = StringUtils.nullToString(model.getPictureType());
        int pictureType = MimeType.isPictureType(mimeType);
        if (pictureType == MimeType.TYPE_IMAGE) {
            tv_duration.setVisibility(View.GONE);
            tv_duration.setText("");
        } else if (pictureType == MimeType.TYPE_VIDEO) {
            UIUtils.setDrawable(tv_duration, R.drawable.bottom_video_icon);
        } else if (pictureType == MimeType.TYPE_AUDIO) {
            UIUtils.setDrawable(tv_duration, R.drawable.bottom_audio_icon);
        } else {
            tv_duration.setVisibility(View.GONE);
            tv_duration.setText("");
        }
        tv_gif.setVisibility(MimeType.isGif(mimeType) ? View.VISIBLE : View.GONE);
        tv_long_chart.setVisibility(UIUtils.isLongImg(model) ? View.VISIBLE : View.GONE);
        root_rl.setSelected(model.isSelect());
        currentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(model);
                }
            }
        });

    }
}
