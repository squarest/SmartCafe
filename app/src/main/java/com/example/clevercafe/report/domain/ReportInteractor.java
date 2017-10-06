package com.example.clevercafe.report.domain;

import com.example.clevercafe.data.repositories.ReportRepository;
import com.example.clevercafe.report.entity.GeneralReportItem;
import com.example.clevercafe.report.entity.ProductReportItem;
import com.example.clevercafe.report.entity.StorageReportItem;
import com.example.clevercafe.utils.dateTime.Period;

import java.util.ArrayList;

import io.reactivex.Single;

/**
 * Created by Chudofom on 05.10.17.
 */

public class ReportInteractor implements IReportInteractor {
    public ReportRepository repository;

    public ReportInteractor(ReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<ArrayList<GeneralReportItem>> loadGeneralReport(Period period, int periodType) {
        return null;
    }

    @Override
    public Single<ArrayList<ProductReportItem>> loadProductReport(Period period) {
        return null;
    }

    @Override
    public Single<ArrayList<StorageReportItem>> loadStorageReport(Period period, int periodType) {
        return null;
    }
}
