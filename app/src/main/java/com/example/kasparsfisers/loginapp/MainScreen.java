package com.example.kasparsfisers.loginapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.kasparsfisers.loginapp.data.LocationContract;
import com.example.kasparsfisers.loginapp.data.LocationContract.LocationEntry;

public class MainScreen extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    // Identifier for coordinate data loader
    private static final int COORDINATE_LOADER = 0;


    LocationCursorAdapter mCursorAdapter;

    Button mTracking, mLogout;
    SharedPreferencesUtils preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        mTracking = (Button) findViewById(R.id.btnLoc);
        mLogout = (Button) findViewById(R.id.btnLogout);
        preferences = new SharedPreferencesUtils(this);
        if(LocationService.isInstanceCreated()){
            mTracking.setText(R.string.stop);
        } else {
            mTracking.setText(R.string.start);
        }

        mTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MainScreen.this,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            1);
                }
                else if(!LocationService.isInstanceCreated()){

                    startService(new Intent(getBaseContext(), LocationService.class));
                    mTracking.setText(R.string.stop);

                } else {
                    stopService(new Intent(getBaseContext(), LocationService.class));
                    mTracking.setText(R.string.start);
                }
            }
        });


        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.sessionSetLoggedIn(false);
                startActivity(new Intent(MainScreen.this, LoginActivity.class));
                finish();
            }
        });

        ListView coordinateListView = (ListView) findViewById(R.id.list);


        mCursorAdapter = new LocationCursorAdapter(this, null);
        coordinateListView.setAdapter(mCursorAdapter);


        coordinateListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {










                Intent intent = new Intent(MainScreen.this, CoordinatesDisplay.class);

                Uri currentCoordinatesUri = ContentUris.withAppendedId(LocationEntry.CONTENT_URI, id);
                intent.setData(currentCoordinatesUri);
                startActivity(intent);
            }
        });


        getLoaderManager().initLoader(COORDINATE_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {
                LocationEntry._ID,
                LocationEntry.COLUMN_LOCNAME };

        //  ContentProviders query method on a background thread
        return new CursorLoader(this,
                LocationEntry.CONTENT_URI,
                projection,
                null,
                null,
                LocationContract.LocationEntry._ID + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update cursor containing updated coordinate data
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }
}
