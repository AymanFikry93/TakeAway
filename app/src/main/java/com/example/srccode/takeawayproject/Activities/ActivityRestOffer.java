package com.example.srccode.takeawayproject.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.srccode.takeawayproject.Adapters.AdapterClassRestOffer;
import com.example.srccode.takeawayproject.Classes.ClassResturants;
import com.example.srccode.takeawayproject.Classes.ClassViewOrderDb;
import com.example.srccode.takeawayproject.Classes.JSONResponse;
import com.example.srccode.takeawayproject.Classes.RetofitResturantClass;
import com.example.srccode.takeawayproject.UI.RecyclerItemClickListener;
import com.example.srccode.takeawayproject.WebServices.ResturantWebService;
import com.example.srccode.takeawayproject.service.AdapterResturantRetrofit;
import com.example.srccode.takeawayproject.service.RetrofitClass;
import com.example.srccode.takeawayproject.service.RetrofitService;
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
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restimage;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restname;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restopening;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restrating;
import static com.example.srccode.takeawayproject.Global.GlopalClass.adapterClassRestOffer;
import static com.example.srccode.takeawayproject.Global.GlopalClass.adapterClassResturant;
import static com.example.srccode.takeawayproject.Global.GlopalClass.classResturantsList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.currentusedresturantId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.mincharge;
import static com.example.srccode.takeawayproject.Global.GlopalClass.offerID;
import static com.example.srccode.takeawayproject.Global.GlopalClass.originalList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.recyclerView;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantofferFlag;
import static com.example.srccode.takeawayproject.Global.GlopalClass.retofitResturantClass;
import static com.example.srccode.takeawayproject.Global.GlopalClass.retofitoriginalList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.tableviewOrderDb;

public class ActivityRestOffer extends AppCompatActivity implements
        SearchView.OnQueryTextListener, SearchView.OnCloseListener{

    ListView listView;
    private SearchView mSearchView;
    private RecyclerView.LayoutManager layoutManager;
    private android.support.v4.app.FragmentManager fragmentManager;

    private ContextMenuDialogFragment mMenuDialogFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_offer);
        ActiveAndroid.initialize(this);

//        retofitoriginalList = new List<RetofitResturantClass>();
//        retofitResturantClass = new List<RetofitResturantClass>();

        fragmentManager = getSupportFragmentManager();
        initMenuFragment();
        loadJSON();

//        if (GlobalRegionID!=0){
//            resturantofferFlag=1;
//
//          //  new ResturantWebService(getApplicationContext()).execute("http://"+HostName+"/api/Restaurants?RegionID=" + GlobalRegionID);
//            loadJSON();
//        }


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) findViewById(R.id.search);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnCloseListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //adapterClassRestOffer = new AdapterClassRestOffer(classResturantsList, originalList);

        //recyclerView.setAdapter(adapterClassRestOffer);
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
                                    Intent ResturantsIntent = new Intent(getApplicationContext(), PagerActivity.class);
                                    resturantId = Integer.parseInt(classResturantsList.get(position).getId());
                                    Restname = classResturantsList.get(position).getName();
                                    Restimage = classResturantsList.get(position).getImage();
                                    Restrating = classResturantsList.get(position).getRating();
                                    Restopening = classResturantsList.get(position).getopenandclose();
                                    FeeDeliveryValue=classResturantsList.get(position).getFeeDeliveryValue();
                                    offerID=classResturantsList.get(position).getofferID();
//                                    offerValue=classResturantsList.get(position).getofferValue();
                                    currentusedresturantId =resturantId;
                                    mincharge= Double.parseDouble(classResturantsList.get(position).getmincharge());

                                    currentusedresturantId=resturantId;
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
                            offerID=classResturantsList.get(position).getofferID();
//                            offerValue=classResturantsList.get(position).getofferValue();
                            currentusedresturantId =resturantId;
                            mincharge= Double.parseDouble(classResturantsList.get(position).getmincharge());


                            startActivity(ResturantsIntent);

                        }

                    }
                })

        );
      //  listView = (ListView) findViewById(R.id.list);
      //  adapterClassRestOffer = new AdapterClassRestOffer(getApplicationContext(), R.layout.rest_offer_row, classResturantsList, originalList);
       // listView.setAdapter(adapterClassRestOffer);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//
