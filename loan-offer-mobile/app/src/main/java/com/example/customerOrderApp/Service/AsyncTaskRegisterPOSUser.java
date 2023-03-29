package com.example.customerOrderApp.Service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.customerOrderApp.Json.WebOrdersJSONParser;
import com.example.customerOrderApp.WelcomeScreen.RegistrationScreen1;
import com.example.customerOrderApp.helper.AppSettings;
import com.example.customerOrderApp.pojo.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
  Created by dev1 on 11/1/17.
 */

public class AsyncTaskRegisterPOSUser extends AsyncTask<String,String,String> {

    private Context context;
    private ProgressDialog progressRegister;
    RegistrationScreen1 registrationScreen1;

    public AsyncTaskRegisterPOSUser(Context context, RegistrationScreen1 registrationScreen1) {
        this.context = context;
        this.registrationScreen1 = registrationScreen1;
    }

    @Override
    protected void onPreExecute() {
        progressRegister = ProgressDialog.show(context, "Register all users", "Registering...", true);
    }

    @Override
    protected String doInBackground(String... strings) {
        String baseURL = AppSettings.getURLs(context);
        String URL = baseURL+"api/auth/register-pos-user";
        List<User> userList = new ArrayList<>();//= dbHandler.getUnRegisterUsers();
        String clientId = AppSettings.getClientId(context);
        String companyId = AppSettings.getCompanyId(context);

        User user = new User();
        user.setUserName("Admin");
        user.setUserPassward("1234");
        user.setUserAddress("address");
        user.setUserDesignation("admin");
        user.setUserUniqueId(AppSettings.getUniqueId(context));

        getAuthToken(URL,user,clientId,companyId);
        /*for(User user : userList){
           // Log.e("USER_ID",""+user.getUserID());
           // Log.e("USER_ID",""+user.getUserDesignation());

            getAuthToken(URL,user,clientId,companyId);
        }*/
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        progressRegister.dismiss();

        // this task will POST user details to server
        AsyncTaskUserPOST userPOST = new AsyncTaskUserPOST(context,registrationScreen1);
        userPOST.executeOnExecutor(THREAD_POOL_EXECUTOR);
    }

    private void getAuthToken(String url, User user, String clientId, String companyId){
        try { /// start here

            String URL = url+"?phone_number="+"%2B94718561834&user_id="+user.getUserUniqueId();

         //   Log.e("URL",URL);

            // String url1 = "http://url.com";
            java.net.URL object = new URL(URL);

            //String clientId = AppSettings.getClientId(MainActivity.appContext);
            //String companyId = AppSettings.getCompanyId(MainActivity.appContext);

            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            /*con.addRequestProperty("phone-number", AppSettings.convertNumber(context));
            con.addRequestProperty("authorization",AppSettings.getToken(context));*/
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");

           // Log.e("id",clientId);
          //  Log.e("id",companyId);

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
          //  parent.accumulate("phone_number","+94718561834");
           // parent.accumulate("user_id", user.getUserUniqueId());

            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(parent.toString());
            Log.d("OutputStreamWriter123"," " + parent.toString());
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
                System.out.println("" + sb.toString());
                WebOrdersJSONParser.getUserAuth(sb.toString());
                String userAuth = WebOrdersJSONParser.getUserAuth(sb.toString());
                Log.e("Register_Register","auth"+userAuth);
              //  dbHandler.updateUserAuth(user.getUserID(),userAuth);
                AppSettings.setAuthId(context,userAuth);

            } else {
                //System.out.println(con.getResponseMessage());
                con.getResponseMessage();
                Log.e("serverRU", con.getResponseMessage());

                /*if(!auth.equalsIgnoreCase("Not Found")) {
                    String a = WebOrdersJSONParser.getUserAuth(auth);
                    Log.e("JSONCUT",a);
                   // dbHandler.updateUserAuth(user.getUserID(), a);
                }*/
            }

        } catch (Exception e) {
            Log.e("InputStream", e.getLocalizedMessage());
        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }


}
