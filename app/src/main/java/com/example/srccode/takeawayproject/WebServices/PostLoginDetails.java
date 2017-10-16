package com.example.srccode.takeawayproject.WebServices;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.srccode.takeawayproject.Activities.ActivityClientView;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;
import static com.example.srccode.takeawayproject.Global.GlopalClass.HostName;
import static com.example.srccode.takeawayproject.Global.GlopalClass.accesstoken;

/**
 * Created by ayman on 2017-09-13.
 */

public class PostLoginDetails extends AsyncTask<String, Void, String>{
    boolean isResponse=false;
    String returnresponse;
    Context mcontext;
    public  PostLoginDetails(Context context){

        mcontext=context;
    }



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

                    SharedPreferences sharedPreferences_save= mcontext.getSharedPreferences("accessTokendata", MODE_PRIVATE);
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
                new PostuserCategoryJson(mcontext).execute("http://"+ HostName+"/api/Users/UpdateUserCategory?UserCategory=2", ""); //http://192.168.1.65:7742/api   takeawayapi.afshat.com/api

            }
            else {

                Toast.makeText(mcontext,returnresponse,Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(result);
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
        }
    }

