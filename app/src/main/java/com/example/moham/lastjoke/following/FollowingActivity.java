package com.example.moham.lastjoke.following;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moham.lastjoke.Database.DbUtilies;
import com.example.moham.lastjoke.Database.JokeContract;
import com.example.moham.lastjoke.MainActivity;
import com.example.moham.lastjoke.MainJokesAdapter;
import com.example.moham.lastjoke.R;
import com.example.moham.lastjoke.auth.AuthinticationActivity;
import com.example.moham.lastjoke.comonUtilties.ShardprfContract;
import com.example.moham.lastjoke.comonUtilties.SharedprfUtiles;
import com.example.moham.lastjoke.user.UserJokes;
import com.firebase.ui.auth.data.model.User;

import java.util.List;

/**
 * Created by moham on 1/27/2018.
 */

public class FollowingActivity extends AppCompatActivity implements FollwingAdapter.OnitemClick{


    private RecyclerView mRecyclerView;
    private FollwingAdapter adapter;

    private UserJokes userJokes;
    private SharedprfUtiles sharedprfUtiles;
    private TextView nofrinds;
    private ImageView logo;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_jokes);
        setTitle("YOUR FAVOURITE LIST");

        nofrinds=findViewById(R.id.tv_nofrinds);
        logo=findViewById(R.id.logo);
         userJokes= (UserJokes) getIntent().getSerializableExtra(AuthinticationActivity.AUTHKEY);
        DbUtilies dbUtilies=new DbUtilies(this);
        Cursor cursor=dbUtilies.getallFollowers();

        mRecyclerView=findViewById(R.id.rv_userjokes);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        adapter=new FollwingAdapter(cursor,this);
        mRecyclerView.setAdapter(adapter);
        if (adapter.getItemCount()==0)
        {
            nofrinds.setVisibility(View.VISIBLE);
            logo.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(String email) {
        Intent intent=new Intent(FollowingActivity.this,MainActivity.class);
        intent.putExtra("useremail",email);
        intent.putExtra("activity","followingactivity");
        intent.putExtra(AuthinticationActivity.AUTHKEY,userJokes);
        startActivity(intent);

    }


    //ON ITEM CLICK

}
