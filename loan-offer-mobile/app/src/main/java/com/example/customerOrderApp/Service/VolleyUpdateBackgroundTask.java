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
import com.example.customerOrderApp.pojo.OrderDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VolleyUpdateBackgroundTask {

    private static Context context;
    private static int count = 0;

    private static UpdateCustomerDelegate updateCustomerDelegate;

    public interface UpdateCustomerDelegate{
        void notifyUpdateCustomer(int success);
    }

    public static void updateItem(Context ctx, String url, Item item, int size){
        context = ctx;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                count = count++;
                if(size == count) {
                    ToastMessageHelper.customSuccToast((Activity) ctx,"Updated");
                }
                Log.e("respomce_item_update",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                count = count++;
                if(size == count) {
                    ToastMessageHelper.customErrToast((Activity) ctx,"Not Updated");
                }
                Log.e("respomce_item_err",error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData();
            }

            @Override
            public byte[] getBody() {
                return setItemParams(item);
            }
        };
        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
        count = 0;
    }

    public static void updateAddQty(Context ctx,String url,List<Item> itemList){
        context = ctx;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("respomce_item",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("respomce_item_err",error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData();
            }

            @Override
            public byte[] getBody() {
                return setItemParams(itemList);
            }
        };
        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }

    public static void updateReduceQty(Context ctx,String url,List<OrderDetails> itemList){
        context = ctx;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.e("respomce_item",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("respomce_item_err",error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData();
            }

            @Override
            public byte[] getBody() {
                return setItemReduceParams(itemList);
            }
        };
        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }

    public static void updateReduceQty(Context ctx,String url,OrderDetails orderDetails,Double qty){
        context = ctx;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("respomce_item",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("respomce_item_err",error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData();
            }

            @Override
            public byte[] getBody() {
                return setItemReduceParams(orderDetails,qty);
            }
        };
        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }


    private static byte[] setItemParams(Item item) {

        String clientId = AppSettings.getClientId(context);
        String companyId = AppSettings.getCompanyId(context);

        String body = null;
        try{

            JSONObject params = new JSONObject();

            params.put("client_id", clientId);
            params.put("company_id", companyId);
            params.put("item_number", item.getItemNo());
            params.put("temp_bid",item.getBid());
            params.put("bid", item.getBid());
            params.put("track_inventory",item.getItemTrackItem());
            params.put("item_desc", item.getItemName());
            params.put("category", item.getItemCategory());
            params.put("subcategory", item.getItemSubCategory());
            params.put("uom", item.getItemUMO());
            params.put("selling_price", item.getItemSellingPrice());
            params.put("max_discount", item.getItemMaxDiscount());
            params.put("max_discount_type",item.getItemMaxDiscountType());
            params.put("default_discount", item.getItemDefaultDiscount());
            params.put("default_discount_type",item.getItemDefaultDiscountType());
            params.put("type", String.valueOf(item.getProductType())); // this is product item

            params.put("vatcode1", item.getVatCode1());//String
            params.put("vatcode2", item.getVatCode2());//String
            params.put("vatcode3", item.getVatCode3());//String
            params.put("vatcode4", item.getVatCode4());//String

            params.put("allow_sales", "1"); // this is product item

            body = params.toString();
            Log.e("update_item",body);

            return body.getBytes(StandardCharsets.UTF_8);


        } catch (JSONException e){
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }


    private static byte[] setItemParams(List<Item> itemList) {

        String clientId = AppSettings.getClientId(context);
        String companyId = AppSettings.getCompanyId(context);
        //DBHandler dbHandler = DBSingleton.getInstance(context);
        //User user = dbHandler.getUserAuth(AppSettings.getUserSession(context));

        String body = null;
        try{
            //Here are your parameters:

            //String date = ConversionClass.postServerDateFormat(item.getDate());

            for(Item item : itemList) {

                JSONObject params = new JSONObject();


                params.put("client_id", clientId);
                params.put("company_id", companyId);
                params.put("item_number", item.getItemNo());
                params.put("bid", item.getItemBatchNo());
                params.put("qoh", item.getItemQty());

                body = params.toString();

                return body.getBytes("utf-8");
            }

        } catch (JSONException e){
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] setItemReduceParams(List<OrderDetails> itemList) {

        String clientId = AppSettings.getClientId(context);
        String companyId = AppSettings.getCompanyId(context);
        //DBHandler dbHandler = DBSingleton.getInstance(context);
        //User user = dbHandler.getUserAuth(AppSettings.getUserSession(context));

        String body = null;
        try{
            //Here are your parameters:

            //String date = ConversionClass.postServerDateFormat(item.getDate());

            for(OrderDetails item : itemList) {

                JSONObject params = new JSONObject();


                params.put("client_id", clientId);
                params.put("company_id", companyId);
                params.put("item_number", item.getOrderDetailsItemNumber());
                params.put("bid", item.getOrderDetailsPatchId());
                params.put("qoh", -item.getOrderDetailsItemQty());

                //Log.e("CRCRCRCR","in "+item.getItemNumber()+" bid "+item.getBatchNo()+" qoh "+item.getQty());

                body = params.toString();

                return body.getBytes("utf-8");
            }

        } catch (JSONException e){
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] setItemReduceParams(OrderDetails orderDetails,Double qty) {

        String clientId = AppSettings.getClientId(context);
        String companyId = AppSettings.getCompanyId(context);
        //DBHandler dbHandler = DBSingleton.getInstance(context);
        //User user = dbHandler.getUserAuth(AppSettings.getUserSession(context));

        String body = null;
        try{
            //Here are your parameters:

            //String date = ConversionClass.postServerDateFormat(item.getDate());

            //for(OrderDetails item : itemList) {

            JSONObject params = new JSONObject();


            params.put("client_id", clientId);
            params.put("company_id", companyId);
            params.put("item_number", orderDetails.getOrderDetailsItemNumber());
            params.put("bid", orderDetails.getOrderDetailsPatchId());
            params.put("location_id",AppSettings.getCompanyLocationId(context));
            params.put("qoh", -qty);

            //Log.e("CRCRCRCR","in "+item.getItemNumber()+" bid "+item.getBatchNo()+" qoh "+item.getQty());

            body = params.toString();

            return body.getBytes("utf-8");
            // }

        } catch (JSONException e){
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }


    private static Map<String, String> setHeaderData(){
        Map<String, String> headers = new HashMap<>();


        String clientId = AppSettings.getClientId(context);
        String companyId = AppSettings.getCompanyId(context);

        String userId = AppSettings.getUniqueId(context);
        String authId = AppSettings.getAuthId(context);


        headers.put("client_id",clientId);
        headers.put("company_id",companyId);
        headers.put("user_id",userId);
        headers.put("authorization",authId);

        return headers;
    }

}
