package com.example.moham.lastjoke.comonUtilties;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.moham.lastjoke.R;

/**
 * Created by moham on 1/26/2018.
 */

public class PopupDialogUtiles {

    AlertDialog.Builder builder;
    AlertDialog dialog;
    ViewsActionInterface buttonActions;

    public PopupDialogUtiles (Activity context, int viewID, ViewsActionInterface action)
    {
        builder =new AlertDialog.Builder(context);
        LayoutInflater inflater=context.getLayoutInflater();
        View view1=inflater.inflate(viewID,null);
        builder.setView(view1);
        dialog= builder.create();
        action.action(view1,dialog);
    }

    public void showDialoge()
    {
      dialog=builder.show();
    }

    public void cancelDialog()
    {
        dialog.dismiss();
    }



}
