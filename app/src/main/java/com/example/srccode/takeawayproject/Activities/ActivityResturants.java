package com.example.srccode.takeawayproject.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import com.example.srccode.takeawayproject.Adapters.AdapterClassResturant;
import com.example.srccode.takeawayproject.Classes.ClassResturants;
import com.example.srccode.takeawayproject.Classes.ClassViewOrderDb;
import com.example.srccode.takeawayproject.UI.RecyclerItemClickListener;
import com.example.srccode.takeawayproject.WebServices.ResturantDataWebService;
import com.example.srccode.takeawayproject.WebServices.ResturantWebService;
import java.util.ArrayList;

import static com.example.srccode.takeawayproject.Global.GlopalClass.ClientInformationregionId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.FeeDeliveryValue;
import static com.example.srccode.takeawayproject.Global.GlopalClass.GlobalRegionID;
import static com.example.srccode.takeawayproject.Global.GlopalClass.HostName;

import static com.example.srccode.takeawayproject.Global.GlopalClass.OfferFeeTypeId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.OfferResturantValue;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restimage;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restname;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restopening;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restrating;

import static com.example.srccode.takeawayproject.Global.GlopalClass.adapterClassResturant;
import static com.example.srccode.takeawayproject.Global.GlopalClass.classResturantsList;

import static com.example.srccode.takeawayproject.Global.GlopalClass.currentusedresturantId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.findresturantmapflag;

import static com.example.srccode.takeawayproject.Global.GlopalClass.mincharge;
import static com.example.srccode.takeawayproject.Global.GlopalClass.offerID;
import static com.example.srccode.takeawayproject.Global.GlopalClass.originalList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantId;

import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantofferFlag;
import static com.example.srccode.takeawayproject.Global.GlopalClass.tableviewOrderDb;
import static com.example.srccode.takeawayproject.Global.GlopalClass.typeface;

public class ActivityResturants extends AppCompatActivity {



    RecyclerView recyclerresturantView ;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeContainer;
    Dialog custom;
   public static TextView restnumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturants);

        ActiveAndroid.initialize(this);


        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.activity_test_json);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
                Toast.makeText(ActivityResturants.this,"Resturants are Refreshed.", Toast.LENGTH_SHORT).show();
            }
        });
         restnumber=(TextView)findViewById(R.id.resttextid);

        classResturantsList = new ArrayList<ClassResturants>();
        originalList = new ArrayList<ClassResturants>();
//        SharedPreferences sharedPreferences_show=getSharedPreferences("resturantdata", Context.MODE_PRIVATE);// to get the contetnt of the data base
//        int RegionID =sharedPreferences_show.getInt("RegionId",0);
         if(findresturantmapflag==1){
             findresturantmapflag=0;
             resturantofferFlag=0;

     new ResturantWebService(getApplicationContext()).execute("http://"+HostName+"/api/Restaurants?RegionID="+ClientInformationregionId);
         }else {
             resturantofferFlag=0;
             findresturantmapflag=0;
             new ResturantDataWebService(getApplicationContext(),"http://"+HostName+"/api/Restaurants?RegionID=" + GlobalRegionID);

             //new ResturantWebService(getApplicationContext()).execute("http://"+HostName+"/api/Restaurants?RegionID=" + RegionID);
         }
        tableviewOrderDb =new Select().from(ClassViewOrderDb.class).execute();

