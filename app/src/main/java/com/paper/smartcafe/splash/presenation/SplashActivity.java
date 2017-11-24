package com.paper.smartcafe.splash.presenation;

import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.paper.smartcafe.R;
import com.paper.smartcafe.base.BaseActivity;
import com.paper.smartcafe.main.presentation.MainActivity;
import com.paper.smartcafe.sign.presentation.LoginActivity;

public class SplashActivity extends BaseActivity implements SplashView {

    @InjectPresenter
    public SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        presenter.viewInit();
    }

    @Override
    public void startLogin() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void startApp() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
