package com.example.srccode.takeawayproject.Activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.srccode.takeawayproject.Activities.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static com.example.srccode.takeawayproject.Global.GlopalClass.HostName;


public class ActivityClientReview extends AppCompatActivity {
    RatingBar ratingbarqualtyt,ratingbarprice,ratingbardelivery;
    EditText  Editqualty,Editprice,Editdeliver;
    Button postreviewbutton;

    double qualityrateval,pricerateval,deliveryrateval;
    String [] descratearray;
    double[] ratearray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_review);

        postreviewbutton=(Button)findViewById(R.id.postreview);
        Editqualty=(EditText)findViewById(R.id.editquality);
        Editprice=(EditText)findViewById(R.id.editprice);
        Editdeliver=(EditText)findViewById(R.id.editdelivery);

        ratingbarqualtyt=(RatingBar)findViewById(R.id.ratingBarquality);
        ratingbarprice=(RatingBar)findViewById(R.id.ratingBarprice);
        ratingbardelivery=(RatingBar)findViewById(R.id.ratingBardelivery);
        qualityrateval= Double.valueOf(ratingbarqualtyt.getRating());
        pricerateval= Double.valueOf(ratingbarprice.getRating());
        deliveryrateval= Double.valueOf(ratingbardelivery.getRating());

        ratingbarqualtyt.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                qualityrateval= Double.valueOf(ratingbarqualtyt.getRating());

            }
        });

        ratingbarprice.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                pricerateval= Double.valueOf(ratingbarprice.getRating());

            }
        });

        ratingbardelivery.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                deliveryrateval= Double.valueOf(ratingbardelivery.getRating());

            }
        });

        postreviewbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String qualitytxt= String.valueOf(Editqualty.getText());
                String pricetxt= String.valueOf(  Editprice.getText());
                String deliverytxt= String.valueOf( Editdeliver.getText());
                descratearray=new String[3];
                descratearray[0]=qualitytxt;
                descratearray[1]=pricetxt;
                descratearray[2]=deliverytxt;

                 ratearray=new double[3] ;
                ratearray[0]=qualityrateval*20;
                ratearray[1]=pricerateval*20;
                ratearray[2]=deliveryrateval*20;


                new ActivityClientReview.SendDeviceDetails().execute("http://"+ HostName+"/api/RestaurantRate/AddRateList","");
            }
        });


    }

    public String jsondata(){


        JSONArray jsonArrayClientReview  = new JSONArray();
        for (int i = 0; i<3; i++) {

            JSONObject postclientreview = new JSONObject();
            try {
                postclientreview.put("RestaurantID",13);
                postclientreview.put("UserID",4);
                postclientreview.put("RateType", i+1);
                postclientreview.put("UserRate",ratearray[i]);
                postclientreview.put("Description",descratearray[i]);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsonArrayClientReview.put(postclientreview);
        }

//        JSONObject reviewlist = new JSONObject();
//        try {
//            reviewlist.put("OrderDetailsList", jsonArrayClientReview);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        System.out.println("the JSON ARRAY is"+jsonArrayClientReview.toString());
        return  jsonArrayClientReview.toString();

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
                String request        = "http://"+ HostName+"/api/RestaurantRate/AddRateList";
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
                httpURLConnection.connect();

                int responseCode;
                DataOutputStream printout = new DataOutputStream(httpURLConnection.getOutputStream ());
                printout.writeBytes(urlParameters);
                printout.flush ();
                printout.close ();



                responseCode = httpURLConnection.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
                    String line = null;
                    StringBuilder sb = new StringBuilder();

                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }
//                    bufferedReader.close();
//                   String result = sb.toString();
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
            super.onPostExecute(result);
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
        }
    }






}
