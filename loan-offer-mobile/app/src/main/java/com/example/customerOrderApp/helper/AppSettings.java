package com.example.customerOrderApp.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import java.util.ArrayList;
import java.util.Objects;

/**
  Created by dev1 on 7/27/17.
 */

public class AppSettings {

    private static String encrypt(String input) {

        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }

    private static String decrypt(String input) {
        return new String(Base64.decode(input, Base64.DEFAULT));

    }

    public static void setInput(Context context, String pNo) {
        // Write
        Log.e("pN",pNo);
        SharedPreferences preferences = context.getSharedPreferences("some_prefs_name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("phoneNo", encrypt(pNo));
        editor.apply(); // Or commit if targeting old devices

    }


    public static String getURLs(Context context){
        String DEFAULT = "https://prdc1.kalesystems.com:443/"; // new Production server name
        //String DEFAULT = "http://csdev.kaleapps.com:3000/"; //replacement of http://35.162.203.20:3000/ server

        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "SyncSettings", Context.MODE_PRIVATE);
        return sharedPreferences.getString("baseURL",DEFAULT);

    }

    public static void setURLs(Context context, String baseURL){

        SharedPreferences sharedPreference = context.getSharedPreferences("SyncSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();

        editor.putString("baseURL",baseURL);
        editor.apply();

    }


    public static void setCompanyId(Context context, String company_id) {
        // Write
        SharedPreferences preferences = context.getSharedPreferences("some_prefs_name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("company_id", company_id);
        editor.apply(); // Or commit if targeting old devices

    }

    public static String getCompanyId(Context context){
        String DEFAULT = "n/l";

        SharedPreferences sharedPreferences = context.getSharedPreferences("some_prefs_name", Context.MODE_PRIVATE);

        return sharedPreferences.getString("company_id",DEFAULT);

    }

    public static void setClientId(Context context, String client_id) {
        // Write
        SharedPreferences preferences = context.getSharedPreferences("some_prefs_name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("client_id", client_id);
        editor.apply(); // Or commit if targeting old devices
    }

    public static String getClientId(Context context) {
        String DEFAULT = "n/l";

        SharedPreferences preferences = context.getSharedPreferences("some_prefs_name",Context.MODE_PRIVATE);
        return preferences.getString("client_id", DEFAULT);

    }

    public static void setUniqueId(Context context, String state){

        SharedPreferences sharedPreferences = context.getSharedPreferences("Unique id", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("Unique",state);
        editor.apply();

    }

    public static String getUniqueId(Context context) {
        String DEFAULT = "n/l";

        SharedPreferences sharedPreferences = context.getSharedPreferences("Unique id", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Unique", DEFAULT);

    }

    public static void setAuthId(Context context, String state){
        SharedPreferences sharedPrefence = context.getSharedPreferences("Auth id", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefence.edit();

        editor.putString("Auth",state);
        editor.apply();

    }

    public static String getAuthId(Context context) {
        String DEFAULT = "n/l";
        SharedPreferences sharedPreferences = context.getSharedPreferences("Auth id", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Auth", DEFAULT);

    }


    public static int getRegisterCloud(Context context){
        int DEFAULT = 0;
        SharedPreferences sharedPreferences = context.getSharedPreferences("cloud setting", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("isRegistered",DEFAULT);

    }

    public static void setRegisterCloud(Context context, int cloud){
        SharedPreferences sharedPrefence = context.getSharedPreferences("cloud setting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefence.edit();
        editor.putInt("isRegistered",cloud);
        editor.apply();

    }

    public static void setActivateCloud(Context context, int cloud){
        SharedPreferences sharedPrefence = context.getSharedPreferences("cloud setting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefence.edit();
        editor.putInt("isActivated",cloud);
        editor.apply();

    }

    public static void setCompanyLocationId(Context context, Integer locationId){
        SharedPreferences sharedPreferences = context.getSharedPreferences("company setup", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("companyLocationId",locationId);
        editor.apply();
    }

    public static int getCompanyLocationId(Context context) {
        int DEFAULT = 1;
        SharedPreferences sharedPreferences = context.getSharedPreferences("company setup", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("companyLocationId", DEFAULT);

    }

    public static String getUserId(Context context) {
        String DEFAULT ="0";
        SharedPreferences preferences = context.getSharedPreferences("some_prefs_name", Context.MODE_PRIVATE);
        return preferences.getString("user_id", encrypt("default"));
    }


    public static void setBluetoothPrinterMac(Context context, String state){
        SharedPreferences sharedPrefence = context.getSharedPreferences("Printer settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefence.edit();
        editor.putString("MAC",state);
        editor.apply();

    }

    public static String getBluetoothPrinterMac(Context context) {
        String DEFAULT = "N/A";
        SharedPreferences sharedPreferences = context.getSharedPreferences("Printer settings", Context.MODE_PRIVATE);
        return sharedPreferences.getString("MAC", DEFAULT);
    }

    public static void setPrinterPaperSize(Context context, int size){
        SharedPreferences sharedPrefence = context.getSharedPreferences("Printer settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefence.edit();
        editor.putInt("SIZE",size);
        editor.apply();

    }

    public static int getPrinterPaperSize(Context context) {
        int DEFAULT = 0;
        SharedPreferences sharedPreferences = context.getSharedPreferences("Printer settings", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("SIZE", DEFAULT);
    }

    public static void setPrinterLibrary(Context context, int lib){
        SharedPreferences sharedPrefence = context.getSharedPreferences("Printer settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefence.edit();
        editor.putInt("LIBRARY",lib);
        editor.apply();

    }

    public static int getPrinterLibrary(Context context) {
        int DEFAULT = 1;
        SharedPreferences sharedPreferences = context.getSharedPreferences("Printer settings", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("LIBRARY", DEFAULT);

    }


    @SuppressLint("ApplySharedPref")
    public static void setDisType(Context context, String state){
        SharedPreferences sharedPreferences = context.getSharedPreferences("order discount id", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("discount",state);
        editor.apply();

    }

    public static String getDisType(Context context) {
        String DEFAULT = "N";

        SharedPreferences sharedPreferences = context.getSharedPreferences("order discount id", Context.MODE_PRIVATE);
        return sharedPreferences.getString("discount", DEFAULT);

    }

    public static void setEditPriceAllow(Context context, String state){

        SharedPreferences sharedPreferences = context.getSharedPreferences("allow edit price", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("editPrice",state);
        editor.apply();

    }

    public static String getEditPriceAllow(Context context) {
        String DEFAULT = "Y";

        SharedPreferences sharedPreferences = context.getSharedPreferences("allow edit price", Context.MODE_PRIVATE);
        return sharedPreferences.getString("editPrice", DEFAULT);

    }


    public static String getLastOrderId(Context context){
        String DEFAULT = "1001";
        SharedPreferences sharedPreferences = context.getSharedPreferences("response jason", Context.MODE_PRIVATE);
        return sharedPreferences.getString("lastId",DEFAULT);

    }

    public static void setLastOrderId(Context context, String orderId){
        Log.d("setLastOrderId",orderId);
        SharedPreferences sharedPrefence = context.getSharedPreferences("response jason", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefence.edit();

        editor.putString("lastId",orderId);
        editor.apply();

    }


    public static void setCompanyName(Context context, String name){

        SharedPreferences sharedPreferences = context.getSharedPreferences("company setup", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("companyName",name);
        editor.apply();

    }

    public static String getCompanyName(Context context) {
        String DEFAULT = "Company Name";

        SharedPreferences sharedPreferences = context.getSharedPreferences("company setup", Context.MODE_PRIVATE);
        return sharedPreferences.getString("companyName", DEFAULT);

    }

    public static void setCompanyDes(Context context, String des){

        SharedPreferences sharedPreferences = context.getSharedPreferences("company setup", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("companyDes",des);
        editor.apply();

    }

    public static void setCompanyAddress(Context context, String address){

        SharedPreferences sharedPreferences = context.getSharedPreferences("company setup", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("companyAddress",address);
        editor.apply();

    }

    public static String getCompanyAddress(Context context) {
        String DEFAULT = "Company Address";

        SharedPreferences sharedPreferences = context.getSharedPreferences("company setup", Context.MODE_PRIVATE);
        return sharedPreferences.getString("companyAddress", DEFAULT);

    }


    public static void setCompanyStoreAddress(Context context, String storeAddress){

        SharedPreferences sharedPreferences = context.getSharedPreferences("company setup", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("companyStoreAddress",storeAddress);
        editor.apply();

    }

    public static String getCompanyStoreAddress(Context context) {
        String DEFAULT = "Store Address";

        SharedPreferences sharedPreferences = context.getSharedPreferences("company setup", Context.MODE_PRIVATE);
        return sharedPreferences.getString("companyStoreAddress", DEFAULT);

    }

    public static void setCompanyContactName(Context context, String contactName){

        SharedPreferences sharedPreferences = context.getSharedPreferences("company setup", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("companyContactName",contactName);
        editor.apply();

    }

    public static String getCompanyContactName(Context context) {
        String DEFAULT = "Company Contact Name";

        SharedPreferences sharedPreferences = context.getSharedPreferences("company setup", Context.MODE_PRIVATE);
        return  sharedPreferences.getString("companyContactName", DEFAULT);

    }

    public static void setCompanyContactNumber(Context context, String contactNumber){

        SharedPreferences sharedPreferences = context.getSharedPreferences("company setup", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("companyContactNumber",contactNumber);
        editor.apply();

    }

    public static String getCompanyContactNumber(Context context) {
        String DEFAULT = "Company Number";

        SharedPreferences sharedPreferences = context.getSharedPreferences("company setup", Context.MODE_PRIVATE);
        return sharedPreferences.getString("companyContactNumber", DEFAULT);

    }

    public static void setCompanyOrderId(Context context, Integer orderId){

        SharedPreferences sharedPreferences = context.getSharedPreferences("company setup", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("companyOrderId",orderId);
        editor.apply();

    }

    public static int getCompanyOrderId(Context context) {
        int DEFAULT = 1;

        SharedPreferences sharedPreferences = context.getSharedPreferences("company setup", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("companyOrderId", DEFAULT);

    }


    public static void setPrinterConnectionBluetooth(Context context, Integer connect){

        SharedPreferences sharedPreferences = context.getSharedPreferences("printer setup", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("printerConnected",connect);
        editor.apply();

    }

    public static int getPrinterConnectionBluetooth(Context context) {
        int DEFAULT = 0;

        SharedPreferences sharedPreferences = context.getSharedPreferences("printer setup", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("printerConnected", DEFAULT);

    }

    public static void setCompanyMessage(Context context, String message){

        SharedPreferences sharedPreferences = context.getSharedPreferences("company setup", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("companyMessage",message);
        editor.apply();

    }

    public static String getCompanyMessage(Context context) {
        String DEFAULT = "Thank You Come Again";

        SharedPreferences sharedPreferences = context.getSharedPreferences("company setup", Context.MODE_PRIVATE);
        return sharedPreferences.getString("companyMessage", DEFAULT);

    }

    public static void setCompanyIndustryType(Context context, String message){

        SharedPreferences sharedPreferences = context.getSharedPreferences("company setup", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("companyIndustry",message);
        editor.apply();

    }

    public static String getCompanyIndustryType(Context context) {
        String DEFAULT = "MultiShop";

        SharedPreferences sharedPreferences = context.getSharedPreferences("company setup", Context.MODE_PRIVATE);
        return sharedPreferences.getString("companyIndustry", DEFAULT);

    }

    public static void setPrinterUser(Context context, String message){

        SharedPreferences sharedPreferences = context.getSharedPreferences("printer setup", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("loginUser",message);
        editor.apply();

    }

    public static String getPrinterUser(Context context) {
        String DEFAULT = "Admin";

        SharedPreferences sharedPreferences = context.getSharedPreferences("printer setup", Context.MODE_PRIVATE);
        return sharedPreferences.getString("loginUser", DEFAULT);

    }
//auto generate
    public static void setLastItemNumber(Context context, int itemNumber){

        SharedPreferences sharedPreferences = context.getSharedPreferences("item number setup", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("itemNumber",itemNumber);
        editor.apply();

    }

    public static int getLastItemNumber(Context context) {
        int DEFAULT = 10000;

        SharedPreferences sharedPreferences = context.getSharedPreferences("item number setup", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("itemNumber", DEFAULT);

    }

    public static void setAllowAutoGenerate(Context context, String state){

        SharedPreferences sharedPreferences = context.getSharedPreferences("allow auto number", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("autoGenerate",state);
        editor.apply();

    }

    public static String getAllowAutoGenerate(Context context) {
        String DEFAULT = "Y";

        SharedPreferences sharedPreferences = context.getSharedPreferences("allow auto number", Context.MODE_PRIVATE);
        return sharedPreferences.getString("autoGenerate", DEFAULT);

    }

    public static void setAllowTax(Context context, String state){

        SharedPreferences sharedPreferences = context.getSharedPreferences("allow tax", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("tax",state);
        editor.apply();

    }

    public static String getAllowtax(Context context) {
        String DEFAULT = "N";

        SharedPreferences sharedPreferences = context.getSharedPreferences("allow tax", Context.MODE_PRIVATE);
        return sharedPreferences.getString("tax", DEFAULT);

    }

    public static void setCountryCode(Context context, String code){

        SharedPreferences sharedPreferences = context.getSharedPreferences("allow country code", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("countryCode",code);
        editor.apply();

    }

    public static String getCountryCode(Context context) {
        String DEFAULT = "Srilanka";

        SharedPreferences sharedPreferences = context.getSharedPreferences("allow country code", Context.MODE_PRIVATE);
        return sharedPreferences.getString("countryCode", DEFAULT);

    }

    public static void setLanguageCode(Context context, String code){

        SharedPreferences sharedPreferences = context.getSharedPreferences("select language code", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("languageCode",code);
        editor.apply();

    }

    public static String getLanguageCode(Context context) {
        String DEFAULT = "English";

        SharedPreferences sharedPreferences = context.getSharedPreferences("select language code", Context.MODE_PRIVATE);
        return sharedPreferences.getString("languageCode", DEFAULT);

    }

    public static void setRecepitType(Context context, String code){

        SharedPreferences sharedPreferences = context.getSharedPreferences("allow receipt type", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("receiptType",code);
        editor.apply();

    }

    public static String getRecepitType(Context context) {
        String DEFAULT = "SendSMS";

        SharedPreferences sharedPreferences = context.getSharedPreferences("allow receipt type", Context.MODE_PRIVATE);
        return sharedPreferences.getString("receiptType", DEFAULT);

    }

    /*public static void setVatType(Context context, int code){

        SharedPreferences sharedPreferences = context.getSharedPreferences("allow vat type", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("vatType",code);
        editor.apply();

    }

    public static int getVatType(Context context) {
        int DEFAULT = 0;

        SharedPreferences sharedPreferences = context.getSharedPreferences("allow vat type", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("vatType", DEFAULT);

    }*/


    public static void setVatType(Context context, int code){

        SharedPreferences sharedPreferences = context.getSharedPreferences("select vat type", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("vatType",code);
        editor.apply();

    }

    public static int getVatType(Context context) {
        int DEFAULT = 0;

        SharedPreferences sharedPreferences = context.getSharedPreferences("select vat type", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("vatType", DEFAULT);

    }


    public static void setReorderAlertQty(Context context, int code){

        SharedPreferences sharedPreferences = context.getSharedPreferences("select reorder alert qty", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("reorderAlert",code);
        editor.apply();

    }

    public static int getReorderAlertQty(Context context) {
        int DEFAULT = 0;

        SharedPreferences sharedPreferences = context.getSharedPreferences("select reorder alert qty", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("reorderAlert", DEFAULT);

    }


    public static void setExpiryDateAlert(Context context, String code){

        SharedPreferences sharedPreferences = context.getSharedPreferences("select Expiry date code", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("expirydatecode",code);
        editor.apply();

    }

    public static String getExpiryDateAlert(Context context) {
        String DEFAULT = "Today";

        SharedPreferences sharedPreferences = context.getSharedPreferences("select Expiry date code", Context.MODE_PRIVATE);
        return sharedPreferences.getString("expirydatecode", DEFAULT);

    }


    public static void setAllowAutoCut(Context context, String state){

        SharedPreferences sharedPreferences = context.getSharedPreferences("allow auto cut", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("autoCut",state);
        editor.apply();

    }

    public static String getAllowAutoCut(Context context) {
        String DEFAULT = "N";

        SharedPreferences sharedPreferences = context.getSharedPreferences("allow auto cut", Context.MODE_PRIVATE);
        return sharedPreferences.getString("autoCut", DEFAULT);

    }

    public static void setAllowEditItemDis(Context context, String state){

        SharedPreferences sharedPreferences = context.getSharedPreferences("allow edit item discount", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("editItemDiscount",state);
        editor.apply();

    }

    public static String getAllowEditItemDis(Context context) {
        String DEFAULT = "N";

        SharedPreferences sharedPreferences = context.getSharedPreferences("allow edit item discount", Context.MODE_PRIVATE);
        return sharedPreferences.getString("editItemDiscount", DEFAULT);

    }

    public static void setAllowSendSms(Context context, String state){

        SharedPreferences sharedPreferences = context.getSharedPreferences("allow send sms", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("sendSms",state);
        editor.apply();

    }

    public static String getAllowSendSms(Context context) {
        String DEFAULT = "N";

        SharedPreferences sharedPreferences = context.getSharedPreferences("allow send sms", Context.MODE_PRIVATE);
        return sharedPreferences.getString("sendSms", DEFAULT);

    }

    public static void setClearList(Context context, String state){

        SharedPreferences sharedPreferences = context.getSharedPreferences("clear list", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("clearList",state);
        editor.apply();

    }

    public static String getClearList(Context context) {
        String DEFAULT = "N";

        SharedPreferences sharedPreferences = context.getSharedPreferences("clear list", Context.MODE_PRIVATE);
        return sharedPreferences.getString("clearList", DEFAULT);

    }

    public static void setAllowShareImage(Context context, String state){

        SharedPreferences sharedPreferences = context.getSharedPreferences("allow share image", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("shareImage",state);
        editor.apply();

    }

    public static String getAllowShareImage(Context context) {
        String DEFAULT = "N";

        SharedPreferences sharedPreferences = context.getSharedPreferences("allow share image", Context.MODE_PRIVATE);
        return sharedPreferences.getString("shareImage", DEFAULT);

    }

    public static void setActivityLive(Context context, boolean state){

        SharedPreferences sharedPreferences = context.getSharedPreferences("activity live", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("ActivityLive",state);
        editor.apply();

    }
    public static boolean getActivityLive(Context context) {
        boolean DEFAULT = false;

        SharedPreferences sharedPreferences = context.getSharedPreferences("activity live", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("ActivityLive", DEFAULT);

    }

    public static void setCatalogCall(Context context, boolean state){

        SharedPreferences sharedPreferences = context.getSharedPreferences("catalog item", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("catalogItem",state);
        editor.apply();

    }

    public static boolean getCatalogCall(Context context) {
        boolean DEFAULT = false;

        SharedPreferences sharedPreferences = context.getSharedPreferences("catalog item", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("catalogItem", DEFAULT);

    }

    public static void setCompanyMailID(Context context, String code){

        SharedPreferences sharedPreferences = context.getSharedPreferences("mail address", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("mailID",code);
        editor.apply();

    }

    public static String getCompanyMailID(Context context) {
        String DEFAULT = "n/l";

        SharedPreferences sharedPreferences = context.getSharedPreferences("mail address", Context.MODE_PRIVATE);
        return sharedPreferences.getString("mailID", DEFAULT);

    }

    public static void setItemNumberQuickSale(Context context, int code){

        SharedPreferences sharedPreferences = context.getSharedPreferences("ItemNo QuickSale", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("ItemNoQuickSale",code);
        editor.apply();

    }

    public static int getItemNumberQuickSale(Context context) {
        int DEFAULT = 1;

        SharedPreferences sharedPreferences = context.getSharedPreferences("ItemNo QuickSale", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("ItemNoQuickSale", DEFAULT);

    }

    public static void setCustomerId(Context context, int code){

        SharedPreferences sharedPreferences = context.getSharedPreferences("Customer ID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("CustomerID",code);
        editor.apply();

    }

    public static int getCustomerId(Context context) {
        int DEFAULT = 1000;

        SharedPreferences sharedPreferences = context.getSharedPreferences("Customer ID", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("CustomerID", DEFAULT);

    }

    public static void setLastOrderPaymentMethod(Context context, int paymentMethod){

        SharedPreferences sharedPreferences = context.getSharedPreferences("lastOrderPaymentMethod", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("paymentMethodId",paymentMethod);
        editor.apply();

    }

    public static int getLastOrderPaymentMethod(Context context) {
        int DEFAULT = 1001;

        SharedPreferences sharedPreferences = context.getSharedPreferences("lastOrderPaymentMethod", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("paymentMethodId", DEFAULT);

    }

    public static void setLogin(Context context, int code){

        SharedPreferences sharedPreferences = context.getSharedPreferences("select login code", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("loginCode",code);
        editor.apply();

    }

    public static int getLogin(Context context) {
        int DEFAULT = 0;

        SharedPreferences sharedPreferences = context.getSharedPreferences("select login code", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("loginCode", DEFAULT);

    }

    public static void setStepper(Context context, boolean state){

        SharedPreferences sharedPreferences = context.getSharedPreferences("activity stepper", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("activityStepper",state);
        editor.apply();

    }
    public static boolean getStepper(Context context) {
        boolean DEFAULT = false;

        SharedPreferences sharedPreferences = context.getSharedPreferences("activity stepper", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("activityStepper", DEFAULT);

    }

    public static void setPasswordArrayPrefs(String arrayName, ArrayList<String> array, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName +"_size", array.size());
        for(int i=0;i<array.size();i++)
            editor.putString(arrayName + "_" + i, array.get(i));
        editor.apply();
    }

    public static ArrayList<String> getPasswordArrayPrefs(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        ArrayList<String> array = new ArrayList<>(size);
        for(int i=0;i<size;i++)
            array.add(prefs.getString(arrayName + "_" + i, null));
        return array;
    }
}
