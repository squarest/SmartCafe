package com.example.clevercafe.analytics.presentation;

import com.arellomobile.mvp.MvpView;
import com.example.clevercafe.entities.Analytics;
import com.example.clevercafe.entities.Ingredient;
import com.example.clevercafe.entities.TopProduct;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

/**
 * Created by Chudofom on 22.08.17.
 */

public interface AnalyticsView extends MvpView {
    void setTodayAnalytics(Analytics todayAnalytics);

    void setDataToChart(ArrayList<Entry> entries, String label);

    void setChartTitle(String title);

    void setPopularProducts(ArrayList<TopProduct> popularProducts);

    void setUnpopularProducts(ArrayList<TopProduct> unpopularProducts);

    void setExpiringIngredient(ArrayList<Ingredient> ingredients);

    void showLoading();

    void hideLoading();
}
