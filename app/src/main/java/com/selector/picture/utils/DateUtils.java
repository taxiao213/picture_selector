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
    private static int HOURS_TIME = 60 * 60 * 1000;//1小时的毫秒值

    /**
     * 转换成 小时:分钟:秒
     *
     * @param duration 时间 毫秒值 转换成 分钟:秒
     * @return String
     */
    private static String timeParseHours(long duration) {
        String hours = "";
        int min = (int) (duration / HOURS_TIME);
        if (min == 0) {
            hours = "01:00:00";
        } else if (min > 0 && min <= 9) {
            hours = "0" + min + ":" + timeParseMinute(duration - min * HOURS_TIME);
        } else {
            hours = min + ":" + timeParseMinute(duration - min * HOURS_TIME);
        }
        return hours;
    }

    /**
     * 转换成 分钟:秒
     *
     * @param duration 时间 毫秒值
     * @return String
     */
    private static String timeParseMinute(long duration) {
        try {
            return msFormat.format(duration);
        } catch (Exception e) {
            e.printStackTrace();
            return "0:00";
        }
    }

    /**
     * 转换时间
     *
     * @param duration 时间 毫秒值 59:59
     * @return String
     */
    public static String timeParse(long duration) {
        String time = "";
        if (duration >= (HOURS_TIME - 100)) {
            //转换成时分秒
            time = timeParseHours(duration);
        } else {
            //转换成分秒
            time = timeParseMinute(duration);
        }
        System.out.println(time);
        return time;
    }
}
