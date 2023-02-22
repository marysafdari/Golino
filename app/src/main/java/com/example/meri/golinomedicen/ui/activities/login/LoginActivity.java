package com.example.meri.golinomedicen.ui.activities.login;

import android.content.Intent;
import android.os.Bundle;

import com.example.meri.golinomedicen.R;
import com.example.meri.golinomedicen.application.AppController;
import com.example.meri.golinomedicen.ui.activities.IOnBackPressed;
import com.example.meri.golinomedicen.ui.activities.MyActivity;
import com.example.meri.golinomedicen.ui.activities.splash.SplashActivity;
import com.example.meri.golinomedicen.utility.ActivityUtils;

public class LoginActivity extends MyActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();


    private LoginPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.setFullScreen(this);
        setContentView(R.layout.activity_login);
        setupMVP();
    }

    private void setupMVP() {
        LoginFragment fragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if (fragment == null) {
            fragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.content_frame);
        }

        if (presenter == null)
            presenter = new LoginPresenter(fragment, AppController.getInstance().getDataModel());

    }
    @Override
    public void onBackPressed() {
        LoginFragment fragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
          //  super.onBackPressed();
            startActivity(new Intent(this, SplashActivity.class));
            finish();
        }
    }
}
