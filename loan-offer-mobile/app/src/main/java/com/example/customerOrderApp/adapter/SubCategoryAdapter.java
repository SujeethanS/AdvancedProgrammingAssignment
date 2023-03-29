package com.example.customerOrderApp.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerOrderApp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {

    Context mContext;
    private List<String> subCategoryList  = new ArrayList();
    private List<String> arrayList;
    private LayoutInflater mInflater;

    DecimalFormat df = new DecimalFormat("#,##0.00");

    public SubCategoryAdapter(ArrayList<String> selectItemList) {
        this.subCategoryList=selectItemList;
    }

    public SubCategoryAdapter(Context c, List<String> subCategoryList) {
        mContext = c;
        mInflater = LayoutInflater.from(c);
        this.subCategoryList = subCategoryList;
        arrayList = new ArrayList<>();
        this.arrayList.addAll(subCategoryList);

    }

    public List<String> getSelectCategoryList() {
        return subCategoryList;
    }

    @Override
    public SubCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // Log.d("SelectedItemAdapter ","SelectedItemAdapter ");
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategory_single_grid,parent,false);
        final ViewHolder mViewHolder = new ViewHolder(view);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        this.arrayList.clear();
        this.arrayList.addAll(subCategoryList);

        holder.txtId.setText(subCategoryList.get(position));

    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtId;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            txtId=itemView.findViewById(R.id.grid_textSubCategory);
            cardView = itemView.findViewById(R.id.subcategory_root);
        }
    }

    public void filter(String charText) {
       // Log.e("VFGHHTFGHH",charText);
        charText = charText.toLowerCase(Locale.getDefault());
        subCategoryList.clear();
        if (charText.length() == 0) {
            subCategoryList.addAll(arrayList);
        } else {

            try{
                int number = Integer.parseInt(charText);

                for (String item : arrayList) {
                    if (item.equalsIgnoreCase(String.valueOf(number))) {
                        subCategoryList.add(item);
                    }
                }

            }catch (NumberFormatException e){
              //  Log.e("VFGHHTFGHH",""+arrayList.size());
                for (String item : arrayList) {
                    if (item.toLowerCase(Locale.getDefault()).contains(charText)) {
                        subCategoryList.add(item);
                    }
                }

            }
        }
        this.notifyDataSetChanged();
    }


}


/*public class SubCategoryAdapter extends BaseAdapter {

    Context mContext;
    private List<String> subCategoryList  = new ArrayList();
    private List<String> arrayList;

    private LayoutInflater mInflater;

    public SubCategoryAdapter(Context c, List<String> subCategoryList)
    {
        mContext=c;
        mInflater = LayoutInflater.from(c);
        this.subCategoryList = subCategoryList;
        arrayList = new ArrayList<>();
        this.arrayList.addAll(subCategoryList);

    }
    public int getCount()
    {
        return subCategoryList.size();
    }
    public Object getItem(int position)
    {
        return position;
    }
    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        this.arrayList.clear();
        this.arrayList.addAll(subCategoryList);

        CategoryAdapter.ViewHolder holder=null;
        if(convertView==null)
        {
            convertView = mInflater.inflate(R.layout.subcategory_single_grid,parent,false);
            holder = new CategoryAdapter.ViewHolder();
            holder.txtId=(TextView)convertView.findViewById(R.id.grid_textSubCategory);

                convertView.setTag(holder);

        }
        else
        {
            holder = (CategoryAdapter.ViewHolder) convertView.getTag();
        }
       // Item item = (Item)subCategoryList.get(position);

        holder.txtId.setText(subCategoryList.get(position));

        return convertView;
    }
    static class ViewHolder
    {
        TextView txtId;

    }

    public void filter(String charText) {
        Log.e("VFGHHTFGHH",charText);
        charText = charText.toLowerCase(Locale.getDefault());
        subCategoryList.clear();
        if (charText.length() == 0) {
            subCategoryList.addAll(arrayList);
        } else {

            try{
                int number = Integer.parseInt(charText);

                for (String item : arrayList) {
                    if (item.equalsIgnoreCase(String.valueOf(number))) {
                        subCategoryList.add(item);
                    }
                }

            }catch (NumberFormatException e){
                Log.e("VFGHHTFGHH",""+arrayList.size());
                for (String item : arrayList) {
                    if (item.toLowerCase(Locale.getDefault()).contains(charText)) {
                        subCategoryList.add(item);
                    }
                }

            }
        }
        this.notifyDataSetChanged();
    }
}*/

