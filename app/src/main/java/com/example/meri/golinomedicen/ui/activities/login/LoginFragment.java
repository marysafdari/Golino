package com.example.meri.golinomedicen.ui.activities.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.meri.golinomedicen.R;
import com.example.meri.golinomedicen.ui.activities.IOnBackPressed;
import com.example.meri.golinomedicen.ui.activities.MyFragment;
import com.example.meri.golinomedicen.ui.activities.sendMedicine.SendMedicineActivity;
import com.example.meri.golinomedicen.ui.activities.signUp.SignUpActivity;
import com.example.meri.golinomedicen.utility.AppConstant;

public class LoginFragment extends MyFragment implements LoginContract.View, IOnBackPressed {


    private LoginContract.Presenter presenter;

    //region widget
//    @BindView(R.id.lin_lyt_send_number)
//    LinearLayout lin_lyt_send_number;
    @BindView(R.id.btn_send_number)
    Button btn_send_number;

//    @BindView(R.id.lin_lyt_code)
//    LinearLayout lin_lyt_send_code;
//    @BindView(R.id.btn_send_code)
//    Button btn_send_code;

    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;

    @BindView(R.id.lin_lyt_number)
    ConstraintLayout lin_lyt_number;
    @BindView(R.id.et_number)
    EditText et_number;

//    @BindView(R.id.lin_lyt_send_code)
//    LinearLayout lin_lyt_code;
//    @BindView(R.id.et_sms_code)
//    EditText et_sms_code;
    //endregion


    public LoginFragment() {
    }


    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup
            container, final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, root);

        btn_send_number.setOnClickListener(view -> {

            if (et_number.getText().toString().length() > 10 && et_number.getText().toString().startsWith("09")) {
                presenter.login(et_number.getText().toString());
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogCustom);
                View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.login_dialog_layout, (ViewGroup) getView(), false);
                builder.setView(viewInflated);
                builder.setPositiveButton("تأیید", (dialog, which) -> {
                    String inputCode = ((EditText) viewInflated.findViewById(R.id.et_dlg_input_code)).getText().toString();
                    if (inputCode.length() == 5) {
                        dialog.dismiss();
                        presenter.sendCode(inputCode);
                    } else {
                        Toast.makeText(getContext(), "کد فعال سازی اشتباه وارد شده است", Toast.LENGTH_LONG).show();
                    }
                    progress_bar.setVisibility(View.GONE);
                });
                builder.setNegativeButton("لغو", (dialog, which) ->
                {
                    dialog.cancel();
                    progress_bar.setVisibility(View.GONE);
                });

                builder.show();
            } else {
                Toast.makeText(getContext(), "شماره اشتباه وارد شده است", Toast.LENGTH_LONG).show();
            }
        });
        //btn_send_number.setOnClickListener(v -> presenter.login(et_number.getText().toString()));
        //btn_send_code.setOnClickListener(v -> presenter.sendCode(et_sms_code.getText().toString()));

        return root;
    }


    //region Implementation LoginContract.View
    @Override
    public void setPresenter(final LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgress() {
        progress_bar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress_bar.setVisibility(View.GONE);
    }

    @Override
    public void showSignUpUi(final String number) {
        Intent signUp = new Intent(getActivity(), SignUpActivity.class);
        signUp.putExtra(AppConstant.KEY_INTENT_NUMBER, number);
        startActivity(signUp);
    }

    @Override
    public void showSendMedicineUi() {
        Intent sendMedicine = new Intent(getActivity(), SendMedicineActivity.class);
        startActivity(sendMedicine);
    }

    @Override
    public void switchToSmsCode() {
        lin_lyt_number.setVisibility(View.GONE);
//        lin_lyt_send_number.setVisibility(View.GONE);
//        lin_lyt_code.setVisibility(View.VISIBLE);
//        lin_lyt_send_code.setVisibility(View.VISIBLE);
    }

    @Override
    public void switchToNumber() {
        lin_lyt_number.setVisibility(View.VISIBLE);
//        lin_lyt_send_number.setVisibility(View.VISIBLE);
//        lin_lyt_code.setVisibility(View.GONE);
//        lin_lyt_send_code.setVisibility(View.GONE);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }


    //endregion
}
