package com.example.clevercafe.utils;

import com.example.clevercafe.utils.dateTime.Period;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Chudofom on 05.10.17.
 */

public class ReportUtil {
    public static final int GENERAL_REPORT_TYPE = 0;
    public static final int PRODUCT_REPORT_TYPE = 1;
    public static final int STORAGE_REPORT_TYPE = 2;

    public static final String[] REPORT_TITLES = {"Общий отчет", "По товарам", "На складе"};

    public static final int DAY_PERIOD_TYPE = 0;
    public static final int WEEK_PERIOD_TYPE = 1;
    public static final int MONTH_PERIOD_TYPE = 2;

    public static String[] getReportTitles() {
        return REPORT_TITLES;
    }

    public static String[] getPeriodTypeTitles() {

        return PERIOD_TYPE_TITLES;
    }

    public static final String[] PERIOD_TYPE_TITLES = {"По дням", "По неделям", "По месяцам"};

    public static ArrayList<Period> getPeriodsForPeriodType(Period period, int periodType) {
        ArrayList<Period> periods = new ArrayList<>();
        Date curDate = period.startDate;
        Date endDate = period.endDate;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        while (curDate.before(endDate)) {
            switch (periodType) {
                //// TODO: 06.10.17 проверять если не хватает дней для того что бы разбить на недели или месяцы
                case ReportUtil.DAY_PERIOD_TYPE: {
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                    periods.add(new Period(curDate, calendar.getTime()));
                    break;
                }
                case ReportUtil.WEEK_PERIOD_TYPE: {
                    calendar.add(Calendar.WEEK_OF_YEAR, 1);
                    periods.add(new Period(curDate, calendar.getTime()));
                    break;
                }
                case ReportUtil.MONTH_PERIOD_TYPE: {
                    calendar.add(Calendar.MONTH, 1);
                    periods.add(new Period(curDate, calendar.getTime()));
                    break;
                }
            }
            curDate = calendar.getTime();
        }
        return periods;
    }

}