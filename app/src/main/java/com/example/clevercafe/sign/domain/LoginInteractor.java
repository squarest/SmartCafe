package com.example.clevercafe.sign.domain;

import com.example.clevercafe.data.repositories.UserRepository;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.Completable;

/**
 * Created by Chudofom on 29.10.17.
 */

public class LoginInteractor implements ILoginInteractor {
    public FirebaseAuth firebaseAuth;
    public UserRepository userRepository;

    public LoginInteractor(FirebaseAuth firebaseAuth, UserRepository userRepository) {
        this.firebaseAuth = firebaseAuth;
        this.userRepository = userRepository;
    }

    @Override
    public Completable login(String login, String password) {
        return Completable.create(e ->
        {
            firebaseAuth.signInWithEmailAndPassword(login, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (firebaseAuth.getCurrentUser() != null) {
                                String userId = firebaseAuth.getCurrentUser().getUid();
                                userRepository.setUserId(userId);
                                e.onComplete();
                            } else e.onError(new NullPointerException());
                        } else {
                            e.onError(task.getException());
                        }
                    });
        });
    }
}
