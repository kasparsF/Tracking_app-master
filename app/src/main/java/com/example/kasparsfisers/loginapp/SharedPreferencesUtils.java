package com.example.kasparsfisers.loginapp;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferencesUtils {
    private Context context;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public SharedPreferencesUtils(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void register(String newUser, String newPass, String newEmail, String newName) {
        editor.putString(newUser + newPass + "data", newUser + "\n" + newEmail);
        editor.putString(newUser + newPass + "info", newName);
        editor.apply();
    }

    public String login(String user, String pass) {
        return prefs.getString(user + pass + "info", "");
    }


    public void sessionSetLoggedIn(Boolean logged) {
        editor.putBoolean("session",logged);
        editor.apply();
    }

    public Boolean sessionLoggedIn() {
        return prefs.getBoolean("session",false);
    }
}