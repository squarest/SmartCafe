package com.example.clevercafe.report.domain;

import com.example.clevercafe.report.entity.GeneralReportItem;
import com.example.clevercafe.report.entity.ProductReportItem;
import com.example.clevercafe.report.entity.StorageReportItem;
import com.example.clevercafe.utils.dateTime.Period;

import java.util.ArrayList;

import io.reactivex.Single;

/**
 * Created by Chudofom on 05.10.17.
 */

public interface IReportInteractor {
    Single<ArrayList<GeneralReportItem>> loadGeneralReport(Period period, int periodType);

    Single<ArrayList<ProductReportItem>> loadProductReport(Period period);

    Single<ArrayList<StorageReportItem>> loadStorageReport(Period period, int periodType);
}
