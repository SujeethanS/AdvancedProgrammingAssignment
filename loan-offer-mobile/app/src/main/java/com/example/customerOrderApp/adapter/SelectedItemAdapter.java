package com.example.customerOrderApp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.customerOrderApp.pojo.OrderDetails;
import com.example.customerOrderApp.R;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SelectedItemAdapter extends RecyclerView.Adapter<SelectedItemAdapter.ViewHolder> {

    List<OrderDetails> selectItemList = new ArrayList<OrderDetails>();

    Context activity;

    DecimalFormat df = new DecimalFormat("#,##0.00");

    public SelectedItemAdapter(ArrayList<OrderDetails> selectItemList) {
            this.selectItemList=selectItemList;
    }

    public SelectedItemAdapter(Context activity, List<OrderDetails> selectIdList) {
        this.activity = activity;
        this.selectItemList = selectIdList;
    }

    public List<OrderDetails> getSelectItemList() {
        return selectItemList;
    }

    @Override
    public SelectedItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Log.d("SelectedItemAdapter ","SelectedItemAdapter ");
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_orderlist_view,parent,false);
        final ViewHolder mViewHolder = new ViewHolder(view);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.selectQty.setText(String.valueOf(selectItemList.get(position).getOrderDetailsItemQty()));
        holder.selectItemName.setText(selectItemList.get(position).getOrderDetailsItemName());
        Double doublePriceString = selectItemList.get(position).getItemSellingPriceTotal();
        holder.selectPrice.setText(String.valueOf(df.format(doublePriceString)));
    }

    @Override
    public int getItemCount() {
        return selectItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView selectQty,selectItemName,selectPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            selectQty=(TextView)itemView.findViewById(R.id.selected_item_Qty_textView);
            selectItemName=(TextView)itemView.findViewById(R.id.selected_itemName_textVIew);
            selectPrice=(TextView)itemView.findViewById(R.id.selected_itemPrice_textVIew);

           // sumTOtal = (TextView) itemView.findViewById(R.id.textView_totalSum);
        }
    }
/*    public void getSumofAllitems(){
        int total_sum=0;
        for(int i=0;i<selectItemList.size();i++){
            OrderDetailsPojo orderDetailsPojo=selectItemList.get(i);
            Double price=orderDetailsPojo.getOrderDetailsItemPrice();//getPrice() is a getter method in your POJO class.
            total_sum+= price;
        }
        sumTOtal.setText(total_sum+"");
    }*/

}
