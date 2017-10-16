package com.example.srccode.takeawayproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.TextView;


import com.activeandroid.query.Delete;
import com.example.srccode.takeawayproject.Activities.R;
import com.example.srccode.takeawayproject.Classes.ClassClientInformation;

import java.util.ArrayList;

/**
 * Created by ayman on 2017-06-14.
 */

public class AdapterClientAdress extends ArrayAdapter<ClassClientInformation> {


    ArrayList<ClassClientInformation> classClientInformationlist;
//    ClassClientInformation ClientInformationdata;

    LayoutInflater vi;
    int Resource;
    AdapterClientAdress.ViewHolder holder;


    public AdapterClientAdress(Context context, int resource, ArrayList<ClassClientInformation> objects) {
        super(context, resource, objects);

        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        classClientInformationlist = objects;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;

        // if website is closed
        if (v == null) {
            holder = new AdapterClientAdress.ViewHolder();

            v = vi.inflate(Resource, null);


            holder.addressName = (TextView) v.findViewById(R.id.adressviewid);

            v.setTag(holder);


        } else {

            holder = (AdapterClientAdress.ViewHolder) v.getTag();
        }


        if (classClientInformationlist != null) {
           // ClientInformationdata =  new Select().from(ClassClientInformation.class).where("orderid = ?", classClientInformationlist.get(position).getId()).executeSingle();

            holder.addressName.setText(classClientInformationlist.get(position).getadress());
        }
        Button deletebtn=(Button)v.findViewById(R.id.deletebtn);
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // new Delete().from(ClassViewOrder.class).where("Id = ?", ViewOrderData.getId()).execute();
                new Delete().from(ClassClientInformation.class).where("Id = ?", classClientInformationlist.get(position).getId()).execute();

                classClientInformationlist.remove(position);
                notifyDataSetChanged();

            }
        });

        return v;


    }

    static class ViewHolder {
        public TextView addressName;

    }
}