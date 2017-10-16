package com.example.srccode.takeawayproject.WebServices;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.srccode.takeawayproject.Activities.R;
import com.example.srccode.takeawayproject.Classes.ClassCookingDB;
import com.example.srccode.takeawayproject.Classes.ClassOrderStatus;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.srccode.takeawayproject.Global.GlopalClass.classcookcatList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.classorderstatusList;

/**
 * Created by ayman on 2017-08-22.
 */

public class OrderStatusJsonAsync extends AsyncTask<String, Void, Boolean> {

    HttpURLConnection urlConnection;
    Context mcontext;
    public  OrderStatusJsonAsync(Context context){

        mcontext=context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Boolean doInBackground(String... urls) {
        StringBuilder result = new StringBuilder();
        try {
//
            URL url = new URL(urls[0]);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());


            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();


            JSONObject jsono = new JSONObject(finalJson);
            JSONArray jarray = jsono.getJSONArray("OrderStatusDataList");
            for (int i = 0; i < jarray.length(); i++) {

                JSONObject object = jarray.getJSONObject(i);
                ClassOrderStatus classorderstatus = new ClassOrderStatus();
                classorderstatus.setorderstatusid(object.getString("ID"));
                classorderstatus.setorderstatusname(object.getString(mcontext.getResources().getString(R.string.OrderStatus)));

                classorderstatusList.add(classorderstatus);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }


        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        // dialog.cancel();
        if (result == false) {
            Toast.makeText(mcontext, "Unable to fetch data from server", Toast.LENGTH_LONG).show();
        }
    }
}

