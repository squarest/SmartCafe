package com.paper.smartcafe.invoice.presentation;

import com.arellomobile.mvp.InjectViewState;
import com.paper.smartcafe.App;
import com.paper.smartcafe.base.BasePresenter;
import com.paper.smartcafe.entities.Invoice;
import com.paper.smartcafe.invoice.domain.IInvoiceInteractor;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Chudofom on 20.03.17.
 */
@InjectViewState
public class InvoicePresenter extends BasePresenter<InvoiceView> {
    private ArrayList<Invoice> invoices = new ArrayList<>();
    private InvoiceView view = getViewState();
    @Inject
    public IInvoiceInteractor interactor;

    public InvoicePresenter() {
        App.getInvoiceComponent().inject(this);
    }

    public void viewInit() {
        showInvoices();
    }

    private void showInvoices() {
        interactor.loadInvoices()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(returnedInvoices ->
                {
                    invoices.clear();
                    invoices.addAll(returnedInvoices);
                    getViewState().showInvoices(invoices);
                }, Throwable::printStackTrace);
    }

    public void addInvoice(Invoice invoice) {
        interactor.addInvoice(invoice)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showInvoices, Throwable::printStackTrace);
    }

    public void invoiceDeleted(int invoicePosition) {
        Disposable disposable = interactor.removeInvoice(invoices.get(invoicePosition))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showInvoices, Throwable::printStackTrace);
        setDisposable(disposable);
    }

    public void editInvoiceButClicked(int invoicePosition) {
        view.showEditForm(invoices.get(invoicePosition));
    }
}


