package com.example.srccode.takeawayproject.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.srccode.takeawayproject.Adapters.AdapterViewClient;
import com.example.srccode.takeawayproject.Classes.ClassClientInformation;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.srccode.takeawayproject.Global.GlopalClass.CobonValue;
import static com.example.srccode.takeawayproject.Global.GlopalClass.HostName;
import static com.example.srccode.takeawayproject.Global.GlopalClass.accesstoken;
import static com.example.srccode.takeawayproject.Global.GlopalClass.cobonvauetype;

import static com.example.srccode.takeawayproject.Global.GlopalClass.typeface;

public class ActivityClientView extends AppCompatActivity {
    private ListView ViewClientlist;
    private AdapterViewClient adapterViewClient;

    List<ClassClientInformation> tableViewClientDB;
    ArrayList<ClassClientInformation> classViewClientDBs;
    EditText  editcobon;
    Dialog custom;
    Button cobonButton;
    String cobondata;
    boolean isResponse=false;
    String returnresponse;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerViewOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_view);
        ActiveAndroid.initialize(this);

        tableViewClientDB = new Select().from(ClassClientInformation.class).execute();
        createviewlist();
        SharedPreferences sharedPreferences_show=getSharedPreferences("accessTokendata", Context.MODE_PRIVATE);// to get the contetnt of the data base
        accesstoken =sharedPreferences_show.getString("accesstokenkey",null);


        Button addnewaddress=(Button) findViewById(R.id.addnewaddress);

        addnewaddress.setTypeface(typeface);
        addnewaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent ClientInformation =new Intent(getApplicationContext(),ActivityClientInformation.class);
                    startActivity(ClientInformation);


            }
        });
        final Button bgetcobon = (Button) findViewById(R.id.bgetcobon);
        bgetcobon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                custom = new Dialog(ActivityClientView.this);
                custom.setContentView(R.layout.activity_cobon);
                custom.setTitle("get cobon");

                cobonButton=(Button)custom.findViewById(R.id.cobonbuttonid);


                    cobonButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            editcobon=(EditText) custom.findViewById(R.id.coboneditid);
                            cobondata=  editcobon.getText().toString();
                            if( cobondata.length() == 0 )
                                editcobon.setError( "cobon number is required!" );
                            else if(accesstoken!=null){
                                new SendCobonDetails().execute("http://"+ HostName+"/api/Cobon?serial="+cobondata, ""); //http://192.168.1.65:7742/api   takeawayapi.afshat.com/api

                            }else {
                                Intent gotologin =new Intent(getApplicationContext(),ActivityLogin.class);
                                startActivity(gotologin);
                            }

                        }
                    });
                custom.show();
            }
        });
    }

    public  void createviewlist(){

        classViewClientDBs = new ArrayList<ClassClientInformation>();
        for (int i = 0; i<tableViewClientDB.size(); i++) {
            classViewClientDBs.add(tableViewClientDB.get(i));
        }

//        ViewClientlist= (ListView) findViewById(R.id.viewaddresslist);
//
//        adapterViewClient = new AdapterViewClient(getApplicationContext(),R.layout.viewclient_row, classViewClientDBs);
//
//        ViewClientlist.setAdapter(adapterViewClient);

        recyclerViewOrder = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerViewOrder.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewOrder.setLayoutManager(layoutManager);
        recyclerViewOrder.setItemAnimator(new DefaultItemAnimator());

        adapterViewClient = new AdapterViewClient(getApplicationContext(), classViewClientDBs);
        recyclerViewOrder.setAdapter(adapterViewClient);

    }



    private class SendCobonDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String data = "";


            HttpURLConnection httpURLConnection = null;
            int responseCode;

            try {


                URL url = new URL(params[0]);
                httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setRequestProperty("Authorization","Bearer "+ accesstoken);
                httpURLConnection.connect();

                InputStream in;
                responseCode = httpURLConnection.getResponseCode();
                if (responseCode >= 400 && responseCode <= 499) {
                    isResponse=false;

                    in = new BufferedInputStream(httpURLConnection.getErrorStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    StringBuffer buffer = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    String finalJson = buffer.toString();
                    JSONObject jsono = new JSONObject(finalJson);
                    returnresponse=   jsono.getString("Message");

                }

                if (responseCode == 200||responseCode ==201) {
                    isResponse=true;

                    InputStream _in = new BufferedInputStream(httpURLConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(_in));

                    StringBuffer buffer = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    String finalJson = buffer.toString();
                    JSONObject jsono = new JSONObject(finalJson);
                    CobonValue= Double.parseDouble(jsono.getString("Value"));
                    cobonvauetype= Integer.parseInt(jsono.getString("Type"));
                    returnresponse=   jsono.getString("Value");

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
            custom.dismiss();
//            if(!isResponse) {
                Toast.makeText(getApplicationContext(),returnresponse, Toast.LENGTH_LONG).show();
//            }

            super.onPostExecute(result);
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
        }
    }

//    @Override
//    protected void onDestroy() {
//        // call the superclass method first
//        super.onDestroy();
//        ActiveAndroid.initialize(this);
//        new Delete().from(ClassViewOrderDb.class).execute();
//
//    }
}
