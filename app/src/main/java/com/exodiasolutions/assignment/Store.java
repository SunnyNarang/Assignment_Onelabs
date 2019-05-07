package com.exodiasolutions.assignment;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Sunny on 15-07-2018.
 */

public class Store {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    public Store(Context c){
        // 0 - for private mode
        pref = c.getSharedPreferences("Store", 0);

        editor = pref.edit();
    }

    public void setStatus(String key, boolean value){
        editor.putBoolean(key, value);
        editor.commit();
    }
    public void setValue(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }
    public boolean getStatus(String key){
        return  pref.getBoolean(key,false);
    }
    public String getValue(String key){
        return pref.getString(key,null);
    }




}
