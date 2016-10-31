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

public class Display extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /** Identifier for the pet data loader */
    private static final int PET_LOADER = 0;

    /** Adapter for the ListView */
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

                    ActivityCompat.requestPermissions(Display.this,
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
                startActivity(new Intent(Display.this, MainActivity.class));
                finish();
            }
        });
        // Find the ListView which will be populated with the pet data
        ListView coordinateListView = (ListView) findViewById(R.id.list);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
       // View emptyView = findViewById(R.id.empty_view);
     //   petListView.setEmptyView(emptyView);

        // Setup an Adapter to create a list item for each row of pet data in the Cursor.
        // There is no pet data yet (until the loader finishes) so pass in null for the Cursor.
        mCursorAdapter = new LocationCursorAdapter(this, null);
        coordinateListView.setAdapter(mCursorAdapter);


        coordinateListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Display.this, CoordinatesDisplay.class);

                Uri currentCoordinatesUri = ContentUris.withAppendedId(LocationEntry.CONTENT_URI, id);
                intent.setData(currentCoordinatesUri);
                startActivity(intent);
            }
        });




        // Kick off the loader
        getLoaderManager().initLoader(PET_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Define a projection that specifies the columns from the table we care about.
        String[] projection = {
                LocationEntry._ID,
                LocationEntry.COLUMN_LOCNAME };

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                LocationEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                LocationContract.LocationEntry._ID + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update {@link PetCursorAdapter} with this new cursor containing updated pet data
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }
}
