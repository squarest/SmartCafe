package com.paper.smartcafe.storage.di;

import com.paper.smartcafe.dagger.scopes.StorageScope;
import com.paper.smartcafe.storage.presentation.ingredient.IngredientPresenter;
import com.paper.smartcafe.storage.presentation.invoiceIngredients.InvoiceIngredientPresenter;
import com.paper.smartcafe.storage.presentation.storage.StoragePresenter;

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
