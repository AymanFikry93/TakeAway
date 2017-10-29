package com.example.srccode.takeawayproject.WebServices;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;


import com.example.srccode.takeawayproject.Activities.ActivityCounter;
import com.example.srccode.takeawayproject.Activities.ActivityLogin;

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

import static com.example.srccode.takeawayproject.Global.GlopalClass.CobonValue;
import static com.example.srccode.takeawayproject.Global.GlopalClass.FeeDeliveryValue;
import static com.example.srccode.takeawayproject.Global.GlopalClass.HostName;
import static com.example.srccode.takeawayproject.Global.GlopalClass.OfferFeeTypeId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.OfferResturantValue;
import static com.example.srccode.takeawayproject.Global.GlopalClass.accesstoken;
import static com.example.srccode.takeawayproject.Global.GlopalClass.cobonvauetype;
import static com.example.srccode.takeawayproject.Global.GlopalClass.currentaddressfororder;
import static com.example.srccode.takeawayproject.Global.GlopalClass.currentregionfororder;
import static com.example.srccode.takeawayproject.Global.GlopalClass.fee;
import static com.example.srccode.takeawayproject.Global.GlopalClass.offerID;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantDataId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.tableViewOrderDB;

/**
 * Created by ayman on 2017-08-12.
 */

public class PostOrderJson   extends AsyncTask<String, Void, String> {

    Context mcontext;
    boolean isresponce;
    public  PostOrderJson(Context context){
        mcontext=context;
    }
        @Override
        protected String doInBackground(String... params) {

            String data = "";
            HttpURLConnection httpURLConnection = null;

            try {

                String urlParameters  =jsondata();
                int    postDataLength = urlParameters.length();
                String request        = "http://"+ HostName+"/api/Orders";
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

                if (responseCode == 200||responseCode ==201) {
                    isresponce=true;
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
                    isresponce=false;
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
            CobonValue=0.0;
         if(isresponce){

             Intent gotocounter =new Intent(mcontext,ActivityCounter.class);
             gotocounter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             mcontext.startActivity(gotocounter);
         }
            else {
             Intent gotoPagerActivity =new Intent(mcontext,ActivityLogin.class);
             gotoPagerActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             mcontext.startActivity(gotoPagerActivity);
         }
            super.onPostExecute(result);
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
        }
    public String jsondata(){

        double totalplaceprice=0.0;
        double placeprice=0.0;
        int quantityplace=0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd 'T'HH:MM:ss.SSS", Locale.US);
        Date date = new Date();
        String currentdate= simpleDateFormat.format(date);
        String AddressforOrder=currentaddressfororder+","+currentregionfororder;
        JSONArray jsonArrayorderdetails  = new JSONArray();
        for (int i = 0; i<tableViewOrderDB.size(); i++) {
            quantityplace = Integer.valueOf(tableViewOrderDB.get(i).getOrderquantity());
            placeprice = Double.parseDouble(tableViewOrderDB.get(i).getOrderprice());
            totalplaceprice = totalplaceprice + (placeprice * quantityplace);
            int ItemFoodID=Integer.valueOf(tableViewOrderDB.get(i).getorderid());
            int ItemFoodParentID=0;
            int additionId= Integer.valueOf(tableViewOrderDB.get(i).getadditionID());
            int drinksId= Integer.valueOf(tableViewOrderDB.get(i).getdrinksID());
            int itemofferorderedid= Integer.valueOf(tableViewOrderDB.get(i).getitemofferorderedID());
           int CookingCatorderedID= Integer.valueOf(tableViewOrderDB.get(i).getCookingCatorderedID());

            JSONObject postorderdetails = new JSONObject();
            try {
                postorderdetails.put("ItemFoodID",ItemFoodID);
                postorderdetails.put("ItemFoodParentID",ItemFoodParentID);
                postorderdetails.put("ItemPrice", placeprice);
                postorderdetails.put("ItemCount",quantityplace);
                postorderdetails.put("CookingCatID",CookingCatorderedID);
                postorderdetails.put("ItemOfferID",itemofferorderedid);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsonArrayorderdetails.put(postorderdetails);
            if(additionId!=0){
                postorderdetails = new JSONObject();
                try {
                    postorderdetails.put("ItemFoodID",additionId);
                    postorderdetails.put("ItemFoodParentID",ItemFoodID);
                    postorderdetails.put("ItemPrice", 0);
                    postorderdetails.put("ItemCount",quantityplace);
                    postorderdetails.put("CookingCatID",CookingCatorderedID);
                    postorderdetails.put("ItemOfferID",itemofferorderedid);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jsonArrayorderdetails.put(postorderdetails);
            }
            if(drinksId!=0){
                postorderdetails = new JSONObject();
                try {
                    postorderdetails.put("ItemFoodID",drinksId);
                    postorderdetails.put("ItemFoodParentID",ItemFoodID);
                    postorderdetails.put("ItemPrice", 0);
                    postorderdetails.put("ItemCount",quantityplace);
                    postorderdetails.put("CookingCatID",CookingCatorderedID);
                    postorderdetails.put("ItemOfferID",itemofferorderedid);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArrayorderdetails.put(postorderdetails);
            }
        }
        if (cobonvauetype==1){
            totalplaceprice=totalplaceprice-CobonValue;
        }else{
            totalplaceprice=totalplaceprice-totalplaceprice*(CobonValue*  .01);

        }
        if(OfferFeeTypeId==1) {
            totalplaceprice=totalplaceprice-OfferResturantValue;
        }
        else if(OfferFeeTypeId==2) {
            totalplaceprice=totalplaceprice-(totalplaceprice*OfferResturantValue*.01);
        }
        totalplaceprice=totalplaceprice+fee;

        JSONObject orderlist = new JSONObject();
        JSONObject order = new JSONObject();
        try {
            orderlist.put("OrderDetailsList", jsonArrayorderdetails);
            orderlist.put("RestaurantID", resturantId);
            orderlist.put("RestaurantDataID", resturantDataId);
            orderlist.put("OrderStatusID",1);
            orderlist.put("RestOfferID",offerID);
            orderlist.put("FeeValue",FeeDeliveryValue);
            orderlist.put("Address",AddressforOrder);
            orderlist.put("TotalPrice", totalplaceprice);
            orderlist.put("OrderDate", currentdate);//currentdate  "2017-07-20T13:15:48.9777968+03:00"
            orderlist.put("PayType", 1);
            orderlist.put("Delivery_estimation",  "01:05:00.0000000");

        //  jsonArrayorder.put(orderlist);
            order.put("OrdersList",orderlist);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("the JSON ARRAY is"+order.toString());
        return  order.toString();

    }


}