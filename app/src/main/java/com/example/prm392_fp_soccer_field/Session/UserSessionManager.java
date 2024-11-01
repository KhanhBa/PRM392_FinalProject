package com.example.prm392_fp_soccer_field.Session;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.prm392_fp_soccer_field.Models.Customer;
import com.google.gson.Gson;

public class UserSessionManager {

    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USER = "user";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public UserSessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
    }


    public void saveLoginSession(Customer user) {
        String userJson = gson.toJson(user);
        editor.putString(KEY_USER, userJson);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }


    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }


    public Customer getUser() {
        String userJson = sharedPreferences.getString(KEY_USER, null);
        return gson.fromJson(userJson, Customer.class);
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
