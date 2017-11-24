package com.paper.smartcafe.invoice.di;

import com.paper.smartcafe.dagger.scopes.InvoiceScope;
import com.paper.smartcafe.data.dao.DatabaseDao;
import com.paper.smartcafe.data.repositories.InvoiceRepository;
import com.paper.smartcafe.invoice.domain.IInvoiceInteractor;
import com.paper.smartcafe.invoice.domain.InvoiceInteractor;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chudofom on 28.07.17.
 */
@Module
public class InvoiceModule {
    @Provides
    @InvoiceScope
    public InvoiceRepository provideInvoiceRepository(DatabaseDao databaseDao) {
        return new InvoiceRepository(databaseDao);
    }

    @Provides
    @InvoiceScope
    public IInvoiceInteractor provideInvoiceInteractor(InvoiceRepository invoiceRepository) {
        return new InvoiceInteractor(invoiceRepository);
    }
}
