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

    void showGeneralReport(ArrayList<GeneralReportItem> items);

    void showProductReport(ArrayList<ProductReportItem> items);

    void showStorageReport(ArrayList<StorageReportItem> items);
}
