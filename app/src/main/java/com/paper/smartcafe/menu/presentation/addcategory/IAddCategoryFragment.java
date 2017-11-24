package com.paper.smartcafe.menu.presentation.addcategory;

import com.arellomobile.mvp.MvpView;
import com.paper.smartcafe.entities.ProductCategory;

/**
 * Created by Chudofom on 01.08.17.
 */

public interface IAddCategoryFragment extends MvpView {
    void showAddForm();

    void showEditForm(ProductCategory category);

    void hideForm();

    void showMessage(String message);

}
