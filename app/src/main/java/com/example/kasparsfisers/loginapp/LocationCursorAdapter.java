package com.example.kasparsfisers.loginapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.kasparsfisers.loginapp.data.LocationContract;


public class LocationCursorAdapter extends CursorAdapter {
    public LocationCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
    //    TextView idName = (TextView) view.findViewById(R.id.tvBody);
        TextView placeName = (TextView) view.findViewById(R.id.placeName);
        // Extract properties from cursor
    //    int idIndex = cursor.getColumnIndex(LocationContract.LocationEntry._ID);
        int nameIndex = cursor.getColumnIndex(LocationContract.LocationEntry.COLUMN_LOCNAME);
        //Read the pet attributes from cursor
     //   String id = cursor.getString(idIndex);
        String name = cursor.getString(nameIndex);

   //     idName.setText(id);
        placeName.setText(name);

    }
}