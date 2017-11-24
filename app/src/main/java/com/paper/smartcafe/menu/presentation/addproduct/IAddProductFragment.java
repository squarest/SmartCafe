package com.paper.smartcafe.menu.presentation.addproduct;

import com.arellomobile.mvp.MvpView;
import com.paper.smartcafe.entities.Product;

/**
 * Created by Chudofom on 01.08.17.
 */

public interface IAddProductFragment extends MvpView {
    void showAddForm();

    void showEditForm(Product product);

    void hideForm();

    void showMessage(String message);

}
