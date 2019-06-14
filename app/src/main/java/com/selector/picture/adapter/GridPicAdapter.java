package com.selector.picture.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.selector.picture.R;
import com.selector.picture.activity.PhotoSelectActivity;
import com.selector.picture.fragment.PhotoSelectFragment;
import com.selector.picture.model.LocalMedia;
import com.selector.picture.utils.OnItemClickListener;

import java.util.List;


/**
 * 图片加载Adapter
 * Create by Han on 2019/5/21
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class GridPicAdapter extends RecyclerView.Adapter<GridPicViewHolder> {

    private PhotoSelectActivity mContext;
    private List<LocalMedia> mList;
    private OnItemClickListener<LocalMedia> mOnItemClickListener;
    private PhotoSelectFragment mFragment;

    public GridPicAdapter(PhotoSelectActivity context, PhotoSelectFragment fragment, OnItemClickListener<LocalMedia> onItemClickListener, List<LocalMedia> list) {
        this.mContext = context;
        this.mFragment = fragment;
        this.mOnItemClickListener = onItemClickListener;
        this.mList = list;
    }

    @NonNull
    @Override
    public GridPicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_grid_pic, viewGroup, false);
        return new GridPicViewHolder(view, GridPicAdapter.this, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GridPicViewHolder viewHolder, int position) {
        viewHolder.bindViewHolder(mContext, mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 获取选中的集合
     *
     * @return List<LocalMedia>
     */
    public List<LocalMedia> getSendMedia() {
        if (mFragment != null) {
            return mFragment.getSendMedia();
        } else {
            return null;
        }
    }

    /**
     * 获取当前相册集合
     *
     * @return List<LocalMedia>
     */
    public List<LocalMedia> getCurrentMedia() {
        return mList;
    }

    /**
     * 添加需要发送的集合数据
     */
    public void setSendList() {
        if (mFragment != null) {
            mFragment.setSendList();
        }
    }
}
