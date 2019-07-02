package com.selector.picture.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.selector.picture.constant.Constant;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 图片压缩工具类
 * Created by han on 2019/6/13
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class CompressPicUtil {

    private static String path = null;//压缩文件路径
    private static float MIN_WIDTH = 480f;//最小宽度
    private static float MIN_HEIGHT = 480f;//最小高度
    private static float MAX_WIDTH = 1080f;//最大宽度
    private static float MAX_HEIGHT = 1920f;//最大高度
    private static int QUALITY_COMPRESSION = 400;//质量压缩大小

    /**
     * @param strpath 图片路径
     */
    public static String getCompToRealPath(String strpath) {
        if (TextUtils.isEmpty(strpath)) {
        } else if (strpath.endsWith("gif")) {
            path = strpath;
        } else {
            try {
                path = Constant.FILE_COMPRESS_NAME;
                if (strpath.contains("/")) {
                    String imagePath = strpath.substring(strpath.lastIndexOf("/") + 1);
                    Bitmap bt = getImage(strpath);
                    int degree = readPictureDegree(strpath);
                    Bitmap rotaingBt = rotaingImageView(degree, bt);
                    path = saveFile(rotaingBt, path, imagePath);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return path;
    }

    /**
     * 图片按比例大小压缩方法
     *
     * @param srcPath 图片路径
     * @return Bitmap
     */
    public static Bitmap getImage(String srcPath) {
        Bitmap src = BitmapFactory.decodeFile(srcPath);
        if (src == null) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        int width = options.outWidth;
        int height = options.outHeight;
        float ww = 0f;
        float hh = 0f;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        if (width >= MAX_WIDTH && height >= MAX_HEIGHT) {
            ww = MAX_WIDTH;
            hh = MAX_HEIGHT;
        } else if (width >= MIN_WIDTH && height >= MIN_HEIGHT) {
            ww = MIN_WIDTH;// 这里设置宽度为480f
            hh = MIN_HEIGHT;// 这里设置高度为800f---960*540
        }
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (isNotLongPic(width, height)) {
            if (width > height && width > ww) {// 如果宽度大的话根据宽度固定大小缩放
                be = (int) (width / ww);
            } else if (width < height && height > hh) {// 如果高度高的话根据宽度固定大小缩放
                be = (int) (height / hh);
            }
        }
        if (be <= 0)
            be = 1;
        options.inSampleSize = be;// 设置缩放比例
        options.inPreferredConfig = Bitmap.Config.RGB_565;//降低图片从ARGB888到RGB565
        options.inJustDecodeBounds = false;
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        if (!src.isRecycled()) src.recycle();
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 是否长图片
     *
     * @param width  图片宽度
     * @param height 图片高度
     * @return true 不是长图 false长图
     */
    private static boolean isNotLongPic(int width, int height) {
        return height < (width * 3) && width < (height * 3);
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    private static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (image != null) {
            image.compress(Bitmap.CompressFormat.PNG, 100, baos);//质量压缩方法，这里100表示不压缩(代表的是压缩率)，把压缩后的数据存放到baos中
        }
        byte[] bytes = baos.toByteArray();
        int options = 100;
        while (bytes.length / 1024 > QUALITY_COMPRESSION) {//循环判断如果压缩后图片是否大于400kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            if (options <= 0) {
                break;
            }
            if (image != null) {
                image.compress(Bitmap.CompressFormat.PNG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            }
            bytes = baos.toByteArray();
        }
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

    }


    /**
     * 将Bitmap转换成文件
     * 保存文件
     *
     * @param src      源文件
     * @param path     文件路径
     * @param fileName 文件名称
     */
    public static String saveFile(Bitmap src, String path, String fileName) {
        if (src == null) return "";
        BufferedOutputStream bos = null;
        File myCaptureFile = null;
        try {
            File dirFile = new File(path);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            myCaptureFile = new File(path, fileName);
            if (!myCaptureFile.exists()) {
                myCaptureFile.createNewFile();
            }
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            src.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            if (!src.isRecycled()) src.recycle();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return myCaptureFile == null ? "" : myCaptureFile.getAbsolutePath();
    }


    /**
     * 读取照片旋转角度
     *
     * @param path 照片路径
     * @return 角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片
     *
     * @param angle  被旋转角度
     * @param bitmap 图片对象
     * @return 旋转后的图片
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        if (bitmap == null) return null;
        Bitmap returnBm = null;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (Exception e) {
        }
        if (returnBm == null) {
            returnBm = bitmap;
        }
        if (bitmap != returnBm) {
            bitmap.recycle();
        }
        return returnBm;
    }

    /**
     * 根据指定的图像路径和大小来获取缩略图
     * 此方法有两点好处：
     * 1. 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
     * 第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。
     * 2. 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使
     * 用这个工具生成的图像不会被拉伸。
     *
     * @param id      图像的路径thumbnails表  image_id
     * @param context Context 上下文
     * @return 查找的的缩略图路径
     */
    public static String getImageThumbnail(Context context, String id) {
        String path = null;
        ContentResolver cr = context.getContentResolver();
        //先得到缩略图的URL和对应的图片id
        Cursor cursor = cr.query(
                MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Thumbnails.IMAGE_ID, MediaStore.Images.Thumbnails.DATA},
                MediaStore.Images.Thumbnails.IMAGE_ID + "=" + id,
                null,
                null);
        if (cursor.moveToFirst()) {
            do {
                path = cursor.getString(1);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return path;
    }
}
