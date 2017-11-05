package com.example.srccode.takeawayproject.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
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
import android.util.Log;
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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.srccode.takeawayproject.Classes.ClassViewOrderDb;
import com.example.srccode.takeawayproject.Classes.JSONResponse;
import com.example.srccode.takeawayproject.Classes.RetofitResturantClass;
import com.example.srccode.takeawayproject.UI.RecyclerItemClickListener;
import com.example.srccode.takeawayproject.service.AdapterResturantRetrofit;
import com.example.srccode.takeawayproject.service.RetrofitClass;
import com.example.srccode.takeawayproject.service.RetrofitService;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.srccode.takeawayproject.Activities.ActivityResturants.restnumber;
import static com.example.srccode.takeawayproject.Global.GlopalClass.ClientInformationregionId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.FeeDeliveryValue;
import static com.example.srccode.takeawayproject.Global.GlopalClass.FeeTypeid;
import static com.example.srccode.takeawayproject.Global.GlopalClass.GlobalRegionID;
import static com.example.srccode.takeawayproject.Global.GlopalClass.OfferFeeTypeId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.OfferResturantValue;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restimage;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restname;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restopening;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restrating;
import static com.example.srccode.takeawayproject.Global.GlopalClass.adapterResturantRetrofit;
import static com.example.srccode.takeawayproject.Global.GlopalClass.currentusedresturantId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.findresturantmapflag;
import static com.example.srccode.takeawayproject.Global.GlopalClass.mincharge;
import static com.example.srccode.takeawayproject.Global.GlopalClass.offerID;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantDataId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantofferFlag;
import static com.example.srccode.takeawayproject.Global.GlopalClass.retofitResturantClass;
import static com.example.srccode.takeawayproject.Global.GlopalClass.retofitoriginalList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.tableviewOrderDb;
import static com.example.srccode.takeawayproject.Global.GlopalClass.typeface;

public class RetrofitResturantActivity extends AppCompatActivity {

    public static SliderLayout mDemoSlider;
    RecyclerView recyclerresturantView ;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeContainer;
    ImageButton imageButton;
    TextView mTitle;
    Dialog custom;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_resturant);
        ActiveAndroid.initialize(this);

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

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // Get the ActionBar here to configure the way it behaves.
//        final ActionBar ab = getSupportActionBar();
        setSupportActionBar(mToolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.category_resturants);
        mTitle.setTypeface(typeface);
        imageButton=(ImageButton)findViewById(R.id.next_btn_search);

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
                        loadRetrofitResturantJSON();
                        Toast.makeText(RetrofitResturantActivity.this,"Resturants are Refreshed.", Toast.LENGTH_SHORT).show();
                        swipeContainer.setRefreshing(false);
                    }
                });


            }
        });
        restnumber=(TextView)findViewById(R.id.resttextid);
        restnumber.setTypeface(typeface);

        tableviewOrderDb =new Select().from(ClassViewOrderDb.class).execute();

        recyclerresturantView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerresturantView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerresturantView.setLayoutManager(layoutManager);//new GridLayoutManager(this,2)
        recyclerresturantView.setItemAnimator(new DefaultItemAnimator());
        recyclerresturantView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view,final int position) {
                        new Delete().from(ClassViewOrderDb.class).execute();
                        if (tableviewOrderDb.size()!=0 && currentusedresturantId!=Integer.parseInt(retofitResturantClass.get(position).getId())){
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(RetrofitResturantActivity.this);

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
                                    Toast.makeText(getApplicationContext(), "You cleared your order", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(), retofitResturantClass.get(position).getName(), Toast.LENGTH_LONG).show();
                                    Intent ResturantsIntent = new Intent(getApplicationContext(), ActivityCategory.class);
                                    resturantId = Integer.parseInt(retofitResturantClass.get(position).getId());
                                    resturantDataId =retofitResturantClass.get(position).getresturantDataId();
                                    Restname = retofitResturantClass.get(position).getName();
                                    Restimage = retofitResturantClass.get(position).getImage();
                                    Restrating = retofitResturantClass.get(position).getRating();
                                    Restopening = retofitResturantClass.get(position).getopenandclose();
                                    FeeDeliveryValue=retofitResturantClass.get(position).getFeeDeliveryValue();
                                    OfferResturantValue= retofitResturantClass.get(position).getofferValue();
                                    OfferFeeTypeId=retofitResturantClass.get(position).getOfferFeeTypeId();
                                    offerID=retofitResturantClass.get(position).getofferID();
                                    Restimage= retofitResturantClass.get(position).getImage();
                                    mincharge= Double.parseDouble(retofitResturantClass.get(position).getmincharge());
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

                            Toast.makeText(getApplicationContext(), retofitResturantClass.get(position).getName(), Toast.LENGTH_LONG).show();
                            Intent ResturantsIntent = new Intent(getApplicationContext(), ActivityCategory.class);
                            resturantId = Integer.parseInt(retofitResturantClass.get(position).getId());
                            resturantDataId =retofitResturantClass.get(position).getresturantDataId();
                            Restname = retofitResturantClass.get(position).getName();
                            Restimage = retofitResturantClass.get(position).getImage();
                            Restrating = retofitResturantClass.get(position).getRating();
                            Restopening = retofitResturantClass.get(position).getopenandclose();
                            FeeDeliveryValue=retofitResturantClass.get(position).getFeeDeliveryValue();
                            OfferResturantValue= retofitResturantClass.get(position).getofferValue();
                            OfferFeeTypeId=retofitResturantClass.get(position).getOfferFeeTypeId();
                            Restimage= retofitResturantClass.get(position).getImage();
                            mincharge= Double.parseDouble(retofitResturantClass.get(position).getmincharge());
                            currentusedresturantId =resturantId;
                            startActivity(ResturantsIntent);

                        }

                    }
                })

        );
        initViews();

    }

    private void initViews(){
        pd = new ProgressDialog(this);
        pd.setMessage("Fetching Movies...");
        pd.setCancelable(false);
        pd.show();
        loadRetrofitResturantJSON();
    }
    private void loadRetrofitResturantJSON() {
        try {
            // RetrofitService service = RetrofitClass.createRetrofitService(RetrofitService.class);
            RetrofitService service =RetrofitClass.retrofit.create(RetrofitService.class);
            service.getobserveResturant()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<JSONResponse>() {//getUser(1)
                        @Override
                        public final void onCompleted() {
                            // do nothing
                            //Toast.makeText(this, "completed", Toast.LENGTH_SHORT).show();
                            try {
                                //Toast.makeText(this, , Toast.LENGTH_SHORT).show();
                                pd.hide();
                            }
                            catch (Exception e1){
                                e1.getMessage();
                            }
                        }

                        @Override
                        public final void onError(Throwable e) {
                            try {
                                e.getMessage();
                                pd.hide();
                                }
                            catch (Exception e1){
                                e1.getMessage();
                            }
                        }

                        @Override
                        public void onNext(JSONResponse jsonResponse) {
                            retofitResturantClass = new ArrayList<RetofitResturantClass>();
                retofitoriginalList = new ArrayList<RetofitResturantClass>();
                            FeeTypeid=1;
                            retofitResturantClass = jsonResponse.getRetrofirRetofitResturant();
                            retofitoriginalList=jsonResponse.getRetrofirRetofitResturant();
                            restnumber.setText(retofitResturantClass.size()+" "+getString(R.string.Resturantsarefound));
                            adapterResturantRetrofit= new AdapterResturantRetrofit(getApplicationContext(),retofitResturantClass, retofitoriginalList);
                            recyclerresturantView.setAdapter(adapterResturantRetrofit);
                            recyclerresturantView.smoothScrollToPosition(0);
                            pd.hide();
                        }


                    });
        }catch (Exception e) {
        Log.d("Error", e.getMessage());
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            pd.hide();
    }
