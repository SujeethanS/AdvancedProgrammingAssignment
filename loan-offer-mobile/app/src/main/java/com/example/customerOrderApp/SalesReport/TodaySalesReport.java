package com.example.customerOrderApp.SalesReport;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.customerOrderApp.OrderDetailsReport;
import com.example.customerOrderApp.Service.VolleyBackgroundTaskGet;
import com.example.customerOrderApp.adapter.SalesReceiptAndInvoiceAdapter;
import com.example.customerOrderApp.helper.AppSettings;
import com.example.customerOrderApp.helper.CheckOnline;
import com.example.customerOrderApp.helper.ReadableDateFormat;
import com.example.customerOrderApp.helper.RecyclerTouchListener;
import com.example.customerOrderApp.pojo.Order;
import com.example.customerOrderApp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import io.supercharge.shimmerlayout.ShimmerLayout;

//Our class extending fragment
public class TodaySalesReport extends Fragment implements VolleyBackgroundTaskGet.ResultSalesreceiptAndInvoiceDelegate {

    private RecyclerView reportRecyclerView;
    private ArrayList<Order> reportOrderList;
    SalesReceiptAndInvoiceAdapter salesReceiptAndInvoiceAdapter;
    OnFragmentInteractionListener mListener;
    private LinearLayout noDataFound,dataFound;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout skeletonLayout;
    private ShimmerLayout shimmer;
    private LayoutInflater inflater;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_today_salesreport, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        noDataFound = view.findViewById(R.id.no_data_found);
        dataFound = view.findViewById(R.id.data_found);

        swipeRefreshLayout = view.findViewById(R.id.pullToRefreshTodaySales);
        reportOrderList = new ArrayList<>();

        reportRecyclerView = view.findViewById(R.id.order_recyclerView);
        reportRecyclerView.setItemAnimator(new DefaultItemAnimator());

        skeletonLayout = view.findViewById(R.id.skeletonLayout);
        shimmer = view.findViewById(R.id.shimmerSkeleton);
        this.inflater = (LayoutInflater) Objects.requireNonNull(getActivity())
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        dayReportUrl();

        if(CheckOnline.isOnline(getActivity())) {
            salesReceiptAndInvoiceAdapter = new SalesReceiptAndInvoiceAdapter(getActivity(),reportOrderList);
            salesReceiptAndInvoiceAdapter.setLoadMoreListener(() -> {
                int index = reportOrderList.size() - 1;
                if(index >= 19) {
                    loadMore(index);
                }
            });


            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
            reportRecyclerView.setLayoutManager(layoutManager);
            reportRecyclerView.setHasFixedSize(true);
            reportRecyclerView.setAdapter(salesReceiptAndInvoiceAdapter);

        }else {
            CheckOnline.internetErrorMgs(getActivity());
        }


        reportRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), reportRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (position > -1) {
                    Intent intent = new Intent(getActivity(), OrderDetailsReport.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onItemClick(View v, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        swipeRefreshLayout.setColorSchemeResources(R.color.DarkGreen);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.White);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            load();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
    }



    private void dayReportUrl() {
        reportRecyclerView.setAdapter(null);
        if(CheckOnline.isOnline(getActivity())) {
            load();
        }
    }


    public void load(){
        String url = "http://10.0.2.2:8080/get/all/order";
        VolleyBackgroundTaskGet.syncAllSalesreceiptAndInvoice(Objects.requireNonNull(getActivity()),url,this,"load");
        showSkeleton(true);
        dataFound.setVisibility(View.GONE);

    }
    private void loadMore(int index){
        String url = "http://10.0.2.2:8080/get/all/order";
        VolleyBackgroundTaskGet.syncAllSalesreceiptAndInvoice(Objects.requireNonNull(getActivity()),url,this,"loadMore");
    }


    @Override
    public void sendSalesReceiptAndInvoiceDetails(List<Order> salesReceiptAndInvoiceList, String type) {

        if(type.equals("load")){
            if(reportOrderList.size() > 0){
                reportOrderList.clear();
            }
            reportOrderList.addAll(salesReceiptAndInvoiceList);
            if(reportOrderList.size() == 0){
                noDataFound.setVisibility(View.VISIBLE);
                dataFound.setVisibility(View.GONE);
            }else {
                noDataFound.setVisibility(View.GONE);
                dataFound.setVisibility(View.VISIBLE);
            }
            showSkeleton(false);
            salesReceiptAndInvoiceAdapter.notifyDataChanged();

        }else if(type.equals("loadMore")){
            if(reportOrderList.size()>0) {
                reportOrderList.remove(reportOrderList.size() - 1);
            }

            if (salesReceiptAndInvoiceList.size() > 0) {
                reportOrderList.addAll(salesReceiptAndInvoiceList);

                if (salesReceiptAndInvoiceList.size() == 1) {
                    salesReceiptAndInvoiceAdapter.setMoreDataAvailable();

                }
            } else {
                salesReceiptAndInvoiceAdapter.setMoreDataAvailable();
                Toast.makeText(getActivity(), "No More Data Available", Toast.LENGTH_LONG).show();
            }
            salesReceiptAndInvoiceAdapter.notifyDataChanged();
        }

    }

    private int getSkeletonRowCount(Context context) {
        int pxHeight = getDeviceHeight(context);
        int skeletonRowHeight = (int) getResources()
                .getDimension(R.dimen.row_layout_height); //converts to pixel
        return (int) Math.ceil(pxHeight / skeletonRowHeight);
    }
    private int getDeviceHeight(Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return metrics.heightPixels;
    }


    private void showSkeleton(boolean show) {

        if (show) {

            skeletonLayout.removeAllViews();

            int skeletonRows = getSkeletonRowCount(getActivity());
            for (int i = 0; i <= skeletonRows; i++) {
                @SuppressLint("InflateParams") ViewGroup rowLayout = (ViewGroup) inflater
                        .inflate(R.layout.refresh_settlement_report_single, null);
                skeletonLayout.addView(rowLayout);
            }
            shimmer.setVisibility(View.VISIBLE);
            shimmer.startShimmerAnimation();
            skeletonLayout.setVisibility(View.VISIBLE);
            skeletonLayout.bringToFront();
        } else {
            shimmer.stopShimmerAnimation();
            shimmer.setVisibility(View.GONE);
        }
    }

}

