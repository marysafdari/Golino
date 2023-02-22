package com.example.meri.golinomedicen.ui.activities.splash;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.meri.golinomedicen.R;
import com.example.meri.golinomedicen.ui.activities.login.LoginActivity;
import com.example.meri.golinomedicen.ui.activities.signUp.SignUpActivity;
import com.example.meri.golinomedicen.utility.ActivityUtils;
import com.example.meri.golinomedicen.repository.sources.local.pref.MyPrefManager;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();

    private static final int DELAY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.setFullScreen(this);
        setContentView(R.layout.splash_activity);

        initNetState();

        ConstraintLayout mainLayout = findViewById(R.id.childLayout);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.translate_in);
        animation.setDuration(1500);
        mainLayout.startAnimation(animation);
//        Handler mHandler = new Handler();
//        Runnable runnable = () -> {
//            Intent intentLogin = new Intent(this, LoginActivity.class);
//            startActivity(intentLogin);
//            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//            finish();
//        };
//
//        mHandler.postDelayed(runnable, DELAY);
    }

    private void initNetState() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        MyPrefManager.getInstance().setNetState(connectivityManager != null && connectivityManager.getActiveNetworkInfo() != null);
    }

    public void onBtnSplashLoginClick(View view) {
        Intent intentLogin = new Intent(this, LoginActivity.class);
        startActivity(intentLogin);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    public void onBtnSplashRegisterClick(View view) {
        Intent intentLogin = new Intent(this, SignUpActivity.class);
        startActivity(intentLogin);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}
