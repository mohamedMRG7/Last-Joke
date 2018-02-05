package com.example.moham.lastjoke.comonUtilties;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.moham.lastjoke.R;
import com.example.moham.lastjoke.animation.AnimationUtilies;

/**
 * Created by moham on 1/26/2018.
 */

public class PopupDialogUtiles {

    AlertDialog.Builder builder;
    AlertDialog dialog;
    ViewsActionInterface buttonActions;
    View view1;

    public PopupDialogUtiles (Activity context, int viewID, ViewsActionInterface action)
    {
        builder =new AlertDialog.Builder(context);
        builder.create();
        LayoutInflater inflater=context.getLayoutInflater();

        view1=inflater.inflate(viewID,null,false);



        builder.setView(view1);

        action.action(view1,dialog);
    }

    public void showDialoge()
    {
        if (view1.getParent() !=null)
            ((ViewGroup)view1.getParent()).removeView(view1);
        dialog=builder.show();
        AnimationUtilies.zomein(view1);
    }

    public void cancelDialog()
    {

        dialog.dismiss();


    }



}
