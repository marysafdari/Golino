package com.example.meri.golinomedicen.baseInterfaces;

import com.example.meri.golinomedicen.baseInterfaces.viewInterfaces.ContextInterface;
import com.example.meri.golinomedicen.baseInterfaces.viewInterfaces.ShowToast;

public interface BaseView<T> extends ShowToast, ContextInterface {

    void setPresenter(T presenter);

}
