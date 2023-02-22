package com.example.meri.golinomedicen.repository.sources.remote;

import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import com.example.meri.golinomedicen.application.AppController;
import com.example.meri.golinomedicen.model.ErrorModel;
import com.example.meri.golinomedicen.repository.ModelInterface;
import com.example.meri.golinomedicen.repository.sources.remote.models.LoginModel;
import com.example.meri.golinomedicen.repository.sources.remote.models.PurchaseHistoryModelApi;
import com.example.meri.golinomedicen.repository.sources.remote.models.SuccessfulModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelRemote implements ModelInterface {

    private static final String TAG = ModelRemote.class.getSimpleName();


    private static ModelRemote INSTANCE;

    private static RemoteApiService services;


    private ModelRemote() {
    }

    public static ModelRemote getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ModelRemote();
            services = AppController.getInstance().getRemote();
        }
        return INSTANCE;
    }


    @Override
    public void loginWithMobile(final String number, final LoadModelCallback<LoginModel> callback) {
        Call<LoginModel> api = services.loginWithMobile(number);

        Log.e(TAG, "loginWithMobile: " + api.request());

        api.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(final Call<LoginModel> call, final Response<LoginModel> response) {
                if (isSuccessful(response)) {
                    Log.e(TAG, "onResponse: successful , " + response.body().getMessage());
                    callback.onModelLoaded(response.body());
                } else {
                    Log.e(TAG, "onResponse: unSuccessful");
                    failure("Unknown Error", callback);
                }
            }

            @Override
            public void onFailure(final Call<LoginModel> call, final Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                failure(t.getMessage(), callback);
            }
        });

    }

    @Override
    public void loginWithMobileAndToken(final String number, final String token, final
    LoadModelCallback<LoginModel> callback) {

    }

    @Override
    public void loginWithMobileAndVerifyCode(final String number, final String verifyCode, final
    LoadModelCallback<LoginModel> callback) {

    }

    @Override
    public void updateInformationUser(final String name, final String lastName, final String email, final
    String token, LoadModelCallback<SuccessfulModel> callback) {

        Call<SuccessfulModel> api = services.updateInformationUser(name, lastName, email, token);

        api.enqueue(new Callback<SuccessfulModel>() {
            @Override
            public void onResponse(final Call<SuccessfulModel> call, final Response<SuccessfulModel>
                    response) {
                if (isSuccessful(response)) {
                    Log.e(TAG, "onResponse: successful , " + response.body().getMessage());
                    callback.onModelLoaded(response.body());
                } else {
                    Log.e(TAG, "onResponse: unSuccessful");
                    failure("Unknown Error", callback);
                }
            }

            @Override
            public void onFailure(final Call<SuccessfulModel> call, final Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                failure(t.getMessage(), callback);
            }
        });

    }

    @Override
    public void allPurchaseHistory(final String token, LoadModelsCallback<PurchaseHistoryModelApi> callback) {

        Call<List<PurchaseHistoryModelApi>> api = services.allPurchaseHistory(token);

        api.enqueue(new Callback<List<PurchaseHistoryModelApi>>() {
            @Override
            public void onResponse(final Call<List<PurchaseHistoryModelApi>> call, final
            Response<List<PurchaseHistoryModelApi>> response) {
                if (isSuccessful(response)) {
                    Log.e(TAG, "onResponse: successful");
                    callback.onModelsLoaded(response.body());
                } else {
                    Log.e(TAG, "onResponse: unSuccessful");
                    failure("Unknown Error", callback);
                }
            }

            @Override
            public void onFailure(final Call<List<PurchaseHistoryModelApi>> call, final Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                failure(t.getMessage(), callback);

            }
        });

    }

    private boolean isSuccessful(final Response response) {
        return response != null&&response.isSuccessful()&&response.body() != null;
    }

    private void failure(@NonNull final String message, @NonNull final ModelNotAvailable callback) {
        callback.onModelNotAvailable(new ErrorModel(message));
    }
}
