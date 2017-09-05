package com.example.clevercafe.analytics.domain;

import com.example.clevercafe.entities.Analytics;
import com.example.clevercafe.entities.TopProduct;
import com.example.clevercafe.utils.dateTime.Period;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Created by Chudofom on 22.08.17.
 */

public interface IAnalyticsInteractor {
    Observable<Analytics> loadTodayAnalytics();

    Observable<ArrayList<Double>> loadProceedsForPeriods(ArrayList<Period> periods);

    Observable<ArrayList<Double>> loadProfitForPeriods(ArrayList<Period> periods);

    Observable<ArrayList<Double>> loadOrdersForPeriods(ArrayList<Period> periods);

    Observable<ArrayList<Double>> loadAverageForPeriods(ArrayList<Period> periods);

    Observable<ArrayList<TopProduct>> loadPopularProducts();

    Observable<ArrayList<TopProduct>> loadUnpopularProducts();
}
