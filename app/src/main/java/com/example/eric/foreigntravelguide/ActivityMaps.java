package com.example.eric.foreigntravelguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActivityMaps extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private String[] latitude;
    private String[] longitude;
    private String[] latZA;
    private String[] lngZA;
    private String[] latHighAdv;
    private String[] latLowAdv;
    private String[] lngHighAdv;
    private String[] lngLowAdv;
    private String[] namesZA;
    private String[] nameList;
    private String[] highAdv;
    private String[] lowAdv;
    private String selected = "";
    private int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //Get intent
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        pos = intent.getIntExtra("pos", pos);
        latitude = extras.getStringArray("latitude");
        longitude = extras.getStringArray("longitude");
        selected = extras.getString("selected", selected);
        nameList = extras.getStringArray("nameList");
        namesZA = extras.getStringArray("namesZA");
        highAdv = extras.getStringArray("highAdv");
        lowAdv = extras.getStringArray("lowAdv");

        Log.d("position", Integer.toString(pos));
        Log.d("selected", selected);

        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        if (namesZA != null) {
            if (selected.equals(namesZA[pos])) {
                latZA = new String[latitude.length];
                lngZA = new String[longitude.length];
                sortLatZA();
                sortLongZA();
                double lat = Double.parseDouble(latZA[pos]);
                double lng = Double.parseDouble(lngZA[pos]);
                LatLng latLng = new LatLng(lat, lng);
                CameraPosition cameraPosition =
                        new CameraPosition.Builder()
                                .target(latLng)
                                .bearing(0)
                                .zoom(5)
                                .tilt(25)
                                .build();
                mMap.addMarker(new MarkerOptions().position(latLng).title(selected));
                CameraUpdate point = CameraUpdateFactory.newCameraPosition(cameraPosition);
                mMap.animateCamera(point);
            }
        } else if (selected.equals(highAdv[pos])) {
            latHighAdv = new String[latitude.length];
            lngHighAdv = new String[longitude.length];
            sortLatHighAdv();
            sortLngHighAdv();
            double lat = Double.parseDouble(latHighAdv[pos]);
            double lng = Double.parseDouble(lngHighAdv[pos]);
            LatLng latLng = new LatLng(lat, lng);
            CameraPosition cameraPosition =
                    new CameraPosition.Builder()
                            .target(latLng)
                            .bearing(0)
                            .zoom(5)
                            .tilt(25)
                            .build();
            mMap.addMarker(new MarkerOptions().position(latLng).title(selected));
            CameraUpdate point = CameraUpdateFactory.newCameraPosition(cameraPosition);
            mMap.animateCamera(point);
        } else if (selected.equals(lowAdv[pos])) {
            latLowAdv = new String[latitude.length];
            lngLowAdv = new String[longitude.length];
            sortLatLowAdv();
            sortLngLowAdv();
            double lat = Double.parseDouble(latLowAdv[pos]);
            double lng = Double.parseDouble(lngLowAdv[pos]);
            LatLng latLng = new LatLng(lat, lng);
            CameraPosition cameraPosition =
                    new CameraPosition.Builder()
                            .target(latLng)
                            .bearing(0)
                            .zoom(5)
                            .tilt(25)
                            .build();
            mMap.addMarker(new MarkerOptions().position(latLng).title(selected));
            CameraUpdate point = CameraUpdateFactory.newCameraPosition(cameraPosition);
            mMap.animateCamera(point);
        } else {
            double lat = Double.parseDouble(latitude[pos]);
            double lng = Double.parseDouble(longitude[pos]);
            LatLng latLng = new LatLng(lat, lng);
            CameraPosition cameraPosition =
                    new CameraPosition.Builder()
                            .target(latLng)
                            .bearing(0)
                            .zoom(5)
                            .tilt(25)
                            .build();
            mMap.addMarker(new MarkerOptions().position(latLng).title(selected));
            CameraUpdate point = CameraUpdateFactory.newCameraPosition(cameraPosition);
            mMap.animateCamera(point);
        }
    }

    private void sortLatZA() {
        int c = 0;
        for (int i = latitude.length - 1; i > -1; i--) {
            latZA[c] = latitude[i];
            c++;
        }
    }

    private void sortLongZA() {
        int c = 0;
        for (int i = longitude.length - 1; i > -1; i--) {
            lngZA[c] = longitude[i];
            c++;
        }
    }

    private void sortLatHighAdv() {
        for (int i = 0; i < highAdv.length; i++) {
            for (int j = 0; j < nameList.length; j++) {
                if (highAdv[i].equals(nameList[j]))
                    latHighAdv[i] = latitude[j];
            }
        }
    }

    private void sortLatLowAdv() {
        for (int i = 0; i < lowAdv.length; i++) {
            for (int j = 0; j < nameList.length; j++) {
                if (lowAdv[i].equals(nameList[j]))
                    latLowAdv[i] = latitude[j];
            }
        }
    }

    private void sortLngHighAdv() {
        for (int i = 0; i < highAdv.length; i++) {
            for (int j = 0; j < nameList.length; j++) {
                if (highAdv[i].equals(nameList[j]))
                    lngHighAdv[i] = longitude[j];
            }
        }
    }

    private void sortLngLowAdv() {
        for (int i = 0; i < lowAdv.length; i++) {
            for (int j = 0; j < nameList.length; j++) {
                if (lowAdv[i].equals(nameList[j]))
                    lngLowAdv[i] = longitude[j];
            }
        }
    }
}
