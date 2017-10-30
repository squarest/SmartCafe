package com.example.clevercafe.sign.di;

import com.example.clevercafe.dagger.scopes.LoginScope;
import com.example.clevercafe.sign.presentation.LoginPresenter;

import dagger.Subcomponent;

/**
 * Created by Chudofom on 29.10.17.
 */
@LoginScope
@Subcomponent(modules = {LoginModule.class})
public interface LoginComponent {
    public void inject(LoginPresenter loginPresenter);
}
