package com.selector.picture.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.selector.picture.R;
import com.selector.picture.model.LocalMedia;

/**
 * Create by Han on 2019/5/21
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class GridPicViewHolder extends RecyclerView.ViewHolder {

    public GridPicViewHolder(@NonNull View itemView) {
        super(itemView);
        View iv_picture = itemView.findViewById(R.id.iv_picture);
        View ll_check = itemView.findViewById(R.id.ll_check);
        View tv_check = itemView.findViewById(R.id.tv_check);
        View tv_gif = itemView.findViewById(R.id.tv_gif);
        View tv_long_chart = itemView.findViewById(R.id.tv_long_chart);
        View tv_duration = itemView.findViewById(R.id.tv_duration);

    }

    public void bindViewHolder(Context mContext, LocalMedia model) {

    }
}
