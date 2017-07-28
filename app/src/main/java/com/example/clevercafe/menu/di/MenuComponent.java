package com.example.clevercafe.menu.di;

import com.example.clevercafe.dagger.scopes.MenuScope;
import com.example.clevercafe.menu.presentation.MenuPresenter;

import dagger.Subcomponent;

/**
 * Created by Chudofom on 28.07.17.
 */
@MenuScope
@Subcomponent(modules = {MenuModule.class})
public interface MenuComponent {
    void inject(MenuPresenter presenter);

}
