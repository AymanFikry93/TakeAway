package com.example.srccode.takeawayproject.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.srccode.takeawayproject.Classes.ClassViewOrderDb;
import com.example.srccode.takeawayproject.Classes.NotificationCountSetClass;
import com.example.srccode.takeawayproject.Classes.SetNotificationCount;
import com.example.srccode.takeawayproject.WebServices.CategoryJsonAsync;

import java.util.List;

import static com.example.srccode.takeawayproject.Global.GlopalClass.ExpandList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.FeeDeliveryValue;
import static com.example.srccode.takeawayproject.Global.GlopalClass.HostName;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restimage;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restname;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restopening;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restrating;
import static com.example.srccode.takeawayproject.Global.GlopalClass.catid;
import static com.example.srccode.takeawayproject.Global.GlopalClass.grouplist;
import static com.example.srccode.takeawayproject.Global.GlopalClass.iscooking;
import static com.example.srccode.takeawayproject.Global.GlopalClass.itemOfferFeeTypeId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.itemOfferValue;
import static com.example.srccode.takeawayproject.Global.GlopalClass.itemfoodname;
import static com.example.srccode.takeawayproject.Global.GlopalClass.itemfoodprice;
import static com.example.srccode.takeawayproject.Global.GlopalClass.itemid;
import static com.example.srccode.takeawayproject.Global.GlopalClass.itemofferid;
import static com.example.srccode.takeawayproject.Global.GlopalClass.mincharge;
import static com.example.srccode.takeawayproject.Global.GlopalClass.notificationCountCart;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.typeface;
import static com.example.srccode.takeawayproject.Global.GlopalClass.url;

public class ActivityCategory extends AppCompatActivity {
    private SwipeRefreshLayout swipeContainer;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.activity_refresh);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                finish();
//                startActivity(getIntent());
                new CategoryJsonAsync(getApplicationContext());
                Toast.makeText(ActivityCategory.this,"Categories are Refreshed.", Toast.LENGTH_SHORT).show();
                swipeContainer.setRefreshing(false);
            }
        });
        TextView restName =(TextView)findViewById(R.id.RestName);
        restName.setText(Restname);

        TextView minchargeName =(TextView)findViewById(R.id.minchargeid);
        minchargeName.setText(mincharge+"");
        TextView restdeliveryName =(TextView)findViewById(R.id.restdeliveryid);
        restdeliveryName.setText(FeeDeliveryValue+"");

        restName.setTypeface(typeface);
        TextView restopened =(TextView)findViewById(R.id.Restopening);
        restopened.setText(Restopening);

        RatingBar restRating=(RatingBar)findViewById(R.id.Restrating_bar);
        restRating.setRating(Restrating);

        LayerDrawable stars = (LayerDrawable) restRating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.rgb(255,131,66), PorterDuff.Mode.SRC_ATOP);
        ImageView restimageView= (ImageView)findViewById(R.id.Resturantlogo);
        Glide.with(ActivityCategory.this).load(Restimage).apply(RequestOptions.circleCropTransform()).into(restimageView);
        restimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoitemfooddetails=new Intent(ActivityCategory.this, PagerActivity.class);

                startActivity(gotoitemfooddetails);
            }
        });
        ExpandList = (ExpandableListView) findViewById(R.id.exp_list);

//       catid =Integer.valueOf(dataresturant);
        url ="http://"+HostName+"/api/Categories?restaurantID="+resturantId;//resturantId
//        url = "https://aymanfikryeng.000webhostapp.com/ItemGroup.json";
        new CategoryJsonAsync(getApplicationContext());
        ActiveAndroid.initialize(this);
        List<ClassViewOrderDb> tableviewOrderDb;
        tableviewOrderDb =new Select().from(ClassViewOrderDb.class).execute();

        if(tableviewOrderDb.size()==0){//&&!Restopening.equals("Opening")
            notificationCountCart=0;
        }else {
            notificationCountCart=tableviewOrderDb.size();
        }

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

                Intent gotoitemfooddetails=new Intent(ActivityCategory.this, ActivityItemDetailsTest.class);

                startActivity(gotoitemfooddetails);
                return false;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chartmenu,menu);
        this.menu = menu;
        MenuItem itemCart = menu.findItem(R.id.action_cart);
        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
        SetNotificationCount.setBadgeCount(this, icon, notificationCountCart);
        return super.onCreateOptionsMenu(menu);

//
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                Intent ResturantsIntent = new Intent(getApplicationContext(), ActivityViewOrder.class);
                startActivity(ResturantsIntent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
