package com.example.meri.golinomedicen.ui.activities.login;

import android.util.Log;

import com.example.meri.golinomedicen.R;
import com.example.meri.golinomedicen.model.ErrorModel;
import com.example.meri.golinomedicen.repository.ModelInterface;
import com.example.meri.golinomedicen.repository.sources.local.pref.MyPrefManager;
import com.example.meri.golinomedicen.repository.sources.remote.models.LoginModel;

public class LoginPresenter implements LoginContract.Presenter {

    private static final String TAG = LoginPresenter.class.getSimpleName();

    private LoginContract.View mView;
    private ModelInterface mModel;

    private String number;


    public LoginPresenter(final LoginContract.View view, final ModelInterface model) {
        this.mView = view;
        this.mModel = model;

        mView.setPresenter(this);
    }

    //region Implementation LoginContract.Presenter
    @Override
    public void login(final String number) {
        if (isCorrectNumber(number))
            loginUser(number);
        else
            mView.showToast(mView.getContextView().getResources().getString(R.string
                    .text_toast_number_login_activity));
    }

    private void loginUser(final String number) {

        this.number = number;

        mView.showProgress();

        mModel.loginWithMobile(number, new ModelInterface.LoadModelCallback<LoginModel>() {
            @Override
            public void onModelLoaded(final LoginModel model) {
                Log.e(TAG, "onModelLoaded: " + model.toString());
                //mView.hideProgress();
                //mView.switchToSmsCode();
            }

            @Override
            public void onModelNotAvailable(final ErrorModel error) {
                Log.e(TAG, "onModelNotAvailable: " + error.getMessage());
                //mView.hideProgress();
                //mView.showToast(error.getMessage());
                //mView.switchToSmsCode();
            }
        });

    }

    private boolean isCorrectNumber(final String number) {
        return number.length() > 10 && number.startsWith("09");
    }

    @Override
    public void sendCode(final String code) {
        Log.e(TAG, "SmsCode: " + code);
        if (isCorrectCode(code)){
            // TODO: 11/15/18 send code to server and set token to shared preferences
            MyPrefManager.getInstance().setToken("EDPS13E9QHPZZMP73DMLY656TW1VGR");
            mView.showSignUpUi(number);
        }
        else
            mView.showToast(mView.getContextView().getResources().getString(R.string
                    .text_toast_code_login_activity));
    }

    private boolean isCorrectCode(final String code) {
//        return code.length() == 5;
        return true;
    }

    //endregion

}
