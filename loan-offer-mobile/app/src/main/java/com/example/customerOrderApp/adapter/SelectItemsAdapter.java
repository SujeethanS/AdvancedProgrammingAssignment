package com.example.customerOrderApp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.customerOrderApp.R;
import com.example.customerOrderApp.pojo.Item;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SelectItemsAdapter extends RecyclerView.Adapter<SelectItemsAdapter.ViewHolder> {


    Context mContext;
    LayoutInflater inflater;
    private List<Item> itemsList = new ArrayList<>();
    DecimalFormat df = new DecimalFormat("#,##0.00");

    private SelectItemsAdapter.OnLoadMoreListener loadMoreListener;
    private boolean isLoading = false, isMoreDataAvailable = true;


    public  SelectItemsAdapter(Context context , List<Item> itemsList){
        mContext = context;
        this.itemsList = itemsList;
        inflater = LayoutInflater.from(mContext);
    }

    public void SelectInvoiceAdapter(Context context,List<Item>list){
        this.mContext = context;
        this.itemsList = list;
    }


    @Override
    public SelectItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        /*View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_adapter_single, parent, false);
        final ViewHolder mViewHolder = new ViewHolder(itemView);
        return mViewHolder;*/
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.multi_bid_select_single,parent,false);
        //final ViewHolder mViewHolder = new ViewHolder(view);
        return new SelectItemsAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(SelectItemsAdapter.ViewHolder holder, int position) {

        if(position>=getItemCount()-1 && isMoreDataAvailable && !isLoading && loadMoreListener!=null){

            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        Item item = itemsList.get(position);
        holder.itemQty.setText(String.valueOf(item.getItemQty()));
        holder.itemPrice.setText(String.valueOf(df.format(item.getItemSellingPrice())));
        holder.itemBID.setText(String.valueOf(item.getBid()));
        holder.itemExDate.setText(item.getItemExpiryDate());
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemQty,itemPrice,itemBID,itemExDate;
        public ViewHolder(View itemView) {
            super(itemView);

            itemQty = itemView.findViewById(R.id.single_Qty);
            itemPrice = itemView.findViewById(R.id.single_price);
            itemBID = itemView.findViewById(R.id.single_bid);
            itemExDate = itemView.findViewById(R.id.single_ExDate);
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

    public void setLoadMoreListener(SelectItemsAdapter.OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}
