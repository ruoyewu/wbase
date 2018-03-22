package com.wuruoye.library.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wuruoye on 2018/2/14.
 * this file is to
 */

public class DateUtil {
    public static String formatTime(long time, String format) {
        Date date = new Date(time);
        DateFormat f = new SimpleDateFormat(format, Locale.ENGLISH);
        return f.format(date);
    }

    public static String formatCurrent(String format) {
        return formatTime(System.currentTimeMillis(), format);
    }
}
