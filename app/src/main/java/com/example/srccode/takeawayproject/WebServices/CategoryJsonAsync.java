package com.example.srccode.takeawayproject.WebServices;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.srccode.takeawayproject.Activities.R;
import com.example.srccode.takeawayproject.Adapters.AdapterClassCategoryExpandList;
import com.example.srccode.takeawayproject.Classes.ClassCategory;
import com.example.srccode.takeawayproject.Classes.ClassItemFood;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import static com.example.srccode.takeawayproject.Global.GlopalClass.ExpAdapter;
import static com.example.srccode.takeawayproject.Global.GlopalClass.ExpandList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.PD;
import static com.example.srccode.takeawayproject.Global.GlopalClass.ch_list;
import static com.example.srccode.takeawayproject.Global.GlopalClass.grouplist;

import static com.example.srccode.takeawayproject.Global.GlopalClass.url;

/**
 * Created by ayman on 2017-08-12.
 */

public class CategoryJsonAsync {
    Context mcontext;
    public CategoryJsonAsync(Context context){
        mcontext=context;
        makejsonobjreq();

    }
    private  void makejsonobjreq() {


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                try {
//                    String encodestring= URLEncoder.encode(String.valueOf(response),"iso-8859-1 ");
//                    response= URLDecoder.decode(encodestring," utf-8");
//
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }


                grouplist = new ArrayList<ClassCategory>();

                try {
                    Iterator<String> key = response.keys();
                    while (key.hasNext()) {
                        String k = key.next();

                        JSONArray ja = null;
                        ja = response.getJSONArray(k);
                        for (int i = 0; i < ja.length(); i++) {

                            JSONObject cat = ja.getJSONObject(i);
                            ClassCategory gru = new ClassCategory();
                            gru.setName(cat.getString("CategoryName"));
                            gru.setId(cat.getString("ID"));

                            JSONArray child =  cat.getJSONArray("ItemFoodList");
                            //  JSONArray child = response.getJSONArray("ItemFoodList");
                            ch_list = new ArrayList<ClassItemFood>();
                            for (int x = 0; x < child.length();x++) {
                                JSONObject _child = child.getJSONObject(x);
                                //_child.getString("notforsale");
                                ClassItemFood ch = new ClassItemFood();
                                ch.setId(_child.getString("ID"));
                                ch.setName(_child.getString(mcontext.getResources().getString(R.string.ItemFood)));
                                ch.setPrice(_child.getString("ItemPrice"));
                                ch.setOfferType(_child.getString((mcontext.getResources().getString(R.string.OfferType))));
                                ch.setitemofferid(_child.getInt("OfferID"));
                                ch.setitemOfferValue(_child.getDouble("OfferValue"));
                                ch.setitemOfferFeeTypeId(_child.getInt("OfferFeeTypeId"));



                                ch.setIsCooking(_child.getBoolean("IsCooking"));

                                //  ch.setImage(jo.getString("image"));
                                ch.setImage("http://takeaway.afshat.com/Images/ItemFood/"+_child.getString("Image"));
                                ch_list.add(ch);

                            }// for loop end
                            gru.setItems(ch_list);
                            grouplist.add(gru);
                        }

                    } // while loop end


                    ExpAdapter = new AdapterClassCategoryExpandList(
                            mcontext, grouplist);
                    ExpandList.setAdapter(ExpAdapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  PD.dismiss();
            }
        });
        MyApplication.getInstance().addToReqQueue(jsonObjReq, "jreq");
    }







//
//    class JSONAsyncTask extends AsyncTask<String,Void,Boolean> {
//
//        ProgressDialog dialog;
//        HttpURLConnection urlConnection;
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
////            dialog = new ProgressDialog(getActivity());
////            dialog.setMessage("Please wait , Loading..");
////            dialog.setTitle("Connecting Server");
////            dialog.show();
////            dialog.setCancelable(false);
//          //  Toast.makeText(getActivity(),"Please wait , Loading..",Toast.LENGTH_LONG).show();
//
//        }
//
//        @Override
//        protected Boolean doInBackground(String... urls) {
//            StringBuilder result = new StringBuilder();
//            try {
//                String[] urlpart= urls[0].split("=",0);
//                String splittedurl=urlpart[0];
//                URL url = new URL(urls[0]);
//
//                urlConnection = (HttpURLConnection) url.openConnection();
////                urlConnection.setRequestMethod("GET");
////                urlConnection.connect();
//
//                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//
//                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//
//                StringBuffer buffer = new StringBuffer();
//                String line;
//                while ((line = reader.readLine()) != null){
//                    buffer.append(line);
//                }
//                String finalJson = buffer.toString();
//
//
//
//                JSONObject jsono = new JSONObject(finalJson);
//                JSONArray jarray = jsono.getJSONArray("ItemFoodList");
//                for (int i=0 ; i<jarray.length();i++)
//                {
//
//                    JSONObject object = jarray.getJSONObject(i);
//                    ClassItemFood ch = new ClassItemFood();
//
//                    ch.setId(object.getString("ID"));
//                    ch.setName(object.getString("ItemFood"));
//                    ch.setImage(object.getString("ItemPrice"));
//
//                    ch_list.add(ch);
//
//                }
//
//            }catch( Exception e) {
//                e.printStackTrace();
//            }
//            finally {
//                urlConnection.disconnect();
//            }
//
//
//
//
//
//
//            return true;
//        }
//
//        @Override
//        protected void onPostExecute(Boolean result) {
//            dialog.cancel();
//          //  adapte.notifyDataSetChanged();
//            if (result == false){
//                Toast.makeText(getActivity(),"Unable to fetch data from server",Toast.LENGTH_LONG).show();
//            }
//        }
//    }
//
//
//
//
//


}
