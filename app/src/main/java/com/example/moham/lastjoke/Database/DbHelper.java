package com.example.moham.lastjoke.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by moham on 1/29/2018.
 */

public class DbHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME="lastjoke.db";
    private static final int DATABASE_VERSION=3;






    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CRATE_TABLE_JOKES="CREATE TABLE "+JokeContract.JokeEntry.TABLE_NAME+" ( "
                +JokeContract.JokeEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +JokeContract.JokeEntry.COLUMN_USERNAME+" TEXT NOT NULL, "
                +JokeContract.JokeEntry.COLUMN_EMAIL+" TEXT NOT NULL, "
                +JokeContract.JokeEntry.COLUMN_USER_UNIQ_ID+" TEXT NOT NULL, "
                +JokeContract.JokeEntry.COLUMN_USER_ICON+" TEXT NOT NULL, "
                +JokeContract.JokeEntry.COLUMN_JOKE+" TEXT NOT NULL, "
                +JokeContract.JokeEntry.COLUMN_LANGUAGE+" TEXT NOT NULL, "
                +JokeContract.JokeEntry.COLUMN_HAPPYNUM+" INTEGER NOT NULL, "
                +JokeContract.JokeEntry.COLUMN_SADNUM+ " INTEGER NOT NULL "
                +");";


        sqLiteDatabase.execSQL(CRATE_TABLE_JOKES);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ JokeContract.JokeEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
