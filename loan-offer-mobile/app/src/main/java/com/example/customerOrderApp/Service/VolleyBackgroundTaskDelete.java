package com.example.customerOrderApp.Service;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.customerOrderApp.Singleton.VolleySingleton;
import com.example.customerOrderApp.helper.AppSettings;
import com.example.customerOrderApp.helper.ToastMessageHelper;
import com.example.customerOrderApp.pojo.Item;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class VolleyBackgroundTaskDelete {


    private static Context context;



    //==============================================================================================
    //==============================================================================================
    //DELETE ITEM ON ITEMS
    //==============================================================================================
    //==============================================================================================
    public static void deleteItem(Context ctx, String url, String name){
        context = ctx;

        /*JSONObject json = new JSONObject();
        try {

            String clientId = AppSettings.getClientId(context);
            String companyId = AppSettings.getCompanyId(context);

            json.put("client_id", clientId);
            json.put("company_id", companyId);
            json.put("item_number", item.getItemNumber());
            json.put("bid", item.getBid());

        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("delete_item",response.toString());
                ToastMessageHelper.customSuccToast((Activity)ctx, name+" deleted");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("delete_item_err",error.toString());
                ToastMessageHelper.customErrToast((Activity)ctx, name+"Not deleted");
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData();
            }
        };


        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }



    //==============================================================================================
    //==============================================================================================
    //DELETE ITEM ON INVENTORY
    //==============================================================================================
    //==============================================================================================
    public static void deleteItemInventory(Context ctx, String url){
        context = ctx;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("delete_item",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("delete_item_err",error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData();
            }
        };


        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }



    private static byte[] setDeleteItemParams(Item item){

        Log.e("delete_item","COLL");

        String clientId = AppSettings.getClientId(context);
        String companyId = AppSettings.getCompanyId(context);
        //DBHandler dbHandler = DBSingleton.getInstance(context);
        //User user = dbHandler.getUserAuth(AppSettings.getUserSession(context));

        JSONObject params = new JSONObject();
        String body = null;
        try{
            //Here are your parameters:

            //String date = ConversionClass.postServerDateFormat(item.getDate());

            params.put("client_id", clientId);
            params.put("company_id", companyId);
            params.put("item_number", item.getItemNo());
            params.put("bid", item.getBid());

            body = params.toString();

        } catch (JSONException e){
            e.printStackTrace();
        }
        try{
            return body.getBytes("utf-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }



    private static Map<String, String> setHeaderData(){
        Map<String, String> headers = new HashMap<>();

        /*String clientId = AppSettings.getClientId(context);
        String companyId = AppSettings.getCompanyId(context);
        DBHandler dbHandler = DBSingleton.getInstance(context);
        User user = dbHandler.getUserAuth(AppSettings.getUserSession(context));*/

        String clientId = AppSettings.getClientId(context);
        String companyId = AppSettings.getCompanyId(context);
        //DbHandler dbHandler = DBSingleton.getInstance(context);
        //User user = dbHandler.getUserAuth(AppSettings.getUserSession(context));
        String userId = AppSettings.getUniqueId(context);
        String authId = AppSettings.getAuthId(context);


        /*headers.put("client_id",clientId);
        headers.put("company_id",companyId);
        headers.put("user_id",user.getUserUniqueId());
        headers.put("authorization",user.getUserAuthId());*/

        headers.put("client_id",clientId);
        headers.put("company_id",companyId);
        headers.put("user_id",userId);
        headers.put("authorization",authId);

        return headers;
    }

}
