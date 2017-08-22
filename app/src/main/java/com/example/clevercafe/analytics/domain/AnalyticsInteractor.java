package com.example.clevercafe.analytics.domain;

import com.example.clevercafe.data.repositories.AnalyticsRepository;
import com.example.clevercafe.entities.Analytics;
import com.example.clevercafe.utils.dateTime.DateTimeUtil;

import io.reactivex.Observable;

/**
 * Created by Chudofom on 22.08.17.
 */

public class AnalyticsInteractor implements IAnalyticsInteractor {
    private AnalyticsRepository repository;

    public AnalyticsInteractor(AnalyticsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<Analytics> loadTodayAnalytics() {
        return Observable.create(e -> {
            Analytics analytics = repository.getAnalyticsForPeriod(DateTimeUtil.getTodayPeriod());
            if (analytics != null) {
                e.onNext(analytics);
            } else e.onError(new NullPointerException());
        });
    }
}
