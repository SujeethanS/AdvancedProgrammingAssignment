package com.example.customerOrderApp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerOrderApp.Service.VolleyBackgroundTaskGet;
import com.example.customerOrderApp.Service.VolleyGetTempItems;
import com.example.customerOrderApp.adapter.CategoryAdapter;
import com.example.customerOrderApp.adapter.ItemsAdapter;
import com.example.customerOrderApp.adapter.SubCategoryAdapter;
import com.example.customerOrderApp.helper.AppSettings;
import com.example.customerOrderApp.helper.CheckOnline;
import com.example.customerOrderApp.helper.CustomDialog;
import com.example.customerOrderApp.helper.RecyclerItemClickListener;
import com.example.customerOrderApp.helper.Utility;
import com.example.customerOrderApp.helper.Validation;
import com.example.customerOrderApp.pojo.Company;
import com.example.customerOrderApp.pojo.Item;
import com.example.customerOrderApp.pojo.OrderDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.supercharge.shimmerlayout.ShimmerLayout;

public class OrderSearchFragment extends Fragment implements CategoryAdapter.OnGridChangeListener, View.OnClickListener,VolleyBackgroundTaskGet.ResultInventoryItemDelegate,VolleyBackgroundTaskGet.ResultCategoryDelegate
        , VolleyBackgroundTaskGet.ResultSubCategoryDelegate, CustomDialog.EditItemPrice, VolleyGetTempItems.QuickAddItemDelegate, VolleyBackgroundTaskGet.ResultItemSummaryDelegate{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnFragmentInteractionListener mListener;
    private View fragmentView;
    private CardView numberCardView;

    private CategoryAdapter categoryAdapter;
    private SubCategoryAdapter subCategoryAdapter;
    ItemsAdapter itemsAdapter;
    private OrderSearchFragment orderSearchFragment;

    private RecyclerView categoryRecyclerView, subCategoryRecyclerView, itemsRecyclerView;

    private ProgressBar progressBarItem, progressBarSubCategory;

    private String baseUrl;
    private String locationId;
    private String companyId;

    String selectCategoryName = "";
    String selectSubCategoryName = "";

    private static List<String> categoryList=new ArrayList<>();

    private static List<String> subCategoryList;

    private List<Item> itemsList;
    private final Company company = new Company();
    private TextView txtItemNoDataFound, txtCategoryNoDataFound;


    private boolean searchFirstTime = false;
    private SearchView searchView = null;

    ConstraintLayout categoryVisibleLayout,subcategoryVisibleLayout,itemVisibleLayout;
    private TextView textCartItemCount;
    private int mCartItemCount = 0;
    private String videoID = "1og1fmYqTbg"; //https://youtu.be/1og1fmYqTbg

    private Button btnViewOrder;

    private LinearLayout skeletonLayout;
    private ShimmerLayout shimmer;
    private LayoutInflater inflater;

    public OrderSearchFragment() {
        WallActivity.categoryClick = 0;
    }

    // TODO: Rename and change types and number of parameters
    public static OrderSearchFragment newInstance(String param1, String param2) {
        OrderSearchFragment fragment = new OrderSearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // TODO: Rename and change types of parameters

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView= inflater.inflate(R.layout.order_search_fragment,container,false);
        orderSearchFragment = this;

        initializeUI();
        fragmentView.setOnKeyListener((v, keyCode, event) -> {
            // here your code
            if( keyCode == KeyEvent.KEYCODE_BACK ) {
                return true;
            }
            else if (keyCode == KeyEvent.KEYCODE_HOME) {
                Toast.makeText(getContext(), "You pressed the home button!",
                        Toast.LENGTH_LONG).show();
                return true;
            }
            return false;
        });

        setHasOptionsMenu(true);
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

    void toolBarInitialize(){
        Toolbar toolbar = fragmentView.findViewById(R.id.toolbarOrder);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_navigation_icon);
        toolbar.setTitle("Sales Order");

        toolbar.setNavigationOnClickListener(v -> {
            if(WallActivity.categoryClick == 0 ){

                if (itemVisibleLayout.getVisibility() == View.VISIBLE) {
                    if (MainActivity.cartCount > 0) {
                        String message = "Do you want to close the order ?";
                        String positive = "Yes";
                        String negative = "No";

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                        builder1.setMessage(message);
                        builder1.setCancelable(false);

                        builder1.setPositiveButton(
                                positive,
                                (dialog, id) -> {
                                    WallActivity.categoryClick = 0;
                                    mListener.sendCardCount("close", "Normal");
                                    Objects.requireNonNull(getActivity()).finish();

                                });

                        builder1.setNegativeButton(
                                negative,
                                (dialog, id) -> dialog.cancel());

                        AlertDialog alert1 = builder1.create();
                        alert1.show();

                    } else {
                        Objects.requireNonNull(getActivity()).finish();
                    }
                }

            }else {

                if (categoryVisibleLayout.getVisibility() == View.VISIBLE) {
                    if (MainActivity.cartCount > 0) {
                        String message = "Do you want to close the order ?";
                        String positive = "Yes";
                        String negative = "No";

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                        builder1.setMessage(message);
                        builder1.setCancelable(false);

                        builder1.setPositiveButton(
                                positive,
                                (dialog, id) -> {
                                    WallActivity.categoryClick = 0;
                                    mListener.sendCardCount("close", "Normal");
                                    Objects.requireNonNull(getActivity()).finish();
                                });

                        builder1.setNegativeButton(
                                negative,
                                (dialog, id) -> dialog.cancel());

                        AlertDialog alert1 = builder1.create();
                        alert1.show();

                    } else {
                        Objects.requireNonNull(getActivity()).finish();
                    }

                } else if (subcategoryVisibleLayout.getVisibility() == View.VISIBLE) {
                    subCategoryList.clear();
                    subcategoryVisibleLayout.setVisibility(View.INVISIBLE);
                    categoryVisibleLayout.setVisibility(View.VISIBLE);
                } else if (itemVisibleLayout.getVisibility() == View.VISIBLE) {
                    itemsList.clear();
                    itemVisibleLayout.setVisibility(View.INVISIBLE);
                    subcategoryVisibleLayout.setVisibility(View.VISIBLE);
                    numberCardView.setCardBackgroundColor(Color.BLACK);
                }
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initializeUI() {

        company.setCompanyName(AppSettings.getCompanyName(Objects.requireNonNull(getContext())));
        company.setCompanyAddress(AppSettings.getCompanyAddress(getContext()));
        company.setCompanyContactNumber(AppSettings.getCompanyContactNumber(getContext()));
        company.setCompanyMessage(AppSettings.getCompanyMessage(getContext()));

        baseUrl = AppSettings.getURLs(getContext());
        locationId = String.valueOf(AppSettings.getCompanyLocationId(getContext()));
        companyId = AppSettings.getCompanyId(getContext());

        progressBarItem = fragmentView.findViewById(R.id.progressBar_cyclic);
        progressBarSubCategory = fragmentView.findViewById(R.id.progressBar_cyclic_subCategory);
        itemsRecyclerView = fragmentView.findViewById(R.id.itemActivityGridMenu);
        txtItemNoDataFound = fragmentView.findViewById(R.id.itemSearch_nodataFound);
        txtCategoryNoDataFound = fragmentView.findViewById(R.id.categorySearch_nodataFound);

        skeletonLayout = fragmentView.findViewById(R.id.skeletonLayout);
        shimmer = fragmentView.findViewById(R.id.shimmerSkeleton);
        this.inflater = (LayoutInflater) Objects.requireNonNull(getActivity())
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        toolBarInitialize();

        Animation anim = new AlphaAnimation(0.0f, 1.0f);

        categoryVisibleLayout = fragmentView.findViewById(R.id.categoryVisibleLayout);
        subcategoryVisibleLayout = fragmentView.findViewById(R.id.subcategoryVisibleLayout);
        itemVisibleLayout = fragmentView.findViewById(R.id.itemVisibleLayout);

        ImageButton shoppingCardButton = fragmentView.findViewById(R.id.shoppingCardButton);
        shoppingCardButton.setVisibility(View.INVISIBLE);

        btnViewOrder = fragmentView.findViewById(R.id.btn_view_order);
        btnViewOrder.setVisibility(View.INVISIBLE);

        categoryList = new ArrayList<>();

        categoryRecyclerView = fragmentView.findViewById(R.id.orderActivityGridMenu);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        categoryRecyclerView.setHasFixedSize(true);
        categoryAdapter = new CategoryAdapter(getContext(), categoryList, this);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

        subCategoryList = new ArrayList<>();
        subCategoryRecyclerView = fragmentView.findViewById(R.id.subcategoryActivityGridMenu);
        subCategoryRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        subCategoryRecyclerView.setHasFixedSize(true);
        subCategoryAdapter = new SubCategoryAdapter(getContext(), subCategoryList);
        subCategoryRecyclerView.setAdapter(subCategoryAdapter);
        subCategoryAdapter.notifyDataSetChanged();

        if (WallActivity.categoryClick != 0) {
            categoryVisibleLayout.setVisibility(View.VISIBLE);
            subcategoryVisibleLayout.setVisibility(View.INVISIBLE);
            itemVisibleLayout.setVisibility(View.INVISIBLE);
        }

        itemsList = new ArrayList<>();

        itemsAdapter = new ItemsAdapter(getActivity(), itemsList);

            itemsAdapter.setLoadMoreListener(() -> {

                if(itemsList.size()!= 1) {
                    int index = itemsList.size() - 1;
                    if(index >= 19) {
                        loadMore(index, selectCategoryName, selectSubCategoryName);
                    }
                }
            });

        int mNoOfColumns = Utility.calculateNoOfColumns(getActivity());

        itemsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),mNoOfColumns));
        itemsRecyclerView.setHasFixedSize(true);
        itemsRecyclerView.setAdapter(itemsAdapter);
        numberCardView = fragmentView.findViewById(R.id.number_card_view);


        anim.setDuration(200);
        anim.setStartOffset(50);
        anim.setRepeatCount(1);


        categoryRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), categoryRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        selectCategoryName = categoryList.get(position);

                        VolleyBackgroundTaskGet.syncAllItemSubCategory(Objects.requireNonNull(getActivity()),"http://10.0.2.2:8080/get/brand",orderSearchFragment);
                        subCategoryAdapter.notifyDataSetChanged();


                        categoryVisibleLayout.setVisibility(View.INVISIBLE);
                        subcategoryVisibleLayout.setVisibility(View.VISIBLE);
                        subCategoryRecyclerView.setVisibility(View.GONE);
                        progressBarSubCategory.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );

        subCategoryRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), subCategoryRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        selectSubCategoryName = subCategoryList.get(position);

                        selectItemUrl(selectCategoryName,selectSubCategoryName);

                        subcategoryVisibleLayout.setVisibility(View.INVISIBLE);
                        itemVisibleLayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );

        itemsRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), itemsRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if(itemsList.size()>0 && position >-1) {
                            String iNo = itemsList.get(position).getItemNo();
                            itemClickForSale(itemsList, position);
                        }

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        new CustomDialog().itemDetailsPopup(getActivity(),position,itemsList);

                    }
                })
        );

        btnViewOrder.setOnClickListener(view -> {
            mListener.onOrderButtonClicked();
            numberCardView.setCardBackgroundColor(Color.BLACK);


        });

    }

    private void setupBadge() {

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(mCartItemCount));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private OrderDetails setItemData(Item item){
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderDetailsItemNumber(item.getItemNo());
        orderDetails.setOrderDetailsItemName(item.getItemName());
        orderDetails.setOrderDetailsItemQty(1.0);
        orderDetails.setOrderDetailsProductType(item.getProductType());
        orderDetails.setDefaultType(item.getItemDefaultDiscountType());
        orderDetails.setMaxType(item.getItemMaxDiscountType());
        orderDetails.setUom(item.getItemUMO());
        orderDetails.setTrackItem(item.getItemTrackItem());
        orderDetails.setReOrder(item.getItemReorderQty());
        orderDetails.setSubCategory(item.getItemSubCategory());
        orderDetails.setExpiryDate(item.getItemExpiryDate());
        orderDetails.setOrderDetailsItemSellingPrice(item.getItemSellingPrice());
        orderDetails.setOrderDetailsItemPrice(item.getItemSellingPrice() - item.getItemDefaultDiscount());

        orderDetails.setOrderDetailsItemCategory(item.getItemCategory());
        orderDetails.setOrderDetailsPurchasePrice(item.getItemPurchasePrice());
        orderDetails.setItemSellingPriceTotal( (item.getItemSellingPrice() - item.getItemDefaultDiscount() ) * orderDetails.getOrderDetailsItemQty());
        orderDetails.setOrderDetailsPatchId(item.getBid());
        orderDetails.setOrderDetailsItemDiscount(item.getItemDefaultDiscount());
        orderDetails.setOrderDetailsItemMaxDiscount(item.getItemMaxDiscount());
        orderDetails.setSalesType("normal");

        return orderDetails;
    }

    private void sendToMain(OrderDetails od){
        if(od != null) {
            mListener.setItemDetailsToOrder(od);
        }
    }

    void clearSearchDetails(){
            itemVisibleLayout.setVisibility(View.VISIBLE);
            subcategoryVisibleLayout.setVisibility(View.INVISIBLE);
            categoryVisibleLayout.setVisibility(View.INVISIBLE);
            btnViewOrder.setVisibility(View.INVISIBLE);
        subCategoryList.clear();
        categoryList.clear();

        categoryList = new ArrayList<>();
        categoryAdapter= new CategoryAdapter(getContext(),categoryList,this);
        VolleyBackgroundTaskGet.syncAllItemCategory(Objects.requireNonNull(getActivity()),"http://10.0.2.2:8080/get/category",this);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void sendCategoryList(List<String> list) {
        if(list.size()>0){
            categoryList.clear();
            categoryList.addAll(list);
            txtCategoryNoDataFound.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
        }else {
            categoryRecyclerView.setVisibility(View.GONE);
            txtCategoryNoDataFound.setVisibility(View.VISIBLE);
        }
        categoryAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void sendSubCategoryList(List<String> list) {
        if (list.size()>0){

            subCategoryList.clear();
            subCategoryList.addAll(list);

            subCategoryAdapter.notifyDataSetChanged();

            subCategoryRecyclerView.setVisibility(View.VISIBLE);
            progressBarSubCategory.setVisibility(View.GONE);
        }

    }

    @Override
    public void sendItemList(List<Item> list,String type) {
        if(list != null) {
            if (type.equalsIgnoreCase("Sale")) {

                if (list.size() == 1) {
                    itemClickForSale(list, 0);
                } else if (list.size() > 1) {
                    new CustomDialog().multiBIDItemDialog(getActivity(), list, this::itemClickForSale);
                }
            }
        }
    }

    @Override
    public void OnGridChange(int colorCode , int position ) {
    }

    @Override
    public void editItemPrice(Item item) {

        if(item != null) {
            OrderDetails orderDetails = setItemData(item);
            sendToMain(orderDetails);
            mListener.sendCardCount("normal", "Normal");
            btnViewOrder.setVisibility(View.VISIBLE);
        }
    }

    void hideViewButton(){
        btnViewOrder.setVisibility(View.INVISIBLE);
    }

    @Override
    public void notifyAddItem() {
        if (WallActivity.categoryClick == 0) {
            selectItemUrl(selectCategoryName, selectSubCategoryName);
        }
    }

    @Override
    public void sendItemSummaryList(List<Item> list, String type) {
        if(type.equals("load")){

            if(list.size() > 0){
                itemsList.clear();
                itemsList.addAll(list);

                itemsRecyclerView.setVisibility(View.VISIBLE);
                showSkeleton(false);
                txtItemNoDataFound.setVisibility(View.GONE);

            }else {
                itemsList.addAll(list);
                itemsAdapter.notifyDataChanged();
                itemsRecyclerView.setVisibility(View.VISIBLE);
                showSkeleton(false);
                txtItemNoDataFound.setVisibility(View.GONE);

                if (list.size()==0){
                    txtItemNoDataFound.setVisibility(View.VISIBLE);
                    itemsRecyclerView.setVisibility(View.GONE);
                    showSkeleton(false);
                }
            }

        }else if(type.equals("loadMore")){
            if(itemsList.size()>0){
                itemsList.remove(itemsList.size() - 1);

            }

            if (itemsList.size() > 0) {
                itemsList.addAll(list);

                itemsRecyclerView.setVisibility(View.VISIBLE);
                progressBarItem.setVisibility(View.GONE);
                txtItemNoDataFound.setVisibility(View.GONE);
                if (list.size() == 1) {
                    itemsAdapter.setMoreDataAvailable();
                }
            } else {
                itemsAdapter.setMoreDataAvailable();
                Toast.makeText(getActivity(), "No More Data Available", Toast.LENGTH_LONG).show();
            }

        }

        itemsAdapter.notifyDataChanged();

    }

    void setCardCount(int count){
        mCartItemCount = count;
        setupBadge();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void onOrderButtonClicked();
        void setItemDetailsToOrder(OrderDetails orderDetails);
        void categoryClicked();
        void itemClicked();
        void cardPopup();
        void sendCardCount(String type, String sales );
        void changeQuickSales(String type);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu , MenuInflater inflater) {
        menu.clear();
       inflater.inflate(R.menu.menu_main, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_addcart);

        View actionView = menuItem.getActionView();
        textCartItemCount = actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(v -> onOptionsItemSelected(menuItem));

        MenuItem searchItem = menu.findItem(R.id.search_Item);
        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            assert searchManager != null;
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint("Search Item");
            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    if (searchFirstTime && newText.length() == 0) {
                        if (CheckOnline.isOnline(getActivity())) {
                            selectItemUrl(selectCategoryName, selectSubCategoryName);
                            itemsAdapter.setMoreDataAvailableTrue();
                        }
                    } else {
                        searchFirstTime = true;
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    if (query.length() != 0) {
                        searchItems(query);
                    } else {
                        if (CheckOnline.isOnline(getActivity())) {
                            selectItemUrl(selectCategoryName, selectSubCategoryName);
                            itemsAdapter.setMoreDataAvailableTrue();
                        }
                    }
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_addcart) {
            mListener.cardPopup();
           return true;
        }else if (id == R.id.categories) {
            if(item.getTitle().toString().equalsIgnoreCase("Categories")){
                item.setTitle("Items");
                videoID = "9xkCz3a-3WE";//https://youtu.be/9xkCz3a-3WE
                mListener.categoryClicked();

            }else if(item.getTitle().toString().equalsIgnoreCase("Items")) {
                item.setTitle("Categories");
                videoID = "1og1fmYqTbg";
                mListener.itemClicked();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {
        super.onResume();

        Validation.HideBottomNavigationCall(getActivity());
        AppSettings.setActivityLive(Objects.requireNonNull(getActivity()),false);
        Validation.HideBottomNavigationCall(getActivity());

        if (WallActivity.categoryClick == 0) {
            selectItemUrl(selectCategoryName, selectSubCategoryName);
        }
        VolleyBackgroundTaskGet.syncAllItemCategory(getActivity(), "http://10.0.2.2:8080/get/category", this);
        itemsAdapter.setMoreDataAvailableTrue();
        itemsAdapter.notifyDataChanged();

    }

    void selectItemUrl(String category , String subCategory) {
        if(CheckOnline.isOnline(getActivity())) {
            load(category,subCategory);
        }
    }

    public void load(String category,String subCategory){

        itemsList.clear();
        itemsRecyclerView.setVisibility(View.GONE);
        txtItemNoDataFound.setVisibility(View.GONE);
        showSkeleton(true);
        String url = "http://10.0.2.2:8080/get/product/details";
        VolleyBackgroundTaskGet.syncAllItemsSummary(Objects.requireNonNull(getActivity()), url, orderSearchFragment, "load", category, subCategory);
        itemsAdapter.notifyDataChanged();
        itemsAdapter.setMoreDataAvailableTrue();

    }

    private void loadMore(int index,String category,String subCategory) {

        String url = "http://10.0.2.2:8080/get/product/details";
        VolleyBackgroundTaskGet.syncAllItemsSummary(Objects.requireNonNull(getActivity()), url, orderSearchFragment, "loadMore", category, subCategory);

    }

    private void itemClickForSale(List<Item> list, int position){
        if(list != null ) {
            if(list.size() > 0) {
                Item selectItem = list.get(position);
                if (list.get(position).getItemSellingPrice() != 0.0) {

                    if(selectItem != null) {
                        OrderDetails orderDetails = setItemData(selectItem);
                        sendToMain(orderDetails);

                        btnViewOrder.setVisibility(View.VISIBLE);
                        mListener.sendCardCount("normal", "Normal");
                    }
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        VolleyBackgroundTaskGet.syncAllItemCategory(Objects.requireNonNull(getActivity()), "http://10.0.2.2:8080/get/category", this);

    }

    private int getSkeletonRowCount(Context context) {
        int pxHeight = getDeviceHeight(context);
        int skeletonRowHeight = (int) getResources()
                .getDimension(R.dimen.row_layout_height); //converts to pixel
        return (int) Math.ceil(pxHeight / skeletonRowHeight);
    }
    private int getDeviceHeight(Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return metrics.heightPixels;
    }


    private void showSkeleton(boolean show) {

        if (show) {

            skeletonLayout.removeAllViews();

            int skeletonRows = getSkeletonRowCount(getActivity());
            for (int i = 0; i <= skeletonRows; i++) {
                @SuppressLint("InflateParams") ViewGroup rowLayout = (ViewGroup) inflater
                        .inflate(R.layout.refresh_item_sales_single, null);
                skeletonLayout.addView(rowLayout);
            }
            shimmer.setVisibility(View.VISIBLE);
            shimmer.startShimmerAnimation();
            skeletonLayout.setVisibility(View.VISIBLE);
            skeletonLayout.bringToFront();
        } else {
            shimmer.stopShimmerAnimation();
            shimmer.setVisibility(View.GONE);
        }
    }

    private void searchItems(String items){

        if(items.trim().length()>0) {
            String URL = "http://10.0.2.2:8080/get/product/details";
            VolleyBackgroundTaskGet.syncAllItemsSummary(Objects.requireNonNull(getActivity()), URL, this, "load", "", "");
            showSkeleton(true);

        }else{
            itemsList.clear();
            itemsAdapter.notifyDataSetChanged();
            selectItemUrl(selectCategoryName,selectSubCategoryName);
        }
    }

    private static String setSendWithOutspace(String iName){

        String name;
        name = iName.replaceAll(" ","%20");
        return name;
    }
}

