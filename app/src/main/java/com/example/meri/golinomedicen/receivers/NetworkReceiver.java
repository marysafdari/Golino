package com.example.meri.golinomedicen.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import com.example.meri.golinomedicen.repository.sources.local.pref.MyPrefManager;

public class NetworkReceiver extends BroadcastReceiver {

    private static final String TAG = NetworkReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive");

        String action = intent.getAction();

        if (action != null && action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            final boolean isNetworkDown = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            Log.e(TAG, "onReceive: action" + action + " , isNetworkDown : " + isNetworkDown);
            MyPrefManager.getInstance().setNetState(!isNetworkDown);
        }


    }
}
