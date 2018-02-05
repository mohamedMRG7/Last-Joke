package com.example.moham.lastjoke.jokeNotification;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.moham.lastjoke.Database.DbUtilies;
import com.example.moham.lastjoke.Database.JokeContract;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by moham on 2/4/2018.
 */

public class JokeNotifyTask {


    public static final String JOKE_NOTIFY_ACTION="joke_notification";

    public static void excuteTask(Context context, String action)
    {
        if (action.equals(JOKE_NOTIFY_ACTION));
        {
            sayAJoke(context);





        }
    }

    private static void sayAJoke(Context context) {

        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        DbUtilies dbUtilies=new DbUtilies(context);
        Cursor cursor=dbUtilies.showDependonLangauae(sharedPreferences,context);
        Random randomPos=new Random();
        int pos= randomPos.nextInt(cursor.getCount());
        cursor.moveToPosition(pos);
        String joke=cursor.getString(cursor.getColumnIndex(JokeContract.JokeEntry.COLUMN_JOKE));

        NotificationUtilies.notifybyrondomJoke(context,joke,pos);
    }

}
