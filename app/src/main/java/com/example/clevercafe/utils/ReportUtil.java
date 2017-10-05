package com.example.clevercafe.utils;

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

}