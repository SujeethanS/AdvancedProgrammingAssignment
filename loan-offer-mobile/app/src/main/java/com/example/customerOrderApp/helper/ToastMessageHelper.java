package com.example.customerOrderApp.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.customerOrderApp.R;

/**
 * Created by keert on 04/01/2017.
 */

public class ToastMessageHelper {
    private static Toast warningToast;
    @SuppressLint("NewApi")
    public static void customErrToast(Activity activity, String message){
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                activity.findViewById(R.id.toast_layout_root));

        layout.setBackground(ContextCompat.getDrawable(activity, R.drawable.err_toast_design));

        ImageView image = layout.findViewById(R.id.image);
        image.setImageResource(R.drawable.err_toast);
        TextView text = layout.findViewById(R.id.text);
        text.setText(message);

        if (warningToast != null){
            warningToast.cancel();
        }

        warningToast = new Toast(activity.getApplicationContext());
        warningToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        warningToast.setDuration(Toast.LENGTH_LONG);
        warningToast.setView(layout);
        warningToast.show();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void customSuccToast(Activity activity, String message){
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                activity.findViewById(R.id.toast_layout_root));

        layout.setBackground(ContextCompat.getDrawable(activity, R.drawable.succ_toast_design));

        ImageView image = layout.findViewById(R.id.image);
        image.setImageResource(R.drawable.succ_toast_img);
        TextView text = layout.findViewById(R.id.text);
        text.setText(message);

        if (warningToast != null){
            warningToast.cancel();
        }

        warningToast = new Toast(activity.getApplicationContext());
        warningToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        warningToast.setDuration(Toast.LENGTH_SHORT);
        warningToast.setView(layout);
        warningToast.show();
    }


}
