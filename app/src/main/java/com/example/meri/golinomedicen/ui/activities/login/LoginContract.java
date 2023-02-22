package com.example.meri.golinomedicen.ui.activities.login;

import com.example.meri.golinomedicen.baseInterfaces.BasePresenter;
import com.example.meri.golinomedicen.baseInterfaces.BaseView;
import com.example.meri.golinomedicen.baseInterfaces.viewInterfaces.ProgressBarInterface;

public interface LoginContract {

    interface View extends BaseView<Presenter>, ProgressBarInterface {

        void showSignUpUi(String number);

        void switchToSmsCode();

        void switchToNumber();

        void showSendMedicineUi();

    }

    interface Presenter extends BasePresenter {

        void login(String number);

        void sendCode(String code);

    }


}
