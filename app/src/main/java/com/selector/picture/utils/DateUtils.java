package com.selector.picture.utils;

import java.text.SimpleDateFormat;

/**
 * 时间转换工具类
 * Create by Han on 2019/5/22
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class DateUtils {
    private static SimpleDateFormat msFormat = new SimpleDateFormat("mm:ss");
    private static SimpleDateFormat hmsFormat = new SimpleDateFormat("HH:mm:ss");

    /**
     * MS turn every minute
     *
     * @param duration Millisecond
     * @return Every minute
     */
//    public static String timeParse(long duration) {
//        String time = "";
//        if (duration > 1000) {
//            time = timeParseMinute(duration);
//        } else {
//            long minute = duration / 60000;
//            long seconds = duration % 60000;
//            long second = Math.round((float) seconds / 1000);
//            if (minute < 10) {
//                time += "0";
//            }
//            time += minute + ":";
//            if (second < 10) {
//                time += "0";
//            }
//            time += second;
//        }
//        return time;
//    }

    /**
     * MS turn every minute
     *
     * @param duration Millisecond
     * @return Every minute
     */
    public static String timeParseMinute(long duration) {
        try {
            return msFormat.format(duration);
        } catch (Exception e) {
            e.printStackTrace();
            return "0:00";
        }
    }

    /**
     * 转换
     *
     * @param duration 时间 毫秒值
     * @param type     1 转换成 分钟:秒  2 转换成 小时:分钟:秒
     * @return String
     */
    private static String timeParseMinute(long duration, int type) {
        try {
            if (type == 1) {
                return msFormat.format(duration);
            } else {
                return hmsFormat.format(duration);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "0:00";
        }
    }

    /**
     * 转换时间
     *
     * @param duration 时间 毫秒值
     * @return String
     */
    public static String timeParse(long duration) {
        String time = "";
        if (duration > 60 * 60 * 1000) {
            //转换成时分秒
            time = timeParseMinute(duration, 1);
        } else {
            //转换成时分秒
            time = timeParseMinute(duration, 1);
        }
        return time;
    }

}
