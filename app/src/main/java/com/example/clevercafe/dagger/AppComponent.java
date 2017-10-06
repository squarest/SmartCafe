package com.example.clevercafe.dagger;

import com.example.clevercafe.analytics.di.AnalyticsComponent;
import com.example.clevercafe.analytics.di.AnalyticsModule;
import com.example.clevercafe.dagger.modules.AppModule;
import com.example.clevercafe.dagger.modules.DataModule;
import com.example.clevercafe.invoice.di.InvoiceComponent;
import com.example.clevercafe.invoice.di.InvoiceModule;
import com.example.clevercafe.main.di.MainComponent;
import com.example.clevercafe.main.di.MainModule;
import com.example.clevercafe.report.di.ReportComponent;
import com.example.clevercafe.report.di.ReportModule;
import com.example.clevercafe.storage.di.StorageComponent;
import com.example.clevercafe.storage.di.StorageModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Chudofom on 28.07.17.
 */
@Component(modules = {AppModule.class, DataModule.class})
@Singleton
public interface AppComponent {
    MainComponent plusMainComponent(MainModule mainModule);

    StorageComponent plusStorageComponent(StorageModule storageModule);

    InvoiceComponent plusInvoiceComponent(InvoiceModule invoiceModule);

    AnalyticsComponent plusAnalyticsComponent(AnalyticsModule analyticsModule);

    ReportComponent plusReportComponent(ReportModule reportModule);
}
