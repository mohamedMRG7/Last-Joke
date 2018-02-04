package com.example.moham.lastjoke.Database;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;

import com.example.moham.lastjoke.MainActivity;
import com.example.moham.lastjoke.MainJokesAdapter;
import com.example.moham.lastjoke.comonUtilties.ShardprfContract;
import com.example.moham.lastjoke.comonUtilties.SharedprfUtiles;
import com.example.moham.lastjoke.user.UserJokes;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Scanner;

/**
 * Created by moham on 1/31/2018.
 */

public class FirebaseDbUtilies {

    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference follwerref;
    DatabaseReference userinfoRef;
    ChildEventListener mChildEventListener;
    Cursor cursor;
    DbUtilies dbUtilies;
    SharedprfUtiles sharedprfUtiles;
    String userid;

    public FirebaseDbUtilies(Context context,SharedprfUtiles sharedprfUtiles,String uniq_id) {
        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("users_jokes");
        follwerref= database.getReference().child("users_followers").child("followers");
        dbUtilies=new DbUtilies(context);
        this.sharedprfUtiles=sharedprfUtiles;
        this.userid=uniq_id;
    }

    public FirebaseDbUtilies(Context context)
    {
        database=FirebaseDatabase.getInstance();
        dbUtilies=new DbUtilies(context);
        userinfoRef=database.getReference().child("userInfo");

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
    public void addfollwerlist(String email, List<String> follwerlist)
    {
        database.getReference().child("users_followers").child("followers").child(email).setValue(follwerlist);
    }
    public void addUserInfo(UserJokes userJokes)
    {
        database.getReference().child("userInfo").child(userJokes.getUserUniq_id()).setValue(userJokes);
    }

    public void readFromFB(final MainJokesAdapter adapter, final Activity activity, final SharedPreferences sharedPreferences, final String useremail, final String activityname) {

        if (mChildEventListener == null){
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                try {
                    UserJokes userJokes = dataSnapshot.getValue(UserJokes.class);

                    dbUtilies.addJoke(userJokes);
                    checkActivityname(sharedPreferences,activity,useremail,activityname);
                    adapter.updateCursor(cursor);
                }catch (Exception e) {

                    if (userid.equals(dataSnapshot.getKey())) {
                        List<String> list = (List<String>) dataSnapshot.getValue();
                        for (int i=0;i<list.size();i++) {
                            String email2 = list.get(i);
                            Cursor cursor2 = dbUtilies.getUserinfo(email2);
                            if (cursor2.moveToFirst()) {
                                String email = cursor2.getString(cursor2.getColumnIndex(JokeContract.UserEntry.COLUMN_EMAIL));
                                String username = cursor2.getString(cursor2.getColumnIndex(JokeContract.UserEntry.COLUMN_USERNAME));
                                Log.d("cursor",username);
                                UserJokes userJokes = new UserJokes();
                                userJokes.setUsername(username);
                                userJokes.setEmail(email);
                                dbUtilies.addJFollwer(userJokes);
                            }
                        }
                        sharedprfUtiles.saveObect(list, ShardprfContract.ISFOLLOW_KEY);
                        Log.d("list", dataSnapshot.getKey() + "");
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                try {
                    UserJokes userJokes = dataSnapshot.getValue(UserJokes.class);
                    dbUtilies.updateJokeFeedback(userJokes);
                    checkActivityname(sharedPreferences,activity,useremail,activityname);
                    adapter.updateCursor(cursor);
                }catch (Exception e){
                    if (userid.equals(dataSnapshot.getKey())) {
                        List<String> list = (List<String>) dataSnapshot.getValue();
                        sharedprfUtiles.saveObect(list, ShardprfContract.ISFOLLOW_KEY);
                        Log.d("list", dataSnapshot.getKey() + "");
                    }}
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
        follwerref.addChildEventListener(mChildEventListener);
    }
    }

    public void readUserinfo()
    {
        mChildEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                UserJokes userJokes=dataSnapshot.getValue(UserJokes.class);
                dbUtilies.addUserInfo(userJokes);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        userinfoRef.addChildEventListener(mChildEventListener);
    }
    public void removeLisner()
    {
        if (mChildEventListener!=null)
        {
        reference.removeEventListener(mChildEventListener);
        follwerref.removeEventListener(mChildEventListener);
        mChildEventListener=null;
        }
    }

    public void removeuserlisner()
    {
        if (mChildEventListener !=null)
        {
            userinfoRef.removeEventListener(mChildEventListener);
            mChildEventListener=null;
        }
    }
    public void checkActivityname(SharedPreferences sharedPreferences,Activity activity,String useremail,String activityname)
    {
        if (activityname.equals("authactivity")) {
            cursor = dbUtilies.showDependonLangauae(sharedPreferences, activity);
            Log.d("email",cursor.getCount()+"");
        }
        else if (activityname.equals("followingactivity")) {

             cursor = dbUtilies.getJokesforUser(useremail);
            Log.d("email",cursor.getCount()+"");
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
