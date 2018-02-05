package com.example.moham.lastjoke.Database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.moham.lastjoke.R;
import com.example.moham.lastjoke.user.UserJokes;

import java.text.Bidi;

/**
 * Created by moham on 1/29/2018.
 */

public class DbUtilies {

    private DbHelper dbHelper;
    private SQLiteDatabase database;
    private boolean firstAdd=true;
    private final String ARABIC="Arabic";
    private final String ENGLISH="English";
    public DbUtilies(Context context) {
       dbHelper=new DbHelper(context);
       database=dbHelper.getWritableDatabase();

    }

    public  void addJoke (UserJokes userJokes)
    {
        String language=chcek_Language(userJokes.getJoke());
        ContentValues values=new ContentValues();
        values.put(JokeContract.JokeEntry.COLUMN_USERNAME,userJokes.getUsername());
        values.put(JokeContract.JokeEntry.COLUMN_EMAIL,userJokes.getEmail());
        values.put(JokeContract.JokeEntry.COLUMN_USER_UNIQ_ID,userJokes.getUserUniq_id());
        values.put(JokeContract.JokeEntry.COLUMN_USER_ICON,userJokes.getUserIcon());
        values.put(JokeContract.JokeEntry.COLUMN_JOKE,userJokes.getJoke());
        values.put(JokeContract.JokeEntry.COLUMN_HAPPYNUM,userJokes.getHappy_num());
        values.put(JokeContract.JokeEntry.COLUMN_SADNUM,userJokes.getSad_num());
        values.put(JokeContract.JokeEntry.COLUMN_LANGUAGE,language);

        if (firstAdd)
        {
            database.delete(JokeContract.JokeEntry.TABLE_NAME,null,null);
            firstAdd=false;
            Log.d("ADDED","Canceld");
        }

        database.insert(JokeContract.JokeEntry.TABLE_NAME,null,values);

    }

    public void updateJokeFeedback (UserJokes userJokes)
    {
        int happynum=userJokes.getHappy_num();
        int sadnum=userJokes.getSad_num();
        String uniq_id=userJokes.getUserIcon();

        ContentValues values=new ContentValues();
        values.put(JokeContract.JokeEntry.COLUMN_HAPPYNUM,happynum);
        values.put(JokeContract.JokeEntry.COLUMN_SADNUM,sadnum);
        database.update(JokeContract.JokeEntry.TABLE_NAME,values,
                JokeContract.JokeEntry.COLUMN_USER_ICON+" = '"+uniq_id+"'"
                ,null);
    }

    public Cursor getallJokes()
    {
        return database.query(
                JokeContract.JokeEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                JokeContract.JokeEntry.COLUMN_HAPPYNUM+ " DESC");

    }
    public Cursor getJokesforUser (String email)
    {
        return database.query(
                JokeContract.JokeEntry.TABLE_NAME,
                null,
                JokeContract.JokeEntry.COLUMN_EMAIL +" ='"+email+"'",
                null,
                null,
                null,
                JokeContract.JokeEntry.COLUMN_HAPPYNUM+ " DESC");
    }

    public Cursor getJokeswithLangauge (String language)
    {
        return database.query(
                JokeContract.JokeEntry.TABLE_NAME,
                null,
                JokeContract.JokeEntry.COLUMN_LANGUAGE +" ='"+language+"'",
                null,
                null,
                null,
                JokeContract.JokeEntry.COLUMN_HAPPYNUM+ " DESC");
    }



    public String chcek_Language(String joke)
    {
        Bidi bidi=new Bidi(joke,Bidi.DIRECTION_LEFT_TO_RIGHT);
        if (bidi.isLeftToRight())
            return ENGLISH;
        else return ARABIC;

    }

    //show the jokes depend on its language
    public Cursor showDependonLangauae(SharedPreferences sharedPreferences,Context activity)
    {
        String language_key=activity.getString(R.string.key_jokesLanguage);
        String language_defultValue=activity.getString(R.string.lang_defValue);
        String language=sharedPreferences.getString(language_key,language_defultValue);
        Cursor cursor;

        if (language.equals(ARABIC) || language.equals(ENGLISH)) {
            cursor = this.getJokeswithLangauge(language);
        }else cursor=this.getallJokes();

        return cursor;
    }


    public  void addJFollwer (UserJokes userJokes)
    {

        ContentValues values=new ContentValues();
        values.put(JokeContract.FollowersEntry.COLUMN_USERNAME,userJokes.getUsername());
        values.put(JokeContract.FollowersEntry.COLUMN_EMAIL,userJokes.getEmail());




        database.insert(JokeContract.FollowersEntry.TABLE_NAME,null,values);

    }


    public Cursor getallFollowers()
    {
        return database.query(
                JokeContract.FollowersEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

    }

    public  void deleteFollower (UserJokes userJokes)
    {

        ContentValues values=new ContentValues();
        values.put(JokeContract.FollowersEntry.COLUMN_USERNAME,userJokes.getUsername());
        values.put(JokeContract.FollowersEntry.COLUMN_EMAIL,userJokes.getEmail());




        database.delete(JokeContract.FollowersEntry.TABLE_NAME,
                JokeContract.FollowersEntry.COLUMN_EMAIL+"= '"+userJokes.getEmail()+"'",null);

    }

    public  void addUserInfo (UserJokes userJokes)
    {

        ContentValues values=new ContentValues();
        values.put(JokeContract.UserEntry.COLUMN_USERNAME,userJokes.getUsername());
        values.put(JokeContract.UserEntry.COLUMN_EMAIL,userJokes.getEmail());
        values.put(JokeContract.UserEntry.COLUMN_UID,userJokes.getUserUniq_id());



        database.insert(JokeContract.UserEntry.TABLE_NAME,null,values);

    }

    public Cursor getUserinfo (String email)
    {
        return database.query(
                JokeContract.UserEntry.TABLE_NAME,
                null,
                JokeContract.UserEntry.COLUMN_EMAIL +" ='"+email+"'",
                null,
                null,
                null,
                null);
    }

}
