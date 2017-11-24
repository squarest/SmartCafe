package com.paper.smartcafe.report.presentation;

import com.arellomobile.mvp.InjectViewState;
import com.paper.smartcafe.App;
import com.paper.smartcafe.base.BasePresenter;
import com.paper.smartcafe.report.domain.IReportInteractor;
import com.paper.smartcafe.utils.ReportUtil;
import com.paper.smartcafe.utils.dateTime.Period;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
                Disposable disposable = interactor.loadGeneralReport(period, periodType)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(d -> reportView.showLoading())
                        .doOnSuccess(d -> reportView.hideLoading())
                        .subscribe(generalReportItems -> reportView.showGeneralReport(generalReportItems),
                                throwable -> {
                                    reportView.hideLoading();
                                    reportView.showMessage("Ошибка загрузки данных");
                                    throwable.printStackTrace();
                                });
                setDisposable(disposable);
                break;
            }
            case ReportUtil.PRODUCT_REPORT_TYPE: {
                Disposable disposable = interactor.loadProductReport(period)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(d -> reportView.showLoading())
                        .doOnSuccess(d -> reportView.hideLoading())
                        .subscribe(productReportItems -> reportView.showProductReport(productReportItems),
                                throwable -> {
                                    reportView.hideLoading();
                                    reportView.showMessage("Ошибка загрузки данных");
                                    throwable.printStackTrace();
                                });
                setDisposable(disposable);
                break;
            }
            case ReportUtil.STORAGE_REPORT_TYPE: {
                Disposable disposable = interactor.loadStorageReport(period)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(d -> reportView.showLoading())
                        .doOnSuccess(d -> reportView.hideLoading())
                        .subscribe(storageReportItems -> reportView.showStorageReport(storageReportItems),
                                throwable -> {
                                    reportView.hideLoading();
                                    reportView.showMessage("Ошибка загрузки данных");
                                    throwable.printStackTrace();
                                });
                setDisposable(disposable);
                break;
            }
        }
    }
}
