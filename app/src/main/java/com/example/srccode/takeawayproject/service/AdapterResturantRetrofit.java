package com.example.srccode.takeawayproject.service;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.srccode.takeawayproject.Activities.R;
import com.example.srccode.takeawayproject.Adapters.AdapterClassRestOffer;
import com.example.srccode.takeawayproject.Classes.ClassResturants;
import com.example.srccode.takeawayproject.Classes.RetofitResturantClass;
import com.example.srccode.takeawayproject.WebServices.MyApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.srccode.takeawayproject.Global.GlopalClass.retofitResturantClass;
import static com.example.srccode.takeawayproject.Global.GlopalClass.retofitoriginalList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.typeface;

/**
 * Created by ayman on 2017-09-28.
 */

public class AdapterResturantRetrofit extends RecyclerView.Adapter<AdapterResturantRetrofit.MyViewHolder> {


    List<RetofitResturantClass> classResturantsList;
    List<RetofitResturantClass> originalList;
    ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName,mincharge;
        public TextView restdelivery;
        RatingBar stars;
        NetworkImageView imageViewIcon;
        public TextView restofferimageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.restName);
            this.mincharge = (TextView) itemView.findViewById(R.id.minchargeid);
            this.stars = (RatingBar) itemView.findViewById(R.id.restrating_bar);
            this.restdelivery = (TextView)itemView.findViewById(R.id.restdeliveryid);

            this.imageViewIcon = (NetworkImageView) itemView.findViewById(R.id.resturantlogo);
            this.restofferimageView = (TextView)itemView.findViewById(R.id.resturantofferlogo);

        }
    }

    public AdapterResturantRetrofit(List<RetofitResturantClass> objects, List<RetofitResturantClass> objects2) {

        retofitResturantClass = objects;
        retofitoriginalList =objects2;
    }



    @Override
    public AdapterResturantRetrofit.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rest_offer_row, parent, false);

        AdapterResturantRetrofit.MyViewHolder myViewHolder = new AdapterResturantRetrofit.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterResturantRetrofit.MyViewHolder holder, int position) {
        TextView restName = holder.textViewName;
        TextView mincharge = holder.mincharge;

        RatingBar restRating = holder.stars;
        NetworkImageView restimageView = holder.imageViewIcon;
        if (imageLoader == null)
            imageLoader = MyApplication.getInstance().getImageLoader();
        if(classResturantsList.get(position).getofferID()==0) {
            //  holder.restofferimageView.setPaintFlags(pv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.restofferimageView.setVisibility(View.GONE);
        }
        restName.setText(classResturantsList.get(position).getName());
        mincharge.setText(classResturantsList.get(position).getmincharge());
        holder.restdelivery.setText((classResturantsList.get(position).getFeeDeliveryValue())+"");

        restName.setTypeface(typeface);
        restRating.setRating((classResturantsList.get(position).getRating()/2));
        LayerDrawable stars = (LayerDrawable)  restRating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.rgb(255,131,66), PorterDuff.Mode.SRC_ATOP);
        restimageView.setImageResource(R.mipmap.ic_launcher);
        //   new DownloadImageTask(restimageView).execute(classResturantsList.get(position).getImage());
        restimageView.setImageUrl(classResturantsList.get(position).getImage(), imageLoader);

    }

    @Override
    public int getItemCount() {
        return classResturantsList.size();
    }


    public void filterData(String query){

        query = query.toLowerCase();
        Log.v("MyListAdapter", String.valueOf(classResturantsList.size()));
        classResturantsList.clear();

        if(query.isEmpty()){
            classResturantsList.addAll(originalList);
        }
        else {


            for(RetofitResturantClass resturant: originalList){
                ArrayList<RetofitResturantClass> newList = new ArrayList<RetofitResturantClass>();

                if(resturant.getName().toLowerCase().contains(query))

                {
                    newList.add(resturant);
                }

                if(newList.size() > 0){
                    RetofitResturantClass nresturant = new RetofitResturantClass(resturant.getId(),resturant.getName(),
                            resturant.getopenandclose(),resturant.getmincharge(),resturant.getRating(),resturant.getImage()
                            ,resturant.getofferValue(),resturant.getOfferFeeTypeId());
                    classResturantsList.add(nresturant);
                }
            }
        }



        notifyDataSetChanged();

    }

    public void sortByNameAsc() {
        Comparator<RetofitResturantClass> comparator = new Comparator<RetofitResturantClass>() {

            @Override
            public int compare(RetofitResturantClass o1, RetofitResturantClass o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }


        };
        Collections.sort(classResturantsList, comparator);
        notifyDataSetChanged();
    }

    /** Sort shopping list by name descending */
    public void sortByNameDesc() {
        Comparator<RetofitResturantClass> comparator = new Comparator<RetofitResturantClass>() {

            @Override
            public int compare(RetofitResturantClass object1, RetofitResturantClass object2) {
                return object2.getName().compareToIgnoreCase(object1.getName());
            }
        };
        Collections.sort(classResturantsList, comparator);
        notifyDataSetChanged();
    }

    public void sortByRateAsc() {
        Comparator<RetofitResturantClass> comparator = new Comparator<RetofitResturantClass>() {

            @Override
            public int compare(RetofitResturantClass o1, RetofitResturantClass o2) {
                return ( String.valueOf(o1.getRating())).compareToIgnoreCase(String.valueOf(o2.getRating()));
            }


        };
        Collections.sort(classResturantsList, comparator);
        notifyDataSetChanged();
    }

    /** Sort shopping list by name descending */
    public void sortByRateDesc() {
        Comparator<RetofitResturantClass> comparator = new Comparator<RetofitResturantClass>() {

            @Override
            public int compare(RetofitResturantClass object1, RetofitResturantClass object2) {
                return ( String.valueOf(object2.getRating())).compareToIgnoreCase(String.valueOf(object1.getRating()));

            }
        };
        Collections.sort(classResturantsList, comparator);
        notifyDataSetChanged();
    }


}