package com.example.moham.lastjoke.setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;

import com.example.moham.lastjoke.R;

/**
 * Created by moham on 1/27/2018.
 */

public class SettingFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener{


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
    {

        addPreferencesFromResource(R.xml.setting);
        SharedPreferences sharedPreferences=getPreferenceScreen().getSharedPreferences();
        android.support.v7.preference.PreferenceScreen preferenceScreen=getPreferenceScreen();

        for (int i=0;i<preferenceScreen.getPreferenceCount();i++)
        {
            Preference preference=preferenceScreen.getPreference(i);
            if (preference instanceof ListPreference)
            {
                ListPreference listPreference=(ListPreference)preference;
                listPreference.setSummary(listPreference.getEntry());
            }
        }




    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

        Preference preference=findPreference(s);
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            listPreference.setSummary(listPreference.getEntry());

        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
