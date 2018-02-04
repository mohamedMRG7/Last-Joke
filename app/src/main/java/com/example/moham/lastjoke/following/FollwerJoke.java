package com.example.moham.lastjoke.following;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.moham.lastjoke.Database.DbUtilies;
import com.example.moham.lastjoke.MainJokesAdapter;
import com.example.moham.lastjoke.R;

public class FollwerJoke extends AppCompatActivity implements MainJokesAdapter.Onitemclick{

    private RecyclerView recyclerView;
    private MainJokesAdapter adapter;
    private DbUtilies dbUtilies;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follwer_joke);

        dbUtilies=new DbUtilies(this);
        String email=getIntent().getExtras().getString("useremail");
        cursor=dbUtilies.getJokesforUser(email);
        recyclerView=findViewById(R.id.rv_followerJokes);
        adapter=new MainJokesAdapter(this,cursor);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onclick(String username, String joke, String email, String key, int happynum, int sadnum, String useruniqid) {

    }
}
