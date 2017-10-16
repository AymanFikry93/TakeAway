package com.example.srccode.takeawayproject.Activities;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.srccode.takeawayproject.WebServices.CategoryJsonAsync;


import static com.example.srccode.takeawayproject.Global.GlopalClass.ExpandList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.HostName;
import static com.example.srccode.takeawayproject.Global.GlopalClass.OfferFeeTypeId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.OfferResturantValue;
import static com.example.srccode.takeawayproject.Global.GlopalClass.catid;
import static com.example.srccode.takeawayproject.Global.GlopalClass.grouplist;
import static com.example.srccode.takeawayproject.Global.GlopalClass.iscooking;
import static com.example.srccode.takeawayproject.Global.GlopalClass.itemOfferFeeTypeId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.itemOfferValue;
import static com.example.srccode.takeawayproject.Global.GlopalClass.itemfoodname;
import static com.example.srccode.takeawayproject.Global.GlopalClass.itemfoodprice;
import static com.example.srccode.takeawayproject.Global.GlopalClass.itemid;
import static com.example.srccode.takeawayproject.Global.GlopalClass.itemofferid;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.url;

/**
 * Created by ayman on 2017-05-21.
 */

public class FragmentCategory extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_pager_category,container,false);


        ExpandList = (ExpandableListView) view.findViewById(R.id.exp_list);

//       catid =Integer.valueOf(dataresturant);
        url ="http://"+HostName+"/api/Categories?restaurantID="+resturantId;//resturantId
//        url = "https://aymanfikryeng.000webhostapp.com/ItemGroup.json";
        new CategoryJsonAsync (getContext());


        ExpandList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
             //   Toast.makeText(getActivity(), grouplist.get(groupPosition).getId()+"selected category", Toast.LENGTH_SHORT).show();
                catid=Integer.valueOf(grouplist.get(groupPosition).getId());
                 itemid= grouplist.get(groupPosition).getItems().get(childPosition).getId();
                 itemfoodname=  grouplist.get(groupPosition).getItems().get(childPosition).getName();
               String prc  =  grouplist.get(groupPosition).getItems().get(childPosition).getPrice();
                itemfoodprice=  Float.parseFloat(prc);
                itemofferid=grouplist.get(groupPosition).getItems().get(childPosition).getitemofferid();
                itemOfferValue=grouplist.get(groupPosition).getItems().get(childPosition).getitemOfferValue();
                itemOfferFeeTypeId=grouplist.get(groupPosition).getItems().get(childPosition).getitemOfferFeeTypeId();

                iscooking =grouplist.get(groupPosition).getItems().get(childPosition).getIsCooking();
//                if(OfferFeeTypeId==1) {
//                    itemfoodprice=itemfoodprice-OfferResturantValue;
//                }
//                else if(OfferFeeTypeId==2) {
//                    itemfoodprice=itemfoodprice-(itemfoodprice*OfferResturantValue*.01);
//                }
                if(itemOfferFeeTypeId==1) {
                    itemfoodprice=itemfoodprice-itemOfferValue;
                }
                else if(itemOfferFeeTypeId==2) {
                    itemfoodprice=itemfoodprice-(itemfoodprice*itemOfferValue*.01);
                }
                // String itemfoodimage=  grouplist.get(groupPosition).getItems().get(childPosition).getImage();

              Intent gotoitemfooddetails=new Intent(getActivity(), ActivityItemDetailsTest.class);

                startActivity(gotoitemfooddetails);
                return false;
            }
        });

        return  view;

    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        adapter.onSaveInstanceState(outState);
//    }
}
