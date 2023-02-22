package com.example.meri.golinomedicen.ui.activities.signUp;

import android.util.Log;

import javax.inject.Inject;

import com.example.meri.golinomedicen.repository.ModelInterface;
import com.example.meri.golinomedicen.repository.sources.local.pref.MyPrefManager;
import com.example.meri.golinomedicen.repository.sources.remote.models.SuccessfulModel;
import com.example.meri.golinomedicen.model.ErrorModel;

public class SignUpPresenter implements SignUpContract.Presenter {
    private static final String TAG = SignUpPresenter.class.getSimpleName();

    private SignUpContract.View mView;
    private ModelInterface mModel;

    @Inject
    public SignUpPresenter(SignUpContract.View view, ModelInterface model) {
        this.mModel = model;
        this.mView = view;

        mView.setPresenter(this);

    }


    @Override
    public void registerInformation(final String number, final String name, final String email,
                                    final String code, boolean cb_role) {
        if (isCompleteInformation(number, name, email, code, cb_role))
            updateInformationUser(name, name, email);
//        mView.showSendMedicineUi();
    }

    private boolean isCompleteInformation(String number, String nameFamily, String email, String
            codePresentation, boolean cb_role) {
//        if (true)
//            return true;
        if (number.isEmpty()) {
            mView.showToast("لطفا شماره تلفن خود را وارد نمایید");
            return false;
        } else if (nameFamily.isEmpty()) {
            mView.showToast("لطفا نام و نام خانوادگی خود را وارد نمایید");
            return false;
        } /*else if (email.isEmpty()) {
            mView.showToast("لطفا ایمیل خود را وارد نمایید");
            return false;
        }*/
        if (!cb_role) {
            mView.showToast("لطفا قوانین را انتخاب نمایید");
            return false;
        }
        return true;
    }

    private void updateInformationUser(final String name, final String name1, final String email) {

        mView.showProgress();

        mModel.updateInformationUser(name, name, email, MyPrefManager.getInstance().getToken(),
                new ModelInterface.LoadModelCallback<SuccessfulModel>() {


                    @Override
                    public void onModelLoaded(final SuccessfulModel model) {
                        Log.e(TAG, "onModelLoaded: " + model.toString());
                        mView.hideProgress();
                        mView.showToast(model.getMessage());
                        mView.showSendMedicineUi();
                    }

                    @Override
                    public void onModelNotAvailable(final ErrorModel error) {
                        Log.e(TAG, "onModelNotAvailable: " + error.getMessage());
                        mView.hideProgress();
                        mView.showToast(error.getMessage());
                        mView.showSendMedicineUi();

                    }
                });
    }
}
