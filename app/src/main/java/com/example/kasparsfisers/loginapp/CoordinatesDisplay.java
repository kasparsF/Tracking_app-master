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

    private TextView mLatText;
    private TextView mLonText;
    private TextView mAccText;

    private Uri mCurrentCoordinatesUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinates_display);


        Intent intent = getIntent();

        mCurrentCoordinatesUri = intent.getData();

        getLoaderManager().initLoader(EXISTING_COORDINATES_LOADER, null, this);

        mLatText = (TextView)findViewById(R.id.textViewLat);
        mLonText = (TextView)findViewById(R.id.textViewLon);
        mAccText = (TextView)findViewById(R.id.textViewAcc);

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

            mLatText.setText(Lat);
            mLonText.setText(Lon);
            mAccText.setText(Acc);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mLatText.setText("");
        mLonText.setText("");
        mAccText.setText("");

    }
}

