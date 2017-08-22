package com.example.clevercafe.data.repositories;

import com.example.clevercafe.data.dao.AnalyticsDao;
import com.example.clevercafe.entities.Analytics;
import com.example.clevercafe.utils.dateTime.Period;

/**
 * Created by Chudofom on 22.08.17.
 */

public class AnalyticsRepository {
    private AnalyticsDao analyticsDao;

    public AnalyticsRepository(AnalyticsDao analyticsDao) {
        this.analyticsDao = analyticsDao;
    }

    public Analytics getAnalyticsForPeriod(Period period) {
        Analytics analytics = new Analytics();
        analytics.proceeds = analyticsDao.proceedsForPeriod(period.startDate, period.endDate);
        analytics.profit = analytics.proceeds -
                analyticsDao.costSumForPeriod(period.startDate, period.endDate);
        analytics.orders = analyticsDao.orderCountForPeriod(period.startDate, period.endDate);
        analytics.averageCheck = analytics.proceeds/analytics.orders;
        return analytics;
    }
}
