package com.example.srccode.takeawayproject.WebServices;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.srccode.takeawayproject.Global.GlopalClass.HostName;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Reviewdelivery;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Reviewprice;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Reviewquality;
import static com.example.srccode.takeawayproject.Global.GlopalClass.reviewdelivery;
import static com.example.srccode.takeawayproject.Global.GlopalClass.reviewprice;
import static com.example.srccode.takeawayproject.Global.GlopalClass.reviewquality;

/**
 * Created by ayman on 2017-08-12.
 */

public class PagerReviewJson  extends AsyncTask<Object,Void,Boolean> {

        HttpURLConnection urlConnection;

    Context mcontext;
    public  PagerReviewJson(Context context){

        mcontext=context;
    }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(Object... urls) {
            StringBuilder result = new StringBuilder();
            try {

                for (int i=0;i<3;i++){
                    String myurl= (String) urls[i];

                    String[] urlpart= myurl.split("=",0);

                    String splittedurl=urlpart[0];

                    URL url = new URL(myurl);

                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    StringBuffer buffer = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null){
                        buffer.append(line);
                    }
                    String finalJson = buffer.toString();

                    if (splittedurl.equals("http://"+ HostName+"/api/RestaurantRate/QualityRate?RestaurantID")){

                        Reviewquality = finalJson;
                    }
                    else if(splittedurl.equals("http://"+ HostName+"/api/RestaurantRate/PriceRate?RestaurantID")){
                        Reviewdelivery = finalJson;
                    }
                    else {
                        Reviewprice =finalJson;
                    }

                }
            }

            catch( Exception e) {
                e.printStackTrace();
            }
            finally {
                urlConnection.disconnect();
            }






            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            reviewquality.setText(Reviewquality+" %");
            reviewdelivery.setText(Reviewdelivery+" %");
            reviewprice.setText(Reviewprice+" %");
            if (Reviewquality.equals("null")){
                reviewquality.setText("0"+" %");
            }
            if (Reviewdelivery.equals("null")){
                reviewdelivery.setText("0"+" %");
            }
            if (Reviewprice.equals("null")){
                reviewprice.setText("0"+" %");

            }

            if (result == false){
                Toast.makeText(mcontext,"Unable to fetch data from server",Toast.LENGTH_LONG).show();
            }
        }
    }










