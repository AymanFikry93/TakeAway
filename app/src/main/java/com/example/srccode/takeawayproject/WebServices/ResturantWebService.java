package com.example.srccode.takeawayproject.WebServices;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.srccode.takeawayproject.Activities.R;
import com.example.srccode.takeawayproject.Classes.ClassResturants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.srccode.takeawayproject.Global.GlopalClass.FeeTypeid;
import static com.example.srccode.takeawayproject.Global.GlopalClass.adapterClassRestOffer;
import static com.example.srccode.takeawayproject.Global.GlopalClass.adapterClassResturant;
import static com.example.srccode.takeawayproject.Global.GlopalClass.classResturantsList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.originalList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.regionfoundbymap;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantDataId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantofferFlag;

/**
 * Created by ayman on 2017-08-12.
 */

public class ResturantWebService  extends AsyncTask<String, Void, Boolean> {

        HttpURLConnection urlConnection;
    Context mcontext;
    public  ResturantWebService(Context context){

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
                JSONArray jarray = jsono.getJSONArray("RestaurantDataList");
                for (int i = 0; i < jarray.length(); i++) {

                    JSONObject object = jarray.getJSONObject(i);

                            ClassResturants classResturants = new ClassResturants(
                             object.getString("RestID"),object.getString(mcontext.getResources().getString(R.string.RestDataname))
                            ,object.getString(mcontext.getResources().getString(R.string.OpenOrClose)),object.getString("MinimumOrderPrice")
                            ,4,"http://takeaway.afshat.com/Images/Restaurant/"+object.getString("RestImg")
                            ,object.getDouble("OfferValue"),object.getInt("OfferFeeTypeId")
                                    ,object.getDouble("DeliveryValue"),object.getInt("OfferID")  );
                            resturantDataId =object.getInt("RestDataID");
//                    classResturants.setName(object.getString(mcontext.getResources().getString(R.string.RestDataname) ));
//                    classResturants.setFeeDeliveryValue(object.getDouble("DeliveryValue"));
//                    classResturants.setofferID(object.getInt("OfferID"));
                    FeeTypeid=1;
                            classResturantsList.add(classResturants);
                            originalList.add(classResturants);
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

                adapterClassResturant.notifyDataSetChanged();


            if (result == false) {
                Toast.makeText(mcontext, "Unable to fetch data from server", Toast.LENGTH_LONG).show();
            }
        }
    }

