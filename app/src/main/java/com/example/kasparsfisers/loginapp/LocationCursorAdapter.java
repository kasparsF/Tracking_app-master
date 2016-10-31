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


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View rowView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.placeName = (TextView) rowView.findViewById(R.id.placeName);
        rowView.setTag(holder);
        return rowView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find views
        ViewHolder holder = (ViewHolder) view.getTag();

        int nameIndex = cursor.getColumnIndex(LocationContract.LocationEntry.COLUMN_LOCNAME);

        String name = cursor.getString(nameIndex);


        holder.placeName.setText(name);

    }

    private  class ViewHolder {
        TextView placeName;

    }
}