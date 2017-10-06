package com.example.clevercafe.report.presentation;

import com.arellomobile.mvp.InjectViewState;
import com.example.clevercafe.App;
import com.example.clevercafe.base.BasePresenter;
import com.example.clevercafe.report.domain.IReportInteractor;
import com.example.clevercafe.utils.ReportUtil;
import com.example.clevercafe.utils.dateTime.Period;

import javax.inject.Inject;

/**
 * Created by Chudofom on 04.10.17.
 */
@InjectViewState
public class ReportPresenter extends BasePresenter<ReportView> {
    private ReportView reportView = getViewState();
    @Inject
    public IReportInteractor interactor;

    public ReportPresenter() {
        App.getReportComponent().inject(this);
    }

    public void submitButtonClicked(int reportType, Period period, int periodType) {
        switch (reportType) {
            case ReportUtil.GENERAL_REPORT_TYPE: {

                break;
            }
            case ReportUtil.PRODUCT_REPORT_TYPE: {

                break;
            }
            case ReportUtil.STORAGE_REPORT_TYPE: {

                break;
            }
        }
    }
}
