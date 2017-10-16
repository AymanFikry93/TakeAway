package com.example.srccode.takeawayproject;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.srccode.takeawayproject.Activities.R;

import java.util.ArrayList;

/**
 * Created by ayman on 2017-07-23.
 */

public class AdapterSpinner extends BaseAdapter {

    private LayoutInflater mInflater;
    ArrayList<String> COUNTRIES;
    private Typeface myFont;
    public AdapterSpinner(Context context,ArrayList<String> countryvalueslist,Typeface typefac) {
        // TODO Auto-generated constructor stub
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        COUNTRIES=countryvalueslist;
        myFont=typefac;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return COUNTRIES.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final ViewHolder holder;
        View v = convertView;
        if (v == null) {
            v = mInflater.inflate(R.layout.spinner_row, null);
            holder = new ViewHolder();

            holder.regionName = (TextView) v.findViewById(R.id.countryName);

            v.setTag(holder);
        } else {

            holder = (ViewHolder) v.getTag();
        }

        holder.regionName.setTypeface(myFont);
        holder.regionName.setText(COUNTRIES.get(position));

        return v;
    }
    static class ViewHolder {
        public TextView regionName;


    }
}




