package com.example.meri.golinomedicen.ui.activities.sendMedicine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;
import com.example.meri.golinomedicen.baseInterfaces.BasePresenter;
import com.example.meri.golinomedicen.baseInterfaces.BaseView;
import com.example.meri.golinomedicen.baseInterfaces.viewInterfaces.ActivityInterface;
import com.example.meri.golinomedicen.baseInterfaces.viewInterfaces.ContextInterface;

public interface SendMedicineContract {

    interface View extends BaseView<Presenter>, ContextInterface, ActivityInterface {

        void showMapUi(int requestCode);

        void showTakePicture(Uri photoUri, int requestCode);

        void showPicture(Bitmap pic);

        int getHeightImageView();

        int getWidthImageView();

        void visibleTakePhoto();

        void visibleDescriptionDrug();

        void showLocation(String location);

        void setDrugType(SendMedicinePresenter.TypeDrug type);
    }

    interface Presenter extends BasePresenter {

        void attachView(View view);

        void detachView();

        void takePicture();

        void getLocation();

        void sendDrug();

        void clickTypeDrug(SendMedicinePresenter.TypeDrug type);

        void onPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
                grantResults);

        void onActivityResult(final int requestCode, final int resultCode, final Intent data);

    }


}
