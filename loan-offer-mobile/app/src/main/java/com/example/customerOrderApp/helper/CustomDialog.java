package com.example.customerOrderApp.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerOrderApp.R;
import com.example.customerOrderApp.adapter.SelectItemsAdapter;
import com.example.customerOrderApp.pojo.Item;
import com.example.customerOrderApp.pojo.OrderDetails;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomDialog {


    public static ProgressDialog progress;

    public interface ModifyOrderDetailsQty {
        void modifyOrderDetailsQty(OrderDetails orderDetails, int position);
    }

    public interface MultiBIDItmsDelegate {
        void returnItemsSelectList(List<Item> items, int position);
    }

    private static ModifyOrderDetailsQty modifyOrderDetailsQtyDelegate;

    private MultiBIDItmsDelegate multiBIDItmsDelegate;

    private static int discountType = 1;

    @SuppressLint("ClickableViewAccessibility")
    public static void ModifyOrderDetailItemQtyPopup(final Context context, ModifyOrderDetailsQty modifyOrderDetailsQty, OrderDetails orderDetails, int listPosition) {


        modifyOrderDetailsQtyDelegate = modifyOrderDetailsQty;
        int position ;
        DecimalFormat df = new DecimalFormat("##0.00");
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        dialog.setContentView(R.layout.quantity_popup_layout);
        dialog.show();

        final TextView itemNo = dialog.findViewById(R.id.user_id_editText);
        final TextView itemName = dialog.findViewById(R.id.user_name_editText);
        itemName.setSelected(true);
        final EditText itemQty = dialog.findViewById(R.id.user_qty_editText);
        final EditText itemPrice = dialog.findViewById(R.id.user_price_editText);
        String edit = AppSettings.getEditPriceAllow(context);
        itemPrice.setEnabled(edit.equalsIgnoreCase("Y"));

        final EditText itemDis = dialog.findViewById(R.id.user_dis_editText);
        String allow = AppSettings.getAllowEditItemDis(context);
        itemDis.setEnabled(allow.equalsIgnoreCase("Y"));

        itemPrice.setOnTouchListener((v, event) -> {
            itemPrice.setText("");
            itemPrice.setTextSize(25);
            itemDis.setTextSize(16);
            itemQty.setTextSize(16);
            return false;
        });

        itemDis.setOnTouchListener((v, event) -> {
            itemDis.setText("");
            itemDis.setTextSize(25);
            itemPrice.setTextSize(16);
            itemQty.setTextSize(16);
            return false;
        });

        itemQty.setOnTouchListener((v, event) -> {
            itemQty.setText("");
            itemQty.setTextSize(25);
            itemPrice.setTextSize(16);
            itemDis.setTextSize(16);
            return false;
        });


        final Button buttonOK = dialog.findViewById(R.id.button_Save_qty);
        final Button cancel = dialog.findViewById(R.id.button_Cancel_qty);
        final ImageButton close = dialog.findViewById(R.id.imgBtn_close);

        String id = orderDetails.getOrderDetailsItemNumber();
        String name = orderDetails.getOrderDetailsItemName();
        String actualQty = String.valueOf(orderDetails.getOrderDetailsItemQty());
        String actualSellingPrice = String.valueOf(df.format(orderDetails.getOrderDetailsItemSellingPrice()));

        String actualDiscount = String.valueOf(df.format(orderDetails.getOrderDetailsItemDiscount()));

        itemNo.setText(id);
        itemName.setText(name);
        itemQty.setText(actualQty);
        itemPrice.setText(actualSellingPrice);
        itemDis.setText(actualDiscount);
        position = listPosition;

        int finalPosition = position;


        buttonOK.setOnClickListener(v -> {

            String readQty = itemQty.getText().toString().trim();

            String readPrice;
            if(itemPrice.getText().toString().trim().length() == 0){
                readPrice = "0.0";
            }else {
                readPrice = itemPrice.getText().toString().trim();
            }

            String readItemDis;
            if(itemDis.getText().toString().trim().length() == 0){
                readItemDis = "0.0";
            }else {
                readItemDis  = itemDis.getText().toString().trim();
            }

            double editItemDiscount;
            Double editItemPrice;
            Double editItemQty;

            Double actualPrice;

            if (!readQty.equals(".") && !readItemDis.equals(".") && itemQty.getText().toString().trim().length() != 0 ) {

                editItemPrice = Double.parseDouble(readPrice);
                editItemQty = Double.parseDouble(readQty);
                actualPrice = Double.parseDouble(actualSellingPrice);

                if(itemDis.getText().toString().trim().length() == 0 && Double.parseDouble(readItemDis) == 0.0 ){
                    editItemDiscount = 0.0 ;

                    calculateDiscountFactor(editItemDiscount,editItemPrice,editItemQty,actualPrice,orderDetails,context,finalPosition);
                    dialog.dismiss();

                }else {

                    if(orderDetails.getOrderDetailsItemMaxDiscount() != 0.0 ){
                        editItemDiscount = Double.parseDouble(readItemDis);

                        if(editItemDiscount < orderDetails.getOrderDetailsItemMaxDiscount()){
                            calculateDiscountFactor(editItemDiscount,editItemPrice,editItemQty,actualPrice,orderDetails,context,finalPosition);
                            dialog.dismiss();
                        }else{
                            ToastMessageHelper.customErrToast((Activity) context,"Greater than max Discount");
                        }

                    }else{
                        editItemDiscount = Double.parseDouble(readItemDis);

                        if(editItemDiscount <= orderDetails.getOrderDetailsItemSellingPrice()){// because zero price sales
                            calculateDiscountFactor(editItemDiscount,editItemPrice,editItemQty,actualPrice,orderDetails,context,finalPosition);
                            dialog.dismiss();
                        }else{
                            ToastMessageHelper.customErrToast((Activity) context,"Greater than item Price");
                        }
                    }

                }
            }else {
                ToastMessageHelper.customErrToast((Activity) context,"Enter Correct Value");
            }

        });

        cancel.setOnClickListener(view -> dialog.dismiss());

        close.setOnClickListener(view -> dialog.dismiss());
    }

    //**********************************************************************************************
    public interface OrderDiscountAmount {
        void orderDiscountAmount(Double discount, int type);
    }

    static OrderDiscountAmount orderDiscountAmountDelegate;

    @SuppressLint({"ClickableViewAccessibility", "NonConstantResourceId"})
    public static void OrderDiscountAmountPopup(final Context context, OrderDiscountAmount orderDiscountAmount) {
        orderDiscountAmountDelegate = orderDiscountAmount;
        discountType = 1;

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        dialog.setContentView(R.layout.discount_popup_layout);
        dialog.show();

        final EditText itemDis = dialog.findViewById(R.id.user_dis_editText);
        itemDis.setText("0.0");

        itemDis.setOnTouchListener((v, event) -> {
            itemDis.setText("");
            itemDis.setTextSize(25);
            return false;
        });

        final RadioGroup radioGroup = dialog.findViewById(R.id.radio_group);

        final Button buttonOK = dialog.findViewById(R.id.button_Save_qty);
        final Button cancel = dialog.findViewById(R.id.button_Cancel_qty);
        final ImageButton close = dialog.findViewById(R.id.imgBtn_close);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {

            switch (checkedId){
                case R.id.percentage_radio_button:
                    discountType = 0;

                    break;
                case R.id.amount_radio_button:
                    discountType = 1;
                    break;
            }
        });

        buttonOK.setOnClickListener(view -> {

            if(itemDis.getText().toString().length() != 0 && !itemDis.getText().toString().equals(".")){

                orderDiscountAmountDelegate.orderDiscountAmount(Double.parseDouble(itemDis.getText().toString()),discountType);
                dialog.dismiss();
            }else{
                ToastMessageHelper.customErrToast((Activity) context,"Enter Correct Value");
            }

        });
        cancel.setOnClickListener(view -> {
            if(itemDis.getText().toString().trim().length() == 0){
                orderDiscountAmountDelegate.orderDiscountAmount(Double.parseDouble("0.0"),discountType);
            }
            dialog.dismiss();
        });

        close.setOnClickListener(view -> {
            if(itemDis.getText().toString().trim().length() == 0){
                orderDiscountAmountDelegate.orderDiscountAmount(Double.parseDouble("0.0"),discountType);
            }
            dialog.dismiss();
        });

    }

    public interface EditItemPrice {
        void editItemPrice(Item item);
    }

    static EditItemPrice editItemPriceDelegate;

    @SuppressLint("ClickableViewAccessibility")
    public static void EditItemPricePopup(final Context context, EditItemPrice editItemPrice, Item item) {
        editItemPriceDelegate = editItemPrice;

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        dialog.setContentView(R.layout.edit_price_popup);
        dialog.show();

        final EditText price = dialog.findViewById(R.id.price_editText);
        //final TextView priceHead = dialog.findViewById(R.id.price_textView);

        final Button buttonOK = dialog.findViewById(R.id.button_Save_price);
        final Button cancel = dialog.findViewById(R.id.button_Cancel_price);
        final ImageButton close = dialog.findViewById(R.id.imgBtn_close);

        Double actualPrice = item.getItemSellingPrice();
        price.setText(String.valueOf(actualPrice));
        price.setOnTouchListener((v, event) -> {
            price.setText("");
            price.setTextSize(25);
            return false;
        });

        buttonOK.setOnClickListener(view -> {

            String editPrice = price.getText().toString().trim();

            if(editPrice.length() != 0 && !editPrice.equals(".")){

                item.setItemSellingPrice(Double.parseDouble(editPrice));
                editItemPriceDelegate.editItemPrice(item);
                dialog.dismiss();
            }else{
                ToastMessageHelper.customErrToast((Activity) context,"Enter Correct Value");
            }

        });
        cancel.setOnClickListener(view -> dialog.dismiss());

        close.setOnClickListener(view -> dialog.dismiss());

    }

    private static void calculateDiscountFactor( Double editedItemDis,Double editPrice,Double itemQty,Double actualPrice,OrderDetails orderDetails,Context context,int finalPosition){

        if(itemQty != 0 ){//&& editPrice != 0

            if(editedItemDis <= editPrice) {
                double editedItemPrice;

                if(!AppSettings.getAllowEditItemDis(context).equalsIgnoreCase("Y")) {
                    editedItemPrice = (editPrice - editedItemDis);
                }else {
                    editedItemPrice = (actualPrice -editedItemDis );
                }
                //TODO discount work pending
                Log.d("editedItemQty", "" + editedItemDis);
                orderDetails.setOrderDetailsItemQty(itemQty);
                orderDetails.setOrderDetailsItemDiscount(editedItemDis);
                orderDetails.setItemSellingPriceTotal(itemQty * editedItemPrice);
                orderDetails.setOrderDetailsItemPrice(editedItemPrice );
                orderDetails.setOrderDetailsItemSellingPrice(actualPrice);
                modifyOrderDetailsQtyDelegate.modifyOrderDetailsQty(orderDetails, finalPosition);
            }else {
                ToastMessageHelper.customErrToast((Activity)context,"Less than item discount");
            }

        }else {
            ToastMessageHelper.customErrToast((Activity) context,"Enter Correct Value");
        }

    }

    public void customProgressBar(Context context,String title,String mgs,String styleType){
        progress=new ProgressDialog(context);
        progress.setTitle(title);
        progress.setMessage(mgs);
        if(styleType.equals("H")) {//H or S

            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setIndeterminate(true);
            progress.setProgress(0);
        }else {
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }

        progress.setCancelable(false);
        progress.show();

    }

    public void closeProgressBar(){

        if(progress!=null){
            progress.setProgress(100);
            progress.dismiss();
        }
    }

    @SuppressLint({"ClickableViewAccessibility", "NotifyDataSetChanged"})
    public void multiBIDItemDialog(final Context context, List<Item> itemList, MultiBIDItmsDelegate mi) {

        multiBIDItmsDelegate = mi;
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.multi_bid_select_popup);

        Objects.requireNonNull(dialog.getWindow()).setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);

        List<Item> itemListPopup = new ArrayList<>(itemList);

        ImageButton imgBtnClose = dialog.findViewById(R.id.payment_imgBtn_close);
        RecyclerView recyclerViewSelectItems = dialog.findViewById(R.id.selectPopup_recyclerView);

        SelectItemsAdapter selectInvoiceAdapter ;
        selectInvoiceAdapter = new SelectItemsAdapter(context,itemListPopup);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context);
        recyclerViewSelectItems.setLayoutManager(layoutManager);
        recyclerViewSelectItems.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSelectItems.setAdapter(selectInvoiceAdapter);
        selectInvoiceAdapter.notifyDataSetChanged();

        recyclerViewSelectItems.addOnItemTouchListener(new RecyclerItemClickListener(context, recyclerViewSelectItems, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                multiBIDItmsDelegate.returnItemsSelectList(itemListPopup,position);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        imgBtnClose.setOnClickListener(view -> dialog.cancel());

        dialog.show();
    }

    @SuppressLint("SetTextI18n")
    public void itemDetailsPopup(Context context, int position, List<Item> list){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        dialog.setContentView(R.layout.item_details_popup);
        dialog.show();

        DecimalFormat df = new DecimalFormat("#,###,##0.00");
        ImageButton imgBtnClose = dialog.findViewById(R.id.payment_imgBtn_close);

        TextView textViewItemNo = dialog.findViewById(R.id.txtItemNo);
        TextView textViewItemName = dialog.findViewById(R.id.txtItemName);
        TextView textViewItemQty = dialog.findViewById(R.id.txtItemQty);
        TextView textViewItemSellingPrice = dialog.findViewById(R.id.txtItemSellingPrice);
        TextView textViewItemCategory = dialog.findViewById(R.id.txtItemCategory);
        TextView textViewItemSubCategory = dialog.findViewById(R.id.txtItemSubCategory);
        TextView textViewItemPurchasePrice = dialog.findViewById(R.id.txtItemPurchasePrice);
        TextView textViewItemExpiryDate = dialog.findViewById(R.id.txtItemExpiryDate);
        TextView textViewItemDefDis = dialog.findViewById(R.id.txtItemDefDis);
        TextView textViewItemMaxDis = dialog.findViewById(R.id.txtItemMaxDis);
        TextView textViewItemReorderQty = dialog.findViewById(R.id.txtItemReorderQty);
        TextView textViewItemTrack = dialog.findViewById(R.id.txtItemTWI);
        TextView textViewItemUOM = dialog.findViewById(R.id.txtItemUOM);


        if(list.size()>0){
            textViewItemNo.setText(String.valueOf(list.get(position).getItemNo()));
            textViewItemName.setText(list.get(position).getItemName());
            textViewItemQty.setText(String.valueOf(list.get(position).getItemQty()));
            textViewItemSellingPrice.setText(String.valueOf(df.format(list.get(position).getItemSellingPrice())));
            textViewItemCategory.setText(list.get(position).getItemCategory());
            textViewItemSubCategory.setText(list.get(position).getItemSubCategory());
            textViewItemPurchasePrice.setText(String.valueOf(df.format(list.get(position).getItemPurchasePrice())));
            textViewItemExpiryDate.setText(list.get(position).getItemExpiryDate());
            if(list.get(position).getItemDefaultDiscountType().equals(1)){
                textViewItemDefDis.setText(df.format(list.get(position).getItemDefaultDiscount()));
            }else {
                textViewItemDefDis.setText(list.get(position).getItemDefaultDiscount()+"%");
            }
            if(list.get(position).getItemMaxDiscountType().equals(1)){
                textViewItemMaxDis.setText(String.valueOf(df.format(list.get(position).getItemMaxDiscount())));
            }else {
                textViewItemMaxDis.setText(list.get(0).getItemMaxDiscount()+"%");
            }
            textViewItemReorderQty.setText(String.valueOf(list.get(position).getItemReorderQty()));
            textViewItemTrack.setText(list.get(position).getItemTrackItem());
            textViewItemUOM.setText(list.get(position).getItemUMO());

        }

        imgBtnClose.setOnClickListener(view -> dialog.cancel());

    }

}