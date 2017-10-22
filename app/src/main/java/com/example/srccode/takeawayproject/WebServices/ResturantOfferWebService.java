package com.example.srccode.takeawayproject.WebServices;

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

import static com.example.srccode.takeawayproject.Activities.ActivityRestOffer.restoffernumber;
import static com.example.srccode.takeawayproject.Activities.ActivityResturants.restnumber;
import static com.example.srccode.takeawayproject.Global.GlopalClass.FeeTypeid;
import static com.example.srccode.takeawayproject.Global.GlopalClass.adapterClassRestOffer;
import static com.example.srccode.takeawayproject.Global.GlopalClass.adapterClassResturant;
import static com.example.srccode.takeawayproject.Global.GlopalClass.classResturantsList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.originalList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantDataId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantofferFlag;

/**
 * Created by ayman on 2017-09-12.
 */

public class ResturantOfferWebService extends AsyncTask<String, Void, Boolean> {

    HttpURLConnection urlConnection;
    Context mcontext;
    public  ResturantOfferWebService(Context context){

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

                    if(object.getInt("OfferID")!=0){
                        ClassResturants classResturants = new ClassResturants();
                        resturantDataId =object.getInt("RestDataID");
                        classResturants.setId(object.getString("RestID"));
                        classResturants.setName(object.getString(mcontext.getResources().getString(R.string.RestDataname)));
                        classResturants.setopenandclose(object.getString(mcontext.getResources().getString(R.string.OpenOrClose)));
                        classResturants.setmincharge(object.getString("MinimumOrderPrice"));
                        classResturants.setRating(4);//object.getInt("Stars")
                        classResturants.setImage("http://takeaway.afshat.com/Images/Restaurant/"+object.getString("RestImg"));

                        //    classResturants.setImage(object.getString("LogUrl"));
                        classResturants.setFeeDeliveryValue(object.getDouble("DeliveryValue"));
                        classResturants.setofferID(object.getInt("OfferID"));
                        classResturants.setofferValue(object.getDouble("OfferValue"));
                        classResturants.setOfferFeeTypeId(object.getInt("OfferFeeTypeId"));
                        classResturantsList.add(classResturants);
                        originalList.add(classResturants);




                }
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

            adapterClassRestOffer.notifyDataSetChanged();
        restoffernumber.setText(classResturantsList.size()+mcontext.getResources().getString(R.string.Resturantsarefound));


        if (result == false) {
            Toast.makeText(mcontext, "Unable to fetch data from server", Toast.LENGTH_LONG).show();
        }
    }
}