//        listView = (ListView) findViewById(R.id.list);
//        adapterClassResturant = new AdapterClassResturant(getApplicationContext(), R.layout.resturants_row, classResturantsList, originalList);
//        listView.setAdapter(adapterClassResturant);

        recyclerresturantView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerresturantView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerresturantView.setLayoutManager(layoutManager);
        recyclerresturantView.setItemAnimator(new DefaultItemAnimator());
        adapterClassResturant = new AdapterClassResturant(getApplicationContext(),classResturantsList, originalList);

        recyclerresturantView.setAdapter(adapterClassResturant);
        recyclerresturantView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view,final int position) {
                new Delete().from(ClassViewOrderDb.class).execute();
             //   tableviewOrderDb =new Select().from(ClassViewOrderDb.class).execute();

              // new ResturantAlertDialoge(getBaseContext(),position);
                if (tableviewOrderDb.size()!=0 && currentusedresturantId!=Integer.parseInt(classResturantsList.get(position).getId())){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ActivityResturants.this);

                    // Setting Dialog Title
                    alertDialog.setTitle("Warning...");

                    // Setting Dialog Message
                    alertDialog.setMessage("this order from another resturant,your order will be lost");

                    // Setting Icon to Dialog
                    alertDialog.setIcon(R.drawable.cleararea);

                    // Setting Positive "Yes" Button
                    alertDialog.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {
                            new Delete().from(ClassViewOrderDb.class).execute();

                            // Write your code here to invoke YES event
                            Toast.makeText(getApplicationContext(), "You cleared your order", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), classResturantsList.get(position).getName(), Toast.LENGTH_LONG).show();
                            Intent ResturantsIntent = new Intent(getApplicationContext(), PagerActivity.class);
                            resturantId = Integer.parseInt(classResturantsList.get(position).getId());
                            Restname = classResturantsList.get(position).getName();
                            Restimage = classResturantsList.get(position).getImage();
                            Restrating = classResturantsList.get(position).getRating();
                            Restopening = classResturantsList.get(position).getopenandclose();
                            FeeDeliveryValue=classResturantsList.get(position).getFeeDeliveryValue();
                            OfferResturantValue= classResturantsList.get(position).getofferValue();
                            OfferFeeTypeId=classResturantsList.get(position).getOfferFeeTypeId();
                            offerID=classResturantsList.get(position).getofferID();
                            Restimage= classResturantsList.get(position).getImage();
                            mincharge= Double.parseDouble(classResturantsList.get(position).getmincharge());
                            currentusedresturantId =resturantId;
                             startActivity(ResturantsIntent);
                        }
                    });

                    // Setting Negative "NO" Button
                    alertDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                            dialog.cancel();

                            return ;
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();

                }
                else{
                    Toast.makeText(getApplicationContext(), classResturantsList.get(position).getName(), Toast.LENGTH_LONG).show();
                    Intent ResturantsIntent = new Intent(getApplicationContext(), PagerActivity.class);
                    resturantId = Integer.parseInt(classResturantsList.get(position).getId());
                    Restname = classResturantsList.get(position).getName();
                    Restimage = classResturantsList.get(position).getImage();
                    Restrating = classResturantsList.get(position).getRating();
                    Restopening = classResturantsList.get(position).getopenandclose();
                    FeeDeliveryValue=classResturantsList.get(position).getFeeDeliveryValue();
                    OfferResturantValue= classResturantsList.get(position).getofferValue();
                    OfferFeeTypeId=classResturantsList.get(position).getOfferFeeTypeId();
                    Restimage= classResturantsList.get(position).getImage();
                    mincharge= Double.parseDouble(classResturantsList.get(position).getmincharge());
                    currentusedresturantId =resturantId;
                    startActivity(ResturantsIntent);

                }

                    }
                })

        );

    }
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.resturant_sort_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        searchItem.expandActionView();
        searchView.requestFocus();

        int searchEditId = android.support.v7.appcompat.R.id.search_src_text;
        EditText et = (EditText) searchView.findViewById(searchEditId);
        et.setTextColor(Color.WHITE);
        et.setHintTextColor(Color.WHITE);
        et.setTypeface(typeface);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                adapterClassResturant.filterData(query);
               // return false;
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterClassResturant.filterData(newText);
                return false;
            }

        });
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                custom = new Dialog(ActivityResturants.this);
                custom.setContentView(R.layout.resturantfilter);
                custom.setTitle("filter");
                RadioGroup filtergroup=(RadioGroup)custom.findViewById(R.id.langgroup);
                filtergroup.setOnCheckedChangeListener(new
                                           RadioGroup.OnCheckedChangeListener() {
                                              @Override
                                                 public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                     switch (checkedId) {
                                                           case R.id.NameAsc:
                                                                    adapterClassResturant.sortByNameAsc();
                                                                        custom.dismiss();
                                                                           break;
                                                                             case R.id.NameDesc:
                                                                                 adapterClassResturant.sortByNameDesc();
                                                                                 custom.dismiss();
                                                                                 break;
                                                                             case R.id.RateAsc:
                                                                                 adapterClassResturant.sortByRateAsc();
                                                                                 custom.dismiss();
                                                                                 break;
                                                                             case R.id.RateDesc:
                                                                                 adapterClassResturant.sortByRateDesc();
                                                                                 custom.dismiss();
                                                                                 break;
                                                                             default:
                                                                                 custom.dismiss();
                                                                                 break;
                                                       }
                                                }
                                                                 });

                custom.show();
                break;


        }
        return super.onOptionsItemSelected(item);
    }


}


