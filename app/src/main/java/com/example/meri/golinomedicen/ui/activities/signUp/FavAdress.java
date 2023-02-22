package com.example.meri.golinomedicen.ui.activities.signUp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.meri.golinomedicen.R;

import com.example.meri.golinomedicen.ui.activities.maps.GetLocationActivity;
import com.example.meri.golinomedicen.ui.adapters.AdapterAdress;
import com.example.meri.golinomedicen.utility.DatabaseHandler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import java.util.ArrayList;

public class FavAdress extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.recycler_adress)
    RecyclerView recyAdress;
    @BindView(R.id.add_address_FavAdress)
    AppCompatButton addAdress;

    ArrayList arrayAdress;
    AdapterAdress adapterAdress;
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_adress);
        ButterKnife.bind(this);
        addAdress.setOnClickListener(this);
        arrayAdress = new ArrayList<>();
        db = new DatabaseHandler(this);
        arrayAdress.addAll(db.getAllAdress());

        adapterAdress = new AdapterAdress(getApplicationContext(), arrayAdress);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyAdress.setHasFixedSize(true);
        recyAdress.setLayoutManager(layoutManager);
        recyAdress.setAdapter(adapterAdress);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_address_FavAdress:
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("Exist",false);
                editor.apply();
                startActivity(new Intent(this, GetLocationActivity.class));
                break;
        }
    }


}
