package com.example.meri.golinomedicen.ui.activities.signUp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.meri.golinomedicen.R;
import com.example.meri.golinomedicen.ui.activities.IOnBackPressed;
import com.example.meri.golinomedicen.ui.activities.MyFragment;
import com.example.meri.golinomedicen.ui.activities.sendMedicine.SendMedicineActivity;
import com.example.meri.golinomedicen.utility.AppConstant;

public class SignUpFragment extends MyFragment implements SignUpContract.View, IOnBackPressed {

    private SignUpContract.Presenter presenter;

    @BindView(R.id.et_number)
    EditText et_number;
    @BindView(R.id.et_name_family)
    EditText et_name_family;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_code_presentation)
    EditText et_code_presentation;
    @BindView(R.id.et_addr)
    TextView adress;
//    @BindView(R.id.cb_role)
//    CheckBox cb_role;

    @BindView(R.id.btn_confirm)
    Button btn_confirm;

    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.layoutSignup)
    LinearLayout layoutSignup;

    private Bundle extras;


    public SignUpFragment() {
    }


    public static SignUpFragment newInstance(Bundle extras) {
        SignUpFragment fragment = new SignUpFragment();
        fragment.extras = extras;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup
            container, @Nullable final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this, root);

        if (extras != null)
            et_number.setText(extras.getString(AppConstant.KEY_INTENT_NUMBER));

        btn_confirm.setOnClickListener(v ->
                presenter.registerInformation(et_number.getText().toString(),
                        et_number.getText().toString(),
                        et_email.getText().toString(),
                        et_code_presentation.getText().toString(),
                        true));
        // جاگذاری آدرس دریافت شده در فیلد آدرس
        if (getActivity().getIntent().getBooleanExtra("hasData", false)) {
            String address = getActivity().getIntent().getStringExtra("adress");
            Log.e("Fragment", "setupMVP: Address " + address);
            adress.setText(address);
        }
        layoutSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),FavAdress.class));
            }
        });
        return root;
    }

    //region Implementation SignUpContract.View
    @Override
    public void showProgress() {
        progress_bar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress_bar.setVisibility(View.GONE);
    }

    @Override
    public void showSendMedicineUi() {
        startActivity(new Intent(getContext(), SendMedicineActivity.class));
        if (getActivity() != null)
            getActivity().finish();

    }

    @Override
    public void setPresenter(final SignUpContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
    //endregion
}
