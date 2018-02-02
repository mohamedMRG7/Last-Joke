package com.example.moham.lastjoke.following;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.moham.lastjoke.MainJokesAdapter;
import com.example.moham.lastjoke.R;
import com.example.moham.lastjoke.comonUtilties.ShardprfContract;
import com.example.moham.lastjoke.comonUtilties.SharedprfUtiles;
import com.example.moham.lastjoke.user.UserJokes;
import com.firebase.ui.auth.data.model.User;

import java.util.List;

/**
 * Created by moham on 1/27/2018.
 */

public class FollowingActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private FollwingAdapter adapter;
    private List<String> followers;
    private SharedprfUtiles sharedprfUtiles;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_jokes);

        setTitle("YOUR FAVOURITE LIST");
        UserJokes userJokes= (UserJokes) getIntent().getSerializableExtra("followers");

        followers=userJokes.getFollowers();
        Log.d("follow",followers.size()+"");
        mRecyclerView=findViewById(R.id.rv_userjokes);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        adapter=new FollwingAdapter(followers);
        mRecyclerView.setAdapter(adapter);
    }


    //ON ITEM CLICK

}
