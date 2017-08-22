package com.example.clevercafe.analytics.presentation;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.clevercafe.R;
import com.example.clevercafe.base.BaseActivity;
import com.example.clevercafe.databinding.ActivityAnalyticsBinding;
import com.example.clevercafe.entities.Analytics;

public class AnalyticsActivity extends BaseActivity implements AnalyticsView {
    private ActivityAnalyticsBinding binding;
    @InjectPresenter
    public AnalyticsPresenter presenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_analytics);
        createToolbar("Состояние предприятия");
        createDrawer();
        presenter.viewInit();
    }

    @Override
    public void setTodayAnalytics(Analytics todayAnalytics) {
        binding.todayAnalytics.setAnalytics(todayAnalytics);
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
