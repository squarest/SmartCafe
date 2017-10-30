package com.example.clevercafe.sign.presentation;

import com.arellomobile.mvp.MvpView;

/**
 * Created by Chudofom on 29.10.17.
 */

public interface LoginView extends MvpView {
    void showProgress(final boolean show);

    void startApp();

    void showMessage(String message);
}
