package com.steve.wanqureader.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by steve on 3/26/16.
 */
public class DateUtil {
    public static String displayTime(Long time) {
        long ti = Long.valueOf(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.CHINA);
        String t1 = dateFormat.format(ti);
        String t2 = dateFormat.format(System.currentTimeMillis());
        if (t1.equals(t2)) {
            // 今年
            dateFormat = new SimpleDateFormat("MM-dd", Locale.CHINA);
            return dateFormat.format(ti);
        } else {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            return dateFormat.format(ti);
        }
    }
}
