package com.example.meri.golinomedicen.repository;

import java.util.List;

import com.example.meri.golinomedicen.model.ErrorModel;
import com.example.meri.golinomedicen.repository.sources.remote.models.LoginModel;
import com.example.meri.golinomedicen.repository.sources.remote.models.PurchaseHistoryModelApi;
import com.example.meri.golinomedicen.repository.sources.remote.models.SuccessfulModel;

public interface ModelInterface {


    interface ModelNotAvailable {
        void onModelNotAvailable(ErrorModel error);
    }

    interface LoadModelCallback<T> extends ModelNotAvailable {

        void onModelLoaded(T model);

    }

    interface LoadModelsCallback<T> extends ModelNotAvailable {

        void onModelsLoaded(List<T> models);

    }


    void loginWithMobile(String number, LoadModelCallback<LoginModel> callback);

    void loginWithMobileAndToken(String number, String token, LoadModelCallback<LoginModel> callback);

    void loginWithMobileAndVerifyCode(String number, String verifyCode, LoadModelCallback<LoginModel>
            callback);

    void updateInformationUser(String name, String lastName, String email, String token,
                               LoadModelCallback<SuccessfulModel> callback);

    void allPurchaseHistory(String token, LoadModelsCallback<PurchaseHistoryModelApi> callback);

}
