package com.paper.smartcafe.report.domain;

import com.paper.smartcafe.report.entity.GeneralReportItem;
import com.paper.smartcafe.report.entity.ProductReportItem;
import com.paper.smartcafe.report.entity.StorageReportItem;
import com.paper.smartcafe.utils.dateTime.Period;

import java.util.ArrayList;

import io.reactivex.Single;

/**
 * Created by Chudofom on 05.10.17.
 */

public interface IReportInteractor {
    Single<ArrayList<GeneralReportItem>> loadGeneralReport(Period period, int periodType);

    Single<ArrayList<ProductReportItem>> loadProductReport(Period period);

    Single<ArrayList<StorageReportItem>> loadStorageReport(Period period);
}
