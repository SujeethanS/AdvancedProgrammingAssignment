package com.example.customerOrderApp.Service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.customerOrderApp.Json.WebOrdersJSONParser;
import com.example.customerOrderApp.WelcomeScreen.RegistrationScreen1;
import com.example.customerOrderApp.helper.AppSettings;
import com.example.customerOrderApp.pojo.Company;
import com.example.customerOrderApp.pojo.Location;
import com.example.customerOrderApp.pojo.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev1 on 12/3/17.
 */

public class AsyncTaskUserPOST extends AsyncTask<String,String,String> {

    private Context context;
  //  private DBHandler dbHandler;
    private ProgressDialog progressPOST;
    RegistrationScreen1 registrationScreen1;


    public AsyncTaskUserPOST(Context context, RegistrationScreen1 registrationScreen1){
        this.context = context;
        this. registrationScreen1= registrationScreen1;
  //      this.dbHandler = DBSingleton.getInstance(context);
    }

    @Override
    protected void onPreExecute() {
        progressPOST = ProgressDialog.show(context, "Save all users' data.", "Saving...", true);
    }

    @Override
    protected String doInBackground(String... strings) {
        String baseURL = AppSettings.getURLs(context);
        String URL = baseURL+"api/auth/user";
        List<User> userList = new ArrayList<>();
        User user1 = new User();

        user1.setUserID("1");
        user1.setUserPNO("0775272030");
        user1.setUserName("admin");
        user1.setUserPassward("1234");
        user1.setUserAddress("address");
        user1.setUserDesignation("admin");
        user1.setUserState(0);
        user1.setUserEmail("n@g.com");
        user1.setUserUniqueId(AppSettings.getUniqueId(context));


        userList.add( user1);//= dbHandler.getUnPOSTUsers();
        String clientId = AppSettings.getClientId(context);
        String companyId = AppSettings.getCompanyId(context);

        Log.e("sdfsdfdsf",""+userList.size());
        POST_USER(URL,user1,clientId,companyId);

        /*for(User user : userList){

            POST_USER(URL,user,clientId,companyId);
            Log.e("post",user+"");
        }
*/
        // POST only when if it server tab
        //if(CheckCompanyType.useAsServer(context)) {
            // Post company details
        Company company = new Company();
        company.setCompanyName(AppSettings.getCompanyName(context));
        company.setCompanyDesc(AppSettings.getCompanyName(context));
        company.setCompanyAddress(AppSettings.getCompanyAddress(context));
        company.setCompanyContactNumber(AppSettings.getCompanyContactNumber(context));
        company.setCompanyContactName(AppSettings.getCompanyContactName(context));
        company.setLocationId(AppSettings.getCompanyLocationId(context));
        company.setCloudComId(AppSettings.getCompanyId(context));
        company.setClientId(AppSettings.getClientId(context));
        company.setCompanyMessage(AppSettings.getCompanyMessage(context));
        company.setCompanyIndustry(AppSettings.getCompanyIndustryType(context));
        company.setIsRegistered(AppSettings.getRegisterCloud(context));



       // VolleyBackgroundTaskPost.postCompanyDetails(context, baseURL + "api/com/company", company);
        Log.e("company_reg","" + company.getCompanyIndustry());
            // Post default locations
        Location location = new Location();
        location.setLocationId(1);
        location.setLocationName("Main Store");
        location.setLocationAddress("n/l");

        //VolleyBackgroundTaskPost.postLocationDetails(context, baseURL + "api/inv/inventory-location", location, registrationScreen1);

        //}

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        progressPOST.dismiss();

    }

    private void POST_USER(String url, User user, String clientId, String companyId){
        try {
            URL object = new URL(url);


            Log.d("DATA","url "+url);

            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");

            con.setRequestProperty("company_id",companyId);
            con.setRequestProperty("client_id",clientId);

            con.setRequestMethod("POST");

            JSONObject parent = new JSONObject();

            parent.put("client_id",clientId);
            Log.e("DATA","client_id "+clientId);

            parent.put("company_id",companyId);
            Log.e("DATA","company_id "+companyId);

            parent.put("user_id",user.getUserID());
            Log.e("DATA","user_id "+user.getUserID());

            parent.put("user_unique_id",AppSettings.getUniqueId(context));
            Log.e("DATA","user_unique_id "+AppSettings.getUniqueId(context));

            parent.put("user_auth",AppSettings.getAuthId(context));
            Log.e("DATA","user_auth "+AppSettings.getAuthId(context));

            parent.put("user_name", user.getUserName());
            Log.e("DATA","user_name "+user.getUserName());

            parent.put("user_type",user.getUserDesignation());
            Log.e("DATA","user_type "+user.getUserDesignation());

            parent.put("designation",user.getUserDesignation());
            Log.e("DATA","designation "+user.getUserDesignation());

            parent.put("password",user.getUserPassward());
            Log.e("DATA","password "+user.getUserPassward());

            parent.put("name",user.getUserName());
            Log.e("DATA","name " +user.getUserName());

            parent.put("phone_number",user.getUserPNO());
            Log.e("DATA","phone_number "+user.getUserPNO());

            parent.put("email",user.getUserEmail());
            Log.e("DATA","email "+user.getUserEmail());

            parent.put("address", user.getUserAddress());
            Log.e("DATA","address "+user.getUserAddress());

            parent.put("status",user.getUserState());
            Log.e("DATA","status "+user.getUserState());

            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(parent.toString());
            Log.d("OutputStreamWriter",parent.toString());
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
                Log.e("output123User",sb.toString());

                WebOrdersJSONParser.getUserAuth(sb.toString());
                // TODO: 12/3/17 json fetch

   //             dbHandler.updateUserStatus(user.getUserID(),1);

            } else {
                con.getResponseMessage();
                Log.e("serverR", con.getResponseMessage());

            }

        } catch (Exception e) {
            Log.e("InputStream", e.getLocalizedMessage());
        }
    }

}
