package com.example.meri.golinomedicen.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.meri.golinomedicen.R;
import com.example.meri.golinomedicen.model.AdressModel;
import com.example.meri.golinomedicen.ui.activities.maps.GetLocationActivity;
import com.example.meri.golinomedicen.ui.activities.signUp.SignUpActivity;

/**
 * Created by Reza on 06/10/2016.
 */
public class AdapterAdress extends RecyclerView.Adapter<AdapterAdress.ViewHolder> {
    private static String TAG = AdapterAdress.class.getSimpleName();
    private Context context;
    private List<AdressModel> list;
    private LayoutInflater layoutInflater;
//    private ApplicationModel model;

    public AdapterAdress(Context context, List<AdressModel> list) {
        this.context = context;
        this.list = list;
        //this.model = model;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.layout_adress_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final AdressModel model = list.get(position);
        holder.Title.setText(model.getTitle());
        String Desc = model.getCity() + " " + model.getRoad() + " پلاک " + model.getPelak() + " طبقه " + model.getStair() + " واحد " + model.getUnit();
        holder.Description.setText(Desc);

        holder.editAdress.setOnClickListener(view -> {
            Intent intent = new Intent(context,GetLocationActivity.class);
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("id",model.getId());
            editor.putBoolean("Exist",true);
            editor.apply();
            intent.putExtra("lat",model.getLat());
            intent.putExtra("long",model.getLong());
            context.startActivity(intent);


        });
        holder.row.setOnClickListener(view -> {
            Intent intent = new Intent(context, SignUpActivity.class);
            intent.putExtra("adress", Desc);
            intent.putExtra("hasData", true);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView row;
        ImageButton editAdress;
        TextView Title, Description;

        public ViewHolder(View itemView) {
            super(itemView);
            row = itemView.findViewById(R.id.adress_row);
            editAdress = itemView.findViewById(R.id.edit_adress);
            Title = itemView.findViewById(R.id.title_adress);
            Description = itemView.findViewById(R.id.description_adress);
        }
    }
}
