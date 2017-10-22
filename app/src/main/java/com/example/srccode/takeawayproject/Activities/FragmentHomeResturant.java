package com.example.srccode.takeawayproject.Activities;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.srccode.takeawayproject.Adapters.AdapterClassRestOffer;
import com.example.srccode.takeawayproject.Classes.ClassResturants;
import com.example.srccode.takeawayproject.Classes.ClassViewOrderDb;
import com.example.srccode.takeawayproject.UI.RecyclerItemClickListener;

import com.example.srccode.takeawayproject.WebServices.ResturantOfferWebService;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.srccode.takeawayproject.Global.GlopalClass.FeeDeliveryValue;
import static com.example.srccode.takeawayproject.Global.GlopalClass.GlobalRegionID;
import static com.example.srccode.takeawayproject.Global.GlopalClass.HostName;
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
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantofferFlag;
import static com.example.srccode.takeawayproject.Global.GlopalClass.tableviewOrderDb;
import static com.example.srccode.takeawayproject.Global.GlopalClass.typeface;


/**
 * Created by ayman on 2017-08-15.
 */

public class FragmentHomeResturant extends Fragment

{
    ListView listView;
    private android.support.v4.app.FragmentManager fragmentManager;
    private RecyclerView.LayoutManager layoutManager;

//    private ContextMenuDialogFragment mMenuDialogFragment;
    private Context myContext;
    SearchManager searchManager;
    Dialog custom;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //View view=inflater.inflate(R.layout.fragmenthomemap,container,false);
        View view=inflater.inflate(R.layout.fragmenthomemap,container,false);

        setHasOptionsMenu(true);
        ActiveAndroid.initialize(getContext());

        classResturantsList = new ArrayList<ClassResturants>();
        originalList = new ArrayList<ClassResturants>();

        if (GlobalRegionID!=0){
            resturantofferFlag=1;
            new ResturantOfferWebService(getContext()).execute("http://"+ HostName+"/api/Restaurants?RegionID=" + GlobalRegionID);//GlobalRegionID

        }
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view,final int position) {
                        tableviewOrderDb =new Select().from(ClassViewOrderDb.class).execute();

                if (tableviewOrderDb.size()!=0 && currentusedresturantId!=Integer.parseInt(classResturantsList.get(position).getId())){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

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
                            Toast.makeText(getContext(), "You cleared your order", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getContext(), classResturantsList.get(position).getName(), Toast.LENGTH_LONG).show();
                            Intent ResturantsIntent = new Intent(getContext(), ActivityCategory.class);
                            resturantId = Integer.parseInt(classResturantsList.get(position).getId());
                            Restname = classResturantsList.get(position).getName();
                            Restimage = classResturantsList.get(position).getImage();
                            Restrating = classResturantsList.get(position).getRating();
                            Restopening = classResturantsList.get(position).getopenandclose();
                            FeeDeliveryValue=classResturantsList.get(position).getFeeDeliveryValue();
                            offerID=classResturantsList.get(position).getofferID();
                            OfferResturantValue=classResturantsList.get(position).getofferValue();
                            currentusedresturantId =resturantId;
                            mincharge= Double.parseDouble(classResturantsList.get(position).getmincharge());
                            resturantofferFlag=0;
                            currentusedresturantId=resturantId;
                            startActivity(ResturantsIntent);
                        }
                    });

                    // Setting Negative "NO" Button
                    alertDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Toast.makeText(getContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                            dialog.cancel();

                            return ;
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();

                }
                else{
                    Toast.makeText(getContext(), classResturantsList.get(position).getName(), Toast.LENGTH_LONG).show();
                    Intent ResturantsIntent = new Intent(getContext(), ActivityCategory.class);
                    resturantId = Integer.parseInt(classResturantsList.get(position).getId());
                    Restname = classResturantsList.get(position).getName();
                    Restimage = classResturantsList.get(position).getImage();
                    Restrating = classResturantsList.get(position).getRating();
                    Restopening = classResturantsList.get(position).getopenandclose();
                    FeeDeliveryValue=classResturantsList.get(position).getFeeDeliveryValue();
                    offerID=classResturantsList.get(position).getofferID();
                    OfferResturantValue=classResturantsList.get(position).getofferValue();
                    currentusedresturantId =resturantId;
                    mincharge= Double.parseDouble(classResturantsList.get(position).getmincharge());

                    startActivity(ResturantsIntent);

                }

            }
        })

        );



        return  view;
  }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fragmentManager =getActivity().getSupportFragmentManager();
       // initMenuFragment();

        adapterClassRestOffer = new AdapterClassRestOffer(classResturantsList, originalList);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterClassRestOffer);

    }

    @Override
    public void onAttach(Context context) {
        myContext=context;
        super.onAttach(context);
    }






//    private void initMenuFragment() {
//        MenuParams menuParams = new MenuParams();
//        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
//        menuParams.setMenuObjects(getMenuObjects());
//        menuParams.setClosableOutside(false);
//        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
//        mMenuDialogFragment.setItemClickListener(new OnMenuItemClickListener() {
//            @Override
//            public void onMenuItemClick(View clickedView, int position) {
//                switch (position){
//                    case 1:
//                        adapterClassRestOffer.sortByNameAsc();
//                        break;
//                    case 2:
//                        adapterClassRestOffer.sortByNameDesc();
//                        break;
//                    case 3:
//                        adapterClassRestOffer.sortByRateAsc();
//                        break;
//                    case 4:
//                        adapterClassRestOffer.sortByRateDesc();
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
//        //  mMenuDialogFragment.setItemLongClickListener(this);
//    }
//    private List<MenuObject> getMenuObjects() {
//
//
//        List<MenuObject> menuObjects = new ArrayList<>();
//
//        MenuObject close = new MenuObject();
//        close.setResource(R.drawable.cleararea);
//
//        MenuObject ascname = new MenuObject(getString(R.string.RestaurantnameAsc));
//        ascname.setResource(R.drawable.ic_arrow_upward_black_24dp);
//
//        MenuObject descname = new MenuObject(getString(R.string.RestaurantnameDsc));
//        Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.ic_arrow_downward_black_24dp);
//        descname.setBitmap(b);
//
//        MenuObject ascrate= new MenuObject(getString(R.string.RestaurantrateAsc));
//        ascrate.setResource(R.drawable.rateup);
//
//        MenuObject descrate= new MenuObject(getString(R.string.RestaurantrateDsc));
//        Bitmap c = BitmapFactory.decodeResource(getResources(),R.drawable.ratedown);
//        descrate.setBitmap(c);
//
//        menuObjects.add(close);
//        menuObjects.add(ascname);
//        menuObjects.add(descname);
//        menuObjects.add(ascrate);
//        menuObjects.add(descrate);
//
//        return menuObjects;
//    }



//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.context_menu:
//                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
//                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
//                }
//                break;
//            case R.id.action_refresh:
//                getActivity().finish();
//                startActivity(getActivity().getIntent());
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                custom = new Dialog(getContext());
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       // super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.resturant_offer_sort_menu, menu);
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
     //   return super.onCreateOptionsMenu(menu);
    }
}


