package com.selector.picture.model;

import java.util.EnumSet;
import java.util.Set;

/**
 * Create by Han on 2019/5/20
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class MimeType {
    public final static int TYPE_ALL = 0;//获取全部
    public final static int TYPE_IMAGE = 1;//只获取图片
    public final static int TYPE_VIDEO = 2;//只获取视频
    public final static int TYPE_AUDIO = 3;//只获取音频

    public static int ofAll() {
        return MimeType.TYPE_ALL;
    }

    public static int ofImage() {
        return MimeType.TYPE_IMAGE;
    }

    public static int ofVideo() {
        return MimeType.TYPE_VIDEO;
    }

    public static int ofAudio() {
        return MimeType.TYPE_AUDIO;
    }

    public static int isPictureType(String pictureType) {
        switch (pictureType) {
            case "image/png":
            case "image/PNG":
            case "image/jpeg":
            case "image/JPEG":
            case "image/webp":
            case "image/WEBP":
            case "image/gif":
            case "image/bmp":
            case "image/GIF":
            case "imagex-ms-bmp":
                return MimeType.TYPE_IMAGE;
            case "video/3gp":
            case "video/3gpp":
            case "video/3gpp2":
            case "video/avi":
            case "video/mp4":
            case "video/quicktime":
            case "video/x-msvideo":
            case "video/x-matroska":
            case "video/mpeg":
            case "video/webm":
            case "video/mp2ts":
                return MimeType.TYPE_VIDEO;
            case "audio/mpeg":
            case "audio/x-ms-wma":
            case "audio/x-wav":
            case "audio/amr":
            case "audio/wav":
            case "audio/aac":
            case "audio/mp4":
            case "audio/quicktime":
            case "audio/lamr":
            case "audio/3gpp":
                return MimeType.TYPE_AUDIO;
        }
        return MimeType.TYPE_IMAGE;
    }
}