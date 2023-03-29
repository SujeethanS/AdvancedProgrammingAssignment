package com.example.customerOrderApp.SalesReport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.customerOrderApp.R;
import com.example.customerOrderApp.pojo.OrderDetails;

import java.text.DecimalFormat;
import java.util.List;

public class PaymentDetailAdapter extends ArrayAdapter<OrderDetails> {

    private List<OrderDetails> dataSet;
    Context mContext;
    private DecimalFormat df = new DecimalFormat("#,##0.00");

    public PaymentDetailAdapter(@NonNull Context context, List<OrderDetails> list) {
        super(context, 0, list);

        dataSet = list;
        mContext = context;

    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;
        if (listItem == null)

            listItem = LayoutInflater.from(mContext).inflate(R.layout.payment_list, parent, false);
        OrderDetails orderDetails = dataSet.get(position);

        TextView itemNo = listItem.findViewById(R.id.itemNumber);
        itemNo.setText(orderDetails.getOrderDetailsItemNumber());

        TextView itemDesc = listItem.findViewById(R.id.itemDesc);
        itemDesc.setText(orderDetails.getOrderDetailsItemName());

        TextView itemQty = listItem.findViewById(R.id.itemQty);
        itemQty.setText(orderDetails.getExpiryDate());

        TextView itemPrice = listItem.findViewById(R.id.itemPrice);

        itemPrice.setText(df.format(orderDetails.getOrderDetailsItemPrice()));

        return listItem;
    }


}
