package com.example.customerOrderApp.Service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.customerOrderApp.helper.AppSettings;
import com.example.customerOrderApp.helper.ConversionClass;
import com.example.customerOrderApp.helper.ToastMessageHelper;
import com.example.customerOrderApp.pojo.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dev1 on 11/1/17.//54.201.243.114
 */

public class AsyncTaskRegisterUserPhone extends AsyncTask<String,String,String> {

    private Context context;
    private String phoneNumber;
    //private DBHandler dbHandler;
    private ProgressDialog progressReg;
    private String userId;

    public AsyncTaskRegisterUserPhone(Context context, String phoneNumber, String userId){
        this.context = context;
       // this.dbHandler = DBSingleton.getInstance(context);
        this.phoneNumber = phoneNumber;
        this.userId = userId;
    }

    @Override
    protected void onPreExecute() {
        progressReg = ProgressDialog.show(context, "Register your phone number ", "Please wait...", true);
    }

    @Override
    protected String doInBackground(String... strings) {
        String baseURL = AppSettings.getURLs(context);
        //User user = dbHandler.getUserByID(AppSettings.getUserSession(context));
        User user = new User();//dbHandler.getUserByID(Integer.parseInt(userId));

        //String url = baseURL+"api/register/register-phone-verify/"+user.getUserUniqueId()+"/"+phoneNumber;
        String url = baseURL+"api/auth/register-phone-verify?phone_number="+phoneNumber+"&user_id="+ ConversionClass.getUUID();
        String clientId = AppSettings.getClientId(context);
        String companyId = AppSettings.getCompanyId(context);

        test(url,clientId,companyId);
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        progressReg.dismiss();
        ToastMessageHelper.customSuccToast((Activity) context,"Phone number registered");
    }

    private void test(String url, String clientId, String companyId){
        try { /// start here

            // String url1 = "http://url.com";
            URL object = new URL(url);

            //String clientId = AppSettings.getClientId(MainActivity.appContext);
            //String companyId = AppSettings.getCompanyId(MainActivity.appContext);

            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            /*con.addRequestProperty("phone-number", AppSettings.convertNumber(context));
            con.addRequestProperty("authorization",AppSettings.getToken(context));*/
            //con.setRequestProperty("Content-Type", "application/json");
            //con.setRequestProperty("Accept", "application/json");

            con.setRequestProperty("company_id",companyId);
            con.setRequestProperty("client_id",clientId);

            con.setRequestMethod("POST");

            //JSONObject cred = new JSONObject();
            //JSONObject auth = new JSONObject();
            JSONObject parent = new JSONObject();

            // cred.put("username", "adm");
            // cred.put("password", "pwd");

            // auth.put("tenantName", "adm");
            // auth.put("passwordCredentials", cred.toString());

            // parent.put("auth", "pcs");
            //parent.accumulate("token",token);
           // parent.accumulate("phone_number", phoneNumber);

            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(parent.toString());
            wr.flush();


            StringBuilder sb = new StringBuilder();
            int HttpResult = con.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
                Log.e("result",sb.toString());
                System.out.println("" + sb.toString());
               // dbHandler.updateUserStatus(Integer.parseInt(userId),1);

                Log.d("serverRU", con.getResponseMessage());

            } else {
                //System.out.println(con.getResponseMessage());
                Log.d("serverRU", con.getResponseMessage());
            }

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
    }

}
