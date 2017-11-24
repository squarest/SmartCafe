package com.paper.smartcafe.report.di;

import com.paper.smartcafe.dagger.scopes.ReportScope;
import com.paper.smartcafe.data.AppDatabase;
import com.paper.smartcafe.data.dao.AnalyticsDao;
import com.paper.smartcafe.data.dao.DatabaseDao;
import com.paper.smartcafe.data.dao.ReportDao;
import com.paper.smartcafe.data.repositories.ReportRepository;
import com.paper.smartcafe.report.domain.IReportInteractor;
import com.paper.smartcafe.report.domain.ReportInteractor;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chudofom on 06.10.17.
 */
@Module
public class ReportModule {
    @Provides
    @ReportScope
    public ReportDao provideReportDao(AppDatabase database) {
        return database.reportDao();
    }

    @Provides
    @ReportScope
    public ReportRepository provideReportRepository(AnalyticsDao analyticsDao, ReportDao reportDao, DatabaseDao databaseDao) {
        return new ReportRepository(analyticsDao, reportDao, databaseDao);
    }

    @Provides
    @ReportScope
    public IReportInteractor provideReportInteractor(ReportRepository reportRepository) {
        return new ReportInteractor(reportRepository);
    }
}
