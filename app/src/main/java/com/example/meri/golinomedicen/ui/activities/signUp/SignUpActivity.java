package com.example.meri.golinomedicen.ui.activities.signUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;

import com.example.meri.golinomedicen.R;
import com.example.meri.golinomedicen.application.AppController;
import com.example.meri.golinomedicen.ui.activities.IOnBackPressed;
import com.example.meri.golinomedicen.ui.activities.MyActivity;
import com.example.meri.golinomedicen.ui.activities.maps.MapsActivity;
import com.example.meri.golinomedicen.ui.activities.splash.SplashActivity;
import com.example.meri.golinomedicen.utility.ActivityUtils;

public class SignUpActivity extends MyActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();

    private SignUpPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.setFullScreen(this);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        setupMVP();
    }

    private void setupMVP() {

        SignUpFragment fragment = (SignUpFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (fragment == null) {
            Bundle extras = getIntent().getExtras();
            fragment = SignUpFragment.newInstance(extras);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.content_frame);
        }

        if (presenter == null)
            presenter = new SignUpPresenter(fragment, AppController.getInstance().getDataModel());


    }


    public void onSgnUpLinLayClick(View view) {
        Toast.makeText(getApplicationContext(),"hello", Toast.LENGTH_LONG).show();

       // startActivityForResult(new Intent(getApplicationContext(), MapsActivity.class), 2);

        startActivity(new Intent(getApplicationContext(),FavAdress.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 2)
        {
            Bundle extras = data.getExtras();
            if (extras != null) ((TextView) findViewById(R.id.et_addr)).setText(extras.getString(MapsActivity.EXTRA_KEY_LOCATION));
        }

    }



    @Override
    public void onBackPressed() {
        SignUpFragment fragment = (SignUpFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
            //  super.onBackPressed();
            startActivity(new Intent(this, SplashActivity.class));
            finish();
        }
    }
}
