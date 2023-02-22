package com.example.meri.golinomedicen.ui.activities.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.meri.golinomedicen.R;
import com.example.meri.golinomedicen.model.PurchaseHistoryModel;
import com.example.meri.golinomedicen.ui.activities.MyFragment;
import com.example.meri.golinomedicen.ui.adapters.HistoryAdapter;

public class HistoryFragment extends MyFragment implements HistoryContract.View {


    //region Variables
    private static final String TAG = HistoryFragment.class.getSimpleName();


    private HistoryContract.Presenter presenter;

    //region widget
    @BindView(R.id.rv_history)
    RecyclerView rv_history;

    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    //endregion

    private List<PurchaseHistoryModel> items = new ArrayList<>();
    private HistoryAdapter adapter;
    //endregion

    public HistoryFragment() {
    }


    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);

        ButterKnife.bind(this, root);


        rv_history.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        adapter = new HistoryAdapter(getContext(), items);
        rv_history.setAdapter(adapter);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadItems();
    }

    @Override
    public void setPresenter(final HistoryContract.Presenter presenter) {
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
    public void setItems(final List<PurchaseHistoryModel> items) {
        this.items = items;
        adapter.updateItems(items);
    }
}
