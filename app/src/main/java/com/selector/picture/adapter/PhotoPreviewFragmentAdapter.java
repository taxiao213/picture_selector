package com.selector.picture.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.selector.picture.fragment.PhotoFragment;
import com.selector.picture.model.LocalMedia;

import java.util.ArrayList;

/**
 * 图片预览adapter
 * Create by Han on 2019/6/4
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoPreviewFragmentAdapter extends FragmentStatePagerAdapter {
    private ArrayList<LocalMedia> mList;

    public PhotoPreviewFragmentAdapter(FragmentManager fm, ArrayList<LocalMedia> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public Fragment getItem(int i) {
        return PhotoFragment.newInstances(mList.get(i));
    }

    @Override
    public int getCount() {
        return mList.size();
    }


}
