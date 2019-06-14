package com.selector.picture.model;

import java.util.List;

/**
 * 相册选择数据保存
 * Create by Han on 2019/6/14
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PicList {
    private static PicList mPicList;
    private List<LocalMedia> list;//当前选择相册数据的集合
    private List<LocalMedia> sendMedia;//发送和预览的集合

    public static PicList getInstances() {
        if (mPicList == null) {
            synchronized (PicConfig.class) {
                if (mPicList == null) {
                    mPicList = new PicList();
                }
            }
        }
        return mPicList;
    }

    /**
     * 设置当前选择相册数据的集合
     *
     * @param localMedia List<LocalMedia>
     */
    public void setCurrentList(List<LocalMedia> localMedia) {
        this.list = localMedia;
    }

    /**
     * 获取当前选择相册数据的集合
     *
     * @return List<LocalMedia>
     */
    public List<LocalMedia> getCurrentList() {
        return list;
    }


    /**
     * 设置发送和预览的集合
     *
     * @param localMedia List<LocalMedia>
     */
    public void setSendList(List<LocalMedia> localMedia) {
        this.sendMedia = localMedia;
    }

    /**
     * 获取发送和预览的集合
     *
     * @return List<LocalMedia>
     */
    public List<LocalMedia> getSendList() {
        return sendMedia;
    }

    /**
     * 还原配置
     */
    public void restoreConfig() {
        list = null;//当前选择相册数据的集合
        sendMedia = null;//发送和预览的集合
    }
}
