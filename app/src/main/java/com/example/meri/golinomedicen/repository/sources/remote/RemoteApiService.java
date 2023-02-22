package com.example.meri.golinomedicen.repository.sources.remote;

import com.google.gson.JsonObject;

import java.util.List;

import com.example.meri.golinomedicen.repository.sources.remote.models.LoginModel;
import com.example.meri.golinomedicen.repository.sources.remote.models.PurchaseHistoryModelApi;
import com.example.meri.golinomedicen.repository.sources.remote.models.SuccessfulModel;
import com.example.meri.golinomedicen.utility.AppConstant;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RemoteApiService {

    @FormUrlEncoded
    @POST("api/Users/UserLogin")
    Call<LoginModel> loginWithMobile(@Field(AppConstant.SERVER_LOGIN_PHONE) String number);


    @FormUrlEncoded
    @POST("api/Users/UserLogin")
    Call<LoginModel> loginWithMobileAndVerifyCode(@Field(AppConstant.SERVER_LOGIN_PHONE) String number,
                                                  @Field(AppConstant.SERVER_LOGIN_VERIFY_CODE) String
                                                          verifyCode);

    @FormUrlEncoded
    @POST("api/Users/UserLogin")
    Call<JsonObject> loginWithMobileAndToken(@Field(AppConstant.SERVER_LOGIN_PHONE) String number, @Field
            (AppConstant.SERVER_TOKEN) String token);

    @FormUrlEncoded
    @POST("api/Users/UserUpdate")
    Call<SuccessfulModel> updateInformationUser(@Field(AppConstant.SERVER_UPDATE_USER_NAME) String name,
                                                @Field(AppConstant.SERVER_UPDATE_USER_LAST_NAME) String
                                                        lastName, @Field(AppConstant
            .SERVER_UPDATE_USER_EMAIL) String email, @Field(AppConstant.SERVER_TOKEN) String token);


    @FormUrlEncoded
    @POST("api/Users/UserBuyRecords")
    Call<List<PurchaseHistoryModelApi>> allPurchaseHistory(@Field(AppConstant.SERVER_TOKEN) String token);


}
