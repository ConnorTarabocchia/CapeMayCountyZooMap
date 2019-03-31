package com.example.capemaycountyzoomap;
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

        mMap.moveCamera(CameraUpdateFactory.newLatLng(zoo));  //moves camera to the coordinates of zoo
        mMap.getUiSettings().setRotateGesturesEnabled(false);   //prevents user from rotating with a gesture
        mMap.setMinZoomPreference(16);                          //minimum zoom level so user cant see outside zoo boundaries

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.zoomap); //converts the map png to bitmap format

        GroundOverlayOptions gOO = new GroundOverlayOptions()      //used for overlaying the image
            .image(BitmapDescriptorFactory.fromBitmap(bmp))        //calls the bitmap image bmp                                                         //requires more method calls to set required settings
            .position(zoo, 1000f, 1000f);                   //This needs to be adjusted, check the API for parameters
        mMap.addGroundOverlay(gOO);     //ads the overlay to the map


        // Create a LatLngBounds that locks the user from moving the map outside of the zoo
        //using coordinates from 2 corners around the zoo
        LatLngBounds ZOO = new LatLngBounds(
                new LatLng(39.1, -74.815),new LatLng(39.103, -74.812));
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
