package com.example.projectdemo1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.util.Locale.getDefault;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static int flag=0;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().isCompassEnabled();

            init();
        }
    }

    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    //widgets
    private EditText mSearchText;
    private ImageView mGps,mback;

    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Double latitude,longitude;
    private int radious=10000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState
        );
        setContentView(R.layout.activity_map);
        mSearchText = (EditText) findViewById(R.id.input_search);

        Bundle bundle =getIntent().getExtras();

        if(bundle !=null){
            mSearchText.setText(bundle.getString("key"));

        }


        mGps = (ImageView) findViewById(R.id.ic_gps);
        mback = (ImageView) findViewById(R.id.info);

        getLocationPermission();

        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
            }
        });

    }

    private void init(){
        Log.d(TAG, "init: initializing");

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER){

                    //execute our method for searching
                    geoLocate();
                }

                return false;
            }
        });

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked gps icon");
                getDeviceLocation();
            }
        });
        hideSoftKeyboard();
    }
//    public void onClick(View v){
//
//        String hospital="hospital",school="school",restaurant="restaurant";
//        Object transferData[] = new Object[2];
//        GetNearbyPlacesData getNearbyPlacesData =new GetNearbyPlacesData();
//
//
//        switch (v.getId()){
//            case R.id.restaurant:
//                String url=getUrl(latitude,longitude,restaurant);
//                transferData[0]=mMap;
//                transferData[1]=url;
//
//                getNearbyPlacesData.execute(transferData);
//                Log.d(TAG, "onClick: Nearby restaurant click");
//                break;
//            case R.id.schools:
//                url=getUrl(latitude,longitude,school);
//                transferData[0]=mMap;
//                transferData[1]=url;
//
//                getNearbyPlacesData.execute(transferData);
//                Log.d(TAG, "onClick: Nearby Schools click");
//                break;
//            case R.id.hospitals:
//                url=getUrl(latitude,longitude,hospital);
//                transferData[0]=mMap;
//                transferData[1]=url;
//
//                getNearbyPlacesData.execute(transferData);
//                Log.d(TAG, "onClick: Nearby Hospitals click");
//                break;
//        }
//    }
//    private String getUrl(Double latitude, Double longitude, String type){
//
//         StringBuilder googlUrl =new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
//         googlUrl.append("loaction=" + latitude +","+ longitude);
//         googlUrl.append("&radious=" + radious);
//         googlUrl.append("@type=" + type);
//         googlUrl.append("@sensor=true");
//         googlUrl.append("@key=" + "AIzaSyCUMfYwoYNo2pZg02Fdwu1J9WGB8KT7v8U");
//
//        Log.d(TAG, "getUrl: Url"+googlUrl.toString());
//         return googlUrl.toString();
//    }

    private void geoLocate(){
        Log.d(TAG, "geoLocate: geolocating");
        String searchString;



        searchString = mSearchText.getText().toString();


        Geocoder geocoder = new Geocoder(MapActivity.this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
        }

        if(list.size() > 0){
            Address address = list.get(0);

            latitude=address.getLatitude();
            longitude=address.getLongitude();

            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();

            String datas= "Address: "+address.getAddressLine(0)+"\n" +
                    "Phone Number: "+address.getPhone()+"\n"+
                    "Website: "+address.getUrl()+"\n"+
                    "Rating: "+address.getExtras()
            ;


            String str =address.getAddressLine(0);
            Intent intent =new Intent(getApplicationContext(),ProfileActivity.class);
            intent.putExtra("key",str);
            setResult(2,intent);



            //Toast.makeText(getApplicationContext(),datas,Toast.LENGTH_SHORT).show();
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                    address.getAddressLine(0));

            //finish();
        }
    }

    private void getDeviceLocation(){


        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                Log.d(TAG, "getDeviceLocation: "+location);


                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();
                            Log.d(TAG, "onComplete: "+currentLocation);


                            latitude=currentLocation.getLatitude();
                            longitude=currentLocation.getLongitude();

                            String str = getAddress(getApplicationContext(),latitude,longitude);


                            if(flag==0){
                                Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                                intent.putExtra("key",str);
                                setResult(1,intent);
                                flag++;
                                finish();
                            }


                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM,
                                    "My Location");

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    public  String getAddress(Context context, double LATITUDE, double LONGITUDE) {


        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {

                String address = addresses.get(0).getAddressLine(0);

                Log.d(TAG, "getAddress:  address" + address);
                return  address;


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void moveCamera(LatLng latLng, float zoom, String title){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if(!title.equals("My Location")){
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }

        hideSoftKeyboard();
    }

    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapActivity.this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}


