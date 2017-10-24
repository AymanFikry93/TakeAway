package com.example.srccode.takeawayproject.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.srccode.takeawayproject.Global.GlopalClass.CobonValue;
import static com.example.srccode.takeawayproject.Global.GlopalClass.HostName;
import static com.example.srccode.takeawayproject.Global.GlopalClass.accesstoken;
import static com.example.srccode.takeawayproject.Global.GlopalClass.cobonvauetype;

public class ActivityCobon extends AppCompatActivity {

    EditText editcobon;
    String cobonnum;
    Button cobonButton;
    String cobondata;
    String registerresponse;
    boolean isResponse=false;
    String returnresponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobon);
        // Always cast your custom Toolbar here, and set it as the ActionBar.
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        // Get the ActionBar here to configure the way it behaves.
        final ActionBar ab = getSupportActionBar();
        setSupportActionBar(mToolbar);
        TextView mTitle = (TextView) findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.GetnewCobon);
        ImageButton imageButton=(ImageButton)findViewById(R.id.next_btn_search);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), Home_MainActivity.class);
                startActivity(homeIntent);
            }
        });
        editcobon=(EditText) findViewById(R.id.coboneditid);
       cobondata=  editcobon.getText().toString();
        cobonnum = editcobon.getText().toString();
        cobonButton=(Button)findViewById(R.id.cobonbuttonid);

        cobonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
           // custom.dismiss();
            if(!isResponse) {
            Toast.makeText(getApplicationContext(),returnresponse, Toast.LENGTH_LONG).show();
            }else
            {
                Intent gotohome =new Intent(getApplicationContext(),Home_MainActivity.class);
                startActivity(gotohome);

            }

            super.onPostExecute(result);
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
        }
    }

}
