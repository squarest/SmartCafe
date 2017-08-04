package com.example.clevercafe.menu.presentation;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.clevercafe.App;
import com.example.clevercafe.entities.ProductCategory;
import com.example.clevercafe.menu.domain.IMenuInteractor;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by Chudofom on 20.03.17.
 */
@InjectViewState
public class MenuPresenter extends MvpPresenter<MenuView> {
    private MenuView menuView = getViewState();
    private ArrayList<ProductCategory> categories = new ArrayList<>();

    @Inject
    public IMenuInteractor interactor;

    public MenuPresenter() {
        App.getMenuComponent().inject(this);
    }

}
