package com.paper.smartcafe.dagger;

import com.paper.smartcafe.analytics.di.AnalyticsComponent;
import com.paper.smartcafe.analytics.di.AnalyticsModule;
import com.paper.smartcafe.dagger.modules.AppModule;
import com.paper.smartcafe.dagger.modules.DataModule;
import com.paper.smartcafe.invoice.di.InvoiceComponent;
import com.paper.smartcafe.invoice.di.InvoiceModule;
import com.paper.smartcafe.main.di.MainComponent;
import com.paper.smartcafe.main.di.MainModule;
import com.paper.smartcafe.report.di.ReportComponent;
import com.paper.smartcafe.report.di.ReportModule;
import com.paper.smartcafe.sign.di.LoginComponent;
import com.paper.smartcafe.sign.di.LoginModule;
import com.paper.smartcafe.splash.di.SplashComponent;
import com.paper.smartcafe.splash.di.SplashModule;
import com.paper.smartcafe.storage.di.StorageComponent;
import com.paper.smartcafe.storage.di.StorageModule;

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

    LoginComponent plusLoginComponent(LoginModule loginModule);

    SplashComponent plusSplashComponent(SplashModule splashModule);
}
