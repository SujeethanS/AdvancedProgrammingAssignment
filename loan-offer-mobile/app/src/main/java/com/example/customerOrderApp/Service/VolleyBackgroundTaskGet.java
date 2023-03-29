package com.example.customerOrderApp.Service;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.customerOrderApp.Singleton.VolleySingleton;
import com.example.customerOrderApp.helper.AppSettings;
import com.example.customerOrderApp.helper.ReadableDateFormat;
import com.example.customerOrderApp.helper.ShowMsg;
import com.example.customerOrderApp.pojo.Company;
import com.example.customerOrderApp.pojo.Item;
import com.example.customerOrderApp.pojo.Location;
import com.example.customerOrderApp.pojo.Order;
import com.example.customerOrderApp.pojo.OrderDetails;
import com.example.customerOrderApp.pojo.Payment;
import com.example.customerOrderApp.pojo.SalesContent;
import com.example.customerOrderApp.pojo.TopTen;
import com.example.customerOrderApp.pojo.TotalContent;
import com.example.customerOrderApp.pojo.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VolleyBackgroundTaskGet  {

    private  Context ctx;

    private static List<Item> itemList = new ArrayList<>();
    private static List<Item> itemInvList = new ArrayList<>();
    private static List<Item> itemInvListQuick = new ArrayList<>();
    private static List<Company> companyList = new ArrayList<>();
    private static List<String> categoryList = new ArrayList<>();
    private static List<String> subCategoryList = new ArrayList<>();
    private static List<User> userList = new ArrayList<>();
    private static List<Payment> creditPaymentList = new ArrayList<>();
    private static List<Order> salesReceiptAndInvoiceList = new ArrayList<>();
    private static List<TotalContent>totalAmount = new ArrayList<>();
    private static List<Item> reOrderList = new ArrayList<>();
    private static List<TopTen> topTenList = new ArrayList<>();
    private static List<SalesContent> salesList = new ArrayList<>();
    private static List<SalesContent> expenseList = new ArrayList<>();
    private static List<Location> locationsList = new ArrayList<>();

    private static Double totalDiscount = 0.0;
    private static Double totalCount = 0.0;

    //get all orderId from server
    public static void syncAllOrderIds(Context context, String url, final ResultOrderDelegate delegate, final String type) {

        /*
         * Server tab (Sign_up) only need to sync all order.
          This function will call by server tab.
          So, no nee to check COMPANY_ID.
          Before sync order need to check database by (order id and company id) unique
          Join tab will show only join tab's sales.
         *
         * tk */

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("okGood",response.toString());

                try {

                    JSONArray jsonArray = response.getJSONArray("salesreceipt");

                    List<Order> orderList = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject odObj = jsonArray.getJSONObject(i);

                        Order order = new Order();

                        if (!odObj.isNull("client_id") && odObj.getString("client_id").equalsIgnoreCase(AppSettings.getClientId(context))) {
                            //Log.e("COUNTCCC","also come "+object.getString("item_number"));

                            if (!odObj.isNull("order_id")) {
                                order.setOrderId(String.valueOf(odObj.getInt("order_id")));
                            }
                            if (!odObj.isNull("created")) {
                                order.setOrderDate(odObj.getString("created"));
                            }
                            if (!odObj.isNull("payment_method")) {
                                order.setOrderPaymentMethod(odObj.getInt("payment_method"));
                            }
                            if (!odObj.isNull("sub_total")) {
                                order.setOrderSubTotal(odObj.getDouble("sub_total"));
                            }
                            if (!odObj.isNull("discount_total")) {
                                order.setOrderDiscount(odObj.getDouble("discount_total"));
                            }
                            if (!odObj.isNull("total")) {
                                order.setOrderTotal(odObj.getDouble("total"));
                            }

                            orderList.add(order);
                            //syncAllOrderAndOrderDetails(context,"http://54.70.201.204:3000/api/acct/salesreceipt/"+order.getOrderId(),delegate);
                        }
                    }

                    delegate.sendOrderList(orderList,type);



                } catch (JSONException e){
                    e.printStackTrace();
                    Log.e("JSONERR",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR",error.toString());

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                }

            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData(context);
            }
        };

        // now volley retry policy is 20s
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    public static void syncAllOrderIdsFilter(Context context, String url, final ResultOrderDelegate delegate, final String type) {

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("okGood",response.toString());

                try {
                    List<Order> orderList = new ArrayList<>();

                        if(!response.isNull("salereceipts_invoices")) {
                            JSONObject object = response.getJSONObject("salereceipts_invoices");
                            //JSONArray jsonArray = response.getJSONArray("salesreceipt");
                            Order order = new Order();

                        //if (!object.isNull("client_id") && response.getString("client_id").equalsIgnoreCase(AppSettings.getClientId(context))) {
                        //Log.e("COUNTCCC","also come "+object.getString("item_number"));

                        if (!object.isNull("order_id")) {
                            order.setOrderId(String.valueOf(object.getInt("order_id")));
                        }
                        if (!object.isNull("created")) {
                            order.setOrderDate(object.getString("created"));
                        }
                        if (!object.isNull("payment_method")) {
                            order.setOrderPaymentMethod(object.getInt("payment_method"));
                        }
                        if (!object.isNull("sub_total")) {
                            order.setOrderSubTotal(object.getDouble("sub_total"));
                        }
                        if (!object.isNull("discount_total")) {
                            order.setOrderDiscount(object.getDouble("discount_total"));
                        }
                        if (!object.isNull("total")) {
                            order.setOrderTotal(object.getDouble("total"));
                        }
                        if (!object.isNull("order_type")) {
                            order.setOrderType(object.getString("order_type"));
                        }

                        orderList.add(order);
                        //syncAllOrderAndOrderDetails(context,"http://54.70.201.204:3000/api/acct/salesreceipt/"+order.getOrderId(),delegate);
                        // }

                        delegate.sendOrderList(orderList, type);

                    }else {
                        delegate.sendOrderList(orderList, type);
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                    Log.e("JSONERR",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR",error.toString());

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                    // Log.e("respomce_order_err","come error");
                }

            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData(context);
            }
        };

        // now volley retry policy is 20s
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    public static void syncAllOrderPaymentDetails(Context context, String url, final ResultOrderAndOrderDelegate delegate) {
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject obj) {
                try {

                    List<Order> orderList = new ArrayList<>();
                    List<OrderDetails> orderDetailsList = new ArrayList<>();

                    JSONArray jsonArray = obj.getJSONArray("value");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject odObj = jsonArray.getJSONObject(i);
                        OrderDetails od = new OrderDetails();
                        od.setOrderDetailsItemNumber(String.valueOf(odObj.getInt("historyId")));
                        od.setOrderDetailsItemName(odObj.getString("installmentPlan"));
                        od.setOrderDetailsItemPrice(odObj.getDouble("amountPaid"));
                        od.setExpiryDate(odObj.getString("createdDate"));

                        orderDetailsList.add(od);

                    }

                    orderList.add(null);
                    delegate.sendOrderListBack(orderDetailsList,orderList);

                } catch (JSONException e){
                    e.printStackTrace();
                    Log.e("JSONERR",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR",error.toString());

                ShowMsg.parseVolleyError(context,error);

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                    // Log.e("respomce_order_err","come error");
                }

            }

        }){
            @Override
            public byte[] getBody() {
                return setOrderPaymentParams();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData(context);
            }
        };

        // now volley retry policy is 20s
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    private static byte[] setOrderPaymentParams() {

        JSONObject parent = new JSONObject();
        String body = null;
        try {

            parent.put("orderId", 5);

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
    //get all orderId & details from server
    public static void syncAllOrderAndOrderDetails(Context context, String url, final ResultOrderAndOrderDelegate delegate) {
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject obj) {
                try {

                    List<Order> orderList = new ArrayList<>();
                    List<OrderDetails> orderDetailsList = new ArrayList<>();

                    JSONArray jsonArray = obj.getJSONArray("value");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject odObj = jsonArray.getJSONObject(i);
                            OrderDetails od = new OrderDetails();
                            od.setOrderDetailsItemNumber(String.valueOf(odObj.getInt("productId")));
                            od.setOrderDetailsItemName(odObj.getString("productName"));
                            od.setOrderDetailsItemPrice(odObj.getDouble("subTotal"));

                            orderDetailsList.add(od);

                        }

                        orderList.add(null);
                        delegate.sendOrderListBack(orderDetailsList,orderList);

                } catch (JSONException e){
                    e.printStackTrace();
                    Log.e("JSONERR",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR",error.toString());

                ShowMsg.parseVolleyError(context,error);

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                    // Log.e("respomce_order_err","come error");
                }

            }

        }){
            @Override
            public byte[] getBody() {
                return setOrderDetailsParams();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData(context);
            }
        };

        // now volley retry policy is 20s
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    private static byte[] setOrderDetailsParams() {

        JSONObject parent = new JSONObject();
        String body = null;
        try {

            parent.put("orderId", 3);

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

    //store order and details in db
    private static void storeOrderAndDetails(List<Order> orderList){

        for(Order o : orderList){
            Log.e("ORDER",""+o.getOrderId());

            if(true){
                //Toast.makeText(context, "already exit"+o.getCompanyId(), Toast.LENGTH_SHORT).show();
                o.setOrderSyncStatus(1);
                //dbHandler.addOrder(o,"order",o.getCashierId(),o.getId());

            }

            //o.getOrderDetailsList().get(0).getItemNumber();

            //Log.e("ORDER_DETAILS",""+o.getOrderDetailsList().get(0).getItemNumber());

        }

        // Log.e("ORDER","list size "+orderList.get(0).getOrderDetailsList().size());
        for(OrderDetails od : orderList.get(0).getOrderDetailsList()){
            //Log.e("ORDER_DETAILS",""+od.getItemNumber()+"order id "+od.getOrderId());

            //if (!object.isNull("order_id")) {
            //od.setOrderId(String.valueOf(object.getInt("order_id")));

                /*Log.e("DETAILS_CHECK","getOrderId "+od.getOrderId());
                Log.e("DETAILS_CHECK","getQty "+od.getQty());
                Log.e("DETAILS_CHECK","getSales_price "+od.getSales_price());
                Log.e("DETAILS_CHECK","getBatchNo "+od.getBatchNo());
                Log.e("DETAILS_CHECK","getItemNumber "+od.getItemNumber());
                Log.e("DETAILS_CHECK","getDiscount "+od.getDiscount());
                Log.e("DETAILS_CHECK","getItemCategory "+od.getItemCategory());
                Log.e("DETAILS_CHECK","getItemDes "+od.getItemDes());
                Log.e("DETAILS_CHECK","getCompanyId "+od.getCompanyId());
                Log.e("DETAILS_CHECK","getPurchasePrice "+od.getPurchasePrice());
                Log.e("DETAILS_CHECK","getRegularPrice "+od.getRegularPrice());
*/


            //}


            if(true){
                //Toast.makeText(context, "already not exit"+od.getCompanyId(), Toast.LENGTH_SHORT).show();

                //dbHandler.addOrderDetailsList(list,od,type);    // add order details

                //dbHandler.addWebOrderDetails(od);


            }
        }
    }

    //get all Items Summary from server for search fragment
    public static void syncAllItemsSummary (Context context,String url, final ResultItemSummaryDelegate delegate,final String type,
                                            String category, String brand) {
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, url, null, response -> {

        try {

            JSONArray jsonArray = response.getJSONArray("value");

            if (itemList.size() > 0) {
                itemList.clear();

            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                Item item = new Item();

                item.setItemNo(String.valueOf(object.getInt("productId")));
                String details = object.getString("productDetails");
                item.setItemName(details.split("/")[0]);
                item.setItemCategory(details.split("/")[1].split("~")[0].split("-")[0]);
                item.setItemSubCategory(details.split("/")[1].split("~")[0].split("-")[1]);
                String price = details.split("/")[1].split("~")[1];//Smart Watch/Watch-Samsung/(LKR 1200)
                item.setItemSellingPrice(Double.parseDouble(price.substring(4,price.length() - 1)));

                Log.e("itemLog",item.toString());

                itemList.add(item);
            }

            delegate.sendItemSummaryList(itemList,type);

        } catch (JSONException e){
            e.printStackTrace();
        }
    }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR",error.toString());
            }

        }){
            @Override
            public byte[] getBody() {
                return setUserLoginParams(category, brand);
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData(context);
            }
        };

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    private static byte[] setUserLoginParams(String category, String brand) {

        JSONObject parent = new JSONObject();
        String body = null;
        try {

            parent.put("category", category);
            parent.put("brand", brand);

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

    //get all inventry from server
    public static void syncAllInventory(Context context,String url, final ResultInventoryItemDelegate delegate,final String type) {
        //itemList.clear();
        long startTime = System.currentTimeMillis() % 1000;

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                long endTime= startTime + System.currentTimeMillis() % 1000 ;

                Log.e("endTime get",""+endTime);

                try {

                    String name;
                    if(response.has("inventories")){
                        name = "inventories";
                        Log.e("CHECK","inventories");
                    }else{
                        name = "item";
                        Log.e("CHECK","item");
                    }

                    JSONArray jsonArray = response.getJSONArray(name);
                    Log.e("SIse",""+jsonArray.length()+url);
                    if (itemInvList.size() > 0) {
                        itemInvList.clear();

                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        Item item = new Item();

                      //  if (!object.getString("type").equalsIgnoreCase("3")) {

                            if (!object.isNull("category")) {
                                item.setItemCategory(object.getString("category"));
                            }
                            if (!object.isNull("max_discount_type")) {
                                item.setItemMaxDiscountType(object.getInt("max_discount_type"));
                            }
                            if (!object.isNull("item_desc")) {
                                item.setItemName(object.getString("item_desc"));
                            }
                            if (!object.isNull("item_number")) {
                                item.setItemNo(object.getString("item_number"));
                            }
                            if (!object.isNull("default_discount")) {
                                item.setItemDefaultDiscount(object.getDouble("default_discount"));
                            }
                            if (!object.isNull("max_discount")) {
                                item.setItemMaxDiscount(object.getDouble("max_discount"));
                            }
                            if (!object.isNull("subcategory")) {
                                item.setItemSubCategory(object.getString("subcategory"));
                            }
                            if (!object.isNull("type")) {
                                item.setProductType(object.getString("type"));
                            }
                            if (!object.isNull("track_inventory")) {
                                item.setItemTrackItem(object.getString("track_inventory"));
                            }
                            if (!object.isNull("uom")) {
                                item.setItemUMO(object.getString("uom"));
                            }
                            if (!object.isNull("default_discount_type")) {
                                item.setItemDefaultDiscountType(object.getInt("default_discount_type"));
                            }
                            if (!object.isNull("selling_price")) {
                                item.setItemSellingPrice(object.getDouble("selling_price"));
                            }
                            if (!object.isNull("bid")) {
                                item.setBid(object.getInt("bid"));
                            }
                            if (!object.isNull("vat_code")) {
                                item.setItemVatCode(String.valueOf(object.getInt("vat_code")));
                            }
                            if (!object.isNull("bid_text")) {
                                item.setItemBidText(object.getString("bid_text"));
                            }
                            if (!object.isNull("qoh")) {
                                item.setItemQty(object.getDouble("qoh"));
                            }
                            if (!object.isNull("item_number")) {
                                item.setItemNo(object.getString("item_number"));
                            }
                            if (!object.isNull("bid_exp_date")) {
                                item.setItemExpiryDate(ReadableDateFormat.humanReadableFormat(object.getString("bid_exp_date")));
                            }
                            if (!object.isNull("location_id")) {
                                item.setItemLocationId(object.getInt("location_id"));
                            }
                            if (!object.isNull("last_purchase_price")) {
                                item.setItemPurchasePrice(object.getDouble("last_purchase_price"));
                            }
                            if (!object.isNull("track_inventory")) {
                                item.setItemTrackItem(object.getString("track_inventory"));
                            }
                            if (!object.isNull("supplier_id")) {
                                item.setSupplierId(object.getString("supplier_id"));
                            }
                            if (!object.isNull("reorder_qty")) {
                                item.setItemReorderQty(object.getDouble("reorder_qty"));
                            }
                            if (!object.isNull("bid")) {
                                item.setItemBatchNo(String.valueOf(object.getInt("bid")));
                            }
                            if (!object.isNull("allow_sales")) {
                                item.setAllowSales(object.getString("allow_sales"));
                            }
                            if (!object.isNull("type")) {
                                item.setProductType(object.getString("type"));
                            }

                            itemInvList.add(item);
                      //  }
                    }

                    delegate.sendItemList(itemInvList,type);
                    Log.e("testing","  "+itemInvList.size());
                            /*if(SetupDetailFragment.loadItems != null) {
                                SetupDetailFragment.loadItems.setVisibility(View.INVISIBLE);
                                SetupDetailFragment.importItemsButton.setEnabled(true);


                                SetupDetailFragment.importItemsArrayList.clear();
                                Item itemListHeader = new Item();
                                itemListHeader.setItemNumber("Title");
                                itemListHeader.setItemDesc("Title");
                                itemListHeader.setItemQty(0.0);
                                itemListHeader.setItemPrice(0.0);
                                if (itemList.size() > 0) {
                                    SetupDetailFragment.importItemsArrayList.add(0, itemListHeader);
                                    if (itemList != null) {
                                        SetupDetailFragment.importItemsArrayList.addAll(itemList);
                                    }
                                    SetupDetailFragment.importItemsListAdapter.notifyDataSetChanged();
                                }
                            }else {
                                Log.e("RESULTS","no items there");
                                //ToastMessageHelper.customErrToast(activity,"no items there");
                            }*/
                        //storeInvItemsDB(itemList);

                    //storeInvenItemsDB(itemInvList);

                } catch (JSONException e){
                    e.printStackTrace();
                    Log.e("JSONERR",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR",error.toString());

                ShowMsg.parseVolleyError(context,error);

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                }

            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData(context);
            }
        };

        // now volley retry policy is 20s
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    //get all inventry quick from server
    public static void syncAllInventoryQuick(Context context,String url, final ResultInventoryItemQuickDelegate delegate) {
        //itemList.clear();
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {

                    JSONArray jsonArray = response.getJSONArray("inventories");

                    if (itemInvListQuick.size() > 0) {
                        itemInvListQuick.clear();

                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        Item item = new Item();

                        Log.e("COUNTCCC", "come");

                            if (!object.isNull("category")) {
                                item.setItemCategory(object.getString("category"));
                            }
                            if (!object.isNull("max_discount_type")) {
                                item.setItemMaxDiscountType(object.getInt("max_discount_type"));
                            }
                            if (!object.isNull("item_desc")) {
                                item.setItemName(object.getString("item_desc"));
                            }
                            if (!object.isNull("item_number")) {
                                item.setItemNo(object.getString("item_number"));
                            }
                            if (!object.isNull("default_discount")) {
                                item.setItemDefaultDiscount(object.getDouble("default_discount"));
                            }
                            if (!object.isNull("max_discount")) {
                                item.setItemMaxDiscount(object.getDouble("max_discount"));
                            }
                            if (!object.isNull("subcategory")) {
                                item.setItemSubCategory(object.getString("subcategory"));
                            }
                            if (!object.isNull("type")) {
                                item.setProductType(object.getString("type"));
                            }
                            if (!object.isNull("track_inventory")) {
                                item.setItemTrackItem(object.getString("track_inventory"));
                            }
                            if (!object.isNull("uom")) {
                                item.setItemUMO(object.getString("uom"));
                            }
                            if (!object.isNull("default_discount_type")) {
                                item.setItemDefaultDiscountType(object.getInt("default_discount_type"));
                            }
                            if (!object.isNull("selling_price")) {
                                item.setItemSellingPrice(object.getDouble("selling_price"));
                            }
                            if (!object.isNull("bid")) {
                                item.setBid(object.getInt("bid"));
                            }
                            if (!object.isNull("vat_code")) {
                                item.setItemVatCode(String.valueOf(object.getInt("vat_code")));
                            }
                            if (!object.isNull("bid_text")) {
                                item.setItemBidText(object.getString("bid_text"));
                            }
                            if (!object.isNull("qoh")) {
                                item.setItemQty(object.getDouble("qoh"));
                            }
                            if (!object.isNull("item_number")) {
                                item.setItemNo(object.getString("item_number"));
                            }
                            if (!object.isNull("bid_exp_date")) {
                                item.setItemExpiryDate(ReadableDateFormat.humanReadableFormat(object.getString("bid_exp_date")));
                            }
                            if (!object.isNull("location_id")) {
                                item.setItemLocationId(object.getInt("location_id"));
                            }
                            if (!object.isNull("last_purchase_price")) {
                                item.setItemPurchasePrice(object.getDouble("last_purchase_price"));
                            }
                            if (!object.isNull("track_inventory")) {
                                item.setItemTrackItem(object.getString("track_inventory"));
                            }
                            if (!object.isNull("supplier_id")) {
                                item.setSupplierId(object.getString("supplier_id"));
                            }
                            if (!object.isNull("reorder_qty")) {
                                item.setItemReorderQty(object.getDouble("reorder_qty"));
                            }
                            if (!object.isNull("bid")) {
                                item.setItemBatchNo(String.valueOf(object.getInt("bid")));
                            }
                            if (!object.isNull("allow_sales")) {
                                item.setAllowSales(object.getString("allow_sales"));
                            }
                            if (!object.isNull("type")) {
                                item.setProductType(object.getString("type"));
                            }

                            itemInvListQuick.add(item);

                            /*if(SetupDetailFragment.loadItems != null) {
                                SetupDetailFragment.loadItems.setVisibility(View.INVISIBLE);
                                SetupDetailFragment.importItemsButton.setEnabled(true);


                                SetupDetailFragment.importItemsArrayList.clear();
                                Item itemListHeader = new Item();
                                itemListHeader.setItemNumber("Title");
                                itemListHeader.setItemDesc("Title");
                                itemListHeader.setItemQty(0.0);
                                itemListHeader.setItemPrice(0.0);
                                if (itemList.size() > 0) {
                                    SetupDetailFragment.importItemsArrayList.add(0, itemListHeader);
                                    if (itemList != null) {
                                        SetupDetailFragment.importItemsArrayList.addAll(itemList);
                                    }
                                    SetupDetailFragment.importItemsListAdapter.notifyDataSetChanged();
                                }
                            }else {
                                Log.e("RESULTS","no items there");
                                //ToastMessageHelper.customErrToast(activity,"no items there");
                            }*/
                            //storeInvItemsDB(itemList);
                    }
                    //storeInvenItemsDB(itemInvList);
                    delegate.sendItemListQuick(itemInvListQuick);


                } catch (JSONException e){
                    e.printStackTrace();
                    Log.e("JSONERR",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR",error.toString());

                ShowMsg.parseVolleyError(context,error);

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                }

            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData(context);
            }
        };

        // now volley retry policy is 20s
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }



    public static void syncAllItemCategory(Context context,String url, final ResultCategoryDelegate delegate) {

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("value");

                    if (categoryList.size() > 0) {
                        categoryList.clear();

                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        categoryList.add(object.getString("categoryName"));
                    }

                    delegate.sendCategoryList(categoryList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR",error.toString());

                ShowMsg.parseVolleyError(context,error);

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                }

            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData(context);
            }
        };

        // now volley retry policy is 20s
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }


    public static void syncAllItemSubCategory(Context context,String url, final ResultSubCategoryDelegate delegate) {

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = response.getJSONArray("value");

                    if (subCategoryList.size() > 0) {
                        subCategoryList.clear();

                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        subCategoryList.add(object.getString("brandName"));

                    }

                    delegate.sendSubCategoryList(subCategoryList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR",error.toString());

                ShowMsg.parseVolleyError(context,error);

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                }
            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData(context);
            }
        };

        // now volley retry policy is 20s
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }


    public static void syncReportTotalAmount(Context context,String url, final ResultTotalAmountDelegate delegate) {

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    Log.e("response_sub",response.toString() + url);

                    JSONArray jsonArray = response.getJSONArray("orders");

                        if(totalAmount.size() > 0){
                            totalAmount.clear();
                        }
                        double amount = 0.0;
                        TotalContent total = new TotalContent();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            if (!object.isNull("sales_total")) {
                                amount = amount + object.getDouble("sales_total");
                                Log.e("subcategori "," " + object.getDouble("sales_total"));
                            }
                        }
                        total.setSum(amount);
                        totalAmount.add(total);

                    delegate.sendAmountTotal(totalAmount);
                    Log.e("TotalAmountListsize "," " + totalAmount.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR",error.toString());

                ShowMsg.parseVolleyError(context,error);

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                }

            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData(context);
            }
        };

        // now volley retry policy is 20s
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }


    public static void syncReportTotalCount(Context context,String url, final ResultReportTotalCount delegate) {

        totalCount = 0.0;
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    Log.e("response_sub",response.toString());

                    JSONArray jsonArray = response.getJSONArray("orders");


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        if (!object.isNull("total_orders")) {
                            totalCount = (object.getDouble("total_orders"));
                        }

                    }

                    delegate.sendOrderCount(totalCount);
                    Log.e("subcategoriListsize "," " + totalCount);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR",error.toString());

                ShowMsg.parseVolleyError(context,error);

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                }

            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData(context);
            }
        };
        // now volley retry policy is 20s
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }


    public static void syncPaymentReportTotal(Context context,String url, final ResultPaymentReportTotal delegate) {

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("sujee"," total" + response.toString());
                try {
                    totalCount = 0.0;
                    Log.e("response_sub",response.toString());

                    JSONArray jsonArray = response.getJSONArray("payment");


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        if (!object.isNull("total_payment_received")) {
                            totalCount = (object.getDouble("total_payment_received"));
                        }

                    }

                    delegate.sendPaymentTotal(totalCount);
                    Log.e("sendPaymentTotal "," " + totalCount);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR",error.toString());

                ShowMsg.parseVolleyError(context,error);

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                }

            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData(context);
            }
        };
        // now volley retry policy is 20s
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    public static void syncAllItemCount(Context context,String url, final ResultReportTotalCount delegate) {

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = response.getJSONArray("items");


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        if (!object.isNull("count")) {
                            totalCount = (object.getDouble("count"));
                        }

                    }

                    delegate.sendOrderCount(totalCount);
                    Log.e("subcategoriListsize "," " + totalCount);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR",error.toString());

                ShowMsg.parseVolleyError(context,error);

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                }

            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData(context);
            }
        };
        // now volley retry policy is 20s
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }


    public static void syncAllUser(Context context,String url, final ResultSyncAllUser delegate) {

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    Log.e("response_sub",response.toString());

                    JSONArray jsonArray = response.getJSONArray("users");

                    if (userList.size() > 0) {
                        userList.clear();
                    }

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        User user = new User();
                        if (!object.isNull("phone_number") && !object.getString("phone_number").equalsIgnoreCase("+94718561834")) {
                            if (!object.isNull("user_id")) {
                                user.setUserID(object.getString("user_id"));
                            }
                            if (!object.isNull("name")) {
                                user.setUserName(object.getString("name"));
                            }
                            if (!object.isNull("address")) {
                                user.setUserAddress(object.getString("address"));
                            }
                            if (!object.isNull("password")) {
                                user.setUserPassward(object.getString("password"));
                            }
                            if (!object.isNull("phone_number")) {
                                user.setUserPNO(object.getString("phone_number"));
                            }
                            if (!object.isNull("designation")) {
                                user.setUserDesignation(object.getString("designation"));
                            }
                            if (!object.isNull("user_unique_id")) {
                                user.setUserUniqueId(object.getString("user_unique_id"));
                            }


                            userList.add(user);
                        }
                    }

                    delegate.sendUserList(userList);
                    Log.e("subcategoriListsize "," " + userList.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("subcategoriListsize "," " + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR",error.toString());

                ShowMsg.parseVolleyError(context,error);

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                }

            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData(context);
            }
        };

        // now volley retry policy is 20s
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    public static void syncAllCreditPayment(Context context,String url, final ResultCreditPaymentDelegate delegate,String type) {

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    /*Object json = new JSONTokener(response.toString()).nextValue();
                    if(json instanceof JSONObject){
                        Log.e("JSON","objact");
                    }else if(json instanceof  JSONArray){
                        Log.e("JSON","Array");
                    }*/

                    JSONArray jsonArray = response.getJSONArray("payment");

                    if (creditPaymentList.size() > 0) {
                        creditPaymentList.clear();
                    }

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        Payment payment = new Payment();

                        if (!object.isNull("description")) {
                            payment.setDescription(object.getString("description"));
                        }
                        if (!object.isNull("payment_method")) {
                            payment.setPayment_method(object.getInt("payment_method"));
                        }
                        if (!object.isNull("payment_discount")) {
                            payment.setPayment_discount(object.getDouble("payment_discount"));
                        }
                        if (!object.isNull("received_by")) {
                            payment.setReceived_by(object.getString("received_by"));
                        }
                        if (!object.isNull("invoice_number")) {
                            payment.setInvoice_number(object.getInt("invoice_number"));
                        }
                        if (!object.isNull("payment_date")) {
                            payment.setPaymentDate(object.getString("payment_date"));
                        }
                        if (!object.isNull("payment_type")) {
                            payment.setPayment_type(object.getInt("payment_type"));
                        }
                        if (!object.isNull("payment_status")) {
                            payment.setPayment_status(object.getString("payment_status"));
                        }
                        if (!object.isNull("payment_number")) {
                            payment.setPayment_number(object.getString("payment_number"));
                        }
                        if (!object.isNull("payment_total")) {
                            payment.setPayment_total(object.getDouble("payment_total"));
                        }
                        if (!object.isNull("payment_id")) {
                            payment.setPayment_id(object.getInt("payment_id"));
                        }
                        if (!object.isNull("customer_id")) {
                            payment.setCustomer_id(object.getString("customer_id"));
                        }
                        if (!object.isNull("cash_received")) {
                            payment.setReceivedAmount(object.getDouble("cash_received"));
                        }

                        creditPaymentList.add(payment);
                    }

                    delegate.sendPaymentDetails(creditPaymentList,type);
                    Log.e("creditPayment "," " + creditPaymentList.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("creditPayment "," " + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR",error.toString());

                ShowMsg.parseVolleyError(context,error);

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                }

            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData(context);
            }
        };

        // now volley retry policy is 20s
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    //for search payment
    public static void syncCreditPaymentFilter(Context context,String url, final ResultCreditPaymentSearchDelegate delegate) {

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    List<Payment> paymentList = new ArrayList<>();

                    if(!response.isNull("payment")) {
                        JSONObject object = response.getJSONObject("payment");

                        Payment payment = new Payment();

                        if (!object.isNull("description")) {
                            payment.setDescription(object.getString("description"));
                        }
                        if (!object.isNull("payment_method")) {
                            payment.setPayment_method(object.getInt("payment_method"));
                        }
                        if (!object.isNull("payment_discount")) {
                            payment.setPayment_discount(object.getDouble("payment_discount"));
                        }
                        if (!object.isNull("received_by")) {
                            payment.setReceived_by(object.getString("received_by"));
                        }
                        if (!object.isNull("invoice_number")) {
                            payment.setInvoice_number(object.getInt("invoice_number"));
                        }
                        if (!object.isNull("payment_date")) {
                            payment.setPaymentDate(object.getString("payment_date"));
                        }
                        if (!object.isNull("payment_type")) {
                            payment.setPayment_type(object.getInt("payment_type"));
                        }
                        if (!object.isNull("payment_status")) {
                            payment.setPayment_status(object.getString("payment_status"));
                        }
                        if (!object.isNull("payment_number")) {
                            payment.setPayment_number(object.getString("payment_number"));
                        }
                        if (!object.isNull("payment_total")) {
                            payment.setPayment_total(object.getDouble("payment_total"));
                        }
                        if (!object.isNull("payment_id")) {
                            payment.setPayment_id(object.getInt("payment_id"));
                        }
                        if (!object.isNull("customer_id")) {
                            payment.setCustomer_id(object.getString("customer_id"));
                        }

                        paymentList.add(payment);

                    delegate.sendPaymentSearchDetails(paymentList);
                    Log.e("creditPayment "," " + paymentList.size());

                    }else {
                        delegate.sendPaymentSearchDetails(paymentList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("creditPayment "," " + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR",error.toString());

                ShowMsg.parseVolleyError(context,error);

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                }

            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData(context);
            }
        };

        // now volley retry policy is 20s
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    public static void syncAllCreditInvoice(Context context, String url, final ResultInvoiceDelegate delegate, final String type) {

        List<Order> orderList = new ArrayList<>();

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("okGood",response.toString());

                try {

                    String name;
                    if(response.has("invoices")) {
                        name = "invoices";
                    }else {
                        name = "invoice";
                    }
                        JSONArray jsonArray = response.getJSONArray(name);

                        if(orderList.size() > 0){
                            orderList.clear();
                        }

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject odObj = jsonArray.getJSONObject(i);

                            Log.e("JSONArray","JSONArray"+jsonArray.length());

                            Order order = new Order();

                            // if (!odObj.isNull("client_id") && odObj.getString("client_id").equalsIgnoreCase(AppSettings.getClientId(context))) {


                            if (!odObj.isNull("sub_total")) {
                                order.setOrderSubTotal(odObj.getDouble("sub_total"));
                            }
                            if (!odObj.isNull("payment_due_date")) {
                                order.setDueDate(odObj.getString("payment_due_date"));
                            }
                            if (!odObj.isNull("date")) {
                                order.setOrderDate(ReadableDateFormat.humanReadableFormat(odObj.getString("date")));
                                Log.e("DATE","list "+ReadableDateFormat.humanReadableFormat(odObj.getString("date")));
                            }
                            if (!odObj.isNull("cashier_id")) {
                                order.setCashierId(Integer.parseInt(odObj.getString("cashier_id")));
                            }
                            if (!odObj.isNull("order_status")) {
                                order.setOrderStatusString(odObj.getString("order_status"));
                            }
                            if (!odObj.isNull("discount_total")) {
                                order.setOrderDiscount(odObj.getDouble("discount_total"));
                            }
                            if (!odObj.isNull("total")) {
                                order.setOrderTotal(odObj.getDouble("total"));
                            }
                            if (!odObj.isNull("order_id")) {
                                order.setOrderId(String.valueOf(odObj.getInt("order_id")));
                            }
                            if (!odObj.isNull("vat_total")) {
                                order.setOrderVatTotal(odObj.getDouble("vat_total"));
                            }
                            if (!odObj.isNull("service_charge")) {
                                order.setServiceCharge(odObj.getDouble("service_charge"));
                            }
                            if (!odObj.isNull("customer_id")) {
                                order.setOrderCustomerId(Integer.parseInt(odObj.getString("customer_id")));
                            }
                            if (!odObj.isNull("balance_due")) {
                                order.setBalanceDue(odObj.getDouble("balance_due"));
                            }
                            if (!odObj.isNull("balance_due")) {
                                order.setOrderPaymentReceived(odObj.getDouble("total_payment"));
                            }

                            orderList.add(order);
                            // }
                        }

                        delegate.sendInvoiceDetails(orderList,type);

                } catch (JSONException e){
                    e.printStackTrace();
                    Log.e("JSONERR",e.toString());
                    delegate.sendInvoiceDetails(orderList,type);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR",error.toString());

                ShowMsg.parseVolleyError(context,error);

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                }
            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData(context);
            }
        };

        // now volley retry policy is 20s
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }


    //search invoice
    public static void syncInvoiceFilter(Context context, String url, final ResultInvoiceSearchDelegate delegate) {


        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("okGood",response.toString());

                try {
                    List<Order> orderList = new ArrayList<>();

                    if(!response.isNull("invoice")) {
                    JSONObject odObj  = response.getJSONObject("invoice");
                    if(orderList.size() > 0){
                        orderList.clear();
                    }

                    Order order = new Order();

                    if (!odObj.isNull("sub_total")) {
                        order.setOrderSubTotal(odObj.getDouble("sub_total"));
                    }
                    if (!odObj.isNull("payment_due_date")) {
                        order.setDueDate(odObj.getString("payment_due_date"));
                    }
                    if (!odObj.isNull("date")) {
                        order.setOrderDate(ReadableDateFormat.humanReadableFormat(odObj.getString("date")));
                        Log.e("DATE","search "+ReadableDateFormat.humanReadableFormat(odObj.getString("date")));

                    }
                    if (!odObj.isNull("cashier_id")) {
                        order.setCashierId(Integer.parseInt(odObj.getString("cashier_id")));
                    }
                    if (!odObj.isNull("order_status")) {
                        order.setOrderStatusString(odObj.getString("order_status"));
                    }
                    if (!odObj.isNull("discount_total")) {
                        order.setOrderDiscount(odObj.getDouble("discount_total"));
                    }
                    if (!odObj.isNull("total")) {
                        order.setOrderTotal(odObj.getDouble("total"));
                    }
                    if (!odObj.isNull("order_id")) {
                        order.setOrderId(String.valueOf(odObj.getInt("order_id")));
                    }
                    if (!odObj.isNull("vat_total")) {
                        order.setOrderVatTotal(odObj.getDouble("vat_total"));
                    }
                    if (!odObj.isNull("service_charge")) {
                        order.setServiceCharge(odObj.getDouble("service_charge"));
                    }
                    if (!odObj.isNull("customer_id")) {
                        order.setOrderCustomerId(Integer.parseInt(odObj.getString("customer_id")));
                    }
                    if (!odObj.isNull("balance_due")) {
                        order.setBalanceDue(odObj.getDouble("balance_due"));
                    }
                    if (!odObj.isNull("balance_due")) {
                        order.setOrderPaymentReceived(odObj.getDouble("total_payment"));
                    }

                    orderList.add(order);

                    delegate.sendInvoiceSearchDetails(orderList);


                    }else {
                        delegate.sendInvoiceSearchDetails(orderList);
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                    Log.e("JSONERR",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR",error.toString());

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                }

            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData(context);
            }
        };

        // now volley retry policy is 20s
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    public static void syncAllReOrderItem(Context context,String url, final ResultReOrderItemsDelegate delegate,final String type) {

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {

                    JSONArray jsonArray = response.getJSONArray("items");

                    if (reOrderList.size() > 0) {
                        reOrderList.clear();

                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        Item item = new Item();

                        if (!object.getString("category").equalsIgnoreCase("Quick Sales")) {

                            if (!object.isNull("category")) {
                                item.setItemCategory(object.getString("category"));
                            }
                            if (!object.isNull("item_desc")) {
                                item.setItemName(object.getString("item_desc"));
                            }
                            if (!object.isNull("item_number")) {
                                item.setItemNo(object.getString("item_number"));
                            }
                            if (!object.isNull("subcategory")) {
                                item.setItemSubCategory(object.getString("subcategory"));
                            }
                            if (!object.isNull("bid")) {
                                item.setBid(object.getInt("bid"));
                            }
                            if (!object.isNull("qoh")) {
                                item.setItemQty(object.getDouble("qoh"));
                            }
                            if (!object.isNull("reorder_qty")) {
                                item.setItemReorderQty(object.getDouble("reorder_qty"));
                            }

                            reOrderList.add(item);

                        }

                    }
                    delegate.sendReOrderItems(reOrderList, type);
                } catch (JSONException e){
                    e.printStackTrace();
                    Log.e("JSONERR",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR",error.toString());

                ShowMsg.parseVolleyError(context,error);

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                }

            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData(context);
            }
        };

        // now volley retry policy is 20s
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    public static void syncTopTenSales(Context context, String url, final VolleyBackgroundTaskGet.ResultTopTenSalesDelegate delegate) {

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("items");
                    if(topTenList.size() > 0){
                        topTenList.clear();
                    }

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        TopTen toTen = new TopTen();

                        //if(object.getString("client_id").equalsIgnoreCase("121243212")) {
                        if (object.getString("item_desc") != null) {
                            toTen.setItemsName(object.getString("item_desc"));
                        }
                        if (!object.isNull("item_qty")) {
                            toTen.setQty(object.getDouble("item_qty"));
                        }
                        if (!object.isNull("total_sales")) {
                            toTen.setSalesTotal(object.getDouble("total_sales"));
                        }

                        topTenList.add(toTen);
                    }
                    delegate.sendTopTenSales(topTenList);

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ShowMsg.parseVolleyError(context,error);

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                }
            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData(context);
            }
        };
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    public static void syncBottomTenSales(Context context, String url, final VolleyBackgroundTaskGet.ResultBottomTenSalesDelegate delegate) {

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("items");
                    if(topTenList.size() > 0){
                        topTenList.clear();
                    }

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        TopTen toTen = new TopTen();

                        //if(object.getString("client_id").equalsIgnoreCase("121243212")) {
                        if (object.getString("item_desc") != null) {
                            toTen.setItemsName(object.getString("item_desc"));
                        }
                        if (!object.isNull("item_qty")) {
                            toTen.setQty(object.getDouble("item_qty"));
                        }
                        if (!object.isNull("total_sales")) {
                            toTen.setSalesTotal(object.getDouble("total_sales"));
                        }

                        topTenList.add(toTen);
                    }
                    delegate.sendBottomTenSales(topTenList);
                    /*if(progressBar != null){
                        progressBar.setVisibility(View.INVISIBLE);
                    }*/

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ShowMsg.parseVolleyError(context,error);

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                }
            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData(context);
            }
        };
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    public static void syncMonthWiseSales(Context context, String url, final VolleyBackgroundTaskGet.ResultMonthWiseSalesDelegate delegate) {

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("year_sales"," "+ response.toString() + url);

                    JSONArray jsonArray = response.getJSONArray("orders");
                    if(salesList.size() > 0){
                        salesList.clear();
                    }

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        SalesContent salesContent = new SalesContent();

                        //if(object.getString("client_id").equalsIgnoreCase("121243212")) {
                        if (!object.isNull("month")) {
                            salesContent.setMonth(object.getString("month").substring(5,7));
                        }
                        if (!object.isNull("sales_total")) {
                            salesContent.setTotal(object.getDouble("sales_total"));
                        }
                        salesList.add(salesContent);
                    }
                    delegate.sendMonthWiseSales(salesList);


                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ShowMsg.parseVolleyError(context,error);

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                }
            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData(context);
            }
        };
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    public static void syncMonthWiseExpense(Context context, String url, final VolleyBackgroundTaskGet.ResultMonthWiseExpenseDelegate delegate) {

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("expense");
                    if(expenseList.size() > 0){
                        expenseList.clear();
                    }

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        SalesContent salesContent = new SalesContent();

                        //if(object.getString("client_id").equalsIgnoreCase("121243212")) {
                        if (object.getString("month") != null) {
                            salesContent.setMonth(object.getString("month").substring(5,7));
                        }
                        if (!object.isNull("expense_total")) {
                            salesContent.setTotal(object.getDouble("expense_total"));
                        }
                        expenseList.add(salesContent);
                    }
                    delegate.sendMonthWiseExpense(expenseList);


                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ShowMsg.parseVolleyError(context,error);

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                }
            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData(context);
            }
        };
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }



    //GET ALL LOCATION
    public static void syncAllLocations(Context context, String url, final VolleyBackgroundTaskGet.ResultLocationDelegate delegate) {

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("inventory_locations");
                    if(locationsList.size() > 0){
                        locationsList.clear();
                    }

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        Location location = new Location();

                        if (!object.isNull("location_id")) {
                            location.setLocationId(Integer.parseInt(object.getString("location_id")));
                        }
                        if (!object.isNull("location_desc")){
                            location.setLocationName(object.getString("location_desc"));
                        }

                        locationsList.add(location);
                    }
                    delegate.sendLocationDetails(locationsList);


                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ShowMsg.parseVolleyError(context,error);

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                }
            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData(context);
            }
        };
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }


    public static void syncAllSalesreceiptAndInvoice(Context context,String url, final ResultSalesreceiptAndInvoiceDelegate delegate,String type) {

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("value");

                    if (salesReceiptAndInvoiceList.size() > 0) {
                        salesReceiptAndInvoiceList.clear();
                    }

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        Order order = new Order();

                        order.setOrderId(String.valueOf(object.getInt("orderId")));
                        order.setOrderTotal(object.getDouble("grandTotal"));
                        order.setOrderCustomerId(object.getInt("userId"));
                        order.setOrderDate(object.getString("createdDate"));
                        order.setOrderSyncStatus(object.getInt("installmentStatus"));

                        salesReceiptAndInvoiceList.add(order);
                    }

                    delegate.sendSalesReceiptAndInvoiceDetails(salesReceiptAndInvoiceList,type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ShowMsg.parseVolleyError(context,error);
                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                }
            }
        }){
            @Override
            public byte[] getBody() {
                return setOrderParams();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData(context);
            }
        };

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    private static byte[] setOrderParams() {

        JSONObject parent = new JSONObject();
        String body = null;
        try {

            parent.put("userId", 1);

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

    public static void syncPaymentDetailsByOrderId(Context context, String url,SyncOrderPaymentByOrderId rd) {
        Log.e("paymentDetails","url "+url);

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            Log.e("okGood",response.toString());

            try {

                JSONArray jsonArray = response.getJSONArray("payment");

                List<Payment> paymentList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject odObj = jsonArray.getJSONObject(i);

                    Payment payment = new Payment();

                    if (!odObj.isNull("payment_method")) {
                        payment.setPayment_method(odObj.getInt("payment_method"));
                    }
                    if (!odObj.isNull("payment_discount")) {
                        payment.setPayment_discount(odObj.getDouble("payment_discount"));
                    }
                    if (!odObj.isNull("received_by")) {
                        payment.setReceived_by(odObj.getString("received_by"));
                    }
                    if (!odObj.isNull("invoice_number")) {
                        payment.setInvoice_number(odObj.getInt("invoice_number"));
                    }
                    if (!odObj.isNull("payment_date")) {
                        payment.setPaymentDate(ReadableDateFormat.UTCToLocalTime(odObj.getString("payment_date")));
                    }

                    if (!odObj.isNull("payment_total")) {
                        payment.setPayment_total(odObj.getDouble("payment_total"));
                    }

                    if (!odObj.isNull("cash_received")) {
                        payment.setUserPayment(odObj.getDouble("cash_received"));
                    }

                    if (!odObj.isNull("cash_balance")) {
                        payment.setBalance(odObj.getDouble("cash_balance"));
                    }

                    /*if (!odObj.isNull("payment_id")) {
                    }*/


                    paymentList.add(payment);

                }


                rd.finishedOrderPaymentByOrderId(paymentList);


            } catch (JSONException e){
                e.printStackTrace();
                Log.e("JSONERR",e.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR",error.toString());

                ShowMsg.parseVolleyError(context,error);

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                }

            }

        }){
            @Override
            public Map<String, String> getHeaders() {
                return setHeaderData(context);
            }
        };

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    //set header
    private static Map<String, String> setHeaderData( Context context){
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


    public interface ResultOrderDelegate{
        void sendOrderList(List<Order> orderList, String type);
    }

    public interface ResultOrderAndOrderDelegate{
        void sendOrderListBack(List<OrderDetails> orderDetailsList, List<Order> orderList);
    }

    public interface ResultCompanyDelegate{
        void sendCompanyList(List<Company> companyList);
    }

    public interface ResultInventoryItemDelegate{
        void sendItemList(List<Item> list, String type);
    }

    public interface ResultItemSummaryDelegate{
        void sendItemSummaryList(List<Item> list, String type);
    }

    public interface ResultInventoryItemQuickDelegate{
        void sendItemListQuick(List<Item> list);
    }

    public interface ResultCategoryDelegate{
        void sendCategoryList(List<String> categoryList);
    }

    public interface ResultSubCategoryDelegate{
        void sendSubCategoryList(List<String> subCategoryList);
    }

    public interface ResultTotalAmountDelegate{
        void sendAmountTotal(List<TotalContent> total);
    }

    public interface ResultReportTotalCount{
        void sendOrderCount(Double count);
    }

    public interface ResultPaymentReportTotal{
        void sendPaymentTotal(Double total);
    }

    public interface ResultSyncAllUser{
        void sendUserList(List<User> userList);
    }

    public interface ResultInvoiceDelegate{
        void sendInvoiceDetails(List<Order> invoiceList, String type);
    }

    public interface ResultCreditPaymentDelegate{
        void sendPaymentDetails(List<Payment> invoiceList, String type);
    }

    public interface ResultCreditPaymentSearchDelegate{
        void sendPaymentSearchDetails(List<Payment> paymentList);
    }

public interface ResultInvoiceSearchDelegate{
    void sendInvoiceSearchDetails(List<Order> invoiceList);
}

    public interface ResultSalesreceiptAndInvoiceDelegate{
        void sendSalesReceiptAndInvoiceDetails(List<Order> salesReceiptAndInvoiceList, String type);
    }

    public interface ResultReOrderItemsDelegate{
        void sendReOrderItems(List<Item> list, String type);
    }

    public interface ResultTopTenSalesDelegate{
        void sendTopTenSales(List<TopTen> toTenList);
    }

    public interface ResultBottomTenSalesDelegate{
        void sendBottomTenSales(List<TopTen> toTenList);
    }

    public interface ResultMonthWiseSalesDelegate{
        void sendMonthWiseSales(List<SalesContent> sales);
    }

    public interface ResultMonthWiseExpenseDelegate{
        void sendMonthWiseExpense(List<SalesContent> sales);
    }


    public interface ResultLocationDelegate{
        void sendLocationDetails(List<Location> locationList);
    }

    public interface SyncOrderPaymentByOrderId{
        void finishedOrderPaymentByOrderId(List<Payment> list);
    }

}