package com.example.customerOrderApp;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crashlytics.android.Crashlytics;
import com.example.customerOrderApp.adapter.SelectedItemAdapter;
import com.example.customerOrderApp.helper.AppSettings;
import com.example.customerOrderApp.helper.CheckOnline;
import com.example.customerOrderApp.helper.Validation;
import com.example.customerOrderApp.pojo.Company;
import com.example.customerOrderApp.pojo.Order;
import com.example.customerOrderApp.pojo.OrderDetails;
import com.printlib.kale.kaleprintsdk.PrinterUtilHelper;

import java.util.List;
import java.util.Objects;


import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        OrderSearchFragment.OnFragmentInteractionListener,
        OrderListFragment.OnFragmentInteractionListener,
        OrderPaymentFragment.OnFragmentInteractionListener{


    OrderSearchFragment orderSearchFragment;
    OrderListFragment orderListFragment;
    OrderPaymentFragment orderPaymentFragment;

    private static FragmentManager fragmentManager;

    Company company = new Company();

    static MainActivityDelegate delegate;

    Integer customer_Id = 0;
    String  customer_Name = "";

    String  baseUrl;

    String  type="";
    String  screenType="";
    static String customerNameSms;
    static String customerPhoneSms;
    public static int cartCount = 0;

    public MainActivity() {
        WallActivity.categoryClick = 0;
    }

    public static void getInstance(MainActivityDelegate mainActivityDelegate){
        delegate = mainActivityDelegate;
    }

    PrinterUtilHelper printerUtilHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        //TODO: Move this to where you establish a use

        if (AppInit.getUtilHelper()!=null){
            printerUtilHelper = AppInit.getUtilHelper();
        }else {
            printerUtilHelper = new PrinterUtilHelper(this);
            AppInit.setUtilHelper(printerUtilHelper);
        }
        company.setCompanyName(AppSettings.getCompanyName(this));
        company.setCompanyAddress(AppSettings.getCompanyAddress(this));
        company.setCompanyContactNumber(AppSettings.getCompanyContactNumber(this));
        company.setCompanyMessage(AppSettings.getCompanyMessage(this));

        baseUrl = AppSettings.getURLs(this);

        if(!CheckOnline.isOnline(MainActivity.this)) {
            Intent startIntent = new Intent(MainActivity.this, CheckInternetConnection.class);
            startActivity(startIntent);
        }

        String[] result = getIntent().getStringArrayExtra("select customer data");
        if (result != null){
            customer_Id= Integer.parseInt(result[0]);
            customer_Name =(result[1]);
            type = result[3];
            customerNameSms = result[1];
            customerPhoneSms = result[2];
        }

        setContentView(R.layout.main_activity);
        initializeFragment();

        if( getIntent().getStringExtra("NORMAL")!= null){
            type = getIntent().getStringExtra("NORMAL");}
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void onPayButtonClicked(Order order, List<OrderDetails> detailsPojoList) {
        if(type.equalsIgnoreCase("Normal")) {
            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .hide(orderListFragment).commit();

            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .hide(orderSearchFragment).commit();

            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .show(orderPaymentFragment).commit();

        }else {

            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .hide(orderListFragment).commit();

            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .show(orderPaymentFragment).commit();


        }
        orderPaymentFragment.setType(type);
        order.setOrderCustomerId(customer_Id);
        order.setCustomerName(customer_Name);
        customer_Id = 0;
        customer_Name = "";
        if(order.getOrderCustomerId()!=0){
            orderPaymentFragment.paymentMethod = 1001;
            orderPaymentFragment.cash.setBackgroundColor(Color.GREEN);
            orderPaymentFragment.credit.setBackgroundColor(Color.LTGRAY);
        }
        orderPaymentFragment.populatePaymentView(order,detailsPojoList);

    }

    @Override
    public void onBackButtonClicked(int size) {

        Validation.HideBottomNavigationCall(this);
        cartCount = OrderListFragment.selectIdList.size();

        if (type.equalsIgnoreCase("Normal") && screenType.equalsIgnoreCase("")) {
            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .hide(orderListFragment).commit();

            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .hide(orderPaymentFragment).commit();

            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .show(orderSearchFragment).commit();


            if (orderSearchFragment != null) {
                orderSearchFragment.setCardCount(cartCount);
            }

        }else if(screenType.equalsIgnoreCase("Normal")){

            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .hide(orderListFragment).commit();

            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .hide(orderPaymentFragment).commit();

            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .show(orderSearchFragment).commit();

            if(orderSearchFragment != null) {
                orderSearchFragment.setCardCount(cartCount);
            }

        }else {

            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .hide(orderListFragment).commit();

            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .hide(orderPaymentFragment).commit();
        }

    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onsetShoppingCardQty() {
        if(OrderListFragment.selectIdList == null) {
            cartCount = 0;
            orderSearchFragment.hideViewButton();
        }else {
            cartCount = OrderListFragment.selectIdList.size();
        }

        if (type.equalsIgnoreCase("Normal") && screenType.equalsIgnoreCase("")) {
            if (orderSearchFragment != null) {
                orderSearchFragment.setCardCount(cartCount);
            }
        }
    }

    @Override
    public void onOrderButtonClicked() {

        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .hide(orderSearchFragment).commit();

        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .hide(orderPaymentFragment).commit();

        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .show(orderListFragment).commit();


        orderListFragment.setOrderNumber(AppSettings.getLastOrderId(this));
        cartCount = OrderListFragment.selectIdList.size();
        if (type.equalsIgnoreCase("Normal") && screenType.equalsIgnoreCase("")) {
            if (orderSearchFragment != null) {
                orderSearchFragment.setCardCount(cartCount);
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onAcceptButtonClick() {

        if (type.equalsIgnoreCase("Normal")) {

            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .hide(orderPaymentFragment).commitAllowingStateLoss();

            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .hide(orderListFragment).commitAllowingStateLoss();

            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .show(orderSearchFragment).commitAllowingStateLoss();

            orderSearchFragment.itemVisibleLayout.setVisibility(View.VISIBLE);
            orderSearchFragment.subcategoryVisibleLayout.setVisibility(View.INVISIBLE);
            orderSearchFragment.categoryVisibleLayout.setVisibility(View.INVISIBLE);
            orderSearchFragment.clearSearchDetails();
            orderListFragment.finalOderDiscountSum = 0.0;//item.setEnabled(false);

            orderSearchFragment.selectCategoryName = "";
            orderSearchFragment.selectSubCategoryName = "";
            //todo we need to discuss about this, clear list or get from server for qty?
            orderSearchFragment.selectItemUrl("", "");
            orderSearchFragment.itemsAdapter.notifyDataSetChanged();
            orderSearchFragment.itemsAdapter.setMoreDataAvailableTrue();
            orderSearchFragment.toolBarInitialize();
            orderSearchFragment.selectItemUrl("","");
            orderSearchFragment.setCardCount(0);

        } else {
            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .hide(orderListFragment).commitAllowingStateLoss();

            orderListFragment.finalOderDiscountSum = 0.0;

        }
    }

    @Override
    public void onGetOrderType() {
            String orderType = type;
            if (orderType.equalsIgnoreCase("Standard")){
                orderListFragment = (OrderListFragment) fragmentManager.findFragmentByTag("orderListFragmentTag");
                assert orderListFragment != null;
                orderListFragment.selectItemRecyclerView.setEnabled(false);
            }
    }


    @Override
    public void setItemDetailsToOrder(OrderDetails orderDetails) {
        orderListFragment.setItemToOrderDetailsList(orderDetails);

    }

    @Override
    public void categoryClicked() {
        WallActivity.categoryClick = 1;
        orderSearchFragment.categoryVisibleLayout.setVisibility(View.VISIBLE);
        orderSearchFragment.subcategoryVisibleLayout.setVisibility(View.INVISIBLE);
        orderSearchFragment.itemVisibleLayout.setVisibility(View.INVISIBLE);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void itemClicked() {
        WallActivity.categoryClick = 0;
        orderSearchFragment.categoryVisibleLayout.setVisibility(View.INVISIBLE);
        orderSearchFragment.subcategoryVisibleLayout.setVisibility(View.INVISIBLE);
        orderSearchFragment.itemVisibleLayout.setVisibility(View.VISIBLE);

        orderSearchFragment.selectCategoryName = "";
        orderSearchFragment.selectSubCategoryName = "";
        orderSearchFragment.selectItemUrl("", "");
        orderSearchFragment.itemsAdapter.notifyDataSetChanged();
        orderSearchFragment.itemsAdapter.setMoreDataAvailableTrue();
    }

    @Override
    public void cardPopup() {
        if(OrderListFragment.selectIdList.size() > 0 ){
            showSelectedItemPopup();
        }
    }

    @Override
    public void sendCardCount(String type, String sales) {
        if(type.equalsIgnoreCase("close")){
            cartCount = 0;
            if (this.type.equalsIgnoreCase("Normal") && sales.equalsIgnoreCase("Normal")) {
                if (orderSearchFragment != null) {
                    orderSearchFragment.setCardCount(cartCount);
                }
            }
            orderPaymentFragment.clearData();
            orderSearchFragment.hideViewButton();
        }else {
            cartCount = OrderListFragment.selectIdList.size();
            orderPaymentFragment.clearData();
            if (type.equalsIgnoreCase("Normal") && sales.equalsIgnoreCase("Normal")) {
                if (orderSearchFragment != null) {
                    orderSearchFragment.setCardCount(cartCount);
                }
            }
        }

    }

    @Override
    public void changeQuickSales(String type) {

        Validation.HideBottomNavigationCall(this);
        screenType = type;
        if (type.equalsIgnoreCase("Normal")) {

            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .hide(orderPaymentFragment).commit();

            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .hide(orderListFragment).commit();

            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .show(orderSearchFragment).commit();

            orderSearchFragment.toolBarInitialize();
            orderSearchFragment.selectItemUrl("", "");

            if (orderSearchFragment != null) {
                orderSearchFragment.setCardCount(cartCount);
            }

        }else {

            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .hide(orderPaymentFragment).commit();

            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .hide(orderSearchFragment).commit();

            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .hide(orderListFragment).commit();


        }
    }


    @Override
    public void clearSelectIdlist() {
        cartCount = 0;
        orderListFragment.clearSelectIdlistItems();
        if (this.type.equalsIgnoreCase("Normal") && screenType.equalsIgnoreCase("")) {
            if (orderSearchFragment != null) {
                orderSearchFragment.setCardCount(cartCount);
            }
        }

    }

    @Override
    public void onpaymentBackButtonClick() {


        if(type.equalsIgnoreCase("Normal")) {
            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .hide(orderPaymentFragment).commit();

            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .hide(orderSearchFragment).commit();

            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .show(orderListFragment).commit();
        }else {
            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .hide(orderPaymentFragment).commit();

            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .show(orderListFragment).commit();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initializeFragment(){
        fragmentManager = getSupportFragmentManager();

        if( getIntent().getStringExtra("NORMAL")!= null){
            type = getIntent().getStringExtra("NORMAL");}

        orderListFragment = new OrderListFragment();
        orderPaymentFragment = new OrderPaymentFragment();
        orderSearchFragment = new OrderSearchFragment();

        if (type.equalsIgnoreCase("Normal")) {

            fragmentManager.beginTransaction()
                    .add(R.id.mainRelativeLayout,orderListFragment,"orderListFragmentTag")
                    .addToBackStack(null)
                    .hide(orderListFragment).commit();

            fragmentManager.beginTransaction()
                    .add(R.id.mainRelativeLayout,orderPaymentFragment,"orderPaymentFragmentTag")
                    .addToBackStack(null)
                    .hide(orderPaymentFragment).commit();

            fragmentManager.beginTransaction()
                    .add(R.id.mainRelativeLayout,orderSearchFragment,"orderSearchFragmentTag")
                    .addToBackStack(null)
                    .show(orderSearchFragment).commit();

        }else {

            fragmentManager.beginTransaction()
                    .add(R.id.mainRelativeLayout,orderListFragment,"orderListFragmentTag")
                    .addToBackStack(null)
                    .hide(orderListFragment).commit();

            fragmentManager.beginTransaction()
                    .add(R.id.mainRelativeLayout,orderPaymentFragment,"orderPaymentFragmentTag")
                    .addToBackStack(null)
                    .hide(orderPaymentFragment).commit();

            fragmentManager.beginTransaction()
                    .add(R.id.mainRelativeLayout,orderSearchFragment,"orderSearchFragmentTag")
                    .addToBackStack(null)
                    .hide(orderSearchFragment).commit();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        AppSettings.setActivityLive(this,false);
        Validation.HideBottomNavigationCall(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onPause() {
        super.onPause();
        AppSettings.setActivityLive(this,true);

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        assert activityManager != null;
        activityManager.moveTaskToFront(getTaskId(), 0);
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_HOME) {
            Log.i("Home Button", "Clicked");
            return true;
        }
        return keyCode == KeyEvent.KEYCODE_BACK;
    }

    interface MainActivityDelegate{
        void sendToPrint(Order order, List<OrderDetails> orderDetails);
    }


    @SuppressLint("NotifyDataSetChanged")
    public void showSelectedItemPopup() {

        AlertDialog.Builder builderRegistrationPopup = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams") final View mView = getLayoutInflater().inflate(R.layout.shopping_items_list_popup, null);
        builderRegistrationPopup.setCancelable(false);



        final ImageButton btn_close = mView.findViewById(R.id.imgBtn_close_cardClick);
        final RecyclerView recyclerSelectItems = mView.findViewById(R.id.SelectedItemList_Recycler);
        SelectedItemAdapter selectedItemAdapter;

        recyclerSelectItems.setItemAnimator(new DefaultItemAnimator());
        selectedItemAdapter = new SelectedItemAdapter(this, OrderListFragment.selectIdList);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerSelectItems.setLayoutManager(layoutManager);
        recyclerSelectItems.setAdapter(selectedItemAdapter);
        selectedItemAdapter.notifyDataSetChanged();

        builderRegistrationPopup.setView(mView);
        final AlertDialog dialog = builderRegistrationPopup.create();
        Objects.requireNonNull(dialog.getWindow()).getAttributes();
        dialog.show();



        btn_close.setOnClickListener(view -> dialog.cancel());
    }


}
