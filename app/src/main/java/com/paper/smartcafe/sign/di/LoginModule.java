package com.paper.smartcafe.sign.di;

import android.content.SharedPreferences;

import com.paper.smartcafe.dagger.scopes.LoginScope;
import com.paper.smartcafe.data.repositories.UserRepository;
import com.paper.smartcafe.sign.domain.ILoginInteractor;
import com.paper.smartcafe.sign.domain.LoginInteractor;
import com.google.firebase.auth.FirebaseAuth;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chudofom on 29.10.17.
 */
@Module
public class LoginModule {
    @Provides
    @LoginScope
    public FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    @LoginScope
    public UserRepository provideUserRepository(SharedPreferences sharedPreferences) {
        return new UserRepository(sharedPreferences);
    }


    @Provides
    @LoginScope
    public ILoginInteractor provideLoginInteractor(UserRepository userRepository, FirebaseAuth firebaseAuth) {
        return new LoginInteractor(firebaseAuth, userRepository);
    }
}
