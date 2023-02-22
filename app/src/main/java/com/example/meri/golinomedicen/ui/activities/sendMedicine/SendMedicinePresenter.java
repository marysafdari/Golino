package com.example.meri.golinomedicen.ui.activities.sendMedicine;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;

import com.example.meri.golinomedicen.BuildConfig;
import com.example.meri.golinomedicen.repository.ModelInterface;
import com.example.meri.golinomedicen.ui.activities.maps.MapsActivity;
import com.example.meri.golinomedicen.utility.AppConstant;

import static android.app.Activity.RESULT_OK;

public class SendMedicinePresenter implements SendMedicineContract.Presenter {

    //region Variables

    //region static final variables
    private static final String TAG = SendMedicinePresenter.class.getSimpleName();

    private static final String permissionCamera = Manifest.permission.CAMERA;
    private static final String permissionLocation = Manifest.permission.ACCESS_COARSE_LOCATION;


    private static final int REQUEST_CODE_PERMISSION_TAKE_PHOTO = 1;
    private static final int REQUEST_CODE_PERMISSION_LOCATION = 2;

    private static final int REQUEST_CODE_TAKE_PHOTO = 1;
    private static final int REQUEST_CODE_GET_LOCATION = 2;
    //endregion

    private SendMedicineContract.View mView;
    private ModelInterface mModel;

    private File pictureFile = null;

    private boolean cameraPermission;//false
    private boolean locationPermission;//false

    private TypeDrug typeDrug;
    //endregion


    public SendMedicinePresenter(SendMedicineContract.View mView, final ModelInterface model) {
        this.mModel = model;
        attachView(mView);

    }

    //region take picture
    @Override
    public void takePicture() {

        if (cameraPermission||getPermissionCamera()) dispatchTakePictureIntent();

    }

    private void dispatchTakePictureIntent() {
        try {
            // Create the File where the photo should save
            pictureFile = createImageFile();
            if (pictureFile != null) {
                Uri photoURI = FileProvider.getUriForFile(mView.getContextView(), BuildConfig
                        .APPLICATION_ID, pictureFile);
                Log.e(TAG, "dispatchTakePictureIntent: uri photo = " + photoURI);
                mView.showTakePicture(photoURI, REQUEST_CODE_TAKE_PHOTO);
                mView.visibleDescriptionDrug();
            }
        } catch (IOException ex) {
            Log.e(TAG, "dispatchTakePictureIntent: could not create file for photo");
            mView.showToast("قادر به گرفتن عکس نمی باشیم!");
        }

    }

    private File createImageFile() throws IOException {
        String imageFileName = "JPEG_";
        File storageDir = mView.getContextView().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        Log.e(TAG, "createImageFile: storage directory : " + image.getAbsolutePath());
        return image;
    }

    //region show captured image
    private void showCapturedImage() {
        String path = pictureFile.getPath();
        Bitmap bitmapPic;
        if ((bitmapPic = makeUpPicture(path)) != null) mView.showPicture(bitmapPic);
        else mView.showToast("عکس گرفته نشده است!");
    }

    private Bitmap makeUpPicture(String imagePath) {

        if (imagePath.isEmpty()) return null;

        // Get the dimensions of the View
        int targetW = mView.getWidthImageView();
        int targetH = mView.getHeightImageView();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(imagePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        Bitmap bm = BitmapFactory.decodeFile(imagePath, bmOptions);

        try {
            ExifInterface exif = new ExifInterface(imagePath);
            String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
            int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface
                    .ORIENTATION_NORMAL;
            int rotationAngle = 0;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;

            Matrix matrix = new Matrix();
            matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);

            return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //endregion


    //endregion

    //region get location
    @Override
    public void getLocation() {
        if (locationPermission||getLocationPermission()) goToMapActivity();

    }

    private void goToMapActivity() {
        mView.showMapUi(REQUEST_CODE_GET_LOCATION);
    }
    //endregion

    //region permissions

    // TODO: 11/10/18 check this function for 'else return cameraPermission = true'
    private boolean getPermissionCamera() {
        Log.e(TAG, "getCameraPermission: ");

        String[] permissions = {permissionCamera};

        cameraPermission = ContextCompat.checkSelfPermission(mView.getContextView(), permissionCamera) ==
                PackageManager.PERMISSION_GRANTED;

        if (!cameraPermission)
            ActivityCompat.requestPermissions(mView.getActivityFragment(), permissions,
                    REQUEST_CODE_PERMISSION_TAKE_PHOTO);
        return cameraPermission;
    }

    private boolean getLocationPermission() {
        Log.e(TAG, "getLocationPermission: ");

        String[] permissions = {permissionLocation};
        if (ContextCompat.checkSelfPermission(mView.getContextView(), permissionLocation) != PackageManager
                .PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(mView.getActivityFragment(), permissions, AppConstant
                    .REQUEST_CODE_PERMISSION_LOCATION);
        else return locationPermission = true;
        return false;
    }

    @Override
    public void onPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_TAKE_PHOTO:
                if (isGrantedPermission(grantResults)) {
                    Log.e(TAG, "onRequestPermissionsResult: permission camera granted");
                    cameraPermission = true;
                    dispatchTakePictureIntent();
                } else {
                    Log.e(TAG, "onRequestPermissionsResult: permission camera failed");
                    mView.showToast("برای ارسال نسخه نیاز به عکس نسخه داریم!!");
                    cameraPermission = false;
                }
                break;
            case REQUEST_CODE_PERMISSION_LOCATION:
                if (isGrantedPermission(grantResults)) {
                    Log.e(TAG, "onRequestPermissionsResult: permission location granted");
                    locationPermission = true;
                    goToMapActivity();
                } else {
                    Log.e(TAG, "onRequestPermissionsResult: permission location failed");
                    mView.showToast("برای ارسال نسخه نیاز به ادرس داریم!!");
                    locationPermission = false;
                }
                break;
            default:
                Log.e(TAG, "onPermissionResult: default case happened");
        }
    }

    private boolean isGrantedPermission(@NonNull int[] grantResults) {
        return (grantResults.length > 0&&grantResults[0] == PackageManager.PERMISSION_GRANTED);

    }

    //endregion


    @Override
    public void sendDrug() {
        Log.e(TAG, "sendDrug: ");
        mView.showToast("ارسال دارو");
    }

    @Override
    public void attachView(final SendMedicineContract.View view) {
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_TAKE_PHOTO:
                if (resultCode == RESULT_OK) showCapturedImage();
                else mView.visibleTakePhoto();
                break;
            case REQUEST_CODE_GET_LOCATION:
                Log.e(TAG, "onActivityResult: location");
                Bundle extras = data.getExtras();
                if (extras != null) mView.showLocation(extras.getString(MapsActivity.EXTRA_KEY_LOCATION));
                break;
            default:
                Log.e(TAG, "onActivityResult: default case happened");
        }

    }

    @Override
    public void clickTypeDrug(final TypeDrug type) {
        typeDrug = type;
        mView.setDrugType(type);
    }

    enum TypeDrug {
        NORMAL, RARE, CHOICE

    }


}
