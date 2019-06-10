package com.selector.picture.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.selector.picture.R;
import com.selector.picture.activity.PhotoPreviewsActivity;
import com.selector.picture.activity.PhotoSelectActivity;
import com.selector.picture.fragment.PhotoPreviewsFragment;
import com.selector.picture.fragment.PhotoSelectFragment;
import com.selector.picture.model.LocalMedia;
import com.selector.picture.utils.OnItemClickListener;

import java.util.List;


/**
 * 预览界面底部图片滑动加载Adapter
 * Create by Han on 2019/5/21
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoPreviewAdapter extends RecyclerView.Adapter<PhotoPreviewViewHolder> {

    private PhotoPreviewsActivity mContext;
    private List<LocalMedia> mList;
    private OnItemClickListener<LocalMedia> mOnItemClickListener;
    private PhotoPreviewsFragment mFragment;

    public PhotoPreviewAdapter(PhotoPreviewsActivity context, PhotoPreviewsFragment fragment, OnItemClickListener<LocalMedia> onItemClickListener, List<LocalMedia> list) {
        this.mContext = context;
        this.mFragment = fragment;
        this.mOnItemClickListener = onItemClickListener;
        this.mList = list;
    }

    @NonNull
    @Override
    public PhotoPreviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_photo_preview_pic, viewGroup, false);
        return new PhotoPreviewViewHolder(view, PhotoPreviewAdapter.this, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoPreviewViewHolder viewHolder, int position) {
        viewHolder.bindViewHolder(mContext, mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
