package com.example.meri.golinomedicen.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

import com.example.meri.golinomedicen.R;
import com.example.meri.golinomedicen.repository.ModelInterface;
import com.example.meri.golinomedicen.repository.RepositoryManager;
import com.example.meri.golinomedicen.repository.sources.remote.RemoteApiService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppController extends Application {

    private static final String TAG = AppController.class.getSimpleName();

    private static AppController mInstance;

    private static final String BASE_URL = "http://2.144.244.190:81/";

    private ModelInterface dataModel;
    private RemoteApiService remote;
    private SharedPreferences pref;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        initFont();

        initModules();

    }

    private void initModules() {
        remote = new Retrofit.Builder().baseUrl(BASE_URL).client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create()).build().create
                        (RemoteApiService.class);

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        dataModel = RepositoryManager.getInstance();


    }

    private void initFont() {
        ViewPump.init(ViewPump.builder().addInterceptor(new CalligraphyInterceptor(new
                CalligraphyConfig.Builder().setDefaultFontPath("fonts/iranian_sans.ttf")
                .setFontAttrId(R.attr.fontPath).build())).build());
    }


    public static synchronized AppController getInstance() {
        return mInstance;
    }


    public SharedPreferences getSharedPreferences() {
        return pref;
    }

    public ModelInterface getDataModel() {
        return dataModel;
    }

    public RemoteApiService getRemote() {
        return remote;
    }
}
