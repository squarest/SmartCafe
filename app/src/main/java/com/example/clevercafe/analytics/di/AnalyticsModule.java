package com.example.clevercafe.analytics.di;

import com.example.clevercafe.analytics.domain.AnalyticsInteractor;
import com.example.clevercafe.analytics.domain.IAnalyticsInteractor;
import com.example.clevercafe.dagger.scopes.AnalyticsScope;
import com.example.clevercafe.data.AppDatabase;
import com.example.clevercafe.data.dao.AnalyticsDao;
import com.example.clevercafe.data.dao.DatabaseDao;
import com.example.clevercafe.data.repositories.AnalyticsRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chudofom on 22.08.17.
 */
@Module
public class AnalyticsModule {

    @Provides
    @AnalyticsScope
    public AnalyticsDao provideAnalyticsDao(AppDatabase database) {
        return database.analyticsDao();
    }

    @Provides
    @AnalyticsScope
    public AnalyticsRepository provideAnalyticsRepository(AnalyticsDao analyticsDao, DatabaseDao databaseDao) {
        return new AnalyticsRepository(analyticsDao, databaseDao);
    }

    @Provides
    @AnalyticsScope
    public IAnalyticsInteractor provideAnalyticsInteractor(AnalyticsRepository analyticsRepository) {
        return new AnalyticsInteractor(analyticsRepository);
    }
}
