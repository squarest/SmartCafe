package com.example.clevercafe.menu.presentation.addproduct;

import com.arellomobile.mvp.MvpView;
import com.example.clevercafe.entities.Product;

/**
 * Created by Chudofom on 01.08.17.
 */

public interface IAddProductFragment extends MvpView {
    void showAddForm();

    void showEditForm(Product product);

    void hideForm();

}
