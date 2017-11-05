package com.example.srccode.takeawayproject.Activities;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.srccode.takeawayproject.Adapters.AdapterClassRestOffer;
import com.example.srccode.takeawayproject.Classes.ClassResturants;
import com.example.srccode.takeawayproject.Classes.ClassViewOrderDb;
import com.example.srccode.takeawayproject.Classes.JSONResponse;
import com.example.srccode.takeawayproject.Classes.RetofitResturantClass;
import com.example.srccode.takeawayproject.UI.RecyclerItemClickListener;
import com.example.srccode.takeawayproject.WebServices.ResturantOfferWebService;
import com.example.srccode.takeawayproject.WebServices.ResturantWebService;
import com.example.srccode.takeawayproject.service.AdapterResturantRetrofit;
import com.example.srccode.takeawayproject.service.RetrofitClass;
import com.example.srccode.takeawayproject.service.RetrofitService;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.TextSliderView;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.srccode.takeawayproject.Global.GlopalClass.FeeDeliveryValue;
import static com.example.srccode.takeawayproject.Global.GlopalClass.GlobalRegionID;
import static com.example.srccode.takeawayproject.Global.GlopalClass.HostName;
import static com.example.srccode.takeawayproject.Global.GlopalClass.OfferFeeTypeId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.OfferResturantValue;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restimage;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restname;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restopening;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restrating;
import static com.example.srccode.takeawayproject.Global.GlopalClass.adapterClassRestOffer;
import static com.example.srccode.takeawayproject.Global.GlopalClass.classResturantsList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.currentusedresturantId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.mincharge;
import static com.example.srccode.takeawayproject.Global.GlopalClass.offerID;
import static com.example.srccode.takeawayproject.Global.GlopalClass.originalList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.recyclerView;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantDataId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantofferFlag;
import static com.example.srccode.takeawayproject.Global.GlopalClass.retofitResturantClass;
import static com.example.srccode.takeawayproject.Global.GlopalClass.retofitoriginalList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.tableviewOrderDb;
import static com.example.srccode.takeawayproject.Global.GlopalClass.typeface;

public class ActivityRestOffer extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    Dialog custom;
    SwipeRefreshLayout swipeContainer;
    public static TextView restoffernumber;
    public static SliderLayout mDemoSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_offer);
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        int[] GalImages = new int[] {R.drawable.resturantimage, R.drawable.resturantimage1, R.drawable.resturantimage2};

        for(int image : GalImages) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description("")/// description of the image
                    .image(image).setBitmapTransformation(new CenterCrop())
            ;
            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);//Accordion
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);//Center_Bottom
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        ActiveAndroid.initialize(this);
        // Always cast your custom Toolbar here, and set it as the ActionBar.
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // Get the ActionBar here to configure the way it behaves.
        final ActionBar ab = getSupportActionBar();
        setSupportActionBar(mToolbar);
        TextView  mTitle = (TextView) findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.Resturantnbyoffers);
        ImageButton imageButton=(ImageButton)findViewById(R.id.next_btn_search);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), Home_MainActivity.class);
                startActivity(homeIntent);
            }
        });
        classResturantsList = new ArrayList<ClassResturants>();
        originalList = new ArrayList<ClassResturants>();
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
                        Toast.makeText(ActivityRestOffer.this,"Resturants are Refreshed.", Toast.LENGTH_SHORT).show();
                        swipeContainer.setRefreshing(false);
                    }
                });


            }
        });
        restoffernumber=(TextView)findViewById(R.id.resttextid);

        if (GlobalRegionID!=0){
            resturantofferFlag=1;
            new ResturantOfferWebService(getApplicationContext()).execute("http://"+ HostName+"/api/Restaurants?RegionID=" + GlobalRegionID);//GlobalRegionID

        }
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);


        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        adapterClassRestOffer = new AdapterClassRestOffer(getApplicationContext(),classResturantsList, originalList);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterClassRestOffer);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view,final int position) {
                        tableviewOrderDb =new Select().from(ClassViewOrderDb.class).execute();

                        if (tableviewOrderDb.size()!=0 && currentusedresturantId!=Integer.parseInt(classResturantsList.get(position).getId())){
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ActivityRestOffer.this);

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
                                    resturantDataId =classResturantsList.get(position).getresturantDataId();
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
                            resturantDataId =classResturantsList.get(position).getresturantDataId();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                custom = new Dialog(ActivityRestOffer.this);
                custom.setContentView(R.layout.resturantfilter);
                custom.setTitle("filter");
                RadioGroup filtergroup=(RadioGroup)custom.findViewById(R.id.langgroup);
                filtergroup.setOnCheckedChangeListener(new
                                                               RadioGroup.OnCheckedChangeListener() {
                                                                   @Override
                                                                   public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                                       switch (checkedId) {
                                                                           case R.id.NameAsc:
                                                                               adapterClassRestOffer.sortByNameAsc();
                                                                               custom.dismiss();
                                                                               break;
                                                                           case R.id.NameDesc:
                                                                               adapterClassRestOffer.sortByNameDesc();
                                                                               custom.dismiss();
                                                                               break;
                                                                           case R.id.RateAsc:
                                                                               adapterClassRestOffer.sortByRateAsc();
                                                                               custom.dismiss();
                                                                               break;
                                                                           case R.id.RateDesc:
                                                                               adapterClassRestOffer.sortByRateDesc();
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
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.resturant_sort_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.requestFocus();

        int searchEditId = android.support.v7.appcompat.R.id.search_src_text;
        EditText et = (EditText) searchView.findViewById(searchEditId);
        et.setTextColor(Color.WHITE);
        et.setHintTextColor(Color.WHITE);
        et.setTypeface(typeface);

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                adapterClassRestOffer.filterData(query);
                // return false;
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterClassRestOffer.filterData(newText);
                return false;
            }

        });
        return super.onCreateOptionsMenu(menu);
    }
}


