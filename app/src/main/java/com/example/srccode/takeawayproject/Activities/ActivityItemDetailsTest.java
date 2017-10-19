package com.example.srccode.takeawayproject.Activities;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;

import com.activeandroid.query.Delete;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.srccode.takeawayproject.Classes.ClassCookingDB;
import com.example.srccode.takeawayproject.Classes.ClassViewOrderDb;
import com.example.srccode.takeawayproject.Global.GlobalMethods;
import com.example.srccode.takeawayproject.WebServices.DownloadImageTask;
import com.example.srccode.takeawayproject.WebServices.ItemDetailsJson;

import java.util.ArrayList;

import static android.view.View.VISIBLE;
import static com.example.srccode.takeawayproject.Global.GlopalClass.ExpanditemList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.HostName;
import static com.example.srccode.takeawayproject.Global.GlopalClass.additions;
import static com.example.srccode.takeawayproject.Global.GlopalClass.additionsname;
import static com.example.srccode.takeawayproject.Global.GlopalClass.catId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.classeslistcooking;
import static com.example.srccode.takeawayproject.Global.GlopalClass.cookingvalueslist;
import static com.example.srccode.takeawayproject.Global.GlopalClass.currentusedresturantId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.customadapter;
import static com.example.srccode.takeawayproject.Global.GlopalClass.drinks;
import static com.example.srccode.takeawayproject.Global.GlopalClass.drinksname;
import static com.example.srccode.takeawayproject.Global.GlopalClass.groupitemlist;
import static com.example.srccode.takeawayproject.Global.GlopalClass.iscooking;
import static com.example.srccode.takeawayproject.Global.GlopalClass.itemfoodImage;
import static com.example.srccode.takeawayproject.Global.GlopalClass.itemfoodname;
import static com.example.srccode.takeawayproject.Global.GlopalClass.itemfoodprice;
import static com.example.srccode.takeawayproject.Global.GlopalClass.itemid;
import static com.example.srccode.takeawayproject.Global.GlopalClass.itemofferid;
import static com.example.srccode.takeawayproject.Global.GlopalClass.languagetype;
import static com.example.srccode.takeawayproject.Global.GlopalClass.quantity;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.typeface;
import static com.example.srccode.takeawayproject.Global.GlopalClass.url;

public class ActivityItemDetailsTest extends AppCompatActivity {

    ImageView imageitem;
    TextView itemdetailsName;
    TextView itemdetailsprice;
    int selectedcookcatid=0;
    Spinner cookingspinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details_test);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        this.setSupportActionBar(toolbar);
//        TextView tv = new TextView(getApplicationContext());
//        tv.setTextSize(10);
//        this.getSupportActionBar().setCustomView(tv);


        ActiveAndroid.initialize(this);
        url = "http://"+ HostName+"/api/ItemFoods?CategoryID=" + catId;
        ExpanditemList = (ExpandableListView) findViewById(R.id.exp_list_food);
//        GlobalMethods globalmethod = new GlobalMethods(getApplicationContext());
//        globalmethod.alert();

        new ItemDetailsJson(getApplicationContext());

        imageitem = (ImageView) findViewById(R.id.detailsimage);
        imageitem.setImageResource(R.mipmap.ic_launcher);
        new DownloadImageTask(imageitem).execute(itemfoodImage);
       // Glide.with(getApplicationContext()).load(itemfoodImage).into(imageitem);

        itemdetailsName = (TextView) findViewById(R.id.detailsName);
        itemdetailsName.setText(itemfoodname);

        itemdetailsName.setTypeface(typeface);

        itemdetailsprice = (TextView) findViewById(R.id.detailsprice);
        itemdetailsprice.setText(getString(R.string.Price) + "  " + itemfoodprice);

        Button button = (Button) findViewById(R.id.Purchasebutton2);
        button.setTypeface(typeface);
        cookingspinner = (Spinner) findViewById(R.id.spinner_cooking);
        cookingspinner.setVisibility(View.GONE);

        if (iscooking) {
            customadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, cookingvalueslist);
            customadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            cookingspinner.setAdapter(customadapter);
            cookingspinner.setVisibility(VISIBLE);
            cookingspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedcookcatid = Integer.parseInt(classeslistcooking.get(position).getcookid());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }



    }

    public void Purchaseincrement(View view) {

        quantity = quantity + 1;
        TextView itemquantity = (TextView) findViewById(R.id.itemquantityId2);
        itemquantity.setText("" +quantity);
        double totalprice= itemfoodprice*quantity;
        itemdetailsprice.setText(getString(R.string.Price)+" "+totalprice);

    }


    public void  Purchasedeccrement(View view) {
        if (quantity == 1) {
            return;
        }
        quantity = quantity - 1;
        TextView itemquantity = (TextView) findViewById(R.id.itemquantityId2);
        itemquantity.setText("" +quantity);
        double totalprice= itemfoodprice*quantity;
        itemdetailsprice.setText(getString(R.string.Price)+" "+totalprice);



    }

    public void addPurchase(View view) {


        if(groupitemlist!=null) {

        for(int y=0;y < groupitemlist.size();y++){
            if(groupitemlist.get(y).getItemsdetails()!=null){
                for(int x=0;x < groupitemlist.get(y).getItemsdetails().size();x++) {
                    String iddrink = groupitemlist.get(y).getItemsdetails().get(x).getId();
                    if (iddrink.equals(String.valueOf(drinks))){
                        drinksname = groupitemlist.get(y).getItemsdetails().get(x).getName();
                    }
                    else if (iddrink.equals(String.valueOf(additions))) {
                        additionsname = groupitemlist.get(y).getItemsdetails().get(x).getName();
                    }
                }
            }else
            {
                drinksname="";
                additionsname="";
                drinks=0;
                additions=0;
            }
        }

        }
        else{

            //  if(groupitemlist.size()==0) {

            drinksname="";
            additionsname="";
            drinks=0;
            additions=0;
            //   }s
        }
        currentusedresturantId =resturantId;
        ClassViewOrderDb tableViewOrderDB= new ClassViewOrderDb(itemid,itemfoodname,String.valueOf(itemfoodprice) ,itemfoodImage
                ,String.valueOf(quantity),String.valueOf(drinks),drinksname,String.valueOf(additions)
                ,additionsname,itemofferid,selectedcookcatid,currentusedresturantId);
        tableViewOrderDB.save();
        quantity=1;
        Intent gotovieworder=new Intent(getApplicationContext(), ActivityCategory.class);
        startActivity(gotovieworder);



    }
//
//    @Override
//    protected void onDestroy() {
//        // call the superclass method first
//        super.onDestroy();
//        ActiveAndroid.initialize(this);
//        new Delete().from(ClassViewOrderDb.class).execute();
//
//    }
}
