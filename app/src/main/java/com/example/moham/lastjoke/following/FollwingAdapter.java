package com.example.moham.lastjoke.following;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moham.lastjoke.R;

/**
 * Created by moham on 1/29/2018.
 */

public class FollwingAdapter  extends RecyclerView.Adapter<FollwingAdapter.FollwingAdapterViewholer>{


    private String [] dummynames={"mohamed ahmed","mohamed 5alel","ahmed sha3ban"};
    @Override
    public FollwingAdapterViewholer onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());

        View view=inflater.inflate(R.layout.user_jokes_itemcontent,parent,false);

        return new FollwingAdapterViewholer(view);
    }

    @Override
    public void onBindViewHolder(FollwingAdapterViewholer holder, int position) {

        holder.username.setText(dummynames[position]);
    }

    @Override
    public int getItemCount() {
        return dummynames.length;
    }




    class FollwingAdapterViewholer extends RecyclerView.ViewHolder
    {
        TextView username;
        public FollwingAdapterViewholer(View itemView) {
            super(itemView);
            username= itemView.findViewById(R.id.tv_username);
        }
    }

}
