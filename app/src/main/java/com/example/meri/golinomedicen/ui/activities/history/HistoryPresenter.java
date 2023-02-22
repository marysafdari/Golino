package com.example.meri.golinomedicen.ui.activities.history;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.example.meri.golinomedicen.model.ErrorModel;
import com.example.meri.golinomedicen.model.PurchaseHistoryModel;
import com.example.meri.golinomedicen.repository.ModelInterface;
import com.example.meri.golinomedicen.repository.sources.local.pref.MyPrefManager;
import com.example.meri.golinomedicen.repository.sources.remote.models.PurchaseHistoryModelApi;

public class HistoryPresenter implements HistoryContract.Presenter {

    private static final String TAG = HistoryPresenter.class.getSimpleName();

    private HistoryContract.View mView;
    private ModelInterface mModel;

    public HistoryPresenter(HistoryContract.View mView, ModelInterface mModel) {
        this.mModel = mModel;
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void loadItems() {
        mView.showProgress();
        mModel.allPurchaseHistory(MyPrefManager.getInstance().getToken(), new ModelInterface
                .LoadModelsCallback<PurchaseHistoryModelApi>() {

            @Override
            public void onModelsLoaded(final List<PurchaseHistoryModelApi> models) {
                mView.hideProgress();
                List<PurchaseHistoryModel> items = new ArrayList<>();
                for (PurchaseHistoryModelApi modelApi : models) {
                    PurchaseHistoryModel item = new PurchaseHistoryModel();
                    item.setDate(modelApi.getDate());
                    item.setDate(modelApi.getDate());
                    item.setTime(modelApi.getTime());
                    item.setPharmacyName(modelApi.getPharmacy().getPharmacyName());
                    item.setPrice(String.valueOf(modelApi.getPrice()));
                    item.setImageUrl(modelApi.getPrescriptionDrug().getImage());
                    items.add(item);
                }
                mView.setItems(items);
            }

            @Override
            public void onModelNotAvailable(final ErrorModel error) {
                Log.e(TAG, "onModelNotAvailable: " + error.getMessage());
                mView.hideProgress();
                mView.showToast(error.getMessage());
            }
        });
    }
}
