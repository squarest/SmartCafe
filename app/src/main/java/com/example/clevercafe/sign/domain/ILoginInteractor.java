package com.example.clevercafe.sign.domain;

import io.reactivex.Completable;

/**
 * Created by Chudofom on 29.10.17.
 */

public interface ILoginInteractor {
    Completable login(String login, String password);
}
