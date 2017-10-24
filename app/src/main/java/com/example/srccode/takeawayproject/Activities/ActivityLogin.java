package com.example.srccode.takeawayproject.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.srccode.takeawayproject.WebServices.PostLoginDetails;
import com.example.srccode.takeawayproject.WebServices.PostuserCategoryJson;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.srccode.takeawayproject.Global.GlopalClass.HostName;
import static com.example.srccode.takeawayproject.Global.GlopalClass.accesstoken;
import static com.example.srccode.takeawayproject.Global.GlopalClass.typeface;


public class ActivityLogin extends AppCompatActivity {
     String username;
     String password;
    boolean isResponse=false;
    String returnresponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Always cast your custom Toolbar here, and set it as the ActionBar.
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // Get the ActionBar here to configure the way it behaves.
        final ActionBar ab = getSupportActionBar();
        setSupportActionBar(mToolbar);
        TextView  mTitle = (TextView) findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.Login);
        mTitle.setTypeface(typeface);

        ImageButton imageButton=(ImageButton)findViewById(R.id.next_btn_search);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), ActivityCategory.class);
                startActivity(homeIntent);
            }
        });

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final TextView tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);
        final Button bLogin = (Button) findViewById(R.id.bSignIn);
        tvRegisterLink.setTypeface(typeface);
        bLogin.setTypeface(typeface);
        etPassword.setTypeface(typeface);
        etUsername.setTypeface(typeface);


        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(getApplicationContext(), ActivityRegister.class);
              startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                if( username.length() == 0 )
                    etUsername.setError(String.valueOf(R.string.emailreq));
                else if( password.length() == 0 )
                    etPassword.setError(String.valueOf(R.string.passreq));
                else {

                   // new SendLoginDetails().execute("http://"+ HostName+"/Token",username,password); //http://192.168.1.65:7742/api   takeawayapi.afshat.com/api
                    new PostLoginDetails(getApplicationContext()).execute("http://"+ HostName+"/Token",username,password);
                }
            }
        });
    }

    public class SendLoginDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String data = "";
            String response = "";


            HttpURLConnection httpURLConnection = null;
            try {

                String urlParameters  ="grant_type=password&username="+params[1]+"&password="+params[2];
                int    postDataLength = urlParameters.length();
                String request        ="http://"+ HostName+"/Token";
                URL url            = new URL( request );
                httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoInput (true);
                httpURLConnection.setDoOutput (true);
                httpURLConnection.setUseCaches (false);
                httpURLConnection.setInstanceFollowRedirects( false );
                httpURLConnection.setRequestMethod( "POST" );
                httpURLConnection.setRequestProperty( "Content-Type", "application/json");
                httpURLConnection.setRequestProperty( "charset", "utf-8");
                httpURLConnection.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
                httpURLConnection.setRequestProperty("Host",HostName);
                httpURLConnection.connect();
                int responseCode;
                DataOutputStream printout = new DataOutputStream(httpURLConnection.getOutputStream ());
                printout.writeBytes(urlParameters);
                printout.flush ();
                printout.close ();


                responseCode = httpURLConnection.getResponseCode();
                InputStream in;
                if (responseCode >= 400 && responseCode <= 499) {
                    in = new BufferedInputStream(httpURLConnection.getErrorStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    StringBuffer buffer = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    String finalJson = buffer.toString();
                    JSONObject jsono = new JSONObject(finalJson);
                    String registerresponse=   jsono.getString("error");
                    returnresponse=   jsono.getString("error_description");

                    isResponse=false;
                }
                else if (responseCode == 200||responseCode ==201)  {
                    InputStream _in = new BufferedInputStream(httpURLConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(_in));

                    StringBuffer buffer = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    String finalJson = buffer.toString();
                    JSONObject jsono = new JSONObject(finalJson);
                    accesstoken=   jsono.getString("access_token");

                    SharedPreferences sharedPreferences_save= getApplicationContext().getSharedPreferences("accessTokendata", MODE_PRIVATE);
                    SharedPreferences.Editor editor_save=sharedPreferences_save.edit();
                    editor_save.putString("accesstokenkey",accesstoken);  // to sava the data
                    editor_save.commit();
                    isResponse=true;
                }
                else{
                    in = new BufferedInputStream(httpURLConnection.getErrorStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    StringBuffer buffer = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    String finalJson = buffer.toString();
                    JSONObject jsono = new JSONObject(finalJson);
//                    String registerresponse=   jsono.getString("error");
//                    returnresponse=   jsono.getString("error_description");
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
            if(isResponse) {
                new PostuserCategoryJson(getApplicationContext()).execute("http://"+ HostName+"/api/Users/UpdateUserCategory?UserCategory=2", ""); //http://192.168.1.65:7742/api   takeawayapi.afshat.com/api

            }
            else {

                    Toast.makeText(getApplicationContext(),returnresponse,Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(result);
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
        }
    }

}