package com.example.customerOrderApp.Json;

import org.json.JSONException;
import org.json.JSONObject;

public class WebOrdersJSONParser {
    public static String getUserAuth(String content) {
        String itemNumber;
        try {

            JSONObject jsonObject = new JSONObject(content);
            itemNumber = jsonObject.getString("Auth-token");

            return itemNumber;

        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
            System.out.print("ERROR " + e);
            return null;
        }


    }


    public static String parseFeedClientID(String content) {
        String itemNumber = null;
        try {

            JSONObject jsonObject = new JSONObject(content);
            for (int i = 0; i < jsonObject.length(); i++) {
                itemNumber = jsonObject.getJSONObject("companysetup").getJSONObject("body").getString("client_id");
            }

            return itemNumber;

        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
            System.out.print("ERROR " + e);
            return null;
        }


    }


}
