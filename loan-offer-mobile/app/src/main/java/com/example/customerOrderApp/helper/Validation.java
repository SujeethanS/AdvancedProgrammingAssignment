package com.example.customerOrderApp.helper;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

public class Validation {

    public static boolean validateEditText(EditText[] fields){
        for(EditText editText : fields){
            if(editText.getText().toString().trim().length() == 0 || editText.getText().toString().equalsIgnoreCase(".")){
                editText.setError("Please enter valid data");
                return false;
            }
        }

        return true;
    }


    public static String getDeviceModelName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(model);
        }
    }
    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static boolean getModelNEW9220(){
        return getDeviceModelName().equalsIgnoreCase("NEW9220");
    }

    public static int getIndustryTypeId(String industry){
        int type;
        switch (industry){
            case "Multi Shop":
                type = 100;
                break;
            case "Pharmacy":
                type = 101;
                break;
            case "Restaurant":
                type = 102;
                break;
                default:
                    type = 100;
        }
        return type;
    }

    private static String cardCheck(String value){

        if(value.length() <= 19){
            if(value.substring(0,1).equalsIgnoreCase("4")){
                return "VISA";
            }

            if(value.substring(0,1).equalsIgnoreCase("5")) {
                if (castNumber(value.substring(1, 2)) <= 5 && castNumber(value.substring(1, 2)) >= 1) {
                    if (castNumber(value.substring(0, 6)) <= 559999 && castNumber(value.substring(0, 6)) >= 51000){
                        return "MASTER";
                    }
                }
            }
            if (value.substring(0,1).equalsIgnoreCase("2")){
                if (castNumber(value.substring(1, 2)) <= 7 && castNumber(value.substring(1, 2)) >= 2) {
                    if (castNumber(value.substring(0, 6)) <= 272099 && castNumber(value.substring(0, 6)) >= 222100 ){
                        return "MASTER";
                    }
                }
            }
            if(castNumber(value.substring(0,4)) <= 3589 && castNumber(value.substring(0,4)) >= 3528  ){
                return "JCB";
            }
            if(castNumber(value.substring(0, 6)) <= 601109 && castNumber(value.substring(0, 6)) >= 601100 )
                return "DISCOVER";
                if(castNumber(value.substring(0, 6)) <= 601149 && castNumber(value.substring(0, 6)) >= 601120 )
                    return "DISCOVER";
                    if(castNumber(value.substring(0, 6)) == 601174 )
                        return "DISCOVER";
                        if(castNumber(value.substring(0, 6)) <= 601179 && castNumber(value.substring(0, 6)) >= 601177 )
                            return "DISCOVER";
                            if(castNumber(value.substring(0, 6)) <= 601199 && castNumber(value.substring(0, 6)) >= 601186 )
                                return "DISCOVER";
                                if(castNumber(value.substring(0, 6)) <= 659999 && castNumber(value.substring(0, 6)) >= 644000 )
                                    return "DISCOVER";
            }

        return "";
    }

    private static int castNumber(String value){
        return Integer.parseInt(value);
    }

    public static long totalMemorySize(){
        return Runtime.getRuntime().totalMemory() / 1048576;
    }

    public static long totalFreeMemorySize(){
        return Runtime.getRuntime().freeMemory() / 1048576;
    }

    public static long totalUsageMemorySize(){
        return totalMemorySize() - totalFreeMemorySize() / 1048576;
    }

    public static long totalHeapMemorySize(){
        return Runtime.getRuntime().maxMemory() / 1048576;
    }

    public static void HideBottomNavigationCall(Activity activity) {
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v =  activity.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 21) {
            //for new api versions.
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }


    public static void hideSystemUI(Activity activity) {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    public static void showSystemUI(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public static String translateVendorID(int vendorID) {
        String model="";
        switch (vendorID) {
            case 1208:
                model = "Epson";
                break;
            case 1305:
                model = "StarMicro";
                break;
            case 1046:
            case 4070:
            case 1155:
                model = "Non-Epson";
                break;
            default:
                model = "N";

        }
        return model;
    }


}

