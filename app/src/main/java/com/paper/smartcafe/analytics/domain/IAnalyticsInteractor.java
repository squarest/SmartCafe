package com.paper.smartcafe.analytics.domain;

import com.paper.smartcafe.entities.Analytics;
import com.paper.smartcafe.entities.Ingredient;
import com.paper.smartcafe.entities.TopProduct;
import com.paper.smartcafe.utils.dateTime.Period;

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

    Observable<ArrayList<Ingredient>> loadExpiringIngredients();
}
