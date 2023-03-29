package com.example.customerOrderApp.SalesReport;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.viewpager.widget.ViewPager;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.example.customerOrderApp.CheckInternetConnection;
import com.example.customerOrderApp.R;
import com.example.customerOrderApp.Service.VolleyBackgroundTaskGet;
import com.example.customerOrderApp.adapter.ViewPageAdapter;
import com.example.customerOrderApp.helper.AppSettings;
import com.example.customerOrderApp.helper.CheckOnline;
import com.example.customerOrderApp.helper.ReadableDateFormat;
import com.example.customerOrderApp.helper.Validation;
import com.example.customerOrderApp.pojo.Company;
import com.example.customerOrderApp.pojo.Order;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentReportActivity extends AppCompatActivity implements  VolleyBackgroundTaskGet.ResultOrderDelegate ,
        TodayPaymentReport.OnFragmentInteractionListener {

    private static final List<Order> orderIdList = new ArrayList<>();

    private List<Order>reportOrderList;
    private int reportPosition = 0;

    private final Company company = new Company();

    private TodayPaymentReport todayReport;
    private final String startDate = ReadableDateFormat.getTodayDate(new Date(System.currentTimeMillis()));
    private final String endDate = ReadableDateFormat.tomorrowDate(new Date(System.currentTimeMillis()));//getTomorrowDate
    private MenuItem myMenu;

    private SearchView searchView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_report);

        Validation.HideBottomNavigationCall(this);

        String callingFrom = getIntent().getStringExtra("CALL_FROM");

        if (!CheckOnline.isOnline(this)) {
            Intent startIntent = new Intent(this, CheckInternetConnection.class);
            startActivity(startIntent);
        }

        company.setCompanyName(AppSettings.getCompanyName(this));
        company.setCompanyAddress(AppSettings.getCompanyAddress(this));
        company.setCompanyContactNumber(AppSettings.getCompanyContactNumber(this));

        reportOrderList = new ArrayList<>();
        findViewById(R.id.tabLayout);
        TabLayout tabLayout;

        tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.pager);
        ViewPageAdapter viewPagerAdapter = new ViewPageAdapter(getSupportFragmentManager());

        todayReport = new TodayPaymentReport();

        viewPagerAdapter.addFragment(todayReport, "Payment");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                reportPosition = position;
                switch (reportPosition){
                    case 0:
                        if (myMenu != null) {
                            myMenu.setVisible(true);
                        }
                        break;
                    case 2:
                        if (myMenu != null) {
                            myMenu.setVisible(false);
                        }
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);

        if(callingFrom != null && callingFrom.equals("DASHBOARD")){
            if (myMenu != null) {
                myMenu.setVisible(false);
            }
            viewPager.setCurrentItem(2);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("backActivity","Start" +AppSettings.getActivityLive(this));

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

    @Override
    protected void onPause(){
        super.onPause();
        AppSettings.setActivityLive(this,true);
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.report_menu, menu);// Inflate the menu; this adds items to the action bar if it is present.

        SearchManager searchManager = (SearchManager) getApplicationContext().getSystemService(Context.SEARCH_SERVICE);

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
            searchView.setQueryHint("Order ID");
            searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText.length() == 0) {
                        if (CheckOnline.isOnline(getApplicationContext())) {

                            if (reportPosition == 0) {
                                todayReport.load();
                                todayReport.salesReceiptAndInvoiceAdapter.setMoreDataAvailableTrue();

                            }
                        }
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {// Handle action bar item clicks here. The action bar will
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void sendOrderList(List<Order> orderList,String type) {
        reportOrderList.clear();
        reportOrderList.addAll(orderList);
        orderIdList.clear();
        orderIdList.addAll(reportOrderList);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}