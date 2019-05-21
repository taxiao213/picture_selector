package com.selector.picture.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.selector.picture.R;
import com.selector.picture.model.LocalMedia;
import com.selector.picture.model.LocalMediaFolder;

import java.util.List;


/**
 * Create by Han on 2019/5/21
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class GridPicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<LocalMedia> mList;

    public GridPicAdapter(Context context, List<LocalMedia> list) {
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_grid_pic, viewGroup, false);
        return new GridPicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        GridPicViewHolder picViewHolder = (GridPicViewHolder) viewHolder;
        picViewHolder.bindViewHolder(mContext,mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
