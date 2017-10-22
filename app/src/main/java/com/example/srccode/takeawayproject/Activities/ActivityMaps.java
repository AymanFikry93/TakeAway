package com.example.srccode.takeawayproject.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.srccode.takeawayproject.WebServices.GPSTracker;
import com.example.srccode.takeawayproject.WebServices.GetRegionByMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static com.example.srccode.takeawayproject.Global.GlopalClass.HostName;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Resturantlatitude;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Resturantlongitude;
import static com.example.srccode.takeawayproject.Global.GlopalClass.accesstoken;
import static com.example.srccode.takeawayproject.Global.GlopalClass.clientlatitude;
import static com.example.srccode.takeawayproject.Global.GlopalClass.clientlongitude;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantmapflag;

public class ActivityMaps extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        View.OnClickListener {

    public static GoogleMap mMap;
    private double longitude;
    private double latitude;
    EditText locationSearch;
    //Google ApiClient
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        locationSearch= (EditText) findViewById(R.id.editText);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Need Permissions");
            builder.setMessage("This app needs  Location permissions.");
            builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String[] permissionsRequired = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION};
                    final int PERMISSION_CALLBACK_CONSTANT = 100;
                    dialog.cancel();
                    ActivityCompat.requestPermissions(ActivityMaps.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Initializing our map
        mMap = googleMap;
         googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        googleMap.setMapType(GoogleMap.MAP_T YPE_HYBRID);
        // googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
//        googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        //Creating a random coordinate

        LatLng sydney = new LatLng(latitude,longitude);//(24.68695241, 46.7578125);
        //Adding marker to that coordinate
        mMap.addMarker(new MarkerOptions().position(sydney).draggable(true).title("Here you are")
               ).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(150), 2000, null);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng latLng) {
                // Creating a marker
                mMap.clear();
                //Adding a new marker to the current pressed position we are also making the draggable true
                mMap.addMarker(new MarkerOptions().position(latLng).draggable(true).title("My Location")).showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                if (resturantmapflag == 1) {
                  //  resturantmapflag = 0;
                    Resturantlatitude = latLng.latitude;
                    Resturantlongitude = latLng.longitude;
                    new GetRegionByMap(getApplicationContext()).execute("http://"+ HostName+"/api/Users/GetRegion?" +
                            "pLatitude=" + Resturantlatitude + "&pLongtitude=" + Resturantlongitude);
                }
                else if (resturantmapflag == 2) {
                 //   resturantmapflag = 0;
                    clientlatitude = latLng.latitude;
                    clientlongitude = latLng.longitude;
                    new GetRegionByMap(getApplicationContext())
                            .execute("http://"+ HostName+"/api/Users/GetRegion?pLatitude="+Resturantlatitude+"&pLongtitude="+Resturantlongitude);
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
            getCurrentLocation();
            moveMap();
    }

    @Override
    public void onConnected(Bundle bundle) {

        getCurrentLocation();
    }
    //Getting current location
    private void getCurrentLocation() {

        mMap.clear();
        GPSTracker gps;
        gps = new GPSTracker(getApplicationContext());
        // check if GPS enabled
        if(gps.canGetLocation()){
             latitude =gps.getLatitude();//31.25841263
             longitude =gps.getLongitude();//29.98027325
         //   Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            moveMap();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }
    //Function to move the map
    private void moveMap() {
        //String to display current latitude and longitude
        String msg = latitude + ", "+longitude;

        //Creating a LatLng Object to store Coordinates
        LatLng latLng = new LatLng(latitude, longitude);

        //Adding marker to map
        mMap.addMarker(new MarkerOptions()
                .position(latLng) //setting position
                .draggable(true) //Making the marker draggable
                .title("Current Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))); //Adding a title

        //Moving the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 10, null);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        //Displaying current coordinates in toast
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        //Getting the coordinates
        latitude = marker.getPosition().latitude;
        longitude = marker.getPosition().longitude;

        //Moving the map
        moveMap();

    }
    public void onMapSearch(View view) {

        String location = locationSearch.getText().toString();
        List<Address> addressList = null;

        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            Address address = null;
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            }
            catch (IOException e) {
                e.printStackTrace();
                e.getMessage();
            }
            catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
            }

            try{
                if (addressList != null && addressList.size() > 0) {
                    address = addressList.get(0);
                    mMap.clear();
                    latitude =address.getLatitude();
                    longitude=address.getLongitude();
                    moveMap();
                }
                else {

                    Toast.makeText(this, "هذه العنوان غير متاح ,اختر عنوان اخر ", Toast.LENGTH_LONG).show();

                }
        }
            catch (Exception e) {
            e.printStackTrace();
        }


        }
    }


    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

}
