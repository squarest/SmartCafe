package com.example.clevercafe.splash.di;

import android.content.SharedPreferences;

import com.example.clevercafe.dagger.scopes.SplashScope;
import com.example.clevercafe.data.repositories.UserRepository;
import com.example.clevercafe.splash.domain.ISplashInteractor;
import com.example.clevercafe.splash.domain.SplashInteractor;

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
