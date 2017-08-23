package com.example.clevercafe.analytics.domain;

import com.example.clevercafe.data.repositories.AnalyticsRepository;
import com.example.clevercafe.entities.Analytics;
import com.example.clevercafe.utils.dateTime.DateTimeUtil;
import com.example.clevercafe.utils.dateTime.Period;

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
}
