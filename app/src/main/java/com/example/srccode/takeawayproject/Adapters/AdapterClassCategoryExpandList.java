package com.example.srccode.takeawayproject.Adapters;


import android.content.Context;


import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseExpandableListAdapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.srccode.takeawayproject.Activities.R;
import com.example.srccode.takeawayproject.Classes.ClassCategory;
import com.example.srccode.takeawayproject.Classes.ClassItemFood;
import com.example.srccode.takeawayproject.WebServices.MyApplication;

import java.util.ArrayList;

import static com.example.srccode.takeawayproject.Global.GlopalClass.ExpandList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.typeface;


/**
 * Created by ayman on 2017-05-17.
 */

public class AdapterClassCategoryExpandList extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<ClassCategory> classCategories;

    ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();

    public AdapterClassCategoryExpandList(Context context, ArrayList<ClassCategory> classCategories) {
        this.context = context;
        this.classCategories = classCategories;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<ClassItemFood> chList = classCategories.get(groupPosition).getItems();
        return chList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        ClassItemFood classItemFood = (ClassItemFood) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_food, null);
        }

        if (imageLoader == null)
            imageLoader = MyApplication.getInstance().getImageLoader();

        TextView tv = (TextView) convertView.findViewById(R.id.itemfood_name);
        TextView pv = (TextView) convertView.findViewById(R.id.itemfood_price);
        TextView offerv = (TextView) convertView.findViewById(R.id.offertextid);
        TextView offerimagevText = (TextView) convertView.findViewById(R.id.itemfood_offerimage);

        ImageView iv = (ImageView) convertView
                .findViewById(R.id.itemfood_image);
//        iv.setImageUrl(classItemFood.getImage(), imageLoader);
        Glide.with(context).load(classItemFood.getImage()).apply(RequestOptions.circleCropTransform()).into( iv);

        tv.setText(classItemFood.getName().toString());
        tv.setTypeface(typeface);
        pv.setTypeface(typeface);
        pv.setText(context.getString(R.string.Price)+classItemFood.getPrice().toString()+context.getResources().getString(R.string.SAR));

        offerv.setVisibility(View.GONE);
        offerimagevText.setVisibility(View.GONE);
        double prce= Double.parseDouble(classItemFood.getPrice().toString());
        pv.setPaintFlags( pv.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        if(classItemFood.getitemofferid()!=0){
            pv.setPaintFlags(pv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            offerv.setVisibility(View.VISIBLE);
            offerimagevText.setVisibility(View.VISIBLE);

            double offerval= classItemFood.getitemOfferValue();
            if(classItemFood.getitemOfferFeeTypeId()==2){
                prce=prce-(prce*offerval*.01);
            }else if(classItemFood.getitemOfferFeeTypeId()==1){
                prce=prce-offerval;
            }

            offerv.setText(context.getString(R.string.newprice)+prce+context.getResources().getString(R.string.SAR));
            offerv.setTypeface(typeface);
        }


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<ClassItemFood> chList = classCategories.get(groupPosition).getItems();
        return chList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return classCategories.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return classCategories.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        //        ExpandableListView eLV = (ExpandableListView) parent;
//        eLV.expandGroup(groupPosition);
//        ExpandList.expandGroup(groupPosition);
        ClassCategory classCategory = (ClassCategory) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.xlistcategory, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.category_name);
        tv.setText(classCategory.getName());
        tv.setTypeface(typeface);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}