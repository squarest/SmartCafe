package com.paper.smartcafe.invoice.presentation;

import com.arellomobile.mvp.MvpView;
import com.paper.smartcafe.entities.Invoice;

import java.util.ArrayList;

/**
 * Created by Chudofom on 20.03.17.
 */

public interface InvoiceView extends MvpView{
    void showInvoices(ArrayList <Invoice> invoices);
    void showAddForm();
    void showEditForm(Invoice invoice);
}
