package com.example.customerOrderApp.Singleton;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {

    private static Context mCntx;
    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;

    public static VolleySingleton getmInstance(Context context) {
        if(mInstance == null){
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    private VolleySingleton(Context context){
        mCntx = context;
        mRequestQueue = getmRequestQueue();
    }

    private RequestQueue getmRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mCntx.getApplicationContext());
        }

        return mRequestQueue;
    }

    public<T> void addToRequestQueue(Request<T> request){
        mRequestQueue.add(request);
    }
}
