package com.paper.smartcafe.splash.domain;

import com.paper.smartcafe.data.repositories.UserRepository;

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