//
//                tableviewOrderDb =new Select().from(ClassViewOrderDb.class).execute();
//
//
//                if (tableviewOrderDb.size()!=0 && currentusedresturantId!=Integer.parseInt(classResturantsList.get(position).getId())){
//                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ActivityRestOffer.this);
//
//                    // Setting Dialog Title
//                    alertDialog.setTitle("Warning...");
//
//                    // Setting Dialog Message
//                    alertDialog.setMessage("this order from another resturant,your order will be lost");
//
//                    // Setting Icon to Dialog
//                    alertDialog.setIcon(R.drawable.cleararea);
//
//                    // Setting Positive "Yes" Button
//                    alertDialog.setPositiveButton("continue", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog,int which) {
//                            new Delete().from(ClassViewOrderDb.class).execute();
//
//                            // Write your code here to invoke YES event
//                            Toast.makeText(getApplicationContext(), "You cleared your order", Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getApplicationContext(), classResturantsList.get(position).getName(), Toast.LENGTH_LONG).show();
//                            Intent ResturantsIntent = new Intent(getApplicationContext(), PagerActivity.class);
//                            resturantId = Integer.parseInt(classResturantsList.get(position).getId());
//                            Restname = classResturantsList.get(position).getName();
//                            Restimage = classResturantsList.get(position).getImage();
//                            Restrating = classResturantsList.get(position).getRating();
//                            Restopening = classResturantsList.get(position).getopenandclose();
//                            FeeDeliveryValue=classResturantsList.get(position).getFeeDeliveryValue();
//                            offerID=classResturantsList.get(position).getofferID();
//                            offerValue=classResturantsList.get(position).getofferValue();
//                            currentusedresturantId =resturantId;
//                            mincharge= Double.parseDouble(classResturantsList.get(position).getmincharge());
//
//                            currentusedresturantId=resturantId;
//                            startActivity(ResturantsIntent);
//                        }
//                    });
//
//                    // Setting Negative "NO" Button
//                    alertDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
//                            dialog.cancel();
//
//                            return ;
//                        }
//                    });
//
//                    // Showing Alert Message
//                    alertDialog.show();
//
//                }
//                else{
//                    Toast.makeText(getApplicationContext(), classResturantsList.get(position).getName(), Toast.LENGTH_LONG).show();
//                    Intent ResturantsIntent = new Intent(getApplicationContext(), PagerActivity.class);
//                    resturantId = Integer.parseInt(classResturantsList.get(position).getId());
//                    Restname = classResturantsList.get(position).getName();
//                    Restimage = classResturantsList.get(position).getImage();
//                    Restrating = classResturantsList.get(position).getRating();
//                    Restopening = classResturantsList.get(position).getopenandclose();
//                    FeeDeliveryValue=classResturantsList.get(position).getFeeDeliveryValue();
//                    offerID=classResturantsList.get(position).getofferID();
//                    offerValue=classResturantsList.get(position).getofferValue();
//                    currentusedresturantId =resturantId;
//                    mincharge= Double.parseDouble(classResturantsList.get(position).getmincharge());
//
//
//                    startActivity(ResturantsIntent);
//
//                }
//
//            }
//        });


    }

    private void loadJSON() {
//        Disconnected = (TextView) findViewById(R.id.disconnected);
        try {
            RetrofitClass Client = new RetrofitClass();
            RetrofitService request =RetrofitClass.retrofit.create(RetrofitService.class);
            Call<JSONResponse> call = request.getJSON();//.getMovies();//JSONResponse  List<RetofitResturantClass>
            call.enqueue(new Callback<JSONResponse>() {

                @Override
                public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                    JSONResponse jsonResponse = response.body();
                    retofitResturantClass = response.body().getAndroid();
                    recyclerView.setAdapter(new AdapterResturantRetrofit(retofitResturantClass, retofitoriginalList));
                    recyclerView.smoothScrollToPosition(0);
                    //Toast.makeText(MainActivity.this, movieArrayList.toString(), Toast.LENGTH_SHORT).show();
//                    swipeContainer.setRefreshing(false);
//                    pd.hide();
                }

                @Override
                public void onFailure(Call<JSONResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(ActivityRestOffer.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                    //Disconnected.setVisibility(View.VISIBLE);
//                    pd.hide();
                }

            });

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onClose() {
        adapterClassResturant.filterData("");
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapterClassRestOffer.filterData(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapterClassRestOffer.filterData(newText);
        return false;
    }



    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View clickedView, int position) {
                switch (position){
                    case 1:
                        adapterClassRestOffer.sortByNameAsc();
                        break;
                    case 2:
                        adapterClassRestOffer.sortByNameDesc();
                        break;
                    case 3:
                        adapterClassRestOffer.sortByRateAsc();
                        break;
                    case 4:
                        adapterClassRestOffer.sortByRateDesc();
                        break;
                    default:
                        break;
                }
            }
        });
        //  mMenuDialogFragment.setItemLongClickListener(this);
    }
    private List<MenuObject> getMenuObjects() {


        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.cleararea);

        MenuObject ascname = new MenuObject(getString(R.string.RestaurantnameAsc));
        ascname.setResource(R.drawable.ic_arrow_upward_black_24dp);

        MenuObject descname = new MenuObject(getString(R.string.RestaurantnameDsc));
        Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.ic_arrow_downward_black_24dp);
        descname.setBitmap(b);

        MenuObject ascrate= new MenuObject(getString(R.string.RestaurantrateAsc));
        ascrate.setResource(R.drawable.rateup);

        MenuObject descrate= new MenuObject(getString(R.string.RestaurantrateDsc));
        Bitmap c = BitmapFactory.decodeResource(getResources(),R.drawable.ratedown);
        descrate.setBitmap(c);

        menuObjects.add(close);
        menuObjects.add(ascname);
        menuObjects.add(descname);
        menuObjects.add(ascrate);
        menuObjects.add(descrate);

        return menuObjects;
    }
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.resturant_sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
            case R.id.action_refresh:
                finish();
                startActivity(getIntent());
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}


