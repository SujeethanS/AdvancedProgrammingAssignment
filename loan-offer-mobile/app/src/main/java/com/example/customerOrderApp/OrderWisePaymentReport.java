package com.example.customerOrderApp;

import android.content.Intent;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerOrderApp.Service.VolleyBackgroundTaskGet;
import com.example.customerOrderApp.adapter.CreditPaymentReportAdapter;
import com.example.customerOrderApp.helper.AppSettings;
import com.example.customerOrderApp.helper.CheckOnline;
import com.example.customerOrderApp.helper.Validation;
import com.example.customerOrderApp.pojo.Payment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderWisePaymentReport extends AppCompatActivity implements VolleyBackgroundTaskGet.ResultCreditPaymentDelegate {

    private RecyclerView recyclerViewPaymentDetails;
    private CreditPaymentReportAdapter creditPaymentReportAdapter;
    private List<Payment> paymentList ;

    private LinearLayout noDataFound, dataFound;
    private ProgressBar progressBar;
    private String baseUrl;
    private String invoiceNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_wise_payment_report);

        Validation.HideBottomNavigationCall(this);

        //==========================================================================================
        //==========================================================================================
        //CHECK INTERNET CONNECTION
        //==========================================================================================
        //==========================================================================================
        CheckOnline checkOnline = new CheckOnline();
        if(!CheckOnline.isOnline(OrderWisePaymentReport.this)) {
            Intent startIntent = new Intent(OrderWisePaymentReport.this, CheckInternetConnection.class);
            startActivity(startIntent);
        }

        baseUrl = AppSettings.getURLs(Objects.requireNonNull(getApplicationContext()));
        invoiceNo = getIntent().getStringExtra("INVOICE_NO");

        noDataFound = findViewById(R.id.no_data_found_Settlement);
        dataFound = findViewById(R.id.data_found_Settlement);
        progressBar = findViewById(R.id.progressBar_cyclic_Settlement);

        paymentList = new ArrayList<>();
        recyclerViewPaymentDetails = findViewById(R.id.payment_recyclerView);
        recyclerViewPaymentDetails.setItemAnimator(new DefaultItemAnimator());

        load();

        if (CheckOnline.isOnline(getApplicationContext())) {

            creditPaymentReportAdapter = new CreditPaymentReportAdapter(getApplicationContext(), paymentList,"");
            creditPaymentReportAdapter.setLoadMoreListener(new CreditPaymentReportAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    int index = paymentList.size() - 1;
                   // loadMore(index);
                }
            });

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerViewPaymentDetails.setLayoutManager(layoutManager);
            recyclerViewPaymentDetails.setHasFixedSize(true);
            recyclerViewPaymentDetails.setAdapter(creditPaymentReportAdapter);

        } else {
            CheckOnline.internetErrorMgs(getApplicationContext());
        }

    }

    private void load(){
        String url = baseUrl + "api/sales/payments/ext/order/trans/by/"+ invoiceNo;
        VolleyBackgroundTaskGet.syncAllCreditPayment(getApplicationContext(), url, OrderWisePaymentReport.this, "load");
        progressBar.setVisibility(View.VISIBLE);
        dataFound.setVisibility(View.GONE);
    }

    private void loadMore(int index) {
        String url = baseUrl + "api/sales/payments/ext/order/trans/by/"+ invoiceNo;
        VolleyBackgroundTaskGet.syncAllCreditPayment(getApplicationContext(), url, OrderWisePaymentReport.this, "loadMore");

    }

    @Override
    public void sendPaymentDetails(List<Payment> invoiceList, String type) {

        if (type.equals("load")) {
            if (paymentList.size() > 0) {
                paymentList.clear();
            }
            paymentList.addAll(invoiceList);
            if (paymentList.size() == 0) {
                noDataFound.setVisibility(View.VISIBLE);
                dataFound.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
            } else {
                noDataFound.setVisibility(View.GONE);
                dataFound.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
            creditPaymentReportAdapter.notifyDataChanged();

        } else if (type.equals("loadMore")) {
            paymentList.remove(paymentList.size() - 1);

            if (invoiceList.size() > 0) {
                paymentList.addAll(invoiceList);

                if (invoiceList.size() == 1) {
                    creditPaymentReportAdapter.setMoreDataAvailable();
                }
            } else {
                creditPaymentReportAdapter.setMoreDataAvailable();
                Toast.makeText(getApplicationContext(), "No More Data Available", Toast.LENGTH_LONG).show();
            }
            creditPaymentReportAdapter.notifyDataChanged();
        }

        double receivedTotal = 0.0;
        for(int i = 0; i < paymentList.size(); i++){
            receivedTotal = receivedTotal + paymentList.get(i).getReceivedAmount();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
