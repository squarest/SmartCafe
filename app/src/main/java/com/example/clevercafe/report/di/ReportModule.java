package com.example.clevercafe.report.di;

import com.example.clevercafe.dagger.scopes.ReportScope;
import com.example.clevercafe.data.AppDatabase;
import com.example.clevercafe.data.dao.AnalyticsDao;
import com.example.clevercafe.data.dao.ReportDao;
import com.example.clevercafe.data.repositories.ReportRepository;
import com.example.clevercafe.report.domain.IReportInteractor;
import com.example.clevercafe.report.domain.ReportInteractor;

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
    public ReportRepository provideReportRepository(AnalyticsDao analyticsDao, ReportDao reportDao) {
        return new ReportRepository(analyticsDao, reportDao);
    }

    @Provides
    @ReportScope
    public IReportInteractor provideReportInteractor(ReportRepository reportRepository) {
        return new ReportInteractor(reportRepository);
    }
}
