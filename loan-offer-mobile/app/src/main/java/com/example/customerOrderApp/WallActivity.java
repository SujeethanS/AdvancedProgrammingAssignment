package com.example.customerOrderApp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.crashlytics.android.Crashlytics;
import com.example.customerOrderApp.SalesReport.PaymentReportActivity;
import com.example.customerOrderApp.SalesReport.SalesReportActivity;
import com.example.customerOrderApp.Service.AsyncTaskGetClientID;
import com.example.customerOrderApp.Service.VolleyBackgroundTaskGet;
import com.example.customerOrderApp.dashboard.DashboardActivity;
import com.example.customerOrderApp.helper.AppSettings;
import com.example.customerOrderApp.helper.CheckOnline;
import com.example.customerOrderApp.helper.Validation;
import com.example.customerOrderApp.pojo.Company;
import com.example.customerOrderApp.pojo.Location;
import com.example.customerOrderApp.pojo.Order;
import com.example.customerOrderApp.pojo.OrderDetails;
import com.example.customerOrderApp.pojo.User;
import com.google.android.material.navigation.NavigationView;
import com.printlib.kale.kaleprintsdk.PrinterUtilHelper;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;


@SuppressLint("Registered")
public class WallActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, OrderSearchFragment.OnFragmentInteractionListener,
        Runnable, MainActivity.MainActivityDelegate, AsyncTaskGetClientID.GetClientIdDelegate {
    public static Class<?> java;

    public static int categoryClick = 0;
    public Context appContext;
    WallActivity wallActivity;
    private final List<OrderDetails> printOrderDetails = new ArrayList<>();
    private final Company company = new Company();

    private PrinterUtilHelper printerUtilHelper;

    //AddItemFragment addItemFragment;
    public  Activity activity; // todo leak need to change
    private final List<Location> listLocation = new ArrayList<>();
    private final List<User> listUser = new ArrayList<>();

    String baseUrl;

    private Animation animation1;
    private CardView cardViewOrder;
    private CardView cardViewReport;
    private CardView cardViewDashboard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);
        Fabric.with(this, new Crashlytics());
        //TODO: Move this to where you establish a use
        logUser();
        activity = this;

        cardViewOrder = findViewById(R.id.card_Order);
        CardView cardViewCustomer = findViewById(R.id.card_Customer);
        cardViewReport = findViewById(R.id.card_Report);
        CardView cardViewExpense = findViewById(R.id.card_expense);
        cardViewDashboard = findViewById(R.id.card_dashboard);

        cardViewOrder.setOnClickListener(this);
        cardViewCustomer.setOnClickListener(this);
        cardViewReport.setOnClickListener(this);
        cardViewExpense.setOnClickListener(this);
        cardViewDashboard.setOnClickListener(this);

        animation1 = new AlphaAnimation(0.3f, 1.0f);
        animation1.setDuration(50);

        if (AppInit.getUtilHelper()!=null){
            printerUtilHelper = AppInit.getUtilHelper();
        }else {
            printerUtilHelper = new PrinterUtilHelper(this);
            AppInit.setUtilHelper(printerUtilHelper);
        }

        baseUrl = AppSettings.getURLs(this);
        wallActivity = this;

        //==========================================================================================
        // CUSTOM PERMISSION FOR STORAGE
        //==========================================================================================
        if (ContextCompat.checkSelfPermission(WallActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (!ActivityCompat.shouldShowRequestPermissionRationale(WallActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
                ActivityCompat.requestPermissions(WallActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            }

        }
        if (ContextCompat.checkSelfPermission(WallActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (!ActivityCompat.shouldShowRequestPermissionRationale(WallActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
                ActivityCompat.requestPermissions(WallActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            }

        }
        appContext = this;

        company.setCompanyName(AppSettings.getCompanyName(this));
        company.setCompanyAddress(AppSettings.getCompanyAddress(this));
        company.setCompanyContactNumber(AppSettings.getCompanyContactNumber(this));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (!CheckOnline.isOnline(this)) {
            Intent startIntent = new Intent(WallActivity.this, CheckInternetConnection.class);
            startActivity(startIntent);
        }

        DrawerLayout drawer = findViewById(R.id.wallActivity_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(WallActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        MainActivity.getInstance(this);

        loadLocationAndUSer();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Validation.HideBottomNavigationCall(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (printerUtilHelper!=null){
            String type = String.valueOf(AppSettings.getPrinterPaperSize(this));
            printerUtilHelper.initialized("bluetooth",type);
        }
    }


    @Override
    public void onBackPressed() {

        DrawerLayout layout = findViewById(R.id.wallActivity_drawer_layout);
        if (layout.isDrawerOpen(GravityCompat.START)) {
            layout.closeDrawer(GravityCompat.START);

        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to exit?");
            builder.setPositiveButton("Yes", (dialog, which) -> System.exit(0));
            builder.setNegativeButton("No", (dialog, which) -> {

            });
            builder.show();
            setResult(RESULT_CANCELED);

        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (R.id.menu_dashboard == id) {
            Intent intent = new Intent(WallActivity.this, PaymentReportActivity.class);
            startActivity(intent);
        } else if (R.id.menu_exit == id){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to Logout?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                AppSettings.setLogin(WallActivity.this, 0);
                Intent intent = new Intent(WallActivity.this, UserLogin.class);
                startActivity(intent);
                System.exit(0);
            });
            builder.setNegativeButton("No", (dialog, which) -> {

            });
            builder.show();
            setResult(RESULT_CANCELED);
        }
        {
            DrawerLayout drawer = findViewById(R.id.wallActivity_drawer_layout);
            assert drawer != null;
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.card_Order:
                cardViewOrder.startAnimation(animation1);
                Intent mainActivity = new Intent(WallActivity.this, MainActivity.class);
                String type1 = "Normal";
                mainActivity.putExtra("NORMAL", type1);
                startActivity(mainActivity);
                break;
            case R.id.card_Report:
                cardViewReport.startAnimation(animation1);
                Intent report = new Intent(WallActivity.this, SalesReportActivity.class);
                startActivity(report);
                break;
            case R.id.card_dashboard:
                cardViewDashboard.startAnimation(animation1);
                Intent dashboard = new Intent(WallActivity.this, PaymentReportActivity.class);
                startActivity(dashboard);
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onOrderButtonClicked() {

    }

    @Override
    public void setItemDetailsToOrder(OrderDetails orderDetailsPojo) {

    }

    @Override
    public void categoryClicked() {
        categoryClick = 0;
    }

    @Override
    public void itemClicked() {
        categoryClick = 0;
    }

    @Override
    public void cardPopup() {

    }

    @Override
    public void sendCardCount(String type, String sales) {

    }

    @Override
    public void changeQuickSales(String type) {

    }

    @Override
    public void sendToPrint(Order order, List<OrderDetails> orderDetails) {
        printOrderDetails.addAll(orderDetails);
    }


    private void logUser() {
        Crashlytics.setUserIdentifier(AppSettings.getUniqueId(this));
        Crashlytics.setUserEmail(AppSettings.getCompanyMailID(this));
        Crashlytics.setUserName(AppSettings.getCompanyName(this));
    }

    @Override
    public void run() {

    }

    @Override
    public void clientIDSendBack(String clientID) {

        String comId = AppSettings.getCompanyId(this);
        AppSettings.setClientId(this,clientID);
        AppSettings.setCompanyId(getApplicationContext(),comId);
        AppSettings.setCompanyLocationId(this,company.getLocationId());
        AppSettings.setRegisterCloud(this,1);
    }


    public void loadLocationAndUSer() {
        String url = baseUrl + "api/inv/inventory-locations";
        VolleyBackgroundTaskGet.syncAllLocations(getApplicationContext(), url, locationList -> {

            if(locationList.size()>0){
                listLocation.addAll(locationList);
            }
        });


        String urll = baseUrl + "api/auth/users";
        VolleyBackgroundTaskGet.syncAllUser(getApplicationContext(), urll, userList -> {

            if(userList.size()>0) {
                listUser.addAll(userList);
            }
        });

    }
}


