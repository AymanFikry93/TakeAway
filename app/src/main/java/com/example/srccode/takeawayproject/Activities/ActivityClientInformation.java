package com.example.srccode.takeawayproject.Activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

import com.example.srccode.takeawayproject.Classes.ClassArea;
import com.example.srccode.takeawayproject.Classes.ClassCity;
import com.example.srccode.takeawayproject.Classes.ClassClientInformation;
import com.example.srccode.takeawayproject.Classes.ClassCountries;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.srccode.takeawayproject.Global.GlopalClass.ClientInformationaddressname;
import static com.example.srccode.takeawayproject.Global.GlopalClass.ClientInformationregionId;

import static com.example.srccode.takeawayproject.Global.GlopalClass.HostName;
import static com.example.srccode.takeawayproject.Global.GlopalClass.clientlatitude;
import static com.example.srccode.takeawayproject.Global.GlopalClass.clientlongitude;

import static com.example.srccode.takeawayproject.Global.GlopalClass.currentregionfororder;
import static com.example.srccode.takeawayproject.Global.GlopalClass.findresturantmapflag;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantmapflag;
import static com.example.srccode.takeawayproject.Global.GlopalClass.typeface;

public class ActivityClientInformation extends AppCompatActivity {

    EditText addresstxt;


    Spinner cityspinner, streetspinner,countryspinner;

    List<ClassCountries> tableCountryDB;
    List<ClassCity> tableCityDB;
    List<ClassArea> tableRegionDB;

    ArrayAdapter<String> customadapter;
    ArrayList<ClassCountries> classeslistcountry;
    ArrayList<ClassCity> classeslistCity;
    ArrayList<ClassArea> streetclasslist;
    ArrayList<String> countryvalueslist =new ArrayList<>();
    ArrayList<String> cityvalueslist =new ArrayList<>();
    ArrayList<String> streetvalues=new ArrayList<>();
    int countrySelectedId;
    int citySelectedID;
    String streetSelected="";
    String _regionname;
    String currentaddress;
    double longitude;
    double latitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_information);
        ActiveAndroid.initialize(this);

        addresstxt = (EditText) findViewById(R.id.addresstxt);

        classeslistcountry =new ArrayList<ClassCountries>();
        tableCountryDB = new Select().from(ClassCountries.class).execute();
        if (tableCountryDB.size() != 0) {
            for (int i = 0; i < tableCountryDB.size(); i++) {
                classeslistcountry.add(tableCountryDB.get(i));
                countryvalueslist.add(i, classeslistcountry.get(i).getCountryname());
            }
        } else {
           // new ActivityClientInformation.JSONAsyncTask().execute("http://takeawayapi.afshat.com/api/Countries");

             new ActivityClientInformation.JSONAsyncTask().execute("http://"+ HostName+"/api/Countries");


        }
        tableCityDB = new Select().from(ClassCity.class).execute();

        countryspinner = (Spinner)findViewById(R.id.countryspinnerId);
        cityspinner = (Spinner) findViewById(R.id.spinner_city);
        streetspinner = (Spinner) findViewById(R.id.spinner_Street);
        customadapter = new ArrayAdapter<String>(ActivityClientInformation.this, android.R.layout.simple_spinner_item, countryvalueslist);
        customadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        countryspinner.setAdapter(customadapter);

        countryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, final long id) {

                classeslistCity = new ArrayList<ClassCity>();
                countrySelectedId = Integer.valueOf(classeslistcountry.get(position).getcountryId());
                if (tableCityDB.size() != 0) {
                    cityvalueslist.clear();
                    cityvalueslist.add(0, getString(R.string.selectancity));
                    for (int i = 0; i < tableCityDB.size(); i++) {
                        classeslistCity.add(tableCityDB.get(i));
                        cityvalueslist.add(i + 1, classeslistCity.get(i).getCityname());
                    }
                } else {
//                    new ActivityClientInformation.JSONAsyncTask().execute("http://takeawayapi.afshat.com/api/Cities?CountryID=" + countrySelectedId);
                    new ActivityClientInformation.JSONAsyncTask().execute("http://"+ HostName+"/api/Cities?CountryID="+countrySelectedId);

                }
                customadapter = new ArrayAdapter<String>(ActivityClientInformation.this,android.R.layout.simple_spinner_item, cityvalueslist);
                customadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                cityspinner.setAdapter(customadapter);

                cityspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        streetclasslist = new ArrayList<ClassArea>();
                        if (position != 0) {

                            int realposition = position - 1;
                            tableRegionDB = new Select().from(ClassArea.class).where("cityId = ?", classeslistCity.get(realposition).getcityId()).execute();

                            if (tableRegionDB.size() != 0) {
                                streetvalues.clear();
                                streetvalues.add(0, getString(R.string.selectanarea));
                                for (int i = 0; i < tableRegionDB.size(); i++) {
                                    streetclasslist.add(tableRegionDB.get(i));
                                    streetvalues.add(i + 1, streetclasslist.get(i).getStreetname());
                                }

                            } else {
//                                new ActivityClientInformation.JSONAsyncTask().execute("http://takeawayapi.afshat.com/api/Regions?CityID=" + citySelectedID);
                                citySelectedID = Integer.valueOf(classeslistCity.get(realposition).getcityId());
                                new ActivityClientInformation.JSONAsyncTask().execute("http://"+ HostName+"/api/Regions?CityID="+citySelectedID);
                            }


                            customadapter = new ArrayAdapter<String>(ActivityClientInformation.this, android.R.layout.simple_spinner_item, streetvalues);
                            customadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                            streetspinner.setAdapter(customadapter);
                            streetspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position != 0) {
                                        int realposition = position - 1;

                                        streetSelected = streetclasslist.get(realposition).getregionId();
                                        _regionname = streetclasslist.get(realposition).getStreetname();

                                    }
                                    else{
                                        streetSelected="";
                                    }

                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });

                        }
                        else{

                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Please ,"+getString(R.string.selectancity) , Toast.LENGTH_LONG).show();
            }
        });


        Button locationbutton = (Button) findViewById(R.id.locationbutton);
        locationbutton.setTypeface(typeface);

        // Set a click listener on that View
        locationbutton.setOnClickListener(new View.OnClickListener() {
            //  The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                resturantmapflag=2;
                currentaddress = addresstxt.getText().toString();

//                if( currentaddress.length() == 0 )
//                    addresstxt.setError( "address is required!" );
//                else {
                    ClientInformationaddressname= String.valueOf(addresstxt.getText());
                    Intent it = new Intent(getApplicationContext(),ActivityMaps.class);
                    startActivity(it);
//                }
            }


        });
        // Find the View that shows the Resturants
        Button Resturants = (Button) findViewById(R.id.addressbutton);
        // Set a click listener on that View
        Resturants.setOnClickListener(new View.OnClickListener() {
            //  The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                currentaddress = addresstxt.getText().toString();

                if( currentaddress.length() == 0 )
                    addresstxt.setError( "address is required!" );
//                else if(citySelectedID==0){
//                    Toast.makeText(getApplicationContext(), "Please ,"+getString(R.string.selectancity), Toast.LENGTH_LONG).show();
//                }
//                else if(streetSelected==""){
//                    Toast.makeText(getApplicationContext(), "Please ,"+getString(R.string.selectanarea), Toast.LENGTH_LONG).show();
//                }
              else  if(streetSelected!=""||findresturantmapflag!=0){
                    if(streetSelected!=""){
                        ClientInformationregionId= Integer.parseInt(streetSelected);
                        ClassArea classArea = new Select().from(ClassArea.class).where("regionid = ?",ClientInformationregionId).executeSingle();
                         currentregionfororder=classArea.getStreetname();
                    }
                        ClientInformationaddressname= String.valueOf(addresstxt.getText());
                    ClassClientInformation tableClientInformation= new ClassClientInformation(ClientInformationaddressname,ClientInformationregionId
                          , currentregionfororder ,clientlatitude,clientlongitude);
                    tableClientInformation.save();

                    Intent ClientInformation =new Intent(getApplicationContext(),ActivityClientView.class);
                    startActivity(ClientInformation);

                }
             else {
                    Toast.makeText(getApplicationContext(), "Please ,"+"get your location or select region", Toast.LENGTH_LONG).show();

                }

                //List<ClassClientInformation> tableViewOrderDB =new Select().from(ClassClientInformation.class).execute();
            }


        });


    }


    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;
        HttpURLConnection urlConnection;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getApplicationContext());
            dialog.setMessage("Please wait , Loading..");
            dialog.setTitle("Connecting Server");
