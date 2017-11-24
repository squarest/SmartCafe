package com.paper.smartcafe.sign.di;

import com.paper.smartcafe.dagger.scopes.LoginScope;
import com.paper.smartcafe.sign.presentation.LoginPresenter;

import dagger.Subcomponent;

/**
 * Created by Chudofom on 29.10.17.
 */
@LoginScope
@Subcomponent(modules = {LoginModule.class})
public interface LoginComponent {
    void inject(LoginPresenter loginPresenter);
}
