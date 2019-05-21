package com.selector.picture.adapter;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.selector.picture.utils.UIUtils;

/**
 * Create by Han on 2019/5/21
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class GridItemDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildLayoutPosition(view);
        GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
        if (manager != null) {
            int gridSize = manager.getSpanCount();//每列数
            int width = UIUtils.dp2px(view.getContext(), 2.5f);

            if (position % gridSize == 0) {
                outRect.right = width;
                outRect.left = width;
            } else {
                outRect.right = width;
                outRect.left = 0;
            }
            RecyclerView.Adapter adapter = parent.getAdapter();
            if (adapter!=null){
                int count = adapter.getItemCount();//总数
                if (count % gridSize == 0) {
                    if (position >= count - gridSize) {
                        outRect.top = width;
                        outRect.bottom = width;
                    } else {
                        outRect.top = width;
                        outRect.bottom = 0;
                    }
                } else {
                    if (position >= (count / gridSize) * gridSize) {
                        outRect.top = width;
                        outRect.bottom = width;
                    } else {
                        outRect.top = width;
                        outRect.bottom = 0;
                    }
                }
            }
        }
    }
}
