package com.steve.wanqureader.utils;

import com.steve.wanqureader.R;
import com.steve.wanqureader.WanquApplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by steve on 3/26/16.
 */
public class DateUtil {
    public static String formatTitleDate(String timestampString) {
        Long timestamp = Long.parseLong(timestampString);
        return timeStamp2Date(timestamp, WanquApplication.getContext().getString(R.string.date_year));
    }

    public static String formatDateTime(String timestampString) {
        String text;
        long dateTime = Long.parseLong(timestampString);
        Calendar calendar = GregorianCalendar.getInstance();
        if (isSameDay(dateTime)) {
            if (inOneMinute(dateTime, calendar.getTimeInMillis())) {
                return WanquApplication.getContext().getString(R.string.date_just_now);
            } else if (inOneHour(dateTime, calendar.getTimeInMillis())) {
                return String.format(Locale.getDefault(),
                        WanquApplication.getContext().getString(R.string.date_minute),
                        Math.abs(dateTime - calendar.getTimeInMillis()) / (60 * 1000));
            } else {
                calendar.setTimeInMillis(dateTime);
                int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
//                if (hourOfDay > 17) {
//                    text = "晚上 hh:mm";
//                } else if (hourOfDay >= 0 && hourOfDay <= 6) {
//                    text = "凌晨 hh:mm";
//                } else if (hourOfDay > 11 && hourOfDay <= 17) {
//                    text = "下午 hh:mm";
//                } else {
//                    text = "上午 hh:mm";
//                }
                return String.format(Locale.getDefault(),
                        WanquApplication.getContext().getString(R.string.date_hour),
                        Math.abs(dateTime - calendar.getTimeInMillis()) / (60 * 60 * 1000));
            }
        } else if (isYesterday(dateTime)) {
            return WanquApplication.getContext().getString(R.string.date_yesterday);
        } else if (isOneWeek(dateTime, calendar.getTimeInMillis())) {
            return String.format(Locale.getDefault(),
                    WanquApplication.getContext().getString(R.string.date_day),
                    Math.abs(dateTime - calendar.getTimeInMillis()) / (24 * 60 * 60 * 1000));
        } else if (isSameYear(dateTime)) {
            text = WanquApplication.getContext().getString(R.string.date_month);
        } else {
            text = WanquApplication.getContext().getString(R.string.date_year);
        }

        // 注意，如果使用android.text.format.DateFormat这个工具类，在API 17之前它只支持adEhkMmszy
        return new SimpleDateFormat(text, Locale.getDefault()).format(dateTime);
    }

    private static boolean inOneMinute(long time1, long time2) {
        return Math.abs(time1 - time2) < 60 * 1000;
    }

    private static boolean inOneHour(long time1, long time2) {
        return Math.abs(time1 - time2) < 60 * 60 * 1000;
    }

    private static boolean isSameDay(long time) {
        long startTime = floorDay(Calendar.getInstance()).getTimeInMillis();
        long endTime = ceilDay(Calendar.getInstance()).getTimeInMillis();
        return time > startTime && time < endTime;
    }

    private static boolean isYesterday(long time) {
        Calendar startCal;
        startCal = floorDay(Calendar.getInstance());
        startCal.add(Calendar.DAY_OF_MONTH, -1);
        long startTime = startCal.getTimeInMillis();

        Calendar endCal;
        endCal = ceilDay(Calendar.getInstance());
        endCal.add(Calendar.DAY_OF_MONTH, -1);
        long endTime = endCal.getTimeInMillis();
        return time > startTime && time < endTime;
    }

    private static boolean isOneWeek(long time1, long time2) {
        return Math.abs(time1 - time2) < 7 * 24 * 60 * 60 * 1000;
    }

    private static boolean isSameYear(long time) {
        Calendar startCal;
        startCal = floorDay(Calendar.getInstance());
        startCal.set(Calendar.MONTH, Calendar.JANUARY);
        startCal.set(Calendar.DAY_OF_MONTH, 1);
        return time >= startCal.getTimeInMillis();
    }

    private static Calendar floorDay(Calendar startCal) {
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);
        return startCal;
    }

    private static Calendar ceilDay(Calendar endCal) {
        endCal.set(Calendar.HOUR_OF_DAY, 23);
        endCal.set(Calendar.MINUTE, 59);
        endCal.set(Calendar.SECOND, 59);
        endCal.set(Calendar.MILLISECOND, 999);
        return endCal;
    }

    private static String timeStamp2Date(Long timestamp, String formats) {
        return new SimpleDateFormat(formats, Locale.getDefault()).format(new Date(timestamp));
    }
}
