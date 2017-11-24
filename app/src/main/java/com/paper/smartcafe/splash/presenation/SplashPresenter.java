package com.paper.smartcafe.splash.presenation;

import com.arellomobile.mvp.InjectViewState;
import com.paper.smartcafe.App;
import com.paper.smartcafe.base.BasePresenter;
import com.paper.smartcafe.splash.domain.ISplashInteractor;

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
