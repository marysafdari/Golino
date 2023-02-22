package com.example.meri.golinomedicen.repository;

import com.example.meri.golinomedicen.repository.sources.remote.ModelRemote;
import com.example.meri.golinomedicen.repository.sources.remote.models.LoginModel;
import com.example.meri.golinomedicen.repository.sources.remote.models.PurchaseHistoryModelApi;
import com.example.meri.golinomedicen.repository.sources.remote.models.SuccessfulModel;

public class RepositoryManager implements ModelInterface {

    private static final String TAG = RepositoryManager.class.getSimpleName();

    private static RepositoryManager INSTANCE;

    private static ModelRemote remote;

    private RepositoryManager() {
    }

    public static RepositoryManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RepositoryManager();
            remote = ModelRemote.getInstance();
        } return INSTANCE;
    }


    @Override
    public void loginWithMobile(final String number, final LoadModelCallback<LoginModel> callback) {

        remote.loginWithMobile(number, callback);


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
    String token, final LoadModelCallback<SuccessfulModel> callback) {
        remote.updateInformationUser(name, lastName, email, token, callback);
    }

    @Override
    public void allPurchaseHistory(final String token, final LoadModelsCallback<PurchaseHistoryModelApi>
            callback) {
        remote.allPurchaseHistory(token, callback);
    }
}
