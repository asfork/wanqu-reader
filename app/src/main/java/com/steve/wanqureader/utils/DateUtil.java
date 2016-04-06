package com.steve.wanqureader.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by steve on 3/26/16.
 */
public class DateUtil {
    public static String displayTime(String timestampString) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.CHINA);
        String t1 = dateFormat.format(timestamp);
        String t2 = dateFormat.format(System.currentTimeMillis());
        if (t1.equals(t2)) {
            // 今年
            return timeStamp2Date(timestamp, "M-d");
        } else {
            return timeStamp2Date(timestamp, "yyyy-M-d");
        }
    }

    private static String timeStamp2Date(Long timestamp, String formats) {
        return new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
    }
}
