package com.example.clevercafe.analytics.presentation;

import com.arellomobile.mvp.MvpView;
import com.example.clevercafe.entities.Analytics;

/**
 * Created by Chudofom on 22.08.17.
 */

public interface AnalyticsView extends MvpView {
    void setTodayAnalytics(Analytics todayAnalytics);

    void showLoading();

    void hideLoading();
}
