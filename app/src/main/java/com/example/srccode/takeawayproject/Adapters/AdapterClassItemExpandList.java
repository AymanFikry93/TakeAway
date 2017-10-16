package com.example.srccode.takeawayproject.Adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.srccode.takeawayproject.Activities.R;
import com.example.srccode.takeawayproject.Classes.ClassItemFood;
import com.example.srccode.takeawayproject.Classes.ClassItemFoodDetails;
import com.example.srccode.takeawayproject.WebServices.MyApplication;

import java.util.ArrayList;
import java.util.Locale;

import static com.example.srccode.takeawayproject.Global.GlopalClass.additions;
import static com.example.srccode.takeawayproject.Global.GlopalClass.drinks;
import static com.example.srccode.takeawayproject.Global.GlopalClass.typeface;


/**
 * Created by ayman on 2017-05-29.
 */

public class AdapterClassItemExpandList extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<ClassItemFood> classitemfood;
    ArrayList<ClassItemFoodDetails> chList2;
    TextView tv;


    RadioButton radioBtn;

    ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();

    public AdapterClassItemExpandList(Context context, ArrayList<ClassItemFood> classitemfoods) {
        this.context = context;
        this.classitemfood = classitemfoods;
    }

    @Override
    public int getGroupCount() {
        return classitemfood.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        ArrayList<ClassItemFoodDetails> chList = classitemfood.get(groupPosition).getItemsdetails();

      if(chList==null){return  0;}
        else
        return chList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return classitemfood.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<ClassItemFoodDetails> chList = classitemfood.get(groupPosition).getItemsdetails();


        return chList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ClassItemFood classItemFood = (ClassItemFood) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.xlist_itemfood_parent, null);
        }
     if(classItemFood!=null) {
         chList2 = classitemfood.get(groupPosition).getItemsdetails();
         tv = (TextView) convertView.findViewById(R.id.fooditemparent_name);
         tv.setText(classItemFood.getName());
         AssetManager am = context.getAssets();
         Typeface typeface = Typeface.createFromAsset(am,
                 String.format(Locale.US, "Fonts/%s", "GESSTwoLight.otf"));
         tv.setTypeface(typeface);
     }else {
         chList2=null;

     }




        RadioGroup radioGroupexp = (RadioGroup) convertView.findViewById(R.id.radiogrbexplist);

        if(chList2!=null){
            String name = classitemfood.get(groupPosition).getName();
        if (name == "مشروبات") {
            radioGroupexp.removeAllViews();
            for (int i = 0; i < chList2.size(); i++) {

                radioBtn = new RadioButton(context);

                radioBtn.setText(chList2.get(i).getName());
                int var = Integer.parseInt(chList2.get(i).getId());
                radioBtn.setId(Integer.parseInt(chList2.get(i).getId()));
                radioBtn.setTextColor(Color.BLACK);

                radioBtn.setTypeface(typeface);

                radioGroupexp.addView(radioBtn);

            }

        } else {
            radioGroupexp.removeAllViews();

            for (int i = 0; i < chList2.size(); i++) {

                RadioButton radioBtn2 = new RadioButton(context);
                radioBtn2.setText(chList2.get(i).getName());
                radioBtn2.setId(Integer.parseInt(chList2.get(i).getId()));
                radioBtn2.setTextColor(Color.BLACK);
                AssetManager am = context.getAssets();
                Typeface typeface = Typeface.createFromAsset(am,
                        String.format(Locale.US, "Fonts/%s", "GESSTwoLight.otf"));
                radioBtn2.setTypeface(typeface);
                radioGroupexp.addView(radioBtn2);

            }
        }
    }


        radioGroupexp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String name =classitemfood.get(groupPosition).getName();
                if(name=="مشروبات") {

                    drinks = checkedId;

                }else {

                    additions=checkedId;

                }
                //   String chList = String.valueOf(classitemfood.get(position).getItemsdetails().get(checkedId).getName());
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ClassItemFoodDetails classItemFooddetails = (ClassItemFoodDetails) getChild(groupPosition, childPosition);
        chList2 = classitemfood.get(groupPosition).getItemsdetails();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_detailsfood_row, null);
        }

        final RadioGroup radioGroupexp=(RadioGroup) convertView.findViewById(R.id.radiogrbexplist);




        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
