package com.example.clevercafe.menu.di;

import com.example.clevercafe.dagger.scopes.MenuScope;
import com.example.clevercafe.menu.presentation.addcategory.AddCategoryPresenter;
import com.example.clevercafe.menu.presentation.addproduct.AddProductPresenter;
import com.example.clevercafe.menu.presentation.categories.CategoriesPresenter;
import com.example.clevercafe.menu.presentation.products.ProductsPresenter;
import com.example.clevercafe.menu.presentation.MenuPresenter;

import dagger.Subcomponent;

/**
 * Created by Chudofom on 28.07.17.
 */
@MenuScope
@Subcomponent(modules = {MenuModule.class})
public interface MenuComponent {
    void inject(MenuPresenter presenter);

    void inject(CategoriesPresenter presenter);

    void inject(ProductsPresenter presenter);

    void inject(AddProductPresenter presenter);

    void inject(AddCategoryPresenter presenter);

}
