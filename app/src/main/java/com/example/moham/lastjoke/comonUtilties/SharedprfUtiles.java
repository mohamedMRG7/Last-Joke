package com.example.moham.lastjoke.comonUtilties;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by moham on 2/1/2018.
 */

public class SharedprfUtiles {

      private   SharedPreferences mPrefs;
      private   Activity activity;

    public SharedprfUtiles(Activity activity) {
         mPrefs = activity.getPreferences(MODE_PRIVATE);
         this.activity=activity;
    }


    public void saveObect(List list,String key)
    {
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        prefsEditor.putString(key, json);
        prefsEditor.apply();
    }


    public List getList(String key)
    {
        List<String> isfeedbacked ;
        Gson gson = new Gson();
        String json = mPrefs.getString(key, "");
        if (json.isEmpty()) {
            isfeedbacked = new ArrayList< >();
        } else {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            isfeedbacked = gson.fromJson(json, type);
        }



        return  isfeedbacked;
    }


}
