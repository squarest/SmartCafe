package com.paper.smartcafe.analytics.presentation;

import com.arellomobile.mvp.InjectViewState;
import com.paper.smartcafe.App;
import com.paper.smartcafe.analytics.domain.IAnalyticsInteractor;
import com.paper.smartcafe.base.BasePresenter;
import com.paper.smartcafe.utils.chart.ChartUtil;
import com.paper.smartcafe.utils.dateTime.DateTimeUtil;
import com.paper.smartcafe.utils.dateTime.Period;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

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

    private int curChartMode;
    private int curPeriod;
    private ArrayList<Period> curPeriods;

    public AnalyticsPresenter() {
        App.getAnalyticsComponent().inject(this);
    }

    public void viewInit(int periodChecked, int modeChecked) {
        interactor.loadTodayAnalytics()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::setTodayAnalytics, Throwable::printStackTrace);
        setCurPeriods(periodChecked);
        setCurChartMode(modeChecked);
        setChart();
        setProductTops();
    }

    private void setCurPeriods(int periodChecked) {
        curPeriod = periodChecked;
        switch (periodChecked) {
            case DateTimeUtil.WEEK_PERIOD: {
                curPeriods = DateTimeUtil.getWeekPeriods();
                break;
            }
            case DateTimeUtil.MONTH_PERIOD: {
                curPeriods = DateTimeUtil.getMonthPeriods();
                break;
            }
            case DateTimeUtil.YEAR_PERIOD: {
                curPeriods = DateTimeUtil.getYearPeriods();
                break;
            }
        }
    }

    public void curPeriodChanged(int periodId) {
        setCurPeriods(periodId);
        setChart();
    }

    private void setCurChartMode(int curChartMode) {
        this.curChartMode = curChartMode;
    }

    public void curChartModeChanged(int chartModeId) {
        setCurChartMode(chartModeId);
        setChart();
    }

    private void setChart() {
        switch (curChartMode) {
            case ChartUtil.PROCEED_CHART_MODE: {
                interactor.loadProceedsForPeriods(curPeriods)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(values -> {
                            view.setDataToChart(populateChart(values), ChartUtil.getModeName(curChartMode));
                        }, Throwable::printStackTrace);
                break;
            }
            case ChartUtil.PROFIT_CHART_MODE: {
                interactor.loadProfitForPeriods(curPeriods)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(values -> {
                            view.setDataToChart(populateChart(values), ChartUtil.getModeName(curChartMode));
                        }, Throwable::printStackTrace);
                break;
            }
            case ChartUtil.ORDERS_CHART_MODE: {
                interactor.loadOrdersForPeriods(curPeriods)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(values -> {
                            view.setDataToChart(populateChart(values), ChartUtil.getModeName(curChartMode));
                        }, Throwable::printStackTrace);
                break;
            }
            case ChartUtil.AVERAGE_CHART_MODE: {
                interactor.loadAverageForPeriods(curPeriods)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(values -> {
                            view.setDataToChart(populateChart(values), ChartUtil.getModeName(curChartMode));
                        }, Throwable::printStackTrace);
                break;
            }
        }

    }

    private String generateChartTitle() {
        return ChartUtil.getModeName(curChartMode) + " за " +
                DateTimeUtil.getPeriodName(curPeriod) + ": ";
    }

    private ArrayList<Entry> populateChart(ArrayList<Double> values) {
        double sum = 0.0;
        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < curPeriods.size(); i++) {
            entries.add(new Entry(curPeriods.get(i).endDate.getTime(), values.get(i).floatValue()));
            sum += values.get(i);
        }
        view.setChartTitle(String.format("%s %.2f", generateChartTitle(), sum));
        return entries;
    }

    private void setProductTops() {
        interactor.loadPopularProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::setPopularProducts, Throwable::printStackTrace);
        interactor.loadUnpopularProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::setUnpopularProducts, Throwable::printStackTrace);
        interactor.loadExpiringIngredients()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::setExpiringIngredient, Throwable::printStackTrace);
    }
}
