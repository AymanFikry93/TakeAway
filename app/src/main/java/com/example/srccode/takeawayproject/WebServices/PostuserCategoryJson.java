package com.example.srccode.takeawayproject.WebServices;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.srccode.takeawayproject.Activities.ActivityClientView;
import com.example.srccode.takeawayproject.Activities.ActivityCounter;
import com.example.srccode.takeawayproject.Activities.ActivityViewOrder;
import com.example.srccode.takeawayproject.Activities.PagerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.srccode.takeawayproject.Global.GlopalClass.HostName;
import static com.example.srccode.takeawayproject.Global.GlopalClass.accesstoken;
import static com.example.srccode.takeawayproject.Global.GlopalClass.offerID;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantDataId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.tableViewOrderDB;

/**
 * Created by ayman on 2017-08-29.
 */

public class PostuserCategoryJson  extends AsyncTask<String, Void, String> {

    Context mcontext;
    public  PostuserCategoryJson(Context context){
        mcontext=context;
    }
    @Override
    protected String doInBackground(String... params) {

        String data = "";
        String response = "";
        HttpURLConnection httpURLConnection = null;

        try {

            String urlParameters  ="2";//jsondata();
            int    postDataLength = urlParameters.length();
            String request        = "http://"+ HostName+"/api/User/2";
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
            httpURLConnection.setRequestProperty("Host", HostName);
            httpURLConnection.setRequestProperty("Authorization","Bearer "+ accesstoken);
            httpURLConnection.connect();

            int responseCode;
            DataOutputStream printout = new DataOutputStream(httpURLConnection.getOutputStream ());
            printout.writeBytes(urlParameters);
            printout.flush ();
            printout.close ();



            responseCode = httpURLConnection.getResponseCode();

            if (responseCode == 200||responseCode ==201||responseCode ==204) {
                InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
                JSONObject jsono = new JSONObject(finalJson);
            }
            else{
                InputStream in = new BufferedInputStream(httpURLConnection.getErrorStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
                JSONObject jsono = new JSONObject(finalJson);
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
        Intent loginIntent = new Intent(mcontext, ActivityClientView.class);
     loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mcontext.startActivity(loginIntent);
        super.onPostExecute(result);
        Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
    }




}