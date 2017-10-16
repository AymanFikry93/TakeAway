package com.example.srccode.takeawayproject.Activities;

import android.*;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.srccode.takeawayproject.Classes.ClassClientInformation;
import com.example.srccode.takeawayproject.WebServices.PostLoginDetails;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static com.example.srccode.takeawayproject.Global.GlopalClass.HostName;


public class ActivityRegister extends AppCompatActivity {
    List<ClassClientInformation> tableClientInformation;
    String ContactName;
    String ContactEmail;
    String ContactPhone;
     String password;
     String confirmPassword;
     String username;
    TextView login;
    boolean isResponse;
    String registerresponse1;
    TelephonyManager tMgr;
     EditText etContactPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etContactName = (EditText) findViewById(R.id.etContactName);
        final EditText etContactEmail = (EditText) findViewById(R.id.etContactEmail);
          etContactPhone = (EditText) findViewById(R.id.etcontactphone);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etconfirmPassword = (EditText) findViewById(R.id.etconfirmPassword);
        ActiveAndroid.initialize(this);
        tableClientInformation = new Select().from(ClassClientInformation.class).execute();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                &&   ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Need Multiple Permissions");
            builder.setMessage("This app needs phone permissions.");
            builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String[] permissionsRequired = new String[]{android.Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_SMS};

                    final int PERMISSION_CALLBACK_CONSTANT = 101;
                    dialog.cancel();
                    ActivityCompat.requestPermissions(ActivityRegister.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }

        try{
            tMgr = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            ContactPhone = tMgr.getLine1Number();
            etContactPhone.setText(ContactPhone);
        }
        catch (Exception e)
        {
            e.getMessage();
        }


        login = (TextView)findViewById(R.id.logiin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ActivityRegister.this,ActivityLogin.class);
                startActivity(it);
            }
        });

        final Button bRegister = (Button) findViewById(R.id.bRegister);
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactName = etContactName.getText().toString();
                ContactEmail = etContactEmail.getText().toString();

                if (ContactPhone.equals("")||ContactPhone==null)
                {
                    ContactPhone = etContactPhone.getText().toString();

                }
                password = etPassword.getText().toString();
                confirmPassword = etconfirmPassword.getText().toString();
                username = etUsername.getText().toString();
                if( ContactEmail.length() == 0 )
                    etContactEmail.setError( "Contact Email is required!" );
              else if( ContactName.length() == 0 )
                    etContactName.setError( "Contact Name is required!" );

               else if( username.length() == 0 )
                    etUsername.setError( "username is required!" );

               else if(ContactPhone.equals("")||ContactPhone.equals(null)){
                    Toast.makeText(getApplicationContext(),"Your phone number is required",Toast.LENGTH_LONG).show();

                }
               else if(!password.equals(confirmPassword)){
                    etPassword.setError( "Passwords is not matched !" );
                    etconfirmPassword.setError( "Passwords is not matched !" );

                }
                else if(tableClientInformation.size()==0){
                    Intent loginIntent = new Intent(getApplicationContext(), ActivityClientView.class);
                    startActivity(loginIntent);
                }
                else {
                    new SendDeviceDetails().execute(HostName+"/Api/Account/Register",""); //http://192.168.1.65:7742/api   takeawayapi.afshat.com/api
                }



            }
        });
    }




    private class SendDeviceDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String data = "";
            String response = "";


            HttpURLConnection httpURLConnection = null;
            try {

                String urlParameters  =jsondata();
                int    postDataLength = urlParameters.length();
                String request        = "http://"+ HostName+"/Api/Account/Register";
                URL url            = new URL( request );
                httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoInput (true);
                httpURLConnection.setDoOutput (true);
                httpURLConnection.setUseCaches (false);
                httpURLConnection.setInstanceFollowRedirects( false );
                httpURLConnection.setRequestMethod( "POST" );
                httpURLConnection.setRequestProperty( "Content-Type", "text/json");
            //  httpURLConnection.setRequestProperty( "charset", "utf-8");
                httpURLConnection.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
                httpURLConnection.setRequestProperty("Host", HostName);
                httpURLConnection.connect();
                int responseCode;
                DataOutputStream printout = new DataOutputStream(httpURLConnection.getOutputStream ());
                printout.writeBytes(urlParameters);
                printout.flush ();
                printout.close ();

                InputStream in;
                responseCode = httpURLConnection.getResponseCode();
                if (responseCode >= 400 && responseCode <= 499) {
                    isResponse=false;

                    in = new BufferedInputStream(httpURLConnection.getErrorStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    StringBuffer buffer = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    String finalJson = buffer.toString();
                    JSONObject jsono = new JSONObject(finalJson);
                    String registerresponse=   jsono.getString("Message");
                     registerresponse1=   jsono.getString("ModelState");

                }

                if (responseCode == 200||responseCode ==201) {
                    isResponse=true;

                    InputStream _in = new BufferedInputStream(httpURLConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(_in));

                    StringBuffer buffer = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    String finalJson = buffer.toString();

                }
                else  {
                        in = new BufferedInputStream(httpURLConnection.getErrorStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        StringBuffer buffer = new StringBuffer();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            buffer.append(line);
                        }
                        String finalJson = buffer.toString();
                        JSONObject jsono = new JSONObject(finalJson);
                        isResponse=false;
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return data;
        }

        @Override
        protected void onPostExecute(String result) {
             if(isResponse){

                new PostLoginDetails(getApplicationContext()).execute("http://"+ HostName+"/Token",username,password);

//                 Intent gotoActivityLogin = new Intent(ActivityRegister.this,ActivityLogin.class);
//                 gotoActivityLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                 startActivity(gotoActivityLogin);
                 }
             else {

                 Toast.makeText(getApplicationContext(),registerresponse1,Toast.LENGTH_LONG).show();

             }

            super.onPostExecute(result);
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
        }
    }


    public String jsondata(){


//        JSONArray jsonArrayClientInformation  = new JSONArray();
//        for (int i = 0; i<1; i++) {//tableClientInformation.size()
//
//            JSONObject postclientinformations = new JSONObject();
//            try {
//                //postclientinformations.put("UserID","");
//                postclientinformations.put("Address",tableClientInformation.get(i).getadress());
//                postclientinformations.put("Region_id",tableClientInformation.get(i).getregionId());//tableClientInformation.get(i).getregionId()
//                postclientinformations.put("MapLatitude",tableClientInformation.get(i).getregionmapLatitude());
//                postclientinformations.put("MapLongitude",tableClientInformation.get(i).getregionmapLongtitude());
//                postclientinformations.put("Address","40th elgiesh");
//                postclientinformations.put("Region_id",1);
//                postclientinformations.put("MapLatitude",30.55);
//                postclientinformations.put("MapLongitude",31.56);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            jsonArrayClientInformation.put(postclientinformations);
//        }

        JSONObject TakeawayUserlist = new JSONObject();
        JSONObject order = new JSONObject();

        try {
            TakeawayUserlist.put("ContactName",ContactName);
            TakeawayUserlist.put("ContactEmail",ContactEmail);
            TakeawayUserlist.put("ContactPhone", ContactPhone);
            TakeawayUserlist.put("UserName",username);
            TakeawayUserlist.put("Password",password);
            TakeawayUserlist.put("ConfirmPassword",confirmPassword);
          //  TakeawayUserlist.put("UserAddressList", jsonArrayClientInformation);
            TakeawayUserlist.put("UserCategory", 2);

            //  jsonArrayorder.put(orderlist);
            order.put("takeawayuser",TakeawayUserlist);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("the JSON ARRAY is :"+order);
        return  order.toString();

    }
}