package com.example.moham.lastjoke.Database;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;

import com.example.moham.lastjoke.MainJokesAdapter;
import com.example.moham.lastjoke.user.UserJokes;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Scanner;

/**
 * Created by moham on 1/31/2018.
 */

public class FirebaseDbUtilies {

    FirebaseDatabase database;
    DatabaseReference reference;
    ChildEventListener mChildEventListener;
    Cursor cursor;
    DbUtilies dbUtilies;

    public FirebaseDbUtilies(Context context) {
        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("users_jokes");
        dbUtilies=new DbUtilies(context);
    }

    public  void addUserJoketoFB(UserJokes userInfo)
    {
    //    database.getReference().child("users_jokes").push().setValue(userInfo);
        DatabaseReference rf=    database.getReference().child("users_jokes").push();
        String key=rf.getKey();
        userInfo.setUserIcon(key);
        rf.setValue(userInfo);
    }

    public void updateHappynum(String key,int happynewValue)
    {
        database.getReference().child("users_jokes").child(key).child("happy_num").setValue(happynewValue);
    }

    public void updatesadNum(String key,int happynewValue)
    {
        database.getReference().child("users_jokes").child(key).child("sad_num").setValue(happynewValue);
    }


    public void readFromFB( final MainJokesAdapter adapter, final Activity activity, final SharedPreferences sharedPreferences) {

        if (mChildEventListener == null){
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                UserJokes userJokes = dataSnapshot.getValue(UserJokes.class);
               // database.getReference().child("ads").child(getparent(dataSnapshot.getRef().toString())).child("user").setValue(getparent(dataSnapshot.getRef().toString()));
                dbUtilies.addJoke(userJokes);
                cursor = dbUtilies.showDependonLangauae(sharedPreferences, activity);
                adapter.updateCursor(cursor);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                UserJokes userJokes = dataSnapshot.getValue(UserJokes.class);
                dbUtilies.updateJokeFeedback(userJokes);
                cursor = dbUtilies.showDependonLangauae(sharedPreferences, activity);
                adapter.updateCursor(cursor);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("error", databaseError.getMessage());

            }
        };

        reference.addChildEventListener(mChildEventListener);
    }
    }

    public void removeLisner()
    {
        if (mChildEventListener!=null)
        {
        reference.removeEventListener(mChildEventListener);
        mChildEventListener=null;
        }
    }
    public String getparent(String refrnce)
    {
        Scanner scanner=new Scanner(refrnce);
        String pattarn="https://lastjoke-736b0.firebaseio.com/users_jokes";
        scanner.skip(pattarn);
        return scanner.nextLine();
    }

}
