package com.example.moham.lastjoke;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.moham.lastjoke.Database.DbUtilies;
import com.example.moham.lastjoke.Database.FirebaseDbUtilies;
import com.example.moham.lastjoke.comonUtilties.Dofn_after_fn;
import com.example.moham.lastjoke.comonUtilties.Done;
import com.example.moham.lastjoke.comonUtilties.Done2;
import com.example.moham.lastjoke.comonUtilties.PopupDialogUtiles;
import com.example.moham.lastjoke.comonUtilties.ViewsActionInterface;
import com.example.moham.lastjoke.following.FollowingActivity;
import com.example.moham.lastjoke.setting.SettingActivity;
import com.example.moham.lastjoke.user.UserJokes;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements
        MainJokesAdapter.Onitemclick,View.OnClickListener,
        RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener,
        SharedPreferences.OnSharedPreferenceChangeListener{



    private RapidFloatingActionHelper rfabHelper;
    private PopupDialogUtiles dialogUtiles;
    private MainJokesAdapter adapter;
    private RecyclerView rv_alljokes;
    private EditText et_addjoke;
    private DbUtilies dbUtilies;
    private Cursor cursor;
    private SharedPreferences sharedPreferences;
    private FirebaseAuth firebaseAuth;
    UserJokes userJokes;
    FirebaseUser user1;
    FirebaseDbUtilies db;
    DatabaseReference reference;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);





        //dialog intiation
         dialogUtiles=new PopupDialogUtiles(MainActivity.this, R.layout.activity_addjoke, new ViewsActionInterface() {


            @Override
            public void action(View view, android.app.AlertDialog dialog) {

                Button add =view.findViewById(R.id.bt_addjoke);
                add.setOnClickListener(MainActivity.this);
                Button cancel=view.findViewById(R.id.bt_cancel);
                cancel.setOnClickListener(MainActivity.this);
                et_addjoke=view.findViewById(R.id.et_addjoke);
            }
        });





        userJokes= (UserJokes) getIntent().getSerializableExtra(AuthinticationActivity.AUTHKEY);

        Log.d("ONCreat",userJokes.getEmail().isEmpty()+"");


        db=new FirebaseDbUtilies(this);


        dbUtilies=new DbUtilies(this);





        sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
        cursor=dbUtilies.showDependonLangauae(sharedPreferences,MainActivity.this);



        //Recycler view set up
         rv_alljokes=findViewById(R.id.rv_alljokes);
         adapter=new MainJokesAdapter(this,cursor);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_alljokes.setLayoutManager(layoutManager);
         rv_alljokes.setAdapter(adapter);



        //set up floating action button and meus
        List<RFACLabelItem> listOfFloatingMenu=getfloatingMenuList();
        RapidFloatingActionContentLabelList labelshape=getlablelcontentshap(getApplicationContext(),listOfFloatingMenu);


        //build floating button with list menu
        rfabHelper = new RapidFloatingActionHelper(
                getApplicationContext(),
                (RapidFloatingActionLayout) findViewById(R.id.activity_main_rfal),
                (RapidFloatingActionButton) findViewById(R.id.activity_main_rfab),
                labelshape
        ).build();



    PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    db.readFromFB(adapter,MainActivity.this,sharedPreferences);
    }



    //on floating action menu click
    @Override
    public void onRFACItemLabelClick(int i, RFACLabelItem rfacLabelItem) {
        rfabHelper.toggleContent();

        switch (i) {
            case  0:
                //add joke
                dialogUtiles.showDialoge();
                break;
            case  1:
                //move to followes activity
                Intent intent=new Intent(MainActivity.this, FollowingActivity.class);
                startActivity(intent);
                break;

            case  2:
                //start setting fragmentc

                Intent intent1=new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent1);
                break;

            case  3:
                signOut();
        }
    }



    @Override
    public void onRFACItemIconClick(int i, RFACLabelItem rfacLabelItem) {
        rfabHelper.toggleContent();
        int positionIndex = 6 - i;
    }





    private void signOut() {
        AuthUI.getInstance()
                .signOut(getApplicationContext())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        db.removeLisner();
                    }
                });
    }



    //Menu list items edit
    private List<RFACLabelItem> getfloatingMenuList()
    {
        List<RFACLabelItem> items = new ArrayList<>();

        items.add(new RFACLabelItem<Integer>()
                .setLabel("Add Joke")
                .setResId(R.drawable.myjokes)

                .setWrapper(0)
        );

        items.add(new RFACLabelItem<Integer>()
                .setLabel("My Funny people")
                .setResId(R.drawable.favourits)

                .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("Setting")
                .setResId(R.drawable.settings)

                .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("Sign Out")
                .setResId(R.drawable.exit)

                .setWrapper(0)
        );

        return items;
    }

    //Lable shape and add items
    private RapidFloatingActionContentLabelList getlablelcontentshap(Context context,List<RFACLabelItem> items)

    {
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(context);

        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);


        rfaContent
                .setItems(items)
                .setIconShadowRadius(ABTextUtil.dip2px(getApplicationContext(), 5))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(ABTextUtil.dip2px(getApplicationContext(), 5))
        ;

        return rfaContent;
    }


    //add joke click lisner
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_cancel :
                dialogUtiles.cancelDialog();
                break;

            case R.id.bt_addjoke :
                String joke=et_addjoke.getText().toString();
                String name=userJokes.getUsername();
                String email=userJokes.getEmail();
                String uniq_id=String.valueOf(new Random().nextInt());
                String icon ="logonotext.png";
                UserJokes userJokes=new UserJokes(name,email,uniq_id,icon,joke,0,0);
                db.addUserJoketoFB(userJokes);
                cursor=dbUtilies.showDependonLangauae(sharedPreferences,MainActivity.this);
                adapter.updateCursor(cursor);
                et_addjoke.setText("");
                dialogUtiles.cancelDialog();
                break;

                    //add joke to data base



        }

    }

    @Override
    public void onclick(int itempos) {
        Log.d("onclick",itempos+"");

    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

        cursor=dbUtilies.showDependonLangauae(sharedPreferences,MainActivity.this);
        adapter.updateCursor(cursor);
        Log.d("shared change",s);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }
}
