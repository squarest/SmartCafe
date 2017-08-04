package com.example.clevercafe.menu.presentation.products;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.clevercafe.entities.Product;

import java.util.ArrayList;

/**
 * Created by Chudofom on 01.08.17.
 */

public interface IProductsFragment extends MvpView {
    @StateStrategyType(SingleStateStrategy.class)
    void showProducts(ArrayList<Product> products);
}
