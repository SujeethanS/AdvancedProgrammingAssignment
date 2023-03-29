package com.example.customerOrderApp.helper;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.UUID;

/**
  Created by kaledev7 on 7/20/2017.
 */

public class ConversionClass {
    public static String getPaymentMethod(int methodId){
        String method;
        switch (methodId){
            case 1001:
                method = "Cash";
                break;
            case 1002:
                method = "Cheque";
                break;
            case 1003:
                method = "Card";
                break;
            case 1004:
                method = "Credit";
                break;
            case 1005:
                method = "Return";
                break;
            case 1006:
                method = "Created";
                break;
            case 1010:
                method = "Deleted";
                break;
            default:method="";
                break;
        }
        return method;
    }

    public static String getServerDateFormat(String date){
        if(!date.equalsIgnoreCase("n/l")) {
            String time = ReadableDateFormat.getTodayTimeForServer(new Date(System.currentTimeMillis()));
            Log.e("DATE", date);
            Log.e("TIME",time);
            return date+"T"+time+"Z";
        }
        else{
            return "n/l";
        }
    }


    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

    public static String  validationQtyAndUom(String uom,Double qty) {
        if (qty>0) {
            DecimalFormat decimalFormat = new DecimalFormat("#.###");

            switch (uom.toLowerCase()){
                case "no":
                    return decimalFormat.format(qty)+"";
                case "kg":
                    if (qty < 1) {
                        return decimalFormat.format(qty * 1000) + " g";
                    } else {
                        return decimalFormat.format(qty) + " kg";
                    }
                case "g":
                    if (qty >= 1000) {
                        return decimalFormat.format(qty / 1000) + " kg";
                    } else {
                        return decimalFormat.format(qty) + " g";
                    }

                case "l":
                    if (qty < 1) {
                        return decimalFormat.format(qty * 1000) + " ml";
                    } else {
                        return decimalFormat.format(qty) + " l";
                    }
                case "ml":
                    if (qty >= 1000) {
                        return decimalFormat.format(qty / 1000) + " l";
                    } else {
                        return decimalFormat.format(qty) + " ml";
                    }

                case "m":
                    if (qty < 1) {
                        return decimalFormat.format(qty * 100) + " cm";
                    } else {
                        return decimalFormat.format(qty) + " m";
                    }
                case "cm":
                    if (qty >= 100) {
                        return decimalFormat.format(qty / 100) + " m";
                    } else {
                        return decimalFormat.format(qty) + " cm";
                    }
                default:return decimalFormat.format(qty)+" "+uom;
            }

        }else {
            return "0";
        }
    }

}
