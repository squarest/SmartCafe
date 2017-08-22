package com.example.clevercafe.utils.dateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Chudofom on 22.08.17.
 */

public class DateTimeUtil {
    public static final long DAY = 1000 * 60 * 60 * 24;

    public static String dateToString(Date date) {
        String myFormat = "dd.MM.yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        return sdf.format(date);
    }

    public static String timeToString(Date date) {
        String myFormat = "HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        return sdf.format(date);
    }

    public static String dateTimeToString(Date date) {
        String myFormat = "dd.MM.yyyy HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        return sdf.format(date);
    }

    public static Period getTodayPeriod() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Period(calendar.getTime(),
                new Date(calendar.getTimeInMillis() + DateTimeUtil.DAY));
    }
}
