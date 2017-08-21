package com.example.clevercafe.storage.di;

import com.example.clevercafe.dagger.scopes.StorageScope;
import com.example.clevercafe.storage.presentation.ingredient.IngredientPresenter;
import com.example.clevercafe.storage.presentation.invoiceIngredients.InvoiceIngredientPresenter;
import com.example.clevercafe.storage.presentation.storage.StoragePresenter;

import dagger.Subcomponent;

/**
 * Created by Chudofom on 28.07.17.
 */
@StorageScope
@Subcomponent(modules = {StorageModule.class})
public interface StorageComponent {
    void inject(StoragePresenter storagePresenter);

    void inject(IngredientPresenter ingredientPresenter);

    void inject(InvoiceIngredientPresenter ingredientPresenter);

}
