package com.example.dell.mygooglemap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
private  static final int REQUEST_PERMISSION_LOCATION=1;
//1.declare loction client
private FusedLocationProviderClient mFusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);//
        //2.initialization
        mFusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
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
        mMap = googleMap;

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_PERMISSION_LOCATION);
        }
        else{
           getMyLocation();
            Toast.makeText(this,"Permition is allowed",Toast.LENGTH_LONG).show();
        }
        // Add a marker in Sydney and move the camera
        LatLng vimanNagar = new LatLng(18.5679, 73.9143 );
        LatLng kalayaniNagar = new LatLng(18.54763, 73.9033 );
        LatLng baner = new LatLng(18.5590, 73.7868 );
        LatLng kharadi = new LatLng(18.550985, 73.934982 );


        MarkerOptions markerOptionsVimanNagar=new MarkerOptions().position(vimanNagar).title("Marker in viman nagar");
        mMap.addMarker(markerOptionsVimanNagar);

        MarkerOptions markerOptionskalayaniNagar=new MarkerOptions().position(kalayaniNagar).title("kalayaniNagar");
        mMap.addMarker(markerOptionskalayaniNagar);

        MarkerOptions markerOptionsbaner=new MarkerOptions().position(baner).title("baner");
        mMap.addMarker(markerOptionsbaner);
        MarkerOptions markerOptionskharadi=new MarkerOptions().position(kharadi).title("kharadi");
        mMap.addMarker(markerOptionskharadi);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(vimanNagar,12.01f));
        CircleOptions circleOptions= new CircleOptions().center(vimanNagar).radius(1000).strokeColor(Color.BLACK).strokeWidth(3).fillColor(Color.BLUE);
        mMap.addCircle(circleOptions);


        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.add(vimanNagar);
        polylineOptions.add(kalayaniNagar);
        polylineOptions.add(baner);
        polylineOptions.add(kharadi);
        polylineOptions.width(10);
        polylineOptions.color(Color.RED);
        mMap.addPolyline(polylineOptions);

PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.add(vimanNagar);
        polygonOptions.add(kalayaniNagar);
        polygonOptions.add(baner);
        //polygonOptions.width(10);
        polygonOptions.fillColor(Color.RED);
        mMap.addPolygon(polygonOptions);
    }

    @SuppressLint("MissingPermission")
    private void getMyLocation() {
        mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location !=null){
                    LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    MarkerOptions markerOptions=new MarkerOptions()
                            .position(myLocation).title("My Location");
                    mMap.addMarker(markerOptions);

                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode== REQUEST_PERMISSION_LOCATION){
        if (grantResults.length > 0 && grantResults[0] ==  PackageManager.PERMISSION_GRANTED);
        getMyLocation();
    }else {
        Toast.makeText(this,"Permition is mandtory",Toast.LENGTH_LONG).show();
ActivityCompat.requestPermissions(this,
        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
        REQUEST_PERMISSION_LOCATION);
    }
    }
}
