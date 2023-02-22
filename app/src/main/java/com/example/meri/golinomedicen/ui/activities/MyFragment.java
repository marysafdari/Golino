package com.example.meri.golinomedicen.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.example.meri.golinomedicen.baseInterfaces.viewInterfaces.ActivityInterface;
import com.example.meri.golinomedicen.baseInterfaces.viewInterfaces.ContextInterface;
import com.example.meri.golinomedicen.baseInterfaces.viewInterfaces.ShowToast;

public abstract class MyFragment extends Fragment implements ShowToast, ContextInterface, ActivityInterface {


    @Override
    public void showToast(final String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContextView() {
        return getContext();
    }

    @Override
    public Activity getActivityFragment() {
        return getActivity();
    }



}