//            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {
                //to delete the (  "=ID")
                String[] urlpart= urls[0].split("=",0);
                String splittedurl=urlpart[0];
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

                if (splittedurl.equals("http://"+ HostName+"/api/Regions?CityID")) {

                    JSONArray jarray = jsono.getJSONArray("RegionList");
                    streetvalues.clear();
                    streetvalues.add(0,  getString(R.string.selectanarea));
                    int streetindex=1;
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        ClassArea _ClassArea = new ClassArea();

                        streetvalues.add(streetindex, object.getString(getString(R.string.areaname)));
                        streetindex++;
                        _ClassArea.setStreetname(object.getString(getString(R.string.areaname)));
                        _ClassArea.setId(object.getString("ID"));

                        streetclasslist.add(_ClassArea);
                        _ClassArea.save();

                    }
                }
                else if (splittedurl.equals("http://"+ HostName+"/api/Cities?CountryID")) {
                    JSONArray jarray = jsono.getJSONArray("CityList");
                    cityvalueslist.clear();

                    cityvalueslist.add(0, getString(R.string.selectancity));
                    int cityindex=1;
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        ClassCity _ClassCity = new ClassCity();

                        cityvalueslist.add(cityindex, object.getString(getString(R.string.cityname)));
                        cityindex++;
                        _ClassCity.setCityname(object.getString(getString(R.string.cityname)));
                        _ClassCity.setId(object.getString("ID"));

                        classeslistCity.add(_ClassCity);
                        _ClassCity.save();


                    }

                }
                else{
                    JSONArray jarray = jsono.getJSONArray("CountryDataList");
                    countryvalueslist.clear();

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        ClassCountries _ClassCountries = new ClassCountries();

                        countryvalueslist.add(i, object.getString(getString(R.string.CountryName)));
                        _ClassCountries.setCountryname(object.getString(getString(R.string.CountryName)));
                        _ClassCountries.setId(object.getString("ID"));
                        classeslistcountry.add(_ClassCountries);
                        _ClassCountries.save();


                    }

                }

            }
            catch (Exception e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            dialog.cancel();
            customadapter.notifyDataSetChanged();

            if (result == false) {
                Toast.makeText(getApplicationContext(),"Unable to fetch data from server",Toast.LENGTH_LONG).show();
            }
        }

    }

}
