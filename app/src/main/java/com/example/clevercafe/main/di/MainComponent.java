package com.example.clevercafe.main.di;

import com.example.clevercafe.dagger.scopes.MainScope;
import com.example.clevercafe.main.presentation.orderfragment.OrderPresenter;
import com.example.clevercafe.main.presentation.ordersfragment.OrdersPresenter;
import com.example.clevercafe.menu.presentation.addcategory.AddCategoryPresenter;
import com.example.clevercafe.menu.presentation.addproduct.AddProductPresenter;
import com.example.clevercafe.menu.presentation.categories.CategoriesPresenter;
import com.example.clevercafe.menu.presentation.products.ProductsPresenter;

import dagger.Subcomponent;

/**
 * Created by Chudofom on 28.07.17.
 */
@MainScope
@Subcomponent(modules = {MainModule.class})
public interface MainComponent {
    void inject(OrderPresenter orderPresenter);

    void inject(OrdersPresenter ordersPresenter);

    void inject(CategoriesPresenter presenter);

    void inject(ProductsPresenter presenter);

    void inject(AddProductPresenter presenter);

    void inject(AddCategoryPresenter presenter);

}
