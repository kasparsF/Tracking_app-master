package com.example.kasparsfisers.loginapp;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$";


    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;


        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0 || str.equals("null");
    }



    public static void getAddressFromLocation(
            final double lat, final double lon, final double acc, final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                String psCode;
                String addLine;
                String locality;
                String country;
                try {
                    List<Address> list = geocoder.getFromLocation(
                            lat, lon, 1);
                    if (list != null && list.size() > 0) {
                        Address address = list.get(0);
                        addLine = address.getAddressLine(0);
                        locality = address.getLocality();
                        country = address.getCountryName();
                        psCode = address.getPostalCode();

                        if (isEmpty(addLine))
                            addLine = "";
                        if (isEmpty(locality))
                            locality = "";
                        if (isEmpty(country))
                            country = "";
                        if (isEmpty(psCode))
                            psCode = "";

                        result = addLine;
                        result += ", " + locality;
                        result += ", " + country;
                        result += ", " + psCode;
                    }
                } catch (IOException e) {
                    Log.e("Error", "Impossible to connect to Geocoder", e);
                } finally {

                    Message msg = Message.obtain();
                    msg.setTarget(handler);
                    if (result != null) {
                        msg.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putDouble("lat",lat);
                        bundle.putDouble("lon",lon);
                        bundle.putDouble("acc",acc);
                        bundle.putString("address", result);
                        msg.setData(bundle);
                    } else
                        msg.what = 0;
                    msg.sendToTarget();
                }
            }
        };
        thread.start();
    }




}
