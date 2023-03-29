package com.example.customerOrderApp.http;

import android.content.Context;
import android.util.Log;


import com.example.customerOrderApp.helper.AppSettings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dev1 on 2/23/17.
 */

public class HttpManager {

    public static String getData(Context context, String uri){

        BufferedReader reader = null;

        try{
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            String clientId = AppSettings.getClientId(context);
            String companyId = AppSettings.getCompanyId(context);
            String uniqueId = AppSettings.getUniqueId(context);
            String authId = AppSettings.getAuthId(context);

           // User user = dbHandler.getUserAuth(AppSettings.getUserSession(context));

            Log.e("check",clientId);
            Log.e("check",companyId);
            Log.e("check",uniqueId);
            Log.e("check",authId);

            con.setRequestProperty("client_id",clientId);
            con.setRequestProperty("company_id",companyId);
            con.setRequestProperty("user_id",uniqueId);
            con.setRequestProperty("authorization",authId);



            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;
            while((line = reader.readLine()) != null){
                sb.append(line + "\n");
            }



            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            if(reader != null){
                try {
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
}
