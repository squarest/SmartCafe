package com.example.clevercafe.analytics.presentation;

import com.arellomobile.mvp.InjectViewState;
import com.example.clevercafe.App;
import com.example.clevercafe.analytics.domain.IAnalyticsInteractor;
import com.example.clevercafe.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Chudofom on 22.08.17.
 */
@InjectViewState
public class AnalyticsPresenter extends BasePresenter<AnalyticsView> {
    private AnalyticsView view = getViewState();
    @Inject
    public IAnalyticsInteractor interactor;

    public AnalyticsPresenter() {
        App.getAnalyticsComponent().inject(this);
    }

    public void viewInit() {
        interactor.loadTodayAnalytics()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::setTodayAnalytics, Throwable::printStackTrace);
    }
}
