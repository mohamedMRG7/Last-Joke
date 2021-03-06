package com.example.moham.lastjoke.following;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moham.lastjoke.Database.DbUtilies;
import com.example.moham.lastjoke.Database.JokeContract;
import com.example.moham.lastjoke.R;

import java.util.List;

/**
 * Created by moham on 1/29/2018.
 */

public class FollwingAdapter  extends RecyclerView.Adapter<FollwingAdapter.FollwingAdapterViewholer> {


    private Cursor cursor;
    private OnitemClick onitemClick;

    public FollwingAdapter(Cursor cursor,OnitemClick onitemClick) {
        this.cursor = cursor;
        this .onitemClick=onitemClick;
    }

    @Override
    public FollwingAdapterViewholer onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());

        View view=inflater.inflate(R.layout.user_jokes_itemcontent,parent,false);

        return new FollwingAdapterViewholer(view);
    }

    @Override
    public void onBindViewHolder(FollwingAdapterViewholer holder, int position) {

        cursor.moveToPosition(position);

        String username=cursor.getString(cursor.getColumnIndex(JokeContract.FollowersEntry.COLUMN_USERNAME));
        String email=cursor.getString(cursor.getColumnIndex(JokeContract.FollowersEntry.COLUMN_EMAIL));
        Cursor userjokes= DbUtilies.getJokesforUser(email);
        String numofJokes=String.valueOf(userjokes.getCount());
        holder.numberofjokes.setText(numofJokes);
        holder.username.setText(username);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }




    class FollwingAdapterViewholer extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView username,numberofjokes;

        public FollwingAdapterViewholer(View itemView) {
            super(itemView);
            username= itemView.findViewById(R.id.tv_username);
            numberofjokes=itemView.findViewById(R.id.tv_numofjokes);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position=getAdapterPosition();
            cursor.moveToPosition(position);
            String email=cursor.getString(cursor.getColumnIndex(JokeContract.FollowersEntry.COLUMN_EMAIL));
            onitemClick.onClick(email);
        }
    }

    interface OnitemClick
    {
        void onClick(String email);
    }
}
