package com.example.clevercafe.invoice.di;

import com.example.clevercafe.dagger.scopes.InvoiceScope;
import com.example.clevercafe.invoice.presentation.InvoicePresenter;

import dagger.Subcomponent;

/**
 * Created by Chudofom on 28.07.17.
 */
@InvoiceScope
@Subcomponent(modules = {InvoiceModule.class})
public interface InvoiceComponent {
    void inject(InvoicePresenter storagePresenter);
}
