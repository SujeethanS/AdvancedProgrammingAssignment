package com.example.customerOrderApp.Service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.customerOrderApp.AppInit;
import com.example.customerOrderApp.Json.ItemsJSONParser;
import com.example.customerOrderApp.Singleton.VolleySingleton;
import com.example.customerOrderApp.helper.AppSettings;
import com.example.customerOrderApp.helper.CustomDialog;
import com.example.customerOrderApp.helper.ReadableDateFormat;
import com.example.customerOrderApp.helper.ShowMsg;
import com.example.customerOrderApp.helper.ToastMessageHelper;
import com.example.customerOrderApp.pojo.Company;
import com.example.customerOrderApp.pojo.Item;
import com.example.customerOrderApp.pojo.Location;
import com.example.customerOrderApp.pojo.Order;
import com.example.customerOrderApp.pojo.OrderDetails;
import com.example.customerOrderApp.pojo.Payment;
import com.example.customerOrderApp.pojo.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VolleyBackgroundTaskPost {

    private static Context context;
    private static List<OrderDetails> temItemList = new ArrayList<>();
    private static int count = 0;

    private static SaleReceipt delegate;

    private static QuickAddItemDelegate quickAddItemDelegate;
    private static AddCustomerDelegate addCustomerDelegate;
    private static PostLocationDelegate postLocationDelegate;
    private static PostCompanyDelegate postCompanyDelegate;

    private static CustomerInitialPayment customerInitialPayment ;

    private static UserLoginDelegate loginUserDeligate ;
    private static UserSignUpDelegate userSignUpDelegate ;
    private static UserVerifyDelegate userVerifyDelegate ;

    private static RefreshCategoryList refreshCategoryList;
    private static RefreshItemsList refreshItemsList;

    private static  OrderCompleteResponse orderCompleteResponse;
    private static  SettlementCompleteResponse settlementCompleteResponse;

    private static  PostVatDetailsComplete postVatDetailsComplete;

    private static  ActivationCompleteResponse activationCompleteResponse;

    public interface ResultLocationStartDelegate{
        void sendLocationStartDetails( );
    }

    public interface SaleReceipt{
        void sendOrderAndOrderDetailsComplete(Order printOrder, List<OrderDetails> printDetails, int id, String responseName);
    }

    public interface QuickAddItemDelegate{
        void notifyAddItem();
    }

    public interface RefreshCategoryList{
        void refreshCategory(String category);
    }

    public interface RefreshItemsList{
        void refreshItems(boolean items);
    }

    public interface OrderCompleteResponse{
        void orderComplete(String completeOrder, String orderId);
    }

    public interface ActivationCompleteResponse{
        void ActivationComplete(String completeActivation);
    }

    public interface SettlementCompleteResponse{
        void settlementComplete(String completeSettlement, Payment payment);
        void settlementNotComplete(VolleyError error);
    }

    public interface AddCustomerDelegate{
        void notifyAddCustomer(int success);
    }

    public interface PostLocationDelegate{
        void notifyAddLocation(boolean success);
    }

    public interface PostCompanyDelegate{
        void notifyCompany(boolean success);
    }

    public interface CustomerInitialPayment{
        void notifyInitialPayment();
    }

    public interface UserLoginDelegate{
        void notifyUserLogin(boolean success);
    }

    public interface UserSignUpDelegate{
        void notifyUserSignUp(boolean success);
    }

    public interface UserVerifyDelegate{
        void notifyUserVerify(boolean success);
    }

    public interface ResultCreditPaymentDelegate{
        void sendPaymentDetails(List<Payment> invoiceList, String type);
    }

    public interface PostVatDetailsComplete{
        void vatPostComplete(boolean complete);
    }

    /* this method post items details *

     */
    //post items details on web service
    public static void postItem(Context ctx, String url , final Item item , RefreshCategoryList delegate ){
        refreshCategoryList = delegate;
        context = ctx;
        new CustomDialog().customProgressBar(ctx,"Sending data","Please wait","S");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                new CustomDialog().closeProgressBar();
                ToastMessageHelper.customSuccToast((Activity) ctx, item.getItemName()+" Added");


                try {
                    JSONArray jsonArray = response.getJSONArray("item");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);


                    refreshCategoryList.refreshCategory(jsonObject.getString("category"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("response_item",response.toString());
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new CustomDialog().closeProgressBar();
                ToastMessageHelper.customErrToast((Activity) ctx,item.getItemName()+" Can't Added");
                try {
                    if(error.networkResponse.data != null) {
                        String responseBody = new String(error.networkResponse.data, "utf-8");
                        Log.e("response_order_err", "body " + responseBody);
                    }

                } catch (UnsupportedEncodingException errorr) {
                    errorr.printStackTrace();
                }
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

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    //temp item details
    static void postTempItem(Context ctx, String url, final Item item, int size){
        context = ctx;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                count++;
                if(size == count){
                    ToastMessageHelper.customSuccToast((Activity) ctx,"Downloaded");
                    //CustomDialog.closeProgressBar();
                    CustomDialog.progress.dismiss();

                }
                Log.e("response_item",response.toString());
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new CustomDialog().closeProgressBar();
                ToastMessageHelper.customErrToast((Activity) ctx,"Can't Downloaded");
                try {
                    if(error.networkResponse.data != null) {
                        String responseBody = new String(error.networkResponse.data, "utf-8");
                        Log.e("response_order_err", "body " + responseBody);
                    }

                } catch (UnsupportedEncodingException errorr) {
                    errorr.printStackTrace();
                }
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

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    //add items to body volley
    private static byte[] setItemParams(Item item){

        String clientId = AppSettings.getClientId(context);
        String companyId = AppSettings.getCompanyId(context);

        Log.d("postServerDateFormat"," "+clientId + " " + companyId);
        JSONObject params = new JSONObject();
        String body = null;
        try{
            //Here are your parameters:


            Double qoh =item.getItemQty() + item.getItemFreeQty();
            Double qoh_count =item.getItemQty() + item.getItemFreeQty();
            //String date = ConversionClass.postServerDateFormat(item.getDate());

            params.put("client_id", clientId);//string
            Log.d("item_post","clientId "+clientId);
            params.put("company_id", companyId);//string
            Log.d("item_post","company_id "+companyId);
            params.put("item_number", item.getItemNo());//string
            Log.d("item_post","item_number "+item.getItemNo());
            params.put("temp_bid",item .getBid());//Integer
            Log.d("item_post","temp_bid "+item.getBid());
            params.put("bid", item.getBid());//Integer
            Log.d("item_post","bid "+item.getBid());
            params.put("track_inventory",item.getItemTrackItem());//string
            Log.d("item_post","track_inventory "+item.getItemTrackItem());
            params.put("item_desc", item.getItemName());//string
            Log.d("item_post","item_desc "+item.getItemName());
            params.put("category", item.getItemCategory());//string
            Log.d("item_post","category "+item.getItemCategory());
            params.put("subcategory", item.getItemSubCategory());//string
            Log.d("item_post","subcategory "+item.getItemSubCategory());
            params.put("uom", item.getItemUMO());//string
            Log.d("item_post","uom "+item.getItemUMO());
            //params.put("vat_code", item.getItemVatCode());//String
            Log.d("item_post","vat_code "+item.getItemVatCode());
            params.put("selling_price", item.getItemSellingPrice());//Double
            Log.d("item_post","selling_price "+item.getItemSellingPrice());
            params.put("max_discount", item.getItemMaxDiscount());//Double
            Log.d("item_post","max_discount "+item.getItemMaxDiscount());
            params.put("max_discount_type",item.getItemMaxDiscountType());//Integer
            Log.d("item_post","max_discount_type "+item.getItemMaxDiscountType());
            params.put("default_discount", item.getItemDefaultDiscount());//Double
            Log.d("item_post","default_discount "+item.getItemDefaultDiscount());
            params.put("default_discount_type",item.getItemDefaultDiscountType());//Integer
            Log.d("item_post","default_discount_type "+item.getItemDefaultDiscountType());
            params.put("type", String.valueOf(item.getProductType())); // this is product item  String
            Log.d("item_post","type "+item.getProductType());
            params.put("last_purchase_price", item.getItemPurchasePrice());//Double
            Log.d("item_post","last_purchase_price "+item.getItemPurchasePrice());
            params.put("bid_exp_date", item.getItemExpiryDate()); //String
            Log.d("item_post","bid_exp_date "+item.getItemExpiryDate());
            params.put("qoh", qoh);//Double
            Log.d("item_post","qoh "+ qoh);
            params.put("qoh_count", qoh_count);//Double
            Log.d("item_post","qoh_count "+qoh_count);
            params.put("reorder_qty", item.getItemReorderQty());//Double
            Log.d("item_post","reorder_qty "+item.getItemReorderQty());
            params.put("location_id",String.valueOf(item.getItemLocationId()));//Integer
            Log.d("item_post","location_id "+item.getItemLocationId());
            params.put("bid_text", item.getItemBatchNo());//String
            Log.d("item_post","bid_text "+  item.getItemBatchNo());
            params.put("bid_rcd_qty", item.getItemQty());//Double
            Log.d("item_post","bid_rcd_qty "+item.getItemQty());
            params.put("supplier_id", item.getSupplierId());//String
            Log.d("item_post","supplier_id "+item.getSupplierId());
            //params.put("ave_cost", item.getItemAveCoast());//String
            Log.d("item_post","ave_cost "+item.getSupplierId());

            params.put("vatcode1", item.getVatCode1());//String
            params.put("vatcode2", item.getVatCode2());//String
            params.put("vatcode3", item.getVatCode3());//String
            params.put("vatcode4", item.getVatCode4());//String

            params.put("allow_sales", "1");//String 1 for sales 0 for stock


            body = params.toString();
            Log.e("body_item",body);

        } catch (JSONException e){
            e.printStackTrace();
            Log.d("error_message",""+e);
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

    // add order details on web services

    public static void postOrderAndOrderDetails(Context ctx, String url , final Order order, final List<OrderDetails> orderDetailsList, SaleReceipt d, OrderCompleteResponse oc){
        delegate = d;
        context = ctx;
        orderCompleteResponse = oc;
        new CustomDialog().customProgressBar(ctx,"Saving Data","Please wait","H");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                orderCompleteResponse.orderComplete("1","1001");
            }
        }, error -> {
            try {
                orderCompleteResponse.orderComplete("0"," ");
                new CustomDialog().closeProgressBar();
                ShowMsg.parseVolleyError(ctx, error);
            }catch (Exception e){
                e.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                return setHeaderData();
            }
            @Override
            public byte[] getBody() {
                return setOrderAndOrderDetailsParams(order,orderDetailsList);
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);



    }

    //add order details to body volley
    private static byte[] setOrderAndOrderDetailsParams(Order orderData,List<OrderDetails> orderDetailsList){

        JSONObject order = new JSONObject();

        String body = null;
        try{

            order.put("paymentOption",1);
            order.put("userId",1);
            order.put("installmentStatus",1);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < orderDetailsList.size(); i++) {
                stringBuilder.append(orderDetailsList.get(i).getOrderDetailsItemNumber());
                stringBuilder.append("||");
                String qty = orderDetailsList.get(i).getOrderDetailsItemQty().toString().split("\\.")[0];
                stringBuilder.append(qty);
                if(i != orderDetailsList.size() -1) {
                    stringBuilder.append(",");
                }
            }
            order.put("orderDetails",stringBuilder.toString());

            body = order.toString();
            Log.e("ERRRRR",body);
        } catch (JSONException e){
            e.printStackTrace();
        }
        try{
            assert body != null;
            return body.getBytes(StandardCharsets.UTF_8);
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }

    //add inventory on web server
    public static void postInventoryItem(final Context ctx, String url , final Item item, RefreshItemsList delegate){
        context = ctx;
        refreshItemsList = delegate;

        ProgressDialog proDialog = ProgressDialog.show((Activity) ctx, "Sending data", "Please wait");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(response.toString().contains("{")){
                    proDialog.cancel();
                    Item i = ItemsJSONParser.sendItem(ctx,response.toString());
                    ToastMessageHelper.customSuccToast((Activity) ctx,"Updated");
                    refreshItemsList.refreshItems(true);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse.data != null) {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    Log.e("response_order_err", "body " + responseBody);
                }
                ToastMessageHelper.customSuccToast((Activity) ctx,"Not Updated");
                ShowMsg.parseVolleyError(ctx,error);
                refreshItemsList.refreshItems(false);

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData();
            }

            @Override
            public byte[] getBody() {
                return setInventoryParams(item);
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    //inventory web service  body
    private static byte[] setInventoryParams(Item item){

        String clientId = AppSettings.getClientId(context);
        String companyId = AppSettings.getCompanyId(context);
        JSONObject params = new JSONObject();
        String body = null;
        try{

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
            params.put("type",String.valueOf(item.getProductType())); // this is product item

            params.put("last_purchase_price", item.getItemPurchasePrice());
            params.put("bid_exp_date", item.getItemExpiryDate()); // this is convertion date
            params.put("qoh", item.getItemQty()+item.getItemFreeQty());
            params.put("qoh_count", item.getItemQty()+item.getItemFreeQty());
            params.put("reorder_qty", item.getItemReorderQty());
            params.put("location_id",String.valueOf(item.getItemLocationId()));
            params.put("bid_text", item.getItemBidText());
            params.put("bid_rcd_qty", item.getItemQty());
            params.put("supplier_id", item.getSupplierId());
            params.put("allow_sales", item.getAllowSales());


            body = params.toString();

            Log.e("SEND_INV",body);

        } catch (JSONException e){
            e.printStackTrace();
        }
        try{
            return body.getBytes(StandardCharsets.UTF_8);
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }

    //company post service
    public static void postCompanyDetails(Context ctx, String url , Company company, PostCompanyDelegate pc){
        context = ctx;
        postCompanyDelegate = pc;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("response_com",response.toString());
                postCompanyDelegate.notifyCompany(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                postCompanyDelegate.notifyCompany(false);
                if(error != null) {
                    if (error.networkResponse.data != null) {
                        String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        Log.e("response_order_err", "body " + responseBody);
                        ShowMsg.parseVolleyError(ctx, error);
                    }
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData();
            }

            @Override
            public byte[] getBody() {
                return postCompanyDetailsParams(company);
            }
        };

        // now volley retry policy is 20s
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(AppInit.appContext).addToRequestQueue(jsonObjectRequest);
    }

    //
    private static byte[] postCompanyDetailsParams(Company com){

        JSONObject company = new JSONObject();

        String body = null;
        try{
            //Here are your parameters:

            company.put("company_description", com.getCompanyDesc());
            company.put("email", com.getCompanyDesc());
            company.put("store_address", com.getCompanyStoreAddress());
            company.put("phone_number", com.getCompanyContactNumber());
            company.put("industry", com.getCompanyIndustry());
            company.put("city", com.getCompanyAddress());
            company.put("company_message", com.getCompanyMessage());
            company.put("company_name", com.getCompanyName());
            company.put("company_address", com.getCompanyAddress());
            company.put("company_id", com.getCloudComId());
            company.put("contact_name", com.getCompanyContactName());
            company.put("client_id", com.getClientId());
            company.put("company_no", com.getCompanyContactNumber());

            body = company.toString();

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


    /* POST Location */
    public static void postLocationDetails(Context ctx, String url , Location location, PostLocationDelegate pl){
        context = ctx;
        postLocationDelegate = pl;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("response_location",response.toString());
                postLocationDelegate.notifyAddLocation(true);
            }
        }, error -> {
            postLocationDelegate.notifyAddLocation(false);
            if(error != null) {
                try {
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    Log.e("response_location_err", "body " + responseBody);

                    ShowMsg.parseVolleyError(ctx, error);

                } catch (UnsupportedEncodingException errorr) {
                    errorr.printStackTrace();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                return setHeaderData();
            }

            @Override
            public byte[] getBody() {
                return postLocationDetailsParams(location);
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(AppInit.appContext).addToRequestQueue(jsonObjectRequest);
    }

    private static byte[] postLocationDetailsParams(Location location){
        DecimalFormat df= new DecimalFormat("0.00");

        String clientId = AppSettings.getClientId(context);
        String companyId = AppSettings.getCompanyId(context);

        JSONObject sub = new JSONObject();

        String body = null;
        try{
            sub.put("client_id",clientId);
            sub.put("company_id",companyId);
            sub.put("location_id",String.valueOf(location.getLocationId()));
            sub.put("location_desc",location.getLocationName());

            body = sub.toString();

            body = sub.toString();

            Log.e("ERRRRR",body);

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
    /* End POST Location */


    /* POST Credit payment details */
    public void postPaymentDetails(Context ctx, String url , Payment payment, SettlementCompleteResponse sc){
        context = ctx;
        settlementCompleteResponse = sc;
        DecimalFormat df= new DecimalFormat("0.00");
        Log.e("sendPayment"," "+ payment.getPayment_total());

        //new CustomDialog().customProgressBar(ctx,"Sending Payment","Please wait","H");


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("response_credit",response.toString());
                new CustomDialog().closeProgressBar();
                try {

                        JSONObject object = response.getJSONObject("payment");

                    Log.e("sendPayment"," "+ payment.getPayment_total()+" "+object.getDouble("payment_total"));


                    Payment payment1 = new Payment();
                        if (!object.isNull("payment_method")) {
                            payment1.setPayment_method(object.getInt("payment_method"));
                        }
                        if (!object.isNull("payment_discount")) {
                            payment1.setPayment_discount(object.getDouble("payment_discount"));
                        }
                        if (!object.isNull("received_by")) {
                            payment1.setReceived_by(object.getString("received_by"));
                        }
                        if (!object.isNull("invoice_number")) {
                            payment1.setInvoice_number(object.getInt("invoice_number"));
                        }
                        if (!object.isNull("payment_date")) {
                            payment1.setPaymentDate(ReadableDateFormat.humanReadableFormat(object.getString("payment_date")));
                        }

                        if (!object.isNull("payment_number")) {
                            payment1.setPayment_number(object.getString("payment_number"));
                        }
                        if (!object.isNull("payment_total")) {
                            payment1.setPayment_total(object.getDouble("payment_total"));
                        }
                        if (!object.isNull("payment_id")) {
                            payment1.setPayment_id(object.getInt("payment_id"));
                        }
                        if (!object.isNull("customer_id")) {
                            payment1.setCustomer_id(String.valueOf(object.getInt("customer_id")));
                        }
                        if (!object.isNull("customer_name")) {
                            payment1.setCustomer_name(object.getString("customer_name"));
                        }

                        settlementCompleteResponse.settlementComplete("1",payment1);
                        Log.e("receiptNo "," " + payment1.getPayment_number());

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("creditPayment "," " + e.toString());
                    settlementCompleteResponse.settlementComplete("0",null);
                }
            }
        }, error -> {
            settlementCompleteResponse.settlementComplete("0",null);
            settlementCompleteResponse.settlementNotComplete(error);
            new CustomDialog().closeProgressBar();

        }){
            @Override
            public Map<String, String> getHeaders() {
                return setHeaderData();
            }

            @Override
            public byte[] getBody() {
                return postPaymentParams(payment);
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private static byte[] postPaymentParams(Payment payment){
        DecimalFormat df= new DecimalFormat("0.00");

        String clientId = AppSettings.getClientId(context);
        String companyId = AppSettings.getCompanyId(context);
        String locationID = String.valueOf(AppSettings.getCompanyLocationId(context));

        JSONObject object = new JSONObject();

        String body = null;
        try{

            object.put("client_id",clientId);
            Log.e("s","client_id: "+clientId);

            object.put("company_id",companyId);
            Log.e("s","company_id: "+companyId);

            object.put("payment_type",String.valueOf(payment.getPayment_type()));
            Log.e("s","payment_type: "+payment.getPayment_type());

            object.put("payment_id",payment.getPayment_id());
            Log.e("s","payment_id: "+payment.getPayment_id());

            object.put("invoice_number",payment.getInvoice_number());
            Log.e("s","invoice_number: "+payment.getInvoice_number());

            object.put("received_by",payment.getReceived_by());
            Log.e("s","received_by: "+payment.getReceived_by());

            object.put("payment_method",String.valueOf(payment.getPayment_method()));
            Log.e("s","payment_method: "+payment.getPayment_method());

            object.put("payment_total",payment.getPayment_total());
            Log.e("s","payment_total: "+payment.getPayment_total());

            object.put("payment_discount",payment.getPayment_discount());
            Log.e("s","payment_discount: "+payment.getPayment_discount());

            object.put("payment_status",payment.getPayment_status());
            Log.e("s","payment_status: "+payment.getPayment_status());

            object.put("payment_number",payment.getPayment_number());
            Log.e("s","payment_number: "+payment.getPayment_number());

            object.put("customer_id",payment.getCustomer_id());
            Log.e("s","customer_id: "+payment.getCustomer_id());

            object.put("description",payment.getDescription());
            Log.e("s","description: "+payment.getDescription());

            object.put("location_id",locationID);
            Log.e("s","location_id: "+locationID);

            if(payment.getInvoice_number() == 0){
                object.put("opening_balance",payment.getPayment_opening_balance());
            }

            object.put("cash_received",payment.getUserPayment());
            Log.e("s","cash_received: "+payment.getUserPayment());

            object.put("cash_balance",payment.getBalance());
            Log.e("s","cash_balance: "+payment.getBalance());

            object.put("payment_date",ReadableDateFormat.getCurrentDateFormet(new Date(System.currentTimeMillis())));
            Log.e("s","payment_date: "+payment.getPaymentDate());

            body = object.toString();

            Log.e("ERRRRR",body);

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

    public static void postActivation (Context ctx, String url, ActivationCompleteResponse response){
        activationCompleteResponse = response;
        context = ctx;
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, url, null, response1 -> {
            ToastMessageHelper.customSuccToast((Activity) context,"Success");
            activationCompleteResponse.ActivationComplete("1");

            JSONArray jsonObject;
            try {
                jsonObject = response1;

                for(int i=0; i<jsonObject.length(); i++) {
                    JSONObject object = jsonObject.getJSONObject(i);
                    AppSettings.setAuthId(context,object.getString("auth_token"));
                    AppSettings.setUniqueId(context,object.getString("user_id"));
                    AppSettings.setCompanyId(context,object.getString("company_id"));
                    AppSettings.setClientId(context,object.getString("client_id"));
                    AppSettings.setRegisterCloud(context,1);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Activation",e.toString());

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("response_Error",error.toString());
                ToastMessageHelper.customErrToast((Activity) context,"Failed");
                activationCompleteResponse.ActivationComplete("0");
                ShowMsg.parseVolleyError(ctx,error);

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData();
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(AppInit.appContext).addToRequestQueue(jsonObjectRequest);
    }

    //==============================================================================================

    private static byte[] setUserParams(User user){

        String clientId = AppSettings.getClientId(context);
        String companyId = AppSettings.getCompanyId(context);

        JSONObject parent = new JSONObject();
        String body = null;
        try{

            parent.put("client_id",clientId);
            Log.e("DATA","client_id "+clientId);

            parent.put("company_id",companyId);
            Log.e("DATA","company_id "+companyId);

            parent.put("user_id",user.getUserID());
            Log.e("DATA","user_id "+user.getUserID());

            parent.put("user_unique_id",AppSettings.getUniqueId(context));
            Log.e("DATA","user_unique_id "+AppSettings.getUniqueId(context));

            parent.put("user_auth",AppSettings.getAuthId(context));
            Log.e("DATA","user_auth "+AppSettings.getAuthId(context));

            parent.put("user_name", user.getUserName());
            Log.e("DATA","user_name "+user.getUserName());

            parent.put("user_type",user.getUserDesignation());
            Log.e("DATA","user_type "+user.getUserDesignation());

            parent.put("designation",user.getUserDesignation());
            Log.e("DATA","designation "+user.getUserDesignation());

            parent.put("password",user.getUserPassward());
            Log.e("DATA","password "+user.getUserPassward());

            parent.put("name",user.getUserName());
            Log.e("DATA","name " +user.getUserName());

            parent.put("phone_number",user.getUserPNO());
            Log.e("DATA","phone_number "+user.getUserPNO());

            parent.put("email",user.getUserEmail());
            Log.e("DATA","email "+user.getUserEmail());

            parent.put("address", user.getUserAddress());
            Log.e("DATA","address "+user.getUserAddress());

            parent.put("status",user.getUserState());
            Log.e("DATA","status "+user.getUserState());

            body = parent.toString();
            Log.e("body_item",body);

        } catch (JSONException e){
            e.printStackTrace();
            Log.d("error_message",""+e);
        }
        try{
            assert body != null;
            return body.getBytes(StandardCharsets.UTF_8);
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }


    //set header volley
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

    private static void changeItem(List<OrderDetails> temItemList){
        Item item = new Item();
        for(OrderDetails orderDetails : temItemList){
            item.setItemNo(orderDetails.getOrderDetailsItemNumber());
            Log.e("tempSizeBBN",""+orderDetails.getOrderDetailsItemNumber());
            item.setItemName(orderDetails.getOrderDetailsItemName());
            Log.e("tempSize",""+orderDetails.getOrderDetailsItemName());
            item.setItemQty(orderDetails.getOrderDetailsItemQty());
            Log.e("tempSize",""+orderDetails.getOrderDetailsItemQty());
            item.setItemSellingPrice(orderDetails.getOrderDetailsItemSellingPrice());
            Log.e("tempSize",""+orderDetails.getOrderDetailsItemSellingPrice());
            item.setItemCategory(orderDetails.getOrderDetailsItemCategory());
            Log.e("tempSize",""+orderDetails.getOrderDetailsItemCategory());
            item.setItemSubCategory(orderDetails.getSubCategory());
            Log.e("tempSize",""+orderDetails.getSubCategory());
            item.setItemPurchasePrice(orderDetails.getOrderDetailsPurchasePrice());
            Log.e("tempSize",""+orderDetails.getOrderDetailsPurchasePrice());
            item.setItemExpiryDate(orderDetails.getExpiryDate());
            Log.e("tempSize",""+orderDetails.getExpiryDate());
            item.setItemDefaultDiscount(orderDetails.getOrderDetailsItemDiscount());
            Log.e("tempSize",""+orderDetails.getOrderDetailsItemDiscount());
            item.setItemDefaultDiscountType(orderDetails.getDefaultType());
            Log.e("tempSize",""+orderDetails.getDefaultType());
            item.setItemMaxDiscount(orderDetails.getOrderDetailsItemMaxDiscount());
            Log.e("tempSize",""+orderDetails.getOrderDetailsItemMaxDiscount());
            item.setItemMaxDiscountType(orderDetails.getMaxType());
            Log.e("tempSize",""+orderDetails.getMaxType());
            item.setItemReorderQty(orderDetails.getReOrder());
            Log.e("tempSize",""+orderDetails.getReOrder());
            item.setItemTrackItem(orderDetails.getTrackItem());
            Log.e("tempSize",""+orderDetails.getTrackItem());
            item.setItemUMO(orderDetails.getUom());
            Log.e("tempSize",""+orderDetails.getUom());
            item.setProductType("0");
            Log.e("tempSize",""+item.getProductType());
            item.setBid(orderDetails.getOrderDetailsPatchId());
            Log.e("tempSize",""+orderDetails.getOrderDetailsPatchId());
            item.setItemVatCode("1");
            item.setItemLocationId(1);

            String baseUrl = AppSettings.getURLs(context);
            String itemURL = baseUrl + "api/item/item";
            VolleyUpdateBackgroundTask.updateItem(context, itemURL, item, temItemList.size());
            String inventoryURL = baseUrl + "api/inv/item/inventory";
            VolleyBackgroundTaskPost.postInventoryItem(context, inventoryURL, item,refreshItemsList);

        }
    }



    //add order details to body volley
    private static byte[] setCreditSalesInvoiceParams(Order orderData, List<OrderDetails> orderDetailsList){
        DecimalFormat df= new DecimalFormat("0.00");

        String clientId = AppSettings.getClientId(context);
        String companyId = AppSettings.getCompanyId(context);
        String locationId = String.valueOf(AppSettings.getCompanyLocationId(context));

        JSONObject order = new JSONObject();
        JSONObject orderDetails;

        String body = null;
        try{

            order.accumulate("client_id", clientId);

            order.accumulate("company_id", companyId);

            order.accumulate("order_id", 0);

            order.accumulate("order_status", String.valueOf(orderData.getOrderStatus()));

            order.accumulate("sub_total", Double.parseDouble(df.format(orderData.getOrderSubTotal())));

            order.accumulate("discount_total", Double.parseDouble(df.format(orderData.getOrderDiscount())));

            order.accumulate("total", Double.parseDouble(df.format(orderData.getOrderTotal())));

            order.accumulate("customer_id", String.valueOf(orderData.getOrderCustomerId()));

            order.accumulate("cashier_id", String.valueOf(orderData.getCashierId()));

            order.accumulate("net_terms",30);

            order.accumulate("payment_due_date","n/l");

            order.accumulate("service_charge",Double.parseDouble(df.format(orderData.getOrderResCharge())));

            order.accumulate("location_id",locationId);

            int row_id = 1;

            temItemList.clear();
            for(OrderDetails od : orderDetailsList){

                orderDetails = new JSONObject();

                orderDetails.put("item_number", od.getOrderDetailsItemNumber());
                Log.d("order_post","item_number "+od.getOrderDetailsItemNumber());

                orderDetails.put("bid", od.getOrderDetailsPatchId());
                Log.d("order_post","bid "+od.getOrderDetailsPatchId());

                orderDetails.put("location_id",locationId);
                Log.d("order_post","location_id "+locationId);

                orderDetails.put("item_price", od.getOrderDetailsItemPrice());
                Log.d("order_post","item_price "+od.getOrderDetailsItemPrice());

                orderDetails.put("qty", od.getOrderDetailsItemQty());
                Log.d("order_post","qty "+od.getOrderDetailsItemQty());

                orderDetails.put("item_discount",od.getOrderDetailsItemDiscount());
                Log.d("order_post","item_discount "+od.getOrderDetailsItemDiscount());

                orderDetails.put("final_price",Double.parseDouble(df.format(od.getOrderDetailsItemPrice()))); //todo need check if value exit or not
                Log.d("order_post","final_price "+Double.parseDouble(df.format(od.getOrderDetailsItemPrice())));

                orderDetails.put("vat_type",AppSettings.getVatType(context));


                if(od.getOrderDetailsProductType().equalsIgnoreCase("4")){
                    temItemList.add(od);
                }

                order.accumulate("details",orderDetails);

                row_id++;

            }
            body = order.toString();
            Log.e("ERRRRR",body);

        } catch (JSONException e){
            Log.e("response_order_err_json",e.toString());
            e.printStackTrace();
        }
        try{
            assert body != null;
            return body.getBytes(StandardCharsets.UTF_8);
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }

    private static void setDataForOrderAndOrderDetails(JSONObject object,SaleReceipt delegate,int id, String responseName){
        String body = null;
        try {

            List<OrderDetails> orderDetailsList = new ArrayList<>();

            Order order = new Order();

                    if (!object.isNull("total")) {
                        order.setOrderTotal(object.getDouble("total"));
                    }
                    if (!object.isNull("order_id")) {
                        order.setOrderId(String.valueOf(object.getInt("order_id")));
                    }


                    JSONArray jsonArray = object.getJSONArray("details");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject odObj = jsonArray.getJSONObject(i);

                        OrderDetails od = new OrderDetails();

                        if (!odObj.isNull("order_id")) {
                            od.setOrderDetailsId(Integer.valueOf(String.valueOf(odObj.getInt("order_id"))));
                        }
                        if (!odObj.isNull("qty")) {
                            od.setOrderDetailsItemQty(odObj.getDouble("qty"));
                        }
                        if (!odObj.isNull("item_price")) {
                            od.setOrderDetailsItemPrice(odObj.getDouble("item_price"));
                            od.setOrderDetailsItemSellingPrice(odObj.getDouble("item_price"));
                        }
                        if (!odObj.isNull("bid")) {
                            od.setOrderDetailsPatchId(odObj.getInt("bid"));
                        }
                        if (!odObj.isNull("item_number")) {
                            od.setOrderDetailsItemNumber(odObj.getString("item_number"));
                        }
                        if (!odObj.isNull("item_discount")) {
                            od.setOrderDetailsItemDiscount(odObj.getDouble("item_discount"));
                        }
                        if (!odObj.isNull("category")) {
                            od.setOrderDetailsItemCategory(odObj.getString("category"));
                        }

                        if (!odObj.isNull("item_desc")) {
                            od.setOrderDetailsItemName(odObj.getString("item_desc"));
                        }
                        if (!odObj.isNull("company_id")) {
                            od.setOrderDetailsCompanyId((odObj.getString("company_id")));
                        }
                        if (!odObj.isNull("last_purchase_price")) {
                            od.setOrderDetailsPurchasePrice((odObj.getDouble("last_purchase_price")));
                        }

                        od.setOrderDetailsRegularPrice(od.getOrderDetailsItemPrice() + od.getOrderDetailsDiscount());

                        orderDetailsList.add(od);
                    }

                    delegate.sendOrderAndOrderDetailsComplete(order,orderDetailsList,id,responseName);
            body = orderDetailsList.toString();
            Log.e("ERRRRR",body);
        } catch (JSONException e){
            e.printStackTrace();
            Log.e("JSONERR",e.toString());
        }

    }



    //==============================================================================================
    //User Login
    //==============================================================================================
    public static void userLogin(Context ctx, String url, final String userName, String userPassword, UserLoginDelegate delegate) {
        context = ctx;
        loginUserDeligate = delegate;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, response -> {
            try {
                JSONObject object = response.getJSONObject("value");
                ToastMessageHelper.customErrToast((Activity) ctx, "Login success");
                /*AppSettings.setAuthId(context, object.getString("auth_token"));
                AppSettings.setUniqueId(context, object.getString("user_id"));
                AppSettings.setCompanyId(context, object.getString("company_id"));
                AppSettings.setClientId(context, object.getString("client_id"));

                ToastMessageHelper.customSuccToast((Activity) ctx, "Login Success");
                Log.e("response_item", response.toString());*/
                loginUserDeligate.notifyUserLogin(true);

            } catch (JSONException e) {
                e.printStackTrace();
                ToastMessageHelper.customErrToast((Activity) ctx, "Login Failed");
                loginUserDeligate.notifyUserLogin(false);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastMessageHelper.customErrToast((Activity) ctx, "Login Failed");
                loginUserDeligate.notifyUserLogin(false);

            }
        }) {
            @Override
            public byte[] getBody() {
                return setUserLoginParams(userName, userPassword);
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private static byte[] setUserLoginParams(String userName, String userPassword) {

        JSONObject parent = new JSONObject();
        String body = null;
        try {

            parent.put("username", userName);
            parent.put("password", userPassword);

            body = parent.toString();
            Log.e("body_item", body);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("error_message", "" + e);
        }
        try {
            assert body != null;
            return body.getBytes(StandardCharsets.UTF_8);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    //==============================================================================================
    //User SignUp
    //==============================================================================================
    public static void userSignUp(Context ctx, String url, final String userName, String userNumber, String userPassword, UserSignUpDelegate delegate) {
        context = ctx;
        userSignUpDelegate = delegate;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("response_item", response.toString() + url);
                userSignUpDelegate.notifyUserSignUp(true);
            }

        }, error -> {
            if(error != null) {
                ShowMsg.parseVolleyError(ctx, error);
            }
            userSignUpDelegate.notifyUserSignUp(false);
            assert error != null;
            Log.e("response_item_err", "body " + error.toString() + url);

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData();
            }

            @Override
            public byte[] getBody() {
                return setUserSignUpParams(userName, userNumber, userPassword);
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private static byte[] setUserSignUpParams(String userName, String userNumber, String userPassword) {

        JSONObject parent = new JSONObject();
        String body = null;
        try {

            parent.put("user_id", userName);
            parent.put("phone_number",   userNumber);
            parent.put("password", userPassword);
            parent.put("subscription","1");
            parent.put("user_name","Admin");
            parent.put("designation","admin");

            body = parent.toString();
            Log.e("body_item", body);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("error_message", "" + e);
        }
        try {
            assert body != null;
            return body.getBytes(StandardCharsets.UTF_8);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    //==============================================================================================
    //User Verify
    //==============================================================================================
    public static void userVerify(Context ctx, String url, final String userName, String userNumber, String userPassword, String userToken, UserVerifyDelegate delegate) {
        context = ctx;
        userVerifyDelegate = delegate;
        Log.e("response_item", url );
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, response -> {
            Log.e("response_item", response.toString());

            JSONArray jsonArray = null;
            try {
                jsonArray = response.getJSONArray("register_step2");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject odObj = jsonArray.getJSONObject(i);

                    if (!odObj.isNull("client_id")) {
                        String clientID = odObj.getString("client_id");
                        AppSettings.setClientId(context, clientID);
                        Log.e("VERIFY", "body " + clientID);
                    }
                    if (!odObj.isNull("company_id")) {
                        String companyID = odObj.getString("company_id");
                        AppSettings.setCompanyId(context, companyID);
                        Log.e("VERIFY", "body " + companyID);
                    }
                    if (!odObj.isNull("user_id")) {
                        String userID = odObj.getString("user_id");
                        AppSettings.setUniqueId(context, userID);
                        Log.e("VERIFY", "body " + userID);
                    }
                    if (!odObj.isNull("auth_token")) {
                        String authID = odObj.getString("auth_token");
                        AppSettings.setAuthId(context, authID);
                        Log.e("VERIFY", "body " + authID);
                    }
                    AppSettings.setRegisterCloud(context, 1);
                    userVerifyDelegate.notifyUserVerify(true);

                }
            } catch (JSONException e) {
                e.printStackTrace();
                userVerifyDelegate.notifyUserVerify(false);
            }
        }, error -> {
            if(error != null) {
                ShowMsg.parseVolleyError(ctx, error);
            }
            userVerifyDelegate.notifyUserVerify(false);
            Log.e("response_item_err", "body " + error.toString());

        }) {
            @Override
            public Map<String, String> getHeaders() {
                return setHeaderData();
            }

            @Override
            public byte[] getBody() {
                return setUserVerifyParams(userName, userNumber, userPassword, userToken);
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private static byte[] setUserVerifyParams(String userName, String userNumber, String userPassword, String token) {

        JSONObject parent = new JSONObject();
        String body = null;
        try {

            parent.put("user_id", userName);
            parent.put("phone_number",  userNumber);
            parent.put("password", userPassword);
            parent.put("token", token);
            body = parent.toString();


        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("error_message", "" + e);
        }
        try {
            assert body != null;
            return body.getBytes(StandardCharsets.UTF_8);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
