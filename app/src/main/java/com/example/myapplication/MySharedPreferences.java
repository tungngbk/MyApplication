package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    private static final String mySharedPreferences = "MSP";
    private Context mContext;

    public MySharedPreferences(Context mContext) {
        this.mContext = mContext;
    }

    public void putString(String key, String value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                mySharedPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                mySharedPreferences, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
}
