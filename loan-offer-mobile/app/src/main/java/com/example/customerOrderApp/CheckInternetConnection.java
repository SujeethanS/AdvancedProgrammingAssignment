package com.example.customerOrderApp;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customerOrderApp.helper.CheckOnline;
import com.example.customerOrderApp.helper.NetworkQuality;
import com.example.customerOrderApp.helper.ToastMessageHelper;

import java.lang.reflect.Method;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class CheckInternetConnection extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_check_internet_connection);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_connection);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
          findViewById(R.id.wifi_button).setOnTouchListener(mDelayHideTouchListener);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        ImageView wifi = findViewById(R.id.wifi_button);
        wifi.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                //startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                try {
                    WifiManager wifi = (WifiManager)
                            CheckInternetConnection.this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

                    WifiConfiguration wc = new WifiConfiguration();
                    wc.SSID = "\"SSIDName\"";
                    wc.preSharedKey  = "\"password\"";
                    wc.hiddenSSID = true;
                    wc.status = WifiConfiguration.Status.ENABLED;

                    wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                    wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                    wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);

                    wc.allowedPairwiseCiphers
                            .set(WifiConfiguration.PairwiseCipher.TKIP);
                    wc.allowedPairwiseCiphers
                            .set(WifiConfiguration.PairwiseCipher.CCMP);
                    wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);

                    boolean b=wifi.isWifiEnabled();
                    if (b) {
                        // wifi.setWifiEnabled(false);
                        //Toast.makeText(AddItem.this, "yes", Toast.LENGTH_SHORT).show();
                    } else {
                        wifi.setWifiEnabled(true);
                        Toast.makeText(CheckInternetConnection.this, "WIFI ON", Toast.LENGTH_SHORT).show();
                    }
                    //Log.d("WifiPreference", "enableNetwork returned " + b );

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ImageView network = findViewById(R.id.internet_button);
        network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //isDataRoamingEnabled(CheckInternetConnection.this);
                Toast.makeText(CheckInternetConnection.this, "INTERNET ON", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.android.settings",
                        "com.android.settings.Settings$DataUsageSummaryActivity"));
                startActivityForResult(intent, 1);

            }
        });


        Button retry = findViewById(R.id.retry_button);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckOnline checkOnline = new CheckOnline();
                if(CheckOnline.isOnline(CheckInternetConnection.this)) {
                    if (NetworkQuality.isConnectedToThisServer("www.google.com")){
                        finish();
                    }else {
                        ToastMessageHelper.customErrToast(CheckInternetConnection.this, "Your network data is low");
                    }

                }else {
                    ToastMessageHelper.customErrToast(CheckInternetConnection.this, "Please connect your network");

                }
            }
        });
        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    public Boolean isDataRoamingEnabled(Context context) {
        try {
            // return true or false if data roaming is enabled or not
            return Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.DATA_ROAMING) == 1;
        }
        catch (Settings.SettingNotFoundException e) {
            // return null if no such settings exist (device with no radio data ?)
            return null;
        }
    }

    public void setMobileDataState(boolean mobileDataEnabled)
    {
        try
        {
            TelephonyManager telephonyService = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

            Method setMobileDataEnabledMethod = telephonyService.getClass().getDeclaredMethod("setDataEnabled", boolean.class);

            if (null != setMobileDataEnabledMethod)
            {
                setMobileDataEnabledMethod.invoke(telephonyService, mobileDataEnabled);
            }
        }
        catch (Exception ex)
        {
           // Log.e("", "Error setting mobile data state", ex);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
