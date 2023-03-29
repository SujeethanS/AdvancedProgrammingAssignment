package com.example.customerOrderApp.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.customerOrderApp.SalesReport.SalesReportActivity;
import com.example.customerOrderApp.Service.VolleyBackgroundTaskGet;
import com.example.customerOrderApp.helper.AppSettings;
import com.example.customerOrderApp.helper.ReadableDateFormat;
import com.example.customerOrderApp.helper.Validation;
import com.example.customerOrderApp.pojo.Item;
import com.example.customerOrderApp.pojo.SalesContent;
import com.example.customerOrderApp.pojo.TopTen;
import com.example.customerOrderApp.pojo.TotalContent;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.customerOrderApp.R;
import io.supercharge.shimmerlayout.ShimmerLayout;


public class DashboardActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener, View.OnClickListener, VolleyBackgroundTaskGet.ResultTopTenSalesDelegate,
        VolleyBackgroundTaskGet.ResultBottomTenSalesDelegate,VolleyBackgroundTaskGet.ResultMonthWiseSalesDelegate,
        VolleyBackgroundTaskGet.ResultInventoryItemDelegate,
        VolleyBackgroundTaskGet.ResultReOrderItemsDelegate,VolleyBackgroundTaskGet.ResultTotalAmountDelegate{

    protected Typeface tfLight;
    protected CombinedChart chart;
    protected PieChart chartTop,chartBot;
    protected SeekBar seekBarXT, seekBarYT,seekBarXB, seekBarYB;
    protected TextView tvXT, tvYT,tvXB, tvYB;
    protected LinearLayout topTenLayout,bottomTenLayout, linearLayoutTotalView;
    protected ImageView topTenAction,bottomTenAction;

    protected String select ="";

    protected static List<TopTen> productListTop;
    protected static List<TopTen> productListBottom;
    protected static List<SalesContent> salesContentList;
    protected static List<SalesContent> expenseContentList;

    protected String day;
    protected String dayTO;
    protected String dayTOF;
    protected String month;
    protected String monthEnd;
    protected String monthE;
    protected String baseUrl;
    protected int reOrderAlertQty;

    protected TextView totalSales,totalExpense,noOfExpiryItem,noOfReOrderItem;
    protected DecimalFormat dfQty = new DecimalFormat("#,##0.0");
    protected DecimalFormat dfAmount = new DecimalFormat("#,##0.00");

    protected CardView cardViewSalesReport;
    //protected CardView cardViewExpenseReport;
    protected CardView cardViewExpiryReport;
    protected CardView cardViewReorderReport;

    private LinearLayout skeletonLayout;
    private ShimmerLayout shimmer;
    private LayoutInflater inflater;

    private String todayDate;// = ReadableDateFormat.tomorrowDate(new Date(System.currentTimeMillis()));
    private String monthDate;// = ReadableDateFormat.getThiMonthDateAcct(new Date(System.currentTimeMillis()));
    private int locationId;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date nextMonthFirstDay = calendar.getTime();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date nextMonthLastDay = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        todayDate = simpleDateFormat.format(nextMonthLastDay) + "T23:59:59Z";
        monthDate = simpleDateFormat.format(nextMonthFirstDay) + "T00:00:00Z";

        locationId = AppSettings.getCompanyLocationId(this);

        totalSales = findViewById(R.id.total_sales);
        //totalExpense = findViewById(R.id.total_expense);
        noOfExpiryItem = findViewById(R.id.total_expiry_item);
        noOfReOrderItem = findViewById(R.id.total_re_order_item);

        cardViewSalesReport = findViewById(R.id.card_SalesReport);
        //cardViewExpenseReport = findViewById(R.id.card_ExpenseReport);
        cardViewExpiryReport = findViewById(R.id.card_ExpiryReport);
        cardViewReorderReport = findViewById(R.id.card_ReorderReport);

        skeletonLayout = findViewById(R.id.skeletonLayout);
        shimmer = findViewById(R.id.shimmerSkeleton);
        this.inflater = (LayoutInflater) getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        Date today = new Date(System.currentTimeMillis());
        day =    ReadableDateFormat.getTodyDate(today);
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DATE, 1);
        //String daySample = String.valueOf(c.getTime());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dayTO = sdf.format(c.getTime());
        month =ReadableDateFormat.getThiMonthDateAcct(new Date(System.currentTimeMillis()));

        String[]date = ReadableDateFormat.monthStartAndEndDate();
        dayTOF = date[0];
        monthEnd = date[1];
        monthE = ReadableDateFormat.getThiMonthDateAcctBefore(ReadableDateFormat.stringToDate(dayTOF));
        baseUrl = AppSettings.getURLs(this);
        reOrderAlertQty = AppSettings.getReorderAlertQty(this);

        salesContentList = new ArrayList<>();
        expenseContentList = new ArrayList<>();

        totalSalesYear();

        Validation.HideBottomNavigationCall(this);

        //layout
        topTenLayout = findViewById(R.id.top_ten_graph);
        bottomTenLayout = findViewById(R.id.bottom_ten_graph);
        linearLayoutTotalView = findViewById(R.id.linearTotal);
        topTenAction = findViewById(R.id.action_top);
        bottomTenAction = findViewById(R.id.action_bottom);
        topTenAction.setOnClickListener(this);
        bottomTenAction.setOnClickListener(this);

        //main chart
        chart = findViewById(R.id.horizontalLinechart);

        //pie

        tvXT = findViewById(R.id.tvXMax1);
        tvYT = findViewById(R.id.tvYMax2);
        tvXB = findViewById(R.id.tvXMax3);
        tvYB = findViewById(R.id.tvYMax4);

        seekBarXT = findViewById(R.id.seekBar1);
        seekBarYT = findViewById(R.id.seekBar2);
        seekBarXB = findViewById(R.id.seekBar3);
        seekBarYB = findViewById(R.id.seekBar4);

        seekBarXT.setOnSeekBarChangeListener(this);
        seekBarYT.setOnSeekBarChangeListener(this);
        seekBarXB.setOnSeekBarChangeListener(this);
        seekBarYB.setOnSeekBarChangeListener(this);

        chartTop = findViewById(R.id.top);
        chartBot = findViewById(R.id.bot);

        chartTop.setOnChartValueSelectedListener(this);

        chartBot.setOnChartValueSelectedListener(this);

        showSkeleton(true);
        linearLayoutTotalView.setVisibility(View.GONE);

        cardViewSalesReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, SalesReportActivity.class);
                intent.putExtra("CALL_FROM","DASHBOARD");
                startActivity(intent);
            }
        });

       /* cardViewExpenseReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*Intent intent = new Intent(DashboardActivity.this, ExpenseReportActivity.class);
                intent.putExtra("CALL_FROM","DASHBOARD");
                startActivity(intent);*//*
            }
        });*/

        cardViewExpiryReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(DashboardActivity.this, ExpiryReportActivity.class);
                intent.putExtra("CALL_FROM","DASHBOARD");
                startActivity(intent);*/
            }
        });

        cardViewReorderReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(DashboardActivity.this, ReOrderReportActivity.class);
                startActivity(intent);*/
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        //center data
        totalSalesMonth();
        totalExpiryMonth();
        totalReOrderMonth();
        //bottom graphs
        topTenMonth();
        bottomTenMonth();
    }

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.e("FINISH","Finished");
        ToastMessageHelper.customErrToast(DashboardActivity.this,"Finished");
        finish();
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Log.e("FINISH","Finished");
        //ToastMessageHelper.customErrToast(DashboardActivity.this,"Finished");
        finish();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvXT.setText(String.valueOf(seekBarXT.getProgress()));
        tvYT.setText(String.valueOf(seekBarYT.getProgress()));

        tvXB.setText(String.valueOf(seekBarXB.getProgress()));
        tvYB.setText(String.valueOf(seekBarYB.getProgress()));

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onClick(View v) {
        if(v != null){
            int id = v.getId();
            if(id == R.id.action_top){
                topTenLayout.setVisibility(View.GONE);
                bottomTenLayout.setVisibility(View.VISIBLE);
                bottomTenAction.setVisibility(View.VISIBLE);
                topTenAction.setVisibility(View.GONE);
                select = "Bottom";
            }else if(id == R.id.action_bottom){
                topTenLayout.setVisibility(View.VISIBLE);
                bottomTenLayout.setVisibility(View.GONE);
                topTenAction.setVisibility(View.VISIBLE);
                bottomTenAction.setVisibility(View.GONE);
                select = "Top";
            }
        }
    }

    @Override
    public void sendTopTenSales(List<TopTen> toTenList) {
        if(toTenList.size() > 0){
            productListTop = toTenList;
            PieChartTop.setData(seekBarXT.getProgress(), seekBarYT.getProgress(),chartTop,toTenList);
        }
    }

    @Override
    public void sendBottomTenSales(List<TopTen> toTenList) {
        if(toTenList.size() > 0){
            productListBottom = toTenList;
            PieChartBot.setData(seekBarXB.getProgress(), seekBarYB.getProgress(),chartBot,toTenList);

        }
    }

    @Override
    public void sendMonthWiseSales(List<SalesContent> sales) {
        if(sales.size() > 0){
            salesContentList = sales;
        }
    }


    @Override
    public void sendItemList(List<Item> list, String type) {
        if(list.size() > 0){
            noOfExpiryItem.setText(dfQty.format(list.size()));
        }else {
            noOfExpiryItem.setText(dfQty.format(0.00));
        }
    }

    @Override
    public void sendReOrderItems(List<Item> list, String type) {
        if(list.size() > 0){
            noOfReOrderItem.setText(dfQty.format(list.size()));
        }else {
            noOfReOrderItem.setText(dfQty.format(0.00));
        }
    }

    @Override
    public void sendAmountTotal(List<TotalContent> total) {
        if(total.size() > 0){
            totalSales.setText(dfAmount.format(total.get(0).getSum()));
        }else {
            totalSales.setText(dfAmount.format(0.00));
        }

    }

    private void totalSalesYear(){
        String startDate = ReadableDateFormat.getTodyDate(new Date(System.currentTimeMillis())).substring(0,4);
        String url = baseUrl+"api/rpt/orders/total-sales/monthly-loc?end_date="+ todayDate +"&start_date="+ startDate +"-01-01&location_id="+locationId;
        VolleyBackgroundTaskGet.syncMonthWiseSales(this,url,this);
    }


    private void totalSalesMonth(){
        VolleyBackgroundTaskGet.syncReportTotalAmount(this, baseUrl + "api/rpt/orders/total-sales/weekly-loc?end_date="+ todayDate +"&start_date="+ monthDate +"&location_id="+ locationId, this);
    }

    private void totalExpiryMonth(){
        String urlE = AppSettings.getURLs(this)+"api/inv/item/inventories/"+ dayTOF +"/"+ monthE;
        VolleyBackgroundTaskGet.syncAllInventory(this, urlE, this, "");
    }

    private void totalReOrderMonth(){
        /*String urlR = AppSettings.getURLs(this)+"api/inv/item/reorder";
        VolleyBackgroundTaskGet.syncAllReOrderItem(this, urlR, this, "");*/
        //String url = baseUrl + "api/inv/item/reorder/"+ reOrderAlertQty +"?page_offset=0&page_limit=20&&";
        //String url = baseUrl + "api/inv/item/reorder/"+reOrderAlertQty;
        String url = baseUrl + "api/inv/item/reorder";
        VolleyBackgroundTaskGet.syncAllReOrderItem(this, url, this, "");
    }

    private void topTenMonth(){
        String uri = AppSettings.getURLs(this) + "api/rpt/items/topsales?end_date=" + monthEnd + "&start_date=" + dayTOF;
        VolleyBackgroundTaskGet.syncTopTenSales(this,uri,this);
    }

    private void bottomTenMonth(){
        String url = AppSettings.getURLs(this) + "api/rpt/items/bottomsales?end_date=" + monthEnd + "&start_date=" + dayTOF;
        VolleyBackgroundTaskGet.syncBottomTenSales(this,url,this);
    }

    /*private void filterThree(List<TopTen> toTenList){
        Double[] array = new Double[toTenList.size()];
        int i = 0;
        for(TopTen topTen : toTenList){
            array[i] = topTen.getQty();
            i++;
        }
        Log.e("topTen",""+ Arrays.toString(array));
    }*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public int getSkeletonRowCount(Context context) {
        int pxHeight = getDeviceHeight(context);
        int skeletonRowHeight = (int) getResources()
                .getDimension(R.dimen.row_layout_height); //converts to pixel
        return (int) Math.ceil(pxHeight / skeletonRowHeight);
    }
    public int getDeviceHeight(Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return metrics.heightPixels;
    }


    public void showSkeleton(boolean show) {

        if (show) {

            skeletonLayout.removeAllViews();

            int skeletonRows = getSkeletonRowCount(getApplicationContext());
            //int skeletonRows =  recyclerViewitemList.getMeasuredHeight();
            for (int i = 0; i <= skeletonRows; i++) {
                ViewGroup rowLayout = (ViewGroup) inflater
                        .inflate(R.layout.refresh_dashboard_single, null);
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
