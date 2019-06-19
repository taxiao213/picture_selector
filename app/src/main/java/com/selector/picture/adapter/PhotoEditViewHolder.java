package com.selector.picture.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.selector.picture.R;
import com.selector.picture.activity.PhotoEditActivity;
import com.selector.picture.activity.PhotoPreviewsActivity;
import com.selector.picture.model.LocalMedia;
import com.selector.picture.model.MimeType;
import com.selector.picture.utils.OnItemClickListener;
import com.selector.picture.utils.PicUtils;
import com.selector.picture.utils.StringUtils;
import com.selector.picture.utils.UIUtils;

/**
 * 图片剪切颜色ViewHolder
 * Create by Han on 2019/5/21
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoEditViewHolder extends RecyclerView.ViewHolder {

    private TextView tv_duration;
    private View currentView;
    private PhotoEditAdapter adapter;
    private OnItemClickListener<LocalMedia> mOnItemClickListener;

    PhotoEditViewHolder(@NonNull View itemView, PhotoEditAdapter adapter, OnItemClickListener<LocalMedia> onItemClickListener) {
        super(itemView);
        this.currentView = itemView;
        this.adapter = adapter;
        this.mOnItemClickListener = onItemClickListener;
        tv_duration = itemView.findViewById(R.id.tv_duration);
    }

    public void bindViewHolder(final PhotoEditActivity context, final LocalMedia model) {
        if (context == null || model == null) return;

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
