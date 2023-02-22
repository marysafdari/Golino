package com.example.meri.golinomedicen.ui.activities.maps;

import com.example.meri.golinomedicen.repository.ModelInterface;

public class MapsPresenter implements MapsContract.Presenter {

    private MapsContract.View mView;
    private ModelInterface mModel;

    public MapsPresenter(MapsContract.View mView, final ModelInterface model) {
        this.mModel = model;
        this.mView = mView;
    }



}
