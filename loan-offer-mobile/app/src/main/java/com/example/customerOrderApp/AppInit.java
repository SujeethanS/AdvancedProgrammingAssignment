package com.example.customerOrderApp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.example.customerOrderApp.helper.Validation;
import com.pos.device.SDKManager;
import com.pos.device.SDKManagerCallback;
import com.printlib.kale.kaleprintsdk.PrinterUtilHelper;

public class AppInit extends Application {
    public static Context appContext;
    private static PrinterUtilHelper utilHelper;

    Activity activity;

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = getApplicationContext();

        if(Validation.getModelNEW9220()){
            init();
        }
    }

    private void init(){
        appContext = getApplicationContext();
        SDKManager.init(AppInit.appContext, new SDKManagerCallback() {
            @Override
            public void onFinish() {
               // LogUtil.e("init SDK success(threadname: "+Thread.currentThread().getName()+")");
            }
        });

        // PrintManager.init(getApplicationContext());
    }

    public static PrinterUtilHelper getUtilHelper() {
        return utilHelper;
    }

    public static void setUtilHelper(PrinterUtilHelper utilHelper) {
        AppInit.utilHelper = utilHelper;
    }
}
