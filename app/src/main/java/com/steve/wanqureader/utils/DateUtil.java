package com.steve.wanqureader.utils;

import com.steve.wanqureader.R;
import com.steve.wanqureader.WanquApplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by steve on 3/26/16.
 */
public class DateUtil {

    /**
     * 1s==1000ms
     */
    private final static int TIME_MILLISECONDS = 1000;
    /**
     * 时间中的分、秒最大值均为60
     */
    private final static int TIME_NUMBERS = 60;
    /**
     * 时间中的小时最大值
     */
    private final static int TIME_HOURSES = 24;

    private final static int TIME_DAYS = 7;

    public static String formatTitleDate(String timestampString) {
        Long timestamp = Long.parseLong(timestampString + "000");
        return parseDate(timestamp, WanquApplication.getContext().getString(R.string.date_year));
    }

    public static String formatDateTime(String timestampString) {
        String text;
        long dateTime = Long.parseLong(timestampString + "000");
//        Log.d("DateUtil", dateTime + "");
        Calendar calendar = GregorianCalendar.getInstance();
//        Log.d("DateUtil", calendar.getTimeInMillis() + "");

        if (isSameDay(dateTime)) {
            if (inOneMinute(dateTime, calendar.getTimeInMillis())) {
                return WanquApplication.getContext().getString(R.string.date_just_now);
            } else if (inOneHour(dateTime, calendar.getTimeInMillis())) {
                return String.format(Locale.getDefault(),
                        WanquApplication.getContext().getString(R.string.date_minute),
                        Math.abs(dateTime - calendar.getTimeInMillis())
                                / (TIME_NUMBERS * TIME_MILLISECONDS));
            } else {
//                calendar.setTimeInMillis(dateTime);
//                int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
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
                        Math.abs(dateTime - calendar.getTimeInMillis())
                                / (TIME_NUMBERS * TIME_NUMBERS * TIME_MILLISECONDS));
            }

        } else if (isYesterday(dateTime)) {
            return WanquApplication.getContext().getString(R.string.date_yesterday);

        } else if (isOneWeek(dateTime, calendar.getTimeInMillis())) {
            return String.format(Locale.getDefault(),
                    WanquApplication.getContext().getString(R.string.date_day),
                    Math.abs(dateTime - calendar.getTimeInMillis())
                            / (TIME_HOURSES * TIME_NUMBERS * TIME_NUMBERS * TIME_MILLISECONDS));

        } else if (isSameYear(dateTime)) {
            text = WanquApplication.getContext().getString(R.string.date_month);
        } else {
            text = WanquApplication.getContext().getString(R.string.date_year);
        }

        // 注意，如果使用android.text.format.DateFormat这个工具类，在API 17之前它只支持adEhkMmszy
        return new SimpleDateFormat(text, Locale.getDefault()).format(dateTime);
    }

    private static boolean inOneMinute(long time1, long time2) {
        return Math.abs(time1 - time2) < TIME_NUMBERS * TIME_MILLISECONDS;
    }

    private static boolean inOneHour(long time1, long time2) {
        return Math.abs(time1 - time2) < TIME_NUMBERS * TIME_NUMBERS * TIME_MILLISECONDS;
    }

    private static boolean isSameDay(long time) {
        long startTime = floorDay(Calendar.getInstance()).getTimeInMillis();
//        long endTime = ceilDay(Calendar.getInstance()).getTimeInMillis();
        return time > startTime;
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
        return Math.abs(time1 - time2) <
                TIME_DAYS * TIME_HOURSES * TIME_NUMBERS * TIME_NUMBERS * TIME_MILLISECONDS;
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

    private static String parseDate(Long date, String formats) {
        String timezone = "GMT+08" ;

        SimpleDateFormat df = new SimpleDateFormat(formats, Locale.getDefault());
        df.setTimeZone(TimeZone.getTimeZone(timezone));

        return df.format(new Date(date));
    }
}
