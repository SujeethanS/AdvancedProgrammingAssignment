package com.example.customerOrderApp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerOrderApp.adapter.SelectedItemAdapter;
import com.example.customerOrderApp.helper.AppSettings;
import com.example.customerOrderApp.helper.CustomDialog;
import com.example.customerOrderApp.helper.ToastMessageHelper;
import com.example.customerOrderApp.helper.Validation;
import com.example.customerOrderApp.pojo.Order;
import com.example.customerOrderApp.pojo.OrderDetails;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class OrderListFragment extends Fragment implements View.OnClickListener, CustomDialog.ModifyOrderDetailsQty,CustomDialog.OrderDiscountAmount {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static int setDiscount = 0;
    private OnFragmentInteractionListener mListener;
    private OrderListFragment fragment;
    private TextView orderId;
    private TextView noOfItem;
    private TextView subTotalSum;
    private TextView discountSum;
    private TextView vatSum;


    Double doubleSubTotalSum = 0.0;
    Double oderDiscountSum = 0.0;
    Double finalOderDiscountSum = 0.0;
    int checkType = 1;
    RecyclerView selectItemRecyclerView;
    static List<OrderDetails> selectIdList;
    SelectedItemAdapter selectedItemAdapter;

    TextView textViewTotalSum;
    private final String appLanguage = "English";


    private final DecimalFormat df = new DecimalFormat("#,##0.00");

    public OrderListFragment() {
        // Required empty public constructor
    }

    Order order = new Order();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderListFragment newInstance(String param1, String param2) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragment = this;
        View fragmentView = inflater.inflate(R.layout.fragment_order_list, container, false);
        initializeOrderListFragment(fragmentView);
        fragmentView.setOnKeyListener((v, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK);
        return fragmentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    private void initializeOrderListFragment(View fragmentView){

        subTotalSum = fragmentView.findViewById(R.id.textView_SubTotalSum);
        discountSum = fragmentView.findViewById(R.id.textView_DiscountSum);
        discountSum.setText("0.00");

        vatSum = fragmentView.findViewById(R.id.textView_vatSum);
        textViewTotalSum = fragmentView.findViewById(R.id.textView_totalSum);

        LinearLayout discountLayout = fragmentView.findViewById(R.id.discountLayout);
        View discountView = fragmentView.findViewById(R.id.discount_view);
        LinearLayout linearLayoutTax = fragmentView.findViewById(R.id.linearTax);
        View viewTaxline = fragmentView.findViewById(R.id.linearTaxLine);
        Button buttonDis = fragmentView.findViewById(R.id.button_Discount);
        Button buttonTax = fragmentView.findViewById(R.id.button_Tax);

        if(AppSettings.getDisType(Objects.requireNonNull(getContext())).equalsIgnoreCase("Y")){
            discountLayout.setVisibility(View.VISIBLE);
            discountView.setVisibility(View.VISIBLE);
            buttonDis.setVisibility(View.VISIBLE);
        }else {
            discountLayout.setVisibility(View.GONE);
            discountView.setVisibility(View.GONE);
            buttonDis.setVisibility(View.GONE);
        }

        if(AppSettings.getAllowtax(getContext()).equalsIgnoreCase("Y")){
            linearLayoutTax.setVisibility(View.VISIBLE);
            viewTaxline.setVisibility(View.VISIBLE);
            buttonTax.setVisibility(View.VISIBLE);
        }else {
            linearLayoutTax.setVisibility(View.GONE);
            viewTaxline.setVisibility(View.GONE);
            buttonTax.setVisibility(View.GONE);
        }

        selectItemRecyclerView = fragmentView.findViewById(R.id.OrderList_Recycler);
        selectItemRecyclerView.setItemAnimator(new DefaultItemAnimator());

        selectIdList = new ArrayList<>();
        selectedItemAdapter = new SelectedItemAdapter(getActivity(),selectIdList);


            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
            selectItemRecyclerView.setLayoutManager(layoutManager);
            selectItemRecyclerView.setAdapter(selectedItemAdapter);
            selectedItemAdapter.notifyDataSetChanged();

        ConstraintLayout constraintLayout = fragmentView.findViewById(R.id.orderlist_fragment);
        constraintLayout.setClickable(true);

        mListener.onGetOrderType();
            ItemTouchHelper.SimpleCallback simpleItemTouchCallbackModify = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    Toast.makeText(getActivity(), "on Move", Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                    final int position = viewHolder.getAdapterPosition();
                    OrderDetails orderDetails = selectIdList.get(position);
                    CustomDialog.ModifyOrderDetailItemQtyPopup(getActivity(), fragment, orderDetails, position);
                    selectedItemAdapter.notifyDataSetChanged();
                }
            };
            ItemTouchHelper itemTouchHelperModify = new ItemTouchHelper(simpleItemTouchCallbackModify);
            itemTouchHelperModify.attachToRecyclerView(selectItemRecyclerView);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                Toast.makeText(getActivity(), "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {
                String message = "Do you want to remove item ?";
                String positive = "Yes";
                String negative = "No";
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage(message);
                builder1.setCancelable(false);

                builder1.setPositiveButton(
                        positive,
                        (dialog, id) -> {
                            AppSettings.setActivityLive(Objects.requireNonNull(getActivity()),false);
                            Validation.HideBottomNavigationCall(getActivity());

                            ToastMessageHelper.customSuccToast(getActivity(),"Deleted Item");
                            final int position = viewHolder.getAdapterPosition();
                            mListener.onsetShoppingCardQty();
                            selectIdList.remove(position);
                            setOrderTotal();
                            noOfItem.setText(calNoOfItem(selectIdList));
                            selectedItemAdapter.notifyItemRemoved(position);
                            selectedItemAdapter.notifyDataSetChanged();
                            if (selectIdList.size()==0){
                                mListener.onAcceptButtonClick();
                                mListener.onsetShoppingCardQty();

                            }
                        });

                builder1.setNegativeButton(
                        negative,
                        (dialog, id) -> {
                            AppSettings.setActivityLive(Objects.requireNonNull(getActivity()),false);
                            Validation.HideBottomNavigationCall(getActivity());
                            selectedItemAdapter.notifyDataSetChanged();
                            dialog.cancel();
                        });

                AlertDialog alert1 = builder1.create();
                alert1.show();

            }
        };

        ItemTouchHelper itemTouchHelperDelete = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelperDelete.attachToRecyclerView(selectItemRecyclerView);

        orderId = fragmentView.findViewById(R.id.textView_orderId);
        noOfItem = fragmentView.findViewById(R.id.textView_number);

        Button buttonPay = fragmentView.findViewById(R.id.button_pay);
        Button buttonBack = fragmentView.findViewById(R.id.button_Back);
        buttonDis = fragmentView.findViewById(R.id.button_Discount);

        buttonDis.setOnClickListener(v -> {
            setDiscount = 1 ;
            discountSum.setText(df.format(0.0));
            textViewTotalSum.setText(String.valueOf(df.format(doubleSubTotalSum)));
            CustomDialog.OrderDiscountAmountPopup(getActivity(),fragment);

        });

        buttonTax.setOnClickListener(v -> ToastMessageHelper.customErrToast(Objects.requireNonNull(getActivity()),"On progressing..."));

        buttonPay.setOnClickListener(v -> {

            if(selectIdList.size()!= 0 ) {

                if (order.getOrderTotal() != 0.0) {
                    Order sendOrder = new Order();
                    final Calendar calendar = Calendar.getInstance();

                    sendOrder.setOrderDate(calendar.get(Calendar.YEAR) +
                            "/" + calendar.get(Calendar.MONTH) +
                            "/" + calendar.get(Calendar.DATE));

                    sendOrder.setOrderSubTotal(order.getOrderSubTotal());
                    sendOrder.setOrderTotal(order.getOrderTotal());
                    sendOrder.setOrderId(orderId.getText().toString());
                    sendOrder.setOrderDiscount(finalOderDiscountSum);
                    sendOrder.setOrderVatTotal(0.0);

                    mListener.onPayButtonClicked(sendOrder, selectIdList);
                }else {
                    ToastMessageHelper.customErrToast(Objects.requireNonNull(getActivity()),"Check Your Total Amount");
                }
            }
            else {
                ToastMessageHelper.customErrToast(Objects.requireNonNull(getActivity()),"Order List is Empty");
            }
        }
        );

        buttonBack.setOnClickListener(v -> mListener.onBackButtonClicked(selectIdList.size()));

    }



    @SuppressLint("NotifyDataSetChanged")
    public void setItemToOrderDetailsList(OrderDetails od){
        if (selectIdList.size()>0){
            int itemCount = 0;
            int index = -1;

            for (OrderDetails itemPojo:selectIdList){

                if (itemPojo.getOrderDetailsItemNumber().equalsIgnoreCase(od.getOrderDetailsItemNumber()) && itemPojo.getOrderDetailsItemName().equalsIgnoreCase(od.getOrderDetailsItemName())
                && itemPojo.getOrderDetailsItemSellingPrice().equals(od.getOrderDetailsItemSellingPrice())){
                    index = itemCount;
                }
                itemCount++;
            }

            if (index == -1){
                selectIdList.add(od);
                setOrderTotal();
            }else {
                selectIdList.get(index).setOrderDetailsItemQty(selectIdList.get(index).getOrderDetailsItemQty()+od.getOrderDetailsItemQty());
                selectIdList.get(index).setItemSellingPriceTotal(selectIdList.get(index).getItemSellingPriceTotal()+od.getItemSellingPriceTotal());
                setOrderTotal();
            }

        }else {

            od.setItemSellingPriceTotal(od.getOrderDetailsItemQty()*od.getOrderDetailsItemPrice());
            selectIdList.add(od);
            setOrderTotal();
        }
        noOfItem.setText(calNoOfItem(selectIdList));
        selectedItemAdapter.notifyDataSetChanged();
    }

    private void setOrderTotal(){
        double orderTotal = 0.0;
        double discount = 0.0  ;
        for (OrderDetails orderDetails:selectIdList){
            orderTotal= orderTotal + orderDetails.getItemSellingPriceTotal();
        }
         doubleSubTotalSum = orderTotal;
        subTotalSum.setText(String.valueOf(df.format(doubleSubTotalSum)));

        if(setDiscount != 0) {
            discount = finalOderDiscountSum;
            discountSum.setText(df.format(discount));
        }else {
            discountSum.setText(df.format(0.0));
        }
        Double doubleVatTotalSum = Double.parseDouble("0.0");
        vatSum.setText(String.valueOf(df.format(doubleVatTotalSum)));

        Double doubleTotalSum = Double.parseDouble(String.valueOf(Double.toString(orderTotal)))- discount- doubleVatTotalSum;
        textViewTotalSum.setText(String.valueOf(df.format(doubleTotalSum)));

        order.setOrderSubTotal(doubleSubTotalSum);
      //  Log.e("setOrderSubTotal", " " +doubleSubTotalSum);
        order.setOrderTotal(doubleTotalSum);

    }

    private Double calDiscount(Double value ,int type){
        Double discount;

        if(type == 0){
            discount= doubleSubTotalSum * (value / 100);
        }else {
            discount = value;
        }
        if(doubleSubTotalSum <= discount){
            Toast.makeText(getContext(), "greater than Subtotal", Toast.LENGTH_SHORT).show();
            discount = 0.0;

        }
        return discount;


    }

    private String calNoOfItem(List<OrderDetails> list){
        int size = list.size();
        return String.valueOf(size);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void modifyOrderDetailsQty(OrderDetails orderDetails , int position) {

        selectIdList.get(position).setOrderDetailsItemQty(orderDetails.getOrderDetailsItemQty());
        selectIdList.get(position).setItemSellingPriceTotal(orderDetails.getItemSellingPriceTotal());
        selectIdList.get(position).setOrderDetailsItemDiscount(orderDetails.getOrderDetailsItemDiscount());
        selectIdList.get(position).setOrderDetailsItemMaxDiscount(orderDetails.getOrderDetailsItemMaxDiscount());

        setOrderTotal();
        selectedItemAdapter.notifyDataSetChanged();

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void orderDiscountAmount(Double discount ,int type) {
        checkType = type;
        oderDiscountSum = discount;//calDiscount(checkType);
        finalOderDiscountSum = calDiscount(oderDiscountSum,type);

        setOrderTotal();
        oderDiscountSum = 0.0;
        selectedItemAdapter.notifyDataSetChanged();
    }

    void clearSelectIdlistItems(){

        selectIdList.clear();
        selectedItemAdapter.notifyDataSetChanged();
        subTotalSum.setText(R.string._0_00);
        discountSum.setText(R.string._0_00);
        vatSum.setText(R.string._0_00);
        textViewTotalSum.setText(R.string._0_00);

    }

    void setOrderNumber(String id){
        orderId.setText(id);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        void onPayButtonClicked(Order order, List<OrderDetails> detailsPojoList);
        void onBackButtonClicked(int size);
        void onsetShoppingCardQty();
        void onAcceptButtonClick();
        void onGetOrderType();
    }

}
