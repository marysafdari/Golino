package com.example.meri.golinomedicen.ui.activities.maps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.meri.golinomedicen.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.meri.golinomedicen.ui.activities.MyActivity;
import com.example.meri.golinomedicen.ui.dialogs.DialogLocation;
import com.example.meri.golinomedicen.utility.AppConstant;

public class MapsActivity extends MyActivity implements OnMapReadyCallback {

    //region Variable

    //region final variables
    private static final String TAG = MapsActivity.class.getSimpleName();

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    private static final float DEFAULT_ZOOM = 15f;
    private static final float TEHRAN_ZOOM = 12f;

    private static final double LATITUDE_DEFAULT = 35.715298;
    private static final double LONGITUDE_DEFAULT = 51.404343;

    private static final String TITLE_MY_LOCATION = "My Location";


    public static final String EXTRA_KEY_LOCATION = "location";
    //endregion

    private GoogleMap mMap;

    //region widget
    @BindView(R.id.lin_lyt_search)
    LinearLayout lin_lyt_search;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.ic_gps)
    AppCompatImageView ic_gps;
    @BindView(R.id.loc_img)
    AppCompatImageView addLocation;
    @BindView(R.id.btn_confirm)
    Button btn_confirm;
    //endregion

    private String locale;
    private Geocoder geocoder;

    private DialogLocation dialogLocation;

    private boolean firstShowDialogLocation = true;


    private MapsContract.Presenter presenter;

    //endregion

    //region LifeCycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ButterKnife.bind(this);

        // setupMVP();

        isServicesOK();
        initMap();
    }

    // private void setupMVP() {
    //     MapsFragment fragment = (MapsFragment) getSupportFragmentManager().findFragmentById(R.id
    //             .content_frame);
    //
    //     if (fragment == null) {
    //         fragment = MapsFragment.newInstance();
    //         ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id
    //                 .content_frame);
    //     }
    //
    //     presenter = (MapsPresenter) getLastCustomNonConfigurationInstance();
    //     if (presenter == null)
    //         presenter = new MapsPresenter(fragment, AppController.getInstance().getDataModel());
    //
    // }

    //region initialize


    private void initMap() {
        Log.e(TAG, "initMap:");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e(TAG, "onMapReady: map is ready");

        mMap = googleMap;
        init();

        getDeviceLocation();
    }

    private void init() {
        Log.e(TAG, "init:");
        // بررسی مجوز های مروط به لوکیشن
        if (ActivityCompat.checkSelfPermission(this, FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            mMap.setMyLocationEnabled(true);

        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMap!=null){

                }
            }
        });

        dialogLocation = new DialogLocation(this, new DialogLocation.ClickListener() {
            @Override
            public void setting() {
                Log.e(TAG, "setting: ");
                startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), AppConstant.REQUEST_CODE_SETTING_LOCATION);
            }

            @Override
            public void onDismiss() {
                if (firstShowDialogLocation) {
                    firstShowDialogLocation = false;
                    moveCamera(LATITUDE_DEFAULT, LONGITUDE_DEFAULT, TEHRAN_ZOOM);
                }

            }
        });

        ic_gps.setOnClickListener(v -> getDeviceLocation());

        et_search.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            Log.e(TAG, "onEditorAction: actionId = " + actionId);
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                    || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                geoLocate();
            }
            return false;
        });

        lin_lyt_search.setOnClickListener(v -> geoLocate());

        btn_confirm.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_KEY_LOCATION, locale);
            setResult(RESULT_OK, intent);
            finish();
        });

        geocoder = new Geocoder(this, new Locale("fa"));//Locale.getDefault());

