package com.example.srccode.takeawayproject.WebServices;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.srccode.takeawayproject.Activities.ActivityClientInformation;
import com.example.srccode.takeawayproject.Activities.ActivityClientView;
import com.example.srccode.takeawayproject.Activities.ActivityResturants;
import com.example.srccode.takeawayproject.Activities.ActivityViewClientAddress;
import com.example.srccode.takeawayproject.Activities.Home_MainActivity;
import com.example.srccode.takeawayproject.Activities.R;
import com.example.srccode.takeawayproject.Classes.ClassClientInformation;
import com.example.srccode.takeawayproject.Classes.ClassResturants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.srccode.takeawayproject.Global.GlopalClass.ClientInformationaddressname;
import static com.example.srccode.takeawayproject.Global.GlopalClass.ClientInformationregionId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.FeeTypeid;
import static com.example.srccode.takeawayproject.Global.GlopalClass.MapRegionID;
import static com.example.srccode.takeawayproject.Global.GlopalClass.adapterClassRestOffer;
import static com.example.srccode.takeawayproject.Global.GlopalClass.adapterClassResturant;
import static com.example.srccode.takeawayproject.Global.GlopalClass.classResturantsList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.clientlatitude;
import static com.example.srccode.takeawayproject.Global.GlopalClass.clientlongitude;
import static com.example.srccode.takeawayproject.Global.GlopalClass.currentregionfororder;
import static com.example.srccode.takeawayproject.Global.GlopalClass.findresturantmapflag;
import static com.example.srccode.takeawayproject.Global.GlopalClass.originalList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.regionfoundbymap;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantDataId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantmapflag;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantofferFlag;

/**
 * Created by ayman on 2017-09-10.
 */

public class GetRegionByMap extends AsyncTask<String, Void, Boolean> {

    HttpURLConnection urlConnection;
    Context mcontext;
    public  GetRegionByMap(Context context){

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
            if(finalJson==""){
                regionfoundbymap=0;
            }
            else{
                regionfoundbymap=1;
                ClientInformationregionId= Integer.parseInt(jsono.getString("id"));
                currentregionfororder= jsono.getString("Name");




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

        if( resturantmapflag==1){
            resturantmapflag = 0;
        if(regionfoundbymap==0){
            Toast.makeText(mcontext, "Unable to find region, try again,or choose from list", Toast.LENGTH_LONG).show();
            Intent ResturantsIntent = new Intent(mcontext, Home_MainActivity.class);
            ResturantsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mcontext.startActivity(ResturantsIntent);
        }
        else{
            regionfoundbymap=1;
            findresturantmapflag=1;

            Intent ResturantsIntent = new Intent(mcontext, ActivityResturants.class);
            ResturantsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mcontext.startActivity(ResturantsIntent);
        }
        }
        else if( resturantmapflag==2){
            resturantmapflag = 0;
            if(regionfoundbymap==0){
                Toast.makeText(mcontext, "Unable to find region, try again,or choose from list", Toast.LENGTH_LONG).show();
                Intent ResturantsIntent = new Intent(mcontext, ActivityClientInformation.class);
                ResturantsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(ResturantsIntent);
            }
            else{
                regionfoundbymap=1;
                findresturantmapflag=0;
                ClassClientInformation tableClientInformation= new ClassClientInformation(ClientInformationaddressname,ClientInformationregionId
                        ,currentregionfororder,clientlatitude,clientlongitude);
                tableClientInformation.save();
                Intent ClientInformation =new Intent(mcontext,ActivityClientView.class);
                ClientInformation.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(ClientInformation);
            }

        }

        if (result == false) {
            Toast.makeText(mcontext, "Unable to fetch data from server", Toast.LENGTH_LONG).show();
        }
    }
}

