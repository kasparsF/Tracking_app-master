package com.example.kasparsfisers.loginapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class LocationContract {

    private LocationContract(){}


    // name for the entire content provider
    public static final String CONTENT_AUTHORITY = "com.example.kasparsfisers.loginapp";

    // base of all URI's which will use to contact
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //Possible path
    public static final String PATH_COORDINATES = "coordinates";


    public static abstract class LocationEntry implements BaseColumns {

        //URI to access the coordinate data
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_COORDINATES);



        public static final String TABLE_NAME = "coordinates";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_ACCURACY = "accuracy";
        public static final String COLUMN_LOCNAME = "name";

    }
}
