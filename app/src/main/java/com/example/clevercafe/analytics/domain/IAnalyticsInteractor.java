package com.example.clevercafe.analytics.domain;

import com.example.clevercafe.entities.Analytics;

import io.reactivex.Observable;

/**
 * Created by Chudofom on 22.08.17.
 */

public interface IAnalyticsInteractor {
    Observable<Analytics> loadTodayAnalytics();
}
