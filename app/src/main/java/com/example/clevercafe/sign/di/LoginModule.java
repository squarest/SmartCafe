package com.example.clevercafe.sign.di;

import com.example.clevercafe.dagger.scopes.LoginScope;
import com.example.clevercafe.sign.domain.ILoginInteractor;
import com.example.clevercafe.sign.domain.LoginInteractor;
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
    public ILoginInteractor provideLOginInteractor(FirebaseAuth firebaseAuth) {
        return new LoginInteractor(firebaseAuth);
    }
}
