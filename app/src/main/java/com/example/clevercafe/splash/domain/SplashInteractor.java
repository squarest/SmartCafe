package com.example.clevercafe.splash.domain;

import com.example.clevercafe.data.repositories.UserRepository;

import io.reactivex.Single;

/**
 * Created by Chudofom on 31.10.17.
 */

public class SplashInteractor implements ISplashInteractor {
    public UserRepository userRepository;

    public SplashInteractor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Single<Boolean> checkUser() {
        return Single.create(e ->
        {
            e.onSuccess(userRepository.getUserId() != null);
        });
    }
}
