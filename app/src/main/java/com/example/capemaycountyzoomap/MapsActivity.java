package com.example.capemaycountyzoomap;
import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        //NOTE: google maps api key is required to access the api, a key i generated is loaded into the
        //google_maps_api.xml file in the app/res/values directory

        mMap = googleMap;
        // Adds a marker
        //mMap.addMarker(new MarkerOptions().position(zoo).title("Marker in Zoo"));
        LatLng zoo = new LatLng(39.10, -74.81); //lat and lng of cape may county zoo
        LatLng pic = new LatLng(39.101451, -74.815200);
        LatLng backing = new LatLng(39.101954,-74.8152057);


        mMap.moveCamera(CameraUpdateFactory.newLatLng(zoo));  //moves camera to the coordinates of zoo
        mMap.getUiSettings().setRotateGesturesEnabled(false);   //prevents user from rotating with a gesture
        mMap.setMinZoomPreference(17.2f);
//minimum zoom level so user cant see outside zoo boundaries
           // mMap.setMyLocationEnabled(true);
        //mMap.setMapType(2);
        //Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.zoomap); //converts the map png to bitmap format

        GroundOverlayOptions backgroundGoo = new GroundOverlayOptions()      //used for overlaying the image
                .image(BitmapDescriptorFactory.fromResource(R.drawable.backdrop))        //calls the bitmap image bmp                                                         //requires more method calls to set required settings
                .position(backing, 1600f, 1600f)       //This needs to be adjusted, check the API for parameters
                .bearing(270);
        //.transparency(0.7f);
        mMap.addGroundOverlay(backgroundGoo);     //ads the overlay to the map

        GroundOverlayOptions gOO = new GroundOverlayOptions()      //used for overlaying the image
            .image(BitmapDescriptorFactory.fromResource(R.drawable.zoomap50))        //calls the bitmap image bmp                                                         //requires more method calls to set required settings
            .position(pic, 700f, 425f)       //This needs to be adjusted, check the API for parameters
            .bearing(270);
            //.transparency(0.7f);
        mMap.addGroundOverlay(gOO);
        //ads the overlay to the map



        // Create a LatLngBounds that locks the user from moving the map outside of the zoo
        //using coordinates from 2 corners around the zoo
        LatLngBounds ZOO = new LatLngBounds(
                new LatLng(39.0995, -74.8152),new LatLng(39.1035, -74.81495));
        mMap.setLatLngBoundsForCameraTarget(ZOO);

        //Changes the camera bearing to be in line with the overlay
        final CameraPosition position =
                new CameraPosition.Builder().target(zoo)
                   .bearing(270)
                        .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));


        //sets default zoom level, the higher the number the closer the zoom
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zoo, 17));
    }
}
