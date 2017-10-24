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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.srccode.takeawayproject.Activities.R;
import com.example.srccode.takeawayproject.Classes.ClassResturants;
import com.example.srccode.takeawayproject.WebServices.MyApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.example.srccode.takeawayproject.Global.GlopalClass.typeface;

/**
 * Created by ayman on 2017-05-14.
 */

public class AdapterClassResturant extends  RecyclerView.Adapter<AdapterClassResturant.MyViewHolder>{


    ArrayList<ClassResturants> classResturantsList;
    ArrayList<ClassResturants> originalList;
    ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();

Context mcontext;
    public AdapterClassResturant( Context context,ArrayList<ClassResturants> objects,
                                 ArrayList<ClassResturants> objects2) {

        classResturantsList = objects;

       originalList=objects2;
        mcontext=context;

    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout rowlayout;
        public ImageView restimageView;
        public TextView restName;
        public TextView mincharge;
        public TextView restdelivery;
        public RatingBar restRating;

        public TextView restofferimageView;

        public MyViewHolder(View itemView) {
            super(itemView);
         this.rowlayout=(LinearLayout)itemView.findViewById(R.id.resturantrowid) ;
            this.restimageView = (ImageView)itemView.findViewById(R.id.resturantlogo);
            this.restofferimageView = (TextView)itemView.findViewById(R.id.resturantofferlogo);
            this.restName = (TextView)itemView.findViewById(R.id.restName);
            this.mincharge = (TextView)itemView.findViewById(R.id.minchargeid);
            this.restdelivery = (TextView)itemView.findViewById(R.id.restdeliveryid);
            this.restRating= (RatingBar)itemView.findViewById(R.id.restrating_bar);
        }
    }


    @Override
    public AdapterClassResturant.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.resturants_row, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterClassResturant.MyViewHolder holder, int position) {
        // if offline will appear the view of layout with ic image
        //  holder.restimageView.setImageResource(R.mipmap.ic_launcher);
    //    holder.restimageView.setImageUrl(classResturantsList.get(position).getImage(), imageLoader);
        int viewtype=getItemViewType(position);
        if(viewtype==111){
           holder.rowlayout.setBackgroundColor(Color.parseColor("#FFCC80"));
        }

        if (imageLoader == null)
            imageLoader = MyApplication.getInstance().getImageLoader();
        if(classResturantsList.get(position).getofferID()==0) {
            //  holder.restofferimageView.setPaintFlags(pv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
           holder.restofferimageView.setVisibility(View.INVISIBLE);
        }
        holder.restName.setText(classResturantsList.get(position).getName());
        holder.restName.setTypeface(typeface);
        holder.restRating.setRating((classResturantsList.get(position).getRating()/2));
        holder.mincharge.setText((classResturantsList.get(position).getmincharge()));
        holder.restdelivery.setText((classResturantsList.get(position).getFeeDeliveryValue())+"");

        LayerDrawable stars = (LayerDrawable)  holder.restRating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.rgb(255,131,66), PorterDuff.Mode.SRC_ATOP);
        holder.restimageView.setImageResource(R.mipmap.ic_launcher);
      //  new DownloadImageTask( holder.restimageView).execute(classResturantsList.get(position).getImage());
       // holder.restimageView.setImageUrl(classResturantsList.get(position).getImage(), imageLoader);
        Glide.with(mcontext).load(classResturantsList.get(position).getImage()).apply(RequestOptions.circleCropTransform()).into( holder.restimageView);

    }

    @Override
    public int getItemCount() {
        return classResturantsList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if(position%2!=0)
        {
      return 111;
        }

        return super.getItemViewType(position);
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
                ,resturant.getofferValue(),resturant.getOfferFeeTypeId());
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