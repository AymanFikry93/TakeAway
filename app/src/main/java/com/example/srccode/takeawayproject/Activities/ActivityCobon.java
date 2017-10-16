package com.example.srccode.takeawayproject.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.srccode.takeawayproject.Global.GlopalClass.HostName;

public class ActivityCobon extends AppCompatActivity {

    EditText editcobon;
    String cobonnum;
    Button cobonButton;
    String cobondata;
   boolean isResponse ;
    String registerresponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobon);
        editcobon=(EditText) findViewById(R.id.coboneditid);
       cobondata=  editcobon.getText().toString();
        cobonnum = editcobon.getText().toString();
        cobonButton=(Button)findViewById(R.id.cobonbuttonid);

        if( cobonnum.length() == 0 )
            editcobon.setError( "Contact Email is required!" );
           else {
        cobonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SendCobonDetails().execute("http://"+ HostName+"/Api/Cobon/4564",""); //http://192.168.1.65:7742/api   takeawayapi.afshat.com/api


            }
        });
        }
    }
    private class SendCobonDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String data = "";


            HttpURLConnection httpURLConnection = null;
            try {

                String urlParameters  =cobondata;
                int    postDataLength = urlParameters.length();
                String request        = "http://"+ HostName+"/Api/Cobon/4564";
                URL url            = new URL( request );
                httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoInput (true);
                httpURLConnection.setDoOutput (true);
                httpURLConnection.setUseCaches (false);
                httpURLConnection.setInstanceFollowRedirects( false );
                httpURLConnection.setRequestMethod( "PUT" );
                httpURLConnection.setRequestProperty( "Content-Type", "text/json");
                //  httpURLConnection.setRequestProperty( "charset", "utf-8");
                httpURLConnection.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
                httpURLConnection.setRequestProperty("Host",HostName);
                httpURLConnection.connect();
                int responseCode;
                DataOutputStream printout = new DataOutputStream(httpURLConnection.getOutputStream ());
                printout.writeBytes(urlParameters);
                printout.flush ();
                printout.close ();

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
                     registerresponse=   jsono.getString("Message");

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
            if(isResponse){
                Intent gotoActivityLogin = new Intent(ActivityCobon.this,Home_MainActivity.class);
                startActivity(gotoActivityLogin);
            }
            else {

                Toast.makeText(getApplicationContext(),registerresponse,Toast.LENGTH_LONG).show();

            }

            super.onPostExecute(result);
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
        }
    }

}
