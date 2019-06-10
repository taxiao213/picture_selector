package com.selector.picture.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * 将数据库的值查出并赋值
 * Create by Han on 2019/5/20
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class LocalMedia implements Parcelable {

    private String id;//唯一id 标识
    private String path;//路径
    private String compressPath;//压缩路径
    private String cutPath;//剪切路径
    private String displayName;//文件名称，带后缀
    private String title;//文件名称，无后缀
    private String pictureType;//文件类型
    private String addedTime;//文件创建时间
    private String modifiedTime;//文件最后一次修改时间
    private int size;//文件大小(B)
    private int mimeType;//文件类型
    private int width;
    private int height;
    private int position;
    private int num;
    private long duration;
    private boolean isChecked;
    private boolean isCut;
    private boolean compressed;
    private boolean isSelect;//是否选择用于预览界面RecyclerView

    public LocalMedia() {

    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getAddedTime() {
        return addedTime;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public String getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getCompressPath() {
        return compressPath;
    }

    public String getCutPath() {
        return cutPath;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getDuration() {
        return duration;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public boolean isCut() {
        return isCut;
    }

    public int getMimeType() {
        return mimeType;
    }

    public boolean isCompressed() {
        return compressed;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getPictureType() {
        if (TextUtils.isEmpty(pictureType)) {
            pictureType = "image/jpeg";
        }
        return pictureType;
    }

    public LocalMedia(String id, String path, int size, String displayName, String title, String pictureType, int mimeType, int width, int height, String addedTime, String modifiedTime, long duration) {
        this.id = id;
        this.path = path;
        this.size = size;
        this.displayName = displayName;
        this.title = title;
        this.pictureType = pictureType;
        this.mimeType = mimeType;
        this.width = width;
        this.height = height;
        this.addedTime = addedTime;
        this.modifiedTime = modifiedTime;
        this.duration = duration;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    protected LocalMedia(Parcel in) {
        id = in.readString();
        path = in.readString();
        compressPath = in.readString();
        cutPath = in.readString();
        displayName = in.readString();
        title = in.readString();
        pictureType = in.readString();
        addedTime = in.readString();
        modifiedTime = in.readString();
        size = in.readInt();
        mimeType = in.readInt();
        width = in.readInt();
        height = in.readInt();
        position = in.readInt();
        num = in.readInt();
        duration = in.readLong();
        isChecked = in.readByte() != 0;
        isCut = in.readByte() != 0;
        compressed = in.readByte() != 0;
    }

    public static final Creator<LocalMedia> CREATOR = new Creator<LocalMedia>() {
        @Override
        public LocalMedia createFromParcel(Parcel in) {
            return new LocalMedia(in);
        }

        @Override
        public LocalMedia[] newArray(int size) {
            return new LocalMedia[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(path);
        dest.writeString(compressPath);
        dest.writeString(cutPath);
        dest.writeString(displayName);
        dest.writeString(title);
        dest.writeString(pictureType);
        dest.writeString(addedTime);
        dest.writeString(modifiedTime);
        dest.writeInt(size);
        dest.writeInt(mimeType);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeInt(position);
        dest.writeInt(num);
        dest.writeLong(duration);
        dest.writeByte((byte) (isChecked ? 1 : 0));
        dest.writeByte((byte) (isCut ? 1 : 0));
        dest.writeByte((byte) (compressed ? 1 : 0));
    }
}
