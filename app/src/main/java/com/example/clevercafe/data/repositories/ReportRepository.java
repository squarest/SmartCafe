package com.example.clevercafe.data.repositories;

import com.example.clevercafe.data.dao.AnalyticsDao;
import com.example.clevercafe.data.dao.ReportDao;
import com.example.clevercafe.utils.dateTime.Period;

/**
 * Created by Chudofom on 06.10.17.
 */

public class ReportRepository {
    public ReportDao reportDao;
    public AnalyticsDao analyticsDao;

    public ReportRepository(AnalyticsDao analyticsDao, ReportDao reportDao) {
        this.analyticsDao = analyticsDao;
        this.reportDao = reportDao;
    }
    public Double getProceedForPeriod(Period period) {
        return analyticsDao.proceedsForPeriod(period.startDate, period.endDate);
    }

    public Double getProfitForPeriod(Period period) {
        return analyticsDao.proceedsForPeriod(period.startDate, period.endDate) -
                analyticsDao.costSumForPeriod(period.startDate, period.endDate);
    }

    public Integer getOrdersForPeriod(Period period) {
        return analyticsDao.orderCountForPeriod(period.startDate, period.endDate);
    }

    public Double getAverageForPeriod(Period period) {
        return analyticsDao.averageProceedsForPeriod(period.startDate, period.endDate);
    }

}
