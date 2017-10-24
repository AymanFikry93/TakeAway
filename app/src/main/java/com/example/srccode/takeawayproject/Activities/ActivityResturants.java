package com.example.srccode.takeawayproject.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import com.example.srccode.takeawayproject.Adapters.AdapterClassResturant;
import com.example.srccode.takeawayproject.Classes.ClassResturants;
import com.example.srccode.takeawayproject.Classes.ClassViewOrderDb;
import com.example.srccode.takeawayproject.Classes.SetNotificationCount;
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

import static com.example.srccode.takeawayproject.Global.GlopalClass.languagetype;
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
    ImageButton imageButton;
    TextView mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturants);

        ActiveAndroid.initialize(this);
// Always cast your custom Toolbar here, and set it as the ActionBar.
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // Get the ActionBar here to configure the way it behaves.
        final ActionBar ab = getSupportActionBar();
        setSupportActionBar(mToolbar);
         mTitle = (TextView) findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.category_resturants);
        mTitle.setTypeface(typeface);
         imageButton=(ImageButton)findViewById(R.id.next_btn_search);

//        if(languagetype.equals("ar")){
//            Drawable d = ResourcesCompat.getDrawable(getResources(),R.drawable.next,null);
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                imageButton.setBackground(d);
//            }
//        }else
//        {
//            Drawable d = ResourcesCompat.getDrawable(getResources(),R.drawable.back_arrow,null);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                imageButton.setBackground(d);
//            }
//
//        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), Home_MainActivity.class);
                startActivity(homeIntent);
            }
        });

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.activity_test_json);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        startActivity(getIntent());
                        Toast.makeText(ActivityResturants.this,"Resturants are Refreshed.", Toast.LENGTH_SHORT).show();
                        swipeContainer.setRefreshing(false);
                    }
                });


            }
        });
         restnumber=(TextView)findViewById(R.id.resttextid);
        restnumber.setTypeface(typeface);

        classResturantsList = new ArrayList<ClassResturants>();
        originalList = new ArrayList<ClassResturants>();
//        SharedPreferences sharedPreferences_show=getSharedPreferences("resturantdata", Context.MODE_PRIVATE);// to get the contetnt of the data base
//        int RegionID =sharedPreferences_show.getInt("RegionId",0);
         if(findresturantmapflag==1){
             findresturantmapflag=0;
             resturantofferFlag=0;
              new ResturantWebService(getApplicationContext()).
             execute("http://"+HostName+"/api/Restaurants?RegionID="+ClientInformationregionId);
         }else {
             resturantofferFlag=0;
             findresturantmapflag=0;
              new ResturantDataWebService(getApplicationContext())
               .execute("http://"+HostName+"/api/Restaurants?RegionID=" + GlobalRegionID);
         }
        tableviewOrderDb =new Select().from(ClassViewOrderDb.class).execute();

        recyclerresturantView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerresturantView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerresturantView.setLayoutManager(layoutManager);//new GridLayoutManager(this,2)
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
                            Intent ResturantsIntent = new Intent(getApplicationContext(), ActivityCategory.class);
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
                    Intent ResturantsIntent = new Intent(getApplicationContext(), ActivityCategory.class);
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
//                custom.setTitle("filter");
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
 public void CustomFilterDialog(){

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


 }

}


