package com.example.srccode.takeawayproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.srccode.takeawayproject.Activities.R;

import java.util.List;


/**
 * Created by ayman on 2017-07-23.
 */

public class AdapterSpinner extends BaseAdapter  {

    Context context;

    List<String> countryNames;
    LayoutInflater inflter;
    public AdapterSpinner(Context context,  List<String> items) {
       //super(context, items);
        this.context = context;
        this.countryNames = items;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return countryNames.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_row, null);
        TextView names = (TextView) view.findViewById(R.id.countryName);
        names.setText(countryNames.get(i));
        return view;
    }
}


