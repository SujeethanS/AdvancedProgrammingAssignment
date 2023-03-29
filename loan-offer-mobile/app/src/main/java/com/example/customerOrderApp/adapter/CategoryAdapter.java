package com.example.customerOrderApp.adapter;

import android.content.Context;
import android.graphics.Color;

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


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    Context mContext;
    private String [] id = {"S001","S002","S003","S004","S005","S006","S007","S008","S009","S010","S011","S012"};
    private List <String> category  = new ArrayList();
    private List<String> arrayList ;
    private int color;
    private LayoutInflater mInflater;

    DecimalFormat df = new DecimalFormat("#,##0.00");

    private OnGridChangeListener onGridChangeListener;

    public CategoryAdapter(ArrayList<String> selectItemList) {
        this.category=selectItemList;
    }

    public CategoryAdapter(Context c, List<String> categoryList,OnGridChangeListener onGridChangeListener ) {
        mContext=c;
        this.onGridChangeListener = onGridChangeListener;
        mInflater = LayoutInflater.from(c);
        this.category = categoryList;
        arrayList = new ArrayList<>();
        this.arrayList.addAll(categoryList);
    }

    public List<String> getSelectCategoryList() {
        return category;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // Log.d("SelectedItemAdapter ","SelectedItemAdapter ");
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_single_grid,parent,false);
        final ViewHolder mViewHolder = new ViewHolder(view);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        this.arrayList.clear();
        this.arrayList.addAll(category);

        holder.txtId.setText(category.get(position));

        int colorPos = getPosition(position);


        switch (colorPos) {
            case 0:
                holder.cardView.setCardBackgroundColor(Color.parseColor("#ebdef0"));
                color = Color.parseColor("#ebdef0");
                break;
            case 1:
                holder.cardView.setCardBackgroundColor(Color.parseColor("#d6eaf8"));
                color = Color.parseColor("#d6eaf8");
                break;
            case 2:
                holder.cardView.setCardBackgroundColor(Color.parseColor("#bddfdb"));
                color = Color.parseColor("#bddfdb");
                break;
            case 3:
                holder.cardView.setCardBackgroundColor(Color.parseColor("#c8e6c9"));
                color = Color.parseColor("#c8e6c9");
                break;
            case 4:
                holder.cardView.setCardBackgroundColor(Color.parseColor("#dcedc8"));
                color = Color.parseColor("#dcedc8");
                break;
            case 5:
                holder.cardView.setCardBackgroundColor(Color.parseColor("#f0f4c3"));
                color = Color.parseColor("#f0f4c3");
                break;
            case 6:
                holder.cardView.setCardBackgroundColor(Color.parseColor("#fff9c4"));
                color = Color.parseColor("#fff9c4");
                break;
            case 7:
                holder.cardView.setCardBackgroundColor(Color.parseColor("#ffecb3"));
                color = Color.parseColor("#ffecb3");
                break;
            case 8:
                holder.cardView.setCardBackgroundColor(Color.parseColor("#ffe0b2"));
                color = Color.parseColor("#ffe0b2");
                break;
        }

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Log.e("color_code", " " + color + " " + position);
                    onGridChangeListener.OnGridChange(color, position);
                }
            });

    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtId;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            txtId=itemView.findViewById(R.id.grid_textCategory);
            cardView = itemView.findViewById(R.id.category_root);
        }
    }

    public void filter(String charText) {
       // Log.e("VFGHHTFGHH",charText);
        charText = charText.toLowerCase(Locale.getDefault());
        category.clear();
        if (charText.length() == 0) {
            category.addAll(arrayList);
        } else {

            try{
                int number = Integer.parseInt(charText);

                for (String item : arrayList) {
                    if (item.equalsIgnoreCase(String.valueOf(number))) {
                        category.add(item);
                    }
                }

            }catch (NumberFormatException e){
                //Log.e("VFGHHTFGHH",""+arrayList.size());
                for (String item : arrayList) {
                    if (item.toLowerCase(Locale.getDefault()).contains(charText)) {
                        category.add(item);
                    }
                }

            }
        }
        this.notifyDataSetChanged();
    }

    private int getPosition(int position){

        for(int i=position ; i <= position ; i++){
            if(i > 8 ){
                position = i%9;
            }
        }
        return position;
    }

    public interface OnGridChangeListener {
        void OnGridChange(int colorCode, int position);
    }
}


