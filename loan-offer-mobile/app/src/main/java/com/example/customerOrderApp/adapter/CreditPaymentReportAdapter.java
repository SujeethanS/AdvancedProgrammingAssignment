package com.example.customerOrderApp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerOrderApp.R;
import com.example.customerOrderApp.helper.ReadableDateFormat;
import com.example.customerOrderApp.pojo.Payment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CreditPaymentReportAdapter extends RecyclerView.Adapter<CreditPaymentReportAdapter.ViewHolder> {

    private List<Payment> invoicePaymentList = new ArrayList<>();
    private Context context;
    private String identifier;
    private int color;
    private DecimalFormat df = new DecimalFormat("#,##0.00");
    private DecimalFormat dFormat = new DecimalFormat("#,##0.0");

    private OnLoadMoreListener loadMoreListener;
    private boolean isLoading = false, isMoreDataAvailable = true;

    public CreditPaymentReportAdapter(Context activity, List<Payment> paymentList, String type) {
        this.context = activity;
        this.invoicePaymentList = paymentList;
        this.identifier = type;
    }

    @NonNull
    @Override
    public CreditPaymentReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.credit_payment_report_single,viewGroup,false);
        return new CreditPaymentReportAdapter.ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull CreditPaymentReportAdapter.ViewHolder viewHolder, int position) {

        if(position>=getItemCount()-1 && isMoreDataAvailable && !isLoading && loadMoreListener!=null){
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        String date = ReadableDateFormat.humanReadableFormat(invoicePaymentList.get(position).getPaymentDate());
        viewHolder.payInvoiceNumber.setText(String.valueOf(invoicePaymentList.get(position).getPayment_id()));
        Double total = invoicePaymentList.get(position).getPayment_total();
        viewHolder.payTotal.setText(df.format( total));
        viewHolder.payDate.setText(date);
        viewHolder.payMethod.setText(selectPaymentMethod(invoicePaymentList.get(position).getPayment_method()));

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView payInvoiceNumber,payDate,payMethod,payTotal;
        public ViewHolder(View itemView) {
            super(itemView);
            payInvoiceNumber= itemView.findViewById(R.id.invoice_number);
            payTotal= itemView.findViewById(R.id.receive_amount);
            payDate= itemView.findViewById(R.id.pay_date);
            payMethod= itemView.findViewById(R.id.customer_name);

        }
    }

    public void setMoreDataAvailable() {
        isMoreDataAvailable = false;
    }

    public void setMoreDataAvailableTrue() {
        isMoreDataAvailable = true;
    }

    public void notifyDataChanged(){
        notifyDataSetChanged();
        isLoading = false;
    }


    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
    @Override
    public int getItemCount() {
        return invoicePaymentList.size();
    }

    private String selectPaymentMethod(int type){
        String method = "";
        switch (type){
            case 1001:
                method = "Cash Payment";
                break;
            case 1002:
                method = "Cheque Payment";
                break;
            case 1003:
                method = "Card Payment";
                break;
                default:
                    method = "Cash Payment";
        }
        return method;
    }

}
