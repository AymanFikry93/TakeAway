package com.example.srccode.takeawayproject.WebServices;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.srccode.takeawayproject.Activities.R;
import com.example.srccode.takeawayproject.Classes.ClassResturants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.concurrent.RunnableFuture;

import retrofit2.http.Url;

import static com.example.srccode.takeawayproject.Activities.ActivityResturants.restnumber;
import static com.example.srccode.takeawayproject.Global.GlopalClass.FeeTypeid;
import static com.example.srccode.takeawayproject.Global.GlopalClass.adapterClassResturant;
import static com.example.srccode.takeawayproject.Global.GlopalClass.classResturantsList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.originalList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantDataId;

/**
 * Created by ayman on 2017-10-03.
 */

public class ResturantDataWebService  extends AsyncTask<String, Void, Boolean> {

    Context mcontext;
    String urlll;
    ProgressDialog pDialog;
    public ResturantDataWebService(Context context){
        mcontext=context;
//        urlll=urll;


    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

                try {
                    pDialog = new ProgressDialog(mcontext, R.style.CustomDialog);
        pDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//                    pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    pDialog.setContentView(R.layout.activity_counter);
//                      pDialog.setMessage("Loading...");
//                    pDialog.setTitle("Connecting Server");
//                    pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pDialog.setMessage("Loading...");
                    pDialog.show();
                    pDialog.setCancelable(false);
                }catch (Exception e)
                {
                    e.getMessage();

                }



    }
    @Override
    protected Boolean doInBackground(String... params) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, params[0], null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Iterator<String> key = response.keys();
                    while (key.hasNext()) {
                        String k = key.next();
                        JSONArray ja = null;
                        ja = response.getJSONArray(k);
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject object = ja.getJSONObject(i);
                            ClassResturants classResturants = new ClassResturants(
                                    object.getString("RestID"),
                                    object.getString(mcontext.getResources().getString(R.string.RestDataname))//Restaurantname  RestDataname
                                    ,object.getString(mcontext.getResources().getString(R.string.OpenOrClose)),object.getString("MinimumOrderPrice")
                                    ,4,"http://takeaway.afshat.com/Images/Restaurant/"+object.getString("RestImg")
                                    ,object.getDouble("OfferValue"),object.getInt("OfferFeeTypeId")
                            );
                            resturantDataId =object.getInt("RestDataID");
                            classResturants.setName(object.getString(mcontext.getResources().getString(R.string.RestDataname) ));
                            classResturants.setFeeDeliveryValue(object.getDouble("DeliveryValue"));
                            classResturants.setofferID(object.getInt("OfferID"));

                            FeeTypeid=1;
                            classResturantsList.add(classResturants);
                            originalList.add(classResturants);
                        }
                    } // while loop end

                    adapterClassResturant.notifyDataSetChanged();
                    restnumber.setText(classResturantsList.size()+mcontext.getResources().getString(R.string.Resturantsarefound));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        MyApplication.getInstance().addToReqQueue(jsonObjReq, "jreq");
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        pDialog.dismiss();
         pDialog.hide();

    }
}
