package com.example.clevercafe.storage.di;

import com.example.clevercafe.dagger.scopes.StorageScope;
import com.example.clevercafe.db.IngredientRepository;
import com.example.clevercafe.storage.domain.IStrorageInteractor;
import com.example.clevercafe.storage.domain.StorageInteractor;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chudofom on 28.07.17.
 */
@Module
public class StorageModule {
    @Provides
    @StorageScope
    public IStrorageInteractor provideStorageInteractor(IngredientRepository ingredientRepository) {
        return new StorageInteractor(ingredientRepository);
    }
}
