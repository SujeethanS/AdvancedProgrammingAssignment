package com.example.customerOrderApp.Json;

import android.content.Context;

import com.example.customerOrderApp.helper.AppSettings;
import com.example.customerOrderApp.pojo.Item;

import org.json.JSONArray;
import org.json.JSONObject;

public class ItemsJSONParser {

    public static Item sendItem(Context context, String content) {
        try {

            Item item = null;

            JSONObject jsonObject = new JSONObject(content);
            JSONArray jsonArray = jsonObject.getJSONArray("item");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                item = new Item();
                if (!object.isNull("client_id") && object.getString("client_id").equalsIgnoreCase(AppSettings.getClientId(context))) {

                    if (!object.isNull("item_number")) {
                        item.setItemNo(object.getString("item_number"));
                    }
                    if (!object.isNull("bid")) {
                        item.setBid(object.getInt("bid"));
                    }

                }
            }

            return item;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


}
