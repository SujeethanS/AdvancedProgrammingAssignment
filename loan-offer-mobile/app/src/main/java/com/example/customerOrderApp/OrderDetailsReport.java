package com.example.customerOrderApp;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.customerOrderApp.Service.VolleyBackgroundTaskGet;
import com.example.customerOrderApp.adapter.ReportOrderDetailsAdapter;
import com.example.customerOrderApp.helper.AppSettings;
import com.example.customerOrderApp.helper.CheckOnline;
import com.example.customerOrderApp.helper.Validation;
import com.example.customerOrderApp.pojo.Company;
import com.example.customerOrderApp.pojo.Order;
import com.example.customerOrderApp.pojo.OrderDetails;
import com.printlib.kale.kaleprintsdk.PrinterUtilHelper;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsReport extends AppCompatActivity implements VolleyBackgroundTaskGet.ResultOrderAndOrderDelegate {
    private ArrayList<OrderDetails> reportOrderDetailsList = new ArrayList<>();
    private List<Order> orderListPrint = new ArrayList<>();
    private ReportOrderDetailsAdapter reportOrderDetailsAdapter;
    private ListView orderDetailsListView;


    PrinterUtilHelper printerUtilHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_report);

        Validation.HideBottomNavigationCall(this);

        //==========================================================================================
        //==========================================================================================
        //CHECK INTERNET CONNECTION
        //==========================================================================================
        //==========================================================================================
        CheckOnline checkOnline = new CheckOnline();
        if(!CheckOnline.isOnline(OrderDetailsReport.this)) {
            Intent startIntent = new Intent(OrderDetailsReport.this, CheckInternetConnection.class);
            startActivity(startIntent);
        }



        orderDetailsListView = findViewById(R.id.orderDetailsListView);

        if (AppInit.getUtilHelper()!=null){
            printerUtilHelper = AppInit.getUtilHelper();
        }else {
            printerUtilHelper = new PrinterUtilHelper(this);
            AppInit.setUtilHelper(printerUtilHelper);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume(){
        super.onResume();
        String url = "http://10.0.2.2:8080/get/all/order/details";
        VolleyBackgroundTaskGet.syncAllOrderAndOrderDetails(getApplicationContext(), url, this);

    }

    @Override
    public void sendOrderListBack(List<OrderDetails> orderDetailsList,List<Order> orderList) {
        orderListPrint.clear();
        reportOrderDetailsList.clear();
        reportOrderDetailsList.addAll(orderDetailsList);

        orderListPrint.addAll(orderList);

        reportOrderDetailsAdapter = new ReportOrderDetailsAdapter(getApplicationContext(),reportOrderDetailsList);
        orderDetailsListView.setAdapter(reportOrderDetailsAdapter);
        reportOrderDetailsAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }



}
