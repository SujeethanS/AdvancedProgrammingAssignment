package com.example.customerOrderApp.WelcomeScreen;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.customerOrderApp.R;
import com.example.customerOrderApp.Service.AsyncTaskGetClientID;
import com.example.customerOrderApp.Service.AsyncTaskRegisterPOSUser;
import com.example.customerOrderApp.Service.VolleyBackgroundTaskPost;
import com.example.customerOrderApp.helper.AppSettings;
import com.example.customerOrderApp.helper.CheckInternetCon;
import com.example.customerOrderApp.helper.CheckOnline;
import com.example.customerOrderApp.helper.ConversionClass;
import com.example.customerOrderApp.helper.ToastMessageHelper;
import com.example.customerOrderApp.helper.Validation;
import com.example.customerOrderApp.pojo.Company;
import com.example.customerOrderApp.pojo.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class RegistrationScreen1 extends Fragment implements AsyncTaskGetClientID.GetClientIdDelegate, VolleyBackgroundTaskPost.ResultLocationStartDelegate {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //REGISTRATION SCREEN
    private String selectedIndustryType = "Multi Shop";
    private Company company = new Company();
    private String baseUrl;

    private ConstraintLayout constraintLayoutRegistration;

    private LinearLayout linearNewRegistration;
    private LinearLayout linearActivation;
    private EditText editTextCompanyName;
    private EditText editTextCompanyAddress;
    private EditText editTextCompanyNumber;
    private EditText editTextCompanyLocationID;
    private EditText edtPhoneNumber;
    private EditText edtActivateCode;
    private EditText edtActivateLocation;
    private Button btn_Register;

    RegistrationScreen1 registrationScreen1;

    public RegistrationScreen1() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RegistrationScreen1 newInstance(String param1, String param2) {
        RegistrationScreen1 fragment = new RegistrationScreen1();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        registrationScreen1 = this;
        return inflater.inflate(R.layout.fragment_registration_screen1, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        baseUrl = AppSettings.getURLs(getActivity());

        constraintLayoutRegistration = view.findViewById(R.id.registerConstain);

        linearNewRegistration = view.findViewById(R.id.leanear_newRegistration_popup);
        editTextCompanyName = view.findViewById(R.id.companyName_popup);
        editTextCompanyNumber = view.findViewById(R.id.companyNumber_register_popup);
        editTextCompanyAddress = view.findViewById(R.id.companyAddress_popup);
        Spinner spinnerIndustryType = view.findViewById(R.id.industryType_spinner_popup);
        editTextCompanyLocationID = view.findViewById(R.id.locationId_register_popup);
        TextView textViewAlreadyRegister = view.findViewById(R.id.txt_already_register);
        btn_Register = view.findViewById(R.id.button_Register_popup);

        List<String> listIndustryType = new ArrayList<>();
        listIndustryType.add("Industry Type");
        listIndustryType.add("Multi Shop");
        listIndustryType.add("Pharmacy");
        listIndustryType.add("Restaurant");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item, listIndustryType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIndustryType.setAdapter(adapter);

        spinnerIndustryType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Industry Type")){
                    selectedIndustryType = "Multi Shop";
                } else{
                    selectedIndustryType = parent.getItemAtPosition(position).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //==========================================================================================
        //==========================================================================================
        //Activation layout
        //==========================================================================================
        //==========================================================================================
        linearActivation = view.findViewById(R.id.leanear_Activation_popup);

        edtPhoneNumber = view.findViewById(R.id.companyNumber_activate_popup);
        edtActivateCode = view.findViewById(R.id.editText_ActivateCode);
        edtActivateLocation = view.findViewById(R.id.locationId_activation_popup);

        Button btn_Activate = view.findViewById(R.id.button_Acticate_popup);

        final TextView textViewNewRegister = view.findViewById(R.id.txt_new_register);


        textViewAlreadyRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearActivation.setVisibility(View.VISIBLE);
                linearNewRegistration.setVisibility(View.GONE);
            }
        });

        textViewNewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearActivation.setVisibility(View.GONE);
                linearNewRegistration.setVisibility(View.VISIBLE);
            }
        });

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (Validation.validateEditText(new EditText[]{editTextCompanyName,editTextCompanyAddress,editTextCompanyLocationID,editTextCompanyNumber})) {
                        btn_Register.setEnabled(false);
                        //company details
                        AppSettings.setCompanyName(Objects.requireNonNull(getActivity()),editTextCompanyName.getText().toString());
                        AppSettings.setCompanyAddress(getActivity(),editTextCompanyAddress.getText().toString());
                        AppSettings.setCompanyContactNumber(getActivity(),editTextCompanyNumber.getText().toString());
                        AppSettings.setCompanyIndustryType(getActivity(),selectedIndustryType);
                        AppSettings.setCompanyLocationId(getActivity(),Integer.parseInt(editTextCompanyLocationID.getText().toString()));

                        //registerCompany();
                        constraintLayoutRegistration.setClickable(false);
                        constraintLayoutRegistration.setEnabled(false);
                        textViewAlreadyRegister.setVisibility(View.INVISIBLE);

                        companyDetailsPOST();
                    }
            }
        });


        btn_Activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*if (!InputValidator(String.valueOf(etxLocation.getText().toString()))) {
                    etxLocation.setError("Please enter Location ID");
                }
                else {

                    Intent intent = new Intent(WallActivity.this, MainActivity.class);
                    AppSettings.setLocationId(WallActivity.this, Integer.valueOf(etxLocation.getText().toString()));
                    startActivity(intent);
                    dialog.cancel();

                }*/

                if (edtPhoneNumber.getText().toString().trim().length() > 0 && !edtPhoneNumber.getText().toString().trim().equalsIgnoreCase(".") ) {

                   if (edtActivateCode.getText().toString().trim().length() > 0 && !edtActivateCode.getText().toString().trim().equalsIgnoreCase(".")) {
                       String phoneNo = edtPhoneNumber.getText().toString().trim();
                       String code = edtActivateCode.getText().toString().trim();

                       //api/auth/register-phone-user?token=2&phone_number=%2B94750413968
                       String itemURL = baseUrl + "api/auth/register-phone-user?token=" + code + "&phone_number=%2B94" + phoneNo;
                       VolleyBackgroundTaskPost.postActivation(getActivity(), itemURL, new VolleyBackgroundTaskPost.ActivationCompleteResponse() {
                           @Override
                           public void ActivationComplete(String completeActivation) {
                               if(completeActivation.equalsIgnoreCase("1")){
                                   mListener.registerChangeScreen();
                               }
                           }
                       });
                       //http://54.200.81.66:3000/api/auth/register-phone-user?token=123456&phone_number=94750413968

                       if (edtActivateLocation.getText().toString().trim().length() > 0) {
                           int location = Integer.parseInt(edtActivateLocation.getText().toString().trim());
                           AppSettings.setCompanyLocationId(Objects.requireNonNull(getActivity()), location);
                       } else {
                           AppSettings.setCompanyLocationId(Objects.requireNonNull(getActivity()), 1);
                       }
                   }else {
                       ToastMessageHelper.customErrToast(Objects.requireNonNull(getActivity()),"Enter correct code");
                    }
                }else{
                    ToastMessageHelper.customErrToast(Objects.requireNonNull(getActivity()),"Enter correct Number");
                }

            }
        });

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
    public void clientIDSendBack(String clientID) {
        Log.e("MOVE","Screeeen 1");

        String comId = AppSettings.getCompanyId(Objects.requireNonNull(getActivity()));
        AppSettings.setClientId(getActivity(),clientID);
        AppSettings.setCompanyId(getActivity(),comId);
        AppSettings.setCompanyLocationId(getActivity(),company.getLocationId());
        AppSettings.setRegisterCloud(getActivity(),1);
        AppSettings.setActivateCloud(getActivity(),1);

        AsyncTaskRegisterPOSUser registerPOSUser = new AsyncTaskRegisterPOSUser(getActivity(), registrationScreen1);
        registerPOSUser.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    @Override
    public void sendLocationStartDetails() {
        mListener.registerChangeScreen();

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void registerChangeScreen();
    }

    //Register Company Popup
    private void registerCompany(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Do you want to register?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    @SuppressLint("HardwareIds")
                    public void onClick(DialogInterface dialog, int id) {

                        if (CheckOnline.isOnline(Objects.requireNonNull(getActivity()))) {
                            AppSettings.setUniqueId(getActivity(), ConversionClass.getUUID());
                            AppSettings.setCompanyId(getActivity(), Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));

                            AsyncTaskGetClientID clientID = new AsyncTaskGetClientID(getActivity(),registrationScreen1 );
                            //// TODO: 9/16/17 save this url in shared preference
                            String baseURL = AppSettings.getURLs(getActivity());
                            Log.d("getUniqueId", baseURL);
                            clientID.execute(baseURL + "api/auth/clientID");
                            //AppSettings.setRegisterCloud(getActivity(), 1);


                            dialog.cancel();

                        } else {
                            CheckInternetCon.networkError(getActivity());
                        }


                    }
                });

        builder1.setNegativeButton(
                "No",
                (dialog, id) -> dialog.cancel());

        AlertDialog alert1 = builder1.create();
        alert1.show();

    }

    private void companyDetailsPOST(){

        Company company = new Company();
        company.setCompanyName(AppSettings.getCompanyName(Objects.requireNonNull(getActivity())));
        company.setCompanyDesc(AppSettings.getCompanyName(getActivity()));
        company.setCompanyAddress(AppSettings.getCompanyAddress(getActivity()));
        company.setCompanyContactNumber(AppSettings.getCompanyContactNumber(getActivity()));
        company.setCompanyContactName(AppSettings.getCompanyContactName(getActivity()));
        company.setLocationId(AppSettings.getCompanyLocationId(getActivity()));
        company.setCloudComId(AppSettings.getCompanyId(getActivity()));
        company.setClientId(AppSettings.getClientId(getActivity()));
        company.setCompanyMessage(AppSettings.getCompanyMessage(getActivity()));
        company.setCompanyIndustry(AppSettings.getCompanyIndustryType(getActivity()));

        VolleyBackgroundTaskPost.postCompanyDetails(getActivity(), baseUrl + "api/com/company", company, new VolleyBackgroundTaskPost.PostCompanyDelegate() {
            @Override
            public void notifyCompany(boolean success) {
                if(success){
                    locationDetailsPOST();
                }else {
                    btn_Register.setEnabled(true);
                    ToastMessageHelper.customErrToast(Objects.requireNonNull(getActivity()),"Failed");
                }
            }
        });
    }

    private void locationDetailsPOST(){

        // Post default locations
        Location location = new Location();
        location.setLocationId(1);
        location.setLocationName("Main Store");
        location.setLocationAddress("n/l");

        VolleyBackgroundTaskPost.postLocationDetails(getActivity(), baseUrl + "api/inv/inventory-location", location, new VolleyBackgroundTaskPost.PostLocationDelegate() {
            @Override
            public void notifyAddLocation(boolean success) {
                if(success){
                    btn_Register.setEnabled(true);
                    mListener.registerChangeScreen();
                    ToastMessageHelper.customSuccToast(Objects.requireNonNull(getActivity()),"Saved");
                }else {
                    ToastMessageHelper.customErrToast(Objects.requireNonNull(getActivity()),"Not Saved");
                }
            }
        });
    }
}
