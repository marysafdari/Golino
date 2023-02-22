package com.example.meri.golinomedicen.repository.sources.local.pref;

import android.content.SharedPreferences;

import com.example.meri.golinomedicen.application.AppController;
import com.example.meri.golinomedicen.utility.AppConstant;

public class MyPrefManager {

    private static final String TAG = MyPrefManager.class.getSimpleName();

    private static MyPrefManager mInstance = new MyPrefManager();


    private SharedPreferences pref;


    private MyPrefManager() {
        this.pref = AppController.getInstance().getSharedPreferences();
    }

    public synchronized static MyPrefManager getInstance() {
        return mInstance;
    }

    //region net state
    public void setNetState(boolean state) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(AppConstant.PREF_KEY_NET_STATE, state);
        editor.apply();
    }

    public boolean getNetState() {
        return pref.getBoolean(AppConstant.PREF_KEY_NET_STATE, false);
    }
    //endregion

    //region token
    public void setToken(String token) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(AppConstant.PREF_KEY_TOKEN, token);
        editor.apply();
    }

    public String getToken() {
        return pref.getString(AppConstant.PREF_KEY_TOKEN, "");
    }
    //endregion
}
