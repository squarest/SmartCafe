package com.example.clevercafe.invoice.domain;

import com.example.clevercafe.data.InvoiceRepository;
import com.example.clevercafe.entities.Invoice;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by Chudofom on 24.07.17.
 */

public class InvoiceInteractor implements IInvoiceInteractor {
    public InvoiceRepository invoiceRepository;

    public InvoiceInteractor(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Observable<ArrayList<Invoice>> loadInvoices() {
        return Observable.create(e ->
        {
            ArrayList<Invoice> invoices = invoiceRepository.getInvoices();
            if (invoices != null) e.onNext(invoices);
            else e.onError(new NullPointerException());
        });
    }

    @Override
    public Completable addInvoice(Invoice invoice) {
        return Completable.create(e ->
        {
            invoiceRepository.addInvoice(invoice);
            e.onComplete();
        });
    }

    @Override
    public Completable removeInvoice(Invoice invoice) {
        return Completable.create(e ->
        {
            invoiceRepository.deleteInvoice(invoice);
            e.onComplete();
        });
    }
}
