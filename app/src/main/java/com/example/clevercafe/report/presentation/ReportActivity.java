package com.example.clevercafe.report.presentation;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.clevercafe.R;
import com.example.clevercafe.base.BaseActivity;
import com.example.clevercafe.databinding.ActivityReportBinding;
import com.example.clevercafe.report.entity.GeneralReportItem;
import com.example.clevercafe.report.entity.ProductReportItem;
import com.example.clevercafe.report.entity.StorageReportItem;
import com.example.clevercafe.utils.ReportUtil;
import com.example.clevercafe.utils.dateTime.DateTimeUtil;

import java.util.ArrayList;
import java.util.Calendar;

public class ReportActivity extends BaseActivity implements ReportView {

    public ActivityReportBinding binding;
    @InjectPresenter
    public ReportPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report);
        createToolbar("Отчеты");
        createDrawer();
        setListeners();
        createSpinners();
    }

    private void setListeners() {
        binding.startPeriod.setOnClickListener(dateClickListener);
        binding.endPeriod.setOnClickListener(dateClickListener);
        binding.reportType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == ReportUtil.PRODUCT_REPORT_TYPE) {
                    binding.periodType.setEnabled(false);
                } else {
                    binding.periodType.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    View.OnClickListener dateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Calendar calendar = Calendar.getInstance();
            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH);
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog mDatePicker = new DatePickerDialog(ReportActivity.this,
                    (datepicker, selectedYear, selectedMonth, selectedDay) -> {
                        calendar.set(Calendar.YEAR, selectedYear);
                        calendar.set(Calendar.MONTH, selectedMonth);
                        calendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                        String dateString = DateTimeUtil.dateToString(calendar.getTime());
                        switch (view.getId()) {
                            case R.id.start_period: {
                                binding.startPeriod.setText(dateString);
                                break;
                            }
                            case R.id.end_period: {
                                binding.endPeriod.setText(dateString);
                                break;
                            }
                        }
                    }, mYear, mMonth, mDay);
            mDatePicker.setTitle("Выберите дату");
            mDatePicker.show();


        }
    };

    private void createSpinners() {
        ArrayAdapter<String> reportTypeAdapter
                = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ReportUtil.getReportTitles());
        ArrayAdapter<String> periodTypeAdapter
                = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ReportUtil.getPeriodTypeTitles());
        binding.reportType.setAdapter(reportTypeAdapter);
        binding.periodType.setAdapter(periodTypeAdapter);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showGeneralReport(ArrayList<GeneralReportItem> rows) {

    }

    @Override
    public void showProductReport(ArrayList<ProductReportItem> rows) {

    }

    @Override
    public void showStorageReport(ArrayList<StorageReportItem> rows) {

    }

}
