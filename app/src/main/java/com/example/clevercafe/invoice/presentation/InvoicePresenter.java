package com.example.clevercafe.invoice.presentation;

import com.arellomobile.mvp.InjectViewState;
import com.example.clevercafe.App;
import com.example.clevercafe.base.BasePresenter;
import com.example.clevercafe.entities.Invoice;
import com.example.clevercafe.invoice.domain.IInvoiceInteractor;

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
                .subscribe(view::showInvoices, Throwable::printStackTrace);
    }

    public void addInvoice(Invoice invoice) {
       interactor.addInvoice(invoice)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showInvoices, Throwable::printStackTrace);
    }

    public void deleteInvoice(Invoice invoice) {
        Disposable disposable = interactor.removeInvoice(invoice)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showInvoices, Throwable::printStackTrace);
        setDisposable(disposable);
    }
}


