package com.paper.smartcafe.menu.presentation.categories;

import com.arellomobile.mvp.MvpView;
import com.paper.smartcafe.entities.ProductCategory;

import java.util.ArrayList;

/**
 * Created by Chudofom on 01.08.17.
 */

public interface ICategoriesFragment extends MvpView {
    void showCategories(ArrayList<ProductCategory> categories);

    void showWarningDialog(String title, String message);
}
