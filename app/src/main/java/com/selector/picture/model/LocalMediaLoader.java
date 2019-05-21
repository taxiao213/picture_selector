package com.selector.picture.model;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.selector.picture.utils.ImageLoadListener;
import com.selector.picture.R;
import com.selector.picture.utils.PicUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Create by Han on 2019/5/20
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class LocalMediaLoader {

    private static LocalMediaLoader mLocalMediaLoader;
    private static final Uri QUERY_URI = MediaStore.Files.getContentUri("external");
    private static final String ORDER_BY = MediaStore.Files.FileColumns._ID + " DESC";
    private static final String DURATION = "duration";
    private static final String NOT_GIF = "!='image/gif'";
    private static final int AUDIO_DURATION = 500;// 过滤掉小于500毫秒的录音
    private long videoMaxS = 0;
    private long videoMinS = 0;

    private LocalMediaLoader() {

    }

    public static LocalMediaLoader getInstances() {
        if (mLocalMediaLoader == null) {
            synchronized (LocalMediaLoader.class) {
                if (mLocalMediaLoader == null) {
                    mLocalMediaLoader = new LocalMediaLoader();
                }
            }
        }
        return mLocalMediaLoader;
    }

    // 媒体文件数据库字段
    private static final String[] PROJECTION = {
            MediaStore.Files.FileColumns._ID,
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.SIZE,
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.MediaColumns.TITLE,
            MediaStore.MediaColumns.MIME_TYPE,
            MediaStore.MediaColumns.WIDTH,
            MediaStore.MediaColumns.HEIGHT,
            DURATION};

    // 图片
    private static final String SELECTION = MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
            + " AND " + MediaStore.MediaColumns.SIZE + ">0";
    // 图片不包含GIF
    private static final String SELECTION_NOT_GIF = MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
            + " AND " + MediaStore.MediaColumns.SIZE + ">0"
            + " AND " + MediaStore.MediaColumns.MIME_TYPE + NOT_GIF;

    // 查询条件(音视频)
    private static String getSelectionArgsForSingleMediaCondition(String time_condition) {
        return MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                + " AND " + MediaStore.MediaColumns.SIZE + ">0"
                + " AND " + time_condition;
    }

    // 全部模式下条件
    private static String getSelectionArgsForAllMediaCondition(String time_condition, boolean isGif) {
        String condition = "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                + (isGif ? "" : " AND " + MediaStore.MediaColumns.MIME_TYPE + NOT_GIF)
                + " OR "
                + (MediaStore.Files.FileColumns.MEDIA_TYPE + "=? AND " + time_condition) + ")"
                + " AND " + MediaStore.MediaColumns.SIZE + ">0";
        return condition;
    }

    /**
     * 获取指定类型的文件
     *
     * @param mediaType
     * @return String[]
     */
    private static String[] getSelectionArgsForSingleMediaType(int mediaType) {
        return new String[]{String.valueOf(mediaType)};
    }

    // 获取图片or视频
    private static final String[] SELECTION_ALL_ARGS = {
            String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE),
            String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO),
    };

    /**
     * 获取视频(最长或最小时间)
     *
     * @param exMaxLimit 最大时间限制
     * @param exMinLimit 最小时间限制
     * @return 语句
     */
    private String getDurationCondition(long exMaxLimit, long exMinLimit) {
        long maxS = videoMaxS == 0 ? Long.MAX_VALUE : videoMaxS;
        if (exMaxLimit != 0) maxS = Math.min(maxS, exMaxLimit);
        return String.format(Locale.CHINA, "%d <%s duration and duration <= %d",
                Math.max(exMinLimit, videoMinS),
                Math.max(exMinLimit, videoMinS) == 0 ? "" : "=",
                maxS);
    }


    public void loadMedia(final FragmentActivity activity, final ImageLoadListener imageLoadListener) {
        LoaderManager manager = activity.getSupportLoaderManager();
        if (manager != null) {
            final int type = PicConfig.getInstances().getImageType();
            manager.initLoader(type, null, new LoaderManager.LoaderCallbacks<Cursor>() {
                @NonNull
                @Override
                public Loader<Cursor> onCreateLoader(int type, @Nullable Bundle bundle) {
                    CursorLoader cursorLoader = null;
                    switch (type) {
                        case MimeType.TYPE_ALL:
                            //获取全部
                            String all_condition = getSelectionArgsForAllMediaCondition(getDurationCondition(0, 0), PicConfig.getInstances().isGif());
                            cursorLoader = new CursorLoader(activity, QUERY_URI, PROJECTION, all_condition, SELECTION_ALL_ARGS, ORDER_BY);
                            break;
                        case MimeType.TYPE_IMAGE:
                            //只获取图片
                            String[] MEDIA_TYPE_IMAGE = getSelectionArgsForSingleMediaType(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE);
                            cursorLoader = new CursorLoader(activity, QUERY_URI, PROJECTION, PicConfig.getInstances().isGif() ? SELECTION : SELECTION_NOT_GIF, MEDIA_TYPE_IMAGE, ORDER_BY);
                            break;
                        case MimeType.TYPE_VIDEO:
                            //只获取视频
                            String video_condition = getSelectionArgsForSingleMediaCondition(getDurationCondition(0, 0));
                            String[] MEDIA_TYPE_VIDEO = getSelectionArgsForSingleMediaType(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO);
                            cursorLoader = new CursorLoader(activity, QUERY_URI, PROJECTION, video_condition, MEDIA_TYPE_VIDEO, ORDER_BY);
                            break;
                        case MimeType.TYPE_AUDIO:
                            //只获取音频
                            String audio_condition = getSelectionArgsForSingleMediaCondition(getDurationCondition(0, AUDIO_DURATION));
                            String[] MEDIA_TYPE_AUDIO = getSelectionArgsForSingleMediaType(MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO);
                            cursorLoader = new CursorLoader(activity, QUERY_URI, PROJECTION, audio_condition, MEDIA_TYPE_AUDIO, ORDER_BY);
                            break;
                    }
                    return cursorLoader;
                }

                @Override
                public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
                    try {
                        List<LocalMediaFolder> imageFolders = new ArrayList<>();
                        LocalMediaFolder allImageFolder = new LocalMediaFolder();
                        List<LocalMedia> latelyImages = new ArrayList<>();
                        if (data != null) {
                            int count = data.getCount();
                            if (count > 0) {
                                data.moveToFirst();
                                do {
                                    String id = data.getString(data.getColumnIndexOrThrow(PROJECTION[0]));//唯一id 标识

                                    String path = data.getString(data.getColumnIndexOrThrow(PROJECTION[1]));//路径

                                    int size = data.getInt(data.getColumnIndexOrThrow(PROJECTION[2]));//文件大小(B)

                                    String displayName = data.getString(data.getColumnIndexOrThrow(PROJECTION[3]));//文件名称，带后缀

                                    String title = data.getString(data.getColumnIndexOrThrow(PROJECTION[4]));//文件名称，无后缀

                                    String pictureType = data.getString(data.getColumnIndexOrThrow(PROJECTION[5]));//文件类型 image/jpeg

                                    int width = data.getInt(data.getColumnIndexOrThrow(PROJECTION[6]));//宽 像素

                                    int height = data.getInt(data.getColumnIndexOrThrow(PROJECTION[7]));//高 像素

                                    int duration = data.getInt(data.getColumnIndexOrThrow(PROJECTION[8]));//时间

                                    LocalMedia image = new LocalMedia(id, path, size, displayName, title, pictureType, type, width, height, duration);

                                    LocalMediaFolder folder = PicUtils.getInstances().getImageFolder(path, imageFolders);
                                    List<LocalMedia> images = folder.getImages();
                                    images.add(image);
                                    folder.setImageNum(folder.getImageNum() + 1);
                                    latelyImages.add(image);
                                    int imageNum = allImageFolder.getImageNum();
                                    allImageFolder.setImageNum(imageNum + 1);
                                } while (data.moveToNext());

                                if (latelyImages.size() > 0) {
                                    PicUtils.getInstances().sortFolder(imageFolders);
                                    imageFolders.add(0, allImageFolder);
                                    allImageFolder.setFirstImagePath(latelyImages.get(0).getPath());
                                    String title = type == MimeType.ofAudio() ? activity.getString(R.string.picture_all_audio) : activity.getString(R.string.picture_camera_roll);
                                    allImageFolder.setName(title);
                                    allImageFolder.setImages(latelyImages);
                                }
                                if (imageLoadListener != null) {
                                    imageLoadListener.loadComplete(imageFolders);
                                }
                            } else {
                                // 如果没有相册
                                if (imageLoadListener != null) {
                                    imageLoadListener.loadComplete(imageFolders);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onLoaderReset(@NonNull Loader<Cursor> loader) {

                }
            });
        }
    }
}
