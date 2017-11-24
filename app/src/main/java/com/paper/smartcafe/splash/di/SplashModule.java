package com.paper.smartcafe.splash.di;

import android.content.SharedPreferences;

import com.paper.smartcafe.dagger.scopes.SplashScope;
import com.paper.smartcafe.data.repositories.UserRepository;
import com.paper.smartcafe.splash.domain.ISplashInteractor;
import com.paper.smartcafe.splash.domain.SplashInteractor;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chudofom on 31.10.17.
 */
@Module
public class SplashModule {
    @Provides
    @SplashScope
    public UserRepository provideUserRepository(SharedPreferences sharedPreferences) {
        return new UserRepository(sharedPreferences);
    }


    @Provides
    @SplashScope
    public ISplashInteractor provideSplashInteractor(UserRepository userRepository) {
        return new SplashInteractor(userRepository);
    }


}
