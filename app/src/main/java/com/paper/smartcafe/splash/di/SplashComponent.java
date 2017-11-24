package com.paper.smartcafe.splash.di;

import com.paper.smartcafe.dagger.scopes.SplashScope;
import com.paper.smartcafe.splash.presenation.SplashPresenter;

import dagger.Subcomponent;

/**
 * Created by Chudofom on 31.10.17.
 */
@SplashScope
@Subcomponent(modules = {SplashModule.class})
public interface SplashComponent {
    void inject(SplashPresenter presenter);
}
