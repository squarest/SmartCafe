package com.paper.smartcafe;

import android.app.Application;

import com.paper.smartcafe.analytics.di.AnalyticsComponent;
import com.paper.smartcafe.analytics.di.AnalyticsModule;
import com.paper.smartcafe.dagger.AppComponent;
import com.paper.smartcafe.dagger.DaggerAppComponent;
import com.paper.smartcafe.dagger.modules.AppModule;
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

/**
 * Created by Chudofom on 24.07.17.
 */

public class App extends Application {
    private static AppComponent appComponent;
    private static MainComponent mainComponent;
    private static StorageComponent storageComponent;
    private static InvoiceComponent invoiceComponent;
    private static AnalyticsComponent analyticsComponent;
    private static ReportComponent reportComponent;
    private static LoginComponent loginComponent;
    private static SplashComponent splashComponent;

    public static final int INGREDIENT_REQUEST_CODE = 0;
    public static final int ICON_REQUEST_CODE = 1;
    public static final int IMAGE_REQUEST_CODE = 2;

    public static MainComponent getMainComponent() {
        return mainComponent;
    }

    public static StorageComponent getStorageComponent() {
        return storageComponent;
    }

    public static InvoiceComponent getInvoiceComponent() {
        return invoiceComponent;
    }

    public static AnalyticsComponent getAnalyticsComponent() {
        return analyticsComponent;
    }

    public static ReportComponent getReportComponent() {
        return reportComponent;
    }

    public static LoginComponent getLoginComponent() {
        return loginComponent;
    }

    public static SplashComponent getSplashComponent() {
        return splashComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = buildComponent();
        mainComponent = plusMainComponent();
        storageComponent = plusStorageComponent();
        invoiceComponent = plusInvoiceComponent();
        analyticsComponent = plusAnalyticsComponent();
        reportComponent = plusReportComponent();
        loginComponent = plusLoginComponent();
        splashComponent = plusSplashComponent();
    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public MainComponent plusMainComponent() {
        if (mainComponent == null) {
            mainComponent = appComponent.plusMainComponent(new MainModule());
        }
        return mainComponent;
    }

    public StorageComponent plusStorageComponent() {
        if (storageComponent == null) {
            storageComponent = appComponent.plusStorageComponent(new StorageModule());
        }
        return storageComponent;
    }

    public InvoiceComponent plusInvoiceComponent() {
        if (invoiceComponent == null) {
            invoiceComponent = appComponent.plusInvoiceComponent(new InvoiceModule());
        }
        return invoiceComponent;
    }

    public AnalyticsComponent plusAnalyticsComponent() {
        if (analyticsComponent == null) {
            analyticsComponent = appComponent.plusAnalyticsComponent(new AnalyticsModule());
        }
        return analyticsComponent;
    }

    public ReportComponent plusReportComponent() {
        if (reportComponent == null) {
            reportComponent = appComponent.plusReportComponent(new ReportModule());
        }
        return reportComponent;
    }

    public LoginComponent plusLoginComponent() {
        if (loginComponent == null) {
            loginComponent = appComponent.plusLoginComponent(new LoginModule());
        }
        return loginComponent;
    }

    public SplashComponent plusSplashComponent() {
        if (splashComponent == null) {
            splashComponent = appComponent.plusSplashComponent(new SplashModule());
        }
        return splashComponent;
    }

}
