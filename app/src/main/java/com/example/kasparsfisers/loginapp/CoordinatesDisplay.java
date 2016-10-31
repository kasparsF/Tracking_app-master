package com.example.kasparsfisers.loginapp;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.kasparsfisers.loginapp.data.LocationContract.LocationEntry;

public class CoordinatesDisplay extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int EXISTING_COORDINATES_LOADER = 0;
    private Uri mCurrentCoordinatesUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        mCurrentCoordinatesUri = intent.getData();

        getLoaderManager().initLoader(EXISTING_COORDINATES_LOADER, null, this);


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                LocationEntry._ID,
                LocationEntry.COLUMN_LATITUDE,
                LocationEntry.COLUMN_LONGITUDE,
                LocationEntry.COLUMN_ACCURACY};

        return new CursorLoader(this,
                mCurrentCoordinatesUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {

            int LatColumnIndex = cursor.getColumnIndex(LocationEntry.COLUMN_LATITUDE);
            int LonColumnIndex = cursor.getColumnIndex(LocationEntry.COLUMN_LONGITUDE);
            int AccColumnIndex = cursor.getColumnIndex(LocationEntry.COLUMN_ACCURACY);

            String Lat = cursor.getString(LatColumnIndex);
            String Lon = cursor.getString(LonColumnIndex);
            String Acc = cursor.getString(AccColumnIndex);


            Double myLatitude = cursor.getDouble(LatColumnIndex);
            Double myLongitude = cursor.getDouble(LonColumnIndex);
            String labelLocation = "Target";

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<" + myLatitude  + ">,<" + myLongitude + ">?q=<" + myLatitude  + ">,<" + myLongitude + ">(" + labelLocation + ")"));
            startActivity(intent);
            finish();


        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

