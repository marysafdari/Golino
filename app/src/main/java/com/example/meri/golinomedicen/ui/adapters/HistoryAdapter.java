package com.example.meri.golinomedicen.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.meri.golinomedicen.R;
import com.example.meri.golinomedicen.model.PurchaseHistoryModel;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private static final String TAG = HistoryAdapter.class.getSimpleName();

    private Context context;

    private List<PurchaseHistoryModel> items;

    public HistoryAdapter(Context context, List<PurchaseHistoryModel> items) {
        this.context = context;
        this.items = items;
    }


    //region RecyclerView.Adapter Methods
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_item_rv_history, parent, false);
        return new HistoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        PurchaseHistoryModel item = items.get(position);

        Log.e(TAG, "onBindViewHolder: " + item);

        holder.tv_name_medicine.setText(item.getPharmacyName());
        holder.tv_date.setText(item.getDate());
        holder.tv_price.setText(item.getPrice());
        holder.tv_time.setText(item.getTime());
        // holder.iv_prescription_drug.setImageBitmap(null);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    //endregion


    public void updateItems(List<PurchaseHistoryModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name_medicine)
        TextView tv_name_medicine;
        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.iv_prescription_drug)
        ImageView iv_prescription_drug;


        public MyViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
