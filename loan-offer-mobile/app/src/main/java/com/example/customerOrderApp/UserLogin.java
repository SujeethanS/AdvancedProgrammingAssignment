package com.example.customerOrderApp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.customerOrderApp.Service.VolleyBackgroundTaskPost;
import com.example.customerOrderApp.helper.AppSettings;
import com.example.customerOrderApp.helper.CheckOnline;
import com.example.customerOrderApp.helper.ToastMessageHelper;
import com.example.customerOrderApp.helper.Validation;

public class UserLogin extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUserNameLogin, editTextPasswordLogin;

    private Button btnLogin;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        btnLogin = findViewById(R.id.btnUserLogin);

        editTextUserNameLogin = findViewById(R.id.txtLoginUserName);
        editTextPasswordLogin = findViewById(R.id.txtLoginPassword);

        btnLogin.setOnClickListener(this);

        if (!CheckOnline.isOnline(this)) {
            Intent startIntent = new Intent(UserLogin.this, CheckInternetConnection.class);
            startActivity(startIntent);
        }

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnUserLogin) {
            if (InputValidator(editTextUserNameLogin.getText().toString())) {
                editTextUserNameLogin.setError("Please enter User Name");
            } else if (InputValidator(editTextPasswordLogin.getText().toString())) {
                editTextPasswordLogin.setError("Please enter Password");
            } else {

                if (editTextPasswordLogin.getText().toString().trim().length() > 0 && editTextUserNameLogin.getText().toString().trim().length() > 0) {

                    String userName = editTextUserNameLogin.getText().toString().trim();
                    String password = editTextPasswordLogin.getText().toString().trim();

                    String url = "http://10.0.2.2:8080/user/login";
                    VolleyBackgroundTaskPost.userLogin(UserLogin.this, url, userName, password, new VolleyBackgroundTaskPost.UserLoginDelegate() {
                        @Override
                        public void notifyUserLogin(boolean success) {

                            if (success) {
                                btnLogin.setEnabled(false);
                                Intent intent = new Intent(UserLogin.this, WallActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                } else {
                    ToastMessageHelper.customErrToast(UserLogin.this, "Please Enter your UserName & Password!");
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to exit?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                System.exit(0);
            });
            builder.setNegativeButton("No", (dialog, which) -> {

            });
            builder.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    //==============================================================================================
    //==============================================================================================
    //hide bottom navigation bar
    //==============================================================================================
    //==============================================================================================
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Validation.HideBottomNavigationCall(this);
        }
    }

    // string validator
    private boolean InputValidator(String data) {
        return !(data.trim().length() >= 1);
    }

}
