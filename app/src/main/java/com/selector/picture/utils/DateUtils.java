package com.selector.picture.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.selector.picture.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
        return time;
    }

    /**
     * recyclerview滑动时间转换
     *
     * @param context  Context
     * @param duration 时间 毫秒值 59:59
     * @return String
     */
    public static String slideTimeParse(Context context, long duration) {
        String time = "";
        SimpleDateFormat formatYM = new SimpleDateFormat("yyyy/MM");
        long timeOfMonthStart = getTimeOfMonthStart();
        if (duration >= timeOfMonthStart) {
            long timeOfWeekStart = getTimeOfWeekStart();
            if (duration >= timeOfWeekStart) {
                time = context.getString(R.string.picture_selector_slide_week);
            } else {
                time = context.getString(R.string.picture_selector_slide_month);
            }
        } else {
            time = formatYM.format(duration);
        }
        return time;
    }

    /**
     * 获取本周第一天的时间 <code>SUNDAY</code> in the U.S. 所以要加一
     *
     * @return long
     */
    private static long getTimeOfWeekStart() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.clear(Calendar.MINUTE);
        ca.clear(Calendar.SECOND);
        ca.clear(Calendar.MILLISECOND);
        ca.set(Calendar.DAY_OF_WEEK, ca.getFirstDayOfWeek() + 1);
        return ca.getTimeInMillis();
    }

    /**
     * 获取本月第一天的时间
     *
     * @return long
     */
    private static long getTimeOfMonthStart() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.clear(Calendar.MINUTE);
        ca.clear(Calendar.SECOND);
        ca.clear(Calendar.MILLISECOND);
        ca.set(Calendar.DAY_OF_MONTH, 1);
        return ca.getTimeInMillis();
    }

    /**
     * 获取本年第一天的时间
     *
     * @return long
     */
    private static long getTimeOfYearStart() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.clear(Calendar.MINUTE);
        ca.clear(Calendar.SECOND);
        ca.clear(Calendar.MILLISECOND);
        ca.set(Calendar.DAY_OF_YEAR, 1);
        return ca.getTimeInMillis();
    }
}
