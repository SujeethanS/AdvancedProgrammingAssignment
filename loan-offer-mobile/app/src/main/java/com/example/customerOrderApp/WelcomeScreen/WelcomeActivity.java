package com.example.customerOrderApp.WelcomeScreen;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.customerOrderApp.CheckInternetConnection;
import com.example.customerOrderApp.R;
import com.example.customerOrderApp.WallActivity;
import com.example.customerOrderApp.adapter.ViewPageAdapter;
import com.example.customerOrderApp.helper.CheckOnline;
import com.example.customerOrderApp.helper.PrefManager;
import com.example.customerOrderApp.helper.Validation;
import com.google.android.material.tabs.TabLayout;

public class WelcomeActivity extends AppCompatActivity implements RegistrationScreen1.OnFragmentInteractionListener {
    private ViewPager viewPager;

    private LinearLayout dotsLayout;

    private PrefManager prefManager;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        if (!CheckOnline.isOnline(WelcomeActivity.this)) {
            Intent startIntent = new Intent(WelcomeActivity.this, CheckInternetConnection.class);
            startActivity(startIntent);
        }

        dotsLayout = findViewById(R.id.layoutDots);
        // adding bottom dots
        addBottomDots(0);

        Button btnSkip = findViewById(R.id.btn_skip);
        Button btnNext = findViewById(R.id.btn_next);

        btnSkip.setOnClickListener(v -> launchHomeScreen());

        btnNext.setOnClickListener(v -> {
            int current = getItem(+1);
            if (current < 6) {
                viewPager.setCurrentItem(current);
            } else {
                    launchHomeScreen();
            }
        });

        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
                launchHomeScreen();
                finish();
        }

        TabLayout tabLayoutWelcome = findViewById(R.id.tabLayoutWelcome);

        viewPager = findViewById(R.id.view_pagerWelcome);
        ViewPageAdapter viewPagerAdapter = new ViewPageAdapter(getSupportFragmentManager());

        RegistrationScreen1 registrationScreen1 = new RegistrationScreen1();


        viewPagerAdapter.addFragment(registrationScreen1, "");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayoutWelcome.setupWithViewPager(viewPager);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);

    }

    private void addBottomDots(int currentPage) {
        TextView[] dots = new TextView[6];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[0]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[0].setTextColor(colorsActive[0]);
    }

    private void launchHomeScreen() {
            prefManager.setFirstTimeLaunch(false);

            Intent i = new Intent(WelcomeActivity.this, WallActivity.class);
            startActivity(i);
            finish();
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Validation.HideBottomNavigationCall(this);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void registerChangeScreen() {
        Intent intent = new Intent(WelcomeActivity.this, WallActivity.class);
        startActivity(intent);
        finish();
    }
}
