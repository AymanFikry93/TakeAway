package com.example.srccode.takeawayproject.WebServices;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.srccode.takeawayproject.Activities.R;
import com.example.srccode.takeawayproject.Classes.ClassArea;
import com.example.srccode.takeawayproject.Classes.ClassCity;
import com.example.srccode.takeawayproject.Classes.ClassCountries;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.srccode.takeawayproject.Global.GlopalClass.HostName;
import static com.example.srccode.takeawayproject.Global.GlopalClass.cityvalueslist;
import static com.example.srccode.takeawayproject.Global.GlopalClass.classeslistCity;
import static com.example.srccode.takeawayproject.Global.GlopalClass.classeslistcountry;
import static com.example.srccode.takeawayproject.Global.GlopalClass.countryvalueslist;
import static com.example.srccode.takeawayproject.Global.GlopalClass.customadapter;
import static com.example.srccode.takeawayproject.Global.GlopalClass.streetclasslist;
import static com.example.srccode.takeawayproject.Global.GlopalClass.streetvalues;

/**
 * Created by ayman on 2017-08-12.
 */

public class AreaJSONAsync  extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;
        HttpURLConnection urlConnection;
    Context mcontext;
    public  AreaJSONAsync( Context context){
        mcontext=context;

    }

    @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(mcontext);
            dialog.setMessage("Please wait , Loading..");
            dialog.setTitle("Connecting Server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {
                //to delete the (  "=ID")
                String[] urlpart = urls[0].split("=", 0);
                String splittedurl = urlpart[0];
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
                JSONObject jsono = new JSONObject(finalJson);

                if (splittedurl.equals("http://"+HostName+"/api/Regions?CityID")) {

                    JSONArray jarray = jsono.getJSONArray("RegionList");
                    streetvalues.clear();
                    streetvalues.add(0, mcontext.getResources().getString(R.string.selectanarea));
                    int streetindex = 1;
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        ClassArea _ClassArea = new ClassArea();

                        streetvalues.add(streetindex, object.getString(mcontext.getResources().getString(R.string.areaname)));
                        streetindex++;
                        _ClassArea.setStreetname(object.getString(mcontext.getResources().getString(R.string.areaname)));
                        _ClassArea.setId(object.getString("ID"));
                        _ClassArea.setCityId(object.getString("CityID"));

                        streetclasslist.add(_ClassArea);
                        _ClassArea.save();
                    }
                } else if (splittedurl.equals("http://"+HostName+"/api/Cities?CountryID")) {
//                      else if (splittedurl.equals("https://aymanfikryeng.000webhostapp.com/City.json")) {

                    JSONArray jarray = jsono.getJSONArray("CityList");
                    cityvalueslist.clear();

                    cityvalueslist.add(0, mcontext.getResources().getString(R.string.selectancity));
                    int cityindex = 1;
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        ClassCity _ClassCity = new ClassCity();

                        cityvalueslist.add(cityindex, object.getString(mcontext.getResources().getString(R.string.cityname)));
                        cityindex++;
                        _ClassCity.setCityname(object.getString(mcontext.getResources().getString(R.string.cityname)));
                        _ClassCity.setId(object.getString("ID"));
                        classeslistCity.add(_ClassCity);
                        _ClassCity.save();
                    }
                } else {
                    JSONArray jarray = jsono.getJSONArray("CountryDataList");
                    countryvalueslist.clear();

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        ClassCountries _ClassCountries = new ClassCountries();

                        countryvalueslist.add(i, object.getString(mcontext.getResources().getString(R.string.CountryName)));
                        _ClassCountries.setCountryname(object.getString(mcontext.getResources().getString(R.string.CountryName)));
                        _ClassCountries.setId(object.getString("ID"));
                        classeslistcountry.add(_ClassCountries);
                        _ClassCountries.save();
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
           // dialog.cancel();
            customadapter.notifyDataSetChanged();

            if (result == false) {
                Toast.makeText(mcontext, "Unable to fetch data from server", Toast.LENGTH_LONG).show();
            }
        }

    }



