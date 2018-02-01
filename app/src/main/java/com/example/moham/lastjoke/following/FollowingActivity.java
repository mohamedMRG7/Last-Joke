package com.example.moham.lastjoke.following;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.moham.lastjoke.MainJokesAdapter;
import com.example.moham.lastjoke.R;

/**
 * Created by moham on 1/27/2018.
 */

public class FollowingActivity extends AppCompatActivity implements MainJokesAdapter.Onitemclick{


    private RecyclerView mRecyclerView;
    private FollwingAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_jokes);

        setTitle("YOUR FAVOURITE LIST");

        mRecyclerView=findViewById(R.id.rv_userjokes);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        adapter=new FollwingAdapter();
        mRecyclerView.setAdapter(adapter);
    }


    //ON ITEM CLICK
    @Override
    public void onclick(int itempos) {

    }
}
