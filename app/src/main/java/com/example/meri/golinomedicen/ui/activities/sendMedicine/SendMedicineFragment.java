package com.example.meri.golinomedicen.ui.activities.sendMedicine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.meri.golinomedicen.R;
import com.example.meri.golinomedicen.ui.activities.MyFragment;
import com.example.meri.golinomedicen.ui.activities.maps.MapsActivity;

public class SendMedicineFragment extends MyFragment implements SendMedicineContract.View {

    private static final String TAG = SendMedicineFragment.class.getSimpleName();

    private Context mContext;
    private SendMedicineContract.Presenter presenter;

    //region widgets
    @BindView(R.id.lin_lyt_add_photo)
    LinearLayout lin_lyt_add_photo;
//    @BindView(R.id.lin_lyt_location)
//    LinearLayout lin_lyt_location;
    @BindView(R.id.iv_prescription_drug)
    ImageView iv_prescription_drug;
//    @BindView(R.id.tv_location)
//    TextView tv_location;
    @BindView(R.id.btn_send_drug)
    Button btn_send_drug;

    @BindView(R.id.rb_rare)
    AppCompatRadioButton rb_rare;
    @BindView(R.id.rb_choice)
    AppCompatRadioButton rb_choice;
    @BindView(R.id.rb_normal)
    AppCompatRadioButton rb_normal;

    //endregion

    private int width_iv_prescription_drug;
    private int height_iv_prescription_drug;


    public SendMedicineFragment() {
    }

    public static SendMedicineFragment newInstance() {
        return new SendMedicineFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_send_medicine, container, false);

        ButterKnife.bind(this, root);


        lin_lyt_add_photo.setOnClickListener(v -> presenter.takePicture());
//        lin_lyt_location.setOnClickListener(v -> presenter.getLocation());
        btn_send_drug.setOnClickListener(v -> presenter.sendDrug());

        rb_choice.setOnClickListener(v -> presenter.clickTypeDrug(SendMedicinePresenter.TypeDrug.CHOICE));
        rb_rare.setOnClickListener(v -> presenter.clickTypeDrug(SendMedicinePresenter.TypeDrug.RARE));
        rb_normal.setOnClickListener(v -> presenter.clickTypeDrug(SendMedicinePresenter.TypeDrug.NORMAL));
        setDrugType(SendMedicinePresenter.TypeDrug.NORMAL);

        // for activityResult problem
        ViewTreeObserver vto = root.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(() -> {
            if (iv_prescription_drug.getVisibility() == View.VISIBLE) {
                width_iv_prescription_drug = iv_prescription_drug.getWidth();
                height_iv_prescription_drug = iv_prescription_drug.getHeight();
            }
        });

        return root;
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        mContext = context;
    }

    //region implementation SendMedicineContract.View

    //region location
    @Override
    public void showMapUi(int requestCode) {
        startActivityForResult(new Intent(getContext(), MapsActivity.class), requestCode);

    }

    @Override
    public void showLocation(String location) {
        Log.e(TAG, "showLocation: " + location);
//        tv_location.setText(location);
    }
    //endregion

    //region Picture
    @Override
    public void showTakePicture(Uri photoUri, int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivityFragment().getPackageManager()) != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(takePictureIntent, requestCode);
        }
    }

    @Override
    public void showPicture(final Bitmap pic) {
        iv_prescription_drug.setImageBitmap(pic);
    }

    @Override
    public int getHeightImageView() {
        return height_iv_prescription_drug;
    }


    @Override
    public int getWidthImageView() {
        return width_iv_prescription_drug;
    }

    @Override
    public void visibleTakePhoto() {
        lin_lyt_add_photo.setVisibility(View.VISIBLE);
        iv_prescription_drug.setVisibility(View.GONE);
    }

    @Override
    public void visibleDescriptionDrug() {
        lin_lyt_add_photo.setVisibility(View.GONE);
        iv_prescription_drug.setVisibility(View.VISIBLE);
    }
    //endregion

    @Override
    public void setPresenter(final SendMedicineContract.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public Context getContextView() {
        return mContext;
    }


    @Override
    public void setDrugType(final SendMedicinePresenter.TypeDrug type) {
        rb_normal.setChecked(false);
        rb_rare.setChecked(false);
        rb_choice.setChecked(false);

        switch (type) {
            case NORMAL:
                rb_normal.setChecked(true);
                break;
            case RARE:
                rb_rare.setChecked(true);
                break;
            case CHOICE:
                rb_choice.setChecked(true);
                break;
            default:
                Log.e(TAG, "setDrugType: default type happen!!!");
        }
    }


    //endregion

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions,
                                           @NonNull final int[] grantResults) {
        presenter.onPermissionResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        presenter.onActivityResult(requestCode, resultCode, data);
    }

}
