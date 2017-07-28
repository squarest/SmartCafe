package com.example.clevercafe.main.di;

import com.example.clevercafe.dagger.scopes.MainScope;
import com.example.clevercafe.main.presentation.MainPresenter;

import dagger.Subcomponent;

/**
 * Created by Chudofom on 28.07.17.
 */
@MainScope
@Subcomponent(modules = {MainModule.class})
public interface MainComponent {
    void inject(MainPresenter mainPresenter);

}
