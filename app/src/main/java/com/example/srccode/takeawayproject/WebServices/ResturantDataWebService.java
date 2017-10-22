package com.example.srccode.takeawayproject.WebServices;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

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
//        Runnable runnable=new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    pDialog = new ProgressDialog(mcontext);
//                    pDialog.setMessage("Loading...");
//            dialog.setTitle("Connecting Server");
//            dialog.show();
//            dialog.setCancelable(false);
//                }catch (Exception e)
//                {
//                    e.getMessage();
//
//                }
//            }
//        };
//        Thread thread=new Thread(runnable);
//        thread.start();

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

                    //   pDialog.hide();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  PD.dismiss();
                // pDialog.hide();
            }
        });
        MyApplication.getInstance().addToReqQueue(jsonObjReq, "jreq");
        return null;
    }


}
