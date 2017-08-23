package com.example.clevercafe.utils.chart;

/**
 * Created by Chudofom on 23.08.17.
 */

public class ChartUtil {
    public static final int PROCEED_CHART_MODE = 0;
    public static final int PROFIT_CHART_MODE = 1;
    public static final int ORDERS_CHART_MODE = 2;
    public static final int AVERAGE_CHART_MODE = 3;

    public static final String PROCEED_CHART_MODE_NAME = "Выручка";
    public static final String PROFIT_CHART_MODE_NAME = "Прибыль";
    public static final String ORDERS_CHART_MODE_NAME = "Заказы";
    public static final String AVERAGE_CHART_MODE_NAME = "Средний чек";

    public static String getModeName(int modeId) {
        switch (modeId) {
            case PROCEED_CHART_MODE: {
                return PROCEED_CHART_MODE_NAME;
            }
            case PROFIT_CHART_MODE: {
                return PROFIT_CHART_MODE_NAME;
            }
            case ORDERS_CHART_MODE: {
                return ORDERS_CHART_MODE_NAME;
            }
            case AVERAGE_CHART_MODE: {
                return AVERAGE_CHART_MODE_NAME;
            }
        }
        return null;
    }

}
