package com.paper.smartcafe.analytics.domain;

import com.paper.smartcafe.data.repositories.AnalyticsRepository;
import com.paper.smartcafe.entities.Analytics;
import com.paper.smartcafe.entities.Ingredient;
import com.paper.smartcafe.entities.TopProduct;
import com.paper.smartcafe.utils.dateTime.DateTimeUtil;
import com.paper.smartcafe.utils.dateTime.Period;

import java.util.ArrayList;

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

    @Override
    public Observable<ArrayList<Double>> loadProceedsForPeriods(ArrayList<Period> periods) {
        return Observable.create(e -> {
            ArrayList<Double> proceeds = new ArrayList<>();
            for (Period period : periods) {
                Double proceed = repository.getProceedForPeriod(period);
                proceeds.add(proceed);
            }
            if (proceeds.size() > 0) e.onNext(proceeds);
            else e.onError(new NullPointerException());
        });
    }

    @Override
    public Observable<ArrayList<Double>> loadProfitForPeriods(ArrayList<Period> periods) {
        return Observable.create(e -> {
            ArrayList<Double> proceeds = new ArrayList<>();
            for (Period period : periods) {
                Double proceed = repository.getProfitForPeriod(period);
                proceeds.add(proceed);
            }
            if (proceeds.size() > 0) e.onNext(proceeds);
            else e.onError(new NullPointerException());
        });
    }

    @Override
    public Observable<ArrayList<Double>> loadOrdersForPeriods(ArrayList<Period> periods) {
        return Observable.create(e -> {
            ArrayList<Double> proceeds = new ArrayList<>();
            for (Period period : periods) {
                double proceed = (double) repository.getOrdersForPeriod(period);
                proceeds.add(proceed);
            }
            if (proceeds.size() > 0) e.onNext(proceeds);
            else e.onError(new NullPointerException());
        });
    }

    @Override
    public Observable<ArrayList<Double>> loadAverageForPeriods(ArrayList<Period> periods) {
        return Observable.create(e -> {
            ArrayList<Double> proceeds = new ArrayList<>();
            for (Period period : periods) {
                Double proceed = repository.getAverageForPeriod(period);
                proceeds.add(proceed);
            }
            if (proceeds.size() > 0) e.onNext(proceeds);
            else e.onError(new NullPointerException());
        });
    }

    @Override
    public Observable<ArrayList<TopProduct>> loadPopularProducts() {
        return Observable.create(e -> {
            ArrayList<TopProduct> topProducts = repository.getPopularProducts();
            if (topProducts != null) e.onNext(topProducts);
            else e.onError(new NullPointerException());
        });
    }

    @Override
    public Observable<ArrayList<TopProduct>> loadUnpopularProducts() {
        return Observable.create(e -> {
            ArrayList<TopProduct> topProducts = repository.getUnpopularProducts();
            if (topProducts != null) e.onNext(topProducts);
            else e.onError(new NullPointerException());
        });
    }

    @Override
    public Observable<ArrayList<Ingredient>> loadExpiringIngredients() {
        return Observable.create(e -> {
            ArrayList list = repository.getExpiringIngredients();
            if (list != null) e.onNext(list);
            else e.onError(new NullPointerException());
        });
    }
}
