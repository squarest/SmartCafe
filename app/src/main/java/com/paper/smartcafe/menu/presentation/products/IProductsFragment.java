package com.paper.smartcafe.menu.presentation.products;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.paper.smartcafe.entities.ProductCategory;

/**
 * Created by Chudofom on 01.08.17.
 */

public interface IProductsFragment extends MvpView {
    @StateStrategyType(SingleStateStrategy.class)
    void showProducts(ProductCategory category);
    void showWarningDialog(String title, String message);
}
