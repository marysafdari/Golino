package com.example.meri.golinomedicen.ui.activities.history;

import java.util.List;

import com.example.meri.golinomedicen.baseInterfaces.BasePresenter;
import com.example.meri.golinomedicen.baseInterfaces.BaseView;
import com.example.meri.golinomedicen.baseInterfaces.viewInterfaces.ProgressBarInterface;
import com.example.meri.golinomedicen.model.PurchaseHistoryModel;

public interface HistoryContract {

    interface View extends BaseView<HistoryContract.Presenter>, ProgressBarInterface {

        void setItems(List<PurchaseHistoryModel> items);
    }

    interface Presenter extends BasePresenter {
        void loadItems();

    }

}
