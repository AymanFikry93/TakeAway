package com.example.srccode.takeawayproject.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.srccode.takeawayproject.Activities.R;
import com.example.srccode.takeawayproject.Classes.ClassResturants;
import com.example.srccode.takeawayproject.WebServices.DownloadImageTask;
import com.example.srccode.takeawayproject.WebServices.MyApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.example.srccode.takeawayproject.Global.GlopalClass.typeface;

/**
 * Created by ayman on 2017-08-02.
 */

public class AdapterClassRestOffer extends RecyclerView.Adapter<AdapterClassRestOffer.MyViewHolder> {


    ArrayList<ClassResturants> classResturantsList;
    ArrayList<ClassResturants> originalList;
    ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();
Context mcontext;
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

    public AdapterClassRestOffer(Context context, ArrayList<ClassResturants> objects, ArrayList<ClassResturants> objects2) {

        classResturantsList = objects;
        originalList=objects2;
        mcontext=context;
    }



    @Override
    public AdapterClassRestOffer.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rest_offer_row, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterClassRestOffer.MyViewHolder holder, int position) {
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
        mincharge.setText(classResturantsList.get(position).getmincharge()+mcontext.getResources().getString(R.string.SAR));
        holder.restdelivery.setText((classResturantsList.get(position).getFeeDeliveryValue())+mcontext.getResources().getString(R.string.SAR));

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


            for(ClassResturants resturant: originalList){
                ArrayList<ClassResturants> newList = new ArrayList<ClassResturants>();

                if(resturant.getName().toLowerCase().contains(query))

                {
                    newList.add(resturant);
                }

                if(newList.size() > 0){
                    ClassResturants nresturant = new ClassResturants(resturant.getId(),resturant.getName(),
                            resturant.getopenandclose(),resturant.getmincharge(),resturant.getRating(),resturant.getImage()
                            ,resturant.getofferValue(),resturant.getOfferFeeTypeId() ,resturant.getFeeDeliveryValue(),resturant.getofferID());
                    classResturantsList.add(nresturant);
                }
            }
        }



        notifyDataSetChanged();

    }

    public void sortByNameAsc() {
        Comparator<ClassResturants> comparator = new Comparator<ClassResturants>() {

            @Override
            public int compare(ClassResturants o1, ClassResturants o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }


        };
        Collections.sort(classResturantsList, comparator);
        notifyDataSetChanged();
    }

    /** Sort shopping list by name descending */
    public void sortByNameDesc() {
        Comparator<ClassResturants> comparator = new Comparator<ClassResturants>() {

            @Override
            public int compare(ClassResturants object1, ClassResturants object2) {
                return object2.getName().compareToIgnoreCase(object1.getName());
            }
        };
        Collections.sort(classResturantsList, comparator);
        notifyDataSetChanged();
    }

    public void sortByRateAsc() {
        Comparator<ClassResturants> comparator = new Comparator<ClassResturants>() {

            @Override
            public int compare(ClassResturants o1, ClassResturants o2) {
                return ( String.valueOf(o1.getRating())).compareToIgnoreCase(String.valueOf(o2.getRating()));
            }


        };
        Collections.sort(classResturantsList, comparator);
        notifyDataSetChanged();
    }

    /** Sort shopping list by name descending */
    public void sortByRateDesc() {
        Comparator<ClassResturants> comparator = new Comparator<ClassResturants>() {

            @Override
            public int compare(ClassResturants object1, ClassResturants object2) {
                return ( String.valueOf(object2.getRating())).compareToIgnoreCase(String.valueOf(object1.getRating()));

            }
        };
        Collections.sort(classResturantsList, comparator);
        notifyDataSetChanged();
    }


}