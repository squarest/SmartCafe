package com.example.clevercafe.utils.dateTime;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Chudofom on 22.08.17.
 */

public class DateTimeUtil {
    public static final int WEEK_PERIOD = 0;
    public static final int MONTH_PERIOD = 1;
    public static final int YEAR_PERIOD = 2;

    public static final String WEEK_PERIOD_NAME = "7 дней";
    public static final String MONTH_PERIOD_NAME = "30 дней";
    public static final String YEAR_PERIOD_NAME = "1 год";

    public static final long DAY = 1000 * 60 * 60 * 24;
    public static final long WEEK = DateTimeUtil.DAY * 7;

    public static String getPeriodName(int modeId) {
        switch (modeId) {
            case WEEK_PERIOD: {
                return WEEK_PERIOD_NAME;
            }
            case MONTH_PERIOD: {
                return MONTH_PERIOD_NAME;
            }
            case YEAR_PERIOD: {
                return YEAR_PERIOD_NAME;
            }
        }
        return null;
    }

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

    public static String getCurDayAndMonth() {
        return String.format(new Locale("ru"), "%1$td %1$tB", Calendar.getInstance().getTime());
    }

    public static ArrayList<Period> getWeekPeriods() {
        ArrayList<Period> periods = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -6);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        for (int i = 0; i < 7; i++) {
            Date startDate = new Date(calendar.getTimeInMillis() + DateTimeUtil.DAY * i);
            Date endDate = new Date(calendar.getTimeInMillis() + DateTimeUtil.DAY * (i + 1));
            periods.add(new Period(startDate, endDate));
        }
        return periods;
    }

    public static ArrayList<Period> getMonthPeriods() {
        ArrayList<Period> periods = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -29);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        for (int i = 0; i < 30; i++) {
            Date startDate = new Date(calendar.getTimeInMillis() + DateTimeUtil.DAY * i);
            Date endDate = new Date(calendar.getTimeInMillis() + DateTimeUtil.DAY * (i + 1));
            periods.add(new Period(startDate, endDate));
            Log.d("DEBUG", "start " + dateTimeToString(startDate) + " end " + dateTimeToString(endDate));
        }
        return periods;
    }

    public static ArrayList<Period> getYearPeriods() {
        ArrayList<Period> periods = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH, 1);
            Date startDate = new Date(calendar.getTimeInMillis());
            calendar.add(Calendar.MONTH, 1);
            Date endDate = new Date(calendar.getTimeInMillis());
            calendar.add(Calendar.MONTH, -1);
            periods.add(new Period(startDate, endDate));
            Log.d("DEBUG", "start " + dateTimeToString(startDate) + " end " + dateTimeToString(endDate));
        }
        return periods;
    }
}
