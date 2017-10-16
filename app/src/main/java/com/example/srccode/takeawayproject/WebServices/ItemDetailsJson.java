package com.example.srccode.takeawayproject.WebServices;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.srccode.takeawayproject.Adapters.AdapterClassItemExpandList;
import com.example.srccode.takeawayproject.Classes.ClassItemFood;
import com.example.srccode.takeawayproject.Classes.ClassItemFoodDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import static com.example.srccode.takeawayproject.Global.GlopalClass.ExpanditemList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.ExpitemAdapter;
import static com.example.srccode.takeawayproject.Global.GlopalClass.PD;
import static com.example.srccode.takeawayproject.Global.GlopalClass.ch_item_list;
import static com.example.srccode.takeawayproject.Global.GlopalClass.ch_list2;


import static com.example.srccode.takeawayproject.Global.GlopalClass.groupitemlist;
import static com.example.srccode.takeawayproject.Global.GlopalClass.iscooking;
import static com.example.srccode.takeawayproject.Global.GlopalClass.itemid;
import static com.example.srccode.takeawayproject.Global.GlopalClass.url;

/**
 * Created by ayman on 2017-08-12.
 */

public class ItemDetailsJson {

    Context mcontext;
    public ItemDetailsJson(Context context){
        mcontext=context;
        makejsonobjreq();
    }



    private void makejsonobjreq() {
        //  PD.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    Iterator<String> key = response.keys();
                    while (key.hasNext()) {
                        String k = key.next();

                        JSONArray ja=null;
                        ja = response.getJSONArray(k);
                        groupitemlist = new ArrayList<ClassItemFood>();

                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject cat = ja.getJSONObject(i);
                            String _itemid= cat.getString("ID");
                            iscooking=cat.getBoolean("IsCooking");

                            if(_itemid.equals(itemid)){
                                JSONArray child =  cat.getJSONArray("ItemFoodDetails");

                                ch_item_list = new ArrayList<ClassItemFoodDetails>();
                                ch_list2 = new ArrayList<ClassItemFoodDetails>();


                                for (int x = 0; x < child.length();x++) {

                                    JSONObject _child = child.getJSONObject(x);
                                    ClassItemFoodDetails ch = new ClassItemFoodDetails();
                                    if(_child.getString("CategoryTypeID").equals("2")){
                                        ch.setId(_child.getString("ID"));
                                        ch.setName(_child.getString("ItemFood"));
                                        ch.setCategoryTypeID(_child.getString("CategoryTypeID"));
                                        ch_item_list.add(ch);

                                    }
                                    else if(_child.getString("CategoryTypeID").equals("3")){
                                        ch.setId(_child.getString("ID"));
                                        ch.setName(_child.getString("ItemFood"));
                                        ch.setCategoryTypeID(_child.getString("CategoryTypeID"));
                                        ch_list2.add(ch);

                                    }


                                }// for loop end
                                ClassItemFood gru = null;

                                if(ch_item_list.size()==0 &&ch_list2.size()==0){
                                    gru = new ClassItemFood();
                                    gru.setId(cat.getString("ID"));

                                }
                                else{
                                    if(ch_item_list.size()!=0){

                                        for(int y=0;y<ch_item_list.size();y++){

                                            gru = new ClassItemFood();
                                            gru.setName("مشروبات");
                                            gru.setId(cat.getString("ID"));
                                            gru.setItemsdetails(ch_item_list);

                                        }
                                        groupitemlist.add(gru);
                                    }


                                    if (ch_list2.size()!=0) {
                                        gru = new ClassItemFood();
                                        for (int y = 0; y < ch_list2.size(); y++) {

                                            gru.setName("إضافات");
                                            gru.setId(cat.getString("ID"));
                                            gru.setItemsdetails(ch_list2);

                                        }
                                        groupitemlist.add(gru);
                                    }
                                }
                            }
                        }

                    } // while loop end


                    ExpitemAdapter = new AdapterClassItemExpandList(mcontext, groupitemlist);
                    ExpanditemList.setAdapter(ExpitemAdapter);


                }
                catch (JSONException e) {
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
        @Override
        protected void finalize() throws Throwable {
                     super.finalize();
        }

}

