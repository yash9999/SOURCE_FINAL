package com.example.yogeshgarg.source.common.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


import com.example.yogeshgarg.source.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Braintech on 22-Jul-16.
 */
public class AlertDialogManager {

    public static void sAlertFinish(final Context context, String msg) {


        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setCancelable(false);


        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                ((Activity) context).finish();
                arg0.dismiss();
            }
        });

        final AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    public static void alertShowImage(final Context mContext, String imgUrl) {
        try {

            final Dialog customDialog = new Dialog(mContext);

            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.alert_show_image);
            customDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            customDialog.setCancelable(true);


            ImageView imageView = (ImageView) customDialog.findViewById(R.id.imageView);

            Picasso.with(mContext).load(imgUrl).into(imageView);


            customDialog.show();


        } catch (RuntimeException e) {


        }


    }

}
