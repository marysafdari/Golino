package com.example.meri.golinomedicen.ui.activities.signUp;

import com.example.meri.golinomedicen.baseInterfaces.BasePresenter;
import com.example.meri.golinomedicen.baseInterfaces.BaseView;
import com.example.meri.golinomedicen.baseInterfaces.viewInterfaces.ProgressBarInterface;

public interface SignUpContract {

    interface View extends BaseView<Presenter>, ProgressBarInterface {

        void showSendMedicineUi();

    }

    interface Presenter extends BasePresenter {

        void registerInformation(String number, String name, String email, String code, boolean cb_role);

    }


}
