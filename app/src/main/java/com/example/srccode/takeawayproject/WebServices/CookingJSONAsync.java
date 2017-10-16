package com.example.srccode.takeawayproject.WebServices;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.srccode.takeawayproject.Activities.R;
import com.example.srccode.takeawayproject.Classes.ClassCookingDB;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.srccode.takeawayproject.Global.GlopalClass.classcookcatList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.classeslistcooking;
import static com.example.srccode.takeawayproject.Global.GlopalClass.cookingvalueslist;
import static com.example.srccode.takeawayproject.Global.GlopalClass.tablecookingDB;

/**
 * Created by ayman on 2017-08-22.
 */

public class CookingJSONAsync extends AsyncTask<String, Void, Boolean> {

    HttpURLConnection urlConnection;
    Context mcontext;
    public  CookingJSONAsync(Context context){

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
            JSONArray jarray = jsono.getJSONArray("CountryDataList");
            for (int i = 0; i < jarray.length(); i++) {

                JSONObject object = jarray.getJSONObject(i);
                ClassCookingDB classcookcats = new ClassCookingDB();
                classcookcats.setcookid(object.getString("ID"));
                classcookcats.setCookname(object.getString(mcontext.getResources().getString(R.string.CookingCatName)));
                classcookcatList.add(classcookcats);
                classcookcats.save();
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
        ActiveAndroid.initialize(mcontext);
        tablecookingDB = new Select().from(ClassCookingDB.class).execute();

        if (tablecookingDB.size() != 0) {
            for (int i = 0; i < tablecookingDB.size(); i++) {
                classeslistcooking.add(tablecookingDB.get(i));
                cookingvalueslist.add(i, classeslistcooking.get(i).getCookname());
            }

        }
        if (result == false) {
            Toast.makeText(mcontext, "Unable to fetch data from server", Toast.LENGTH_LONG).show();
        }
    }
}

