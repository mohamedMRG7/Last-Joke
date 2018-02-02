package com.example.moham.lastjoke;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moham.lastjoke.Database.JokeContract;

/**
 * Created by moham on 1/27/2018.
 */

public class MainJokesAdapter extends RecyclerView.Adapter<MainJokesAdapter.JokeAdapterViewHokder> {



    private Onitemclick onitemclick;
    Cursor cursor;



    public MainJokesAdapter (Onitemclick onitemclick ,Cursor cursor)
    {
        this.onitemclick=onitemclick;
        this.cursor=cursor;
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
            //make position-1 cuse it will start from postion 1 and i want it tio start from postion 0
            // so i removed 1 from it and added 1 to the count
            cursor.moveToPosition(position-1);
            String joke=cursor.getString(cursor.getColumnIndex(JokeContract.JokeEntry.COLUMN_JOKE));
            String sadnumber=String.valueOf(cursor.getInt(cursor.getColumnIndex(JokeContract.JokeEntry.COLUMN_SADNUM)));
            String happynumber=String.valueOf(cursor.getInt(cursor.getColumnIndex(JokeContract.JokeEntry.COLUMN_HAPPYNUM)));

            holder.txt_joke.setText(joke);
            holder.txt_sadNumber.setText(String.valueOf(sadnumber));
            holder.txt_happyNumber.setText(String.valueOf(happynumber));
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount()+1;
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

            if (position>=0) {
                cursor.moveToPosition(position);
                String username=cursor.getString(cursor.getColumnIndex(JokeContract.JokeEntry.COLUMN_USERNAME));
                String email=cursor.getString(cursor.getColumnIndex(JokeContract.JokeEntry.COLUMN_EMAIL));
                String joke=cursor.getString(cursor.getColumnIndex(JokeContract.JokeEntry.COLUMN_JOKE));
                String key=cursor.getString(cursor.getColumnIndex(JokeContract.JokeEntry.COLUMN_USER_ICON));
                int happynum=cursor.getInt(cursor.getColumnIndex(JokeContract.JokeEntry.COLUMN_HAPPYNUM));
                int sadnum=cursor.getInt(cursor.getColumnIndex(JokeContract.JokeEntry.COLUMN_SADNUM));
                onitemclick.onclick(username, email, joke,key,happynum,sadnum);
            }
        }
    }

    public void updateCursor(Cursor cursor)
    {
        this.cursor=cursor;
        notifyDataSetChanged();
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
            void onclick(String username,String joke,String email,String key,int happynum,int sadnum);
        }
}
