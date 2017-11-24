package com.paper.smartcafe.storage.di;

import com.paper.smartcafe.dagger.scopes.StorageScope;
import com.paper.smartcafe.data.repositories.IngredientRepository;
import com.paper.smartcafe.storage.domain.IStrorageInteractor;
import com.paper.smartcafe.storage.domain.StorageInteractor;

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
