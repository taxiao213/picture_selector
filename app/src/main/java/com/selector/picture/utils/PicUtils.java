package com.selector.picture.utils;

import com.selector.picture.model.LocalMediaFolder;
import com.selector.picture.model.PicConfig;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 工具类合集
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
}
