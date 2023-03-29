package com.example.customerOrderApp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.example.customerOrderApp.helper.AppSettings;
import com.example.customerOrderApp.helper.Validation;


import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);

        startAnimation();
        //processIntent();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                //Intent i = new Intent(SplashScreen.this, LoginPinActivity.class);
                if(AppSettings.getStepper(SplashActivity.this)){
                    if (AppSettings.getLogin(SplashActivity.this) == 0) {
                        Intent i = new Intent(SplashActivity.this, UserLogin.class);
                        startActivity(i);
                        finish();
                    } else {
                        Intent i = new Intent(SplashActivity.this, WallActivity.class);
                        startActivity(i);
                        finish();
                    }
                }else {
                    Intent i = new Intent(SplashActivity.this, StepperActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 1000);

        if (AppSettings.getRegisterCloud(this) == 1) {
            /*loadReOrder();
            loadExpiry();*/
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

       /* int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        String toastMsg;
        switch(screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                toastMsg = "Large screen";
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                toastMsg = "Normal screen";
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                toastMsg = "Small screen";
                break;
            default:
                toastMsg = "Screen size is neither large, normal or small";
        }
        Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();*/
    }
    /*//check allIsWell function
    private void processIntent(){

        AllIsWell allIsWell = new AllIsWell();
        startAnimation();


        if (allIsWell.wellCheck(this)) {

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    //Intent i = new Intent(SplashScreen.this, LoginPinActivity.class);

                        Intent i = new Intent(SplashActivity.this, WallActivity.class);
                        startActivity(i);

                    finish();
                }
            }, 1000);

            // this function for notification
            //processNotification();
        } else {

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(SplashActivity.this);
                    builder1.setMessage("Your device is not supported ");
                    builder1.setCancelable(false);

                    builder1.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    System.exit(0);
                                }
                            });


                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            }, 1000);
        }

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle(R.string.on_back_button_title);
        builder.setMessage("Need to Restart");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {

                processIntent();
            }
        });

        builder.show();
    }*/

    //Animation function
    public void startAnimation() {

        ImageView kaleLogo = findViewById(R.id.kalesystempos);
        TextView intelligentBusiness = findViewById(R.id.intelligentbusiness);

        Animation leftToRight = AnimationUtils.loadAnimation(this, R.anim.lefttoright);
        //kaleLogo.setAnimation(leftToRight);

        Animation blink = AnimationUtils.loadAnimation(this, R.anim.blink);
        intelligentBusiness.setAnimation(blink);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Validation.hideSystemUI(this);
        }
    }
}
