package com.selector.picture.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.selector.picture.GlideApp;
import com.selector.picture.R;
import com.selector.picture.constant.Constant;
import com.selector.picture.model.LocalMediaFolder;
import com.selector.picture.model.PicConfig;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 图片加载工具类
 * Create by Han on 2019/5/21
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PicUtils {

    private static PicUtils mPicUtils;

    public static PicUtils getInstances() {
        if (mPicUtils == null) {
            synchronized (PicUtils.class) {
                if (mPicUtils == null) {
                    mPicUtils = new PicUtils();
                }
            }
        }
        return mPicUtils;
    }

    /**
     * 创建相应文件夹
     *
     * @param path         当前文件子路径
     * @param imageFolders 包含子路经的文件夹
     * @return LocalMediaFolder
     */
    public LocalMediaFolder getImageFolder(String path, List<LocalMediaFolder> imageFolders) {
        File imageFile = new File(path);
        File folderFile = imageFile.getParentFile();
        for (LocalMediaFolder folder : imageFolders) {
            // 同一个文件夹下，返回自己，否则创建新文件夹
            if (folder != null) {
                if (folder.getName().equals(folderFile.getName())) {
                    return folder;
                }
            }
        }
        LocalMediaFolder newFolder = new LocalMediaFolder();
        newFolder.setName(folderFile.getName());
        newFolder.setPath(folderFile.getAbsolutePath());
        newFolder.setFirstImagePath(path);
        imageFolders.add(newFolder);
        return newFolder;
    }

    /**
     * 文件夹数量进行排序
     *
     * @param imageFolders List<LocalMediaFolder>
     */
    public void sortFolder(List<LocalMediaFolder> imageFolders) {
        // 文件夹按图片数量排序
        Collections.sort(imageFolders, new Comparator<LocalMediaFolder>() {
            @Override
            public int compare(LocalMediaFolder lhs, LocalMediaFolder rhs) {
                if (lhs.getImages() == null || rhs.getImages() == null) {
                    return 0;
                }
                int lsize = lhs.getImageNum();
                int rsize = rhs.getImageNum();
                return lsize == rsize ? 0 : (lsize < rsize ? 1 : -1);
            }
        });
    }

    /**
     * 加载图片
     *
     * @param context   Context
     * @param imageView ImageView
     * @param file      文件路径
     */
    public void loadImage(Context context, ImageView imageView, String file) {

        PicConfig instances = PicConfig.getInstances();
        int overrideWidth = instances.getOverrideWidth();//获取压缩宽度
        int overrideHeight = instances.getOverrideHeight();//获取压缩高度
        float multiplier = instances.getMultiplier();//Glide压缩资源系数
        RequestOptions options = new RequestOptions();
        if (overrideWidth <= 0 || overrideHeight <= 0) {
            options.sizeMultiplier(multiplier);
        } else {
            options.override(overrideWidth, overrideHeight);
        }
        GlideApp.with(context)
                .asBitmap()
                .load(file)
                .centerCrop()
                .apply(options)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(imageView);
    }

    /**
     * 加载预览底部图片
     *
     * @param context   Context
     * @param imageView ImageView
     * @param file      文件路径
     */
    public void loadPreviewImage(Context context, ImageView imageView, String file) {
        if (context == null) return;
        GlideApp.with(context)
                .asBitmap()
                .load(file)
                .centerCrop()
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(imageView);
    }

    /**
     * 加载图片
     *
     * @param context   Context
     * @param imageView ImageView
     * @param file      文件路径
     */
    public void loadPreviewPhoto(Context context, ImageView imageView, String file) {
        if (context == null) return;
        GlideApp.with(context)
                .load(file)
                .centerCrop()
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(imageView);
    }
}
