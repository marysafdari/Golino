package com.example.meri.golinomedicen.ui.activities.maps;

import android.Manifest;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.meri.golinomedicen.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;


import com.example.meri.golinomedicen.ui.activities.MyActivity;
import com.example.meri.golinomedicen.ui.dialogs.DialogLocation;
import com.example.meri.golinomedicen.utility.AppConstant;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class GetLocationActivity extends MyActivity implements OnMapReadyCallback, View.OnClickListener {

    //region Variable

    //region final variables
    private static final String TAG = GetLocationActivity.class.getSimpleName();

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

    @BindView(R.id.loc_img)
    AppCompatImageView addLocation;
    @BindView(R.id.btn_add_detail)
    Button btnAddDetail;
    @BindView(R.id.btn_cancel_detail)
    Button btnCancel;
    //endregion

    private String locale;
    private Geocoder geocoder;
    private DialogLocation dialogLocation;
    private boolean firstShowDialogLocation = true;
    private MapsContract.Presenter presenter;
    GoogleApiClient client;
    Location userLocation;
    //endregion

    SupportMapFragment mapFragment;
    SettingsClient mSettingsClient;
    FusedLocationProviderClient mFusedLocationClient;
    int Request_PERMISSION_CODE = 1;
    double latitude = 0.0, longitude = 0.0, Altitude = 0.0;
    double lat = 0.0, lon = 0.0, Alt = 0.0;
    double Accuracy;// میزان خطای موجود در مکان یابی است.
    Location mCurrentLocation;
    LocationCallback mLocationCallback;
    LatLng Origin;
    LocationRequest mLocationRequest;
    LocationSettingsRequest mLocationSettingsRequest;
    static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000000000;  //10000
    static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    //region LifeCycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);

        ButterKnife.bind(this);
        initView();

        isServicesOK();
        initMap();

        // initMap();
    }

    private void initView() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);
        btnAddDetail.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_detail:
                showDialogFragment();
                break;
            case R.id.btn_cancel_detail:
                onBackPressed();
                break;
        }

    }

    private void showDialogFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment newFragment = MyDialogFragment.newInstance(6, latitude, longitude);
        newFragment.show(ft, "dialog");

    }

    private void initMap() {
        if (mMap == null) {
            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e(TAG, "onMapReady: map is ready");

        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            mMap.setMyLocationEnabled(true);

        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMap != null) {
// در صورت نیاز به کاری برای دکمه مارکر بر روی نقشه اینجا جاگذاری شود.
                }
            }
        });
        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                latitude = cameraPosition.target.latitude;
                longitude = cameraPosition.target.longitude;
            }
        });
        // بررسی منبع ارجاع که آیا قبلا لوکیشن موجود بوده و الان میخواهیم ویرایش کنیم یا نه آدرس جدید می باشد
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean state = sp.getBoolean("Exist", false);

        //if (getIntent().getStringExtra("NewOrExist").equals("Exist")) {
        if (state) {
            double lat = Double.valueOf(getIntent().getStringExtra("lat"));
            double lon = Double.valueOf(getIntent().getStringExtra("long"));
            LatLng coordinate = new LatLng(lat, lon);
            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(coordinate, 15f);
            mMap.animateCamera(location);
        }else {
            checkPermision();
        }
        //moveCamera(LATITUDE_DEFAULT, LONGITUDE_DEFAULT, TEHRAN_ZOOM);


    }
    //endregion

    // متد بررسی مجوز ها
    private void checkPermision() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            if (ContextCompat.checkSelfPermission(GetLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(GetLocationActivity.this
                        , permissions, Request_PERMISSION_CODE);
            } else if (ContextCompat.checkSelfPermission(GetLocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(GetLocationActivity.this
                        , permissions, Request_PERMISSION_CODE);
            } else {
             //   initMap();
                showMyLoaction();
                createLocationCallback();
                createLocationRequest();
                buildLocationSettingsRequest();
            }
        } else {
          //  initMap();
            showMyLoaction();
            createLocationCallback();
            createLocationRequest();
            buildLocationSettingsRequest();


        }
    }


    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                mCurrentLocation = locationResult.getLastLocation();
                updateLocationUI();
            }
        };
    }

    private void updateLocationUI() {
        if (mCurrentLocation != null) {
            try {
                latitude = mCurrentLocation.getLatitude();
                longitude = mCurrentLocation.getLongitude();
                Altitude = mCurrentLocation.getAltitude();
                Accuracy = mCurrentLocation.getAccuracy();
                Origin = new LatLng(latitude, longitude);
                Toast.makeText(GetLocationActivity.this, " " + Integer.valueOf((int) Accuracy) + " ", Toast.LENGTH_SHORT).show();  //Longitude+"//"+Latitude+"//"+Altitude+"//"+
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    private void showMyLoaction() {
        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // GPS location can be null if GPS is switched off
                if (location != null) {
                    onLocationChanged(location);
                    userLocation = location;
                } else if (location == null) {
                    Toast.makeText(GetLocationActivity.this, "موقعیت کنونی شما شناسایی نشد", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("MapDemoActivity", "Error trying to get last GPS location");
                e.printStackTrace();
            }
        });
    }

    public void onLocationChanged(Location location) {
        gotoLocation(location.getLatitude(), location.getLongitude(), 18);
    }

    private void gotoLocation(double lat, double lng, float zoom) {
        //showAroundBike();
        LatLng latLng = new LatLng(lat, lng);
        Log.i("checking", "gotoLocation: " + latLng);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        mMap.moveCamera(cameraUpdate);
    }

//    @Override
//    protected void onActivityResult(final int requestCode, final int resultCode, final Intent
//            data) {
//        Log.e(TAG, "onActivityResult: ");
//
//        if (requestCode == AppConstant.REQUEST_CODE_SETTING_LOCATION) {
//            if (gpsEnabled()) {
//                Log.e(TAG, "onActivityResult: location on");
//                getMyLocation();
//            } else
//                Log.e(TAG, "onActivityResult: location off ");
//
//        }
//    }

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
//        Log.e(TAG, "onBackPressed: ");
//        Intent location = new Intent();
//        setResult(2, location);
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
/*
    private void getDeviceLocation() {
        Log.e(TAG, "getDeviceLocation: ");
        if (getLocationPermission()) {
            if (gpsEnabled())
                getMyLocation();
            else
                dialogLocation.show();
        }

    }
*/
/*
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

*/
/*
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
*/

/*
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

*/
}

