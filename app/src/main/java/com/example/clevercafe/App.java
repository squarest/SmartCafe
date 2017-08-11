package com.example.clevercafe;

import android.app.Application;

import com.example.clevercafe.dagger.AppComponent;
import com.example.clevercafe.dagger.DaggerAppComponent;
import com.example.clevercafe.dagger.modules.AppModule;
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

    public static MainComponent getMainComponent() {
        return mainComponent;
    }

    public static StorageComponent getStorageComponent() {
        return storageComponent;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = buildComponent();
        mainComponent = plusMainComponent();
        storageComponent = plusStorageComponent();
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

}
