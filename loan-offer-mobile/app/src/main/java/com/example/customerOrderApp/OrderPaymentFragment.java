package com.example.customerOrderApp;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.example.customerOrderApp.Service.VolleyBackgroundTaskPost;
import com.example.customerOrderApp.helper.AppSettings;
import com.example.customerOrderApp.helper.ConversionClass;
import com.example.customerOrderApp.helper.ReadableDateFormat;
import com.example.customerOrderApp.helper.ShowMsg;
import com.example.customerOrderApp.helper.ToastMessageHelper;
import com.example.customerOrderApp.helper.Validation;
import com.example.customerOrderApp.pojo.Company;
import com.example.customerOrderApp.pojo.Order;
import com.example.customerOrderApp.pojo.OrderDetails;
import com.example.customerOrderApp.pojo.Payment;
import com.printlib.kale.kaleprintsdk.PrinterUtilHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class OrderPaymentFragment extends Fragment implements View.OnClickListener, VolleyBackgroundTaskPost.SaleReceipt, VolleyBackgroundTaskPost.OrderCompleteResponse, VolleyBackgroundTaskPost.SettlementCompleteResponse {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    protected String mParam1;
    protected String mParam2;

    private int paymentId = 0;
    private OnFragmentInteractionListener mListener;

    //sms
    private static double receivedAmountSms;
    private static double balanceAmountSms;

    public static int visible = 0;

    @SuppressLint("StaticFieldLeak")
    private static LinearLayout customerLayout;

    private TextView totalSum, balanceAmount, totalReceived, customerName, customerId;
    private TextView orderId;
    private TextView noOfItem;

    private TextView paymentTotal;
    private TextView paymentReceived;
    private TextView paymentBalance;

    public Button cash, cheque, card, credit;
    int paymentMethod = 1001;

    private String salesType = "";
    private String selectAcceptType = "Accept";

    private String baseUrl;

    private Button accept;
    private Button sendAccept;
    private boolean isDecimalClick = true;

    private final StringBuffer totalAmount = new StringBuffer();

    private String receivedComplete = "0.0";
    private String balanceComplete;
    private String totalComplete;

    private final Company company = new Company();
    private Order currentOrder = new Order();
    private final List<OrderDetails> orderDetails = new ArrayList<>();
    private List<OrderDetails> currentOrderDetails = new ArrayList<>();

    private final String todayDate = ReadableDateFormat.getTodayDate(new Date(System.currentTimeMillis()));
    private final String todayTime = ConversionClass.getServerDateFormat(todayDate);

    public OrderPaymentFragment() {
        // Required empty public constructor
    }

    private final DecimalFormat df = new DecimalFormat("##0.00");

    private PrinterUtilHelper printerUtilHelper;

    // TODO: Rename and change types and number of parameters
    public OrderPaymentFragment newInstance(String param1, String param2) {
        OrderPaymentFragment fragment = new OrderPaymentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if (AppInit.getUtilHelper() != null) {
            printerUtilHelper = AppInit.getUtilHelper();
        } else {
            printerUtilHelper = new PrinterUtilHelper(getActivity());
            AppInit.setUtilHelper(printerUtilHelper);
        }


    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_order_payment, container, false);
        initializeUI(fragmentView);
        String appLanguage = "English";
        changeInterFaceLanguage(appLanguage);
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

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n", "ResourceAsColor"})
    private void initializeUI(final View fragmentView) {

        LinearLayout linearLayoutPayment = fragmentView.findViewById(R.id.orderpayment_fragment);
        linearLayoutPayment.setClickable(true);

        visible = 1;
        customerLayout = fragmentView.findViewById(R.id.customer_layout);

        company.setCompanyName(AppSettings.getCompanyName(Objects.requireNonNull(getContext())));
        company.setCompanyAddress(AppSettings.getCompanyAddress(getContext()));
        company.setCompanyContactNumber(AppSettings.getCompanyContactNumber(getContext()));
        company.setCompanyMessage(AppSettings.getCompanyMessage(getContext()));


        baseUrl = AppSettings.getURLs(getContext());

        ImageView imgViewBack = fragmentView.findViewById(R.id.imgBtn_back);

        orderId = fragmentView.findViewById(R.id.textView_orderIdPayment);
        noOfItem = fragmentView.findViewById(R.id.textView_numberPayment);

        cash = fragmentView.findViewById(R.id.imgButtonCash);
        cash.setBackgroundColor(R.color.toolBarBlue);
        cheque = fragmentView.findViewById(R.id.imgButtonCheque);
        cheque.setBackgroundColor(Color.LTGRAY);
        card = fragmentView.findViewById(R.id.imgButtonCard);
        card.setBackgroundColor(Color.LTGRAY);
        credit = fragmentView.findViewById(R.id.imgButtonCredit);
        credit.setBackgroundColor(Color.LTGRAY);

        ImageButton customerClose = fragmentView.findViewById(R.id.close_customer_imageButton);

        totalSum = fragmentView.findViewById(R.id.textViewTotalSum);
        totalReceived = fragmentView.findViewById(R.id.textView_ReceivedAmount);
        totalReceived.setInputType(InputType.TYPE_NULL);

        paymentTotal = fragmentView.findViewById(R.id.textViewTotal);
        paymentReceived = fragmentView.findViewById(R.id.textViewReceived);
        paymentBalance = fragmentView.findViewById(R.id.textViewBalance);

        balanceAmount = fragmentView.findViewById(R.id.textView_Balance);
        balanceAmount.setInputType(InputType.TYPE_NULL);

        accept = fragmentView.findViewById(R.id.paymentAccept);

        sendAccept = fragmentView.findViewById(R.id.paymentPrint);
        Button paymentExact = fragmentView.findViewById(R.id.paymentExact);

        customerName = fragmentView.findViewById(R.id.customerName);
        customerId = fragmentView.findViewById(R.id.customerId);

        imgViewBack.setOnClickListener(v -> {
            totalReceived.setText("0.00");
            balanceAmount.setText("0.00");
            totalAmount.setLength(0);
            isDecimalClick = true;
            mListener.onpaymentBackButtonClick();
        });

        accept.setOnClickListener(view -> {
            selectAcceptType = "Accept";

            if(currentOrder != null) {

                if (paymentMethod != 1004) {
                    if (Double.parseDouble(totalReceived.getText().toString().trim()) >= Double.parseDouble(df.format(currentOrder.getOrderTotal()))) {
                        acceptButtonFunction();
                    } else {
                        ToastMessageHelper.customErrToast(Objects.requireNonNull(getActivity()), "Received amount less than Total");
                    }
                } else {

                    if (Double.parseDouble(totalReceived.getText().toString()) < Double.parseDouble(df.format(currentOrder.getOrderTotal()))) {
                        acceptButtonFunction();

                    } else {
                        ToastMessageHelper.customErrToast(Objects.requireNonNull(getActivity()), "Please consider payment method");
                    }

                }
            }

        });

        sendAccept.setOnClickListener(view -> {
            selectAcceptType = "AcceptAndPrint";

            if(currentOrder != null) {
                if (paymentMethod != 1004) {
                    if (Double.parseDouble(totalReceived.getText().toString().trim()) > 0.0) {
                        acceptButtonFunction();
                    } else {
                        ToastMessageHelper.customErrToast(Objects.requireNonNull(getActivity()), "Enter the received amount");
                    }
                } else {
                    if (Double.parseDouble(totalReceived.getText().toString()) < Double.parseDouble(df.format(currentOrder.getOrderTotal()))) {
                        acceptButtonFunction();

                    } else {
                        ToastMessageHelper.customErrToast(Objects.requireNonNull(getActivity()), "Please consider payment method");
                    }

                }
            }
        });

        paymentExact.setOnClickListener(view -> {

            Double doubleReceived = Double.parseDouble(totalSum.getText().toString());
            totalReceived.setText(String.valueOf(df.format(doubleReceived)));

            Double doubleBalance = Double.parseDouble(String.valueOf(setBalance(totalReceived.getText().toString(), totalSum.getText().toString())));
            balanceAmount.setText(String.valueOf(df.format(doubleBalance)));
            totalAmount.setLength(0);
            isDecimalClick = true;

        });
        cash.setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                cash.setBackgroundColor(R.color.toolBarBlue);
                paymentMethod = 1001;
            } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                cheque.setBackgroundColor(Color.LTGRAY);
                card.setBackgroundColor(Color.LTGRAY);
                credit.setBackgroundColor(Color.LTGRAY);
            }

            return false;
        });

        cheque.setOnTouchListener((view, event) -> {


            if (event.getAction() == MotionEvent.ACTION_UP) {
                cheque.setBackgroundColor(R.color.toolBarBlue);
                paymentMethod = 1002;
            } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                cash.setBackgroundColor(Color.LTGRAY);
                card.setBackgroundColor(Color.LTGRAY);
                credit.setBackgroundColor(Color.LTGRAY);
            }
            return false;
        });

        card.setOnTouchListener((view, event) -> {

            if (Validation.getModelNEW9220()) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    card.setBackgroundColor(R.color.toolBarBlue);
                    paymentMethod = 1003;
                    totalReceived.setText(df.format(0.00));

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    cash.setBackgroundColor(Color.LTGRAY);
                    cheque.setBackgroundColor(Color.LTGRAY);
                    credit.setBackgroundColor(Color.LTGRAY);
                }
            } else {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    card.setBackgroundColor(R.color.toolBarBlue);
                    paymentMethod = 1003;

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    cash.setBackgroundColor(Color.LTGRAY);
                    cheque.setBackgroundColor(Color.LTGRAY);
                    credit.setBackgroundColor(Color.LTGRAY);
                }
            }
            return false;
        });

        credit.setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                credit.setBackgroundColor(R.color.toolBarBlue);

                paymentMethod = 1004;
            } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                cash.setBackgroundColor(Color.LTGRAY);
                card.setBackgroundColor(Color.LTGRAY);
                cheque.setBackgroundColor(Color.LTGRAY);
            }
            return true;
        });

        customerClose.setOnClickListener(v -> {
            customerLayout.setVisibility(View.GONE);
            currentOrder.setCustomerName("");
            currentOrder.setOrderCustomerId(0);
            customerName.setText("");
            customerId.setText("");
            cash.setBackgroundColor(R.color.toolBarBlue);
            credit.setBackgroundColor(Color.LTGRAY);
            card.setBackgroundColor(Color.LTGRAY);
            cheque.setBackgroundColor(Color.LTGRAY);
            paymentMethod = 1001;
        });

        Button myButton0 = fragmentView.findViewById(R.id.paymentKeypad_Num0);
        Button myButton1 = fragmentView.findViewById(R.id.paymentKeypad_Num1);
        Button myButton2 = fragmentView.findViewById(R.id.paymentKeypad_Num2);
        Button myButton3 = fragmentView.findViewById(R.id.paymentKeypad_Num3);
        Button myButton4 = fragmentView.findViewById(R.id.paymentKeypad_Num4);
        Button myButton5 = fragmentView.findViewById(R.id.paymentKeypad_Num5);
        Button myButton6 = fragmentView.findViewById(R.id.paymentKeypad_Num6);
        Button myButton7 = fragmentView.findViewById(R.id.paymentKeypad_Num7);
        Button myButton8 = fragmentView.findViewById(R.id.paymentKeypad_Num8);
        Button myButton9 = fragmentView.findViewById(R.id.paymentKeypad_Num9);
        Button myButtonDot = fragmentView.findViewById(R.id.paymentKeypad_NumDot);
        Button myButtonClear = fragmentView.findViewById(R.id.paymentKeypad_NumClear);

        myButton0.setOnClickListener(this::keyPadClick);
        myButton1.setOnClickListener(this::keyPadClick);
        myButton2.setOnClickListener(this::keyPadClick);
        myButton3.setOnClickListener(this::keyPadClick);
        myButton4.setOnClickListener(this::keyPadClick);
        myButton5.setOnClickListener(this::keyPadClick);
        myButton6.setOnClickListener(this::keyPadClick);
        myButton7.setOnClickListener(this::keyPadClick);
        myButton8.setOnClickListener(this::keyPadClick);
        myButton9.setOnClickListener(this::keyPadClick);
        myButtonDot.setOnClickListener(this::keyPadClick);
        myButtonClear.setOnClickListener(this::keyPadClick);

    }


    @SuppressLint("NonConstantResourceId")
    private void keyPadClick(View view){

        switch (view.getId()) {
            case R.id.paymentKeypad_Num0:
                paymentPadClickEvent("0");
                break;
            case R.id.paymentKeypad_Num1:
                paymentPadClickEvent("1");
                break;
            case R.id.paymentKeypad_Num2:
                paymentPadClickEvent("2");
                break;
            case R.id.paymentKeypad_Num3:
                paymentPadClickEvent("3");
                break;
            case R.id.paymentKeypad_Num4:
                paymentPadClickEvent("4");
                break;
                case R.id.paymentKeypad_Num5:
                paymentPadClickEvent("5");
                break;
            case R.id.paymentKeypad_Num6:
                paymentPadClickEvent("6");
                break;
            case R.id.paymentKeypad_Num7:
                paymentPadClickEvent("7");
                break;
            case R.id.paymentKeypad_Num8:
                paymentPadClickEvent("8");
                break;
            case R.id.paymentKeypad_Num9:
                paymentPadClickEvent("9");
                break;
            case R.id.paymentKeypad_NumDot:
                paymentPadClickEvent(".");
                break;
            case R.id.paymentKeypad_NumClear:
                paymentPadClickEvent("Clear");
                break;

            default:
                break;
        }
    }

    private void paymentPadClickEvent(String position){
        switch (position) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9": {

                totalAmount.append(position);
                Double doubleReceived = Double.parseDouble(String.valueOf(totalAmount));
                totalReceived.setText(String.valueOf(df.format(doubleReceived)));

                if (Double.parseDouble(totalReceived.getText().toString()) >= Double.parseDouble(totalSum.getText().toString())) {
                    Double doubleBalance = Double.parseDouble(String.valueOf(setBalance(totalReceived.getText().toString(), totalSum.getText().toString())));
                    balanceAmount.setText(String.valueOf(df.format(doubleBalance)));
                }
                break;
            }
            case ".":
                if(isDecimalClick){
                    totalAmount.append(".");
                    isDecimalClick = false;
                }
                break;
            case "Clear":
                totalReceived.setText(R.string._0_00);
                balanceAmount.setText(R.string._0_00);
                totalAmount.setLength(0);
                isDecimalClick = true;
                break;
        }
    }


    private Double setBalance(String received, String total) {
        double getReceived;
        double getTotal;

        getReceived = Double.parseDouble(received);
        getTotal = Double.parseDouble(total);

        return (getReceived - getTotal);
    }

    @SuppressLint("SetTextI18n")
    private void acceptButtonFunction() {
        AppSettings.setLastOrderPaymentMethod(Objects.requireNonNull(getActivity()), paymentMethod);

        if(currentOrder != null) {
            currentOrder.setOrderPaymentMethod(paymentMethod);
            currentOrder.setOrderUserPayment(Double.valueOf(totalReceived.getText().toString()));
            currentOrder.setOrderDate(ReadableDateFormat.getTodayDate(new Date(System.currentTimeMillis())));
            currentOrder.setOrderStatus("1002");
        }

        orderDetails.addAll(currentOrderDetails);

        totalComplete = totalSum.getText().toString().trim();
        receivedComplete = totalReceived.getText().toString().trim();
        balanceComplete = balanceAmount.getText().toString().trim();

        String itemURLInvoice;

        itemURLInvoice = "http://10.0.2.2:8080/create/new/order";
        if ( currentOrder != null && Double.parseDouble(totalReceived.getText().toString()) != (0.0)) {
            Double totalReceivedAmount;
            if (Double.parseDouble(totalReceived.getText().toString()) >= Double.parseDouble(df.format(currentOrder.getOrderTotal()))) {
                totalReceivedAmount = currentOrder.getOrderTotal();
                currentOrder.setOrderPaymentReceived(totalReceivedAmount);

                VolleyBackgroundTaskPost.postOrderAndOrderDetails(getActivity(), itemURLInvoice, currentOrder, orderDetails, this, this);
                accept.setEnabled(false);
                sendAccept.setEnabled(false);
                AppSettings.setClearList(Objects.requireNonNull(getContext()), "Y");
                getAmount(Double.parseDouble(totalReceived.getText().toString()), Double.parseDouble(balanceAmount.getText().toString()));

            } else {
                ToastMessageHelper.customErrToast(getActivity(), "Received amount less than Total");
            }
        } else {
            ToastMessageHelper.customErrToast(Objects.requireNonNull(getActivity()), "Enter the received amount");
        }
    }

    private Bitmap createSalesReceiptImage(Order order, List<OrderDetails> orderDetail, Company company) {
        if (order != null && orderDetail.size() > 0) {
            //return printerUtilService.createPrintReceiptImage(order, orderDetail, company);
        }
        return null;
    }

    private void sentSMS(String name, String phone) {

        String message = "Hello! " + name + " \n Your bill amount " + totalComplete + " \n Receive amount " +
                df.format(receivedAmountSms) + " \n Your balance " + df.format(balanceAmountSms) + " \n Thank you \n Send by " +
                company.getCompanyName();


        Uri uri = Uri.parse("smsto:" + phone);

        Intent smsSIntent = new Intent(Intent.ACTION_SENDTO, uri);
        smsSIntent.putExtra("sms_body", message);
        try {
            startActivity(smsSIntent);
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Your sms has failed...",
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }

    }


    void populatePaymentView(Order order, List<OrderDetails> orderDetails) {

        String id = order.getOrderId();
        String noOfOItems = String.valueOf(orderDetails.size());
        setData(id, noOfOItems);

        currentOrder = order;
        currentOrderDetails = orderDetails;

        if (currentOrder.getOrderTotal() != null) {

            Double doubleTotal = Double.parseDouble(String.valueOf(currentOrder.getOrderTotal()));
            totalSum.setText(String.valueOf(df.format(doubleTotal)));
        }

        if (currentOrder.getOrderCustomerId() != 0) {
            customerLayout.setVisibility(View.VISIBLE);
            customerId.setText(String.valueOf(currentOrder.getOrderCustomerId()));
            customerName.setText(currentOrder.getCustomerName());
        }
    }

    @Override
    public void sendOrderAndOrderDetailsComplete(Order order, List<OrderDetails> printDetails, int id, String name) {

        accept.setEnabled(true);
        sendAccept.setEnabled(true);
        isDecimalClick = true;

            if(currentOrder != null) {
                if (currentOrder.getOrderPaymentReceived() > 0) {

                    Payment payment = new Payment();

                    payment.setPayment_id(0);
                    payment.setInvoice_number(id);
                    payment.setReceived_by(AppSettings.getPrinterUser(Objects.requireNonNull(getActivity())));
                    payment.setPayment_total(currentOrder.getOrderPaymentReceived());
                    payment.setPayment_discount(0.00);
                    payment.setPayment_status("1001");
                    payment.setPaymentDate(todayTime);
                    payment.setPayment_number("************0000");
                    payment.setPayment_method(currentOrder.getOrderPaymentMethod());
                    payment.setCustomer_id(String.valueOf(currentOrder.getOrderCustomerId()));
                    payment.setUserPayment(currentOrder.getOrderUserPayment());
                    payment.setBalance(currentOrder.getOrderUserPayment() - currentOrder.getOrderPaymentReceived());

                    if (currentOrder.getOrderPaymentMethod() == 1004) {
                        payment.setPayment_type(1002);
                    } else {
                        payment.setPayment_type(1001);
                    }

                    String url = baseUrl + "api/sales/payment";
                    new VolleyBackgroundTaskPost().postPaymentDetails(getActivity(), url, payment, this);

                } else {
                    String keyWord;
                        keyWord = AppSettings.getRecepitType(Objects.requireNonNull(getContext()));

                        switch (keyWord) {
                            case "No":
                                break;
                            case "ShareImage":
                                createShareImageInBackground(order, printDetails, company);
                                break;
                            case "SendSMS":
                                sendSmsToCustomer(MainActivity.customerNameSms, MainActivity.customerPhoneSms);
                                break;


                    }
                }
            }


        clearData();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void openImage(Bitmap bitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse(root + "/temporary_file.jpg"));
        startActivity(Intent.createChooser(share, "Share Image"));

    }

    private void setData(String id, String item) {
        orderId.setText(id);
        noOfItem.setText(item);
    }

    @SuppressWarnings("rawtypes")
    private void createShareImageInBackground(Order order, List<OrderDetails> list, Company company) {

        @SuppressLint("StaticFieldLeak") AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Bitmap bitmap = createSalesReceiptImage(order, list, company);
                openImage(bitmap);
                return null;
            }
        };
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void getAmount(Double receive, Double balance) {
        receivedAmountSms = receive;
        balanceAmountSms = balance;
    }

    private void sendSmsToCustomer(String name, String phone) {
        if (name != null && phone != null) {
            sentSMS(name, phone);
        }
    }


    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void orderComplete(String completeOrder, String orderId) {
        if (completeOrder != null) {
            Intent completeActivity = new Intent(getActivity(), OrderCompleteActivity.class);
            completeActivity.putExtra("ORDERID", orderId);
            completeActivity.putExtra("TOTAL", totalComplete);
            completeActivity.putExtra("RECEIVED", receivedComplete);
            completeActivity.putExtra("BALANCE", balanceComplete);
            completeActivity.putExtra("RESPONSE", completeOrder);
            completeActivity.putExtra("TYPE", salesType);
            if (paymentMethod == 1004 && receivedComplete.equalsIgnoreCase("0.00")) {
                completeActivity.putExtra("COMPLETED", "COMPLETED");
            } else {
                completeActivity.putExtra("COMPLETED", "C");
            }
            startActivity(completeActivity);
        }

        assert completeOrder != null;
        if (completeOrder.equalsIgnoreCase("1")) {
            totalReceived.setText("0.00");
            balanceAmount.setText("0.00");
            totalAmount.setLength(0);
            customerName.setText("");
            customerId.setText("");

            mListener.clearSelectIdlist();
            mListener.onAcceptButtonClick();
            OrderListFragment.setDiscount = 0;
            WallActivity.categoryClick = 0;

            customerName.setText("");
            customerId.setText("");

            customerLayout.setVisibility(View.GONE);

            paymentMethod = 1001;
            cash.setBackgroundColor(R.color.toolBarBlue);
            cheque.setBackgroundColor(Color.LTGRAY);
            card.setBackgroundColor(Color.LTGRAY);
            credit.setBackgroundColor(Color.LTGRAY);
        }
    }

    void setType(String type){
        salesType = type;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void settlementComplete(String completeSettlement, Payment payment1) {
        if (completeSettlement.equalsIgnoreCase("1")) {
            if(OrderCompleteActivity.btnComplete  != null) {
                OrderCompleteActivity.btnComplete.setText("Completed");
            }
            Objects.requireNonNull(getActivity()).finish();
        } else {
            ToastMessageHelper.customErrToast(Objects.requireNonNull(getActivity()), "Payment details is not completed");
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void settlementNotComplete(VolleyError error) {
        ShowMsg.parseVolleyError(getActivity(), error);
        if(OrderCompleteActivity.btnComplete  != null) {
            OrderCompleteActivity.btnComplete.setText("RESEND");
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        void onAcceptButtonClick();

        void clearSelectIdlist();

        void onpaymentBackButtonClick();


    }

    void clearData() {
        currentOrder = null;
        orderDetails.clear();

    }

    private void changeInterFaceLanguage(String type) {
        if (type.equalsIgnoreCase("සිංහල")) {
            paymentTotal.setText("නිසා මුළු");
            paymentReceived.setText("ලැබුණි");
            paymentBalance.setText("ශේෂය");
        } else if (type.equalsIgnoreCase("தமிழ்")) {
            paymentTotal.setText("மொத்தம்");
            paymentReceived.setText("பெறுவனவு");
            paymentBalance.setText("மீதி");
        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    public void onActivityResult(int mRequestCode, int mResultCode,
                                 Intent mDataIntent) {
        super.onActivityResult(mRequestCode, mResultCode, mDataIntent);

        if (mRequestCode == 10) {
            if (mResultCode == RESULT_OK) {
                String[] result = mDataIntent.getStringArrayExtra("select customer data");
                if (result != null) {
                    currentOrder.setOrderCustomerId(Integer.parseInt(result[0]));
                    customerId.setText(result[0]);
                    customerName.setText(result[1]);
                }

            }
        }
    }


    private int paymentMethodType(String method) {
        int x;
        switch (method) {
            case "Cash":
                x = 1001;
                break;
            case "Card":
                x = 1003;
                break;
            case "Cheque":
                x = 1002;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + method);
        }
        return x;
    }
}
