package com.example.customerOrderApp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.customerOrderApp.helper.AppSettings;
import com.example.customerOrderApp.helper.ToastMessageHelper;
import com.example.customerOrderApp.helper.Validation;

import java.text.DecimalFormat;
import java.util.Objects;


public class OrderCompleteActivity extends AppCompatActivity {


    ImageView ImgSuccess;

    ImageView ImgError;
    TextView txtOrderId,txtTotal,txtReceived,txtBalance;
    @SuppressLint("StaticFieldLeak")
    public static Button btnComplete;
    private String type ="";

    private final DecimalFormat df = new DecimalFormat("#,###,##0.00");

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_complete);

        Validation.HideBottomNavigationCall(this);

        ConstraintLayout constraintLayoutMain = findViewById(R.id.mainCompleteLayout);
        TextView txtMobilePos = findViewById(R.id.txtkaleMobilePos);
        FrameLayout frameLayoutFirst = findViewById(R.id.frameIcon);

        txtOrderId = findViewById(R.id.orderIdComplete);
        txtTotal = findViewById(R.id.totalComplete);
        txtReceived = findViewById(R.id.receivedComplete);
        txtBalance = findViewById(R.id.balanceComplete);
        TextView txtCompleteMsg = findViewById(R.id.orderCompleteMsg);
        btnComplete = findViewById(R.id.btnGoToOrderComplete);

        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation blink = AnimationUtils.loadAnimation(this, R.anim.blink);

        ImgSuccess = findViewById(R.id.giftSuccess);
        ImgError = findViewById(R.id.giftError);

        ImgError.setVisibility(View.GONE);

        ImgSuccess.setVisibility(View.VISIBLE);

        ImgSuccess.setAnimation(fadeIn);

        String orderId = getIntent().getStringExtra("ORDERID");
        double total = Double.parseDouble(Objects.requireNonNull(getIntent().getStringExtra("TOTAL")));
        double received = Double.parseDouble(Objects.requireNonNull(getIntent().getStringExtra("RECEIVED")));
        double balance = Double.parseDouble(Objects.requireNonNull(getIntent().getStringExtra("BALANCE")));
        String response = (getIntent().getStringExtra("RESPONSE"));
        String complete = getIntent().getStringExtra("COMPLETED");
        type = getIntent().getStringExtra("TYPE");

        assert complete != null;
        if(complete.equalsIgnoreCase("COMPLETED")){
             btnComplete.setText("COMPLETED");
         }

        assert response != null;
        if(response.equalsIgnoreCase("0")){
             constraintLayoutMain.setBackgroundColor(getResources().getColor(R.color.DarkRed));
             txtMobilePos.setBackgroundColor(getResources().getColor(R.color.DarkRed));
             frameLayoutFirst.setBackgroundColor(getResources().getColor(R.color.red));

             ImgSuccess.setVisibility(View.GONE);
             txtCompleteMsg.setText("Not Completed");
             txtCompleteMsg.setTextColor((getResources().getColor(R.color.DarkRed)));

             ImgError.setVisibility(View.VISIBLE);

             btnComplete.setText("RESEND");

             ImgError.setAnimation(blink);
         }

         txtOrderId.setText(orderId);
         txtTotal.setText(df.format(total));
         txtReceived.setText(df.format(received));
         txtBalance.setText(df.format(balance));

        btnComplete.setOnClickListener(v -> {
            if(btnComplete.getText().toString().equalsIgnoreCase("Completed") || btnComplete.getText().toString().equalsIgnoreCase("RESEND")) {
                clearFeild();
                if(type.equalsIgnoreCase("Normal")) {
                    Intent mainActivity = new Intent(OrderCompleteActivity.this, MainActivity.class);
                    String type1 = "Normal";
                    mainActivity.putExtra("NORMAL", type1);
                    startActivity(mainActivity);
                }else {
                    Intent mainActivity1 = new Intent(OrderCompleteActivity.this, MainActivity.class);
                    String type2 = "Standard";
                    mainActivity1.putExtra("Standard", type2);
                    startActivity(mainActivity1);
                }
                finish();
            }else {
                ToastMessageHelper.customErrToast(OrderCompleteActivity.this,"Please wait...");
            }
        });
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume(){
        super.onResume();
        AppSettings.setActivityLive(this,false);
        btnComplete.setText("COMPLETED");
        Validation.HideBottomNavigationCall(this);
    }
    private void clearFeild(){

        txtOrderId.setText("");
        txtTotal.setText("");
        txtReceived.setText("");
        txtBalance.setText("");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
