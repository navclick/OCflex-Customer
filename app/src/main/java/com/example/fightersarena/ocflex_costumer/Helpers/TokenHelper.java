package com.example.fightersarena.ocflex_costumer.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class TokenHelper {

    // Declarations
    private final Context context;
    private final SharedPreferences sharedPreferences;

    public TokenHelper(Context context){
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean SetToken(String access_token){
        SharedPreferences.Editor editor = sharedPreferences.edit().putString("token", access_token);
        editor.commit();
        return true;
    }

    public String GetToken(){
        String restoredText = sharedPreferences.getString("token", null);
        return restoredText;
    }

    public boolean SetUserName(String userName){
        SharedPreferences.Editor editor = sharedPreferences.edit().putString("username", userName);
        editor.commit();
        return true;
    }

    public String GetUserName(){
        String restoredText = sharedPreferences.getString("username", null);
        return restoredText;
    }



    public boolean SetUserEmail(String useremail){
        SharedPreferences.Editor editor = sharedPreferences.edit().putString("useremail", useremail);
        editor.commit();
        return true;
    }

    public String GetUserEmail(){
        String restoredText = sharedPreferences.getString("useremail", null);
        return restoredText;
    }

    public boolean SetUserPhoto(String usephoto){
        SharedPreferences.Editor editor = sharedPreferences.edit().putString("userphoto", usephoto);
        editor.commit();
        return true;
    }

    public String GetUserPhoto(){
        String restoredText = sharedPreferences.getString("userphoto", null);
        return restoredText;
    }
    public boolean removeALL() {
        android.content.SharedPreferences.Editor prefsEditor = sharedPreferences.edit();

        prefsEditor.clear();
        return prefsEditor.commit();
    }

}