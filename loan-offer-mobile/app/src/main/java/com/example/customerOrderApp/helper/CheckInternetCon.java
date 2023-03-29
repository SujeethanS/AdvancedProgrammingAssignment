package com.example.customerOrderApp.helper;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

/**
 * Created by dev1 on 7/26/17.
 */

public class CheckInternetCon extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case "android.net.conn.CONNECTIVITY_CHANGE":
            case "android.net.wifi.WIFI_STATE_CHANGED":
            case "android.net.wifi.STATE_CHANGE":
                break;


        }
    }

    public static void networkError(final Context context) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Network isn't available. Turn on your Wi-Fi");
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Settings",
                (dialog, id) -> context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)));

        builder1.setNegativeButton(
                "Cancel",
                (dialog, id) -> {
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


}
