package com.example.customerOrderApp.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.android.volley.VolleyError;

public class ShowMsg {

    public static void parseVolleyError(Context context,VolleyError error) {
        try {
            int statusCode = new Integer(error.networkResponse.statusCode);
            String title = getErrorMsg(statusCode);
            String message = "Please Contact Service Team";

            showMsg(title,message,context);

        } catch (Exception e) {
            Log.e("statusCode_errot"," "+e);

            if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                    error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
            }
        }
    }

    public static void showMsg(String title, String msg, Context context) {
        show(title,msg, context);
    }

    private static void show(String title,String msg, Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                return ;
            }
        });

        alertDialog.setNegativeButton("Report", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"shankeer8than@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                i.putExtra(Intent.EXTRA_TEXT   , "body of email");

                try {
                    context.startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    ToastMessageHelper.customErrToast((Activity) context, "There are no email clients installed.");
                }
            }
        });
        alertDialog.create();
        alertDialog.show();
        //takeScreenshot((Activity) context);
        alertDialog.setCancelable(false);
    }

    private static String getErrorMsg(int statusCode){
        String errorMsg = "";

        switch (statusCode){
            case 403:errorMsg = "Your Authentication is wrong";break;
            case 404:errorMsg = "Client has a Error";break;
            case 500:errorMsg = "Your server has a Error";break;
            case 502:errorMsg = "Your server has a problem";break;
        }

        return errorMsg;
    }
}
