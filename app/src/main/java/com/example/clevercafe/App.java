package com.example.clevercafe;

import android.app.Application;

import com.example.clevercafe.dagger.AppComponent;
import com.example.clevercafe.dagger.DaggerAppComponent;
import com.example.clevercafe.dagger.modules.AppModule;
import com.example.clevercafe.invoice.di.InvoiceComponent;
import com.example.clevercafe.invoice.di.InvoiceModule;
import com.example.clevercafe.main.di.MainComponent;
import com.example.clevercafe.main.di.MainModule;
import com.example.clevercafe.storage.di.StorageComponent;
import com.example.clevercafe.storage.di.StorageModule;

/**
 * Created by Chudofom on 24.07.17.
 */

public class App extends Application {
    private static AppComponent appComponent;
    private static MainComponent mainComponent;
    private static StorageComponent storageComponent;
    private static InvoiceComponent invoiceComponent;

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


    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = buildComponent();
        mainComponent = plusMainComponent();
        storageComponent = plusStorageComponent();
        invoiceComponent = plusInvoiceComponent();
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

}
