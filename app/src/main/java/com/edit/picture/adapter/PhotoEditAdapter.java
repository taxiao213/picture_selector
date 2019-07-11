package com.edit.picture.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edit.picture.activity.PhotoEditActivity;
import com.selector.picture.R;
import com.selector.picture.model.ColorModel;
import com.selector.picture.utils.OnItemClickListener;

import java.util.List;

/**
 * 图片剪切颜色Adapter
 * Create by Han on 2019/5/21
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoEditAdapter extends RecyclerView.Adapter<PhotoEditViewHolder> {

    private PhotoEditActivity mContext;
    private List<ColorModel> mList;
    private OnItemClickListener<Integer> mOnItemClickListener;

    public PhotoEditAdapter(PhotoEditActivity context, OnItemClickListener<Integer> onItemClickListener, List<ColorModel> list) {
        this.mContext = context;
        this.mOnItemClickListener = onItemClickListener;
        this.mList = list;
    }

    @NonNull
    @Override
    public PhotoEditViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_photo_edit, viewGroup, false);
        PhotoEditViewHolder holder = new PhotoEditViewHolder(view, mOnItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoEditViewHolder viewHolder, int position) {
        viewHolder.bindViewHolder(mContext, mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
