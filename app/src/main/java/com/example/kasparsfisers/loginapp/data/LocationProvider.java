package com.example.kasparsfisers.loginapp.data;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.kasparsfisers.loginapp.data.LocationContract.LocationEntry;

public class LocationProvider extends ContentProvider {

    /** Tag for the log messages */
    public static final String LOG_TAG = LocationProvider.class.getSimpleName();

    // code for the coordinates table */
    private static final int COORDINATES = 100;

    // code for a single coordinate in the coordinates table */
    private static final int COORDINATE_ID = 101;

    // UriMatcher object to match a content URI to a corresponding code.
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    static {

        sUriMatcher.addURI(LocationContract.CONTENT_AUTHORITY, LocationContract.PATH_COORDINATES, COORDINATES);

        sUriMatcher.addURI(LocationContract.CONTENT_AUTHORITY, LocationContract.PATH_COORDINATES + "/#", COORDINATE_ID);
    }

    private LocationDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new LocationDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case COORDINATES:
                cursor = database.query(LocationEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case COORDINATE_ID:

                selection = LocationEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(LocationEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        // If the data at this URI changes, then update the Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);


        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case COORDINATES:
                return insertCoordinates(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertCoordinates(Uri uri, ContentValues values) {


        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(LocationEntry.TABLE_NAME, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the coordinates content URI
        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }


    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
