package com.example.customerOrderApp.helper;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by dev1 on 4/1/17.
 */

public class CheckOnline {
    @SuppressLint("MissingPermission")
    public static boolean isOnline(Context context){
        NetworkInfo netInfo = null;
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
             netInfo = cm.getActiveNetworkInfo();
        }
            return netInfo != null && netInfo.isConnectedOrConnecting();
        
    }

    public static void internetErrorMgs(Context context) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("No Connectivity");
        builder1.setMessage("An internet connection is required to complete this action. Please check" +
                " your connection and try again.");
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "   ",
                (dialog, id) -> {


                });


        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
}
