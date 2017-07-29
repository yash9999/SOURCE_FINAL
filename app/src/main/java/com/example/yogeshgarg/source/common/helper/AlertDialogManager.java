package com.example.yogeshgarg.source.common.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.yogeshgarg.source.common.session.UserSession;
import com.example.yogeshgarg.source.mvp.login.LoginActivity;


/**
 * Created by Braintech on 28-03-2017.
 */

public class AlertDialogManager {

    public static void showMessage(String msg, Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    public static void showDialogToClose(final Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Message
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(DialogInterface dialog, int which) {
                ((Activity) context).finishAffinity();
                System.exit(0);
                dialog.dismiss();
            }
        });


        // Showing Alert Message
        alertDialog.show();
    }


/*
    public static void showAlertToConnectionTimeout(
            //final ApiAdapter.ConnectionTimeout connectionTimeout,
            final Context context,
            final String apiType) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setCancelable(false);

        // Setting Dialog Message
        alertDialog.setMessage("Connection is taking too long to respond. Please retry..");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                connectionTimeout.retry(apiType);
                dialog.dismiss();
            }
        });

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                ((Activity) context).startActivityForResult(intent, 12345);
                dialog.dismiss();
            }
        });


        // Showing Alert Message
        alertDialog.show();
    }

}
*/
public static void showAlertLogout(final Context context) {
    final android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(context);
    alertDialog.setCancelable(true);
    alertDialog.setTitle("Logout!");
    alertDialog.setMessage("Are you sure you want to Logout?");

    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            UserSession userSession = new UserSession(context);
            userSession.clearUserSession();
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            ((Activity) context).finishAffinity();
        }
    });

    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    });
    alertDialog.show();
}

}