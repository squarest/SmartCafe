package com.example.clevercafe.menu.presentation;

import com.arellomobile.mvp.MvpView;

/**
 * Created by Chudofom on 20.03.17.
 */

public interface MenuView extends MvpView {
    void showProducts(long categoryId);
}
