package com.selector.picture.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.selector.picture.R;
import com.selector.picture.model.LocalMediaFolder;
import com.selector.picture.utils.OnItemClickListener;

import java.util.List;


/**
 * 相册选择Adapter
 * Create by Han on 2019/5/21
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumViewHolder> {

    private Context mContext;
    private List<LocalMediaFolder> mList;
    private OnItemClickListener<LocalMediaFolder> mOnItemClickListener;

    public AlbumAdapter(Context context, List<LocalMediaFolder> list, OnItemClickListener<LocalMediaFolder> onItemClickListener) {
        this.mContext = context;
        this.mOnItemClickListener = onItemClickListener;
        this.mList = list;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_album, viewGroup, false);
        return new AlbumViewHolder(view, this, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder viewHolder, int position) {
        AlbumViewHolder picViewHolder = (AlbumViewHolder) viewHolder;
        picViewHolder.bindViewHolder(mContext, mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