/*public class CategoryAdapter extends BaseAdapter {

    Context mContext;
    private String [] id = {"S001","S002","S003","S004","S005","S006","S007","S008","S009","S010","S011","S012"};
    private List <String> category  = new ArrayList();
    private List<String> arrayList ;

    private LayoutInflater mInflater;

    public CategoryAdapter(Context c, List<String> categoryList)
    {
        mContext=c;
        mInflater = LayoutInflater.from(c);
        this.category = categoryList;
        arrayList = new ArrayList<>();
        this.arrayList.addAll(categoryList);

    }
    public int getCount()
    {
        return category.size();
    }
    public Object getItem(int position)
    {
        return position;
    }
    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        this.arrayList.clear();
        this.arrayList.addAll(category);

        ViewHolder holder=null;
        if(convertView==null)
        {
            convertView = mInflater.inflate(R.layout.category_single_grid,parent,false);

            holder = new ViewHolder();
            holder.txtId=(TextView)convertView.findViewById(R.id.grid_textCategory);
            holder.cardView = convertView.findViewById(R.id.category_root);


                convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        //Item item = (Item)category.get(position);
        Log.e("12121212122"," " +category.get(position) );
        holder.txtId.setText(category.get(position));

        int colorPos = getPosition(position);


            switch (colorPos) {
                case 0:
                    holder.cardView.setCardBackgroundColor(Color.parseColor("#ebdef0"));
                    break;
                case 1:
                    holder.cardView.setCardBackgroundColor(Color.parseColor("#d6eaf8"));
                    break;
                case 2:
                    holder.cardView.setCardBackgroundColor(Color.parseColor("#bddfdb"));
                    break;
                case 3:
                    holder.cardView.setCardBackgroundColor(Color.parseColor("#c8e6c9"));
                    break;
                case 4:
                    holder.cardView.setCardBackgroundColor(Color.parseColor("#dcedc8"));
                    break;
                case 5:
                    holder.cardView.setCardBackgroundColor(Color.parseColor("#f0f4c3"));
                    break;
                case 6:
                    holder.cardView.setCardBackgroundColor(Color.parseColor("#fff9c4"));
                    break;
                case 7:
                    holder.cardView.setCardBackgroundColor(Color.parseColor("#ffecb3"));
                    break;
                case 8:
                    holder.cardView.setCardBackgroundColor(Color.parseColor("#ffe0b2"));
                    break;
            }


        return convertView;
    }

    static class ViewHolder
    {
        TextView txtId;
        CardView cardView;

    }

    public void filter(String charText) {
        Log.e("VFGHHTFGHH",charText);
        charText = charText.toLowerCase(Locale.getDefault());
        category.clear();
        if (charText.length() == 0) {
            category.addAll(arrayList);
        } else {

            try{
                int number = Integer.parseInt(charText);

                for (String item : arrayList) {
                    if (item.equalsIgnoreCase(String.valueOf(number))) {
                        category.add(item);
                    }
                }

            }catch (NumberFormatException e){
                Log.e("VFGHHTFGHH",""+arrayList.size());
                for (String item : arrayList) {
                    if (item.toLowerCase(Locale.getDefault()).contains(charText)) {
                        category.add(item);
                    }
                }

            }
        }
        this.notifyDataSetChanged();
    }

    private int getPosition(int position){

        for(int i=position ; i <= position ; i++){
            if(i > 8 ){
                position = i%9;
            }
        }
        return position;
    }

}*/

