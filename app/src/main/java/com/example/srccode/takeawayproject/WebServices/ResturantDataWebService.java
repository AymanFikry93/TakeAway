package com.example.srccode.takeawayproject.WebServices;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.srccode.takeawayproject.Activities.R;
import com.example.srccode.takeawayproject.Classes.ClassResturants;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.TextSliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.concurrent.RunnableFuture;

import dmax.dialog.SpotsDialog;
import retrofit2.http.Url;

import static com.example.srccode.takeawayproject.Activities.ActivityResturants.mDemoSlider;
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
    String Resturanturl;
    AlertDialog pDialog;
    public ResturantDataWebService(Context context){
        mcontext=context;
//        urlll=urll;


    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

                try {
                    pDialog = new SpotsDialog(mcontext, R.style.CustomDialog);//, R.style.CustomDialog
                    pDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//                    pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                    pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    pDialog.setTitle(mcontext.getResources().getString(R.string.ConnectingServer));
                    pDialog.setMessage(mcontext.getResources().getString(R.string.Loading));
                    pDialog.show();
                    pDialog.setCancelable(false);
                }catch (Exception e)
                {
                    e.getMessage();

                }



    }
    @Override
    protected Boolean doInBackground(String... params) {
        Resturanturl=params[0];
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,Resturanturl, null, new Response.Listener<JSONObject>() {
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
                                       ,object.getDouble("DeliveryValue"),object.getInt("OfferID")

                            );
                            resturantDataId =object.getInt("RestDataID");
                            FeeTypeid=1;
                            classResturantsList.add(classResturants);
                            originalList.add(classResturants);
                        }
                    } // while loop end

                    adapterClassResturant.notifyDataSetChanged();
                    restnumber.setText(classResturantsList.size()+" "+mcontext.getResources().getString(R.string.Resturantsarefound));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // Load response of any request from cache
        RequestQueue requestQueue = MyApplication.getInstance().getReqQueue();
        if (requestQueue != null) {
            Cache cache = requestQueue.getCache();
            if (cache != null) {
                Cache.Entry entry = cache.get(params[0]);
                if (entry != null) {
                    // Handle the data here....
                } else {
                    // data is not present in the cache..so send server request...
                }
            }
        }

        //Invalidate the cache
        //        If you do not want to delete the data present in the cache, instead of it, you want to invalidate the cache. You can do it as below. Later on, when data comes from the server, it will replace the old data present in the cache.
        requestQueue = MyApplication.getInstance().getReqQueue();
        if (requestQueue != null) {
            Cache cache = requestQueue.getCache();
            if(cache != null) {
                cache.invalidate(params[0], true);
            }
        }

        //        Deleting cached data for any url
//        If you want to delete any cached data, you can do it as below.
         requestQueue = MyApplication.getInstance().getReqQueue();
        if (requestQueue != null) {
            Cache cache = requestQueue.getCache();
            if(cache != null) {
                cache.remove(params[0]);
            }
        }

       // Deleting all cached data
       // If you want to delete all cached data, you can do it as below.
         requestQueue = MyApplication.getInstance().getReqQueue();

        if (requestQueue != null) {
            Cache cache = requestQueue.getCache();
            if(cache != null) {
                cache.clear();
            }
        }


        MyApplication.getInstance().addToReqQueue(jsonObjReq, "jreq");
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        pDialog.dismiss();
         pDialog.hide();
    }
}
