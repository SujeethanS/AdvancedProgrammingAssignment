package com.example.customerOrderApp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerOrderApp.R;
import com.example.customerOrderApp.helper.ReadableDateFormat;
import com.example.customerOrderApp.pojo.Order;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SalesReceiptAndInvoiceAdapter extends RecyclerView.Adapter<SalesReceiptAndInvoiceAdapter.ViewHolder> {


    List<Order> salesAndInvoiceList = new ArrayList<Order>();
    Activity activity;
    DecimalFormat df = new DecimalFormat("#,##0.00");

    private SalesReceiptAndInvoiceAdapter.OnLoadMoreListener loadMoreListener;
    private boolean isLoading = false, isMoreDataAvailable = true;


    public SalesReceiptAndInvoiceAdapter(ArrayList<Order> reportOrderList) {
        this.salesAndInvoiceList=reportOrderList;
    }

    public SalesReceiptAndInvoiceAdapter(FragmentActivity activity, List<Order> reportOrderList) {
        this.activity = activity;
        this.salesAndInvoiceList = reportOrderList;
    }

    public List<Order> getReportOrderList() {
        return salesAndInvoiceList;
    }

    @Override
    public SalesReceiptAndInvoiceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.report_order_list_single,parent,false);
        return new SalesReceiptAndInvoiceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SalesReceiptAndInvoiceAdapter.ViewHolder holder, int position) {

        if(position>=getItemCount()-1 && isMoreDataAvailable && !isLoading && loadMoreListener!=null){

            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        Order order = salesAndInvoiceList.get(position);
        String date = ReadableDateFormat.humanReadableFormat(order.getOrderDate());
        holder.order_Id.setText(String.valueOf(order.getOrderId()));
        holder.order_Date.setText(date);

        if (order.getOrderSyncStatus() == 0) {
            holder.order_ststus.setText("Sales");
        }else {
            holder.order_ststus.setText("Credit Sales");
        }

        Double doublePrice = order.getOrderTotal();
        holder.order_Total.setText(String.valueOf(df.format( doublePrice)));
    }

    @Override
    public int getItemCount() {
        return salesAndInvoiceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView order_Id,order_Date,order_ststus,order_Total;
        public ViewHolder(View itemView) {
            super(itemView);
            order_Id= itemView.findViewById(R.id.orderId);
            order_Date= itemView.findViewById(R.id.orderDate);
            order_ststus= itemView.findViewById(R.id.orderStatus);
            order_Total= itemView.findViewById(R.id.orderTotal);

        }
    }

    public void setMoreDataAvailable() {
        isMoreDataAvailable = false;
    }

    public void setMoreDataAvailableTrue() {
        isMoreDataAvailable = true;
    }

    /* notifyDataSetChanged is final method so we can't override it
         call adapter.notifyDataChanged(); after update the list
         */
    public void notifyDataChanged(){
        notifyDataSetChanged();
        isLoading = false;
    }


    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setLoadMoreListener(SalesReceiptAndInvoiceAdapter.OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

}
