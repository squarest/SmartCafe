package com.example.clevercafe.invoice.di;

import com.example.clevercafe.dagger.scopes.InvoiceScope;
import com.example.clevercafe.data.InvoiceRepository;
import com.example.clevercafe.invoice.domain.IInvoiceInteractor;
import com.example.clevercafe.invoice.domain.InvoiceInteractor;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chudofom on 28.07.17.
 */
@Module
public class InvoiceModule {
    @Provides
    @InvoiceScope
    public IInvoiceInteractor provideStorageInteractor(InvoiceRepository invoiceRepository) {
        return new InvoiceInteractor(invoiceRepository);
    }
}
