package com.example.meri.golinomedicen.ui.activities.maps;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.meri.golinomedicen.R;
import com.google.android.material.textfield.TextInputEditText;


import com.example.meri.golinomedicen.model.AdressModel;
import com.example.meri.golinomedicen.ui.activities.signUp.SignUpActivity;
import com.example.meri.golinomedicen.utility.DatabaseHandler;

public class MyDialogFragment extends DialogFragment {
    int mNum;
String lat,lon;
    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    static MyDialogFragment newInstance(int num, double latitude, double longitude) {
        MyDialogFragment f = new MyDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        args.putString("lat", String.valueOf(latitude));
        args.putString("long", String.valueOf(longitude));
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments().getInt("num");
        lat = getArguments().getString("lat");
        lon = getArguments().getString("long");
        int style = DialogFragment.STYLE_NO_TITLE, theme = R.style.Theme_AppCompat_DayNight_Dialog_Alert;
        // Pick a style based on the num.
        /*
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        switch ((mNum-1)%6) {
            case 1: style = DialogFragment.STYLE_NO_TITLE; break;
            case 2: style = DialogFragment.STYLE_NO_FRAME; break;
            case 3: style = DialogFragment.STYLE_NO_INPUT; break;
            case 4: style = DialogFragment.STYLE_NORMAL; break;
            case 5: style = DialogFragment.STYLE_NORMAL; break;
            case 6: style = DialogFragment.STYLE_NO_TITLE; break;
            case 7: style = DialogFragment.STYLE_NO_FRAME; break;
            case 8: style = DialogFragment.STYLE_NORMAL; break;
        }
        switch ((mNum-1)%6) {
            case 4: theme = android.R.style.Theme_Holo; break;
            case 5: theme = android.R.style.Theme_Holo_Light_Dialog; break;
            case 6: theme = android.R.style.Theme_Holo_Light; break;
            case 7: theme = android.R.style.Theme_Holo_Light_Panel; break;
            case 8: theme = android.R.style.Theme_Holo_Light; break;
        }
        */
        setStyle(style, theme);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog, container, false);
        TextInputEditText editTitle = v.findViewById(R.id.editTitle);
        TextInputEditText editCity = v.findViewById(R.id.editCity);
        TextInputEditText editRoad = v.findViewById(R.id.editRoad);
        TextInputEditText editPelak = v.findViewById(R.id.editPelak);
        TextInputEditText editStair = v.findViewById(R.id.editStair);
        TextInputEditText editUnit = v.findViewById(R.id.editUnit);

        // Watch for button clicks.
        Button btnOk = v.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // When button is clicked, call up to owning activity.
               // ((FragmentDialog)getActivity()).showDialog();
                String title = editTitle.getText().toString();
                String city = editCity.getText().toString();
                String road = editRoad.getText().toString();
                String pelak = editPelak.getText().toString();
                String stair = editStair.getText().toString();
                String unit = editUnit.getText().toString();
                DatabaseHandler db = new DatabaseHandler(getActivity());
                db.insertAdress(title,city,road,pelak,stair,unit,lon,lat);
                //startActivity(new Intent(getActivity(), SignUpActivity.class));
                String Desc = city+" "+ road + " پلاک "+ pelak + " طبقه "+ stair + " واحد "+ unit ;
                Intent intent = new Intent(getActivity(), SignUpActivity.class);
                intent.putExtra("adress", Desc);
                intent.putExtra("hasData", true);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean state = sp.getBoolean("Exist", false);
        int id = sp.getInt("id", 0);
        if (state){
            DatabaseHandler db = new DatabaseHandler(getActivity());
            AdressModel adressModel = db.getAdress(id);
            editTitle.setText(adressModel.getTitle());
            editCity.setText(adressModel.getCity());
            editRoad.setText(adressModel.getRoad());
            editPelak.setText(adressModel.getPelak());
            editStair.setText(adressModel.getStair());
            editUnit.setText(adressModel.getUnit());
        }
        return v;
    }
}
