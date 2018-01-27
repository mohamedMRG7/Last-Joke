package com.example.moham.lastjoke;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by moham on 1/27/2018.
 */

public class MainJokesAdapter extends RecyclerView.Adapter<MainJokesAdapter.JokeAdapterViewHokder> {


    private String [] joke={"mara enten byl3abo sala7 wa7d mat w el tany shala7at","mara wa7ed etneen telata","mara etneen w 3shereen e8tasabo 23 fe el 7amam","fk u bitch"};
    private int[] sadNmbers={22,32,5,13};
    private int[] happynumber={3,6,4,22};
    private Onitemclick onitemclick;



    public MainJokesAdapter (Onitemclick onitemclick)
    {
        this.onitemclick=onitemclick;
    }



    @Override
    public JokeAdapterViewHokder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(viewType,parent,false);

        return new JokeAdapterViewHokder(view);
    }

    @Override
    public void onBindViewHolder(JokeAdapterViewHokder holder, int position) {

        if (position !=0) {
            holder.txt_joke.setText(joke[position-1]);
            holder.txt_sadNumber.setText(String.valueOf(sadNmbers[position-1]));
            holder.txt_happyNumber.setText(String.valueOf(happynumber[position-1]));
        }
    }

    @Override
    public int getItemCount() {
        return joke.length+1;
    }





    class JokeAdapterViewHokder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView txt_joke,txt_happyNumber,txt_sadNumber;

        public JokeAdapterViewHokder(View itemView) {
            super(itemView);
            txt_joke=itemView.findViewById(R.id.tv_joke);
            txt_happyNumber=itemView.findViewById(R.id.tv_happyfacenumbers);
            txt_sadNumber=itemView.findViewById(R.id.tv_sadfacenumbers);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {


            int position =getAdapterPosition()-1;
            if (position>=0)
            onitemclick.onclick(position);

        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return R.layout.profile_upper_content;


            default:
                return R.layout.alljokes_rvitem;
        }
        }




        public interface Onitemclick
        {
            void onclick(int itempos);
        }
}
