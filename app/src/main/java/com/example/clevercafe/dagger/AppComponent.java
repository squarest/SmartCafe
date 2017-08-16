package com.example.clevercafe.dagger;

import com.example.clevercafe.dagger.modules.AppModule;
import com.example.clevercafe.dagger.modules.DataModule;
import com.example.clevercafe.main.di.MainComponent;
import com.example.clevercafe.main.di.MainModule;
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
}
