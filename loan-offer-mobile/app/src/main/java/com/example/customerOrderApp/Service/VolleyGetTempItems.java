package com.example.customerOrderApp.Service;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.customerOrderApp.Singleton.VolleySingleton;
import com.example.customerOrderApp.helper.AppSettings;
import com.example.customerOrderApp.helper.CustomDialog;
import com.example.customerOrderApp.pojo.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VolleyGetTempItems  {

    private static Context context;
    private static List<Item> itemList = new ArrayList<>();

    public static QuickAddItemDelegate quickAddItemDelegate;
    public interface QuickAddItemDelegate{
        void notifyAddItem();
    }

    private static Map<String, String> setHeaderData() {
        Map<String, String> params = new HashMap<>();

        String clientId = AppSettings.getClientId(context);
        String companyId = AppSettings.getCompanyId(context);
        String uniqueId = AppSettings.getUniqueId(context);
        String authId = AppSettings.getAuthId(context);

        params.put("client_id", clientId);
        params.put("company_id", companyId);
        params.put("user_id", uniqueId);
        params.put("authorization", authId);

        Log.d("temp client id ", clientId);

        return params;
    }

    private static Map<String, String> setHeaderDataDefault() {
        Map<String, String> params = new HashMap<>();

        /*String clientId = "CtBohqM6u9YmUuLBi8OiHYQh1HWfN9qvfAQsWQVJ";
        String companyId = "3c12ea6ee0dd1ce7";
        String uniqueId = "558378b8-84bf-4df2-a632-113adafe8af0";
        String authId = "o3la57RcaONgi9SVNCQ6kfl3Q0N3Nz1qaGqX0bYdqVeR7WlnPzhPzgVwGJOCvM6VP8aDgeBFOQb";*/

        String clientId = "UY6ZA78A7sIoxFBCAiSIAOiYXVEy8S5JvkvHSl5j";
        String companyId = "9c1b1de272ae427c";
        String uniqueId = "2b3c69ef-7bd1-4537-b382-d7cc781a0301";
        String authId = "4F4X41vW41aqUPW42rTDt8aJM9ahMVeWo5yeU85dwfSa9ZfRn8q4AtQXf2rfJfahDxR0cVvPklK";

        /*String clientId = "6x7L7LfSxJYnFLXQ7dVu1GNALCVJF7tiL64c7mEe";
        String companyId = "3c12ea6ee0dd1ce7";
        String uniqueId = "43e8723c-e533-4188-8ece-39f71bf83b84";
        String authId = "9YPfUcS9ng8j1uh2fjkURgdlX5I8p56rAkSkxWr4zHItNxdJNwkBmGB8Dq66mKuU9JgQxcJmWkR";*/

        params.put("client_id", clientId);
        params.put("company_id", companyId);
        params.put("user_id", uniqueId);
        params.put("authorization", authId);

        Log.d("temp client id ", clientId);

        return params;
    }

    public static void syncGetAllTempItems(final Context context1, final String url, QuickAddItemDelegate delegate) {
        quickAddItemDelegate = delegate;
        context = context1;
        ProgressDialog progressDialog = ProgressDialog.show(context, "Sync all data.", "Downloading...", true);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response_suc", response.toString());
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    Log.d("array length ", jsonArray.length() + " ");

                    if (itemList.size() > 0) {
                        itemList.clear();
                    }

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Item item = new Item();
                        Log.d("jsonObject id", jsonObject.getString("item_number"));
                        //  if (!jsonObject.isNull("client_id") && jsonObject.getString("client_id").equalsIgnoreCase(AppSettings.getClientId(context))) {
                        if (!jsonObject.isNull("item_number")) {
                            item.setItemNo(jsonObject.getString("item_number"));
                        }
                        if (!jsonObject.isNull("item_desc")) {
                            item.setItemName(jsonObject.getString("item_desc"));
                        }
                        if (!jsonObject.isNull("category")) {
                            if(!jsonObject.getString("category").equalsIgnoreCase("")) {
                                item.setItemCategory(jsonObject.getString("category"));
                            }else {
                                item.setItemCategory("Others");
                            }
                        }
                        if (!jsonObject.isNull("subcategory")) {
                            if(!jsonObject.getString("subcategory").equalsIgnoreCase("")) {
                                item.setItemSubCategory(jsonObject.getString("subcategory"));
                            }else {
                                item.setItemSubCategory("Others");
                            }
                        }
                        if (!jsonObject.isNull("last_purchase_price")) {
                            item.setItemPurchasePrice(jsonObject.getDouble("last_purchase_price"));
                        }
                        if (!jsonObject.isNull("selling_price")) {
                            item.setItemSellingPrice(jsonObject.getDouble("selling_price"));
                        }
                        if (!jsonObject.isNull("uom")) {
                            item.setItemUMO(jsonObject.getString("uom"));
                        }
                        item.setProductType("4");

                        itemList.add(item);

                    }
                    progressDialog.dismiss();

                    sendTempDataToServer(itemList,context);
                    quickAddItemDelegate.notifyAddItem();

                } catch (JSONException j) {
                    j.printStackTrace();
                    Log.e("printStackTrace ", " " + j.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                //return setHeaderData();
                return setHeaderDataDefault();
            }
        };

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private static void sendTempDataToServer(List<Item> itemList,Context context){

        //String developmentServerUrl = "http://54.200.81.66:3000/";
        //String developmentServerUrl = "http://dev.kalesystems.com:3000/";
        String developmentServerUrl = "https://dev.kalesystems.com:443/";
        String  itemURL = developmentServerUrl + "api/item/item";
        new CustomDialog().customProgressBar(context,"Downloading...","Please wait","H");
        for (Item item : itemList) {
            VolleyBackgroundTaskPost.postTempItem(context,itemURL,item,itemList.size());
        }
    }

}








        /*private static void storeTempItemsDB(List<Item> itemList){
            DBHandler dbHandler = DBSingleton.getInstance(context);
            if(itemList != null) {
                for (Item it : itemList) {

                    Item item = new Item();


                    if (it.getItemNumber() != null) {
                        item.setItemNumber(it.getItemNumber());
                    }
                    if (it.getItemDesc() != null) {
                        item.setItemDesc(it.getItemDesc());
                    }
                    if (it.getItemcategory() != null) {
                        item.setItemCategory(it.getItemcategory());
                    }
                    if (it.getItemSubCategory() != null) {
                        item.setItemSubCategory(it.getItemSubCategory());
                    }
                    if(it.getPurchasePrice() != null) {
                        item.setPurchasePrice(it.getPurchasePrice());
                    }
                    if(it.getItemPrice() != null){
                        item.setItemPrice(it.getItemPrice());
                    }
                    if (it.getUom() != null) {
                        item.setUom(it.getUom());
                    }

                    if (dbHandler.isNotExistItemNumber(item.getItemNumber(),dbHandler)) {

                        dbHandler.insertBulkTempItemInDB(item);
                    *//*if(ItemDashboard.loadItems != null) {
                        ItemDashboard.loadItems.setVisibility(View.GONE);
                    }*//*

                    }

                    Integer getId = 0;
                    getId = dbHandler.isExistCategory(item.getItemCategory());
                    Integer generate;
                    Integer getNumber = dbHandler.getCategoryItem();

                    if (getId != 0) {
                        if (getNumber != 0) {
                            generate = ++getNumber;
                        } else {
                            generate = 10;
                        }
                        Category category = new Category(generate+"Q", item.getItemSubCategory(), 0.0, 0.0, 1, 2, getId);
                        dbHandler.addCategory(category);

                    }else {
                        if (getNumber != 0) {
                            generate = ++getNumber;
                        } else {
                            generate = 10;
                        }

                        Category category = new Category(generate+"Q", item.getItemCategory(), 0.0, 0.0, 1, 1, 0);
                        dbHandler.addCategory(category);
                    }

                }


            }


        }*/


