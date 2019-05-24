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
import com.selector.picture.model.LocalMediaFolder;
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
public class AlbumViewHolder extends RecyclerView.ViewHolder {

    private ImageView iv_picture;
    private TextView tv_name;
    private TextView tv_count;
    private ImageView iv_select;
    private View currentView;
    private AlbumAdapter adapter;
    private OnItemClickListener<LocalMediaFolder> mOnItemClickListener;

    public AlbumViewHolder(@NonNull View itemView, AlbumAdapter adapter, OnItemClickListener<LocalMediaFolder> onItemClickListener) {
        super(itemView);
        this.currentView = itemView;
        this.adapter = adapter;
        this.mOnItemClickListener = onItemClickListener;
        iv_picture = itemView.findViewById(R.id.iv_picture);
        tv_name = itemView.findViewById(R.id.tv_name);
        tv_count = itemView.findViewById(R.id.tv_count);
        iv_select = itemView.findViewById(R.id.iv_select);
    }

    public void bindViewHolder(final Context context, final LocalMediaFolder model) {
        if (context == null || model == null) return;
        PicUtils.getInstances().loadImage(context, iv_picture, model.getFirstImagePath());
        tv_name.setText(StringUtils.nullToString(model.getName()));
        if (getAdapterPosition() == 0) {
            tv_count.setVisibility(View.GONE);
        } else {
            tv_count.setVisibility(View.VISIBLE);
        }
        tv_count.setText(context.getString(R.string.picture_selector_dialog_count, String.valueOf(model.getImageNum())));
        boolean checked = model.isChecked();
        if (checked) {
            iv_select.setVisibility(View.VISIBLE);
        } else {
            iv_select.setVisibility(View.GONE);
        }
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
