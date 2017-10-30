package com.example.clevercafe.sign.domain;

import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.Completable;

/**
 * Created by Chudofom on 29.10.17.
 */

public class LoginInteractor implements ILoginInteractor {
    public FirebaseAuth firebaseAuth;

    public LoginInteractor(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public Completable login(String login, String password) {
        return Completable.create(e ->
        {
            firebaseAuth.signInWithEmailAndPassword(login, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            e.onComplete();
                        } else {
                            e.onError(task.getException());
                        }
                    });
        });
    }
}
