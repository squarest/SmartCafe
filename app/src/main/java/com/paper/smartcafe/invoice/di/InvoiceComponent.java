package com.paper.smartcafe.invoice.di;

import com.paper.smartcafe.dagger.scopes.InvoiceScope;
import com.paper.smartcafe.invoice.presentation.InvoicePresenter;

import dagger.Subcomponent;

/**
 * Created by Chudofom on 28.07.17.
 */
@InvoiceScope
@Subcomponent(modules = {InvoiceModule.class})
public interface InvoiceComponent {
    void inject(InvoicePresenter storagePresenter);
}
