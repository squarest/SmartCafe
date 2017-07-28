package com.example.clevercafe.storage.di;

import com.example.clevercafe.dagger.scopes.StorageScope;
import com.example.clevercafe.storage.presentation.IngredientPresenter;
import com.example.clevercafe.storage.presentation.StoragePresenter;

import dagger.Subcomponent;

/**
 * Created by Chudofom on 28.07.17.
 */
@StorageScope
@Subcomponent(modules = {StorageModule.class})
public interface StorageComponent {
    void inject(StoragePresenter storagePresenter);

    void inject(IngredientPresenter ingredientPresenter);

}
