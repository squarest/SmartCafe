package com.example.clevercafe.report.presentation;

import com.arellomobile.mvp.MvpView;
import com.example.clevercafe.report.entity.GeneralReportItem;
import com.example.clevercafe.report.entity.ProductReportItem;
import com.example.clevercafe.report.entity.StorageReportItem;

import java.util.ArrayList;

/**
 * Created by Chudofom on 04.10.17.
 */

public interface ReportView extends MvpView {
    void showMessage(String message);

    void showLoading();

    void hideLoading();

    void showGeneralReport(ArrayList<GeneralReportItem> rows);

    void showProductReport(ArrayList<ProductReportItem> rows);

    void showStorageReport(ArrayList<StorageReportItem> rows);
}
