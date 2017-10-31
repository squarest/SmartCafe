package com.example.clevercafe.splash.di;

import com.example.clevercafe.dagger.scopes.SplashScope;
import com.example.clevercafe.splash.presenation.SplashPresenter;

import dagger.Subcomponent;

/**
 * Created by Chudofom on 31.10.17.
 */
@SplashScope
@Subcomponent(modules = {SplashModule.class})
public interface SplashComponent {
    void inject(SplashPresenter presenter);
}
