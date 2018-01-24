package com.example.moham.lastjoke;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener{

    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionHelper rfabHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);


        AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater=getLayoutInflater();
        View view=inflater.inflate(R.layout.addjoke_contents,null);

        dialog.setView(view);

        dialog.show();


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
    }


    //on floating action menu click
    @Override
    public void onRFACItemLabelClick(int i, RFACLabelItem rfacLabelItem) {
        rfabHelper.toggleContent();
        int positionIndex = 6 - i;
        if(i==0)
        Log.d("Main activity",positionIndex+"");
    }

    @Override
    public void onRFACItemIconClick(int i, RFACLabelItem rfacLabelItem) {
        rfabHelper.toggleContent();
        int positionIndex = 6 - i;
    }





    //Menu list items edit
    private List<RFACLabelItem> getfloatingMenuList()
    {
        List<RFACLabelItem> items = new ArrayList<>();

        items.add(new RFACLabelItem<Integer>()
                .setLabel("My Jokes")
                .setResId(R.drawable.myjokes)

                .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("Followed")
                .setResId(R.drawable.followers)

                .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("My Favourite Jokes")
                .setResId(R.drawable.favourits)

                .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("Setting")
                .setResId(R.drawable.settings)

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
}
