package com.paper.smartcafe.analytics.di;

import com.paper.smartcafe.analytics.domain.AnalyticsInteractor;
import com.paper.smartcafe.analytics.domain.IAnalyticsInteractor;
import com.paper.smartcafe.dagger.scopes.AnalyticsScope;
import com.paper.smartcafe.data.repositories.AnalyticsRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chudofom on 22.08.17.
 */
@Module
public class AnalyticsModule {
    @Provides
    @AnalyticsScope
    public IAnalyticsInteractor provideAnalyticsInteractor(AnalyticsRepository analyticsRepository) {
        return new AnalyticsInteractor(analyticsRepository);
    }
}
