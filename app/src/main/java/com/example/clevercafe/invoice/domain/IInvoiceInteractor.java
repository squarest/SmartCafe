package com.example.clevercafe.invoice.domain;

import com.example.clevercafe.entities.Invoice;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by Chudofom on 24.07.17.
 */

public interface IInvoiceInteractor {
    Observable<ArrayList<Invoice>> loadInvoices();

    Completable addInvoice(Invoice invoice);

    Completable removeInvoice(Invoice invoice);
}
