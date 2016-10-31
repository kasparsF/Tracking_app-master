package com.example.kasparsfisers.loginapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kasparsfisers.loginapp.data.LocationContract.LocationEntry;

public class LocationDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "location.db";
    private static final int DATABASE_VERSION = 1;



    public LocationDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_LOCATION_TABLE ="CREATE TABLE " + LocationEntry.TABLE_NAME + " ("
                + LocationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LocationEntry.COLUMN_LATITUDE + " REAL NOT NULL, "
                + LocationEntry.COLUMN_LONGITUDE + " REAL NOT NULL, "
                + LocationEntry.COLUMN_ACCURACY + " REAL NOT NULL, "
                + LocationEntry.COLUMN_LOCNAME + " REAL NOT NULL);";

        db.execSQL(SQL_CREATE_LOCATION_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
