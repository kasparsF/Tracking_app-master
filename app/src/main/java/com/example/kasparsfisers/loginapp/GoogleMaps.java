package com.example.kasparsfisers.loginapp;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.kasparsfisers.loginapp.data.LocationContract;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMaps extends FragmentActivity implements OnMapReadyCallback, LoaderManager.LoaderCallbacks<Cursor> {
    private static final int EXISTING_COORDINATES_LOADER = 0;
    private static final String LOCATION_SEPARATOR = ",";
    private Uri mCurrentCoordinatesUri;
    private GoogleMap mMap;
    private String mapLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Intent intent = getIntent();

        mCurrentCoordinatesUri = intent.getData();

        getLoaderManager().initLoader(EXISTING_COORDINATES_LOADER, null, this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Sets the map type to be "hybrid"

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                LocationContract.LocationEntry._ID,
                LocationContract.LocationEntry.COLUMN_LATITUDE,
                LocationContract.LocationEntry.COLUMN_LONGITUDE,
                LocationContract.LocationEntry.COLUMN_LOCNAME};

        return new CursorLoader(this,
                mCurrentCoordinatesUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1 || mMap == null) {
            return;
        }
        locateCoordinates(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    private void locateCoordinates(Cursor cursor){

        if (cursor.moveToFirst()) {

            int LatColumnIndex = cursor.getColumnIndex(LocationContract.LocationEntry.COLUMN_LATITUDE);
            int LonColumnIndex = cursor.getColumnIndex(LocationContract.LocationEntry.COLUMN_LONGITUDE);
            int NameColumnIndex = cursor.getColumnIndex(LocationContract.LocationEntry.COLUMN_LOCNAME);
            Double myLatitude = cursor.getDouble(LatColumnIndex);
            Double myLongitude = cursor.getDouble(LonColumnIndex);
            String myPlaceName = cursor.getString(NameColumnIndex);

            if (myPlaceName.contains(LOCATION_SEPARATOR)) {
                String[] parts = myPlaceName.split(LOCATION_SEPARATOR);
                mapLocation = parts[0];
            } else {
                mapLocation = "Unknown";
            }

            LatLng target = new LatLng(myLatitude, myLongitude);
            mMap.addMarker(new MarkerOptions().position(target).title(mapLocation));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(target,13));

        }

    }
}
