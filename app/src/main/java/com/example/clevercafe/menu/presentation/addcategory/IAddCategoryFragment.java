package com.example.clevercafe.menu.presentation.addcategory;

import com.arellomobile.mvp.MvpView;
import com.example.clevercafe.entities.ProductCategory;

/**
 * Created by Chudofom on 01.08.17.
 */

public interface IAddCategoryFragment extends MvpView {
    void showAddForm();

    void showEditForm(ProductCategory category);

    void hideForm();

}
