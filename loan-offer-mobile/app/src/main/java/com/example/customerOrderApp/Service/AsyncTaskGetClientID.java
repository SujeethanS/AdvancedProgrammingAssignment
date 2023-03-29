package com.example.customerOrderApp.Service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.customerOrderApp.Json.WebOrdersJSONParser;
import com.example.customerOrderApp.http.HttpManager;


/**
  Created by dev1 on 9/15/17.
 */

public class AsyncTaskGetClientID extends AsyncTask<String,String,String> {

    public interface GetClientIdDelegate{
        void clientIDSendBack(String clientID);
    }

    private Context context;
    private GetClientIdDelegate getClientIdDelegate;
    private ProgressDialog progressClientId;


    public AsyncTaskGetClientID(Context context, GetClientIdDelegate getClientIdDelegate){
        this.context = context;
        this.getClientIdDelegate =getClientIdDelegate;
    }

    @Override
    protected void onPreExecute() {
        progressClientId = ProgressDialog.show(context, "Register your company ", "Please wait...", true);
    }

    @Override
    protected String doInBackground(String... params) {
        Log.e("+", " " + params[0]);
        return HttpManager.getData(context,params[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressClientId.dismiss();
        if(s != null){
           // Log.e("ID",s);
            String clientID = WebOrdersJSONParser.parseFeedClientID(s);
            getClientIdDelegate.clientIDSendBack(clientID);
        }
    }
}