//            RetrofitService request =RetrofitClass.retrofit.create(RetrofitService.class);
//            Call<JSONResponse> call;
//            if(findresturantmapflag==1){
//                findresturantmapflag=0;
//                resturantofferFlag=0;
//               call = request.getJSON(ClientInformationregionId);
//            }else {
//                resturantofferFlag=0;
//                findresturantmapflag=0;
//                call = request.getJSON(GlobalRegionID);//?RegionID=GlobalRegionID
////                call= request.getResturants();
//            }
//
//        call.enqueue(new Callback<JSONResponse>() {
//            @Override
//            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
////                JSONResponse jsonResponse = response.body();
//                retofitResturantClass = new ArrayList<RetofitResturantClass>();
//                retofitoriginalList = new ArrayList<RetofitResturantClass>();
//                retofitResturantClass = response.body().getRetrofirRetofitResturant();
//                retofitoriginalList = response.body().getRetrofirRetofitResturant();
//                FeeTypeid=1;
//                restnumber.setText(retofitResturantClass.size()+" "+getString(R.string.Resturantsarefound));
//
//                adapterResturantRetrofit= new AdapterResturantRetrofit(getApplicationContext(),retofitResturantClass, retofitoriginalList);
//                recyclerresturantView.setAdapter(adapterResturantRetrofit);
//                recyclerresturantView.smoothScrollToPosition(0);
//            }
//
//            @Override
//            public void onFailure(Call<JSONResponse> call, Throwable t) {
//            try {
//                t.getMessage();
//            }catch (Exception e){
//                e.getMessage();
//            }
//               // Log.d("Error", t.getMessage());
//            }
//        });
//    } catch (Exception e) {
//        Log.d("Error", e.getMessage());
//        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
//    }
    }


    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
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
                adapterResturantRetrofit.filterData(query);
                // return false;
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterResturantRetrofit.filterData(newText);
                return false;
            }

        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                custom = new Dialog(RetrofitResturantActivity.this);
                custom.setContentView(R.layout.resturantfilter);
//                custom.setTitle("filter");
                RadioGroup filtergroup=(RadioGroup)custom.findViewById(R.id.langgroup);
                filtergroup.setOnCheckedChangeListener(new
                                                               RadioGroup.OnCheckedChangeListener() {
                                                                   @Override
                                                                   public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                                       switch (checkedId) {
                                                                           case R.id.NameAsc:
                                                                               adapterResturantRetrofit.sortByNameAsc();
                                                                               custom.dismiss();
                                                                               break;
                                                                           case R.id.NameDesc:
                                                                               adapterResturantRetrofit.sortByNameDesc();
                                                                               custom.dismiss();
                                                                               break;
                                                                           case R.id.RateAsc:
                                                                               adapterResturantRetrofit.sortByRateAsc();
                                                                               custom.dismiss();
                                                                               break;
                                                                           case R.id.RateDesc:
                                                                               adapterResturantRetrofit.sortByRateDesc();
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

