package com.example.customerOrderApp.SalesReport;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.customerOrderApp.AppInit;
import com.example.customerOrderApp.CheckInternetConnection;
import com.example.customerOrderApp.R;
import com.example.customerOrderApp.Service.VolleyBackgroundTaskGet;
import com.example.customerOrderApp.helper.CheckOnline;
import com.example.customerOrderApp.helper.Validation;
import com.example.customerOrderApp.pojo.Order;
import com.example.customerOrderApp.pojo.OrderDetails;
import com.printlib.kale.kaleprintsdk.PrinterUtilHelper;

import java.util.ArrayList;
import java.util.List;

public class PaymentDetailsActivity extends AppCompatActivity implements VolleyBackgroundTaskGet.ResultOrderAndOrderDelegate {

    private ArrayList<OrderDetails> reportOrderDetailsList = new ArrayList<>();
    private List<Order> orderListPrint = new ArrayList<>();
    private PaymentDetailAdapter reportOrderDetailsAdapter;
    private ListView orderDetailsListView;
    PrinterUtilHelper printerUtilHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        Validation.HideBottomNavigationCall(this);

        //==========================================================================================
        //==========================================================================================
        //CHECK INTERNET CONNECTION
        //==========================================================================================
        //==========================================================================================
        CheckOnline checkOnline = new CheckOnline();
        if(!CheckOnline.isOnline(PaymentDetailsActivity.this)) {
            Intent startIntent = new Intent(PaymentDetailsActivity.this, CheckInternetConnection.class);
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
        String url = "http://10.0.2.2:8080/get/all/payment/details";
        VolleyBackgroundTaskGet.syncAllOrderPaymentDetails(getApplicationContext(), url, this);

    }

    @Override
    public void sendOrderListBack(List<OrderDetails> orderDetailsList,List<Order> orderList) {
        orderListPrint.clear();
        reportOrderDetailsList.clear();
        reportOrderDetailsList.addAll(orderDetailsList);

        orderListPrint.addAll(orderList);

        reportOrderDetailsAdapter = new PaymentDetailAdapter(getApplicationContext(),reportOrderDetailsList);
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