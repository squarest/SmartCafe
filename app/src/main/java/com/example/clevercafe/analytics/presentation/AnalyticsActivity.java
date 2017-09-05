package com.example.clevercafe.analytics.presentation;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.clevercafe.R;
import com.example.clevercafe.base.BaseActivity;
import com.example.clevercafe.databinding.ActivityAnalyticsBinding;
import com.example.clevercafe.entities.Analytics;
import com.example.clevercafe.entities.TopProduct;
import com.example.clevercafe.utils.chart.DayFormatter;
import com.example.clevercafe.utils.chart.YearFormatter;
import com.example.clevercafe.utils.dateTime.DateTimeUtil;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import java.util.ArrayList;

public class AnalyticsActivity extends BaseActivity implements AnalyticsView {
    private ActivityAnalyticsBinding binding;
    @InjectPresenter
    public AnalyticsPresenter presenter;
    private ProgressDialog progressDialog;
    private LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_analytics);
        createToolbar("Состояние предприятия");
        createDrawer();
        setChart();
        binding.chartCard.periodSwitch.setOnPositionChangedListener(presenter::curPeriodChanged);
        binding.chartCard.chartModeSwitch.setOnPositionChangedListener(presenter::curChartModeChanged);
        binding.todayAnalytics.currentDate.setText("Сегодня \n" + DateTimeUtil.getCurDayAndMonth());
        presenter.viewInit(binding.chartCard.periodSwitch.getPosition(),
                binding.chartCard.chartModeSwitch.getPosition());
    }

    @Override
    public void setTodayAnalytics(Analytics todayAnalytics) {
        binding.todayAnalytics.setAnalytics(todayAnalytics);
    }

    private void setChart() {
        chart = binding.chartCard.chart;
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setDrawGridLines(false);
        chart.getAxisLeft().setValueFormatter(new LargeValueFormatter());
        chart.getAxisRight().setValueFormatter(new LargeValueFormatter());

    }

    @Override
    public void setDataToChart(ArrayList<Entry> entries) {
        switch (binding.chartCard.periodSwitch.getPosition()) {
            case DateTimeUtil.WEEK_PERIOD: {

                chart.getXAxis().setValueFormatter(new DayFormatter());
                chart.getXAxis().setLabelCount(entries.size(), true);
                break;
            }
            case DateTimeUtil.MONTH_PERIOD: {
                chart.getXAxis().setValueFormatter(new DayFormatter());
                chart.getXAxis().setLabelCount(entries.size() / 3, true);
                break;
            }
            case DateTimeUtil.YEAR_PERIOD: {
                chart.getXAxis().setValueFormatter(new YearFormatter());
                chart.getXAxis().setLabelCount(entries.size(), true);
                break;
            }
        }
        LineDataSet lineDataSet = new LineDataSet(entries, "label");
        lineDataSet.setColor(getResources().getColor(R.color.darkBlue));
        lineDataSet.setCircleColor(getResources().getColor(R.color.purple));
        lineDataSet.setValueFormatter(new LargeValueFormatter());
        chart.setData(new LineData(lineDataSet));
        chart.animateY(500, Easing.EasingOption.Linear);
        chart.invalidate();
    }

    @Override
    public void setChartTitle(String title) {
        binding.chartCard.chartTitle.setText(title);
    }

    @Override
    public void setPopularProducts(ArrayList<TopProduct> popularProducts) {
        binding.popularProducts.setLayoutManager(new LinearLayoutManager(this));
        binding.popularProducts.setAdapter(new TopProductsAdapter(popularProducts));
    }

    @Override
    public void setUnpopularProducts(ArrayList<TopProduct> unpopularProducts) {
        binding.unpopularProducts.setLayoutManager(new LinearLayoutManager(this));
        binding.unpopularProducts.setAdapter(new TopProductsAdapter(unpopularProducts));
    }


    @Override
    public void showLoading() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Идет загрузка");
        progressDialog.setMessage("Пожалуйста подождите");
        progressDialog.show();

    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }
}
