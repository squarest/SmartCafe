package com.example.clevercafe.splash.domain;

import io.reactivex.Single;

/**
 * Created by Chudofom on 31.10.17.
 */

public interface ISplashInteractor {
    Single<Boolean> checkUser();

}
