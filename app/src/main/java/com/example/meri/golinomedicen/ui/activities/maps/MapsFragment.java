package com.example.meri.golinomedicen.ui.activities.maps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.meri.golinomedicen.R;
import com.example.meri.golinomedicen.ui.activities.MyFragment;

public class MapsFragment extends MyFragment implements MapsContract.View {

    private MapsContract.Presenter presenter;


    //region widget
    @BindView(R.id.lin_lyt_search)
    LinearLayout lin_lyt_search;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.ic_gps)
    AppCompatImageView ic_gps;
    @BindView(R.id.btn_confirm)
    Button btn_confirm;
    //endregion

    public MapsFragment() {
    }

    public static MapsFragment newInstance() {
        return new MapsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup
            container, @Nullable final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_maps, container, false);

        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void setPresenter(final MapsContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