//        mMap.setOnMapLongClickListener(latLng -> {
//            Log.e(TAG, "onMapLongClick: " + latLng.latitude + " , " + latLng.longitude);
//            moveCamera(latLng, DEFAULT_ZOOM, TITLE_MY_LOCATION);
//        });



    }
    //endregion


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent
            data) {
        Log.e(TAG, "onActivityResult: ");

        if (requestCode == AppConstant.REQUEST_CODE_SETTING_LOCATION) {
            if (gpsEnabled()) {
                Log.e(TAG, "onActivityResult: location on");
                getMyLocation();
            } else
                Log.e(TAG, "onActivityResult: location off ");

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.e(TAG, "onRequestPermissionsResult: called.");

        switch (requestCode) {
            case AppConstant.REQUEST_CODE_PERMISSION_LOCATION:
                if (grantResults.length > 0) {
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        Log.e(TAG, "onRequestPermissionsResult: permission failed");
                        return;
                    }
                }
                Log.e(TAG, "onRequestPermissionsResult: permission granted");
                initMap();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Log.e(TAG, "onBackPressed: ");
        Intent location = new Intent();
        setResult(2, location);
        super.onBackPressed();
    }
    //endregion

    //region onCreate Method
    public boolean isServicesOK() {
        Log.e(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            Log.e(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occurred but we can resolve it
            Log.e(TAG, "isServicesOK: an error occurred but we can fix it");
            GoogleApiAvailability.getInstance().getErrorDialog(this, available, 9001).show();
        } else
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean gpsEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context
                .LOCATION_SERVICE);
        return locationManager != null && locationManager.isProviderEnabled(LocationManager
                .GPS_PROVIDER);
    }

    //endregion

    private void getDeviceLocation() {
        Log.e(TAG, "getDeviceLocation: ");
        if (getLocationPermission()) {
            if (gpsEnabled())
                getMyLocation();
            else
                dialogLocation.show();
        }

    }

    // TODO: 8/25/18 must be set progress bar and periodic call getMyLocation.
    private void getMyLocation() {
        Log.e(TAG, "getMyLocation: ");

        if (ActivityCompat.checkSelfPermission(this, FINE_LOCATION) == PackageManager
                .PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, COARSE_LOCATION) == PackageManager
                        .PERMISSION_GRANTED) {
            FusedLocationProviderClient mFusedLocationProviderClient = LocationServices
                    .getFusedLocationProviderClient(this);
            mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, location1 -> {
                if (location1 != null) {
                    Log.e(TAG, "onComplete: found location!");
                    //moveCamera(location1, locale); // 2nd line test
                    try {
                        //moveCamera(location1, locale); // 3rd line test
                        List<Address> list = geocoder.getFromLocation(location1.getLatitude(), location1.getLongitude(), 1);
                        if (list.size() > 0)
                            locale = list.get(0).getAddressLine(list.get(0).getMaxAddressLineIndex());//.getFeatureName();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    moveCamera(location1, locale); //def line
                } else {
                    Log.e(TAG, "onComplete: current location is null");
                    // TODO: 8/25/18 must be set progress bar and preiodic call this methid
                }
            });
        }
    }

    //region move camera
    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.e(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " +
                latLng.longitude);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if (!title.equals("")) {
            mMap.clear();
            MarkerOptions options = new MarkerOptions().position(latLng).title(title);
            mMap.addMarker(options);
        }

        hideSoftKeyboard();
    }

    private void moveCamera(Location location, float zoom, String title) {
        moveCamera(new LatLng(location.getLatitude(), location.getLongitude()), zoom, title);
    }

    private void moveCamera(Location location) {
        moveCamera(location, DEFAULT_ZOOM, "");
    }

    private void moveCamera(Location location, String title) {
        moveCamera(location, DEFAULT_ZOOM, title);
    }

    private void moveCamera(double latitude, double longitude, float zoom) {
        moveCamera(new LatLng(latitude, longitude), zoom, "");
    }
    //endregion

    private boolean getLocationPermission() {

        String[] permissions = {FINE_LOCATION, COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this, FINE_LOCATION) == PackageManager
                .PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, COARSE_LOCATION) == PackageManager
                        .PERMISSION_GRANTED)
            return true;
        else
            ActivityCompat.requestPermissions(this, permissions, AppConstant
                    .REQUEST_CODE_PERMISSION_LOCATION);

        return false;
    }

    private void geoLocate() {
        Log.e(TAG, "geoLocate: geo locating");

        String searchString = et_search.getText().toString();

        List<Address> list = new ArrayList<>();

        try {
            list = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
        }

        if (list.size() > 0) {
            Address address = list.get(0);

            Log.e(TAG, "geoLocate: found a location: " + address.toString());
            locale = address.getFeatureName();

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                    address.getAddressLine(0));
        }

        hideSoftKeyboard();
    }


}

