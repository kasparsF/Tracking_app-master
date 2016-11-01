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

    private  class ViewHolder {
        TextView placeName;

    }

//    NewView – Called when the ListView requires a new view to display.
//    The CursorAdapter will take care of recycling views
//    (unlike the GetView method on regular Adapters).

//    BindView – Given a view, update it to display the data in the provided cursor.

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);
        holder.placeName = (TextView) rowView.findViewById(R.id.placeName);
        rowView.setTag(holder);
        return rowView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder holder = (ViewHolder) view.getTag();

        int nameIndex = cursor.getColumnIndex(LocationContract.LocationEntry.COLUMN_LOCNAME);

        String name = cursor.getString(nameIndex);

        holder.placeName.setText(name);

    }


}