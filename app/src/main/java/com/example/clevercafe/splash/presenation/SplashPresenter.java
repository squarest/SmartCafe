package com.example.clevercafe.splash.presenation;

import com.arellomobile.mvp.InjectViewState;
import com.example.clevercafe.App;
import com.example.clevercafe.base.BasePresenter;
import com.example.clevercafe.splash.domain.ISplashInteractor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Chudofom on 31.10.17.
 */
@InjectViewState
public class SplashPresenter extends BasePresenter<SplashView> {
    @Inject
    public ISplashInteractor interactor;

    public SplashPresenter() {
        App.getSplashComponent().inject(this);
    }

    public void viewInit() {
        interactor.checkUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isUserSaved -> {
                    if (isUserSaved) {
                        getViewState().startApp();
                    } else {
                        getViewState().startLogin();
                    }
                }, Throwable::printStackTrace);

    }
}
