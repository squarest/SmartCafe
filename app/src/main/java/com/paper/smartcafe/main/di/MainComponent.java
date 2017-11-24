package com.paper.smartcafe.main.di;

import com.paper.smartcafe.dagger.scopes.MainScope;
import com.paper.smartcafe.main.presentation.orderfragment.OrderPresenter;
import com.paper.smartcafe.main.presentation.ordersfragment.OrdersPresenter;
import com.paper.smartcafe.menu.presentation.addcategory.AddCategoryPresenter;
import com.paper.smartcafe.menu.presentation.addproduct.AddProductPresenter;
import com.paper.smartcafe.menu.presentation.categories.CategoriesPresenter;
import com.paper.smartcafe.menu.presentation.products.ProductsPresenter;

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
