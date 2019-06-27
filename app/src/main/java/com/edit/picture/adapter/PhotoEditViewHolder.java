package com.edit.picture.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.selector.picture.R;
import com.edit.picture.activity.PhotoEditActivity;
import com.selector.picture.model.ColorModel;
import com.selector.picture.utils.OnItemClickListener;
import com.edit.picture.view.PhotoEditRoundView;

/**
 * 图片剪切颜色ViewHolder
 * Create by Han on 2019/5/21
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoEditViewHolder extends RecyclerView.ViewHolder {

    private PhotoEditRoundView tv_round_view;
    private View currentView;
    private OnItemClickListener<ColorModel> mOnItemClickListener;

    PhotoEditViewHolder(@NonNull View itemView,  OnItemClickListener<ColorModel> onItemClickListener) {
        super(itemView);
        this.currentView = itemView;
        this.mOnItemClickListener = onItemClickListener;
        tv_round_view = itemView.findViewById(R.id.tv_round_view);
    }

    public void bindViewHolder(final PhotoEditActivity context, final ColorModel model) {
        if (context == null || model == null) return;
        int frontColor = model.getFrontColor();
        float scaleCoefficient = model.getScaleCoefficient();
        tv_round_view.setFrontColor(frontColor);
        tv_round_view.setScaleCoefficient(scaleCoefficient);
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
