package com.example.srccode.takeawayproject.WebServices;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;


import com.example.srccode.takeawayproject.Activities.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.srccode.takeawayproject.Global.GlopalClass.DeliveryWay;
import static com.example.srccode.takeawayproject.Global.GlopalClass.FeeDeliveryValue;
import static com.example.srccode.takeawayproject.Global.GlopalClass.FeeType;
import static com.example.srccode.takeawayproject.Global.GlopalClass.FeeTypeid;
import static com.example.srccode.takeawayproject.Global.GlopalClass.OpenOrClose;
import static com.example.srccode.takeawayproject.Global.GlopalClass.PayWay;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Regionname;
import static com.example.srccode.takeawayproject.Global.GlopalClass.deliveryWay;
import static com.example.srccode.takeawayproject.Global.GlopalClass.feeType;
import static com.example.srccode.takeawayproject.Global.GlopalClass.feeTypeValue;
import static com.example.srccode.takeawayproject.Global.GlopalClass.openOrClose;
import static com.example.srccode.takeawayproject.Global.GlopalClass.payWay;
import static com.example.srccode.takeawayproject.Global.GlopalClass.restregion;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantId;

/**
 * Created by ayman on 2017-08-12.
 */

public class RestInformationJson  extends AsyncTask<String,Void,Boolean> {

        ProgressDialog dialog;
        HttpURLConnection urlConnection;

    Context mcontext;
    public  RestInformationJson(Context context){

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

                URL url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }
                String finalJson = buffer.toString();


                JSONObject jsono = new JSONObject(finalJson);
                JSONArray jarray = jsono.getJSONArray("RestaurantDataList");
                for (int i=0 ; i<jarray.length();i++)
                {

                    JSONObject object = jarray.getJSONObject(i);
                    int _resturantid= object.getInt("RestID");

                    if(_resturantid==resturantId){
                        OpenOrClose=object.getString(mcontext.getResources().getString(R.string.OpenOrClose));
                        DeliveryWay=object.getString(mcontext.getResources().getString(R.string.DeliveryWay));
                        FeeTypeid=1;//object.getInt("FeeTypeID");
                        FeeType="Fixed";//object.getString(mcontext.getResources().getString(R.string.FeeType));
                        FeeDeliveryValue=object.getDouble("DeliveryValue");
                        PayWay=object.getString(mcontext.getResources().getString(R.string.PayWayName));

                    }
                }
            }catch( Exception e) {
                e.printStackTrace();
            }
            finally {
                urlConnection.disconnect();
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            restregion.setText(Regionname);
            openOrClose.setText(OpenOrClose);
            deliveryWay.setText(DeliveryWay);
            feeType.setText(FeeType);
            payWay.setText(PayWay);
            if (FeeTypeid==1){
                feeTypeValue.setText(FeeDeliveryValue+"SAR");
            }else
            {
                feeTypeValue.setText(FeeDeliveryValue+" %");

            }



            if (result == false){
                Toast.makeText(mcontext,"Unable to fetch data from server",Toast.LENGTH_LONG).show();
            }
        }
    }





