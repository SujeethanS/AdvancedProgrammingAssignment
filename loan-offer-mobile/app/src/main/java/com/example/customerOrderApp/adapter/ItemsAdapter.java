package com.example.customerOrderApp.adapter;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.customerOrderApp.R;
import com.example.customerOrderApp.helper.AppSettings;
import com.example.customerOrderApp.pojo.Item;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    Context mContext;
    Activity activity;
    DecimalFormat df = new DecimalFormat("#,###,##0.00");
    private List<Item> itemList  = new ArrayList();
    private ArrayList<Item> arrayList;// = new ArrayList<>();

    private ItemsAdapter.OnLoadMoreListener loadMoreListener;
    private boolean isLoading = false, isMoreDataAvailable = true;

    public ItemsAdapter(ArrayList<Item> selectItemList) {
        this.itemList=selectItemList;
    }

    public ItemsAdapter(FragmentActivity c, List<Item> itemsList)
    {
        this.activity=c;
        this.itemList = itemsList;
        arrayList = new ArrayList<>();
        this.arrayList.addAll(itemList);

    }

    public List<Item> getSelectCategoryList() {
        return itemList;
    }

    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_single_grid,parent,false);
        return new ItemsAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(position>=getItemCount()-1 && isMoreDataAvailable && !isLoading && loadMoreListener!=null){
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        this.arrayList.clear();
        this.arrayList.addAll(itemList);

        Item item = (Item)itemList.get(position);

        String name = null;
        if(item.getItemName().length() > 11 ) {
            name = item.getItemName().substring(0, 10);
            holder.txtName.setText(name+"...");
        }else {
            name = item.getItemName();
            holder.txtName.setText(name);
        }


        String itemPrice = null;
        if(!AppSettings.getCountryCode(activity).equalsIgnoreCase("Srilanka")) {
            itemPrice = ("₹ " + df.format(item.getItemSellingPrice()));
        }
        else {
            itemPrice = ("Rs " + df.format(item.getItemSellingPrice()));
        }
        holder.txtPrice.setText(itemPrice);


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtItemNo,txtName,txtPrice,txtQty,itemTemp;

        RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            txtItemNo = itemView.findViewById(R.id.grid_item_number);
            txtName = itemView.findViewById(R.id.grid_text_items);
            txtPrice = itemView.findViewById(R.id.grid_text_item_price);
            txtQty = itemView.findViewById(R.id.grid_text_item_qty);
            relativeLayout = itemView.findViewById(R.id.grid_item_image);
            itemTemp = itemView.findViewById(R.id.grid_item_tempT);
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

    public void setLoadMoreListener(ItemsAdapter.OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}

