package com.example.meri.golinomedicen.ui.activities.history;

import android.os.Bundle;

import com.example.meri.golinomedicen.R;
import com.example.meri.golinomedicen.application.AppController;
import com.example.meri.golinomedicen.ui.activities.MyActivity;
import com.example.meri.golinomedicen.utility.ActivityUtils;

public class HistoryActivity extends MyActivity {


    private HistoryContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        setupMVP();
    }

    private void setupMVP() {
        HistoryFragment fragment = (HistoryFragment) getSupportFragmentManager().findFragmentById(R.id
                .content_frame);

        if (fragment == null) {
            fragment = HistoryFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.content_frame);
        }

        if (presenter == null)
            presenter = new HistoryPresenter(fragment, AppController.getInstance().getDataModel());
    }
}
